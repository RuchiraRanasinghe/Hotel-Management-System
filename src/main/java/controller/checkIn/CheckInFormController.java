package controller.checkIn;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import controller.dashboard.AvailableRoomsController;
import controller.dashboard.CustomerController;
import controller.dashboard.ReservationController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import model.Customer;
import model.Reservation;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class CheckInFormController implements Initializable {

    @FXML
    private JFXButton btnCheckIn;

    @FXML
    private JFXButton btnReceipt;

    @FXML
    private JFXButton btnReset;

    @FXML
    private DatePicker checkOutDatePicker;

    @FXML
    private DatePicker checkinDatePicker;

    @FXML
    private Label lblReservationId;

    @FXML
    private JFXComboBox comboAvailableRooms;

    @FXML
    private JFXTextField txtNIC;

    @FXML
    private JFXTextField txtCusName;

    @FXML
    private JFXTextField txtPhoneNumber;

    @FXML
    private Label lblTotalAmount;

    @FXML
    private Label lblTotalDays;

    @FXML
    void btnCheckInOnAction(ActionEvent event) {
        String customerNIC = txtNIC.getText();
        String customerName = txtCusName.getText();
        String cusPhoneNumber = txtPhoneNumber.getText();
        String selectedRoom = String.valueOf(comboAvailableRooms.getSelectionModel().getSelectedItem());
        String checkInDate = String.valueOf(checkinDatePicker.getValue());
        String checkOutDate = String.valueOf(checkOutDatePicker.getValue());

        if (customerNIC.isEmpty() || customerName.isEmpty() || cusPhoneNumber.isEmpty() || selectedRoom.isEmpty() || checkInDate.isEmpty() || checkOutDate.isEmpty()){
            new Alert(Alert.AlertType.WARNING,"Please enter valid details !").show();
        }else {
            setTotalDays(checkInDate,checkOutDate);
            if (!CustomerController.getInstance().isCustomerAlreadyExists(txtNIC.getText())){
                CustomerController.getInstance().addNewCustomer(new Customer(
                        0,
                        customerNIC,
                        customerName,
                        cusPhoneNumber,
                        0
                ));
                new Alert(Alert.AlertType.INFORMATION,"New Customer added successfully").show();
            } else if (ReservationController.getInstance().createNewReservation(new Reservation(
                    0,
                    customerNIC,
                    selectedRoom,
                    checkInDate,
                    checkOutDate,
                    Double.parseDouble(lblTotalAmount.getText()),
                    "Confirmed"
            ))){
                new Alert(Alert.AlertType.INFORMATION,"Reservation added successfully").show();
            }
        }
    }

    private void setTotalDays(String checkInDate, String checkOutDate) {
        if (checkOutDate.compareTo(checkInDate) < 0){
            new Alert(Alert.AlertType.ERROR,"Invalid check-out date");
        }else {
            lblTotalDays.setText(String.valueOf(checkOutDate.compareTo(checkInDate)));
        }
    }

    @FXML
    void btnReceiptOnAction(ActionEvent event) {

    }

    @FXML
    void btnResetOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setNewReservationId();
        loadComboAvailableRooms();
    }

    private void loadComboAvailableRooms() {
        for (String roomNumber : AvailableRoomsController.getInstance().getAvailableRoomNumbers()){
            comboAvailableRooms.getItems().add(roomNumber);
        }
    }

    private void setNewReservationId() {
        String lastReservationId = CheckInController.getInstance().getNewReservationId();
        if (lastReservationId==null){
            lblReservationId.setText("1");
        }else {
            int newReservationId = Integer.parseInt(lastReservationId) + 1;
            lblReservationId.setText(String.valueOf(newReservationId));
        }
    }
}
