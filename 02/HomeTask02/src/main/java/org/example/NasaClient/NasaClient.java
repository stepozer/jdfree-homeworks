package org.example.NasaClient;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class NasaClient {
    public static final String ENDPOINT_ASTRONOMY_PICTURE_OF_THE_DAY_URI = "https://api.nasa.gov/planetary/apod";

    private final String apiKey;
    private final CloseableHttpClient httpClient;
    private final ObjectMapper mapper;

    public NasaClient(String apiKey) {
        this.apiKey = apiKey;
        this.mapper = new ObjectMapper();
        this.httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(
                        RequestConfig.custom()
                                .setConnectTimeout(5000)
                                .setSocketTimeout(30000)
                                .setRedirectsEnabled(false)
                                .build()
                )
                .build();
    }

    public NasaAstronomyPictureOfTheDay getAstronomyPictureOfTheDay() throws IOException {
        var response = this.httpClient.execute(new HttpGet(
            ENDPOINT_ASTRONOMY_PICTURE_OF_THE_DAY_URI + "?api_key=" + apiKey
        ));

        return mapper.readValue(response.getEntity().getContent(), NasaAstronomyPictureOfTheDay.class);
    }
}
