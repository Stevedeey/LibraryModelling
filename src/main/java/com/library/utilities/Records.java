package com.library.utilities;

import java.util.HashMap;
import java.util.Map;

public class Records {
    private static Map<Integer, String> records = new HashMap<>();

    public static void updateDateRecord(int id, String value) {
        records.put(id, value);
    }

    public static Map<Integer, String> getRecords() {
        return records;
    }


}
