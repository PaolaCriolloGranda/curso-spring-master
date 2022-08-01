package com.example.cursospring.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.cursospring.entity.User;
import com.example.cursospring.repository.UserRepository;
import com.example.cursospring.service.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/user")
public class UserControler {

	@Autowired
	private UserRepository useS;
	@Autowired
	private UserServiceImp s3Service;
	@GetMapping
	List<User> getAll(){
		return useS.findAll()
				.stream()
				.peek(user -> user.setImagenUrl(s3Service.getObjectUrl(user.getImagenPath()))).peek(user -> user.setCedulaUrl(s3Service.getObjectUrl(user.getCedulaPath())))
				.collect(Collectors.toList());
	}
	
	//Create a new user
	@PostMapping
	public User create(@RequestBody User user){
		useS.save(user);
		user.setImagenUrl(s3Service.getObjectUrl(user.getImagenPath()));
		user.setCedulaUrl(s3Service.getObjectUrl(user.getCedulaPath()));
		return user;
	}
	
	//read an user
	@GetMapping("/{id}")
	public ResponseEntity<?> read (@PathVariable(value = "id") Integer userId){
		Optional<User>oUser = useS.findById(userId);
		
		if(!oUser.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(oUser);
	}
	
	//Update a user
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@RequestBody User userDetails, @PathVariable(value="id")Integer userId){
		Optional<User> user = useS.findById(userId);
		
		if(!user.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		user.get().setName(userDetails.getName());
		user.get().setPassword(userDetails.getPassword());
		user.get().setEmail(userDetails.getEmail());
		user.get().setEnable(userDetails.getEnable());
		user.get().setImagenUrl(s3Service.getObjectUrl(userDetails.getImagenPath()));
		user.get().setCedulaUrl(s3Service.getObjectUrl(userDetails.getCedulaPath()));
		return ResponseEntity.status(HttpStatus.CREATED).body(useS.save(user.get()));
	}
	
	//delete user
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable(value="id")Integer userId){
		if(!useS.findById(userId).isPresent()) {
			return ResponseEntity.notFound().build();
		}
		useS.deleteById(userId);
		return ResponseEntity.ok().build();
	}
	

	
}
