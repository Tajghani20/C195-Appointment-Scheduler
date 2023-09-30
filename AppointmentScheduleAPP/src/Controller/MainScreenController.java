package Controller;

import javafx.event.ActionEvent;
import Utilities.Scenemovement;

import java.io.IOException;


/**
 * This class is the user's entry point after logging into the program. Three buttons display to navigate to either the
 * Appointment List, Customer List, or Reports List.
 */
public class MainScreenController {

    /**
     * Calls the <code>SceneMovements.goToPage</code> method to change to the <code>ReportsList</code> fxml.
     * @param actionEvent The #onActionToReport button
     */
    public void openReportPage(ActionEvent actionEvent) throws IOException {
        Scenemovement.goToPage(actionEvent, "Reports", 600, 400, "Reports List");
    }

    /**
     * Calls the <code>SceneMovements.goToPage</code> method to change to the <code>CustomerList</code> fxml.
     * @param actionEvent The #onActionToCustomer button
     */
    public void openCustomerList(ActionEvent actionEvent) throws IOException {
        Scenemovement.goToPage(actionEvent, "Customers", 1000, 600, "Customer List");
    }

    /**
     * Calls the <code>SceneMovements.goToPage</code> method to change to the <code>AppointmentList</code> fxml.
     * @param actionEvent The #onActionToApptList button
     */
    public void openAppointmentList(ActionEvent actionEvent) throws IOException {
        Scenemovement.goToPage(actionEvent, "Appointments", 1200, 600, "Appointment List");
    }


}