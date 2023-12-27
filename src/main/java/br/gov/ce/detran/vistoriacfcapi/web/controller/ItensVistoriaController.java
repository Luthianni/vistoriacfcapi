package br.gov.ce.detran.vistoriacfcapi.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.ce.detran.vistoriacfcapi.entity.ItensVistoria;
import br.gov.ce.detran.vistoriacfcapi.service.ItensVistoriaService;

@RestController
@RequestMapping("/api/v1/itens-vistoria")
public class ItensVistoriaController {
	
	private final ItensVistoriaService itensVistoriaService;
	
	@Autowired
	public ItensVistoriaController(ItensVistoriaService itensVistoriaService) {
		this.itensVistoriaService = itensVistoriaService;
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
	
	@PostMapping
	public ResponseEntity<ItensVistoria> saveItensVistoria(@RequestBody ItensVistoria itensVistoria) {
		ItensVistoria savedItensVistoria = itensVistoriaService.saveItensVistoria(itensVistoria);
		return new ResponseEntity<>(savedItensVistoria, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteItensVistoria(@PathVariable Long id) {
		itensVistoriaService.deleteItensVistoria(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
