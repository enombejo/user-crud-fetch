package com.example.user_crud_spring_boot.controller;

import com.example.user_crud_spring_boot.model.Role;
import com.example.user_crud_spring_boot.model.User;
import com.example.user_crud_spring_boot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("admin")
public class AdminRestController {

    @Autowired
    UserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody User user /*@RequestParam(name = "email") String email,
                                        @RequestParam(name = "password") String password,
                                        @RequestParam(name = "lastName") String lastName,
                                        @RequestParam(name = "firstName") String firstName,
                                        @RequestParam(name = "ago") Integer ago,
                                        @RequestParam(name = "roles",  required = false) Set<String> roles/**/) {
//        User user = new User(email, password, firstName, lastName, ago);

        Set<Role> setRoles = new HashSet<>();
        setRoles.add(new Role("ROLE_USER", user));
        if ((user.getRoles() != null) && !user.getRoles().isEmpty()) {
            //roles.forEach(n -> setRoles.add(new Role(n, user)));
            user.getRoles().forEach(role -> role.setUser(user));
        } else {
            //setRoles.add(new Role("ROLE_USER", user));
            user.setRoles(setRoles);
        }
        System.out.println(user.toString());
        userService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/read")
    public ResponseEntity<List<User>> read() {
        final List<User> users = userService.listUser();

        return users != null &&  !users.isEmpty()
                ? new ResponseEntity<>(users, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/updateUser")
    public ResponseEntity<?> update(@RequestBody User user) {

        Set<Role> setRoles = new HashSet<>();
        if ((user.getRoles() != null) && !user.getRoles().isEmpty()) {
            user.getRoles().forEach(role -> role.setUser(user));
        } else {
            userService.getUserByName(user.getEmail()).getRoles().forEach(role -> setRoles.add(new Role(role.getRole(), user)));
            user.setRoles(setRoles);
        }
        System.out.println(user);
        final boolean updated = userService.updateUser(user);

        //final boolean updated = true;
        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<?> delete(@RequestBody User user) {
        System.out.println(user);
        final boolean delete = userService.deleteUser(user.getId());
        return delete
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
