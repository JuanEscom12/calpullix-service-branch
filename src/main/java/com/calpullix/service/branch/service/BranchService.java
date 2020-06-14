package com.calpullix.service.branch.service;

import com.calpullix.service.branch.model.BranchRequest;
import com.calpullix.service.branch.model.BranchResponseDTO;

public interface BranchService {
	
	BranchResponseDTO getBranchList();
	
	BranchResponseDTO getBranchList(BranchRequest request);
	
	BranchResponseDTO getBranchListDummy(BranchRequest request);
	

}
