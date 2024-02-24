package main;

import client.Client;

public class Main {
    public static void main(String [] args){
        System.out.println( "\n\t\t\t\t\t\t\t\t\t\twelcome to :\n\r\n _____                   _                         __  __                                         \r\n|  _  |                 | |                       |  \\/  |                                        \r\n| (_) | _ __   ___    __| | _   _    __  _______  | \\  / |  __ _  _ __    __ _   __ _   ___  _ __ \r\n|  __/ | '__| / _ \\  / _  || | | | / __||__   __| | |\\/| | / _` || '_ \\  / _` | / _` | / _ \\| '__|\r\n| |    | |   | (_) || (_) || \\_/ || |__    | |    | |  | || (_| || | | || (_| || (_| ||  __/| |   \r\n|_|    |_|    \\___/  \\___/  \\___/  \\___|   |_|    |_|  |_| \\__,_||_| |_| \\__,_| \\__, | \\___||_|\r\n                                                                                 __/ |            \r\n                                                                                |___/             \r\n\t\t\t\t\t\t\t\t\t by Mohajer Alireza\r\n");
        Client client = new Client(1965);
        client.run();
    }
}
