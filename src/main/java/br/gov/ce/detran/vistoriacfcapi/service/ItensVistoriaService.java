package br.gov.ce.detran.vistoriacfcapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.gov.ce.detran.vistoriacfcapi.entity.ItensVistoria;
import br.gov.ce.detran.vistoriacfcapi.repository.ItensVistoriaRepository;


@Service
public class ItensVistoriaService {

   private final ItensVistoriaRepository itensVistoriaRepository;
   
   public ItensVistoriaService(ItensVistoriaRepository itensVistoriaRepository) {
	   this.itensVistoriaRepository = itensVistoriaRepository;
   }
   
   public List<ItensVistoria> getAllItensVistoria() {
	   return itensVistoriaRepository.findAll();
	   }
   
   public Optional<ItensVistoria> getItensVistoriaById(Long id) {
	   return itensVistoriaRepository.findById(id);
   }
   
   public ItensVistoria saveItensVistoria(ItensVistoria itensVistoria) {
   	return itensVistoriaRepository.save(itensVistoria);
   }
   
   public void deleteItensVistoria(Long id ) {
	   itensVistoriaRepository.deleteById(id);
   }
}
