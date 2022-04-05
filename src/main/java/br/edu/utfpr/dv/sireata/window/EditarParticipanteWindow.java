package br.edu.utfpr.dv.sireata.window;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;

import br.edu.utfpr.dv.sireata.bo.AtaParticipanteBO;
import br.edu.utfpr.dv.sireata.component.ComboUsuario;
import br.edu.utfpr.dv.sireata.model.AtaParticipante;
import com.vaadin.ui.Component;
import java.util.Arrays;
import java.util.List;

public class EditarParticipanteWindow extends EditarWindow {

	private final AtaParticipante participante;
	private final EditarAtaWindow parentWindow;
	
	private final ComboUsuario cbUsuario;
	private final TextField tfDesignacao = new TextField("Designação");
	private final CheckBox cbPresente = new CheckBox("Presente");
	private final TextField tfMotivo = new TextField("Motivo da Ausência");
	private final CheckBox cbMembro = new CheckBox("Membro do órgão");
	
	public EditarParticipanteWindow(AtaParticipante participante, EditarAtaWindow parentWindow){
		super("Editar Participante", null);
		
		if(participante == null){
			this.participante = new AtaParticipante();
		}else{
			this.participante = participante;
		}
		
		this.parentWindow = parentWindow;
		
		this.cbUsuario = new ComboUsuario("Participante");
		this.cbUsuario.setWidth("400px");

		this.tfDesignacao.setWidth("400px");

		this.tfMotivo.setWidth("400px");
		
                List<Component> componentList = Arrays.asList( this.cbUsuario, this.tfDesignacao, this.cbMembro, this.cbPresente, this.tfMotivo );   
		this.addCampo(componentList);
		
		this.carregarParticipante();
	}
	
	private void carregarParticipante(){
		this.cbUsuario.setUsuario(this.participante.getParticipante());
		this.tfDesignacao.setValue(this.participante.getDesignacao());
		this.cbPresente.setValue(this.participante.isPresente());
		this.tfMotivo.setValue(this.participante.getMotivo());
		this.cbMembro.setValue(this.participante.isMembro());
	}
	
	@Override
	public void salvar() {
		try{
			this.participante.setParticipante(this.cbUsuario.getUsuario());
			this.participante.setDesignacao(this.tfDesignacao.getValue());
			this.participante.setPresente(this.cbPresente.getValue());
			this.participante.setMotivo(this.tfMotivo.getValue());
			this.participante.setMembro(this.cbMembro.getValue());
			
			AtaParticipanteBO bo = new AtaParticipanteBO();
			
			bo.validarDados(this.participante);
			
			if(this.participante.getAta().getIdAta() > 0){
				bo.salvar(this.participante);
			}
			
			Notification.show("Salvar Participante", "Participante salvo com sucesso.", Notification.Type.HUMANIZED_MESSAGE);
			
			this.parentWindow.atualizarParticipante(this.participante);
			
			this.close();
		}catch(Exception e){
			Logger.getGlobal().log(Level.SEVERE, e.getMessage(), e);
			
			Notification.show("Salvar Participante", e.getMessage(), Notification.Type.ERROR_MESSAGE);
		}
	}

}
