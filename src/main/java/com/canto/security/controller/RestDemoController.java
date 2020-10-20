package com.canto.security.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/security")
public class RestDemoController {

	@GetMapping("/")
	public ResponseEntity<String> home() {
		return new ResponseEntity<String>("Demo security", HttpStatus.OK);
	}

	@GetMapping("/user")
	public ResponseEntity<String> user() {
		return new ResponseEntity<String>("Welcome user", HttpStatus.OK);
	}

	@GetMapping("/admin")
	public ResponseEntity<String> admin() {
		return new ResponseEntity<String>("Welcome admin", HttpStatus.OK);
	}
}
