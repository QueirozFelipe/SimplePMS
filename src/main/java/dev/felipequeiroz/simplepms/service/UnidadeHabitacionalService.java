package dev.felipequeiroz.simplepms.service;

import dev.felipequeiroz.simplepms.domain.CategoriaDeUh;
import dev.felipequeiroz.simplepms.domain.Cliente;
import dev.felipequeiroz.simplepms.domain.UnidadeHabitacional;
import dev.felipequeiroz.simplepms.dto.AtualizacaoUnidadeHabitacionalDTO;
import dev.felipequeiroz.simplepms.dto.CadastroUnidadeHabitacionalDTO;
import dev.felipequeiroz.simplepms.repository.CategoriaDeUhRepository;
import dev.felipequeiroz.simplepms.repository.UnidadeHabitacionalRepository;
import dev.felipequeiroz.simplepms.validations.unidadeHabitacional.CadastrarUnidadeHabitacionalValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class UnidadeHabitacionalService {

    @Autowired
    private UnidadeHabitacionalRepository uhRepository;

    @Autowired
    private CategoriaDeUhRepository categoriaRepository;

    @Autowired
    private List<CadastrarUnidadeHabitacionalValidations> validationsList;

    public UnidadeHabitacional cadastrar(CadastroUnidadeHabitacionalDTO dto) {

        validationsList.forEach(v -> v.validate(dto));

        CategoriaDeUh categoria = categoriaRepository.getReferenceById(dto.idCategoriaDeUh());
        return uhRepository.save(new UnidadeHabitacional(null, dto.nomeUh(), categoria, true));

    }

    public URI criarUri(UnidadeHabitacional uh, UriComponentsBuilder uriBuilder) {
        return uriBuilder.path("unidades-habitacionais/{id}").buildAndExpand(uh.getId()).toUri();
    }

    public UnidadeHabitacional atualizar(AtualizacaoUnidadeHabitacionalDTO dto) {

        UnidadeHabitacional uh = uhRepository.getReferenceById(dto.id());

        Optional.ofNullable(dto.nomeUh()).ifPresent(uh::setNomeUh);
        Optional.ofNullable(dto.idCategoriaDeUh()).ifPresent(
                idCategoria -> uh.setCategoriaDeUh(categoriaRepository.getReferenceById(idCategoria))
        );

        return uh;

    }
}
