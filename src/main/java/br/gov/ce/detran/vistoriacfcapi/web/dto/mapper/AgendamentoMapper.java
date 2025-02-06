package br.gov.ce.detran.vistoriacfcapi.web.dto.mapper;



import org.springframework.stereotype.Component;

import br.gov.ce.detran.vistoriacfcapi.entity.Agendamento;
import br.gov.ce.detran.vistoriacfcapi.entity.CFC;
import br.gov.ce.detran.vistoriacfcapi.entity.Endereco;
import br.gov.ce.detran.vistoriacfcapi.entity.TipoVistoria;
import br.gov.ce.detran.vistoriacfcapi.entity.Usuario;
import br.gov.ce.detran.vistoriacfcapi.web.dto.AgendamentoCreateDto;

@Component
public class AgendamentoMapper {
    
    public Agendamento toAgendamento(AgendamentoCreateDto dto) {
        if (dto == null) {
            return null;
        }
        
        Agendamento agendamento = new Agendamento();
        
        if (dto.getCfcId() != null) {
            CFC cfc = new CFC();
            cfc.setId(dto.getCfcId());
            agendamento.setCFC(cfc);
        }
        
        if (dto.getUsuarioId() != null) {
            Usuario usuario = new Usuario();
            usuario.setId(dto.getUsuarioId());
            agendamento.setUsuario(usuario);
        }

        if (dto.getEnderecoId() != null) {
            Endereco endereco = new Endereco();
            endereco.setId(dto.getEnderecoId());
            agendamento.setEndereco(endereco);
        }
        
        agendamento.setDataHoraAgendamento(dto.getDataHoraAgendamento());
        agendamento.setDataHoraPreferencia(dto.getDataHoraPreferencia());
        if (dto.getTipoVistoria() != null) {
            agendamento.setTipoVistoria(TipoVistoria.valueOf(dto.getTipoVistoria().toUpperCase()));
        }
        agendamento.setPrimeiraVistoria(dto.isPrimeiraVistoria());
        agendamento.setObservacoes(dto.getObservacoes());
        
        return agendamento;
    }
}