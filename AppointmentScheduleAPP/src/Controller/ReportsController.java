package Controller;

import javafx.event.ActionEvent;
import Utilities.Scenemovement;

import java.io.IOException;

/**
 * This class shows the list of reports available for the user to select from.
 */
public class ReportsController {

    /**
     * Opens the <code>ReportApptsByTypeMonth</code> fxml.
     * @param actionEvent Pressing the # onActOpenApptByTypeMonth button
     */
    public void onActOpenAppointmentsByTypeMonth(ActionEvent actionEvent) throws IOException {
        Scenemovement.goToPage(actionEvent, "ReportApptsByType", 600, 400, "Total Appointments By Type and Month");
    }

    /**
     * Opens the <code>ReportApptListByContact</code> fxml.
     * @param actionEvent Pressing the #openApptByContact button.
     */
    public void openAppointmentsByContact(ActionEvent actionEvent) throws IOException {
        Scenemovement.goToPage(actionEvent, "ReportAppointmentByContact", 1200, 600, "Appointment List by Contact");
    }

    /**
     * Opens the <code>ReportTotalCustomer</code> fxml.
     * @param actionEvent Pressing the #openTotalCustomer button.
     */
    public void openTotalCustomer(ActionEvent actionEvent) throws IOException {
        Scenemovement.goToPage(actionEvent, "ReportsTotalCustomers", 600, 400, "Total Customer");
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
}