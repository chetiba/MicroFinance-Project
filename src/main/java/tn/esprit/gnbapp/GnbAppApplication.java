package tn.esprit.gnbapp;

import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.catalina.filters.CorsFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.IOException;
import java.text.ParseException;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "tn.esprit.gnbapp.repositories")
@AllArgsConstructor
public class GnbAppApplication {

    public static void main(String[] args) throws IOException, ParseException {
        SpringApplication.run(GnbAppApplication.class, args);
    }

}
