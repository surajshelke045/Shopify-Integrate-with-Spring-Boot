package com.shopify.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.shopify.Exception.ResourceNotFoundException;
import com.shopify.Models.InventoryLevel;
import com.shopify.Models.Shop;

@Service
public class InventoryLevelService {

	@Value("${shopify.base-url}")
	private String baseUrl;

	@Value("${shopify.access-token}")
	private String accessToken;

	private final RestTemplate restTemplate;

	public InventoryLevelService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	private HttpHeaders createHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		headers.set("X-Shopify-Access-Token", accessToken);
		return headers;
	}
	
	private HttpEntity<Object> getHttpEntity(Object body) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		headers.set("X-Shopify-Access-Token", accessToken);
		headers.set("accept", "application/json");
		return new HttpEntity<>(body, headers);
	}

	public ResponseEntity<String> adjustInventoryLevel(InventoryLevel level) {
			HttpHeaders headers = createHeaders();
			String url = level.getUrl() + "/admin/api/2023-10/inventory_levels/adjust.json";
			HttpEntity<InventoryLevel> request = new HttpEntity<>(level,headers);
			return restTemplate.exchange(url, HttpMethod.POST, request, String.class);
	}
	
	public ResponseEntity<String> connectInventoryLevel(InventoryLevel level) {
		String url =level.getUrl()+ "/admin/api/2023-10/inventory_levels/connect.json";
		HttpHeaders headers = createHeaders();
		HttpEntity<InventoryLevel> request = new HttpEntity<>(level, headers);
		return restTemplate.exchange(url, HttpMethod.POST, request, String.class);
	}
	
	public ResponseEntity<String> setInventoryLevel(InventoryLevel level) {
		String url = level.getUrl() + "/admin/api/2023-10/inventory_levels/set.json";
		HttpHeaders headers = createHeaders();
		HttpEntity<InventoryLevel> request = new HttpEntity<>(level, headers);
		return restTemplate.exchange(url, HttpMethod.POST, request, String.class);
	}

    public ResponseEntity<String> getInventoryLevels(long locationIds,InventoryLevel level) {
try {
    String url = level.getUrl()  + "/admin/api/2023-10/inventory_levels.json?location_ids=" + locationIds;
    HttpHeaders headers = createHeaders();
    HttpEntity<InventoryLevel> requestEntity = new HttpEntity<>(level,headers);
    return restTemplate.exchange(url, HttpMethod.GET,requestEntity, String.class);
	
} catch (ResourceNotFoundException e) {
	throw new ResourceNotFoundException("Resource Not Found With Id : "+locationIds);
}
    }

	public ResponseEntity<String> deleteInventoryLevel(long itemId, long locationId,InventoryLevel level) {
		 String url = level.getUrl() + "/admin/api/2023-10/inventory_levels.json?inventory_item_id="+ itemId +"&location_id=" + locationId;
//	        HttpHeaders headers = createHeaders();
//	        HttpEntity<InventoryLevel> request = new HttpEntity<>(level,headers);
//	        System.out.println(url);
//	        System.out.println(headers);
	        return restTemplate.exchange(url, HttpMethod.DELETE, getHttpEntity(level), String.class);
		
	}
}
