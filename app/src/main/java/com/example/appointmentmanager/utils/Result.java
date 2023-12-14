package com.example.appointmentmanager.utils;

public abstract class Result<T>  {
    public static class Success<T> extends Result<T>{
        private T data;

        public Success(T data) {
            this.data = data;
        }

        public T getData() {
            return data;
        }
    }

    public static class SuccessVoid extends Result<Void> {
        public SuccessVoid() {
            // No data for Void
        }
    }

    public static class SuccessWithId<T> extends Success<T> {
        private long id;

        public SuccessWithId(T data, long id) {
            super(data);
            this.id = id;
        }

        public long getId() {
            return id;
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
