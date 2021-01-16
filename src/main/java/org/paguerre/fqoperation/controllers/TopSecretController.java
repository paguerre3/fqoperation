package org.paguerre.fqoperation.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "${org.paguerre.fqoperation.context-path}")
public class TopSecretController {

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> processTopSecret(RequestEntity<?> requestEntity) {
		ResponseEntity<?> retVal = ResponseEntity.notFound().build();
		return retVal;
	}
}
