package com.discbotback.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Usuario {

	@Id @GeneratedValue
	private long id;
	
	@NotEmpty
	@Column(unique = true)
	private String nickdiscord;
	@Email
	private String email;
	
	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaAlta;

	@Size(min = 5)
	private String password;
	
	@ManyToMany
	List<Entradilla> entradillas;
	
	@OneToOne
	private Entradilla seleccionada;

	public Usuario(@NotEmpty String nickdiscord, @Email String email,  String password) {
		super();
		this.nickdiscord = nickdiscord;
		this.email = email;
		this.password = password;
	}
	
	
}
