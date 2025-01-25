package controller.dashboard;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Room;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class DashboardFormController implements Initializable {

    @FXML
    private JFXComboBox<?> availableRooms_comboRoomType;

    @FXML
    private JFXComboBox<?> availableRooms_comboStatus;

    @FXML
    private JFXTextField availableRooms_txtPrice;

    @FXML
    private JFXTextField availableRooms_txtRoomNumber;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private Button btnAvailableRooms;

    @FXML
    private JFXButton btnCheckIn;

    @FXML
    private JFXButton btnClear;

    @FXML
    private Button btnCustomers;

    @FXML
    private Button btnDashboard;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnReservations;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<?, ?> colCheckedIn;

    @FXML
    private TableColumn<?, ?> colCheckedOut;

    @FXML
    private TableColumn<?, ?> colCusId;

    @FXML
    private TableColumn<?, ?> colCusNIC;

    @FXML
    private TableColumn<?, ?> colCusName;

    @FXML
    private TableColumn<?, ?> colCusPhone;

    @FXML
    private TableColumn<?, ?> colCustomerIdtblR;

    @FXML
    private TableColumn<?, ?> colLoyalty;

    @FXML
    private TableColumn<?, ?> colReservationId;

    @FXML
    private TableColumn<?, ?> colReservationStatus;

    @FXML
    private TableColumn<?, ?> colRoomNumber;

    @FXML
    private TableColumn<?, ?> colRoomNumbertblR;

    @FXML
    private TableColumn<?, ?> colRoomPrice;

    @FXML
    private TableColumn<?, ?> colRoomStatus;

    @FXML
    private TableColumn<?, ?> colRoomType;

    @FXML
    private TableColumn<?, ?> colTotalAmount;

    @FXML
    private AnchorPane customerPane;

    @FXML
    private AreaChart<?, ?> dashboardAreaChart;

    @FXML
    private Label dashboardBookToday;

    @FXML
    private Label dashboardIncomeToday;

    @FXML
    private Label dashboardIncomeTotal;

    @FXML
    private AnchorPane dashboardPane;

    @FXML
    private Label lblUser;

    @FXML
    private AnchorPane navPane;

    @FXML
    private AnchorPane reservationPane;

    @FXML
    private AnchorPane roomsPane;

    @FXML
    private FontAwesomeIconView searchICusIcon;

    @FXML
    private FontAwesomeIconView searchReservationIcon;

    @FXML
    private FontAwesomeIconView searchRoomIcon;

    @FXML
    private TableView<?> tblCustomers;

    @FXML
    private TableView<?> tblReservations;

    @FXML
    private TableView<Room> tblRooms;

    @FXML
    private JFXTextField txtSearchCustomer;

    @FXML
    private JFXTextField txtSearchReservation;

    @FXML
    private JFXTextField txtSearchRoom;

    private String[] roomType = {"Single", "Double", "Suite"};
    private String[] roomStatus = {"Available", "Occupied"};


    @FXML
    void btnAddOnAction(ActionEvent event) {
        String roomNumber = availableRooms_txtRoomNumber.getText();
        String roomType = (String) availableRooms_comboRoomType.getSelectionModel().getSelectedItem();
        String status = (String) availableRooms_comboStatus.getSelectionModel().getSelectedItem();
        String price = availableRooms_txtPrice.getText();

        if (roomNumber.isEmpty() || roomType==null || status==null || price.isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Please enter valid details").show();
        }else if (AvailableRoomsController.getInstance().isRoomNumberAlreadyExists(roomNumber)){
            new Alert(Alert.AlertType.WARNING,"Room # "+roomNumber+"was already exists").show();
        }else {
            if (AvailableRoomsController.getInstance().addNewRoom(
                    new Room(roomNumber,roomType,status,Double.parseDouble(price))
            )){
                new Alert(Alert.AlertType.INFORMATION,"New room added successfully !").show();
                loadRoomsTable();
            }else {
                new Alert(Alert.AlertType.ERROR,"Room not Added !").show();
            }
        }
    }

    @FXML
    void btnAvailableRoomsOnAction(ActionEvent event) {

    }

    @FXML
    void btnCheckInOnAction(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/check_in.fxml"))));
        stage.show();
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        availableRooms_txtRoomNumber.clear();
        availableRooms_comboRoomType.getSelectionModel().clearSelection();
        availableRooms_comboStatus.getSelectionModel().clearSelection();
        availableRooms_txtPrice.clear();
    }

    @FXML
    void btnCustomersOnAction(ActionEvent event) {

    }

    @FXML
    void btnDashboardOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        Optional<ButtonType> option = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete this room ?").showAndWait();
        if (option.get().equals(ButtonType.OK) && AvailableRoomsController.getInstance().deleteAvailableRoom(availableRooms_txtRoomNumber.getText())){
            new Alert(Alert.AlertType.INFORMATION,"Room data Deleted successfully").show();
            loadRoomsTable();
        }
    }

    @FXML
    void btnLogoutOnAction(ActionEvent event) throws IOException {
        Optional<ButtonType> option = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure ?").showAndWait();
        if (option.get().equals(ButtonType.OK)){
            logOut();
        }
    }

    private void logOut() throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/sign_in_form.fxml"))));
        stage.show();
        Stage currentStage = (Stage) btnDashboard.getScene().getWindow();
        if (currentStage != null) {
            currentStage.close();
        }
    }

    @FXML
    void btnReservationsOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        if (AvailableRoomsController.getInstance().updateAvailableRoom(
                new Room(
                        availableRooms_txtRoomNumber.getText(),
                        (String) availableRooms_comboRoomType.getSelectionModel().getSelectedItem(),
                        (String) availableRooms_comboStatus.getSelectionModel().getSelectedItem(),
                        Double.parseDouble(availableRooms_txtPrice.getText())
                )
        )){
            new Alert(Alert.AlertType.INFORMATION,"Room data updated successfully !").show();
            loadRoomsTable();
        }else {
            new Alert(Alert.AlertType.INFORMATION,"Room data not updated !").show();
        }
    }

    @FXML
    void searchCusIconOnAction(MouseEvent event) {

    }

    @FXML
    void searchRoomIconOnAction(MouseEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadComboRoomType();
        loadComboRoomStatus();
        loadRoomsTable();

        tblRooms.getSelectionModel()
                .selectedItemProperty()
                .addListener((observableValue, oldValue, newValue) -> {
                    if (newValue != null){
                        setRoomValueToText(newValue);
                    }
                });
    }

    private void setRoomValueToText(Room newValue) {
        availableRooms_txtRoomNumber.setText(newValue.getRoomNumber());
        availableRooms_txtPrice.setText(String.valueOf(newValue.getPrice()));
    }

    private void loadRoomsTable() {
        ArrayList<Room> availableRooms = AvailableRoomsController.getInstance().getAvailableRooms();
        ObservableList<Room> roomsObservableList = FXCollections.observableArrayList();
        availableRooms.forEach(room -> {
            roomsObservableList.add(room);
        });
        tblRooms.setItems(roomsObservableList);

        colRoomNumber.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        colRoomType.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        colRoomStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colRoomPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    private void loadComboRoomStatus() {
        ArrayList<String> roomStatusList = new ArrayList<>();
        for (String status : roomStatus){
            roomStatusList.add(status);
        }
        ObservableList statusObservableList = FXCollections.observableArrayList(roomStatusList);
        availableRooms_comboStatus.setItems(statusObservableList);
    }

    private void loadComboRoomType() {
        ArrayList<String> roomTypeList = new ArrayList<>();
        for (String type : roomType){
            roomTypeList.add(type);
        }
        ObservableList roomTypeObservableList = FXCollections.observableArrayList(roomTypeList);
        availableRooms_comboRoomType.setItems(roomTypeObservableList);
    }

    public void searchReservationIconOnAction(MouseEvent mouseEvent) {

    }
}
