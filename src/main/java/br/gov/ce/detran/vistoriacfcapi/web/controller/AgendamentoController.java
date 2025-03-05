package br.gov.ce.detran.vistoriacfcapi.web.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
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
// @CrossOrigin(origins = "*")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/agendamento")
public class AgendamentoController {

    private final AgendamentoService agendamentoService;
    
    @Operation(summary = "Cria um novo agendamento", responses = {
        @ApiResponse(responseCode = "201", description = "Agendamento criado com sucesso",
                     content = @Content(schema = @Schema(implementation = AgendamentoResponseDto.class))),
        @ApiResponse(responseCode = "400", description = "Requisição inválida",
                     content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PostMapping
    public ResponseEntity<AgendamentoResponseDto> create(@RequestBody AgendamentoCreateDto createDto) {
        log.info("Criando um novo agendamento: {}", createDto);
        AgendamentoResponseDto agendamento = agendamentoService.createAgendamento(null);
        return ResponseEntity.status(201).body(agendamento);
    }

    @Operation(summary = "Cancela um agendamento", responses = {
        @ApiResponse(responseCode = "204", description = "Agendamento cancelado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Agendamento não encontrado",
                     content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelarAgendamento(@PathVariable Long id, @RequestParam String motivo) {
        log.info("Cancelando agendamento ID: {} com motivo: {}", id, motivo);
        agendamentoService.cancelarAgendamento(0, motivo);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Lista horários disponíveis", responses = {
        @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso",
                     content = @Content(schema = @Schema(implementation = HorarioDisponivelDto.class)))
    })
    @GetMapping("/horarios-disponiveis")
    public ResponseEntity<List<HorarioDisponivelDto>> buscarHorariosDisponiveis() {
        log.info("Buscando horários disponíveis");
        List<HorarioDisponivelDto> horarios = agendamentoService.buscarHorariosDisponiveis(0, null);
        return ResponseEntity.ok(horarios);
    }
}