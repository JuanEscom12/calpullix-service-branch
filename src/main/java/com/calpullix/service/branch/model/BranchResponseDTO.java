package com.calpullix.service.branch.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BranchResponseDTO {

	private String latitudeRegion;
	
	private String longitudeRegion; 
	
	private Integer itemCount;
	
	private List<MarkerDetailDTO> markers;
	
	private List<BranchDetailDTO> branches;
	
	private List<BranchDetailDTO> branch;
	
	private List<byte[]> pictures;
	
}
