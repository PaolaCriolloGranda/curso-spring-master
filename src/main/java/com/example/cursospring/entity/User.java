package com.example.cursospring.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name="users")
@Data
@NoArgsConstructor
public class User implements Serializable{

	private static final long serialVersionUID = 7160385895434188773L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String password;
	private String email;
	private Boolean enable;
	private String cedulaPath;
	private  String imagenPath;
	@Transient
	private  String imagenUrl;
	@Transient
	private  String cedulaUrl;
}
