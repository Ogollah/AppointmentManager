package com.example.appointmentmanager.utils;

public abstract class Result<T>  {
    public static class Success<T> extends Result<T>{
        private T date;

        public Success(T date) {
            this.date = date;
        }

        public T getData() {
            return date;
        }
    }

    public static class Error<T> extends Result<T>{
        private Exception error;

        public Error(Exception error){
            this.error=error;
        }

        public Exception getError() {
            return error;
        }
    }
}
