public class Login {

    private String firstName;
    private String lastName;
    private String userName;
    private String userPassword;
    private String phoneNumber;

    // Default constructor
    public Login() {}

    // Parameter constructor
    public Login(String fName, String lName, String uName, String pWord, String phone) {
        this.firstName = fName;
        this.lastName = lName;
        this.userName = uName;
        this.userPassword = pWord;
        this.phoneNumber = phone;
    }

    // SETTERS
    public void setFirstName(String fName) { firstName = fName; }
    public void setLastName(String lName) { lastName = lName; }
    public void setUsername(String uName) { userName = uName; }
    public void setPassword(String pWord) { userPassword = pWord; }
    public void setCellPhoneNumber(String phone) { phoneNumber = phone; }

    // GETTERS
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getUsername() { return userName; }
    public String getCellPhoneNumber() { return phoneNumber; }

    // Username validation
    public boolean checkUserName() {
        return userName != null &&
               userName.contains("_") &&
               userName.length() <= 5;
    }

    // Password validation
    public boolean checkPasswordComplexity() {

        if (userPassword == null || userPassword.length() < 8)
            return false;

        boolean hasUpper = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        for (char c : userPassword.toCharArray()) {

            if (Character.isUpperCase(c))
                hasUpper = true;

            else if (Character.isDigit(c))
                hasDigit = true;

            else if (!Character.isLetterOrDigit(c))
                hasSpecial = true;
        }

        return hasUpper && hasDigit && hasSpecial;
    }

    // SA phone validation (+27XXXXXXXXX)
    public boolean checkCellPhoneNumber() {
        return phoneNumber != null &&
               phoneNumber.matches("\\+27\\d{9}");
    }

    // Registration
    public String registerUser() {

        String message = "";

        if (!checkUserName())
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";

        message += "Username successfully captured\n";

        if (!checkPasswordComplexity())
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";

        message += "Password successfully captured\n";

        if (!checkCellPhoneNumber())
            return "Cell phone number incorrectly formatted or does not contain international code.";

        message += "Cell phone number successfully added";

        return message;
    }

    // Login check
    public boolean loginUser(String enteredUser, String enteredPass) {

        return enteredUser != null &&
               enteredPass != null &&
               userName != null &&
               userPassword != null &&
               userName.equals(enteredUser) &&
               userPassword.equals(enteredPass);
    }

    // Login message
    public String returnLoginStatus(String enteredUser, String enteredPass) {

        if (loginUser(enteredUser, enteredPass)) {
            return "Welcome " + firstName + ", " + lastName +
                   " it is great to see you again.";
        }

        return "Username or password incorrect, please try again.";
    }
}
