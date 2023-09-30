package Controller;

import DAO.ReportsOBJ;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import Model.Report;
import Utilities.Scenemovement;
import Utilities.Time;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class creates a table to shows the shows the total count of appointments grouped by customer and type for
 * the month that is selected.
 */
public class ReportAppointmentByTypeController implements Initializable {

    public ComboBox <String> comboMonth;
    public TextField txtYear;
    public TableView <Report> apptByTypeTable;
    public TableColumn <Report, String> typeCol;
    public TableColumn <Report, Integer> countCol;
    ObservableList<String> monthList = Time.monthList();

    /**
     * Initializes the <code>comboMonth</code> combo box with a list of months.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        comboMonth.setItems(monthList);
        comboMonth.setVisibleRowCount(5);
        comboMonth.setPromptText("Select month");
    }

    /**
     * Calls the <code>totalApptsByTypeMonth</code> method from the <code>ReportsDAO</code> class using the user's
     * selections as the parameters.
     * Populates the table with the returned data.
     * @param actionEvent Pressing the #onActFindTotal button.
     */
    public void onActFindTotal(ActionEvent actionEvent) {

        ObservableList <Report> pulledReport = ReportsOBJ.totalApptsByTypeMonth(comboMonth.getValue(), txtYear.getText());
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        countCol.setCellValueFactory(new PropertyValueFactory<>("count"));
        apptByTypeTable.setItems(pulledReport);
    }

    /**
     * Clears all fields and the table. Sets the txtYear prompt test to "Enter year (yyyy)".
     * @param actionEvent Pressing the #onActClearFields button.
     */
    public void onActClearFields(ActionEvent actionEvent) {
        comboMonth.getSelectionModel().clearSelection();
        txtYear.clear();
        txtYear.setPromptText("Enter year (yyyy)");
        apptByTypeTable.getItems().clear();
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
    public void onActToReports(ActionEvent actionEvent) throws IOException {
        Scenemovement.goToPage(actionEvent, "Reports", 600, 400, "Reports");
    }
}