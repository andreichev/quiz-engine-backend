package com.university.itis.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class MyErrorController implements ErrorController {

    private static final String PATH = "/error";

    @Value("${debug}")
    private boolean debug;

    private final ErrorAttributes errorAttributes;

    public MyErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping(value = PATH)
    @ResponseBody
    ResponseEntity<Map> error(WebRequest request, HttpServletResponse response) {
        return ResponseEntity
                .status(response.getStatus())
                .body(getErrorAttributes(request, debug));
    }

    @Override
    public String getErrorPath() {
        return null;
    }

    private Map<String, Object> getErrorAttributes(WebRequest request, boolean includeStackTrace) {
        return errorAttributes.getErrorAttributes(request, includeStackTrace);
    }
}
