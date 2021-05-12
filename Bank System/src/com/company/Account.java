package com.company;

import java.util.Random;

public class Account {
    private int pin;
    private int accountNum;
    private double accountBalance;
    private boolean blocked;

    //Creating new User Account Constructor with createAccount() function
    public Account (){
        createAccount();
    }

//Getter and Setter methods
    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public boolean getBlocked(){ return blocked; }

    public void setBlocked(boolean blocked){
        this.blocked = blocked;
    }

    public int getAccountNum() {
        return accountNum;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

//Creating new User Account
    public void createAccount(){
        System.out.println("We would generate your pin and account number");
        Random random = new Random();
        accountNum = random.nextInt(9999999-1000000) + 1;
        pin = random.nextInt(9999-1000) + 1;
        accountBalance = 0;
        blocked = false;
        System.out.println("Your Pin is: "+ pin + ". Balance: " + accountBalance + ". AccountNumber: "+ accountNum);
    }

//Calculating balance after transferring money from the source account
    public boolean debitAmount(double num){
        if(accountBalance>num){
            this.accountBalance -= num;
            return true;
        }else{
            System.out.println("Your Balance is too low");
            return false;
        }
    }


//Calculating balance after transferring money on the target account
    public boolean creditAmount(double num){
            this.accountBalance += num;
            return true;
    }


}

