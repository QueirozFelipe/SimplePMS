package dev.felipequeiroz.simplepms.service;

import dev.felipequeiroz.simplepms.domain.CategoriaDeUh;
import dev.felipequeiroz.simplepms.domain.UnidadeHabitacional;
import dev.felipequeiroz.simplepms.dto.CadastroUnidadeHabitacionalDTO;
import dev.felipequeiroz.simplepms.repository.CategoriaDeUhRepository;
import dev.felipequeiroz.simplepms.repository.UnidadeHabitacionalRepository;
import dev.felipequeiroz.simplepms.validations.cliente.CadastrarClienteValidations;
import dev.felipequeiroz.simplepms.validations.unidadeHabitacional.CadastrarUnidadeHabitacionalValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Service
public class UnidadeHabitacionalService {

    @Autowired
    private UnidadeHabitacionalRepository uhRepository;

    @Autowired
    private CategoriaDeUhRepository categoriaRepository;

    @Autowired
    private List<CadastrarUnidadeHabitacionalValidation> validationsList;

    public UnidadeHabitacional cadastrar(CadastroUnidadeHabitacionalDTO dto) {

        validationsList.forEach(v -> v.validate(dto));

        CategoriaDeUh categoria = categoriaRepository.getReferenceById(dto.idCategoriaDeUh());
        return uhRepository.save(new UnidadeHabitacional(null, dto.nomeUh(), categoria, true));

    }

    public URI criarURI(UnidadeHabitacional uh, UriComponentsBuilder uriBuilder) {
        return uriBuilder.path("unidades-habitacionais/{id}").buildAndExpand(uh.getId()).toUri();
    }
}
