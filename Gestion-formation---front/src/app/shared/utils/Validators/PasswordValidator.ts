// src/app/shared/utils/password-validator.ts

export class PasswordValidator {

  // Regex explanation:
  // ^                 : start of string
  // (?=.*[a-z])       : at least one lowercase letter
  // (?=.*[A-Z])       : at least one uppercase letter
  // (?=.*\d)          : at least one digit
  // (?=.*[@$!%*?&])   : at least one special character
  // [A-Za-z\d@$!%*?&] : only allowed characters
  // {8,}              : minimum 8 characters
  // $                 : end of string
  private static readonly PASSWORD_REGEX: RegExp =
    /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;

  /**
   * Validates the given password string.
   * @param password The password to validate
   * @returns true if password meets criteria, false otherwise
   */
  public static validatePassword(password: string): boolean {
    if (!password) return false; // null or empty
    return PasswordValidator.PASSWORD_REGEX.test(password.trim());
  }
}