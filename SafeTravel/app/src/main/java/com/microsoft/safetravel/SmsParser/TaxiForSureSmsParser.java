package com.microsoft.safetravel.SmsParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TaxiForSureSmsParser implements ISmsParser {
    private static Pattern BOOKING_ID_PATTERN = Pattern.compile("TFS-PP-([a-zA-Z0-9]+)");
    private static Pattern BOOKING_TIME_PATTERN = Pattern.compile("confirmed for (.+?)\\. Driver");
    private static Pattern DRIVER_NAME_PATTERN = Pattern.compile("Driver: (.+?)- ");
    private static Pattern DRIVER_MOBILE_PATTERN = Pattern.compile("Driver: .*- (\\d+)");
    private static Pattern VEHICLE_MODEL_PATTERN = Pattern.compile("Driver: .* \\((.+), .*\\)\\.");
    private static Pattern VEHICLE_NUMBER_PATTERN = Pattern.compile("Driver: .* \\(.+, (.+)\\)\\.");

    public ParsedSms parseSms(String message) {
        if(message == null || message.isEmpty()) {
            return null;
        }

        Matcher bookingIdMatcher = BOOKING_ID_PATTERN.matcher(message);

        ParsedSms parsedSms = new ParsedSms();
        parsedSms.setServiceName("TFS");
        parsedSms.setBookingId(RegexUtil.firstMatch(BOOKING_ID_PATTERN, message));
        parsedSms.setBookingTime(RegexUtil.firstMatch(BOOKING_TIME_PATTERN, message));
        parsedSms.setDriverName(RegexUtil.firstMatch(DRIVER_NAME_PATTERN, message));
        parsedSms.setDriverMobile(RegexUtil.firstMatch(DRIVER_MOBILE_PATTERN, message));
        parsedSms.setVehicleModel(RegexUtil.firstMatch(VEHICLE_MODEL_PATTERN, message));
        parsedSms.setVehicleNumber(RegexUtil.firstMatch(VEHICLE_NUMBER_PATTERN, message));

        return parsedSms.isValid() ? parsedSms : null;
    }
}
