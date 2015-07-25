package com.microsoft.safetravel.SmsParser;

public interface ISmsParser {
    ParsedSms parseSms(String message);
}
