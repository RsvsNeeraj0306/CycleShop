package com.cycle.rental.repository;


import com.cycle.rental.entity.Cycles;



import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CyclesRepository extends CrudRepository<Cycles, Integer> {
}
