package com.example.product.controller;

import com.example.product.dto.SupplyRequest;
import com.example.product.service.SupplyService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/supply")
public class SupplyController {

    private SupplyService supplyService;

    @PostMapping
    public ResponseEntity<Void> supplyProduct(@RequestBody SupplyRequest request) {
        supplyService.supplyProduct(request.getProductId(), request.getWarehouseId(), request.getQuantity());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Integer> getQuantityProduct(@PathVariable("id") Long productId) {
        int quantity = supplyService.getQuantityProduct(productId);

        return ResponseEntity.ok(quantity);
    }




}
