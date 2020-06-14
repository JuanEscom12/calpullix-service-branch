package com.calpullix.service.branch.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.calpullix.service.branch.model.BranchRequest;
import com.calpullix.service.branch.service.BranchService;
import com.calpullix.service.branch.util.AbstractWrapper;
import com.calpullix.service.branch.util.ValidationHandler;

import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class BranchHandler {

	@Value("${app.message-error-location-body}")
	private String messageErrorLocationBody;

	@Autowired
	private BranchService branchService;
	
	@Autowired
	private ValidationHandler validationHandler;


	@Timed(value = "calpullix.service.branch.list.metrics", description = "Retriving branch list")
	public Mono<ServerResponse> getBranchList(ServerRequest serverRequest) {
		log.info(":: Branch List Handler {} ", serverRequest);
		return AbstractWrapper.async(() -> branchService.getBranchList())
				.flatMap(response -> ServerResponse.ok().body(BodyInserters.fromObject(response)));
	}

	
	@Timed(value = "calpullix.service.branch.metrics", description = "Retriving branches ")
	public Mono<ServerResponse> getBranches(ServerRequest serverRequest) {
		log.info(":: Get branches handler {} ", serverRequest);
		return validationHandler.handle(
				input -> input.flatMap(request -> AbstractWrapper.async(() -> branchService.getBranchList(request)))
						.flatMap(response -> ServerResponse.ok().body(BodyInserters.fromObject(response))),
				serverRequest, BranchRequest.class).switchIfEmpty(Mono.error(new Exception(messageErrorLocationBody)));
	}
	
	@Timed(value = "calpullix.service.branch.dummy.metrics", description = "Retriving branches dummy ")
	public Mono<ServerResponse> getBranchesDummy(ServerRequest serverRequest) {
		log.info(":: Get branches dummy handler {} ", serverRequest);
		return validationHandler.handle(
				input -> input.flatMap(request -> AbstractWrapper.async(() -> branchService.getBranchListDummy(request)))
						.flatMap(response -> ServerResponse.ok().body(BodyInserters.fromObject(response))),
				serverRequest, BranchRequest.class).switchIfEmpty(Mono.error(new Exception(messageErrorLocationBody)));
	}
	
	
}
