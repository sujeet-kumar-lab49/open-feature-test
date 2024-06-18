package com.lab49.feature.configurations;

import dev.openfeature.contrib.providers.flagd.FlagdOptions;
import dev.openfeature.contrib.providers.flagd.FlagdProvider;
import dev.openfeature.sdk.EvaluationContext;
import dev.openfeature.sdk.OpenFeatureAPI;
import dev.openfeature.sdk.exceptions.OpenFeatureError;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenFeatureBeans {

    @Bean
    public OpenFeatureAPI OpenFeatureAPI() {
        final OpenFeatureAPI openFeatureAPI = OpenFeatureAPI.getInstance();

        // Use flagd as the OpenFeature provider and use default configurations
        /*FlagdProvider provider = new FlagdProvider(FlagdOptions.builder()
                .host("192.168.49.2")
                .port(30634)
                .tls(false)
                .deadline(2000)
                .build());
        try {
            openFeatureAPI.setProviderAndWait(provider);
        } catch (OpenFeatureError e) {
            throw new RuntimeException("Failed to set OpenFeature provider", e);
        }*/


        return openFeatureAPI;
    }
}