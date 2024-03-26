package com.ecommerce.membership.domain.user.service;

import com.ecommerce.membership.domain.user.controller.model.UserCreateRequest;
import com.ecommerce.membership.domain.user.controller.model.UserDeleteRequest;
import com.ecommerce.membership.domain.user.controller.model.UserUpdateRequest;
import com.ecommerce.common.model.user.User;
import com.ecommerce.membership.entity.UserEntity;


public interface UserService {
    String createUser(UserCreateRequest request);
    String updateUser(UserUpdateRequest request);
    String deleteUser(UserDeleteRequest request);
    UserEntity login(String email, String password);
    Boolean doubleCheckEmail(String email);
    UserEntity getUserWithThrow(String email, String password);
    UserEntity me(User user);
    void resetUser();
}
