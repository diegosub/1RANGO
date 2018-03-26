package br.com.rango.web.bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import br.com.rango.ngc.entity.Usuario;
import br.com.rango.ngc.service.UsuarioService;
import br.com.rango.ngc.util.jsf.AbstractBean;
import br.com.rango.ngc.util.jsf.JSFUtil;


@RequestScoped
@ManagedBean
public class PerfilBean extends AbstractBean<Usuario, UsuarioService>
{
	private String senhaAtual;
	private String novaSenha;
	private String confirmaNovaSenha;
	
	private String mensagem;
	
	private JSFUtil util = new JSFUtil();	
	
	public PerfilBean()
	{
		super(UsuarioService.getInstancia());
		this.ACTION_SEARCH = "perfil";
		this.pageTitle = "Perfil";		
	}
	
	public String preparaPerfil()
	{
		try
		{
			this.setEntity(util.getUsuarioLogado());
			this.setCurrentState(STATE_EDIT);
		}
		catch (Exception e)
		{
			FacesMessage message = new FacesMessage(e.getMessage());
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			if(e.getMessage() == null)
				FacesContext.getCurrentInstance().addMessage("", message);
			else
				FacesContext.getCurrentInstance().addMessage(null, message);
		}
		
		return ACTION_SEARCH;
	}
	
	public void preparaAlterarSenha()
	{
		this.setSenhaAtual(null);
		this.setNovaSenha(null);
		this.setConfirmaNovaSenha(null);
	}
	
	public void alterarSenha()
	{
		try
		{
			this.setMensagem(null);
			this.validarAlterarSenha();
			
			UsuarioService.getInstancia().alterarSenha(this.getEntity(), senhaAtual, novaSenha);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sua senha foi alterada com sucesso.", null));			
		}
		catch (Exception e)
		{
			this.setMensagem(e.getMessage());
		}
	}
	
	private void validarAlterarSenha() throws Exception
	{
		if(this.getSenhaAtual() == null
				|| this.getSenhaAtual().trim().equals(""))
		{
			throw new Exception("O campo Senha Atual é obrigatório.");
		}
		
		if(this.getNovaSenha() == null
				|| this.getNovaSenha().trim().equals(""))
		{
			throw new Exception("O campo Nova Senha é obrigatório.");
		}
		
		if(this.getConfirmaNovaSenha() == null
				|| this.getConfirmaNovaSenha().trim().equals(""))
		{
			throw new Exception("O campo Confirma Nova Senha é obrigatório.");
		}
		
		if(this.getNovaSenha().length() < 5)
		{
			throw new Exception("A Nova Senha deve ter no mínimo 5 caracteres.");
		}
		
		if(!this.getNovaSenha().equals(this.getConfirmaNovaSenha()))
		{
			throw new Exception("O campo Nova Senha deve ser igual ao campo Confirma Nova Senha.");
		}
	}
	
	@Override
	public String getFullTitle()
	{	
		return this.pageTitle;
	}

	public String getSenhaAtual() {
		return senhaAtual;
	}

	public void setSenhaAtual(String senhaAtual) {
		this.senhaAtual = senhaAtual;
	}

	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	public String getConfirmaNovaSenha() {
		return confirmaNovaSenha;
	}

	public void setConfirmaNovaSenha(String confirmaNovaSenha) {
		this.confirmaNovaSenha = confirmaNovaSenha;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
}
