package repository.custom.impl;

public class SignInDaoImpl {
    private static SignInDaoImpl instance;

    public SignInDaoImpl() {}

    public static SignInDaoImpl getInstance(){return instance==null?instance=new SignInDaoImpl():instance;}
}
