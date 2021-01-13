package com.angelikabog.controller;

import com.angelikabog.logic.ChangedPet;
import com.angelikabog.logic.Pet;
import com.angelikabog.logic.PetAuth;
import com.angelikabog.logic.PetModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class Controller {
    private static final PetModel petmodel = PetModel.getInstance();
    private static final AtomicInteger newId = new AtomicInteger(1);
    private static final String[] users = {"parent", "child"};

    @PostMapping(value = "/createPet", consumes = "application/json", produces = "application/text")
    public ResponseEntity createPet(@RequestBody PetAuth pet) {
        Pet petInfo = new Pet(pet.getName(), pet.getType(), pet.getAge());
        ResponseEntity result = new ResponseEntity(HttpStatus.CREATED);
        int countUser = 0;
        try {
            for (String user : users) {
                if (pet.getUser().equals(user)) {
                    countUser++;
                    if (pet.getUser().equals("parent") && pet.getPassword().equals("parentPassword")) {
                        petmodel.add(petInfo, newId.getAndIncrement());
                        result = new ResponseEntity(String.format("Поздравляем с очередным пополнением! %s, добро пожаловать в семью!", pet.getName()), HttpStatus.CREATED);

                    }else if(pet.getUser().equals("child") && pet.getPassword().equals("childPassword"))
                        result = new ResponseEntity("Действие не доступно!", HttpStatus.FORBIDDEN);
                    else result = new ResponseEntity("Не верный пароль!", HttpStatus.UNAUTHORIZED);
                }
            }
            if (countUser == 0) result = new ResponseEntity("Действие заблокировано!", HttpStatus.LOCKED);
        }catch (Exception e){
            result = new ResponseEntity("Попытка несанкционированного доступа!",HttpStatus.INTERNAL_SERVER_ERROR);
        }


        return result;
    }

    @DeleteMapping(value = "/deletePet", consumes = "application/json")
    public ResponseEntity deletePet(@RequestBody Map<String, Integer> id) {
        petmodel.remove(id.get("id"));
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/updatedPet", consumes = "application/json")
    public ResponseEntity changePet(@RequestBody ChangedPet pet) {
        ResponseEntity result = new ResponseEntity("updated", HttpStatus.ACCEPTED);
        if (petmodel.getFromList(pet.getId()) != null) {
            if (pet.getName() == null && pet.getAge() != 0) petmodel.update(pet.getId(), pet.getAge());
            else if (pet.getAge() == 0 && pet.getName() != null) petmodel.update(pet.getId(), pet.getName());
            else if (pet.getAge() == 0 && pet.getName() == null)
                result = new ResponseEntity("Для внесения изменений, необходимо передать данные для изменения имени либо возраста.", HttpStatus.BAD_REQUEST);
            else petmodel.update(pet.getId(), pet.getName(), pet.getAge());
        } else {
            result = new ResponseEntity(String.format("Питомца с id = %s не найдено.", pet.getId()), HttpStatus.NOT_FOUND);
        }
        return result;
    }

    @GetMapping(value = "/getAll", produces = "application/json")
    public Map<Integer, Pet> getAll() {
        return petmodel.getAll();
    }

    @GetMapping(value = "/getPet", consumes = "application/json", produces = "application/json")
    public ResponseEntity getPet(@RequestBody Map<String, Integer> id) {
        ResponseEntity resp;
        resp = new ResponseEntity(petmodel.getFromList(id.get("id")), HttpStatus.OK);
        if (petmodel.getFromList(id.get("id")) == null)
            resp = new ResponseEntity("Запрашиваемый питомец не найден!", HttpStatus.NOT_FOUND);
        return resp;
    }

}
