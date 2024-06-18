package com.lab49.feature.controller;

import com.lab49.feature.services.DynamicFeature;
import dev.openfeature.sdk.Client;
import dev.openfeature.sdk.OpenFeatureAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class FeatureController {
    private final OpenFeatureAPI openFeatureAPI;
    private final DynamicFeature dynamicFeature;

    @Autowired
    public FeatureController(OpenFeatureAPI OFApi, DynamicFeature dynamicFeature) {
        this.openFeatureAPI = OFApi;
        this.dynamicFeature = dynamicFeature;
    }

    @GetMapping("/hello")
    public String getHello() {
        final Client client = openFeatureAPI.getClient();

        System.out.println(client.getBooleanValue("show-welcome-banner", false) + " client.getBooleanValue(\"show-welcome-banner\", false)");
        // Evaluate welcome-message feature flag
        if (client.getBooleanValue("show-welcome-banner", false)) {
            return "Hello, welcome to this OpenFeature-enabled website! , color : " + client.getStringValue("background-color", "NONE");
        }

        return "Hello!";
    }

    @GetMapping("/env/all")
    public Map<String, String> envAll() {
        return System.getenv();
    }

    @GetMapping("/env/dynamic")
    public Map<String, String> envDynamic() {
        return dynamicFeature.getFeatureState();
    }
}