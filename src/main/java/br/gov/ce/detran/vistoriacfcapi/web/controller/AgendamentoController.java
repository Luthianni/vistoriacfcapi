package br.gov.ce.detran.vistoriacfcapi.web.controller;

import br.gov.ce.detran.vistoriacfcapi.entity.Agendamento;
import br.gov.ce.detran.vistoriacfcapi.entity.CFC;
import br.gov.ce.detran.vistoriacfcapi.entity.Endereco;
import br.gov.ce.detran.vistoriacfcapi.entity.StatusAgendamento;
import br.gov.ce.detran.vistoriacfcapi.entity.Usuario;
import br.gov.ce.detran.vistoriacfcapi.exception.EntityNotFoundException;
import br.gov.ce.detran.vistoriacfcapi.jwt.JwtUserDetails;
import br.gov.ce.detran.vistoriacfcapi.service.AgendamentoService;
import br.gov.ce.detran.vistoriacfcapi.service.CFCService;
import br.gov.ce.detran.vistoriacfcapi.service.EnderecoService;
import br.gov.ce.detran.vistoriacfcapi.service.UsuarioService;
import br.gov.ce.detran.vistoriacfcapi.web.dto.AgendamentoCreateDto;
import br.gov.ce.detran.vistoriacfcapi.web.dto.AgendamentoFiltroDto;
import br.gov.ce.detran.vistoriacfcapi.web.dto.AgendamentoResponseDto;
import br.gov.ce.detran.vistoriacfcapi.web.dto.CFCCreateDto;
import br.gov.ce.detran.vistoriacfcapi.web.dto.HorarioDisponivelDto;
import br.gov.ce.detran.vistoriacfcapi.web.dto.mapper.AgendamentoMapper;
import br.gov.ce.detran.vistoriacfcapi.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;

@Tag(name = "Agendamento Controller", description = "APIs para gerenciamento de agendamentos")
// @CrossOrigin(origins = "*")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/agendamento")
public class AgendamentoController {

    private final AgendamentoService agendamentoService;
    private final CFCService cfcService;
    private final EnderecoService enderecoService;
    private final AgendamentoMapper agendamentoMapper;
    private final UsuarioService usuarioService;

    @Operation(summary = "Criar novo agendamento", description = "Cria um novo agendamento de vistoria para um CFC", responses = {
            @ApiResponse(responseCode = "201", description = "Agendamento criado com sucesso", content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = AgendamentoResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class)))
    })

   @PostMapping
public ResponseEntity<AgendamentoResponseDto> criar(@Valid @RequestBody AgendamentoCreateDto createDto,
        @AuthenticationPrincipal JwtUserDetails userDetails) {
    log.info("Recebendo requisição de criação de agendamento: {}", createDto);

    // 1. Buscar ou criar usuário
    Usuario usuario = usuarioService.buscarPorUsername(userDetails.getUsername());

    // 2. Buscar ou criar CFC
    CFC cfc = cfcService.buscarPorCnpj(createDto.getCnpj())
            .orElseGet(() -> criarNovoCFC(createDto, usuario));

    // 3. Buscar ou criar endereço
    Endereco endereco = enderecoService
            .buscarPorCepENumero(createDto.getCep(), createDto.getNumero(), createDto.getEndereco())
            .orElseGet(() -> criarNovoEndereco(createDto));

    // 4. Criar o agendamento
    Agendamento agendamento = agendamentoMapper.toAgendamento(createDto);
    agendamento.setCFC(cfc);
    agendamento.setEndereco(endereco);
    AgendamentoResponseDto response = agendamentoService.criarAgendamento(agendamento);

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
}

private CFC criarNovoCFC(AgendamentoCreateDto createDto, Usuario usuario) {
    log.info("CFC não encontrado. Criando novo CFC...");
    
    CFC novoCfc = new CFC();
    // Campos obrigatórios
    novoCfc.setUsuario(usuario);
    novoCfc.setFantasia(createDto.getFantasia());        // Campo obrigatório que estava faltando
    novoCfc.setCnpj(createDto.getCnpj());
    novoCfc.setEmail(createDto.getEmail());
    
    // Campos adicionais
    novoCfc.setCentroDeFormacao(createDto.getCentroDeFormacao());
    novoCfc.setFantasia(createDto.getFantasia());
    novoCfc.setTipo(createDto.getTipo());
    novoCfc.setTelefone(createDto.getTelefone());
    
    // Datas de controle
    LocalDateTime agora = LocalDateTime.now();
    novoCfc.setDataCriacao(agora);
    novoCfc.setDataModificacao(agora);

    return cfcService.salvar(novoCfc);
}

