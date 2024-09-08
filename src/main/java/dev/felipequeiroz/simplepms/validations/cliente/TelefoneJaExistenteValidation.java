package dev.felipequeiroz.simplepms.validations.cliente;

import dev.felipequeiroz.simplepms.dto.CadastroClienteDTO;
import dev.felipequeiroz.simplepms.exception.BusinessValidationException;
import dev.felipequeiroz.simplepms.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TelefoneJaExistenteValidation implements CadastrarClienteValidations {

    @Autowired
    ClienteRepository repository;

    @Override
    public void validate(CadastroClienteDTO dto) {
        if (dto.telefone() != null && repository.existsByTelefone(dto.telefone()))
            throw new BusinessValidationException("Já existe um cliente cadastrado com este telefone. Verifique.");
    }
}
