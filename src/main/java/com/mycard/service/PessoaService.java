package com.mycard.service;

import com.mycard.model.Pessoa;
import com.mycard.repository.PessoaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PessoaService {

    private final PessoaRepository repository;

    public PessoaService(PessoaRepository repository) {
        this.repository = repository;
    }

    public List<Pessoa> listar() {
        return repository.findAll();
    }

    public Pessoa buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));
    }

    public void deletar(Long id) {

        // verifica se existe
        Pessoa pessoa = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));

        // deleta
        repository.delete(pessoa);
    }

    public Pessoa atualizar(Long id, Pessoa dadosAtualizados) {

        // 1. Buscar no banco
        Pessoa pessoa = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));

        // 2. Validar
        if (dadosAtualizados.getNome() == null || dadosAtualizados.getNome().isEmpty()) {
            throw new RuntimeException("Nome é obrigatório");
        }

        // 3. Atualizar dados
        pessoa.setNome(dadosAtualizados.getNome());

        // 4. Salvar novamente
        return repository.save(pessoa);
    }


    public Pessoa salvar(Pessoa pessoa) {

        // REGRA DE NEGÓCIO
        if (pessoa.getNome() == null || pessoa.getNome().isEmpty()) {
            throw new RuntimeException("Nome é obrigatório");
        }

        return repository.save(pessoa);
    }
}