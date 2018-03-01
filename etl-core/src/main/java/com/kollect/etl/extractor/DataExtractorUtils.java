package com.kollect.etl.extractor;

import java.text.MessageFormat;

public class DataExtractorUtils {

    public static String buildStatsMessage(Object[] messageFormatObject) {
        return MessageFormat.format("{0}|{1}|{2}|{3}|{4}|{5}", messageFormatObject);
    }

    public static long getDifference(long start, long end) {
        return end - start;
    }
}
