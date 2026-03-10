package Service.Validators;

import Exception.ValidationException;

public final class StringMatchValidator {

    private StringMatchValidator() {}

    /**
     * Compares original and updated strings. If changed, executes onChange.
     */
    public static void matchOrElse(String original, String updated, String fieldName, Runnable onChange) {
        if (original == null || updated == null) return;
        if (!original.equals(updated)) {
            if (onChange != null) {
                onChange.run();
            } else {
                throw new ValidationException(fieldName + " cannot be changed");
            }
        }
    }
}