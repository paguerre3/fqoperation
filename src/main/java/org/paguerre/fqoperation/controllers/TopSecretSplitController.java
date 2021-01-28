package org.paguerre.fqoperation.controllers;

import java.util.HashMap;
import java.util.Map;

import org.paguerre.fqoperation.models.Satellite;
import org.paguerre.fqoperation.models.Transporter;
import org.paguerre.fqoperation.services.SpacecraftResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("SplitCtrl")
@RequestMapping(path = "${org.paguerre.fqoperation.split.context-path}")
public class TopSecretSplitController {

	@Autowired
	@Qualifier("SplitSvc")
	SpacecraftResolver topSecretSplitSvc;

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, value = "/{name}")
	public ResponseEntity<?> saveTopSecretSplit(RequestEntity<Satellite> requestEntity,
			@PathVariable(value = "name") String name) {
		ResponseEntity<?> retVal = ResponseEntity.notFound().build();
		if (requestEntity != null) {
			Satellite s = requestEntity.getBody();
			s.setName(name);
			if (topSecretSplitSvc.store(s)) {
				retVal = ResponseEntity.accepted().build();
			}
		}
		return retVal;
	}

	/**
	 * Intends to obtain top secret information with stored data.
	 * 
	 * @return ResponseEntity<?> including transporter information
	 */
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> processTopSecretSplit() {
		ResponseEntity<?> retVal = ResponseEntity.notFound().build();
		Transporter transporter = topSecretSplitSvc.find();
		if (transporter != null) {
			retVal = ResponseEntity.status(HttpStatus.OK).body(transporter);
		} else {
			Map<String, String> error = new HashMap<>();
			error.put("error", "unable to find spacecraft as there isn't enough information");
			retVal = ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
		}
		return retVal;
	}
}
