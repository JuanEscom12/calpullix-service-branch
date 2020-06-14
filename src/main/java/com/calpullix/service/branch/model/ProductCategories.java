package com.calpullix.service.branch.model;

import java.util.stream.Stream;

import lombok.Getter;

@Getter
public enum ProductCategories {
	
	DENTAL_CLEANING(1, "Limpieza dental"), SOAP_SHAMPOO(2, "Jabón y shampoo"), PERSONAL_CARE(3, "Cuidado Personal"),
	CREAMS(4, "Cremas"), DERMATOLOGICAL(5 , "Dermatológico"), GROCERIES(6, "Abarrotes");
	
	private Integer id;
	
	private String description;
	
	private ProductCategories(int id, String description) {
		this.id = id;
		this.description = description;
	}
	
	public static ProductCategories of(int id) {
		return Stream.of(ProductCategories.values())
			.filter(p -> p.getId() == id)
			.findFirst()
			.orElseThrow(IllegalArgumentException::new);
	}


}
