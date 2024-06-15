package com.api.splitTesting.service;

import com.api.splitTesting.dao.LayoutDao;
import com.api.splitTesting.dao.UserDao;
import com.api.splitTesting.dto.response.UserLayoutRespDto;
import com.api.splitTesting.models.User;
import com.api.splitTesting.utils.Constants;
import com.api.splitTesting.utils.Response;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;


@Service
public class UserLayoutServiceImpl implements UserLayoutService {

    Logger logger = LoggerFactory.getLogger(UserLayoutServiceImpl.class);

    @Autowired
    UserDao userDao;

    @Autowired
    LayoutDao layoutDao;

    @Override
    public Response getUserLayout(HttpServletRequest request, HttpServletResponse response) {
        try {
            logger.info("UserLayoutServiceImpl : getUserLayout");
            User user;
            String userCookieId = getUserCookieId(request);
            if (userCookieId == null){
                user = createNewUser(response);
                logger.info("UserLayoutServiceImpl : getUserLayout : User Created");
            }
            else{
                user = userDao.getUser(userCookieId);
                logger.info("UserLayoutServiceImpl : getUserLayout : User Retrieved {}", user);
                if(user == null)
                    user = createNewUser(response);
            }
            return new Response(Constants.SUCCESS_CODE, Constants.SUCCESS_MESSAGE, new UserLayoutRespDto(user.getLayout()));
        } catch (Exception e) {
            logger.error("UserLayoutServiceImpl : getUserLayout : EXCEPTION : {}", e.getMessage(), e);
            return new Response(Constants.ERROR_CODE, Constants.ERROR_MESSAGE, null);
        }
    }

    private String getNewCookieIdForGuest(HttpServletResponse response) {
        logger.info("UserLayoutServiceImpl : getNewCookieId");
        String cookieId = UUID.randomUUID().toString();
        Cookie newCookie = new Cookie(Constants.COOKIE_NAME, cookieId);
        newCookie.setPath("/");
        newCookie.setMaxAge((int) Constants.COOKIE_EXPIRATION.getSeconds());
        newCookie.setHttpOnly(true);
        response.addCookie(newCookie);
        return cookieId;
    }

    private String getUserCookieId(HttpServletRequest request) {
        logger.info("UserLayoutServiceImpl : getUserIdFromCookie");
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (Constants.COOKIE_NAME.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    private String determineLayoutForUser(String cookieId) {
        logger.info("UserLayoutServiceImpl : determineLayoutForUser");
        Set<String> layouts = layoutDao.getLayouts();
        logger.info("got maal {}", layouts);
        if (layouts == null || layouts.isEmpty()) {
            throw new RuntimeException("No layouts configured in Redis.");
        }

        List<String> layoutList = new ArrayList<>(layouts);

        logger.info("determineLayoutForUser : layoutList: {}", layoutList);

        int layoutCount = layoutList.size();
        int hash = Math.abs(cookieId.hashCode());
        int index = hash % layoutCount;

        return  String.valueOf(layoutList.get(index));
    }

    private User createNewUser(HttpServletResponse response){
        String userCookieId = getNewCookieIdForGuest(response);
        User user = new User(userCookieId, determineLayoutForUser(userCookieId));
        userDao.saveGuestUser(user);
        logger.info("UserLayoutServiceImpl : createNewUser : User Saved {}", user);
        return user;
    }
}
