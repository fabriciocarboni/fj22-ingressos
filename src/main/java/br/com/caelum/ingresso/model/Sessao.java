package br.com.caelum.ingresso.model;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.Id;

public class Sessao {
	
	@Id
	@GeneratedValue
	private Integer Id;
	private LocalTime horario;
	
	@ManyToOne
	private Sala sala;
	
	@ManyToOne
	private Filme filme;
	
	/**
	 * @deprecated hibernate only
	 */
	public Sessao() {
	}

	public Sessao(LocalTime horario, Sala sala, Filme filme) {
		super();
		this.horario = horario;
		this.sala = sala;
		this.filme = filme;
	}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public LocalTime getHorario() {
		return horario;
	}

	public void setHorario(LocalTime horario) {
		this.horario = horario;
	}

	public Sala getSala() {
		return sala;
	}

	public void setSala(Sala sala) {
		this.sala = sala;
	}

	public Filme getFilme() {
		return filme;
	}

	public void setFilme(Filme filme) {
		this.filme = filme;
	}
	
	public LocalTime getHorarioTermino() {
		return this.horario.plus(filme.getDuracao().toMinutes(),
				ChronoUnit.MINUTES);
	}
	
}	
