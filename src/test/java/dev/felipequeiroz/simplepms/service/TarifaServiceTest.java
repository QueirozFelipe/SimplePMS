package dev.felipequeiroz.simplepms.service;

import dev.felipequeiroz.simplepms.domain.ClassificacaoHospede;
import dev.felipequeiroz.simplepms.domain.Tarifa;
import dev.felipequeiroz.simplepms.domain.TarifaDetalhamento;
import dev.felipequeiroz.simplepms.dto.tarifa.CadastroTarifaDTO;
import dev.felipequeiroz.simplepms.dto.tarifa.CadastroTarifaDetalhamentoDTO;
import dev.felipequeiroz.simplepms.repository.TarifaRepository;
import dev.felipequeiroz.simplepms.validations.tarifa.CadastrarTarifaValidations;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class TarifaServiceTest {

    @Mock
    private TarifaRepository tarifaRepository;
    @Spy
    private Tarifa tarifa = new Tarifa();
    @Spy
    private TarifaDetalhamento tarifaDetalhamento = new TarifaDetalhamento();
    @Spy
    private List<CadastrarTarifaValidations> validationsList = new ArrayList<>();
    @Mock
    private CadastrarTarifaValidations validation1;
    @Mock
    private CadastrarTarifaValidations validation2;
    @Captor
    private ArgumentCaptor<Tarifa> tarifaCaptor;
    @Mock
    private UriComponentsBuilder uriBuilder;
    @InjectMocks
    private TarifaService tarifaService;

    @Test
    void deveriaSalvarTarifaAoCadastrar() {

        CadastroTarifaDTO dto = new CadastroTarifaDTO("Tarifa", new BigDecimal("100.00"),
                List.of(new CadastroTarifaDetalhamentoDTO(ClassificacaoHospede.ADULTO, new BigInteger("50"))));
        Tarifa tarifa = new Tarifa(dto);
        tarifa.setId(1L);

        tarifaService.cadastrar(dto);

        BDDMockito.then(tarifaRepository).should().save(tarifaCaptor.capture());
        Tarifa tarifaSalva = tarifaCaptor.getValue();

        assertEquals(tarifa.getNomeTarifa(), tarifaSalva.getNomeTarifa());
        assertEquals(tarifa.getValorBase(), tarifaSalva.getValorBase());

    }

    @Test
    void deveriaExecutarValidationsAoCadastrar() {

        CadastroTarifaDTO dto = new CadastroTarifaDTO("Tarifa", new BigDecimal("100.00"),
                List.of(new CadastroTarifaDetalhamentoDTO(ClassificacaoHospede.ADULTO, new BigInteger("50"))));
        validationsList.add(validation1);
        validationsList.add(validation2);

        tarifaService.cadastrar(dto);

        BDDMockito.then(validation1).should().validate(dto);
        BDDMockito.then(validation2).should().validate(dto);

    }

    @Test
    void deveriaCriarUriAoCadastrar() {

        CadastroTarifaDTO dto = new CadastroTarifaDTO("Tarifa", new BigDecimal("100.00"),
                List.of(new CadastroTarifaDetalhamentoDTO(ClassificacaoHospede.ADULTO, new BigInteger("50"))));
        Tarifa tarifa = new Tarifa(dto);
        tarifa.setId(1L);

        uriBuilder = UriComponentsBuilder.fromUriString("http://localhost:8080");

        URI uri = tarifaService.criarUri(tarifa, uriBuilder);

        assertEquals("http://localhost:8080/tarifas/1", uri.toString());

    }


}