package com.shopify.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopify.Models.Shop;
import com.shopify.Service.ShopService;

@RestController
@RequestMapping("/api")
public class ShopController {
	
	@Autowired
	private ShopService shopService;
	
	@GetMapping("/shop")
	public ResponseEntity<ResponseEntity<String>> getShopInfo(@RequestBody Shop shop){
		return ResponseEntity.ok(shopService.getShopInfo(shop));
	}
	
    @GetMapping("/products")
    public ResponseEntity<ResponseEntity<String>> getProducts(@RequestBody Shop shop) {
       return ResponseEntity.ok(shopService.getProducts(shop));
    }

}
