package dev.felipequeiroz.simplepms.service;

import dev.felipequeiroz.simplepms.domain.*;
import dev.felipequeiroz.simplepms.dto.tarifa.AtualizacaoTarifaDTO;
import dev.felipequeiroz.simplepms.dto.tarifa.CadastroTarifaDTO;
import dev.felipequeiroz.simplepms.dto.tarifa.CadastroTarifaDetalhamentoDTO;
import dev.felipequeiroz.simplepms.dto.unidadeHabitacional.AtualizacaoUnidadeHabitacionalDTO;
import dev.felipequeiroz.simplepms.repository.TarifaRepository;
import dev.felipequeiroz.simplepms.validations.tarifa.CadastrarTarifaValidations;
import org.junit.jupiter.api.DisplayName;
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
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    @DisplayName("Deveria atualizar os dados da tarifa ao atualizar com dados nao nulos")
    void atualizarComDadosNaoNulos() {

        AtualizacaoTarifaDTO atualizacaoTarifaDTO = new AtualizacaoTarifaDTO(1L, "Novo nome", new BigDecimal("150.0"), null);
        Tarifa tarifa = new Tarifa(1L, "Nome anterior", new BigDecimal("100.0"), List.of(tarifaDetalhamento), true);

        BDDMockito.given(tarifaRepository.getReferenceById(atualizacaoTarifaDTO.id())).willReturn(tarifa);

        Tarifa tarifaAtualizada = tarifaService.atualizar(atualizacaoTarifaDTO);

        assertEquals(atualizacaoTarifaDTO.id(), tarifaAtualizada.getId());
        assertEquals(atualizacaoTarifaDTO.nomeTarifa(), tarifaAtualizada.getNomeTarifa());
        assertEquals(atualizacaoTarifaDTO.valorBase(), tarifaAtualizada.getValorBase());

    }

    @Test
    @DisplayName("Deveria manter os dados atuais da tarifa ao atualizar com dados nulos")
    void atualizarComDadosNulos() {

        AtualizacaoTarifaDTO atualizacaoTarifaDTO = new AtualizacaoTarifaDTO(1L, null, null, null);
        Tarifa tarifa = new Tarifa(1L, "Nome anterior", new BigDecimal("100.0"), List.of(tarifaDetalhamento), true);

        BDDMockito.given(tarifaRepository.getReferenceById(atualizacaoTarifaDTO.id())).willReturn(tarifa);

        Tarifa tarifaAtualizada = tarifaService.atualizar(atualizacaoTarifaDTO);

        assertEquals(tarifa.getId(), tarifaAtualizada.getId());
        assertEquals(tarifa.getNomeTarifa(), tarifaAtualizada.getNomeTarifa());
        assertEquals(tarifa.getValorBase(), tarifaAtualizada.getValorBase());

    }

    @Test
    @DisplayName("Deveria alterar o parametro ativo para false ao receber um id valido e uh esta ativa")
    void excluirComIdValidoEUhAtivo() {

        tarifa.setId(1L);
        tarifa.setAtivo(true);
        BDDMockito.given(tarifaRepository.getReferenceById(1L)).willReturn(tarifa);

        tarifaService.excluir(1L);

        assertEquals(false, tarifa.getAtivo());

    }

    @Test
    @DisplayName("Deveria lancar exception ao receber um id valido e uh esta inativa")
    void excluirComIdValidoEUhInativo() {

        tarifa.setId(1L);
        tarifa.setAtivo(false);
        BDDMockito.given(tarifaRepository.getReferenceById(1L)).willReturn(tarifa);

        assertThrows(IllegalStateException.class, () -> tarifaService.excluir(1L));

    }


}