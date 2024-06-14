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
import com.shopify.Service.InventoryLocation;
import com.shopify.Service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@GetMapping("/")
	public ResponseEntity<String> getorders() {
		return orderService.getOrders();
	}
	
	@GetMapping("/count")
	public ResponseEntity<String> getOrderCount() {
		return orderService.getOrderCount();
	}
	
	@GetMapping("/{orderId}")
	public ResponseEntity<String> getOrderById(@PathVariable long orderId) {
		return orderService.getOrderById(orderId);
	}
	
    @PostMapping("/addTags")
    public ResponseEntity<String> addTags(@RequestBody Tag tag) {
        ResponseEntity<String> response = orderService.addTags(tag);

        if (response.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity.ok(response.getBody());
        } else {
            return ResponseEntity.status(response.getStatusCode()).body("Error calling Shopify API");
        }
    }

}
