package br.com.rango.web.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.ibm.icu.text.DecimalFormat;

import br.com.rango.ngc.entity.Produto;
import br.com.rango.ngc.entity.Segmento;
import br.com.rango.ngc.service.ProdutoService;
import br.com.rango.ngc.service.SegmentoService;
import br.com.rango.ngc.util.jsf.AbstractBean;
import br.com.rango.ngc.util.jsf.JSFUtil;


@RequestScoped
@ManagedBean
public class ProdutoBean extends AbstractBean<Produto, ProdutoService>
{
	private List<Segmento> listaSegmento;
	
	private JSFUtil util = new JSFUtil();	
	
	public ProdutoBean()
	{
		super(ProdutoService.getInstancia());
		this.ACTION_SEARCH = "produto";
		this.pageTitle = "Produto";		
	}
	
	@Override
	protected void setValoresDefault() throws Exception
	{
		this.popularSegmento();
		this.getSearchObject().setFgAtivo("T");
	}
	
	private void popularSegmento() throws Exception
	{
		List<Segmento> listaAll = new ArrayList<Segmento>();
		
		Segmento sIni = new Segmento();
		sIni.setDsSegmento("-- Selecione um Segmento --");
		listaAll.add(sIni);
		
		Segmento segmento = new Segmento();
		segmento.setIdEstabelecimento(util.getUsuarioLogado().getIdEstabelecimento());
		segmento.setFgAtivo("S");
		
		List<Segmento> lista = SegmentoService.getInstancia().pesquisar(segmento, 0);
		listaAll.addAll(lista);
				
		this.setListaSegmento(listaAll);
	}
	
	@Override
	protected int getJoinPesquisar()
	{
		return ProdutoService.JOIN_SEGMENTO;
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
		this.getEntity().setVlProduto(new Double(this.getEntity().getStrVlProduto().toString().replace(".", "").replace(",", ".")));		
		this.getEntity().setIdEstabelecimento(util.getUsuarioLogado().getIdEstabelecimento());
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
				ProdutoService.getInstancia().inativar(this.getEntity());
				
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
				ProdutoService.getInstancia().ativar(this.getEntity());
				
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
	public void preparaAlterar()
	{
		try
		{
			this.getEntityAlt().setStrVlProduto(new DecimalFormat("#,##0.00").format(this.getEntityAlt().getVlProduto()));
			super.preparaAlterar();
		}
		catch (Exception e) 
		{
			FacesMessage message = new FacesMessage(e.getMessage());
	        message.setSeverity(FacesMessage.SEVERITY_ERROR);
	        FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}
	
	@Override
	public void preparaVisualizar()
	{
		try
		{
			this.getEntityAlt().setStrVlProduto(new DecimalFormat("#,##0.00").format(this.getEntityAlt().getVlProduto()));			
			super.preparaVisualizar();
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
				|| this.getEntity().getDsProduto() == null
				|| this.getEntity().getDsProduto().trim().equals(""))
		{
			throw new Exception("O campo Descrição é obrigatório!");
		}
		
		if(this.getEntity() == null
				|| this.getEntity().getDsAdicional() == null
				|| this.getEntity().getDsAdicional().trim().equals(""))
		{
			throw new Exception("O campo Adicional é obrigatório!");
		}
		
		if(this.getEntity() == null
				|| this.getEntity().getStrVlProduto() == null
				|| this.getEntity().getStrVlProduto().trim().equals(""))
		{
			throw new Exception("O campo Valor é obrigatório!");
		}
		
		if(this.getEntity() == null
				|| this.getEntity().getIdSegmento() == null
				|| this.getEntity().getIdSegmento().intValue() <= 0)
		{
			throw new Exception("O campo Segmento é obrigatório!");
		}
	}

	public List<Segmento> getListaSegmento() {
		return listaSegmento;
	}

	public void setListaSegmento(List<Segmento> listaSegmento) {
		this.listaSegmento = listaSegmento;
	}
}
