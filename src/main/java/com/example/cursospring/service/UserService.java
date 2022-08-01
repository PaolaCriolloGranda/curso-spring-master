package com.example.cursospring.service;

import java.awt.print.Pageable;
import java.util.Optional;
import com.example.cursospring.entity.User;
import org.springframework.data.domain.Page;

public interface UserService {
	public Iterable<User> findAll();
	public Page<User> findAll(Pageable pageable);
	public Optional<User>findById(Integer id);
	public User save(User user);
	public void deleteById(Integer id);

}
