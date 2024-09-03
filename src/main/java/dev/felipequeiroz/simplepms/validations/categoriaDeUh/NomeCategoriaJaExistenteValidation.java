package dev.felipequeiroz.simplepms.validations.categoriaDeUh;

import dev.felipequeiroz.simplepms.dto.CadastroCategoriaDeUhDTO;
import dev.felipequeiroz.simplepms.exception.BusinessValidationException;
import dev.felipequeiroz.simplepms.repository.CategoriaDeUhRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NomeCategoriaJaExistenteValidation implements CadastrarCategoriaDeUhValidations{

    @Autowired
    private CategoriaDeUhRepository repository;

    @Override
    public void validate(CadastroCategoriaDeUhDTO dto) {
        if (repository.existsByNomeCategoriaIgnoreCase(dto.nomeCategoria()))
            throw new BusinessValidationException("Já existe uma categoria de uh cadastrada com este nome. Verifique.");
    }
}
