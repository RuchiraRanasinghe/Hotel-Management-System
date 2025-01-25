package controller.dashboard;

import model.Customer;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerController {
    private static CustomerController instance;
    private CustomerController(){}

    public static CustomerController getInstance(){
        return instance == null ? instance=new CustomerController() : instance;
    }

    public boolean isCustomerAlreadyExists(String NIC) {
        String sql = "SELECT customer_NIC FROM customers WHERE customer_NIC='"+NIC+"'";
        try {
            ResultSet rst = CrudUtil.execute(sql);
            return rst.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addNewCustomer(Customer customer) {
        String sql = "INSERT INTO customers (customer_NIC,name,contact_details) VALUES (?,?,?)";
        try {
            CrudUtil.execute(sql,
                    customer.getCustomerNIC(),
                    customer.getName(),
                    customer.getPhoneNumber());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
