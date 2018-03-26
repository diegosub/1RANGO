package br.com.rango.web.bean;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import br.com.rango.ngc.entity.Categoria;
import br.com.rango.ngc.service.CategoriaService;
import br.com.rango.ngc.util.jsf.AbstractBean;
import br.com.rango.ngc.util.jsf.JSFUtil;


@RequestScoped
@ManagedBean
public class CategoriaBean extends AbstractBean<Categoria, CategoriaService>
{	
	private JSFUtil util = new JSFUtil();	
	
	public CategoriaBean()
	{
		super(CategoriaService.getInstancia());
		this.ACTION_SEARCH = "categoria";
		this.pageTitle = "Categoria";		
	}
	
	@Override
	protected void setValoresDefault() throws Exception
	{
		this.getSearchObject().setFgAtivo("T");		
	}
	
	@Override
	protected void completarPesquisar() throws Exception
	{
		if(this.getSearchObject().getFgAtivo() != null
				&& this.getSearchObject().getFgAtivo().equals("T"))
		{
			this.getSearchObject().setFgAtivo(null);
		}		
	}

	@Override
	protected void completarInserir() throws Exception
	{
		this.getEntity().setDtCadastro(new Date());
		this.getEntity().setIdUsuarioCad(util.getUsuarioLogado().getIdUsuario());
		this.getEntity().setFgAtivo("S");
	}
	
	@Override
	protected void completarAlterar() throws Exception 
	{
		this.validarInserir();
		this.getEntity().setDtAlteracao(new Date());
		this.getEntity().setIdUsuarioAlt(util.getUsuarioLogado().getIdUsuario());		
	}
	
	@Override
	public void clean(ActionEvent event)
	{
		super.clean(event);
		this.getSearchObject().setFgAtivo("T");
	}
	
	public void inativar() 
	{		
		try
		{
			if(this.getEntity() != null)
			{
				CategoriaService.getInstancia().inativar(this.getEntity());
				
				FacesMessage message = new FacesMessage("Registro inativado com sucesso!");
				message.setSeverity(FacesMessage.SEVERITY_INFO);
				FacesContext.getCurrentInstance().addMessage(null, message);
			}
		}
		catch (Exception e) 
		{
			FacesMessage message = new FacesMessage(e.getMessage());
	        message.setSeverity(FacesMessage.SEVERITY_ERROR);
	        FacesContext.getCurrentInstance().addMessage(null, message);
		}		
	}
	
	public void ativar() 
	{		
		try
		{
			if(this.getEntity() != null)
			{
				CategoriaService.getInstancia().ativar(this.getEntity());
				
				FacesMessage message = new FacesMessage("Registro ativado com sucesso!");
				message.setSeverity(FacesMessage.SEVERITY_INFO);
				FacesContext.getCurrentInstance().addMessage(null, message);
			}
		}
		catch (Exception e) 
		{
			FacesMessage message = new FacesMessage(e.getMessage());
	        message.setSeverity(FacesMessage.SEVERITY_ERROR);
	        FacesContext.getCurrentInstance().addMessage(null, message);
		}		
	}
	
	@Override
	protected void validarInserir() throws Exception
	{
		if(this.getEntity() == null
				|| this.getEntity().getDsCategoria() == null
				|| this.getEntity().getDsCategoria().trim().equals(""))
		{
			throw new Exception("O campo Descrição é obrigatório!");
		}
	}
}
