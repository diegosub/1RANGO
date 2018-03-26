package br.com.rango.web.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.rango.ngc.entity.Usuario;
import br.com.rango.ngc.service.UsuarioService;
import br.com.rango.ngc.util.jsf.AbstractBean;


@RequestScoped
@ManagedBean
public class RecuperarSenhaBean extends AbstractBean<Usuario, UsuarioService>
{	
	public RecuperarSenhaBean()
	{
		super(UsuarioService.getInstancia());
		this.ACTION_SEARCH = "recuperarSenha";
		this.pageTitle = "Recuperar Senha";
	}
}
