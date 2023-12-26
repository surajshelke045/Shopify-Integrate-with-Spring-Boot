package com.shopify.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.shopify.Models.Shop;

@Service
public class ShopService {
	
	@Value("${shopify.base-url}")
	private String baseUrl;
	
	@Value("${shopify.access-token}")
	private String accessToken;
	
	private HttpEntity<Object> getHttpEntity(Object body) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		headers.set("X-Shopify-Access-Token", accessToken);
		headers.set("accept", "application/json");
		return new HttpEntity<>(body, headers);
	}

	private RestTemplate restTemplate = new RestTemplate();

    public ResponseEntity<String> getShopInfo(Shop shop) {
        String url = shop.getUrl() + "/admin/api/2023-04/shop.json";
        System.out.println(url);
		return restTemplate.exchange(url, HttpMethod.GET, getHttpEntity(shop),String.class);
    }
    
	public ResponseEntity<String> getProducts(Shop shop) {
		String url =shop.getUrl() + "/admin/api/2023-04/products.json";
		System.out.println(url);
		return restTemplate.exchange(url, HttpMethod.GET, getHttpEntity(shop), String.class);
	}

	
	

}
