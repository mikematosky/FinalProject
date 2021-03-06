package com.skilldistillery.campfree.repositories;

import org.springframework.data.jpa.repository.JpaRepository;


import com.skilldistillery.campfree.entities.Feature;

public interface FeaturesRepository extends JpaRepository<Feature, Integer>{
	
	Feature findById(int id);
}
