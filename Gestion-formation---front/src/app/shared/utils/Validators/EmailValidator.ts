// src/app/shared/utils/email-validator.ts

export class EmailValidator {

  // Regex for standard email validation
  private static readonly EMAIL_REGEX: RegExp =
    /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

  /**
   * Validates the given email string.
   * @param email The email to validate
   * @returns true if valid, false otherwise
   */
  public static validateEmail(email: string): boolean {
    if (!email) return false; // null or empty string
    return EmailValidator.EMAIL_REGEX.test(email.trim());
  }
}