package dev.felipequeiroz.simplepms.validations.unidadeHabitacional;

import dev.felipequeiroz.simplepms.dto.CadastroUnidadeHabitacionalDTO;
import dev.felipequeiroz.simplepms.exception.BusinessValidationException;
import dev.felipequeiroz.simplepms.repository.UnidadeHabitacionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NomeJaExistenteValidation implements CadastrarUnidadeHabitacionalValidations {

    @Autowired
    private UnidadeHabitacionalRepository repository;

    @Override
    public void validate(CadastroUnidadeHabitacionalDTO dto) {
        if (repository.existsByNomeUhIgnoreCase(dto.nomeUh())) {
            throw new BusinessValidationException("Já existe uma UH cadastrada com este nome. Verifique.");
        }
    }
}
