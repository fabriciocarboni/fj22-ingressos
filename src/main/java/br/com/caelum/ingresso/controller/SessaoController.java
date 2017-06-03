package br.com.caelum.ingresso.controller;

import java.time.LocalTime;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.caelum.ingresso.dao.FilmeDao;
import br.com.caelum.ingresso.dao.SalaDao;
import br.com.caelum.ingresso.dao.SessaoDao;
import br.com.caelum.ingresso.model.Filme;
import br.com.caelum.ingresso.model.Sala;
import br.com.caelum.ingresso.model.Sessao;

@Controller
public class SessaoController {
	
	@Autowired
	private SalaDao salaDao;
	
	@Autowired
	private FilmeDao filmeDao;
	
	@Autowired
	private SessaoDao sessaoDao;

	@GetMapping("/sessao")
	public ModelAndView form(@RequestParam("salaId") Integer salaId, SessaoForm form) {
		
		form.setSalaId(salaId);
		
		ModelAndView modelAndView = new ModelAndView("sessao/sessao");
		
		modelAndView.addObject("sala", salaDao.findOne(salaId));
		modelAndView.addObject("filmes", filmeDao.findAll());
		
		return modelAndView;
	}
	
	
	@PostMapping(value = "/sessao")
	@Transactional
	public	ModelAndView	salva(@Valid	SessaoForm	form,	BindingResult	result)	{
		
		if	(result.hasErrors()) return	form(form.getSalaId(),form);
			ModelAndView	modelAndView = new	ModelAndView("redirect:/sala/"+form.getSalaId()+"/sessoes");
			Sessao	sessao	=	form.toSessao(salaDao,	filmeDao);
			sessaoDao.save(sessao);
			
			return	modelAndView;
	}
	
	public	class	SessaoForm	{
		
		private	Integer	id;
		@NotNull
		private	Integer	salaId;
		@DateTimeFormat(pattern="HH:mm")
		@NotNull
		private	LocalTime	horario;
		@NotNull
		private	Integer	filmeId;
		public Integer getId() {
			return id;
		}
		
		

		
		public Sessao toSessao(SalaDao salaDao, FilmeDao filmeDao) {
			
			Filme	filme	=	filmeDao.findOne(filmeId);
			Sala	sala	=	salaDao.findOne(salaId);
			Sessao	sessao	=	new	Sessao(horario,	sala, filme);
			sessao.setId(id);
			return	sessao;
		}




		public void setId(Integer id) {
			this.id = id;
		}
		public Integer getSalaId() {
			return salaId;
		}
		public void setSalaId(Integer salaId) {
			this.salaId = salaId;
		}
		public LocalTime getHorario() {
			return horario;
		}
		public void setHorario(LocalTime horario) {
			this.horario = horario;
		}
		public Integer getFilmeId() {
			return filmeId;
		}
		public void setFilmeId(Integer filmeId) {
			this.filmeId = filmeId;
		}
		
	}
	

}
