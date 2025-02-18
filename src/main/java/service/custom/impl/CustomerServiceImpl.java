package service.custom.impl;

import dto.Customer;
import entity.CustomerEntity;
import org.modelmapper.ModelMapper;
import repository.DaoFactory;
import repository.custom.CustomerDao;
import service.custom.CustomerService;
import util.CrudUtil;
import util.DaoType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerServiceImpl implements CustomerService {
    private static CustomerServiceImpl instance;
    private CustomerServiceImpl(){}

    public static CustomerServiceImpl getInstance(){
        return instance == null ? instance=new CustomerServiceImpl() : instance;
    }

    CustomerDao customerDao = DaoFactory.getInstance().getDao(DaoType.CUSTOMER);

    @Override
    public boolean isCustomerAlreadyExists(String NIC) {
        return customerDao.isCustomerAlreadyExists(NIC);
    }

    @Override
    public void addNewCustomer(Customer customer) {
        customerDao.save(new ModelMapper().map(customer, CustomerEntity.class));
    }

    @Override
    public List<Customer> getAll() {
        List<Customer> customerList = new ArrayList<>();
        for (CustomerEntity customerEntity : customerDao.getAll()) {
            customerList.add(new ModelMapper().map(customerEntity, Customer.class));
        }
        return customerList;
    }
}
