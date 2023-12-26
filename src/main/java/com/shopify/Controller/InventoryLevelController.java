package com.shopify.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopify.Exception.ApiResponse;
import com.shopify.Models.InventoryAdjustmentResponse;
import com.shopify.Models.InventoryLevel;
import com.shopify.Models.Shop;
import com.shopify.Service.InventoryLevelService;


@RestController
@RequestMapping("/api")
public class InventoryLevelController {
	
	  private final InventoryLevelService inventoryLevelService;

	    @Autowired
	    public InventoryLevelController(InventoryLevelService inventoryLevelService) {
	        this.inventoryLevelService = inventoryLevelService;
	    }

	    @PostMapping("/adjust-inventory")
	    public ResponseEntity<ResponseEntity<String>> adjustInventory(@RequestBody InventoryLevel level) {
			return ResponseEntity.ok(inventoryLevelService.adjustInventoryLevel(level));
	    }
	    
	    @PostMapping("/inventory-level/connect")
	    public ResponseEntity<ResponseEntity<String>> connectInventoryLevel(@RequestBody InventoryLevel level) {
	    	return ResponseEntity.ok(inventoryLevelService.connectInventoryLevel(level));
	    }
	    
	    @PostMapping("/inventory-level/set")
	    public ResponseEntity<ResponseEntity<String>> setInventoryLevel(@RequestBody InventoryLevel level) {
	    	return ResponseEntity.ok(inventoryLevelService.setInventoryLevel(level));
	    }
	    
	    @GetMapping("/adjust-inventory/{locationId}")
	    public ResponseEntity<String> adjustInventory(@PathVariable long locationId,@RequestBody InventoryLevel level) {
	    	System.out.println("Loading..");
	        return inventoryLevelService.getInventoryLevels(locationId,level);
	    }
	    
	    @DeleteMapping("/inventory-level/inventory-item/{itemId}/locations/{locationId}")
	    public ResponseEntity<ApiResponse> deleteInventoryLevel(@PathVariable long itemId,@PathVariable long locationId,@RequestBody InventoryLevel level) {
	        this.inventoryLevelService.deleteInventoryLevel(itemId,locationId,level);
	        return new ResponseEntity<>(new ApiResponse("Inventory Level Deleted Successfully..!",true), HttpStatus.OK);
	    }

}
