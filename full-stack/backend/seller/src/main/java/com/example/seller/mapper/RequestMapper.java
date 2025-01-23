package com.example.seller.mapper;

import com.example.seller.dto.RequestSellerDto;
import com.example.seller.entity.CreateRequestSeller;
import org.modelmapper.internal.bytebuddy.matcher.ModifierMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.modelmapper.ModelMapper;

@Component
public class RequestMapper extends AbstractMapper<CreateRequestSeller, RequestSellerDto> {

    public RequestMapper() {
        super(CreateRequestSeller.class, RequestSellerDto.class);
    }
}
