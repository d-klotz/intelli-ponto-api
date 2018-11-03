package com.klotz.intelliponto.api.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.klotz.intelliponto.api.entities.Lancamento;
import com.klotz.intelliponto.api.repositories.LancamentoRepository;
import com.klotz.intelliponto.api.services.LancamentoService;

@Service
public class LancamentoServiceImpl implements LancamentoService {
	
	private static final Logger log = LoggerFactory.getLogger(LancamentoServiceImpl.class);

	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	public Page<Lancamento> buscarPorFuncionarioId(Long funcionarioId, PageRequest pageRequest) {
		log.info("buscando lancamentos por funcionario id {}", funcionarioId);
		return this.lancamentoRepository.findByFuncionarioId(funcionarioId, pageRequest);
	}

	@Cacheable("lancamentoPorId")
	public Optional<Lancamento> buscarPorId(Long id) {
		log.info("Buscando um lancamento pelo id {}", id);
		return this.lancamentoRepository.findById(id);
	}
	
	@CachePut
	public Lancamento persistir(Lancamento lancamento) {
		log.info("Persistindo lancamento {}", lancamento);
		return this.lancamentoRepository.save(lancamento);
	}

	public void remover(Long id) {
		log.info("Removendo lancamento pelo id {}", id);
		this.lancamentoRepository.deleteById(id);
	}

}
