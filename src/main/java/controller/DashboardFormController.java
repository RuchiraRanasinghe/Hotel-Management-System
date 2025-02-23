package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import dto.Customer;
import dto.Reservation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import dto.Room;
import service.ServiceFactory;
import service.custom.AvailableRoomsService;
import service.custom.CheckInService;
import service.custom.CustomerService;
import service.custom.ReservationService;
import service.custom.impl.AvailableRoomsServiceImpl;
import util.ServiceType;

import java.io.IOException;
import java.net.URL;
import java.util.*;

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
    private JFXButton btnCancelReservation;

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
    private AnchorPane roomsCrudPane;

    @FXML
    private AnchorPane roomsPane;

    @FXML
    private FontAwesomeIconView searchICusIcon;

    @FXML
    private FontAwesomeIconView searchReservationIcon;

    @FXML
    private FontAwesomeIconView searchRoomIcon;

    @FXML
    private TableView<Customer> tblCustomers;

    @FXML
    private TableView<Reservation> tblReservations;

    @FXML
    private TableView<Room> tblRooms;

    @FXML
    private JFXTextField txtSearchCustomer;

    @FXML
    private JFXTextField txtSearchReservation;

    @FXML
    private JFXTextField txtSearchRoom;

    private final String[] roomTypes = {"Single", "Double", "Suite"};
    private final String[] roomStatus = {"Available", "Occupied"};

    CheckInService checkInService = ServiceFactory.getInstance().getServiceType(ServiceType.CHECKIN);
    CustomerService customerService = ServiceFactory.getInstance().getServiceType(ServiceType.CUSTOMER);
    ReservationService reservationService = ServiceFactory.getInstance().getServiceType(ServiceType.RESERVATION);
    AvailableRoomsService availableRoomsService = ServiceFactory.getInstance().getServiceType(ServiceType.AVAILABLEROOMS);

    @FXML
    void btnAddOnAction(ActionEvent event) {
        String roomNumber = availableRooms_txtRoomNumber.getText();
        String roomType = (String) availableRooms_comboRoomType.getSelectionModel().getSelectedItem();
        String status = (String) availableRooms_comboStatus.getSelectionModel().getSelectedItem();
        String price = availableRooms_txtPrice.getText();

        if (roomNumber.isEmpty() || roomType==null || status==null || price.isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Please enter valid details").show();
        }else if (AvailableRoomsServiceImpl.getInstance().isRoomNumberAlreadyExists(roomNumber)){
            new Alert(Alert.AlertType.WARNING,"Room # "+roomNumber+"was already exists").show();
        }else {
            if (AvailableRoomsServiceImpl.getInstance().addNewRoom(
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
        roomsPane.setVisible(true);
        roomsPane.toFront();
        roomsCrudPane.setVisible(true);
        dashboardPane.setVisible(false);
        customerPane.setVisible(false);
        reservationPane.setVisible(false);
    }

    @FXML
    void btnCheckInOnAction(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/check_in.fxml")))));
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
        customerPane.setVisible(true);
        customerPane.toFront();
        roomsPane.setVisible(false);
        roomsCrudPane.setVisible(false);
        dashboardPane.setVisible(false);
        reservationPane.setVisible(false);
    }

    @FXML
    void btnDashboardOnAction(ActionEvent event) {
        dashboardPane.setVisible(true);
        dashboardPane.toFront();
        roomsPane.setVisible(false);
        roomsCrudPane.setVisible(false);
        customerPane.setVisible(false);
        reservationPane.setVisible(false);
        setCountBookToday();
        setIncomeToday();
        loadDashboardChart();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        Optional<ButtonType> option = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete this room ?").showAndWait();
        if (option.get().equals(ButtonType.OK) && AvailableRoomsServiceImpl.getInstance().deleteAvailableRoom(availableRooms_txtRoomNumber.getText())){
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
        stage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/sign_in_form.fxml")))));
        stage.show();
        Stage currentStage = (Stage) btnDashboard.getScene().getWindow();
        if (currentStage != null) {
            currentStage.close();
        }
    }

    @FXML
    void btnReservationsOnAction(ActionEvent event) {
        reservationPane.setVisible(true);
        reservationPane.toFront();
        roomsPane.setVisible(false);
        roomsCrudPane.setVisible(false);
        dashboardPane.setVisible(false);
        customerPane.setVisible(false);
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        if (availableRoomsService.updateAvailableRoom(
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
//
    }

    @FXML
    void searchRoomIconOnAction(MouseEvent event) {
//
    }

    @FXML
    void searchReservationIconOnAction(MouseEvent mouseEvent) {
//
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadComboRoomType();
        loadComboRoomStatus();
        loadRoomsTable();
        loadCustomersTable();
        loadReservationsTable();

        searchCustomer();
        searchRoom();
        searchReservation();

        setCountBookToday();
        setIncomeToday();
        setTotalIncome();
        loadDashboardChart();

        tblRooms.getSelectionModel()
                .selectedItemProperty()
                .addListener((observableValue, oldValue, newValue) -> {
                    if (newValue != null){
                        setRoomValueToText(newValue);
                    }
                });
    }

    private void loadDashboardChart() {
        dashboardAreaChart.getData().clear();
        dashboardAreaChart.getData().add(reservationService.getChartData());
    }

    private void setTotalIncome() {
        Double totalIncome = reservationService.getTotalIncome();
        dashboardIncomeTotal.setText("LKR "+totalIncome);
    }

    private void setIncomeToday() {
        Double incomeToday = reservationService.getIncomeToday();
        dashboardIncomeToday.setText("LKR "+incomeToday);
    }

    private void setCountBookToday() {
        int countBookToday = reservationService.getCountBookToday();
        dashboardBookToday.setText(String.valueOf(countBookToday));
    }

    private void searchReservation() {
        FilteredList<Reservation> reservationFilteredList = new FilteredList<>(reservationObservableList, room -> true);
        txtSearchReservation.textProperty().addListener((observableValue, oldValue, newValue) -> {
            reservationFilteredList.setPredicate(predicateReservation -> {
                if (newValue==null || newValue.isEmpty()){
                    return true;
                }
                String searchKey = newValue.toLowerCase();
                if (predicateReservation.getReservationId().toString().contains(searchKey)){
                    return true;
                } else if (predicateReservation.getCustomerNIC().contains(searchKey)) {
                    return true;
                } else if (predicateReservation.getRoomNumber().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateReservation.getCheckInDate().contains(searchKey)) {
                    return true;
                } else if (predicateReservation.getCheckOutDate().contains(searchKey)){
                    return true;
                } else if (predicateReservation.getTotalAmount().toString().contains(searchKey)) {
                    return true;
                } else if (predicateReservation.getReservationStatus().contains(searchKey)) {
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<Reservation> reservationSortedList = new SortedList<>(reservationFilteredList);
        reservationSortedList.comparatorProperty();
        tblReservations.setItems(reservationSortedList);
    }

    private void searchRoom() {
        FilteredList<Room> roomFilteredList = new FilteredList<>(roomsObservableList, room -> true);
        txtSearchRoom.textProperty().addListener((observableValue, oldValue, newValue) -> {
            roomFilteredList.setPredicate(predicateRoom -> {
                if (newValue==null || newValue.isEmpty()){
                    return true;
                }
                String searchKey = newValue.toLowerCase();
                if (predicateRoom.getRoomNumber().toLowerCase().contains(searchKey)){
                    return true;
                } else if (predicateRoom.getRoomType().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateRoom.getPrice().toString().contains(searchKey)) {
                    return true;
                } else return predicateRoom.getStatus().toLowerCase().contains(searchKey);
            });
        });

        SortedList<Room> roomSortedList = new SortedList<>(roomFilteredList);
        roomSortedList.comparatorProperty();
        tblRooms.setItems(roomSortedList);
    }

    private void searchCustomer() {
        FilteredList<Customer> customerFilteredList = new FilteredList<>(customerObservableList, customer -> true);
        txtSearchCustomer.textProperty().addListener((observableValue, oldValue, newValue) -> {
            customerFilteredList.setPredicate(predicateCustomer -> {
                if (newValue==null || newValue.isEmpty()){
                    return true;
                }
                String searchKey = newValue.toLowerCase();
                if (predicateCustomer.getCustomerId().toString().contains(searchKey)){
                    return true;
                } else if (predicateCustomer.getCustomerNIC().contains(searchKey)) {
                    return true;
                } else if (predicateCustomer.getName().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateCustomer.getPhoneNumber().contains(searchKey)) {
                    return true;
                } else return predicateCustomer.getLoyaltyPoints().toString().contains(searchKey);
            });
        });

        SortedList<Customer> customerSortedList = new SortedList<>(customerFilteredList);
        customerSortedList.comparatorProperty();
        tblCustomers.setItems(customerSortedList);
    }


    private void setRoomValueToText(Room newValue) {
        availableRooms_txtRoomNumber.setText(newValue.getRoomNumber());
        availableRooms_txtPrice.setText(String.valueOf(newValue.getPrice()));
    }

    private ObservableList<Room> roomsObservableList;
    private void loadRoomsTable() {
        ArrayList<Room> availableRooms = availableRoomsService.getAvailableRooms();
        roomsObservableList = FXCollections.observableArrayList();
        roomsObservableList.addAll(availableRooms);
        tblRooms.setItems(roomsObservableList);

        colRoomNumber.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        colRoomType.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        colRoomStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colRoomPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    private ObservableList<Customer> customerObservableList;
    private void loadCustomersTable(){
        List<Customer> customerList = customerService.getAll();
        customerObservableList = FXCollections.observableArrayList();
        customerObservableList.addAll(customerList);
        tblCustomers.setItems(customerObservableList);

        colCusId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colCusNIC.setCellValueFactory(new PropertyValueFactory<>("customerNIC"));
        colCusName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCusPhone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        colLoyalty.setCellValueFactory(new PropertyValueFactory<>("loyaltyPoints"));
    }

    ObservableList<Reservation> reservationObservableList;
    private void loadReservationsTable() {
        List<Reservation> allReservations = reservationService.getAllReservations();
        reservationObservableList = FXCollections.observableArrayList();
        reservationObservableList.addAll(allReservations);
        tblReservations.setItems(reservationObservableList);

        colReservationId.setCellValueFactory(new PropertyValueFactory<>("reservationId"));
        colCustomerIdtblR.setCellValueFactory(new PropertyValueFactory<>("customerNIC"));
        colRoomNumbertblR.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        colCheckedIn.setCellValueFactory(new PropertyValueFactory<>("checkInDate"));
        colCheckedOut.setCellValueFactory(new PropertyValueFactory<>("checkOutDate"));
        colTotalAmount.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        colReservationStatus.setCellValueFactory(new PropertyValueFactory<>("reservationStatus"));
    }

    private void loadComboRoomStatus() {
        ArrayList<String> roomStatusList = new ArrayList<>(Arrays.asList(roomStatus));
        ObservableList statusObservableList = FXCollections.observableArrayList(roomStatusList);
        availableRooms_comboStatus.setItems(statusObservableList);
    }

    private void loadComboRoomType() {
        ArrayList<String> roomTypeList = new ArrayList<>(Arrays.asList(roomTypes));
        ObservableList roomTypeObservableList = FXCollections.observableArrayList(roomTypeList);
        availableRooms_comboRoomType.setItems(roomTypeObservableList);
    }

    public void btnCancelReservationOnAction(ActionEvent actionEvent) {
        Reservation reservation = tblReservations.getSelectionModel().getSelectedItem();
        if (reservationService.cancelReservation(reservation.getReservationId()) && availableRoomsService.setRoomStatusAvailable(reservation.getRoomNumber())){
                new Alert(Alert.AlertType.CONFIRMATION,"Reservation canceled successfully\nRoom number "+reservation.getRoomNumber()+" is available now.").show();
                loadRoomsTable();
                loadReservationsTable();
            }
    }
}
