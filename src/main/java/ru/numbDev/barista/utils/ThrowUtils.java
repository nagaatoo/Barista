package ru.numbDev.barista.utils;

import ru.numbDev.barista.exeptions.ApiException;

import java.util.function.Supplier;

public class ThrowUtils {

    public static ApiException apiEx(String message, int code) {
        return new ApiException(message, code);
    }

    public static Supplier<ApiException> throwApiExRequest(String message) {
        return () -> new ApiException(message, 400);
    }

    public static Supplier<ApiException> throwApiExServer(String message) {
        return () -> new ApiException(message, 500);
    }

}
