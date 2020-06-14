package com.calpullix.service.branch.repository;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.calpullix.service.branch.model.BranchDetailDTO;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class ProcedureInvoker {

	private final EntityManager entityManager;

	@Autowired
	public ProcedureInvoker(final EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	
	public void executeProcedureBranchInformation(
			BranchDetailDTO branch,
			Integer idBranch, 
			String nameProcedure, 
			String initDate, 
			String endDate) {
    	log.info(":: Executing Procedure {} ", nameProcedure);
		final StoredProcedureQuery storedProcedureQuery = entityManager.
				createStoredProcedureQuery(nameProcedure);
        storedProcedureQuery.registerStoredProcedureParameter("id_branch", Integer.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter("startDate", String.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter("endDate", String.class, ParameterMode.IN);   
        storedProcedureQuery.registerStoredProcedureParameter("numberEmployee", Integer.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("periodPayroll", BigDecimal.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("periodIsr", BigDecimal.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("periodDeductions", BigDecimal.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("periodPurchases", BigDecimal.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("periodSales", BigDecimal.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("periodLosses", BigDecimal.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("periodThefts", BigDecimal.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("periodBalance", BigDecimal.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("periodServices", BigDecimal.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("periodMaintenance", BigDecimal.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("periodElectricity", BigDecimal.class, ParameterMode.INOUT);
        storedProcedureQuery.setParameter("id_branch", idBranch);
        storedProcedureQuery.setParameter("startDate", initDate);
        storedProcedureQuery.setParameter("endDate", endDate);      
        storedProcedureQuery.setParameter("numberEmployee", 0);
        storedProcedureQuery.setParameter("periodPayroll", BigDecimal.ZERO);
        storedProcedureQuery.setParameter("periodIsr", BigDecimal.ZERO);
        storedProcedureQuery.setParameter("periodDeductions", BigDecimal.ZERO);
        storedProcedureQuery.setParameter("periodPurchases", BigDecimal.ZERO);
        storedProcedureQuery.setParameter("periodSales", BigDecimal.ZERO);
        storedProcedureQuery.setParameter("periodLosses", BigDecimal.ZERO);
        storedProcedureQuery.setParameter("periodThefts", BigDecimal.ZERO);
        storedProcedureQuery.setParameter("periodBalance", BigDecimal.ZERO);
        storedProcedureQuery.setParameter("periodServices", BigDecimal.ZERO);
        storedProcedureQuery.setParameter("periodMaintenance", BigDecimal.ZERO);
        storedProcedureQuery.setParameter("periodElectricity", BigDecimal.ZERO);
        storedProcedureQuery.execute();

        final Integer numberEmployee = (Integer) storedProcedureQuery.getOutputParameterValue("numberEmployee");
        final BigDecimal  periodPayroll = (BigDecimal) storedProcedureQuery.getOutputParameterValue("periodPayroll");
        final BigDecimal  periodIsr = (BigDecimal) storedProcedureQuery.getOutputParameterValue("periodIsr");
        final BigDecimal  periodDeductions = (BigDecimal) storedProcedureQuery.getOutputParameterValue("periodDeductions");  
        final BigDecimal  periodPurchases = (BigDecimal) storedProcedureQuery.getOutputParameterValue("periodPurchases");      
        final BigDecimal  periodSales = (BigDecimal) storedProcedureQuery.getOutputParameterValue("periodSales");    
        final BigDecimal  periodLosses = (BigDecimal) storedProcedureQuery.getOutputParameterValue("periodLosses");       
        final BigDecimal  periodThefts = (BigDecimal) storedProcedureQuery.getOutputParameterValue("periodThefts");      
        final BigDecimal  periodBalance = (BigDecimal) storedProcedureQuery.getOutputParameterValue("periodBalance");      
        final BigDecimal  periodServices = (BigDecimal) storedProcedureQuery.getOutputParameterValue("periodServices");     
        final BigDecimal  periodMaintenance = (BigDecimal) storedProcedureQuery.getOutputParameterValue("periodMaintenance");      
        final BigDecimal  periodElectricity = (BigDecimal) storedProcedureQuery.getOutputParameterValue("periodElectricity");
        
        branch.setNumberEmployes(numberEmployee);
        branch.setPeriodPayroll(periodPayroll == null ? BigDecimal.ZERO : periodPayroll);
        branch.setPeriodIsr(periodIsr == null ? BigDecimal.ZERO : periodIsr);
        branch.setPeriodDeductions(periodDeductions == null ? BigDecimal.ZERO : periodDeductions);
        branch.setPeriodPurchases(periodPurchases == null ? BigDecimal.ZERO : periodPurchases);
        branch.setPeriodSales(periodSales == null ? BigDecimal.ZERO : periodSales);
        branch.setPeriodLosses(periodLosses == null ? BigDecimal.ZERO : periodLosses);
        branch.setPeriodThefts(periodThefts == null ? BigDecimal.ZERO : periodThefts);
        branch.setPeriodBalance(periodBalance == null ? BigDecimal.ZERO : periodBalance);
        branch.setPeriodServices(periodServices == null ? BigDecimal.ZERO : periodServices);
        branch.setPeriodMaintenance(periodMaintenance == null ? BigDecimal.ZERO : periodMaintenance);
        branch.setPeriodElectricity(periodElectricity == null ? BigDecimal.ZERO : periodElectricity);
    }


}
