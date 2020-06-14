package com.calpullix.service.branch.conf;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.calpullix.service.branch.handler.BranchHandler;

@Configuration
public class RoutesConf {

	@Value("${app.path-best-branch}")
	private String pathBestBranch;
	
	@Value("${app.path-get-branch}")
	private String pathGetBranch;
	
	@Value("${app.path-get-branch-list}")
	private String pathGetBranchList;
	
	@Value("${app.path-get-branch-dummy}")
	private String pathGetBranchDummy;
	
	
	@Bean
	public RouterFunction<ServerResponse> routesLogin(BranchHandler branchHandler) {
		return route(POST(pathGetBranch), branchHandler::getBranches)
				.and(route(GET(pathGetBranchList), branchHandler::getBranchList))
						.and(route(POST(pathGetBranchDummy), branchHandler::getBranchesDummy));
	}
	
}
