package com.fbcq.backend.legaldistrict.infrastructure;

import com.fbcq.backend.legaldistrict.domain.LatLng;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHeaders;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;


@Component
@RequiredArgsConstructor
public class KakaoMapClient {
//    private final WebClient.Builder webClientBuilder;
    private final WebClient kakaoWebClient;
    public Optional<LatLng> getCoordinates(String fullAddress) {

//        WebClient webClient = webClientBuilder
//                .baseUrl("https://dapi.kakao.com")
//                .defaultHeader(HttpHeaders.AUTHORIZATION, "KakaoAK " + kakaoApiKey)
//                .build();
        try {
            String json = kakaoWebClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/v2/local/search/address.json")
                            .queryParam("query", fullAddress)
                            .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block(); // 동기 방식으로 호출 (비동기 원하면 .subscribe())

            JSONObject result = new JSONObject(json);
            JSONArray docs = result.getJSONArray("documents");

            if (!docs.isEmpty()) {
                JSONObject address = docs.getJSONObject(0).getJSONObject("address");
                return Optional.of(new LatLng(address.getDouble("y"), address.getDouble("x")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}


