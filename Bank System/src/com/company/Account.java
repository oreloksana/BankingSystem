package com.company;

import java.util.Random;

public class Account {
    private int pin;
    private int accountNum;
    private double accountBalance;
    private boolean blocked;

    public Account (){
        createAccount();
    }

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


    public void createAccount(){
        System.out.println("We would generate your pin and account number");
        Random random = new Random();
        accountNum = random.nextInt(999999999-000000001) + 1;
        pin = random.nextInt(9999-0001) + 1;
        accountBalance = 0;
        blocked = false;
        System.out.println("Your Pin is: "+ pin + ". Balance: " + accountBalance + ". AccountNumber: "+ accountNum);
    }

    public boolean debitAmount(double num){
        if(accountBalance>num){
            this.accountBalance -= num;
            return true;
        }else{
            System.out.println("Your Balance is too low");
            return false;
        }
    }

    public boolean creditAmount(double num){
            this.accountBalance += num;
            return true;
    }


}

