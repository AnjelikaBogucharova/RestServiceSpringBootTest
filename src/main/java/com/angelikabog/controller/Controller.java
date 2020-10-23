package com.angelikabog.controller;

import com.angelikabog.logic.ChangedPet;
import com.angelikabog.logic.Pet;
import com.angelikabog.logic.PetModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class Controller {
    private static final PetModel petmodel = PetModel.getInstance();
    private static final AtomicInteger newId = new AtomicInteger(1);

    @PostMapping(value = "/createPet", consumes = "application/json", produces = "application/text")
    @ResponseStatus(HttpStatus.CREATED)
    public String createPet(@RequestBody Pet pet){
        petmodel.add(pet, newId.getAndIncrement());
        String result = "";
        if(petmodel.getAll().size() == 1){
            result = String.format("Уррраа! У тебя появился первый питомец %s!", pet.getName());
        }else{
            result = String.format("Поздравляем с очередным пополнением! %s, добро пожаловать в семью!", pet.getName());
        }
        return result;
    }

    @DeleteMapping(value = "/deletePet", consumes = "application/json")
    public String deletePet(@RequestBody Map<String,Integer> id){
        petmodel.remove(id.get("id"));
        return "Deleted";
    }

    @PutMapping(value = "/updatedPet", consumes = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String changePet(@RequestBody ChangedPet pet){
        String result = "";
        if(petmodel.getFromList(pet.getId()) != null){
            petmodel.update(pet.getId(), pet.getName(), pet.getAge());
            result = "updated";
        }else{
            result = String.format("Питомца с id = %s не найдено.", pet.getId());
        }
        return result;
    }

    @GetMapping(value = "/getAll", produces = "application/json")
    public Map<Integer, Pet> getAll(){
        return petmodel.getAll();
    }

    @GetMapping(value = "/getPet", consumes = "application/json", produces = "application/json")
    public Pet getPet(@RequestBody Map<String,Integer> id){
        return petmodel.getFromList(id.get("id"));
    }

}
