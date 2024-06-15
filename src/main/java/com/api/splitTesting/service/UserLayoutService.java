package com.api.splitTesting.service;

import com.api.splitTesting.utils.Response;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface UserLayoutService {

    Response getUserLayout(HttpServletRequest request, HttpServletResponse response);
}
