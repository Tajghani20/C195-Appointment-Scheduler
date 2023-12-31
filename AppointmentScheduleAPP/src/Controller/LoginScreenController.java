package Controller;

import DAO.UserOBJ;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import Model.User;
import Utilities.LoginScreenTypes;
import Utilities.Scenemovement;
import Utilities.Time;

import java.io.IOException;
import java.net.URL;
import java.time.*;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This class validates access to the software program. A user name and password is required and are then checked
 * against the User table in the database. If the user name/password combo equals a user combo in the database then
 *
 */
public class LoginScreenController implements Initializable {
    public TextField userNameTF;
    public Label locationLBL;
    public PasswordField passwordPF;
    public Label locationIDLBL;
    public Label welcomeLBL;
    public Label pleaseEnterLBL;
    public Button loginLBL;
    public Label usernameLBL;
    public Label passwordLBL;

    /**
     * Sets the location label to show the user location based on the Zone ID.
     * Checks the default locale country and uses the <code>Language_fr.properties</code> if the local is France.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ZoneId localZone = ZoneId.systemDefault();
        locationIDLBL.setText(localZone.getDisplayName(TextStyle.FULL, Locale.ROOT));

        if (Locale.getDefault().getLanguage().equals("fr")) {
            ResourceBundle rb = ResourceBundle.getBundle("Main/Language", Locale.getDefault());
            welcomeLBL.setText(rb.getString("welcome"));
            pleaseEnterLBL.setText(rb.getString("pleaseenter"));
            usernameLBL.setText(rb.getString("username"));
            passwordLBL.setText(rb.getString("password"));
            loginLBL.setText(rb.getString("login"));
            locationLBL.setText(rb.getString("location"));
        }
    }

    /**
     * Checks the default locale country and uses the <code>Language_fr.properties</code> if the local is France.
     * Verifies that the provided user name and password match a user in the database.
     * Alerts the user if there is no match for the user name and password.
     * Opens the <code>MainMenu</code> fxml is user name and password are correct.
     * @param actionEvent Pressing the #onActCheckLogin button
     */
    public void onActCheckLogin(ActionEvent actionEvent) throws IOException {
        String name = userNameTF.getText().trim();
        String password = passwordPF.getText().trim();
        User retrievedUser = UserOBJ.checkUsernamePassword(name, password);
        LocalDateTime ldt = LocalDateTime.now();
        LocalDate date = ldt.toLocalDate();
        LocalTime time = ldt.toLocalTime();

        if (retrievedUser == null && Locale.getDefault().getLanguage().equals("fr")) {
            LoginScreenTypes.failedLoginAttemptWrite(name, date, time);
            ResourceBundle rb = ResourceBundle.getBundle("Main/Language", Locale.getDefault());
            Alert alert = new Alert(Alert.AlertType.ERROR, rb.getString("Incorrect enter"), ButtonType.OK);
            alert.setTitle(rb.getString("Incorrect title"));
            alert.showAndWait();
            passwordPF.clear();
        } else if (retrievedUser == null && !Locale.getDefault().getLanguage().equals("fr")) {
            LoginScreenTypes.failedLoginAttemptWrite(name, date, time);
            Alert alert = new Alert(Alert.AlertType.ERROR, "Incorrect user name or password was entered.", ButtonType.OK);
            alert.setTitle("Incorrect Username/Password");
            alert.showAndWait();
            passwordPF.clear();
        } else {
            LoginScreenTypes.passedLoginAttemptWrite(name, date, time);
            Scenemovement.goToPage(actionEvent, "MainScreen", 600, 400, "Main Screen");
            if (retrievedUser != null) {
                Time.checkApptTimeAtLogin(retrievedUser.getId());
            }
        }
    }


}