package br.gov.ce.detran.vistoriacfcapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import jakarta.persistence.MappedSuperclass;


@MappedSuperclass
public abstract class Pessoa extends Entidade{



	@Column(name = "nome", nullable = false, length = 200)
	private String nome;

	@Column(name = "cpf", nullable = false, length = 11)
	private String cpf;

	@Column(name = "matricula", nullable = false, length = 6)
	private String matricula;

	@Column(name = "email", nullable = false, length = 100)
	private String email;

	@Column(name = "telefone", nullable = false, length = 11)
	private String telefone;

	@Lob
	@Column(name = "foto", nullable = true)
	private String foto;

	@Lob
	@Column(name = "fotoBase64", nullable = true)
	private String fotoBase64;

	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getFoto() {return foto;}

   public void setFoto(String foto) {this.foto = foto;}

	public String getFotoBase64() {return fotoBase64;}
	public void setFotoBase64(String fotoBase64) {this.fotoBase64 = fotoBase64;}
}
