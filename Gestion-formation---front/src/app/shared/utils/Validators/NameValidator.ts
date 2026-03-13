// src/app/shared/utils/name-validator.ts

export class NameValidator {

  // Regex: letters + spaces only
  private static readonly NAME_REGEX: RegExp =
    /^[A-Za-zÀ-ÿ\s]+$/;

  /**
   * Validate name or first name
   * @param value string to validate
   * @returns true if valid
   */
  public static validateName(value: string): boolean {

    if (!value) {
      return false;
    }

    const trimmed = value.trim();

    if (trimmed.length === 0) {
      return false;
    }

    return NameValidator.NAME_REGEX.test(trimmed);
  }

}