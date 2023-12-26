package com.shopify.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.shopify.Exception.ResourceNotFoundException;
import com.shopify.Models.InventoryItem;
import com.shopify.Models.Shop;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;

@Service
public class InventoryService {
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

	private HttpHeaders createHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		headers.set("X-Shopify-Access-Token", accessToken);
		headers.set("accept", "application/json");
		return headers;
	}

	private RestTemplate restTemplate = new RestTemplate();

	public ResponseEntity<String> getInventoryItems(String ids,Shop shop) {
		String url = shop.getUrl() + "/admin/api/2023-04/inventory_items.json?ids=" + ids;
		HttpHeaders headers = createHeaders();
		HttpEntity<Shop> requestEntity = new HttpEntity<>(shop,headers);
		return restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
	}

	public ResponseEntity<String> getInventoryItem(String inventoryItemId,Shop shop) {
		try {
			String url = shop.getUrl()  + "/admin/api/2023-04/inventory_items/" + inventoryItemId + ".json";
			HttpHeaders headers = createHeaders();
			HttpEntity<Shop> requestEntity = new HttpEntity<>(shop,headers);
			System.out.println("Url : "+url);
			return restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
		} catch (HttpClientErrorException e) {
			if (e.getStatusCode().value() == 404) {
				throw new ResourceNotFoundException("Inventory item not found with ID: " + inventoryItemId);
			}
			throw e;
		}
	}
	
	  public InventoryItem updateInventoryItem(Long itemId, InventoryItem updatedItem) {
	        String url = updatedItem.getUrl() +"/admin/api/2023-10/inventory_items/" + itemId + ".json";
	        HttpHeaders headers = createHeaders();
	        HttpEntity<InventoryItem> requestEntity = new HttpEntity<>(updatedItem, headers);
	        ResponseEntity<InventoryItem> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, InventoryItem.class);
	        return response.getBody();
	    }


}
