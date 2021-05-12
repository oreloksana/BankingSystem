package com.company;

import java.util.Scanner;

public class Bank {
    private int max;
    private int number;
    private Account[] accounts;
    private int activeAccount;


    Scanner scan = new Scanner(System.in);

    //Bank class Constructor
    public Bank(int MAX) {
        number = 0;
        max = MAX;

        accounts = new Account[MAX];
    }

    //Adding new account to Array accounts
    public void newAccount() {
        if (number < max) {
            accounts[number] = new Account();
            number += 1;
        } else {
            System.out.println("\u001B[31m Error");
        }

    }

    //Getter for Array accounts
    public Account[] getAccounts(){return accounts;}
    //Setter for active account number
    public void setActiveAccount(int activeAccount){
        this.activeAccount = activeAccount;
    }

    //Dialog function for transferring money, checks from which to which account money would be sent
    public void transferMoneyDialog() {
        System.out.println("\u001B[37mEnter Account number where to send money: ");
        int target = scan.nextInt();
        scan.nextLine();
        System.out.println("Enter amount you want to send: ");
        double money = scan.nextDouble();
        if (target != accounts[activeAccount].getAccountNum()) {
            transferMoney(accounts[activeAccount].getAccountNum(), target, money);
        } else {
            System.out.println("\u001B[31mYou cannot send money to yourself");
        }

    }

    //Transferring money function, looking for source account and target account, makes transaction and calling calculating functions from Account class
    public void transferMoney(int source, int target, double money) {
        boolean debitDone = false;
        boolean creditDone = false;
        boolean creditFound = false;
        boolean debitFound = false;
        int debitNum = 0;
        int creditNum = 0;
        for (int i = 0; i < number; i++) {
            if (accounts[i].getAccountNum() == source) {
                debitFound = true;
                debitNum = i;
            }
        }
        for (int i = 0; i < number; i++) {
            if (accounts[i].getAccountNum() == target) {
                creditFound = true;
                creditNum = i;
            }
        }
        if (debitFound && creditFound) {
            debitDone = accounts[debitNum].debitAmount(money);
            creditDone = accounts[creditNum].creditAmount(money);
        }
        if (creditDone && debitDone) {
            System.out.println("\u001B[32mThis amount:" + money + " Euro was transferred from " + source + " to " + target);
        } else {
            System.out.println("\u001B[31mTransaction failed");
        }
    }

    //Login function, checking Account number and PIN, by giving wrong PIN would lock the Account
    public void doLogin() {
        int attempts = 1;
        boolean stop = true;
        while (stop == true) {
            System.out.println("\u001B[37mEnter your account number: ");
            int accNumInput = scan.nextInt();
            scan.nextLine();
            System.out.println("Enter your pin: ");
            int pinInput = scan.nextInt();
            for (int i = 0; i < number; i++) {
                if (accounts[i].getAccountNum() == accNumInput) {
                    if (!accounts[i].getBlocked()) {
                        if (accounts[i].getPin() == pinInput) {
                            System.out.println("\u001B[32mYou are logged in");
                            activeAccount = i;
                            attempts = 0;
                            stop = false;
                        } else if (attempts >= 3) {
                            System.out.println("\u001B[31mYour Account is locked, please call on the number +4900084677365 to unlock it");
                            accounts[activeAccount].setBlocked(true);
                            BankMenu.logOut();
                            stop = false;
                        } else {
                            attempts++;
                            int left = 3 - attempts;
                            System.out.printf("\u001B[31mFailed, you entered wrong PIN, You have : %.2f tries more %n ", (left + 1));
                        }
                    } else {
                        System.out.println("\u001B[31mYour Account is locked, please call on the number +4900084677365 to unlock it");
                        BankMenu.logOut();
                    }
                }

            }
        }


    }

    //Function for putting money on the account balance, calculating balance with new amount of money
    public void putMoney() {
        System.out.println("\u001B[37mHow much money would You like to put? Enter amount: ");
        double inputMoney = scan.nextDouble();
        if (inputMoney > 0) {
            accounts[activeAccount].setAccountBalance(accounts[activeAccount].getAccountBalance() + inputMoney);
        } else {
            System.out.println("\u001B[31mSomething went wrong...");
        }
        System.out.printf("\u001B[32mYour Balance now is: %.2f Euro%n ", accounts[activeAccount].getAccountBalance());
    }

    //Function to change a PIN in the active account
    public void changePin() {
        System.out.println("\u001B[37mWhich PIN would you like to have? Enter 4 numbers: ");
        int newPin = scan.nextInt();
        if (String.valueOf(newPin).length() == 4) {
            accounts[activeAccount].setPin(newPin);
            System.out.println("\u001B[32mYour PIN changed. Now your PIN is: " + accounts[activeAccount].getPin());
        } else {
            System.out.println("\u001B[31mTransaction failed. Your PIN is: " + accounts[activeAccount].getPin());
        }

    }

    //Check balance function, calling getter method from Account class
    public void checkBalance() {
        System.out.printf("\u001B[32mYour Balance is: %.2f Euro%n", accounts[activeAccount].getAccountBalance());
    }
}












