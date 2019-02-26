package com.ry.es.esdemo.controller;

import com.ry.es.esdemo.service.UserService;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class SearchController {

    @Autowired
    private UserService userService;

    @GetMapping("/get/user")
    @ResponseBody
    public ResponseEntity get(@RequestParam(name = "id", defaultValue = "") String id) {
        GetResponse response = userService.getById(id);

        if (!response.isExists()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(response.getSource(), HttpStatus.OK);
    }

    @PostMapping("add/user")
    @ResponseBody
    public ResponseEntity add(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "age") int age,
            @RequestParam(name = "address") String address,
            @RequestParam(name = "mobile") String mobile
    ) {
        IndexResponse response;
        try {
            response = userService.add(name, age, address, mobile);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }


    @DeleteMapping("remove/user")
    public ResponseEntity remove(@RequestParam(name = "id") String id) {
        DeleteResponse response = userService.remove(id);

        return new ResponseEntity(response.getResult().toString(), HttpStatus.OK);
    }

    @PutMapping("modify/user")
    @ResponseBody
    public ResponseEntity modify(@RequestParam(name = "id") String id,
                                 @RequestParam(name = "name", required = false) String name,
                                 @RequestParam(name = "age", required = false) int age,
                                 @RequestParam(name = "address", required = false) String address,
                                 @RequestParam(name = "mobile", required = false) String mobile) {
        UpdateResponse response;
        try {
            response = userService.modify(id, name, age,address,mobile);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(response.getResult().toString(), HttpStatus.OK);
    }


}
