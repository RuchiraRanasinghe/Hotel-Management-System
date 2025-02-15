package repository.custom.impl;

import repository.custom.CustomerDao;

public class CustomerDaoImpl implements CustomerDao {
    private static CustomerDaoImpl instance;

    public CustomerDaoImpl() {}

    public static CustomerDaoImpl getInstance() {
        return instance==null?instance=new CustomerDaoImpl():instance;
    } 
}
