package repository.custom;

import entity.CustomerEntity;
import repository.CrudDao;

public interface CustomerDao extends CrudDao<CustomerEntity,Integer> {
    boolean isCustomerAlreadyExists(String NIC);
}
