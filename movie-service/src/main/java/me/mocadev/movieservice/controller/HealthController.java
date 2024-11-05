package me.mocadev.movieservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mc.jeon
 * @version 1.0.0
 * @since 2024-11-05
 **/
@RestController
public class HealthController {

	@GetMapping("/api/v1/healthcheck")
	public String healthCheck() {
		return "200 OK";
	}
}
