package Controller;

import DAO.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import Model.*;
import Utilities.Scenemovement;
import Utilities.Time;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class creates new appointments and saves them to the database. Multiple combo boxes are used so the user can
 * make a specific selection. Checks are made throughout the process validating that the selected times meet the
 * specifications.
 */
public class NewAppointmentController extends AppointmentController {

    public TextField NewAppointmentId;
    public TextField NewAppointmentTitle;
    public TextField NewAppointmentDetails;
    public TextField NewAppointmentLocation;
    public ComboBox <Customer> NewAppointmentCustomerCombo;
    public DatePicker NewAppointmentDatePicker;
    public ComboBox <LocalTime> NewAppointmentStartTimeCombo;
    public ComboBox <LocalTime> NewAppointmentEndTimeCombo;
    public ComboBox <User> NewAppointmentUserCombo;
    public ComboBox <Contact> NewAppointmentContactCombo;
    public ComboBox <String> NewAppointmentTypeCombo;

    ObservableList<String> types = AppointmentOBJ.getApptTypes();
    ObservableList<Customer> customerList = CustomerOBJ.getAllCustomer();
    ObservableList<Contact> contactList = ContactOBJ.getAllContacts();
    ObservableList<User> userList = UserOBJ.getAllUsers();
    ObservableList<LocalTime> times = Time.timeList();
    LocalDate date = null;
    LocalTime start = null;
    LocalTime end =  null;
    Customer customer = null;
    User user = null;
    Contact contact = null;

