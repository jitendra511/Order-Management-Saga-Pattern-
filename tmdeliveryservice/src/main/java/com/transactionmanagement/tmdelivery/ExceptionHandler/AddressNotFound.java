package com.transactionmanagement.tmdelivery.ExceptionHandler;

public class AddressNotFound extends Exception{
    public AddressNotFound(String str)
    {
        super(str);
    }
}
