package com.skilldistillery.campfree.controllers;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.campfree.entities.Campsite;
import com.skilldistillery.campfree.services.CampsiteService;

@RestController
@RequestMapping("api")
@CrossOrigin({"*", "http://localhost:4210"})
public class CampsiteController {
	
	@Autowired
	private CampsiteService campSvc;
	
	@GetMapping("campsite")
	public List<Campsite> findAllcampsites() {
		return campSvc.findAllCampsites();
	}

	@GetMapping("campsite/{id}")
	public Campsite show(
			@PathVariable Integer id, 
			HttpServletResponse response) {
		Campsite campsite = campSvc.findCampsiteById(id);
		if (campsite == null) {
			response.setStatus(404);
		}
		return campsite;
	}

	@PostMapping("campsite")
	public Campsite create(
			@RequestBody Campsite campsite, 
			HttpServletRequest request,
			HttpServletResponse response) {
		try {
			campsite = campSvc.createCampsite(campsite);
			response.setStatus(201);
			StringBuffer url = request.getRequestURL();
			url.append("/").append(campsite.getId());
			response.setHeader("Location", url.toString());
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(400);
			campsite = null;
		}
		return campsite;
	}
	
	
	@DeleteMapping("campsite/{id}")
	public void delete(@PathVariable Integer id, HttpServletResponse response) {
		try {
			if(campSvc.disableCampsite(id)) {
			response.setStatus(204);
			}else {
				response.setStatus(404);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(409);
		}
	}
	
	@PutMapping("campsite/{id}")
	public Campsite update(
			@PathVariable Integer id, 
			@RequestBody Campsite campsite, 
			HttpServletResponse response
			) {
		try {
			campsite= campSvc.updateCampsite(campsite, id);
			if (campsite == null) {
				response.setStatus(404);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(400);
			campsite = null;
		}
		return campsite;
	}
	
	@GetMapping("campsite/search/{keyword}")
	public List<Campsite> campsiteForKeyword(@PathVariable String keyword) {
		return campSvc.findByName(keyword);
	}
	

}