    /**
     * Initializes the combo boxes in the <code>NewAppt</code> form.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Setting type combo box
        NewAppointmentTypeCombo.setItems(types);
        NewAppointmentTypeCombo.setVisibleRowCount(5);
        NewAppointmentTypeCombo.setPromptText("Select type.");

        //Setting start time combo box
        NewAppointmentStartTimeCombo.setItems(times);
        NewAppointmentStartTimeCombo.setVisibleRowCount(5);
        NewAppointmentStartTimeCombo.setPromptText("Enter start time.");

        //Setting end time combo box
        NewAppointmentEndTimeCombo.setItems(times);
        NewAppointmentEndTimeCombo.setVisibleRowCount(5);
        NewAppointmentEndTimeCombo.setPromptText("Enter end time.");

        //Setting customer combo box
        NewAppointmentCustomerCombo.setItems(customerList);
        NewAppointmentCustomerCombo.setVisibleRowCount(5);
        NewAppointmentCustomerCombo.setPromptText("Select customer.");

        //Setting user combo box
        NewAppointmentUserCombo.setItems(userList);
        NewAppointmentUserCombo.setVisibleRowCount(5);
        NewAppointmentUserCombo.setPromptText("Select user.");

        //Setting contact combo box
        NewAppointmentContactCombo.setItems(contactList);
        NewAppointmentContactCombo.setVisibleRowCount(5);
        NewAppointmentContactCombo.setPromptText("Select contact.");
    }

    /**
     * Collects the data from the text fields, date picker, and combo boxes and makes the following checks:
     *  - All fields are completed.
     *  - The start time is before the end time.
     *  - The customer ID does not have any conflicting appointments.
     *  - The start and/or end times do not fall outside of the EST business hours.
     *  Saves the new appointment to the database and shows alert stating that the save was successful.
     * @param actionEvent Pressing the #saveNewAppt button.
     */
    public void SaveNewAppointment(ActionEvent actionEvent) {

        try {
            String title = String.valueOf(NewAppointmentTitle.getText());
            String description = String.valueOf(NewAppointmentDetails.getText());
            String location = String.valueOf(NewAppointmentLocation.getText());
            String type = String.valueOf(NewAppointmentTypeCombo.getValue());
            date = NewAppointmentDatePicker.getValue();
            start = NewAppointmentStartTimeCombo.getValue();
            end = NewAppointmentEndTimeCombo.getValue();
            customer = NewAppointmentCustomerCombo.getValue();
            user = NewAppointmentUserCombo.getValue();
            contact = NewAppointmentContactCombo.getValue();

            LocalDateTime startDateTime = LocalDateTime.of(date, start);
            LocalDateTime endDateTime = LocalDateTime.of(date, end);


            if (title.isEmpty() | description.isEmpty() | location.isEmpty() | type.isEmpty() | customer == null | user == null | contact == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please complete every field!", ButtonType.OK);
                alert.setTitle("Every Field Must Be Completed");
                alert.showAndWait();
            } else if (start.isAfter(end)){
                Alert alert = new Alert(Alert.AlertType.ERROR, "The start time must come before the end time.", ButtonType.OK);
                alert.setTitle("Start Time Before End Time");
                alert.showAndWait();
            } else if (Appointment.checkConflictingAppointment(customer.getId(), startDateTime, endDateTime)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "This customer has a conflicting appointment. Please pick a different date and/or time.", ButtonType.OK);
                alert.setTitle("Conflicting Appointment");
                alert.showAndWait();
            } else if (startDateTime.equals(endDateTime)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Appointments can't have the same start and end times.", ButtonType.OK);
                alert.setTitle("Conflicting Appointment");
                alert.showAndWait();
            }else if (Time.isOutsideBusinessHours(date, start, end)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "The appointment hours must be between 8:00 AM and 10:00 PM EST.", ButtonType.OK);
                alert.setTitle("Not During Business Hours");
                alert.showAndWait();
            } else {
                LocalDateTime startDate = LocalDateTime.of(date, start);
                LocalDateTime endDate = LocalDateTime.of(date, end);
                Appointment a = new Appointment(title, description, location, type, startDate, endDate, customer.getId(), user.getId(), contact.getId());
                AppointmentOBJ.createAppointment(a);
                Scenemovement.goToPage(actionEvent, "Appointments", 1200, 600, "Appointments");
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Appointment saved!", ButtonType.OK);
                alert.showAndWait();
            }
        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows alert verifying that the user wants to leave the new appointment form.
     * Opens the <code>AppointmentList</code> fxml.
     * @param actionEvent Pressing the #cancelNewAppt button.
     */
    public void CancelNewAppointment(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit? Data will be lost.", ButtonType.YES,ButtonType.NO);
        alert.setTitle("");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.YES) {
            Scenemovement.goToPage(actionEvent, "Appointments", 1200, 600, "Appointments");
        }
    }

    /**
     * Calls the <code>SceneMovements.goToPage</code> method to change to the <code>MainMenu</code> fxml.
     * @param actionEvent The #onActionToMainMenu button
     */
    public void onActionToMainMenu(ActionEvent actionEvent) throws IOException {
        Scenemovement.goToPage(actionEvent, "MainScreen", 600, 400, "Main Screen");
    }

    /**
     * Calls the <code>SceneMovements.goToPage</code> method to change to the <code>Appointments</code> fxml.
     * @param actionEvent The #onActionToApptList button
     */
    public void onActionToAppointment(ActionEvent actionEvent) throws IOException {
        Scenemovement.goToPage(actionEvent, "Appointments", 1200, 600, "Appointments");
    }

    /**
     * Calls the <code>SceneMovements.goToPage</code> method to change to the <code>CustomerList</code> fxml.
     * @param actionEvent The #onActionToCustomer button
     */
    public void onActionToCustomer(ActionEvent actionEvent) throws IOException {
        Scenemovement.goToPage(actionEvent, "Customers", 1000, 600, "Customers");
    }

    /**
     * Calls the <code>SceneMovements.goToPage</code> method to change to the <code>ReportsList</code> fxml.
     * @param actionEvent The #onActionToReport button
     */
    public void onActionToReport(ActionEvent actionEvent) throws IOException {
        Scenemovement.goToPage(actionEvent, "Reports", 600, 400, "Reports");
    }


}