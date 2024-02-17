package org.example.trading_demo.controller;

import lombok.AllArgsConstructor;
import org.example.trading_demo.model.Security;
import org.example.trading_demo.repository.SecurityRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/security")
@AllArgsConstructor
public class SecurityController {
    private final SecurityRepository securityRepository;

    @GetMapping
    public List<Security> findAll() {
        return securityRepository.findAll();
    }

    @GetMapping("/{name}")
    public Security findByName(@PathVariable String name) {
        return securityRepository.findByName(name);
    }

    @PostMapping("save")
    public Security save(@RequestBody Security security) {
        return securityRepository.save(security);
    }

    @DeleteMapping("/delete/{name}")
    public ResponseEntity<?> delete(@PathVariable String name) {
        var security = securityRepository.findByName(name);
        if (security != null) {
            securityRepository.delete(security);
        }
        return ResponseEntity.noContent().build();
    }
}
