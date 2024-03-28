package com.ecommerce.product.domain.storeproduct.business;

import com.ecommerce.common.model.user.User;
import com.ecommerce.product.common.annotation.Business;
import com.ecommerce.product.common.error.UserErrorCode;
import com.ecommerce.product.common.exception.ApiException;
import com.ecommerce.product.domain.storeproduct.controller.model.StoreProductCreateRequest;
import com.ecommerce.product.domain.storeproduct.controller.model.StoreProductDeleteRequest;
import com.ecommerce.product.domain.storeproduct.controller.model.StoreProductResponse;
import com.ecommerce.product.domain.storeproduct.controller.model.StoreProductUpdateRequest;
import com.ecommerce.product.domain.storeproduct.converter.StoreProductConverter;
import com.ecommerce.product.domain.storeproduct.service.StoreProductService;
import com.ecommerce.product.entity.StoreProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Business
public class StoreProductBusiness {

    private final StoreProductService storeProductService;
    private final StoreProductConverter storeProductConverter;

    public String createStoreProduct(StoreProductCreateRequest request, User user) {
        if (user.getUserId() == null) {
            throw new ApiException(UserErrorCode.USER_SESSTION_NOT_FOUND);
        }

        return storeProductService.createStoreProduct(request);
    }

    public String updateStoreProduct(StoreProductUpdateRequest request, User user) {
        if (user.getUserId() == null) {
            throw new ApiException(UserErrorCode.USER_SESSTION_NOT_FOUND);
        }

        return storeProductService.updateStoreProduct(request);
    }

    public String deleteStoreProduct(StoreProductDeleteRequest request, User user) {
        if (user.getUserId() == null) {
            throw new ApiException(UserErrorCode.USER_SESSTION_NOT_FOUND);
        }

        return storeProductService.deleteStoreProduct(request);
    }

    /**
     * 지정된 상점 ID에 해당하는 상품 리스트를 검색하는 메소드
     *
     * @param storeId 검색할 상품이 속한 상점의 ID
     * @return 검색된 상품 정보를 담은 응답 객체 리스트
     */
    @Cacheable(cacheNames = "StoreProduct", key = "#storeId")
    public List<StoreProductResponse> search(String storeId) {
        List<StoreProductEntity> storeProductList = storeProductService.getStoreProductByStoreId(storeId);

        return storeProductList.stream()
                .map(storeProductConverter::toResponse)
                .collect(Collectors.toList());
    }

    public void resetStoreProduct() {
        storeProductService.resetStoreProduct();
    }
}
