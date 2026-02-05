package com.carmarketpro.transaction.mapper;

import com.carmarketpro.transaction.domain.Purchase;
import com.carmarketpro.transaction.dto.PurchaseResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-03T21:20:45+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23.0.1 (Oracle Corporation)"
)
@Component
public class PurchaseMapperImpl implements PurchaseMapper {

    @Override
    public PurchaseResponse toResponse(Purchase purchase) {
        if ( purchase == null ) {
            return null;
        }

        PurchaseResponse.PurchaseResponseBuilder purchaseResponse = PurchaseResponse.builder();

        purchaseResponse.id( purchase.getId() );
        purchaseResponse.userId( purchase.getUserId() );
        purchaseResponse.carId( purchase.getCarId() );
        purchaseResponse.amount( purchase.getAmount() );
        purchaseResponse.status( purchase.getStatus() );
        purchaseResponse.paymentId( purchase.getPaymentId() );
        purchaseResponse.createdAt( purchase.getCreatedAt() );

        return purchaseResponse.build();
    }
}
