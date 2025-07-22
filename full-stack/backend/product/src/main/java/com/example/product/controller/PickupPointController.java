package com.example.product.controller;

import com.example.product.dto.PickupPointDto;
import com.example.product.entity.PickupPoint;
import com.example.product.service.PickupPointService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/pickup_point")
public class PickupPointController {

    PickupPointService pickupPointService;

    @PostMapping
    public ResponseEntity<PickupPointDto> createPckupPoint(@RequestBody PickupPointDto pickupPoint) {

        return new ResponseEntity<>(pickupPointService.createPickupPoint(pickupPoint), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PickupPointDto>> getPickupPoints() {

        return new ResponseEntity<>(pickupPointService.getPickupPoints(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PickupPointDto> getPickupPoint(@PathVariable("id") Long pickupPoindId) {

        return new ResponseEntity<>(pickupPointService.getPickupPoint(pickupPoindId), HttpStatus.OK);
    }
}
