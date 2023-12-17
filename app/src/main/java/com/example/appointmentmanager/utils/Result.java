package com.example.appointmentmanager.utils;

public abstract class Result<T>  {
    public static class Success<T> extends Result<T>{
        private T data;

        public Success(T date) {
            this.data = date;
        }

        public T getData() {
            return data;
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
