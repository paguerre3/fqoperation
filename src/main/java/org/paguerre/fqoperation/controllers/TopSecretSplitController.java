package org.paguerre.fqoperation.controllers;

import org.paguerre.fqoperation.models.Satellite;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "${org.paguerre.fqoperation.split.context-path}")
public class TopSecretSplitController {

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, value = "/{name}")
	public ResponseEntity<?> processTopSecret(RequestEntity<Satellite> requestEntity,
			@PathVariable(value = "name") String name) {
		ResponseEntity<?> retVal = ResponseEntity.notFound().build();
		if (requestEntity != null && requestEntity.getBody() != null) {
			Satellite s = requestEntity.getBody();
			s.setName(name);
			retVal = ResponseEntity.accepted().build();
		}
		return retVal;
	}

	/**
	 * Intends to obtain top secret information with stored data.
	 * 
	 * @return ResponseEntity<?> including transporter information
	 */
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> processTopSecretStored() {
		ResponseEntity<?> retVal = ResponseEntity.notFound().build();
		return retVal;
	}
}
