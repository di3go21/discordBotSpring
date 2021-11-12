package com.discbotback.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Entradilla {

	@Id
	@GeneratedValue
	private long id;

	private String descripcion;
	private String fileNameq;
	@ManyToMany(mappedBy = "entradillas")
	List<Usuario> usuarios;
	@OneToOne
	private Usuario autor;

	public Entradilla(String descripcion, String fileNameq, Usuario autor) {
		super();
		this.descripcion = descripcion;
		this.fileNameq = fileNameq;
		this.autor = autor;
	}

}
