package br.com.rango.web.bean;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import br.com.rango.ngc.entity.Categoria;
import br.com.rango.ngc.entity.Cidade;
import br.com.rango.ngc.entity.Estabelecimento;
import br.com.rango.ngc.entity.Estado;
import br.com.rango.ngc.service.CategoriaService;
import br.com.rango.ngc.service.CidadeService;
import br.com.rango.ngc.service.EstabelecimentoService;
import br.com.rango.ngc.service.EstadoService;
import br.com.rango.ngc.util.jsf.AbstractBean;
import br.com.rango.ngc.util.jsf.JSFUtil;


@RequestScoped
@ManagedBean
public class EstabelecimentoBean extends AbstractBean<Estabelecimento, EstabelecimentoService>
{	
	private JSFUtil util = new JSFUtil();	
	
	private List<Estado> listaEstado;
	
	private List<Cidade> listaCidade;
	
	private List<Categoria> listaCategoria;
	
	private BigInteger idEstado;
	
	public EstabelecimentoBean()
	{
		super(EstabelecimentoService.getInstancia());
		this.ACTION_SEARCH = "estabelecimento";
		this.pageTitle = "Estabelecimento";
	}
	
	@Override
	protected void setValoresDefault() throws Exception
	{
		this.getSearchObject().setFgAtivo("T");	
	}
	
	@Override
	protected void setDefaultInserir() throws Exception
	{
		this.setIdEstado(null);
	}
	
	@Override
	protected void setListaPesquisa() throws Exception
	{
		this.popularEstado();
		this.popularCategoria();
		this.iniciarCidade();
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
	public void preparaAlterar()
	{
		try
		{
			super.preparaAlterar();
			this.setIdEstado(this.getEntityAlt().getCidade().getIdEstado());
			this.popularCidade();
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
			this.setIdEstado(this.getEntityAlt().getCidade().getIdEstado());
			this.popularCidade();
			super.preparaVisualizar();
		}
		catch (Exception e) 
		{
			FacesMessage message = new FacesMessage(e.getMessage());
	        message.setSeverity(FacesMessage.SEVERITY_ERROR);
	        FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}
	
	public void popularCidade() throws Exception
	{
		List<Cidade> listaAll = new ArrayList<Cidade>();
		
		Cidade cIni = new Cidade();
		cIni.setDsCidade("-- Selecione uma Cidade --");
		listaAll.add(cIni);
		
		if(this.getIdEstado() != null
				&& this.getIdEstado().intValue() > 0)
		{
			Cidade cidade = new Cidade();
			cidade.setIdEstado(this.getIdEstado());
			cidade.setFgAtivo("S");
			
			List<Cidade> lista = CidadeService.getInstancia().pesquisar(cidade, 0);
			listaAll.addAll(lista);
		}		
				
		this.setListaCidade(listaAll);
	}
	
	private void popularCategoria() throws Exception
	{
		List<Categoria> listaAll = new ArrayList<Categoria>();
		
		Categoria cIni = new Categoria();
		cIni.setDsCategoria("-- Selecione uma Categoria --");
		listaAll.add(cIni);
		
		Categoria categoria = new Categoria();
		categoria.setFgAtivo("S");
		
		List<Categoria> lista = CategoriaService.getInstancia().pesquisar(categoria, 0);
		listaAll.addAll(lista);
		
		this.setListaCategoria(listaAll);
	}
	
	private void iniciarCidade() throws Exception
	{
		Cidade cIni = new Cidade();
		cIni.setDsCidade("-- Selecione uma Cidade --");
		
		List<Cidade> lista = new ArrayList<Cidade>();
		lista.add(cIni);
		
		this.setListaCidade(lista);
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
	protected int getJoinPesquisar()
	{
		return EstabelecimentoService.JOIN_CATEGORIA
			 | EstabelecimentoService.JOIN_CIDADE
			 | EstabelecimentoService.JOIN_CIDADE_ESTADO;
	}
	
	@Override
	protected void completarInserir() throws Exception
	{
		this.getEntity().setDtCadastro(new Date());
		this.getEntity().setIdUsuarioCad(util.getUsuarioLogado().getIdUsuario());
		this.getEntity().setFgAtivo("S");
		this.getEntity().setFgStatus("I");
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
				EstabelecimentoService.getInstancia().inativar(this.getEntity());
				
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
				EstabelecimentoService.getInstancia().ativar(this.getEntity());
				
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
				|| this.getEntity().getDsEstabelecimento() == null
				|| this.getEntity().getDsEstabelecimento().trim().equals(""))
		{
			throw new Exception("O campo Descrição é obrigatório!");
		}
		
		if(this.getEntity() == null
				|| this.getIdEstado() == null
				|| this.getIdEstado().intValue() <= 0)
		{
			throw new Exception("O campo Estado é obrigatório!");
		}
		
		if(this.getEntity() == null
				|| this.getEntity().getIdCidade() == null
				|| this.getEntity().getIdCidade().intValue() <= 0)
		{
			throw new Exception("O campo Cidade é obrigatório!");
		}
		
		if(this.getEntity() == null
				|| this.getEntity().getIdCategoria() == null
				|| this.getEntity().getIdCategoria().intValue() <= 0)
		{
			throw new Exception("O campo Categoria é obrigatório!");
		}
	}

	public void ativarStatusEstabelecimento()
	{
		try
		{
			EstabelecimentoService.getInstancia().ativarStatusEstabelecimento();
		}
		catch (Exception e) 
		{
			FacesMessage message = new FacesMessage(e.getMessage());
	        message.setSeverity(FacesMessage.SEVERITY_ERROR);
	        FacesContext.getCurrentInstance().addMessage(null, message);
		}
		
	}
	
	public void inativarStatusEstabelecimento()
	{
		try
		{
			EstabelecimentoService.getInstancia().inativarStatusEstabelecimento();
		}
		catch (Exception e) 
		{
			FacesMessage message = new FacesMessage(e.getMessage());
	        message.setSeverity(FacesMessage.SEVERITY_ERROR);
	        FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}
	
	public List<Estado> getListaEstado() {
		return listaEstado;
	}

	public void setListaEstado(List<Estado> listaEstado) {
		this.listaEstado = listaEstado;
	}

	public BigInteger getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(BigInteger idEstado) {
		this.idEstado = idEstado;
	}

	public List<Cidade> getListaCidade() {
		return listaCidade;
	}

	public void setListaCidade(List<Cidade> listaCidade) {
		this.listaCidade = listaCidade;
	}

	public List<Categoria> getListaCategoria() {
		return listaCategoria;
	}

	public void setListaCategoria(List<Categoria> listaCategoria) {
		this.listaCategoria = listaCategoria;
	}
}
