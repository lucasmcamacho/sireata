package br.edu.utfpr.dv.sireata.window;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;

import br.edu.utfpr.dv.sireata.Session;
import br.edu.utfpr.dv.sireata.bo.UsuarioBO;
import br.edu.utfpr.dv.sireata.model.Usuario;
import com.vaadin.ui.Component;
import java.util.Arrays;
import java.util.List;

public class EditarSenhaWindow extends EditarWindow {
	
	private final PasswordField tfSenhaAtual = new PasswordField("Senha Atual");
	private final PasswordField tfNovaSenha = new PasswordField("Nova Senha");
	private final PasswordField tfConfirmacaoSenha = new PasswordField("Confirmação de Senha");
	
	public EditarSenhaWindow(){
		super("Alterar Senha", null);

		this.tfSenhaAtual.setWidth("400px");		
		this.tfNovaSenha.setWidth("400px");		
		this.tfConfirmacaoSenha.setWidth("400px");
		
                List<Component> componentList = Arrays.asList( this.tfSenhaAtual, this.tfNovaSenha, this.tfConfirmacaoSenha );   
		this.addCampo(componentList);
		
		this.tfSenhaAtual.focus();
	}
	
	@Override
	public void salvar() {
		try {
			if(!this.tfNovaSenha.getValue().equals(this.tfConfirmacaoSenha.getValue())){
				this.tfNovaSenha.focus();
				throw new Exception("As senhas não conferem.");
			}
			
			UsuarioBO bo = new UsuarioBO();
			Usuario usuario = bo.alterarSenha(Session.getUsuario().getIdUsuario(), this.tfSenhaAtual.getValue(), this.tfNovaSenha.getValue());
			
			Session.setUsuario(usuario);
			
			Notification.show("Alterar Senha", "Senha alterada com sucesso.", Notification.Type.HUMANIZED_MESSAGE);
			
			this.close();
		} catch (Exception e) {
			Logger.getGlobal().log(Level.SEVERE, e.getMessage(), e);
			
			Notification.show("Alterar Senha", e.getMessage(), Notification.Type.ERROR_MESSAGE);
		}
	}

}
