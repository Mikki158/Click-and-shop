package com.example.seller.controller;

import com.example.seller.dto.BrandDto;
import com.example.seller.dto.CreateRequestSellerDto;
import com.example.seller.dto.RequestSellerDto;
import com.example.seller.dto.UserDto;
import com.example.seller.service.SellerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/api/seller")
public class SellerController {

    SellerService sellerService;

    @PostMapping("/createRequestSeller")
    public ResponseEntity<String> createRequestSeller(
            @RequestBody CreateRequestSellerDto request,
            @RequestHeader("X-User-Id") Long userId) {

        UserDto userInfo = sellerService.verifyAuthentication(userId);

        String response = sellerService.createRequestSeller(request, userInfo);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/requestSeller")
    public ResponseEntity<List<RequestSellerDto>> getAllRequestSeller() {

        return new ResponseEntity<>(sellerService.getAllRequestSeller(), HttpStatus.OK);
    }

    @PostMapping("/approveRequest")
    public ResponseEntity<String> approveRequest(@RequestParam("requestId") Long requestId) {

        return new ResponseEntity<>(sellerService.approveRequest(requestId), HttpStatus.OK);
    }

    @DeleteMapping("/rejectRequest")
    public ResponseEntity<String> rejectRequest(@RequestParam("requestId") Long requestId) {

        return new ResponseEntity<>(sellerService.rejectRequest(requestId), HttpStatus.OK);
    }

    @GetMapping("/brandList")
    public ResponseEntity<List<BrandDto>> getBrandList(
            @RequestHeader("X-User-Id") Long userId) {

        //UserDto userInfo = sellerService.verifyAuthentication(userId);

        List<BrandDto> response = sellerService.getBrandList(userId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/brand/{id}")
    public ResponseEntity<BrandDto> getBrand(@PathVariable("id") Long brandId) {

        BrandDto response = sellerService.getBrand(brandId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
