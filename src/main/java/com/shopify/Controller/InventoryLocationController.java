package com.shopify.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopify.Models.Shop;
import com.shopify.Service.InventoryLocation;

@RestController
@RequestMapping("/api")
public class InventoryLocationController {

	@Autowired
	private InventoryLocation inventoryLocation;

	@GetMapping("/locations")
	public ResponseEntity<String> getInventoryLevels(@RequestBody Shop shop) {
		return inventoryLocation.getLocations(shop);
	}
	
	@GetMapping("/locations/{locationId}")
	public ResponseEntity<String> getInventoryLevels(@PathVariable String locationId,@RequestBody Shop shop) {
		return inventoryLocation.getLocationById(locationId,shop);
	}
	
	@GetMapping("/locations/{locationId}/")
	public ResponseEntity<String> getInventoryItemByLocation(@PathVariable String locationId,@RequestBody Shop shop) {
		return inventoryLocation.getInventoryByLocationId(locationId,shop);
	}
	
	@GetMapping("/locations/count")
	public ResponseEntity<String> getLocationCount(@RequestBody Shop shop) {
		return inventoryLocation.getLocationCount(shop);
	}

}
