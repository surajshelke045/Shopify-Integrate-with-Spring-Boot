package com.shopify.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.shopify.Exception.ResourceNotFoundException;
import com.shopify.Models.Shop;

@Service
public class InventoryLocation {

	@Value("${shopify.base-url}")
	private String baseUrl;

	@Value("${shopify.access-token}")
	private String accessToken;

	private RestTemplate restTemplate = new RestTemplate();

	private HttpEntity<Object> getHttpEntity(Object body) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		headers.set("X-Shopify-Access-Token", accessToken);
		headers.set("accept", "application/json");
		return new HttpEntity<>(body, headers);
	}

	private HttpHeaders createHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		headers.set("X-Shopify-Access-Token", accessToken);
		return headers;
	}

	public ResponseEntity<String> getLocations(Shop shop) {
		String url = shop.getUrl() + "/admin/api/2023-04/locations.json";
		HttpHeaders headers = createHeaders();
		System.out.println(headers);
		System.out.println(url);
		HttpEntity<Shop> requestEntity = new HttpEntity<>(shop, headers);
		return restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
	}

	public ResponseEntity<String> getLocationCount(Shop shop) {
		String url = shop.getUrl() + "/admin/api/2023-04/locations/count.json";
		HttpHeaders headers = createHeaders();
		HttpEntity<Shop> requestEntity = new HttpEntity<>(shop, headers);
		return restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
	}

	public ResponseEntity<String> getLocationById(String locationId, Shop shop) {
		try {
			String url = shop.getUrl() + "/admin/api/2023-04/locations/" + locationId + ".json";
			HttpHeaders headers = createHeaders();
			HttpEntity<Shop> requestEntity = new HttpEntity<>(shop, headers);
			return restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
		} catch (HttpClientErrorException e) {
			if (e.getStatusCode().value() == 404) {
				throw new ResourceNotFoundException("Location not found with ID: " + locationId);
			}
			throw e;
		}
	}

	public ResponseEntity<String> getInventoryByLocationId(String locationId, Shop shop) {
		try {
			String url = shop.getUrl() + "/admin/api/2023-04/locations/" + locationId + "/inventory_levels.json";
			HttpHeaders headers = createHeaders();
			HttpEntity<Shop> requestEntity = new HttpEntity<>(shop, headers);
			return restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
		} catch (HttpClientErrorException e) {
			if (e.getStatusCode().value() == 404) {
				throw new ResourceNotFoundException("Location not found with ID: " + locationId);
			}
			throw e;
		}
	}

}
