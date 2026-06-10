package com.mycard.service;

import com.mycard.model.Gasto;
import com.mycard.model.GastoDTO;
import com.mycard.model.Pessoa;
import com.mycard.repository.GastoRepository;
import com.mycard.repository.PessoaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GastoService {

    private final GastoRepository gastoRepository;
    private final PessoaRepository pessoaRepository;

    public GastoService(GastoRepository gastoRepository, PessoaRepository pessoaRepository) {
        this.gastoRepository = gastoRepository;
        this.pessoaRepository = pessoaRepository;
    }

    public GastoDTO salvar(GastoDTO dto) {

        // 1. Buscar pessoa
        Pessoa pessoa = pessoaRepository.findById(dto.getPessoaId())
                .orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));

        // 2. Criar gasto
        Gasto gasto = new Gasto();
        gasto.setDescricao(dto.getDescricao());
        gasto.setValor(dto.getValor());
        gasto.setCategoria(dto.getCategoria());
        gasto.setMetodoPagamento(dto.getMetodoPagamento());
        gasto.setData(dto.getData());
        gasto.setPessoa(pessoa);

        // 3. Salvar
        Gasto gastoSalvo = gastoRepository.save(gasto);

        // 4. Retornar DTO
        return converterParaDTO(gastoSalvo);
    }

    private GastoDTO converterParaDTO(Gasto gasto) {

        GastoDTO dto = new GastoDTO();

        dto.setId(gasto.getId());
        dto.setDescricao(gasto.getDescricao());
        dto.setValor(gasto.getValor());
        dto.setCategoria(gasto.getCategoria());
        dto.setMetodoPagamento(gasto.getMetodoPagamento());
        dto.setData(gasto.getData());

        dto.setPessoaId(gasto.getPessoa().getId());
        dto.setNomePessoa(gasto.getPessoa().getNome());

        return dto;
    }

    public List<GastoDTO> listar() {
        return gastoRepository.findAll()
                .stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }
}