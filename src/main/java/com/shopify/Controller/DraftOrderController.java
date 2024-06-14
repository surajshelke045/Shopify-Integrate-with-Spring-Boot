package com.shopify.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shopify.Models.Shop;
import com.shopify.Models.Tag;
import com.shopify.Service.DraftOrderService;
import com.shopify.Service.InventoryLocation;
import com.shopify.Service.OrderService;

@RestController
@RequestMapping("/api/draftOrders")
public class DraftOrderController {

	@Autowired
	private DraftOrderService draftOrderService;

	@GetMapping("/")
	public ResponseEntity<String> geDraftOrders() {
		return draftOrderService.getDraftOrders();
	}
	
	@GetMapping("/count")
	public ResponseEntity<String> geDraftOrderCount() {
		return draftOrderService.getDraftOrderCount();
	}
	
	@GetMapping("/{orderId}")
	public ResponseEntity<String> getOrderById(@PathVariable long orderId) {
		return draftOrderService.getDraftOrderById(orderId);
	}
	
    @PostMapping("/addTags")
    public ResponseEntity<String> addTags(@RequestBody Tag tag) {
        ResponseEntity<String> response = draftOrderService.addTags(tag);

        if (response.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity.ok(response.getBody());
        } else {
            return ResponseEntity.status(response.getStatusCode()).body("Error calling Shopify API");
        }
    }
    
    @GetMapping("/companyLocation/{companyId}")
    public ResponseEntity<ResponseEntity<String>> getCompanyLocation(@PathVariable String companyId) {
        ResponseEntity<String> response = draftOrderService.fetchCompanyLocation(companyId);
        return ResponseEntity.ok(response);
    }

}
