package com.ecommerce.api.domain.store.controller;

import com.ecommerce.api.common.api.Api;
import com.ecommerce.api.domain.store.business.StoreBusiness;
import com.ecommerce.api.domain.store.controller.model.StoreResponse;
import com.ecommerce.db.store.enums.StoreCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/store")
public class StoreController {

    private final StoreBusiness storeBusiness;

    @GetMapping("/search")
    public Api<List<StoreResponse>> search(
            @RequestBody(required = false)
            StoreCategory category
    ) {
        List<StoreResponse> response = storeBusiness.searchCategory(category);
        return Api.ok(response);
    }
}
