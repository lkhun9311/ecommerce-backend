package com.ecommerce.membership.domain.token.controller;

import com.ecommerce.membership.domain.token.business.TokenBusiness;
import com.ecommerce.membership.domain.token.controller.model.TokenValidateRequest;
import com.ecommerce.membership.domain.token.controller.model.TokenValidateResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/validation-api/token")
public class TokenApiController {

    private final TokenBusiness tokenBusiness;

    @PostMapping("/validation")
    public TokenValidateResponse tokenValidate(@RequestBody TokenValidateRequest tokenValidateRequest) {
        log.info("[jwt token validation] TokenValidateRequest : {}", tokenValidateRequest);
        String accessToken = tokenValidateRequest.getTokenValidateDto().getToken();
        return tokenBusiness.validateAccessToken(accessToken);
    }
}
