package br.gov.ce.detran.vistoriacfcapi.web.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.ce.detran.vistoriacfcapi.entity.ItensVistoria;
import br.gov.ce.detran.vistoriacfcapi.entity.Vistoria;
import br.gov.ce.detran.vistoriacfcapi.jwt.JwtUserDetails;
import br.gov.ce.detran.vistoriacfcapi.service.ItensVistoriaService;
import br.gov.ce.detran.vistoriacfcapi.service.UsuarioService;
import br.gov.ce.detran.vistoriacfcapi.service.VistoriaService;
import br.gov.ce.detran.vistoriacfcapi.web.dto.ItensVistoriaCreateDto;
import br.gov.ce.detran.vistoriacfcapi.web.dto.ItensVistoriaResponseDto;
import br.gov.ce.detran.vistoriacfcapi.web.dto.mapper.ItensVistoriaMapper;
import br.gov.ce.detran.vistoriacfcapi.web.dto.mapper.VistoriaMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/itens-vistoria")
public class ItensVistoriaController {
	
	private final ItensVistoriaService itensVistoriaService;
	private final UsuarioService usuarioService;
	private final VistoriaService vistoriaService;	
	
		
	@PostMapping
	public ResponseEntity<ItensVistoriaResponseDto> saveItensVistoria(@RequestBody @Valid ItensVistoriaCreateDto dto,
            @AuthenticationPrincipal JwtUserDetails userDetails) {

		Vistoria vistoria = VistoriaMapper.toVistoria(dto);
		ItensVistoria itensVistoria = ItensVistoriaMapper.toItensVistoria(dto);

		itensVistoria.setVistoria(vistoria);
		itensVistoria.setUsuario(usuarioService.buscarPorId(userDetails.getId()));
		itensVistoria.setSala(dto.isSala() ? ItensVistoria.VistoriaStatus.SIM : ItensVistoria.VistoriaStatus.NAO);
		itensVistoria.setBanheiro(dto.isBanheiro() ? ItensVistoria.VistoriaStatus.SIM : ItensVistoria.VistoriaStatus.NAO);
		itensVistoria.setTreinamento(dto.isTreinamento() ? ItensVistoria.VistoriaStatus.SIM : ItensVistoria.VistoriaStatus.NAO);
		itensVistoria.setPortadorDeficiencia(dto.isPortadorDeficiencia() ? ItensVistoria.VistoriaStatus.SIM : ItensVistoria.VistoriaStatus.NAO);
		itensVistoria.setSuporte(dto.isSuporte() ? ItensVistoria.VistoriaStatus.SIM : ItensVistoria.VistoriaStatus.NAO);
		itensVistoria.setObservacao(dto.getObservacao());
		itensVistoriaService.saveItensVistoria(itensVistoria);
		
		HashMap<Object, Object> response = new HashMap<>();
		HashMap<Object, Object> result = new HashMap<>();

		result.put("sala", dto.isSala());
		result.put("banheiro", dto.isBanheiro());
		result.put("treinamento", dto.isTreinamento());
		result.put("Portador de Deficiencia", dto.isPortadorDeficiencia());
		result.put("suporte", dto.isSuporte());
		result.put("observacao", dto.getObservacao());

		response.put("result", result);
		return ResponseEntity.status(HttpStatus.CREATED).body(ItensVistoriaMapper.toDto(itensVistoria));
	}
	
	@GetMapping
	public ResponseEntity<List<ItensVistoria>> getAllItensVistoria() {
		List<ItensVistoria> itensVistoriaList = itensVistoriaService.getAllItensVistoria();
		return new ResponseEntity<>(itensVistoriaList, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ItensVistoria> getItensVistoriaById(@PathVariable Long id) {
		return itensVistoriaService.getItensVistoriaById(id)
				.map(ItensVistoria -> new ResponseEntity<>(ItensVistoria, HttpStatus.OK)) 
				.orElseGet(() -> new ResponseEntity<> (HttpStatus.NOT_FOUND));
	}	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteItensVistoria(@PathVariable Long id) {
		itensVistoriaService.deleteItensVistoria(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
