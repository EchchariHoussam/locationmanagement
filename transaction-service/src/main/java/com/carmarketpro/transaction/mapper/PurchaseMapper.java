package com.carmarketpro.transaction.mapper;

import com.carmarketpro.transaction.domain.Purchase;
import com.carmarketpro.transaction.dto.PurchaseResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PurchaseMapper {

    PurchaseResponse toResponse(Purchase purchase);
}
