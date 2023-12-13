package org.example;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.example.NasaClient.NasaClient;

import java.io.FileOutputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        var nasaClient = new NasaClient("xxx");
        var picture = nasaClient.getAstronomyPictureOfTheDay();
        var httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(
                        RequestConfig.custom()
                                .setConnectTimeout(5000)
                                .setSocketTimeout(30000)
                                .setRedirectsEnabled(false)
                                .build()
                )
                .build();
        String[] url = picture.getUrl().split("/");
        String fileName = url[url.length - 1];

        var pictureFile = httpClient.execute(new HttpGet(picture.getUrl()));
        var entity = pictureFile.getEntity();
        var fos = new FileOutputStream("/var/www/java/jdfree-homeworks/02/HomeTask02/" + fileName);
        entity.writeTo(fos);
        fos.close();
    }
}