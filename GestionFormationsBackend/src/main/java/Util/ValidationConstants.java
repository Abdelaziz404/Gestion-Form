package Util;

public final class ValidationConstants {

    private ValidationConstants() {} // prevent instantiation

    // -----------------------
    // Text fields
    // -----------------------
    public static final String EMPTY_ERROR_MSG = "Please provide a value for this field";

    public static final String NOT_BLANK_ERROR_MSG = "This field looks great! Just make sure it's not blank";

    public static final String NAME_REGEX = "^[\\p{L} ]+$";
    public static final String NAME_ERROR_MSG = "Please enter only letters and spaces for this field";

    // -----------------------
    // Email
    // -----------------------
    public static final String EMAIL_ERROR_MSG = "Please provide a valid email address (e.g., name@example.com)";

    // -----------------------
    // Password
    // -----------------------
    public static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$";
    public static final String PASSWORD_ERROR_MSG = "Your password is strong! Make sure it has at least 8 characters, including uppercase, lowercase, number, and special character";

    // -----------------------
    // Phone
    // -----------------------
    public static final String PHONE_REGEX = "^\\+?[0-9]{10,15}$";
    public static final String PHONE_ERROR_MSG = "Please enter a valid phone number with 10-15 digits (e.g., +1234567890)";

    // -----------------------
    // URL / image
    // -----------------------
    public static final String URL_ERROR_MSG = "Please provide a valid image URL (e.g., https://example.com/image.png)";

    // -----------------------
    // Roles / permissions
    // -----------------------
    public static final String ROLE_ERROR_MSG = "Please select a valid role: ADMIN, PARTICIPANT, or FORMATEUR";

    public static final String PERMISSION_ERROR_MSG = "Please select a valid permission value (0, 1, 2, 4, or 8)";

    // -----------------------
    // Numbers / doubles
    // -----------------------
    public static final String NUMBER_ERROR_MSG = "Please enter a valid number";

    public static final String DOUBLE_ERROR_MSG = "Please enter a valid decimal value";

    public static final String MIN_VALUE_ERROR_MSG = "The value is good, but it should be a bit higher";

    public static final String MAX_VALUE_ERROR_MSG = "The value is good, but it should be a bit lower";

    // -----------------------
    // Dates
    // -----------------------
    public static final String DATE_ERROR_MSG = "Please enter a valid date (YYYY-MM-DD)";

}
