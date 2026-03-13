package Controller;

import Service.Configuration.ConfigurationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/config")
public class ConfigurationController {

    private final ConfigurationService configurationService;

    public ConfigurationController(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    @PostMapping("/default-salary")
    public ResponseEntity<String> setDefaultSalary(@RequestParam Double salaire) {
        configurationService.setValue("default_salary", salaire.toString());
        return ResponseEntity.ok("Default salary updated");
    }

    @GetMapping("/default-salary")
    public ResponseEntity<Double> getDefaultSalary() {
        Double defaultSalary = configurationService.getValue("default_salary")
                .map(Double::parseDouble)
                .orElse(3000.0); // fallback
        return ResponseEntity.ok(defaultSalary);
    }
}