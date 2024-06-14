package com.shopify.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.shopify.Exception.ResourceNotFoundException;
import com.shopify.Models.Tag;

@Service
public class OrderService {

	@Value("${shopify.base-url}")
	private String baseUrl;

	@Value("${shopify.access-token}")
	private String accessToken;

	@Autowired
	private RestTemplate restTemplate;

	private HttpHeaders createHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		headers.set("X-Shopify-Access-Token", accessToken);
		return headers;
	}

	public ResponseEntity<String> getOrders() {
		HttpHeaders headers = createHeaders();
		HttpEntity<String> entity = new HttpEntity<>(headers);
		return restTemplate.exchange(baseUrl + "/orders.json?status=any", HttpMethod.GET, entity, String.class);
	}
	
	public ResponseEntity<String> getOrderCount() {
		HttpHeaders headers = createHeaders();
		HttpEntity<String> entity = new HttpEntity<>(headers);
		return restTemplate.exchange(baseUrl + "/orders/count.json?status=any", HttpMethod.GET, entity, String.class);
	}


	public ResponseEntity<String> addTags(Tag tag) {
		HttpHeaders headers = createHeaders();
		HttpEntity<Tag> entity = new HttpEntity<>(tag, headers);
		return restTemplate.exchange(baseUrl + "/graphql.json", HttpMethod.POST, entity, String.class);
	}

	public ResponseEntity<String> getOrderById(long orderId) {
		try {
			String url = baseUrl + "/orders/" + orderId + ".json";
			HttpHeaders headers = createHeaders();
			HttpEntity<String> requestEntity = new HttpEntity<>(headers);
			return restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);

		} catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException("Resource Not Found With Id : " + orderId);
		}
	}

}
