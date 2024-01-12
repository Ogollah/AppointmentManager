package com.example.appointmentmanager.data;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Singleton class for creating and configuring the Retrofit instance to communicate with the API.
 */
public class ApiClient {
    // Base URL for the API
    private static final String BASE_URL = "http://192.168.1.100:8099";

    // Singleton instance of Retrofit
    private static Retrofit retrofit = null;

    /**
     * Get or create the Retrofit instance for API communication.
     *
     * @return The Retrofit instance.
     */
    public static Retrofit getClient() {
        // Create Retrofit instance if not already initialized
        if (retrofit == null) {
            // Create a logging interceptor to log HTTP requests and responses
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            // Build OkHttpClient with the logging
            // interceptor for detailed logging of HTTP interactions.
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(loggingInterceptor);

            // Build the Retrofit instance
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }
        return retrofit;
    }
}
