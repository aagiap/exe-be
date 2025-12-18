package com.exebe.util;

import java.util.regex.Pattern;

public class Validate {
    private static final String EMAIL_REGEX =
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    private static final String PHONE_REGEX =
            "^(0[0-9]{9})$";

    private static final String FUL_NAME_REGEX = "^[\\p{L} ]+$";

    private static final String SPECIAL_CHARACTER_REGEX = "^[a-zA-Z0-9 ]+$";

    public static boolean isValidEmail(String email) {
        return email != null && Pattern.matches(EMAIL_REGEX, email);
    }

    public static boolean isValidPhone(String phone) {
        return phone != null && Pattern.matches(PHONE_REGEX, phone);
    }

    public static boolean isValidFullName(String fullName) {
        return fullName != null && fullName.matches(FUL_NAME_REGEX);
    }
}
