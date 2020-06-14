package com.calpullix.service.branch.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.calpullix.service.branch.model.Branch;
import com.calpullix.service.branch.model.BranchDetailDTO;
import com.calpullix.service.branch.model.BranchRequest;
import com.calpullix.service.branch.model.BranchResponseDTO;
import com.calpullix.service.branch.model.MarkerDetailDTO;
import com.calpullix.service.branch.model.State;
import com.calpullix.service.branch.repository.BranchRepository;
import com.calpullix.service.branch.repository.ProcedureInvoker;
import com.calpullix.service.branch.service.BranchService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BranchServiceImpl implements BranchService {
	
	private static final String CDMX_LONGITUDE = "-99.1276627";
	
	private static final String CDMX_LATITUDE = "19.4284706";
	
	private static final String QUERETARO_LONGITUDE = "-100.3880600";
	
	private static final String QUERETARO_LATITUDE = "20.5880600";

	@Autowired
	private BranchRepository branchRepository;
		
	@Autowired
	private ProcedureInvoker procedureInvoker;
	
	private SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
	
	@Value("${app.pagination-size}")
	private Integer paginationSize;

	@Value("${app.procedure-name}")
	private String procedureName;
	
	@Override
	public BranchResponseDTO getBranchList(BranchRequest request) {
		log.info(":: Branch list service {} ", request);
		final List<BranchDetailDTO> detail = new ArrayList<>();
		final List<MarkerDetailDTO> markers = new ArrayList<>();
		final List<byte[]> pictures = new ArrayList<>();
		final BranchResponseDTO result = new BranchResponseDTO();
		Optional<Branch> branch;
		BranchDetailDTO itemDetail;
		Pageable pagination = PageRequest.of(request.getPage() - 1, paginationSize);
		Page<Branch> branches;
		MarkerDetailDTO marker;
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		int currentMonth = Calendar.getInstance().get(Calendar.MONTH);

		String startDate = null;
		String endDate = null;
		Calendar date = Calendar.getInstance();
		if (BooleanUtils.negate(request.getYear() == null) && 
				request.getMonth() == null) {
			date.set(Calendar.YEAR, request.getYear());
			date.set(Calendar.MONTH, date.getMinimum(Calendar.MONTH));
			date.set(Calendar.DATE, date.getMinimum(Calendar.DATE));
			startDate = formatDate.format(date.getTime());
			if (request.getYear() == currentYear) {
				date = Calendar.getInstance();
			} else {
				date.set(Calendar.MONTH, date.getMaximum(Calendar.MONTH));
				date.set(Calendar.DATE, date.getMaximum(Calendar.DATE));
			}
			endDate = formatDate.format(date.getTime());
		} else if (BooleanUtils.negate(request.getYear() == null) && 
					BooleanUtils.negate(request.getMonth() == null)) {
			if (request.getYear() == currentYear && 
					currentMonth + 1 <= request.getMonth()) {
				date.set(Calendar.DATE, date.getMinimum(Calendar.DATE));
				startDate = formatDate.format(date.getTime());
				date = Calendar.getInstance();
			} else {
				date.set(Calendar.YEAR, request.getYear());
				date.set(Calendar.MONTH, request.getMonth() - 1);
				date.set(Calendar.DATE, date.getMinimum(Calendar.DATE));
				startDate = formatDate.format(date.getTime());
				date.set(Calendar.DATE, date.getMaximum(Calendar.DATE));
			}
			endDate = formatDate.format(date.getTime());
		} else if (request.getYear() == null) {
			endDate = formatDate.format(date.getTime());
			date.set(Calendar.MONTH, date.getMinimum(Calendar.MONTH));
			date.set(Calendar.DATE, date.getMinimum(Calendar.DATE));
			startDate = formatDate.format(date.getTime());
		}
		log.info(":: Date Filters {} {} ", startDate, endDate);
			
		if (request.getId() == null) {
			branches = branchRepository.findAllByOrderByStatevalue(pagination);
			if (branches.hasContent()) {
				
				if (branches.getContent().get(0).getState().equals(State.Queretaro)) {
					result.setLatitudeRegion(QUERETARO_LATITUDE);
					result.setLongitudeRegion(QUERETARO_LONGITUDE);
				} else {
					result.setLatitudeRegion(CDMX_LATITUDE);
					result.setLongitudeRegion(CDMX_LONGITUDE);
				}
				for (final Branch item: branches.getContent()) {
					itemDetail = new BranchDetailDTO();
					itemDetail.setPostalCode(item.getPostalcode());
					itemDetail.setHeader(item.getName());
					itemDetail.setName(item.getName());
					itemDetail.setManager(item.getManager().getName());
					itemDetail.setAddress(item.getAddress());
					itemDetail.setContact(item.getContact());
					itemDetail.setState(item.getState().getDescription());
					itemDetail.setMunicipality(item.getMunicipality());
					itemDetail.setRegion(item.getRegion().getDescription());
					itemDetail.setMonthlyRent(item.getMonthlyrent());
					pictures.add(item.getImage());
					marker = new MarkerDetailDTO();
					marker.setAddress(item.getAddress());
					marker.setLatituide(item.getLatitude());
					marker.setLongitude(item.getLongitude());
					marker.setName(item.getName());
					markers.add(marker);
					detail.add(itemDetail);
					procedureInvoker.executeProcedureBranchInformation(itemDetail,
										item.getId(), procedureName, startDate, endDate);
				}
				result.setBranches(detail);
				result.setMarkers(markers);
				result.setItemCount(branchRepository.getBranchCount());
			}
		} else {
			branch = branchRepository.findById(request.getId());
			if (branch.isPresent()) {
				if (branch.get().getState().equals(State.Queretaro)) {
					result.setLatitudeRegion(QUERETARO_LATITUDE);
					result.setLongitudeRegion(QUERETARO_LONGITUDE);
				} else {
					result.setLatitudeRegion(CDMX_LATITUDE);
					result.setLongitudeRegion(CDMX_LONGITUDE);
				}
				itemDetail = new BranchDetailDTO();
				itemDetail.setPostalCode(branch.get().getPostalcode());
				itemDetail.setHeader(branch.get().getName());
				itemDetail.setName(branch.get().getName());
				itemDetail.setManager(branch.get().getManager().getName());
				itemDetail.setAddress(branch.get().getAddress());
				itemDetail.setContact(branch.get().getContact());
				itemDetail.setState(branch.get().getState().getDescription());
				itemDetail.setMunicipality(branch.get().getMunicipality());
				itemDetail.setRegion(branch.get().getRegion().getDescription());
				itemDetail.setMonthlyRent(branch.get().getMonthlyrent());
				pictures.add(branch.get().getImage());
				marker = new MarkerDetailDTO();
				marker.setAddress(branch.get().getAddress());
				marker.setLatituide(branch.get().getLatitude());
				marker.setLongitude(branch.get().getLongitude());
				marker.setName(branch.get().getName());
				markers.add(marker);
				detail.add(itemDetail);
				result.setBranches(detail);
				result.setMarkers(markers);
				result.setItemCount(1);
				procedureInvoker.executeProcedureBranchInformation(itemDetail,
						branch.get().getId(), procedureName, startDate, endDate);
			}
		}
		result.setPictures(pictures);
		return result;
	}

	@Override
	public BranchResponseDTO getBranchList() {
		log.info(":: Branch list name service ");
		final List<Branch> branchList = branchRepository.findAll();
		final BranchResponseDTO result = new BranchResponseDTO();
		final List<BranchDetailDTO> detail = new ArrayList<>();
		BranchDetailDTO item;
		for (final Branch branch: branchList) {
			item = new BranchDetailDTO();
			item.setId(branch.getId());
			item.setValue(branch.getId());	       
			item.setName(branch.getName());
			detail.add(item);
		}
		result.setBranch(detail);
		return result;
	}

	@Override
	public BranchResponseDTO getBranchListDummy(BranchRequest request) {
		Pageable pagination = PageRequest.of(0, paginationSize);
		final Page<Branch> branches = branchRepository.findAll(pagination);
		
		final List<BranchDetailDTO> detail = new ArrayList<>();
		final List<byte[]> pictures = new ArrayList<>();
		final BranchResponseDTO result = new BranchResponseDTO();
		
		BranchDetailDTO itemDetail;
		for (final Branch item : branches.getContent()) {
			itemDetail = new BranchDetailDTO();
			itemDetail.setAddress("Tonatico 264 Las Fuentes");
			itemDetail.setManager("Oscar Martinez Chaides");
			itemDetail.setContact("5545789052");
			itemDetail.setName("Sucursal AAAAA ");
			pictures.add(item.getImage());
			detail.add(itemDetail);
		}
		result.setPictures(pictures);
		result.setBranches(detail);	
		return result;
	}

}
