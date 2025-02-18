package repository.custom.impl;

import dto.Customer;
import entity.CustomerEntity;
import repository.custom.CustomerDao;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao {
    private static CustomerDaoImpl instance;

    public CustomerDaoImpl() {}

    public static CustomerDaoImpl getInstance() {
        return instance==null?instance=new CustomerDaoImpl():instance;
    }

    @Override
    public boolean save(CustomerEntity entity) {
        String sql = "INSERT INTO customers (customer_NIC,name,contact_details) VALUES (?,?,?)";
        try {
            return CrudUtil.execute(sql,
                    entity.getCustomerNIC(),
                    entity.getName(),
                    entity.getPhoneNumber());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Integer integer, CustomerEntity entity) {
        return false;
    }

    @Override
    public boolean delete(Integer integer) {
        return false;
    }

    @Override
    public List<CustomerEntity> getAll() {
        List<CustomerEntity> customerList = new ArrayList<>();
        try {
            ResultSet rst = CrudUtil.execute("SELECT * FROM customers");
            while (rst.next()){
                customerList.add(new CustomerEntity(
                        rst.getInt(1),
                        rst.getString(2),
                        rst.getString(3),
                        rst.getString(4),
                        rst.getInt(5)
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customerList;
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
}
