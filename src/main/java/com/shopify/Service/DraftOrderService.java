package com.shopify.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.shopify.Exception.ResourceNotFoundException;
import com.shopify.Models.Tag;

@Service
public class DraftOrderService {

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

	public ResponseEntity<String> getDraftOrders() {
		HttpHeaders headers = createHeaders();
		HttpEntity<String> entity = new HttpEntity<>(headers);
		return restTemplate.exchange(baseUrl + "/draft_orders.json", HttpMethod.GET, entity, String.class);
	}

	public ResponseEntity<String> getDraftOrderCount() {
		HttpHeaders headers = createHeaders();
		HttpEntity<String> entity = new HttpEntity<>(headers);
		return restTemplate.exchange(baseUrl + "/draft_orders/count.json", HttpMethod.GET, entity, String.class);
	}

	public ResponseEntity<String> addTags(Tag tag) {
		HttpHeaders headers = createHeaders();
		HttpEntity<Tag> entity = new HttpEntity<>(tag, headers);
		return restTemplate.exchange(baseUrl + "/graphql.json", HttpMethod.POST, entity, String.class);
	}

	public ResponseEntity<String> getDraftOrderById(long draftOrderId) {
		try {
			String url = baseUrl + "/draft_orders/" + draftOrderId + ".json";
			HttpHeaders headers = createHeaders();
			HttpEntity<String> requestEntity = new HttpEntity<>(headers);
			return restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);

		} catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException("Resource Not Found With Id : " + draftOrderId);
		}
	}
	
    private final String url = "https://your-shop-name.myshopify.com/admin/api/2023-04/graphql.json";
    private final String token = "YOUR_SHOPIFY_ACCESS_TOKEN";
	
	public ResponseEntity<String> fetchCompanyLocation(String companyId) {
        final String graphQLQuery = String.format(
            "{ " +
            "  \"query\": \"{ companyLocation(id: \\\"%s\\\") { " +
            "      id " +
            "      name " +
            "      address " +
            "      phone " +
            "  } }\" " +
            "}", companyId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Shopify-Access-Token", token);

        HttpEntity<String> entity = new HttpEntity<>(graphQLQuery, headers);

        ResponseEntity<String> response = restTemplate.exchange(
            url,
            HttpMethod.POST,
            entity,
            String.class
        );
       return response;
    }

}
