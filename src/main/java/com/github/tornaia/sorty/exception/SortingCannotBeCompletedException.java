package com.github.tornaia.sorty.exception;

public class SortingCannotBeCompletedException extends RuntimeException {

    public SortingCannotBeCompletedException(String message, Exception e) {
        super(message, e);
    }
}
