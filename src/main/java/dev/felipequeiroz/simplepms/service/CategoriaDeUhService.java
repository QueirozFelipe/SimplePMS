package dev.felipequeiroz.simplepms.service;

import dev.felipequeiroz.simplepms.domain.CategoriaDeUh;
import dev.felipequeiroz.simplepms.dto.AtualizacaoCategoriaDeUhDTO;
import dev.felipequeiroz.simplepms.dto.CadastroCategoriaDeUhDTO;
import dev.felipequeiroz.simplepms.repository.CategoriaDeUhRepository;
import dev.felipequeiroz.simplepms.validations.categoriaDeUh.CadastrarCategoriaDeUhValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaDeUhService {

    @Autowired
    private CategoriaDeUhRepository repository;

    @Autowired
    private List<CadastrarCategoriaDeUhValidations> validationsList;

    public CategoriaDeUh cadastrar(CadastroCategoriaDeUhDTO dto) {

        validationsList.forEach(v -> v.validate(dto));

        CategoriaDeUh categoria = new CategoriaDeUh(dto);
        return repository.save(categoria);

    }

    public URI criarUri(CategoriaDeUh categoria, UriComponentsBuilder uriBuilder) {
        return uriBuilder.path("categorias-de-uh/{id}").buildAndExpand(categoria.getId()).toUri();
    }

    public CategoriaDeUh atualizar(AtualizacaoCategoriaDeUhDTO dto) {

        CategoriaDeUh categoria = repository.getReferenceById(dto.id());

        Optional.ofNullable(dto.nomeCategoria()).ifPresent(categoria::setNomeCategoria);
        Optional.ofNullable(dto.quantidadeDeLeitos()).ifPresent(categoria::setQuantidadeDeLeitos);

        return categoria;

    }

    public void excluir(Long id) {

        CategoriaDeUh categoria = repository.getReferenceById(id);
        if(!categoria.getAtivo()) {
            throw new IllegalStateException("Este cadastro não está ativo e portanto não pode ser excluído.");
        }
        categoria.setAtivo(false);

    }
}
