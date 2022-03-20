package com.vkuzmenko.pizza.admin.service;

import com.vkuzmenko.pizza.admin.dto.Slider;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.vkuzmenko.pizza.admin.Constants.COMMON_SERVICE;
import static com.vkuzmenko.pizza.admin.helper.ResponsesHelper.httpHeaders;

@Service
public class SliderServiceImpl implements SliderService {

    @Override
    public List<Slider> findAll() {
        final String URL = COMMON_SERVICE + "slider";

        final ResponseEntity<List<Slider>> response = new RestTemplate()
                .exchange(URL, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<Slider>>() {
                        });

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Slider not found");
        }

        return response.getBody();
    }

    @Override
    public Optional<Slider> findById(Long id) {
        final String URL = COMMON_SERVICE + "slider/{id}";

        Map<String, Long> urlParams = new HashMap<>();
        urlParams.put("id", id);

        ResponseEntity<Slider> response =
                new RestTemplate().getForEntity(URL, Slider.class, urlParams);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Slider not found");
        }

        return Optional.ofNullable(response.getBody());
    }

    @Override
    public ResponseEntity<String> insert(Slider slider, MultipartFile imageFile) {
        final String URL = COMMON_SERVICE + "slider";

        // Add image
        // Save file imageFile to storage
        // Get url
        // Product.setImage(url);

        HttpEntity<Slider> request = new HttpEntity<>(slider, httpHeaders());

        return new RestTemplate()
                .postForEntity(URL, request, String.class);
    }

    @Override
    public ResponseEntity<String> update(Slider slider, MultipartFile imageFile) {
        final String URL = COMMON_SERVICE + "slider/{id}";

        // Add image
        // Save file imageFile to storage
        // Get url
        // Product.setImage(url);

        Map<String, Long> urlParams = new HashMap<>();
        urlParams.put("id", slider.getId());

        HttpEntity<Slider> request = new HttpEntity<>(slider, httpHeaders());

        return new RestTemplate()
                .exchange(URL, HttpMethod.PUT, request, String.class, urlParams);
    }

    @Override
    public ResponseEntity<String> delete(Long id) {
        final String URL = COMMON_SERVICE + "slider/{id}";

        Map<String, Long> params = new HashMap<>();
        params.put("id", id);

        return new RestTemplate()
                .exchange(URL, HttpMethod.DELETE, null, String.class, params);
    }
}
