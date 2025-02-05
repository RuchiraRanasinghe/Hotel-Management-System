package service.custom;

import dto.Customer;
import service.SuperService;

public interface CustomerService extends SuperService {
    boolean isCustomerAlreadyExists(String NIC);
    void addNewCustomer(Customer customer);
}
