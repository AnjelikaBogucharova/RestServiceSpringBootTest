package com.angelikabog.controller;

import com.angelikabog.logic.Pet;
import com.angelikabog.logic.PetModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class Controller {
    private static final PetModel petmodel = PetModel.getInstance();
    private static final AtomicInteger newId = new AtomicInteger(1);

    @PostMapping(value = "/createPet", consumes = "application/json", produces = "application/text")
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

    @GetMapping(value = "/getAll", produces = "application/json")
    public Map<Integer, Pet> getAll(){
        return petmodel.getAll();
    }

    @GetMapping(value = "/getPet", consumes = "application/json", produces = "application/json")
    public Pet getPet(@RequestBody Map<String,Integer> id){
        return petmodel.getFromList(id.get("id"));
    }

}
