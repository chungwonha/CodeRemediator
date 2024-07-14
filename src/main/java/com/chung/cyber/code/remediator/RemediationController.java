package com.chung.cyber.code.remediator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/remediate")
public class RemediationController {

    @Autowired
    private RemediationService remediationService;

    @PostMapping
    public String remediateCode(@RequestParam String filePath, @RequestParam int startLine, @RequestParam int endLine, @RequestParam String instruction) {
        return remediationService.remediate(filePath, instruction);
    }
}

