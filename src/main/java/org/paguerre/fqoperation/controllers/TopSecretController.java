package org.paguerre.fqoperation.controllers;

import org.paguerre.fqoperation.models.SatellitesComposition;
import org.paguerre.fqoperation.models.Transporter;
import org.paguerre.fqoperation.services.TopSecretDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("TopSecretCtrl")
@RequestMapping(path = "${org.paguerre.fqoperation.context-path}")
public class TopSecretController {

	@Autowired
	@Qualifier("TopSecretSvc")
	TopSecretDelegate topSecretSvc;

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> processTopSecret(RequestEntity<SatellitesComposition> requestEntity) {
		ResponseEntity<?> retVal = ResponseEntity.notFound().build();
		Transporter transporter = topSecretSvc.find(requestEntity.getBody());
		if (transporter != null) {
			retVal = ResponseEntity.status(HttpStatus.OK).body(transporter);
		}
		return retVal;
	}
}
