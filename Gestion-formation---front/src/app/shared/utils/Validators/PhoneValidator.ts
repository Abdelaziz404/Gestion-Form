// src/app/shared/utils/phone-validator.ts

export class PhoneValidator {

  // Regex for exactly 8 digits
  private static readonly PHONE_REGEX: RegExp = /^\d{8}$/;

  /**
   * Validates the given phone number string.
   * @param phone The phone number to validate
   * @returns true if phone has exactly 8 digits, false otherwise
   */
  public static validatePhone(phone: string): boolean {
    if (!phone) return false; // null or empty
    return PhoneValidator.PHONE_REGEX.test(phone.trim());
  }
}