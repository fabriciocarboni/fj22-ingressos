package br.com.caelum.ingresso.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Entity
public class Ingresso {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@ManyToOne
	private Sessao sessao;
	private BigDecimal preco;
	
	@ManyToOne
	private Lugar lugar;
	
	
	
	public Ingresso() {
		super();
	}
	
	
	@Enumerated(EnumType.STRING)
	private	TipoDeIngresso tipoDeIngresso;
	
	
	public Ingresso(Sessao sessao, TipoDeIngresso tipoDeDesconto, Lugar lugar) {
		super();
		this.sessao = sessao;
		//this.preco = tipoDeDesconto.aplicarDescontoSobre(sessao.getPreco());
		this.preco = tipoDeDesconto.aplicaDesconto(sessao.getPreco());
		this.lugar = lugar;

	}
	
	

	public Sessao getSessao() {
		return sessao;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public Lugar getLugar() {
		return lugar;
	}

}
