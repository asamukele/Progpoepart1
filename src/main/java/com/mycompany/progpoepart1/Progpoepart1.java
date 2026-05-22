package com.mycompany.progpoepart1;

import java.util.Scanner;

public class Progpoepart1 {
    
    public static void main(String[] args) {
        // Create a scanner object for user input
        Scanner scanner = new Scanner(System.in);
        
        // Simulating login functionality
        System.out.println("Welcome to the Login System!");
        
        // Prompt for username
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        
        // Prompt for password
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        
        // Simulate authentication (in a real app, you would check a database or other data source)
        if (username.equals("admin") && password.equals("password")) {
            System.out.println("Login successful!");
        } else {
            System.out.println("Invalid username or password.");
        }
        
        // Close the scanner to prevent resource leak
        scanner.close();
    }
}