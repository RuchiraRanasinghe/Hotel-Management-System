package service.custom;

import dto.Customer;
import service.SuperService;

import java.util.List;

public interface CustomerService extends SuperService {
    boolean isCustomerAlreadyExists(String NIC);
    void addNewCustomer(Customer customer);

    List<Customer> getAll();
}
