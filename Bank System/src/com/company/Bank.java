package com.company;

import java.util.Scanner;

public class Bank {
    private  int max;
    private int number;
    private Account[] accounts;
    private int activeAccount;


    Scanner scan = new Scanner(System.in);

//Bank class Constructor
    public Bank(int MAX){
        number = 0;
        max = MAX;

        accounts = new Account[MAX];
    }

//Adding new account to Array accounts
    public void newAccount(){
        if(number < max){
            accounts[number] = new Account();
            number += 1;
        }else{
            System.out.println("\u001B[31m Error");
        }

    }

//Dialog function for transferring money, checks from which to which account money would be sent
    public void transferMoneyDialog(){
        System.out.println("\u001B[37mEnter Account number where to send money: ");
        int target = scan.nextInt();
        scan.nextLine();
        System.out.println("Enter amount you want to send: ");
        double money = scan.nextDouble();
        if(target!=accounts[activeAccount].getAccountNum()){
            transferMoney(accounts[activeAccount].getAccountNum(), target, money );
        }else{
            System.out.println("\u001B[31mYou cannot send money to yourself");
        }

    }

//Transferring money function, looking for source account and target account, makes transaction and calling calculating functions from Account class
    public void transferMoney(int source, int target, double money){
        boolean debitDone=false;
        boolean creditDone=false;
        boolean creditFound=false;
        boolean debitFound=false;
        int debitNum = 0;
        int creditNum = 0;
        for(int i = 0; i < number; i++){
            if(accounts[i].getAccountNum()==source){
                debitFound =true;
                debitNum = i;
            }
        }
        for(int i = 0; i < number; i++){
            if(accounts[i].getAccountNum()==target){
                creditFound = true;
                creditNum = i;
            }
        }
        if(debitFound && creditFound){
            debitDone = accounts[debitNum].debitAmount(money);
            creditDone = accounts[creditNum].creditAmount(money);
        }
        if(creditDone && debitDone){
            System.out.println("\u001B[32mThis amount:" + money + " Euro was transferred from " + source +" to " + target);
        }else{
            System.out.println("\u001B[31mTransaction failed");
        }
    }

//Login function, checking Account number and PIN, by giving wrong PIN would lock the Account
    public void doLogin(){
        int attempts = 1;
        boolean stop =true;
        while(stop==true){
            System.out.println("\u001B[37mEnter your account number: ");
            int accNumInput = scan.nextInt();
            scan.nextLine();
            System.out.println("Enter your pin: ");
            int pinInput = scan.nextInt();
            for(int i = 0; i < number; i++) {
                if (accounts[i].getAccountNum() == accNumInput) {
                    if(!accounts[i].getBlocked()){
                        if (accounts[i].getPin() == pinInput) {
                            System.out.println("\u001B[32mYou are logged in");
                            activeAccount=i;
                            attempts = 0;
                            stop=false;
                        }else if (attempts >= 3) {
                            System.out.println("\u001B[31mYour Account is locked, please call on the number +4900084677365 to unlock it");
                            accounts[activeAccount].setBlocked(true);
                            logOut();
                            stop=false;
                        }else {
                            attempts++;
                            int left = 3 - attempts;
                            System.out.printf("\u001B[31mFailed, you entered wrong PIN, You have : %.2f tries more %n ", (left + 1));
                        }
                    }else{
                        System.out.println("\u001B[31mYour Account is locked, please call on the number +4900084677365 to unlock it");
                        logOut();
                    }
                }

            }
        }


    }

//Function for putting money on the account balance, calculating balance with new amount of money
    public void putMoney(){
        System.out.println("\u001B[37mHow much money would You like to put? Enter amount: ");
        double inputMoney = scan.nextDouble();
        if(inputMoney>0){
            accounts[activeAccount].setAccountBalance(accounts[activeAccount].getAccountBalance() + inputMoney);
        }else{
            System.out.println("\u001B[31mSomething went wrong...");
        }
        System.out.printf("\u001B[32mYour Balance now is: %.2f Euro%n " , accounts[activeAccount].getAccountBalance());
    }

//Function to change a PIN in the active account
    public void changePin(){
        System.out.println("\u001B[37mWhich PIN would you like to have? Enter 4 numbers: ");
        int newPin = scan.nextInt();
        if(String.valueOf(newPin).length()==4){
            accounts[activeAccount].setPin(newPin);
            System.out.println("\u001B[32mYour PIN changed. Now your PIN is: " + accounts[activeAccount].getPin());
        }else{
            System.out.println("\u001B[31mTransaction failed. Your PIN is: " + accounts[activeAccount].getPin());
        }

    }

// Function for logging out, send u back to previous menu
    public void logOut(){
        activeAccount = 0;
        System.out.println("\u001B[32mYou are logged out");
        makeChoice();
    }

//Check balance function, calling getter method from Account class
    public void checkBalance(){
        System.out.printf("\u001B[32mYour Balance is: %.2f Euro%n", accounts[activeAccount].getAccountBalance());
    }

//Function which showing first bank menu and taking back your choice as integer
    public int menu(Scanner scan){
        System.out.println("\u001B[37mBank account menu: ");
        System.out.println("1. Create new account");
        System.out.println("2. Login");
        System.out.println("3. Quit");
        int choice;

        do {
            System.out.println("Enter choice with number 1-3: ");
            choice = scan.nextInt();
        }while (choice < 1 || choice > 3);

        return choice;
    }

//Function which showing account bank menu, after you logged in and taking back your choice as integer
    public int accountMenu(Scanner scan){
        System.out.println("\u001B[37mYou can do: ");
        System.out.println("1. Check Balance");
        System.out.println("2. Transfer money");
        System.out.println("3. Change Pin");
        System.out.println("4. Put money");
        System.out.println("5. Log out");
        int choice;
        do {
            System.out.println("Enter choice with a number 1-5: ");
            choice = scan.nextInt();

        }while (choice < 1 || choice > 5);
        return choice;
    }

//Function which switching between methods in bank operations class, checking if your account is already exists
    public void makeChoice(){
        int choice;
        do{
            choice = menu(scan);
            System.out.println();
            switch (choice){
                case 1:newAccount();break;
                case 2:
                    if(accounts[0]!=null){
                        doLogin();makeSecondChoice();break;
                    }else{
                        System.out.println("\u001B[31m First You need to create an Account!");break;
                }
                case 3:System.exit(0);scan.close();
            }
        }while(choice!=3);

    }

//Function which switching between methods in bank operations class, checking if you are not trying to send money on your own account
    public  void makeSecondChoice(){
        int choice;
        do{
            choice = accountMenu(scan);
            System.out.println();
            switch (choice){
                case 1:checkBalance();break;
                case 2:
                    if(accounts[1]!=null){
                        transferMoneyDialog();break;
                    }else{
                        System.out.println("\u001B[31mYou cannot transfer money to nowhere!  There is only 1 Account!");break;}
                case 3:changePin();break;
                case 4:putMoney();break;
                case 5:logOut();break;
            }
        }while(choice!=5);
    }
}










