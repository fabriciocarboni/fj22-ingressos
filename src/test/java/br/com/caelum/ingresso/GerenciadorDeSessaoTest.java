package br.com.caelum.ingresso;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.ingresso.model.Filme;
import br.com.caelum.ingresso.model.Sala;
import br.com.caelum.ingresso.model.Sessao;
import br.com.caelum.ingresso.validacao.GerenciadorDeSessao;

public class GerenciadorDeSessaoTest {

	@Test
	public void garanteQueNaoDevePermitirSessaoNoMesmoHorario() {

		Filme filme = new Filme();
		filme.setDuracao(120);
		LocalTime horario = LocalTime.now();

		Sala sala = new Sala("");
		List<Sessao> sessoes = Arrays.asList(new Sessao(horario, sala, filme));

		Sessao sessao = new Sessao(horario, sala, filme);

		GerenciadorDeSessao gerenciador = new GerenciadorDeSessao(sessoes);

		Assert.assertFalse(gerenciador.cabe(sessao));

	}

	@Test
	public void garanteQueNaoDevePermitirSessoesTerminandoDentroDoHorarioDeUmaSessaoJaExistente() {
		Filme filme = new Filme();
		filme.setDuracao(120);
		LocalTime horario = LocalTime.now();
		Sala sala = new Sala("");
		List<Sessao> sessoes = Arrays.asList(new Sessao(horario, filme, sala));
		Sessao sessao = new Sessao(horario.plusHours(1), filme, sala);
		GerenciadorDeSessao gerenciador = new GerenciadorDeSessao(sessoes);
		Assert.assertFalse(gerenciador.cabe(sessao));
	}

	@Test
	public void garanteQueNaoDevePermitirSessoesIniciandoDentroDoHorarioDeUmaSessaoJaExistente() {
		Filme filme = new Filme();
		filme.setDuracao(120);
		LocalTime horario = LocalTime.now();
		Sala sala = new Sala("");
		List<Sessao> sessoesDaSala = Arrays.asList(new Sessao(horario, sala, filme));
		GerenciadorDeSessao gerenciador = new GerenciadorDeSessao(sessoesDaSala);
		Assert.assertFalse(gerenciador.cabe(new Sessao(horario.plus(1, ChronoUnit.HOURS), sala, filme)));
	}

	@Test
	public void garanteQueDevePermitirUmaInsercaoEntreDoisFilmes() {
		Sala sala = new Sala("");
		Filme filme1 = new Filme();
		filme1.setDuracao(90);
		LocalTime dezHoras = LocalTime.parse("10:00:00");
		Sessao sessaoDasDez = new Sessao(dezHoras, sala, filme1);
		Filme filme2 = new Filme();
		filme2.setDuracao(120);
		LocalTime dezoitoHoras = LocalTime.parse("18:00:00");
		Sessao sessaoDasDezoito = new Sessao(dezoitoHoras, sala, filme2);
		List<Sessao> sessoes = Arrays.asList(sessaoDasDez, sessaoDasDezoito);
		GerenciadorDeSessao gerenciador = new GerenciadorDeSessao(sessoes);
		Assert.assertTrue(gerenciador.cabe(new Sessao(LocalTime.parse("13:00:00"), sala, filme2)));
	}

}
