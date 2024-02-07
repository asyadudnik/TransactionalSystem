package com.optum.payment.system.utils;

import com.google.gson.Gson;
import com.optum.payment.system.entities.User;

import java.util.List;

import static com.optum.payment.system.controllers.UserController.logger;

public class JsonUtils {
    private JsonUtils() {
    }

    public static final Gson gson = new Gson();

    public static String toJson(User entity){
        return gson.toJson(entity);
    }

    public static String toJson(List<User> entities){
        for (User user : entities) {
            if (logger.isDebugEnabled())
                logger.info(toJson(user), "\n\r");
        }
        return gson.toJson(entities);
    }

    public static User fromJson(String json) {
        return gson.fromJson(json, User.class);
    }
}
