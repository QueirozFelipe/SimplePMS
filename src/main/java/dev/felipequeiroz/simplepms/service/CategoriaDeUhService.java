package dev.felipequeiroz.simplepms.service;

import dev.felipequeiroz.simplepms.domain.CategoriaDeUh;
import dev.felipequeiroz.simplepms.dto.CadastroCategoriaDeUhDTO;
import dev.felipequeiroz.simplepms.repository.CategoriaDeUhRepository;
import dev.felipequeiroz.simplepms.validations.categoriaDeUh.CadastrarCategoriaDeUhValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

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
        return uriBuilder.path("categoria-de-uh/{id}").buildAndExpand(categoria.getId()).toUri();
    }
}
