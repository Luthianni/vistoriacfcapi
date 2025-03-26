package br.gov.ce.detran.vistoriacfcapi.web.controller;

import java.util.HashMap;
import java.util.List;

import br.gov.ce.detran.vistoriacfcapi.entity.Agendamento;
import br.gov.ce.detran.vistoriacfcapi.entity.Endereco;
import br.gov.ce.detran.vistoriacfcapi.entity.StatusAgendamento;
import br.gov.ce.detran.vistoriacfcapi.entity.TipoVistoria;
import br.gov.ce.detran.vistoriacfcapi.jwt.JwtUserDetails;
import br.gov.ce.detran.vistoriacfcapi.repository.AgendamentoRepository;
import br.gov.ce.detran.vistoriacfcapi.service.CFCService;
import br.gov.ce.detran.vistoriacfcapi.service.UsuarioService;
import br.gov.ce.detran.vistoriacfcapi.web.dto.mapper.AgendamentoMapper;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.gov.ce.detran.vistoriacfcapi.service.AgendamentoService;
import br.gov.ce.detran.vistoriacfcapi.web.dto.AgendamentoCreateDto;
import br.gov.ce.detran.vistoriacfcapi.web.dto.AgendamentoResponseDto;
import br.gov.ce.detran.vistoriacfcapi.web.dto.HorarioDisponivelDto;
import br.gov.ce.detran.vistoriacfcapi.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



@Tag(name = "Agendamento Controller", description = "APIs para gerenciamento de agendamentos")
@RestController
@RequestMapping("api/v1/agendamento")
@RequiredArgsConstructor
public class AgendamentoController {

    private static final Logger logger = LoggerFactory.getLogger(AgendamentoController.class);
    private final AgendamentoService agendamentoService;
    private final UsuarioService usuarioService;
    private final CFCService cfcService;
    private final AgendamentoRepository agendamentoRepository;

    @Operation(summary = "Cria um novo agendamento", responses = {
            @ApiResponse(responseCode = "201", description = "Agendamento criado com sucesso",
                    content = @Content(schema = @Schema(implementation = AgendamentoResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PostMapping
    public ResponseEntity<AgendamentoResponseDto> create(@RequestBody @Valid AgendamentoCreateDto createDto,
                                                         @AuthenticationPrincipal JwtUserDetails userDetails) {
        logger.info("Criando um novo agendamento: {}", createDto);

        // Criar a entidade Agendamento
        Agendamento agendamento = new Agendamento();
        agendamento.setDataHoraAgendamento(createDto.getDataHoraAgendamento());

        // Definir valores padrão para campos obrigatórios
        agendamento.setStatus(StatusAgendamento.PENDENTE);
        agendamento.setPrimeiraVistoria(false); // Ajuste conforme necessário
        agendamento.setTipoVistoria(TipoVistoria.INICIAL); // Valor padrão válido

        // Mapear Endereco
        Endereco endereco = new Endereco();
        endereco.setEndereco(createDto.getEndereco());
        endereco.setNumero(createDto.getNumero());
        endereco.setBairro(createDto.getBairro());
        endereco.setCep(createDto.getCep());
        endereco.setCidade(createDto.getCidade());
        endereco.setEstado(createDto.getEstado());
        agendamento.setEndereco(endereco);

        // Buscar e definir CFC e Usuario
        agendamento.setCFC(cfcService.buscarPorId(createDto.getCfcId()));
        agendamento.setUsuario(usuarioService.buscarPorId(userDetails.getId()));

        // Salvar o agendamento (o Endereco será salvo automaticamente pelo cascade)
        Agendamento savedAgendamento = agendamentoRepository.save(agendamento);

        // Log de resultado
        HashMap<Object, Object> result = new HashMap<>();
        result.put("endereco", createDto.getEndereco());
        result.put("numero", createDto.getNumero());
        result.put("bairro", createDto.getBairro());
        result.put("cidade", createDto.getCidade());
        result.put("estado", createDto.getEstado());
        result.put("cep", createDto.getCep());
        result.put("dataHoraAgendamento", createDto.getDataHoraAgendamento());
        logger.info("Agendamento criado: {}", result);

        return ResponseEntity.status(HttpStatus.CREATED).body(AgendamentoMapper.toDto(savedAgendamento));
    }


    @Operation(summary = "Cancela um agendamento", responses = {
        @ApiResponse(responseCode = "204", description = "Agendamento cancelado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Agendamento não encontrado",
                     content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelarAgendamento(@PathVariable Long id, @RequestParam String motivo) {
        //log.info("Cancelando agendamento ID: {} com motivo: {}", id, motivo);
        agendamentoService.cancelarAgendamento(0, motivo);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Lista horários disponíveis", responses = {
        @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso",
                     content = @Content(schema = @Schema(implementation = HorarioDisponivelDto.class)))
    })
    @GetMapping("/horarios-disponiveis")
    public ResponseEntity<List<HorarioDisponivelDto>> buscarHorariosDisponiveis() {
        //log.info("Buscando horários disponíveis");
        List<HorarioDisponivelDto> horarios = agendamentoService.buscarHorariosDisponiveis(0, null);
        return ResponseEntity.ok(horarios);
    }

    @Operation(summary = "Lista todos os agendamentos")
    @GetMapping
    public ResponseEntity<List<AgendamentoResponseDto>> getAll() {
        List<Agendamento> agendamentos = agendamentoRepository.findAll();
        return ResponseEntity.ok(AgendamentoMapper.toListDto(agendamentos));
    }
}