private Endereco criarNovoEndereco(AgendamentoCreateDto createDto) {
    log.info("Endereço não encontrado. Criando novo endereço...");
    
    Endereco novoEndereco = new Endereco();
    novoEndereco.setEndereco(createDto.getEndereco());
    novoEndereco.setNumero(createDto.getNumero());
    novoEndereco.setBairro(createDto.getBairro());
    novoEndereco.setCidade(createDto.getCidade());
    novoEndereco.setEstado(createDto.getEstado());
    novoEndereco.setCep(createDto.getCep());
    
    return enderecoService.salvar(novoEndereco);
}

    @GetMapping
    public ResponseEntity<Page<AgendamentoResponseDto>> listar(
            @Parameter(description = "ID do CFC") @RequestParam(required = false) long cfc,

            @Parameter(description = "Status do Agendamento") @RequestParam(required = false) StatusAgendamento status,

            @Parameter(description = "Data início (formato: yyyy-MM-dd)") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,

            @Parameter(description = "Data fim(formato yyyy-MM-dd)") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim,

            @Parameter(description = "Número da página (0..N)") @RequestParam(defaultValue = "0") @PositiveOrZero Integer page,

            @Parameter(description = "Tamanho da página") @RequestParam(defaultValue = "10") @PositiveOrZero Integer size,

            @Parameter(description = "Critério de ordenação (formato: propriedade, asc|desc)") @RequestParam(defaultValue = "dataHoraAgendamento,desc") String sort) {

        AgendamentoFiltroDto filtro = AgendamentoFiltroDto.builder()
                .cfcId(cfc)
                .status(status)
                .dataInicio(dataInicio != null ? dataInicio.atStartOfDay() : null)
                .dataFim(dataFim != null ? dataFim.atTime(LocalTime.MAX) : null)
                .build();

        String[] sortArray = sort.split(",");
        Sort.Direction direction = sortArray.length > 1 ? Sort.Direction.fromString(sortArray[1]) : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page, size, direction, sortArray[0]);

        Page<AgendamentoResponseDto> response = agendamentoService.buscarAgendamentos(filtro, pageable);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Listar agendamentos", description = "Retorna uma lista paginada de agendamentos com filtros", responses = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso", content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = AgendamentoResponseDto.class)))
    })

    @GetMapping("/horarios-disponiveis")
    public ResponseEntity<List<HorarioDisponivelDto>> buscarHorariosDisponiveis(
            @Parameter(description = "ID do CFC", required = true) @RequestParam Long cfc,

            @Parameter(description = "Data para verificação (formato: yyyy-MM-dd)", required = true) @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {

        log.info("Buscando horários disponíveis para CFC {} na data {}", cfc, data);
        List<HorarioDisponivelDto> horarios = agendamentoService.buscarHorariosDisponiveis(cfc, data);
        return ResponseEntity.ok(horarios);
    }

    @Operation(summary = "Buscar horários disponíveis", description = "Retorna os horários disponíveis para agendamento em uma data específica", responses = {
            @ApiResponse(responseCode = "200", description = "BUsca realizada com sucesso", content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class)))
    })

    @PatchMapping("{id}/cancelar")
    public ResponseEntity<AgendamentoResponseDto> cancelar(
            @PathVariable long id,
            @RequestBody @NotBlank String motivo) {

        log.info("Cancelar agendamento: ID {} com motivo: {}", id, motivo);
        AgendamentoResponseDto response = agendamentoService.cancelarAgendamento(id, motivo);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Cancelar agendamento", description = "Cancela um agendamento existente", responses = {
            @ApiResponse(responseCode = "200", description = "Agendamento cancelado com sucesso", content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = AgendamentoResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Agendamento não pode ser cancelado", content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado", content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
    })
    public ResponseEntity<AgendamentoResponseDto> cancelarAgendamento(
            @PathVariable long id,
            @RequestBody @NotBlank String motivo) {
        log.info("Cancelando agendamento ID: {}", id);
        AgendamentoResponseDto response = agendamentoService.cancelarAgendamento(id, motivo);
        return ResponseEntity.ok(response);
    }
}