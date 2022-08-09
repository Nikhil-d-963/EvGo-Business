package com.example.evgobusiness;

public class TransactionDetailsService {
    String customer_name,email,Date_Time,Slot_hour,Amount,TransactionID,phoneNumber,Customer_Query;


    public TransactionDetailsService(){

    }

    public TransactionDetailsService(String customer_name, String email, String date_Time, String slot_hour, String amount,String TransactionID,String phoneNumber,String Customer_Query) {
        this.customer_name = customer_name;
        this.email = email;
        Date_Time = date_Time;
        Slot_hour = slot_hour;
        Amount = amount;
        this.TransactionID = TransactionID;
        this.phoneNumber = phoneNumber;
        this.Customer_Query = Customer_Query;


    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate_Time() {
        return Date_Time;
    }

    public void setDate_Time(String date_Time) {
        Date_Time = date_Time;
    }

    public String getSlot_hour() {
        return Slot_hour;
    }

    public void setSlot_hour(String slot_hour) {
        Slot_hour = slot_hour;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getTransactionID() {
        return TransactionID;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getCustomer_Query() {
        return Customer_Query;
    }
}
