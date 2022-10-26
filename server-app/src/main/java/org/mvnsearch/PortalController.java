package org.mvnsearch;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class PortalController {

    @Value("${admin}")
    private String admin;

    @GetMapping("/")
    public String index() {
        return "Hello " + admin + "!";
    }

}
