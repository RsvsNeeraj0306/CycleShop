package com.cycle.rental.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cycle.rental.entity.BorrowedCycles;
import com.cycle.rental.entity.Cycles;
import com.cycle.rental.entity.User;
import com.cycle.rental.repository.BorrowedCyclesRepository;
import com.cycle.rental.repository.CyclesRepository;
import com.cycle.rental.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cycles")
public class CyclesController {

    @Autowired
    private CyclesRepository cyclesRepository;

    @Autowired
    private BorrowedCyclesRepository borrowedCyclesRepository;

      @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<Cycles> getCyclesList() {
        return (List<Cycles>) cyclesRepository.findAll();
    }

    @PutMapping("/borrow/{id}")
    public BorrowedCycles borrowCycle(@PathVariable int id) {
        Optional<Cycles> cycleOptional = cyclesRepository.findById(id);

        if (cycleOptional.isPresent()) {
            Cycles borrowedCycle = cycleOptional.get();

            if (borrowedCycle.getStock() != 0) {
                borrowedCycle.setStock(borrowedCycle.getStock() - 1);
            }
            Optional<BorrowedCycles> borrowedOptional = borrowedCyclesRepository.findById(id);

            if (borrowedOptional.isPresent()) {
                BorrowedCycles borrowedCycleEntity = borrowedOptional.get();
                borrowedCycleEntity.setStock(borrowedCycleEntity.getStock() + 1);
               return  borrowedCyclesRepository.save(borrowedCycleEntity);
            } else {
                BorrowedCycles borrowedEntity = new BorrowedCycles();
                borrowedEntity.setBorrowedCycleId(borrowedCycle.getCycleId());
                borrowedEntity.setCycleName(borrowedCycle.getCycleName());
                borrowedEntity.setStock(borrowedEntity.getStock() + 1);
                return borrowedCyclesRepository.save(borrowedEntity);
            }
        }
        return null;
    }

    @PutMapping("/return/{id}")
    public Cycles returnCycle(@PathVariable int id) {
        Optional<BorrowedCycles> cycleOptional = borrowedCyclesRepository.findById(id);

        if (cycleOptional.isPresent()) {
            BorrowedCycles borrowedCycle = cycleOptional.get();

            if (borrowedCycle.getStock() != 0) {
                borrowedCycle.setStock(borrowedCycle.getStock() - 1);

                if (borrowedCycle.getStock() == 0) {
                    borrowedCyclesRepository.delete(borrowedCycle);
                }

                Optional<Cycles> returnedCycleOptional = cyclesRepository.findById(borrowedCycle.getBorrowedCycleId());

                if (returnedCycleOptional.isPresent()) {
                    Cycles returnedCycleEntity = returnedCycleOptional.get();
                    returnedCycleEntity.setStock(returnedCycleEntity.getStock() + 1);
                    return cyclesRepository.save(returnedCycleEntity);
                }
            }
        }
        return null;
    }

    @PutMapping("/add")
    public Cycles addCycle(@RequestBody Cycles newCycle) {
       return cyclesRepository.save(newCycle);
    }

    @GetMapping("/restock")
    public Iterable<Cycles> getCyclesStock() {
        return cyclesRepository.findAll();
    }

    @PostMapping("/addUser")
    public User addUser(@RequestBody User newUser)

    {
        return userRepository.save(newUser);
    }

    @PostMapping("/userList")
    public  List<User> getUserList() {
        return (List<User>) userRepository.findAll();
    }

    @PutMapping("/addStock/{id}")
    public Cycles addStock(@PathVariable int id) {
        Optional<Cycles> cycleOptional = cyclesRepository.findById(id);

        if (cycleOptional.isPresent()) {
            Cycles cycle = cycleOptional.get();
            cycle.setStock(cycle.getStock() + 1);
            return cyclesRepository.save(cycle);
        }
        return null;
    }

   
}
