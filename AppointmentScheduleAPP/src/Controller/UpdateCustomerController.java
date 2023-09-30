package Controller;

import DAO.CountryOBJ;
import DAO.CustomerOBJ;
import DAO.DivisionOBJ;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import Model.Country;
import Model.Customer;
import Model.Division;
import Utilities.Scenemovement;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class update a customer and saves it to the database. Multiple combo boxes are used so the user can
 * make a specific selection. Checks are made throughout the process validating that the data meet the
 * specifications.
 */
public class UpdateCustomerController implements Initializable {
    public TextField UpdateCustomerName;
    public TextField UpdateCustomerAddress;
    public TextField UpdateCustomerPostalCode;
    public TextField UpdateCustomerPhoneNumber;
    public ComboBox<Country> UpdateCustomerCountryCombo;
    public ComboBox<Division> UpdateCustomerDivisionCombo;
    public TextField CustomerId;
    ObservableList<Country> countries = CountryOBJ.getAllCountries();
    private static Customer passedCustomer;
    Country country = null;
    Division division = null;

    /**
     * Passes the customer data from <code>CustomerList</code>
     * @param customer Passed customer
     */
    public static void passingTheCustomer(Customer customer){
        passedCustomer = customer;
    }

    /**
     * Initializes the tableview with the passed data from the <code>CustomerList</code> controller.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UpdateCustomerName.setText(String.valueOf(passedCustomer.getName()));
        UpdateCustomerAddress.setText(String.valueOf(passedCustomer.getCustomerAddress()));
        UpdateCustomerPostalCode.setText(String.valueOf(passedCustomer.getCustomerPostalCode()));
        UpdateCustomerPhoneNumber.setText(String.valueOf(passedCustomer.getCustomerPhone()));
        Country passedCountry = CountryOBJ.getCountry(passedCustomer.getCountryId());
        Division passedDivision = DivisionOBJ.getDivision(passedCustomer.getDivisionId());
        UpdateCustomerCountryCombo.setValue(passedCountry);
        UpdateCustomerDivisionCombo.setValue(passedDivision);
        CustomerId.setText(String.valueOf(passedCustomer.getId()));
    }

    /**
     * Clears the selection in the <code>updateDivisionCombo</code> combo box and sets the value to null after the
     * <code>updateCountrySelection</code> is selected.
     * Sets the <code>updateCountryCombo</code> combo box with the country list.
     * @param actionEvent Selecting the <code>updateCountryCombo</code> combo box.
     */
    public void updateCountrySelection(MouseEvent actionEvent) {
        UpdateCustomerDivisionCombo.getSelectionModel().clearSelection();
        UpdateCustomerDivisionCombo.setValue(null);
        UpdateCustomerCountryCombo.setItems(countries);
    }

    /**
     * Filters the list of divisions in the <code>newDivisionCombo</code> combo box based on the country selection in
     * the <code>updateCountryCombo</code> combo box.
     * @param actionEvent Selecting the <code>updateCountryCombo</code> combo box.
     */
    public void onSelectCustomer(ActionEvent actionEvent) {
        Country selectedCountry = UpdateCustomerCountryCombo.getSelectionModel().getSelectedItem();
        UpdateCustomerDivisionCombo.setItems(DivisionOBJ.filterDivisionCombo(selectedCountry.getId()));
        UpdateCustomerDivisionCombo.setVisibleRowCount(5);
    }

    /**
     * Verifies that all fields are completed. If data is missing, an alert shows requesting user to complete all of the
     * fields.
     * Pulls data from the fields and saves to the customer table in the database.
     * Alerts the user that the customer information was saved.
     * Opens the <code>CustomerList</code> fxml.
     * @param actionEvent Pressing the #onActSaveCustList button
     */
    public void onActSaveCustList(ActionEvent actionEvent) {

        try {
            String name = String.valueOf(UpdateCustomerName.getText());
            String address = String.valueOf(UpdateCustomerAddress.getText());
            String postalCode = String.valueOf(UpdateCustomerPostalCode.getText());
            String phone = String.valueOf(UpdateCustomerPhoneNumber.getText());
            country = UpdateCustomerCountryCombo.getValue();
            division = UpdateCustomerDivisionCombo.getValue();
            int custId = Integer.parseInt(CustomerId.getText());

            if (name.isEmpty() | address.isEmpty() | postalCode.isEmpty() | phone.isEmpty() | country == null | division == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please complete every field!", ButtonType.OK);
                alert.setTitle("");
                alert.showAndWait();
            } else {
                Customer c = new Customer(custId, name, address, postalCode, phone, country.getId(), division.getId());
                CustomerOBJ.updateCustomer(c);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Customer information saved!", ButtonType.OK);
                alert.showAndWait();
                Scenemovement.goToPage(actionEvent, "Customers", 1000, 600, "Customers");
            }
        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Alerts the user that any data in the form will be lost.
     * Opens the <code>CustomerList</code> fxml if the user selects "Yes".
     * @param actionEvent Pressing the #onActNoSaveCustList button
     */
    public void onActNoSaveCustList (ActionEvent actionEvent) throws IOException {
        //DONE: Add alert stating customer data not saved
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit? Data will be lost.", ButtonType.YES,ButtonType.NO);
        alert.setTitle("");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.YES) {
            Scenemovement.goToPage(actionEvent, "Customers", 1000, 600, "Customers");

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
     * Calls the <code>SceneMovements.goToPage</code> method to change to the <code>AppointmentList</code> fxml.
     * @param actionEvent The #onActionToApptList button
     */
    public void onActionToAppt(ActionEvent actionEvent) throws IOException {
        Scenemovement.goToPage(actionEvent, "Appointments", 1200, 600, "Appointment ");
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