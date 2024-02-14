package com.ecommerce.storeadmin.domain.userorderproduct.service;

import com.ecommerce.db.userorderproduct.UserOrderProductEntity;
import com.ecommerce.db.userorderproduct.UserOrderProductRepository;
import com.ecommerce.db.userorderproduct.enums.UserOrderProductStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserOrderProductService {

    private final UserOrderProductRepository userOrderProductRepository;

    public List<UserOrderProductEntity> getUserOrderProductList(Long userOrderId){

        return userOrderProductRepository.findAllByUserOrderIdAndStatusOrderByIdDesc(userOrderId, UserOrderProductStatus.REGISTERED);
    }
}
