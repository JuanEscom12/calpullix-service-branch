package com.calpullix.service.branch.model;

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
public class ProductDetailRequest {

	private Integer id;
	
	private Integer parameter;
	
	private String name;
	
}
