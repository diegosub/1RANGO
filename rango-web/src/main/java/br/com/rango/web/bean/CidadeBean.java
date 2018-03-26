package br.com.rango.web.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import br.com.rango.ngc.entity.Cidade;
import br.com.rango.ngc.entity.Estado;
import br.com.rango.ngc.service.CidadeService;
import br.com.rango.ngc.service.EstadoService;
import br.com.rango.ngc.util.jsf.AbstractBean;
import br.com.rango.ngc.util.jsf.JSFUtil;


@RequestScoped
@ManagedBean
public class CidadeBean extends AbstractBean<Cidade, CidadeService>
{
	private List<Estado> listaEstado;
	
	private JSFUtil util = new JSFUtil();	
	
	public CidadeBean()
	{
		super(CidadeService.getInstancia());
		this.ACTION_SEARCH = "cidade";
		this.pageTitle = "Cidade";		
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
	protected void setListaPesquisa() throws Exception
	{
		this.popularEstado();
	}
	
	private void popularEstado() throws Exception
	{
		List<Estado> listaAll = new ArrayList<Estado>();
		
		Estado eIni = new Estado();
		eIni.setDsEstado("-- Selecione um Estado --");
		listaAll.add(eIni);
		
		
		List<Estado> lista = EstadoService.getInstancia().pesquisar(new Estado(), 0);
		listaAll.addAll(lista);
		
		this.setListaEstado(listaAll);
	}
	
	@Override
	protected int getJoinPesquisar()
	{		
		return CidadeService.JOIN_ESTADO;
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
				CidadeService.getInstancia().inativar(this.getEntity());
				
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
				CidadeService.getInstancia().ativar(this.getEntity());
				
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
				|| this.getEntity().getDsCidade() == null
				|| this.getEntity().getDsCidade().trim().equals(""))
		{
			throw new Exception("O campo Descrição é obrigatório!");
		}
		
		if(this.getEntity() == null
				|| this.getEntity().getIdEstado() == null
				|| this.getEntity().getIdEstado().intValue() <= 0)
		{
			throw new Exception("O campo Estado é obrigatório!");
		}
	}

	public List<Estado> getListaEstado() {
		return listaEstado;
	}

	public void setListaEstado(List<Estado> listaEstado) {
		this.listaEstado = listaEstado;
	}
}
