package com.company;

import java.util.Scanner;

public class Bank {
    private  int max;
    private int number;
    private Account[] accounts;
    private int activeAccount;

    Scanner scan = new Scanner(System.in);


    public Bank(int MAX){
        number = 0;
        max = MAX;

        accounts = new Account[MAX];
    }


    public void newAccount(){
        if(number < max){
            accounts[number] = new Account();
            number += 1;
        }else{
            System.out.println("Error");
        }

    }

    public void transferMoneyDialog(){
        System.out.println("Enter Acccount number where to send money: ");
        int target = scan.nextInt();
        scan.nextLine();
        System.out.println("Enter amount you want to send: ");
        double money = scan.nextDouble();
        if(target!=accounts[activeAccount].getAccountNum()){
            transferMoney(accounts[activeAccount].getAccountNum(), target, money );
        }else{
            System.out.println("You cannot send money to yourself");
        }

    }

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
            System.out.println("This amount:" + money + " was transferred from " + source +" to " + target);
        }else{
            System.out.println("Transaction failed");
        }
    }

    public void doLogin(){
        int attempts = 1;
        boolean stop =true;
        while(stop==true){
            System.out.println("Enter your account number: ");
            int accNumInput = scan.nextInt();
            scan.nextLine();
            System.out.println("Enter your pin: ");
            int pinInput = scan.nextInt();
            for(int i = 0; i < number; i++) {
                if (accounts[i].getAccountNum() == accNumInput) {
                    if(!accounts[i].getBlocked()){
                        if (accounts[i].getPin() == pinInput) {
                            System.out.println("You are logged in");
                            activeAccount=i;
                            attempts = 0;
                            stop=false;
                        }else if (attempts >= 3) {
                            System.out.println("Your Account is locked, please call on the number +4900084677365 to unlock it");
                            accounts[activeAccount].setBlocked(true);
                            logOut();
                            stop=false;
                        }else {
                            attempts++;
                            int left = 3 - attempts;
                            System.out.println("Failed, you entered wrong PIN, You have : " + (left + 1) + " tries more");
                        }
                    }else{
                        System.out.println("Your Account is locked, please call on the number +4900084677365 to unlock it");
                        logOut();
                    }
                }

            }
        }


    }


    public void putMoney(){
        System.out.println("How much money would You like to put? Enter amount: ");
        double inputMoney = scan.nextDouble();
        if(inputMoney>0){
            accounts[activeAccount].setAccountBalance(accounts[activeAccount].getAccountBalance() + inputMoney);
        }else{
            System.out.println("Something went wrong...");
        }
        System.out.println("Your Balance now is: " + accounts[activeAccount].getAccountBalance());
    }


    public void changePin(){
        System.out.println("Which PIN would you like to have? Enter 4 numbers: ");
        int newPin = scan.nextInt();
        if(String.valueOf(newPin).length()==4){
            accounts[activeAccount].setPin(newPin);
            System.out.println("Your PIN changed. Now your PIN is: " + accounts[activeAccount].getPin());
        }else{
            System.out.println("Transaction failed. Your PIN is: " + accounts[activeAccount].getPin());
        }

    }

    public void logOut(){
        activeAccount = 0;
        System.out.println("You are logged out");
        makeChoice();
    }

    public void checkBalance(){
        System.out.println("Your Balance is: " + accounts[activeAccount].getAccountBalance());
    }

    public static int menu(Scanner scan){
        System.out.println("Bank account menu: ");
        System.out.println("1. Create new account");
        System.out.println("2. Login");
        System.out.println("3. Quit");

        int choice;
        do {
            System.out.println("Enter choice: ");
            choice = scan.nextInt();
        }while (choice < 1 || choice > 3);

        return choice;
    }

    public static int accountMenu(Scanner scan){
        System.out.println("You can do: ");
        System.out.println("1. Check Balance");
        System.out.println("2. Transfer money");
        System.out.println("3. Change Pin");
        System.out.println("4. Put money");
        System.out.println("5. Log out");
        int choice;

        do {
            System.out.println("Enter choice: ");
            choice = scan.nextInt();
        }while (choice < 1 || choice > 5);
        return choice;
    }


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
                        System.out.println("\u001B[35m First You need to create an Account!");break;
                }
                case 3:System.exit(0);scan.close();
            }
        }while(choice!=3);

    }

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
                        System.out.println("You cannot transfer money to nowhere!  There is only 1 Account!");break;}
                case 3:changePin();break;
                case 4:putMoney();break;
                case 5:logOut();break;
            }
        }while(choice!=5);
    }
}










