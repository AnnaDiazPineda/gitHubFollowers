package dsa.eetac.upc.edu.githubfollowers.io;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by anita on 03/12/2017.
 */

public class ApiAdapter {
    private static ApiService API_SERVICE;

    public static ApiService getApiService() {



        if (API_SERVICE == null) {

            // Creamos un interceptor y le indicamos el log level a usar
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
           //nivell de detall del log
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            // Asociamos el interceptor a las peticiones
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(logging); //log per veure les respostes

            String baseUrl = "https://api.github.com";
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build()) // <-- usamos el log level
                    .build();
            API_SERVICE = retrofit.create(ApiService.class);
        }

        return API_SERVICE;
    }
}
