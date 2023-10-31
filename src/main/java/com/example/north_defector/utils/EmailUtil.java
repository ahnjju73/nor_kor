package com.example.north_defector.utils;

import java.util.Map;

public interface EmailUtil {
    Map<String, Object> sendEmail(String toAddress, String subject, String name, String code);
}
