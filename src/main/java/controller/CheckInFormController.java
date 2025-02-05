package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.Event;
import service.ServiceFactory;
import service.custom.AvailableRoomsService;
import service.custom.CheckInService;
import service.custom.CustomerService;
import service.custom.ReservationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import dto.Customer;
import dto.Reservation;
import util.ServiceType;

import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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

    CheckInService checkInService = ServiceFactory.getInstance().getServiceType(ServiceType.CHECKIN);
    CustomerService customerService = ServiceFactory.getInstance().getServiceType(ServiceType.CUSTOMER);
    ReservationService reservationService = ServiceFactory.getInstance().getServiceType(ServiceType.RESERVATION);
    AvailableRoomsService availableRoomsService = ServiceFactory.getInstance().getServiceType(ServiceType.AVAILABLEROOMS);

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
            if (!customerService.isCustomerAlreadyExists(txtNIC.getText())){
                customerService.addNewCustomer(new Customer(
                        0,
                        customerNIC,
                        customerName,
                        cusPhoneNumber,
                        0
                ));
                new Alert(Alert.AlertType.INFORMATION,"New Customer added successfully").show();
            }
            if (reservationService.createNewReservation(new Reservation(
                    0,
                    customerNIC,
                    selectedRoom,
                    String.valueOf(checkinDatePicker.getValue()),
                    String.valueOf(checkOutDatePicker.getValue()),
                    Double.parseDouble(lblTotalAmount.getText()),
                    "Confirmed"
            ))){
                new Alert(Alert.AlertType.INFORMATION,"Reservation added successfully").show();
            }
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
        for (String roomNumber : availableRoomsService.getAvailableRoomNumbers()){
            comboAvailableRooms.getItems().add(roomNumber);
        }
    }

    private void setNewReservationId() {
        String lastReservationId = checkInService.getNewReservationId();
        if (lastReservationId==null){
            lblReservationId.setText("1");
        }else {
            int newReservationId = Integer.parseInt(lastReservationId) + 1;
            lblReservationId.setText(String.valueOf(newReservationId));
        }
    }

    public void checkOutDatePickerOnAction(Event event) {
        LocalDate checkInDate = checkinDatePicker.getValue();
        LocalDate checkOutDate = checkOutDatePicker.getValue();
        double NIGHTLY_RATE = 1000.0;

        if (checkInDate != null && checkOutDate != null) {
            if (!checkOutDate.isAfter(checkInDate)){
                new Alert(Alert.AlertType.ERROR,"Invalid check-out date");
            }else {
                long numberOfNights = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
                double totalAmount = numberOfNights * NIGHTLY_RATE;
                lblTotalDays.setText(String.valueOf(numberOfNights));
                lblTotalAmount.setText(String.format("%.2f", totalAmount));
            }
        } else {
            lblTotalAmount.setText("0.00");
        }
    }
}
