package com.example.appointmentmanager.utils;

/**
 * An abstract class representing the result of an operation, which can be either success or failure.
 *
 * @param <T> The type of data associated with the result.
 */
public abstract class Result<T>  {

    /**
     * Represents a successful result containing data.
     *
     * @param <T> The type of data associated with the success result.
     */
    public static class Success<T> extends Result<T>{
        private T data;

        /**
         * Constructs a Success result with the provided data.
         *
         * @param data The data associated with the success result.
         */
        public Success(T data) {
            this.data = data;
        }

        /**
         * Gets the data associated with the success result.
         *
         * @return The data associated with the success result.
         */
        public T getData() {
            return data;
        }
    }

    /**
     * Represents an error result containing an exception.
     *
     * @param <T> The type of data associated with the error result.
     */
    public static class Error<T> extends Result<T>{
        private Exception error;

        /**
         * Constructs an Error result with the provided exception.
         *
         * @param error The exception associated with the error result.
         */
        public Error(Exception error){
            this.error = error;
        }

        /**
         * Gets the exception associated with the error result.
         *
         * @return The exception associated with the error result.
         */
        public Exception getError() {
            return error;
        }
    }
}
