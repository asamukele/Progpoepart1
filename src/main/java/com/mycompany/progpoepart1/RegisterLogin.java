/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.progpoepart1;

/**
 *
 * @author Student
 */
import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class RegisterLogin {
    private static final Scanner input = new Scanner(System.in);
    private static final Map<String, AccountDetails> accountDatabase = new TreeMap<>();
    private static final List<MessageLog> chatHistory = new ArrayList<>();
    private static final List<Message> messagesList = new ArrayList<>();
    private static AccountDetails currentLoggedInUser = null;
    // Hidden frame to act as owner of dialog boxes
    private static final JFrame dialogParentFrame = createDialogParentFrame();

    private static JFrame createDialogParentFrame() {
        JFrame frame = new JFrame("Dialog Parent");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(0, 0); // Invisible size
        frame.setLocationRelativeTo(null);
        frame.setAlwaysOnTop(true); // Keep always on top
        frame.setUndecorated(true); // No window decorations
        frame.setVisible(false); // Invisible frame
        return frame;
    }

    public static void main(String[] args) {
        displayWelcomeBanner();

        boolean applicationRunning = true;
        while (applicationRunning) {
            displayMainMenu();
            String selection = input.nextLine().trim();

            switch (selection) {
                case "1":
                    createNewAccount();
                    break;
                case "2":
                    if (authenticateUser()) {
                        startQuickChatSession();
                    }
                    break;
                case "3":
                    applicationRunning = false;
                    System.out.println("\nExiting application. Thank you for using MessengerSystem!");
                    break;
                default:
                    System.out.println("\n[ERROR] Invalid selection. Please try again.");
            }
        }
        dialogParentFrame.dispose(); // Clean up frame on exit
    }

    private static void displayWelcomeBanner() {
        System.out.println("*************************************************");
        System.out.println("*          WELCOME TO MESSENGER SYSTEM          *");
        System.out.println("*************************************************");
        System.out.println("\nAccount Creation Requirements:");
        System.out.println("- Identifier must contain an underscore (_) and be 5 or fewer characters");
        System.out.println("- Security code must be at least 8 characters with one uppercase letter,");
        System.out.println("  one digit, and one special character");
        System.out.println("- Contact number must include country code (+) and maximum 10 digits");
        System.out.println("*************************************************");
    }

    private static void displayMainMenu() {
        System.out.println("\nMAIN MENU");
        System.out.println("1. Create Account");
        System.out.println("2. Access Account");
        System.out.println("3. Exit Application");
        System.out.print("\nSelect an option (1-3): ");
    }

    private static void createNewAccount() {
        System.out.println("\n--- ACCOUNT CREATION ---");

        System.out.print("Enter identifier: ");
        String identifier = input.nextLine().trim();

        System.out.print("Enter security code: ");
        String securityCode = input.nextLine().trim();

        System.out.print("Enter contact number (with country code, e.g., +12025550179): ");
        String contactNumber = input.nextLine().trim();

        System.out.print("Enter given name: ");
        String givenName = input.nextLine().trim();

        System.out.print("Enter family name: ");
        String familyName = input.nextLine().trim();

        AccountValidator validator = new AccountValidator();

        if (!validator.isValidIdentifier(identifier)) {
            System.out.println("\n[ERROR] Invalid identifier format. Must contain an underscore and be 5 or fewer characters.");
            return;
        }

        if (!validator.isValidSecurityCode(securityCode)) {
            System.out.println("\n[ERROR] Invalid security code. Must be at least 8 characters with one uppercase letter, one digit, and one special character.");
            return;
        }

        if (!validator.isValidContactNumber(contactNumber)) {
            System.out.println("\n[ERROR] Invalid contact number. Must include country code (+) and be 10 or fewer digits.");
            return;
        }

        if (accountDatabase.containsKey(identifier)) {
            System.out.println("\n[ERROR] This identifier is already in use. Please choose another one.");
            return;
        }

        AccountDetails newAccount = new AccountDetails(identifier, securityCode, contactNumber, givenName, familyName);
        accountDatabase.put(identifier, newAccount);

        System.out.println("\n[SUCCESS] Account created successfully!");
        System.out.println("- Identifier registered: " + identifier);
        System.out.println("- Security code set successfully");
        System.out.println("- Contact number registered: " + contactNumber);
    }

    private static boolean authenticateUser() {
        System.out.println("\n--- ACCOUNT ACCESS ---");

        System.out.print("Enter identifier: ");
        String identifier = input.nextLine().trim();

        System.out.print("Enter security code: ");
        String securityCode = input.nextLine().trim();

        AccountDetails account = accountDatabase.get(identifier);

        if (account != null && account.verifyCredentials(identifier, securityCode)) {
            System.out.println("\n[SUCCESS] Authentication successful!");
            System.out.println("Welcome back, " + account.getGivenName() + " " + account.getFamilyName() + "!");
            currentLoggedInUser = account;
            return true;
        } else {
            System.out.println("\n[ERROR] Authentication failed. Invalid identifier or security code.");
            return false;
        }
    }

    private static void startQuickChatSession() {
        System.out.println("\n=== Welcome to QuickChat ===");

        System.out.print("How many messages would you like to send? ");
        int maxMessages;
        try {
            maxMessages = Integer.parseInt(input.nextLine().trim());
            if (maxMessages <= 0) {
                System.out.println("[ERROR] Please enter a positive number.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("[ERROR] Please enter a valid number.");
            return;
        }

        boolean sessionActive = true;
        while (sessionActive) {
            displayQuickChatMenu();
            String choice = input.nextLine().trim();

            switch (choice) {
                case "1":
                    if (Message.getTotalMessagesSent() >= maxMessages) {
                        System.out.println("\n[INFO] You have reached your maximum number of messages (" + maxMessages + ").");
                        System.out.println("Total messages sent: " + Message.getTotalMessagesSent());
                    } else {
                        sendMessage(maxMessages);
                    }
                    break;
                case "2":
                    System.out.println("\n[INFO] Coming soon");
                    break;
                case "3":
                    sessionActive = false;
                    System.out.println("\nExiting QuickChat...");
                    System.out.println("Total messages sent in this session: " + Message.getTotalMessagesSent());
                    currentLoggedInUser = null;
                    break;
                default:
                    System.out.println("\n[ERROR] Invalid option. Please choose 1, 2, or 3.");
            }
        }
    }

    private static void displayQuickChatMenu() {
        System.out.println("\n--- QUICKCHAT MENU ---");
        System.out.println("1) Send messages");
        System.out.println("2) Show recently sent messages");
        System.out.println("3) Quit");
        System.out.print("\nSelect an option (1-3): ");
    }

    private static void sendMessage(int maxMessages) {
        if (Message.getTotalMessagesSent() >= maxMessages) {
            System.out.println("\n[INFO] Maximum number of messages reached.");
            return;
        }

        System.out.println("\n--- SEND MESSAGE ---");

        System.out.print("Enter recipient cell number (with country code, max 10 digits): ");
        String recipient = input.nextLine().trim();

        System.out.print("Enter your message (max 250 characters): ");
        String messageContent = input.nextLine().trim();

        Message newMessage = new Message(recipient, messageContent);

        if (!newMessage.checkMessageID()) {
            System.out.println("\n[ERROR] Message ID validation failed.");
            return;
        }

        int recipientValidation = newMessage.checkRecipientCell();
        if (recipientValidation != 1) {
            if (recipientValidation == 0) {
                System.out.println("\n[ERROR] Recipient cell number is too long (max 10 characters).");
            } else {
                System.out.println("\n[ERROR] Recipient cell number must start with international code (+).");
            }
            return;
        }

        if (messageContent.length() > 250) {
            System.out.println("\n[ERROR] Please enter a message of less than 250 characters");
            return;
        }

        if (messageContent.length() > 50) {
            System.out.println("\n[ERROR] Please enter a message of less than 50 characters");
            return;
        }

        System.out.println("\n[SUCCESS] Message sent");

        System.out.println("\nWhat would you like to do with this message?");
        System.out.println("1. Send Message");
        System.out.println("2. Disregard Message");
        System.out.println("3. Store Message to send later");
        System.out.print("Choose an option (1-3): ");
        String action = input.nextLine().trim();

        switch (action) {
            case "1":
                messagesList.add(newMessage);
                Message.incrementTotalMessages();
                displayMessageDetails(newMessage);
                break;
            case "3":
                newMessage.storeMessage();
                messagesList.add(newMessage);
                Message.incrementTotalMessages();
                System.out.println("\n[INFO] Message stored for later sending.");
                break;
            case "2":
                System.out.println("\n[INFO] Message discarded.");
                break;
            default:
                System.out.println("[ERROR] Invalid choice. Defaulting to 'send'.");
                messagesList.add(newMessage);
                Message.incrementTotalMessages();
                displayMessageDetails(newMessage);
                break;
        }
    }

    private static void displayMessageDetails(Message message) {
        String dialogMessage = String.format(
                "Message ID: %s\n\n" +
                "Message Hash: %s\n\n" +
                "Recipient: %s\n\n" +
                "Message: %s",
                message.getMessageID(),
                message.getMessageHash(),
                message.getRecipient(),
                message.getMessageContent()
        );

        try {
            JOptionPane.showMessageDialog(dialogParentFrame, dialogMessage,
                    "Message Details", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            System.out.println("[ERROR] Could not display dialog: " + e.getMessage());
            System.out.println("[INFO] Message details:");
            System.out.println(dialogMessage);
        }
    }
}
