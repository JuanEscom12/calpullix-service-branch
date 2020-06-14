package com.calpullix.service.branch.model;

import java.math.BigDecimal;

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
public class BranchDetailDTO {

	private Integer id;
	
	private Integer value;
	
	private Integer postalCode;
	
	private String header;
	
	private String name;
	
	private String manager;
	
	private String address;
	
	private String contact;
	
	private String state;
	
	private String municipality;
	
	private String region;
	
	private Integer numberEmployes;
	
	private BigDecimal periodPayroll;
	
	private BigDecimal periodIsr;
	
	private BigDecimal monthlyRent;
	
	private BigDecimal periodDeductions;
	
	private BigDecimal periodPurchases;
		
	private BigDecimal periodSales;
	
	private BigDecimal periodLosses;
	
	private BigDecimal periodThefts;
	
	private BigDecimal periodBalance;
	
	private BigDecimal periodServices;
	
	private BigDecimal periodMaintenance;
	
	private BigDecimal periodElectricity;
	
}
