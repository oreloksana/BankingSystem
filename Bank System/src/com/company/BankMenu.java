package com.company;

import java.util.Scanner;

public class BankMenu {
    // Bank constructor, which takes maximum possible number of accounts in Bank
    static Bank bank = new Bank(20);
    //Function which showing first bank menu and taking back your choice as integer
    public static int menu(Scanner scan){
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
    public static int accountMenu(Scanner scan){
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
    public static void makeChoice(){
        int choice;
        do{
            choice = menu(bank.scan);
            System.out.println();
            switch (choice){
                case 1:bank.newAccount();break;
                case 2:
                    if(bank.getAccounts()[0]!=null){
                        bank.doLogin();makeSecondChoice();break;
                    }else{
                        System.out.println("\u001B[31m First You need to create an Account!");break;
                    }
                case 3:System.exit(0);bank.scan.close();
            }
        }while(choice!=3);

    }

    //Function which switching between methods in bank operations class, checking if you are not trying to send money on your own account
    public static void makeSecondChoice(){
        int choice;
        do{
            choice = accountMenu(bank.scan);
            System.out.println();
            switch (choice){
                case 1:bank.checkBalance();break;
                case 2:
                    if(bank.getAccounts()[1]!=null){
                        bank.transferMoneyDialog();break;
                    }else{
                        System.out.println("\u001B[31mYou cannot transfer money to nowhere!  There is only 1 Account!");break;}
                case 3:bank.changePin();break;
                case 4:bank.putMoney();break;
                case 5:logOut();break;
            }
        }while(choice!=5);
    }

    // Function for logging out, send u back to previous menu
    public static void logOut(){
        bank.setActiveAccount(0);
        System.out.println("\u001B[32mYou are logged out");
        makeChoice();
    }
}


