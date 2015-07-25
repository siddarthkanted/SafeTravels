package com.microsoft.safetravel.SmsParser;

import java.util.ArrayList;
import java.util.List;

public class SmsParser implements ISmsParser {
    private List<ISmsParser> smsParsers = new ArrayList<ISmsParser>();

    public SmsParser() {
        smsParsers.add(new TaxiForSureSmsParser());
    }

    public ParsedSms parseSms(String message) {
        if(message == null || message.isEmpty()) {
            return null;
        }

        for(ISmsParser smsParser : smsParsers) {
            ParsedSms parsedSms = smsParser.parseSms(message);
            if(parsedSms != null) {
                return parsedSms;
            }
        }

        return null;
    }
}
