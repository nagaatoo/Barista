package ru.numbDev.barista.utils;

public class CommonUtils {

    public static void idNullCheck(Long id) {
        if (id == null || id == 0) {
            throw ThrowUtils.apiEx("Id cannot be null or 0", 400);
        }
    }
}
