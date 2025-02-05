package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import dto.User;
import service.custom.impl.SignInServiceImpl;

import java.io.IOException;

public class SignInFormController {

    @FXML
    private JFXButton btnLogin;

    @FXML
    private JFXPasswordField txtPassword;

    @FXML
    private JFXTextField txtUsername;

    @FXML
    void btnLoginOnAction(ActionEvent event) throws IOException {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        User user = new User(0, username, password, "Admin");
        
        if (username.isEmpty() || password.isEmpty()){
            new Alert(Alert.AlertType.WARNING,"Please, Enter username & password !").show();
        }else {
            loginUser(user);
        }
    }

    private void loginUser(User newUser) throws IOException {
        if (SignInServiceImpl.getInstance().authenticateUser(newUser)){
            Stage stage = new Stage();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"))));
            stage.show();
            Stage currentStage = (Stage) txtUsername.getScene().getWindow();
            if (currentStage != null){
                currentStage.close();
            }
        }else {
            new Alert(Alert.AlertType.ERROR,"User not found..!\nSign Up please.").show();
        }
    }

}

