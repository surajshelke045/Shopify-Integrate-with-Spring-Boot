package com.shopify.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.shopify.Models.InventoryItem;
import com.shopify.Models.Shop;
import com.shopify.Service.InventoryService;

@RestController
@RequestMapping("/api")
public class InventoryController {

	@Autowired
    private InventoryService inventoryService;

    @GetMapping("/inventory_items")
    public ResponseEntity<String> getInventoryItems(@RequestParam String ids,@RequestBody Shop shop) {
        return inventoryService.getInventoryItems(ids,shop);
    }
    

    @GetMapping("/inventory_items/{id}")
    public ResponseEntity<String> getInventoryItem(@PathVariable String id,@RequestBody Shop shop) {
        return inventoryService.getInventoryItem(id,shop);
    }
    
    @PutMapping("/inventory_items/update/{itemId}")
    public ResponseEntity<InventoryItem> updateInventoryItem(	@PathVariable("itemId") Long itemId,@RequestBody InventoryItem inventoryItem) {
        InventoryItem updatedItem = inventoryService.updateInventoryItem(itemId, inventoryItem);
        return ResponseEntity.ok(updatedItem);
    }

//    @PutMapping("/inventory_items/{id}")
//    public String updateInventoryItem(@PathVariable Long id, @RequestBody String requestBody) {
//        return inventoryService.updateInventoryItem(id, requestBody);
//    }
}
