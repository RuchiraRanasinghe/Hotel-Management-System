package service.custom.impl;

import dto.Customer;
import service.custom.CustomerService;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerServiceImpl implements CustomerService {
    private static CustomerServiceImpl instance;
    private CustomerServiceImpl(){}

    public static CustomerServiceImpl getInstance(){
        return instance == null ? instance=new CustomerServiceImpl() : instance;
    }

    @Override
    public boolean isCustomerAlreadyExists(String NIC) {
        String sql = "SELECT customer_NIC FROM customers WHERE customer_NIC='"+NIC+"'";
        try {
            ResultSet rst = CrudUtil.execute(sql);
            return rst.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
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
