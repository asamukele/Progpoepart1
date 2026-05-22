/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.progpoepart1;

/**
 *
 * @author Student
 */
// ==================== ACCOUNT VALIDATOR CLASS ====================
class AccountValidator {
    public boolean isValidIdentifier(String identifier) {
        return identifier != null && identifier.contains("_") && identifier.length() <= 5;
    }

    public boolean isValidSecurityCode(String securityCode) {
        if (securityCode == null || securityCode.length() < 8) {
            return false;
        }

        boolean hasUppercase = !securityCode.equals(securityCode.toLowerCase());
        boolean hasDigit = securityCode.matches(".*\\d.*");
        boolean hasSpecial = securityCode.matches(".*[^A-Za-z0-9].*");

        return hasUppercase && hasDigit && hasSpecial;
    }

    public boolean isValidContactNumber(String contactNumber) {
        if (contactNumber == null || !contactNumber.startsWith("+")) {
            return false;
        }

        String withoutPlus = contactNumber.substring(1);

        if (!withoutPlus.matches("\\d+")) {
            return false;
        }

        return withoutPlus.length() <= 11;
    }
}
