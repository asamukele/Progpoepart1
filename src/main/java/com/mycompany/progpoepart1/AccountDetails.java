/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.progpoepart1;

import java.time.LocalDateTime;

/**
 *
 * @author Student
 */
// ==================== ACCOUNT DETAILS CLASS ====================
class AccountDetails {
    private final String identifier;
    private final String securityCode;
    private final String contactNumber;
    private final String givenName;
    private final String familyName;
    private final LocalDateTime creationDate;

    public AccountDetails(String identifier, String securityCode, String contactNumber,
                          String givenName, String familyName) {
        this.identifier = identifier;
        this.securityCode = securityCode;
        this.contactNumber = contactNumber;
        this.givenName = givenName;
        this.familyName = familyName;
        this.creationDate = LocalDateTime.now();
    }

    public boolean verifyCredentials(String identifier, String securityCode) {
        return this.identifier.equals(identifier) && this.securityCode.equals(securityCode);
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getGivenName() {
        return givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }
}
