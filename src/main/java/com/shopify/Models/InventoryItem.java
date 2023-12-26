package com.shopify.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryItem {
	
	private Long id;
    private String sku;
    private String url;

}
