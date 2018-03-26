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
public class PrincipalBean extends AbstractBean<Usuario, UsuarioService>
{
	private static final String ACAO_LOGOUT  = "login";
	
	private JSFUtil util = new JSFUtil();
	
	public PrincipalBean()
	{		
		
	}
	
	private void atualizarCountAgendamentos()
	{
		try
		{
			
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
	}
}
