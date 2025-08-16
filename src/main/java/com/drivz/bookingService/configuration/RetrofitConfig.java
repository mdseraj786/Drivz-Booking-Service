package com.drivz.bookingService.configuration;

import com.drivz.bookingService.api.DrivzSocketApi;
import com.drivz.bookingService.api.LocationServiceApi;
import com.netflix.discovery.EurekaClient;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Configuration
public class RetrofitConfig {
    @Autowired
    private EurekaClient eurekaClient;
    
    private String getServiceUrl(String serviceName){
        return eurekaClient.getNextServerFromEureka(serviceName,false).getHomePageUrl();
    }
    
    @Bean
    public LocationServiceApi locationServiceApi(){
        return new Retrofit.Builder()
                .baseUrl(getServiceUrl("DRIVZ-LOCATIONSERVICE"))
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .build()
                .create(LocationServiceApi.class);
    }

    @Bean
    public DrivzSocketApi drivzSocketApi(){
        return new Retrofit.Builder()
                .baseUrl(getServiceUrl("DRIVZ-SOCKETSERVER"))
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .build()
                .create(DrivzSocketApi.class);
    }
}
