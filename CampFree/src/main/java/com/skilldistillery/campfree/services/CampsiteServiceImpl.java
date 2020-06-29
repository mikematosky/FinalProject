package com.skilldistillery.campfree.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.skilldistillery.campfree.entities.Campsite;
import com.skilldistillery.campfree.entities.Person;
import com.skilldistillery.campfree.repositories.CampsiteRepository;
import com.skilldistillery.campfree.repositories.PersonRepository;


@Service
public class CampsiteServiceImpl implements CampsiteService {

	@Autowired
	private CampsiteRepository campRepo;
	
	@Autowired
	private PersonRepository personRepo;
	
	@Override
	public Campsite findCampsiteById(String username, int campsiteId) {
		Optional<Campsite> campsiteOpt = campRepo.findById(campsiteId);
		Campsite campsite= null;
		if(campsiteOpt.isPresent()) {
			campsite= campsiteOpt.get();
		}
		return campsite;
	}

	@Override
	public List<Campsite> findAllCampsites() {
		List<Campsite> campsites= campRepo.findAll();
		return campsites;
	}

	@Override
	public Campsite createCampsite(String username, Campsite campsite) {
		return campRepo.saveAndFlush(campsite);
	}

	@Override
	public Campsite updateCampsite(String username, Campsite campsite, int campsiteId) {
		Optional<Campsite> campsiteOpt= campRepo.findById(campsiteId);
		Campsite newerCampsite= null;
		if(campsiteOpt.isPresent()) {
			newerCampsite= campsiteOpt.get();
			newerCampsite.setCreationTime(LocalDateTime.now());
			newerCampsite.setFeatures(campsite.getFeatures());
			newerCampsite.setLatitude(campsite.getLatitude());
			newerCampsite.setLongitude(campsite.getLongitude());
			newerCampsite.setName(campsite.getName());
			newerCampsite.setCreator(campsite.getCreator());
			newerCampsite.setPictureUrl(campsite.getPictureUrl());
			newerCampsite.setLocation(campsite.getLocation());
			newerCampsite.setState(campsite.getState());
		}
		return newerCampsite;
	}

	@Override
	public boolean disableCampsite(String username, int campsiteId) {
		Optional<Campsite> campsiteOpt = campRepo.findById(campsiteId);
		if(campsiteOpt.isPresent()) {
			Campsite deleteThis= campsiteOpt.get();
			deleteThis.setEnabled(false);
			campRepo.saveAndFlush(deleteThis);
			return true;
					
		}
		else {
			return false;
		}

	}

	@Override
	public List<Campsite> findByName(String name) {
		List<Campsite> campsites= campRepo.findByName(name);
		return campsites;
		//Definitely needs to be tested.... Or Vince can verify this is what we need to do.
	}

	@Override
	public Set<Campsite> userIndex(String username) {
		return campRepo.findByCreator_UserUsername(username);
	}
	

	
	

}
