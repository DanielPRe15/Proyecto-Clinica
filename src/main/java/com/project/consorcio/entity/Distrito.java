package com.project.consorcio.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="tb_distrito")
public class Distrito {

	@Id
	@Column(name="cod_dis")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer codigo;
	
	@Column(name="nom_dis")
	private String nombre;
	
	//Anotacion para anular Json
	@JsonIgnore
	
	//Relacion
	@OneToMany(mappedBy = "dis")
	private List<Medico> listaMedico;

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Medico> getListaMedico() {
		return listaMedico;
	}

	public void setListaMedico(List<Medico> listaMedico) {
		this.listaMedico = listaMedico;
	}
	
	
	
}
