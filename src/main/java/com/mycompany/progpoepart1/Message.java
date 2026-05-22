/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.progpoepart1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 *
 * @author Student
 */
// ==================== MESSAGE CLASS ====================
class Message {
    private final String messageID;
    private static int numMessagesSent = 0;
    private final String recipient;
    private final String messageContent;
    private final String messageHash;
    private static int totalMessagesSent = 0;

    public Message(String recipient, String messageContent) {
        this.messageID = generateMessageID();
        this.recipient = recipient;
        this.messageContent = messageContent;
        this.messageHash = createMessageHash();
        numMessagesSent++;
    }

    private String generateMessageID() {
        Random random = new Random();
        StringBuilder id = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            id.append(random.nextInt(10));
        }
        return id.toString();
    }

    public boolean checkMessageID() {
        return messageID != null && messageID.length() <= 10;
    }

    public int checkRecipientCell() {
        if (recipient == null) {
            return -1;
        }
        if (!recipient.startsWith("+")) {
            return -1;
        }
        String digits = recipient.substring(1);
        if (digits.length() > 10) {
            return 0;
        }
        if (!digits.matches("\\d+")) {
            return -1;
        }
        return 1;
    }

    public String createMessageHash() {
        if (messageContent == null || messageContent.trim().isEmpty()) {
            return messageID.substring(0, 2) + ":" + numMessagesSent + ":EMPTY";
        }
        String[] words = messageContent.trim().split("\\s+");
        String firstWord = words[0].toUpperCase();
        String lastWord = words[words.length - 1].toUpperCase();
        return messageID.substring(0, 2) + ":" + numMessagesSent + ":" + firstWord + lastWord;
    }

    public void storeMessage() {
        try {
            StringBuilder json = new StringBuilder();
            json.append("{\n");
            json.append("  \"messageID\": \"").append(this.messageID).append("\",\n");
            json.append("  \"messageHash\": \"").append(this.messageHash).append("\",\n");
            json.append("  \"recipient\": \"").append(this.recipient).append("\",\n");
            json.append("  \"messageContent\": \"").append(this.messageContent).append("\",\n");
            json.append("  \"numMessagesSent\": ").append(numMessagesSent).append(",\n");
            json.append("  \"timestamp\": \"").append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\"\n");
            json.append("}");
            String fileName = "stored_message_" + this.messageID + ".json";
            File file = new File(fileName);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(json.toString());
            }
            System.out.println("Message stored in JSON file: " + fileName);
        } catch (IOException e) {
            System.out.println("[ERROR] Failed to store message: " + e.getMessage());
        }
    }

    public String getMessageID() {
        return messageID;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public static int getTotalMessagesSent() {
       
        return totalMessagesSent;
    }
     public static void resetTotalMessagesSent() {
       totalMessagesSent =0;
    }

     
    public static void incrementTotalMessages() {
        totalMessagesSent++;
    }
}
