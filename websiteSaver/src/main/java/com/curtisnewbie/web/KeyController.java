package com.curtisnewbie.web;

import com.curtisnewbie.services.api.RsaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhuangyongj
 */
@RestController
@RequestMapping("/key")
public class KeyController {

    @Autowired
    private RsaService rsaService;

    @GetMapping("/public")
    public ResponseEntity<String> getPublicKey() {
        return ResponseEntity.ok(rsaService.getPublicKeyStr());
    }
}
