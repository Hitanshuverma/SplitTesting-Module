package com.api.splitTesting.controller;

import com.api.splitTesting.service.UserLayoutService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

@RequestMapping("/user")
public class UserLayoutController {

    Logger logger = LoggerFactory.getLogger(UserLayoutController.class);

    @Autowired
    UserLayoutService userLayoutService;

    @GetMapping("/getLayout")
    public ResponseEntity<?> getLayout(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Inside UserLayoutController : ");
        return new ResponseEntity<>(userLayoutService.getUserLayout(request, response), HttpStatus.OK);
    }
}
