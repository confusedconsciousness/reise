package com._xkishan.journal.utils;

public class Utils {
    public static String override(String oldContent,
                                  String newContent) {
        return (newContent == null || newContent.isEmpty()) ? oldContent : newContent;
    }
}
