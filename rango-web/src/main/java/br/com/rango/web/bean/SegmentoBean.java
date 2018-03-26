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

import org.hibernate.Criteria;

import br.com.rango.ngc.entity.Dominio;
import br.com.rango.ngc.entity.Estado;
import br.com.rango.ngc.entity.Segmento;
import br.com.rango.ngc.entity.SegmentoAdicional;
import br.com.rango.ngc.entity.SegmentoDia;
import br.com.rango.ngc.entity.SegmentoItem;
import br.com.rango.ngc.service.DominioService;
import br.com.rango.ngc.service.EstadoService;
import br.com.rango.ngc.service.SegmentoAdicionalService;
import br.com.rango.ngc.service.SegmentoService;
import br.com.rango.ngc.util.jsf.AbstractBean;
import br.com.rango.ngc.util.jsf.JSFUtil;
import br.com.rango.ngc.util.jsf.Variaveis;


@RequestScoped
@ManagedBean
public class SegmentoBean extends AbstractBean<Segmento, SegmentoService>
{
	private SegmentoAdicional segmentoAdicional;
	
	private List<Estado> listaEstado;
	private List<SegmentoAdicional> listaPerguntas;
	private List<SegmentoAdicional> listaPerguntasBase;
	private List<SegmentoItem> listaItens;
	private List<Dominio> listaSemana;
	
	private SegmentoItem segmentoItem;
	
	private BigInteger[] dias;
	
	private String activeTab;
	
	private JSFUtil util = new JSFUtil();	
	
	public SegmentoBean()
	{
		super(SegmentoService.getInstancia());
		this.ACTION_SEARCH = "segmento";
		this.pageTitle = "Segmento";
	}
	
	@Override
	protected void setValoresDefault() throws Exception
	{
		//LISTA SEMANAS
		Dominio dominio = new Dominio();
		dominio.setNmCampo("DIA_SEMANA");
		List<Dominio> listaSemana = DominioService.getInstancia().pesquisar(dominio, 0);
		this.setListaSemana(listaSemana);
		dias = new BigInteger[] {new BigInteger("1"), new BigInteger("2"), new BigInteger("3"), 
								 new BigInteger("4"), new BigInteger("5"), new BigInteger("6"), new BigInteger("7")};
		
		this.getSearchObject().setFgAtivo("T");		
	}
	
	@Override
	protected void setDefaultInserir() throws Exception
	{
		this.setActiveTab("Geral");
		this.setSegmentoAdicional(new SegmentoAdicional());
		this.getSegmentoAdicional().setFgMultiplosItens("S");
		this.getSegmentoAdicional().setFgObrigatorio("S");
		
		List<SegmentoItem> lista = new ArrayList<SegmentoItem>();		
		SegmentoItem segmentoItem = new SegmentoItem();
		segmentoItem.setIdSegmentoItem(new BigInteger("1"));		
		lista.add(segmentoItem);		
		this.setListaItens(lista);
		
		this.setListaPerguntas(new ArrayList<SegmentoAdicional>());
		this.setDias(new BigInteger[7]);
	}
	
	@Override
	protected void completarPesquisar() throws Exception
	{
		if(util.getUsuarioLogado().getIdEstabelecimento() == null
				|| util.getUsuarioLogado().getIdEstabelecimento().intValue() <= 0)
		{
			throw new Exception("O usuário logado não pertence a um estabelecimento.");
		}
		
		if(this.getSearchObject().getFgAtivo() != null
				&& this.getSearchObject().getFgAtivo().equals("T"))
		{
			this.getSearchObject().setFgAtivo(null);
		}
		
		this.getSearchObject().setIdEstabelecimento(util.getUsuarioLogado().getIdEstabelecimento());
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
		return SegmentoService.JOIN_SEGMENTO_DIA
			 | SegmentoService.JOIN_USUARIO_CAD
			 | SegmentoService.JOIN_USUARIO_ALT;
	}
	
	public void pesquisar(ActionEvent event)
	{
		try
		{
			if(validarAcesso(Variaveis.ACAO_PESQUISAR))
			{
				validarPesquisar();
				completarPesquisar();
				validarCampoTexto();
				List<Segmento> lista = SegmentoService.getInstancia().pesquisar(this.getSearchObject(), getJoinPesquisar(), Criteria.DISTINCT_ROOT_ENTITY);
				this.setSearchResult(lista);
				completarPosPesquisar();
				setCurrentState(STATE_SEARCH);
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
	protected void completarInserir() throws Exception
	{
		this.getEntity().setListaSegmentoDia(new ArrayList<SegmentoDia>());
		
		if(util.getUsuarioLogado().getIdEstabelecimento() == null
				|| util.getUsuarioLogado().getIdEstabelecimento().intValue() <= 0)
		{
			throw new Exception("O usuário logado não pertence a um estabelecimento.");
		}
		else
		{
			this.getEntity().setIdEstabelecimento(util.getUsuarioLogado().getIdEstabelecimento());
			this.getEntity().setDtCadastro(new Date());
			this.getEntity().setIdUsuarioCad(util.getUsuarioLogado().getIdUsuario());
			this.getEntity().setFgAtivo("S");
			
			this.getEntity().setListaSegmentoAdicional(this.getListaPerguntas());
			
			if(dias != null
					&& dias.length > 0)
			{
				for (BigInteger obj : dias)
				{
					SegmentoDia segmentoDia = new SegmentoDia();
					segmentoDia.setIdDia(obj);
					this.getEntity().getListaSegmentoDia().add(segmentoDia);
				}
			}
		}
	}
	
	@Override
	public void preparaAlterar()
	{
		try
		{
			super.preparaAlterar();
			
			//LISTA SEMANAS
			this.setDias(new BigInteger[7]);
			
			if(this.getEntityAlt().getListaSegmentoDia() != null
					&& this.getEntityAlt().getListaSegmentoDia().size() > 0)
			{
				for (int i = 0; i < this.getEntityAlt().getListaSegmentoDia().size(); i++)
				{
					this.getDias()[i] = this.getEntityAlt().getListaSegmentoDia().get(i).getIdDia();
				}
			}
			
			//LISTA PERGUNTAS
			SegmentoAdicional segmentoAdicional = new SegmentoAdicional();
			segmentoAdicional.setIdSegmento(this.getEntityAlt().getIdSegmento());
			segmentoAdicional.setFgAtivo("S");
			
			List<SegmentoAdicional> lista = SegmentoAdicionalService.getInstancia().pesquisar(segmentoAdicional, SegmentoAdicionalService.JOIN_SEGMENTO_ITEM,
																																 Criteria.DISTINCT_ROOT_ENTITY);
			this.setListaPerguntas(lista);
			this.setListaPerguntasBase(new ArrayList<SegmentoAdicional>());
			this.getListaPerguntasBase().addAll(lista);
			
			this.limparFormPergunta();			
			this.setActiveTab("Geral");			
		}
		catch (Exception e)
		{
			FacesMessage message = new FacesMessage(e.getMessage());
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}
	
	@Override
	protected void completarAlterar() throws Exception 
	{
		if(util.getUsuarioLogado().getIdEstabelecimento() == null
				|| util.getUsuarioLogado().getIdEstabelecimento().intValue() <= 0)
		{
			throw new Exception("O usuário logado não pertence a um estabelecimento.");
		}
		else
		{
			this.validarInserir();
			this.getEntity().setIdEstabelecimento(util.getUsuarioLogado().getIdEstabelecimento());
			this.getEntity().setDtAlteracao(new Date());
			this.getEntity().setIdUsuarioAlt(util.getUsuarioLogado().getIdUsuario());
			this.getEntity().setFgAtivo("S");
			
			this.getEntity().setListaSegmentoAdicional(this.getListaPerguntas());
			this.getEntity().setListaSegmentoAdicionalBase(this.getListaPerguntasBase());
			
			this.getEntity().setListaSegmentoDia(new ArrayList<SegmentoDia>());
			
			if(dias != null
					&& dias.length > 0)
			{
				for (BigInteger obj : dias)
				{
					SegmentoDia segmentoDia = new SegmentoDia();
					segmentoDia.setIdDia(obj);
					this.getEntity().getListaSegmentoDia().add(segmentoDia);
				}
			}		
		}	
	}

	@Override
	public void clean(ActionEvent event)
	{	
		super.clean(event);
		this.getSearchObject().setFgAtivo("T");
	}
	
	@Override
	protected void validarInserir() throws Exception
	{
		if(this.getEntity().getDsSegmento() == null
				|| this.getEntity().getDsSegmento().trim().equals(""))
		{
			throw new Exception("O campo Descrição é obrigatório.");
		}
	}
	
	public void inativar() 
	{		
		try
		{
			if(this.getEntity() != null)
			{
				SegmentoService.getInstancia().inativar(this.getEntity());
				
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
				SegmentoService.getInstancia().ativar(this.getEntity());
				
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
	
	public void adicionarLinhaItem()
	{
		try
		{	
			this.validarListaItens();
			SegmentoItem segmentoItem = new SegmentoItem();
			segmentoItem.setIdSegmentoItem(new BigInteger((this.getListaItens().get(this.getListaItens().size() - 1).getIdSegmentoItem().intValue() + 1) + ""));
			
			this.getListaItens().add(segmentoItem);
		}
		catch (Exception e) 
		{
			FacesMessage message = new FacesMessage(e.getMessage());
	        message.setSeverity(FacesMessage.SEVERITY_ERROR);
	        FacesContext.getCurrentInstance().addMessage(null, message);
		}
		
	}
	
	public void removerPergunta()
	{
		try
		{
			this.getListaPerguntas().remove(this.getSegmentoAdicional());
			this.limparFormPergunta();
		}
		catch (Exception e) 
		{
			FacesMessage message = new FacesMessage(e.getMessage());
	        message.setSeverity(FacesMessage.SEVERITY_ERROR);
	        FacesContext.getCurrentInstance().addMessage(null, message);
		}	
	}
	
	public void removerLinhaItem()
	{
		for (int i = 0; i < this.getListaItens().size(); i++)
		{
			if(this.getListaItens().get(i).getIdSegmentoItem().intValue() == this.getSegmentoItem().getIdSegmentoItem().intValue())
			{
				this.getListaItens().remove(i);
			}
		}
	}
	
	public void adicionarPergunta()
	{
		try
		{
			this.validarListaItens();		
			this.getSegmentoAdicional().setListaSegmentoItem(this.getListaItens());
			
			this.getListaPerguntas().add(this.getSegmentoAdicional());
			this.limparFormPergunta();
		}
		catch (Exception e) 
		{
			FacesMessage message = new FacesMessage(e.getMessage());
	        message.setSeverity(FacesMessage.SEVERITY_ERROR);
	        FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}
	
	private void validarListaItens() throws Exception
	{
		for (SegmentoItem segmentoItem : this.getListaItens())
		{
			
			if(segmentoItem.getDsSegmentoItem() == null
					|| segmentoItem.getDsSegmentoItem().trim().equals("")
					|| segmentoItem.getStrVlAdicional() == null
					|| segmentoItem.getStrVlAdicional().trim().equals(""))
			{
				throw new Exception("Preencha os dados de todos os itens da pergunta.");
			}
			
			segmentoItem.setVlAdicional(new Double(segmentoItem.getStrVlAdicional().toString().replace(".", "").replace(",", ".")));
		}
	}
	
	private void limparFormPergunta() throws Exception
	{
		this.setSegmentoAdicional(new SegmentoAdicional());
		this.getSegmentoAdicional().setFgMultiplosItens("S");
		this.getSegmentoAdicional().setFgObrigatorio("S");
		
		List<SegmentoItem> lista = new ArrayList<SegmentoItem>();		
		SegmentoItem segmentoItem = new SegmentoItem();
		segmentoItem.setIdSegmentoItem(new BigInteger("1"));		
		lista.add(segmentoItem);		
		this.setListaItens(lista);
	}
	
	@Override
	public void preparaVisualizar()
	{
		try
		{
			//LISTA SEMANAS
			this.setDias(new BigInteger[7]);
			
			if(this.getEntityAlt().getListaSegmentoDia() != null
					&& this.getEntityAlt().getListaSegmentoDia().size() > 0)
			{
				for (int i = 0; i < this.getEntityAlt().getListaSegmentoDia().size(); i++)
				{
					this.getDias()[i] = this.getEntityAlt().getListaSegmentoDia().get(i).getIdDia();
				}
			}
			
			//LISTA PERGUNTAS
			SegmentoAdicional segmentoAdicional = new SegmentoAdicional();
			segmentoAdicional.setIdSegmento(this.getEntityAlt().getIdSegmento());
			segmentoAdicional.setFgAtivo("S");
			
			List<SegmentoAdicional> lista = SegmentoAdicionalService.getInstancia().pesquisar(segmentoAdicional, SegmentoAdicionalService.JOIN_SEGMENTO_ITEM,
																																 Criteria.DISTINCT_ROOT_ENTITY);
			this.setListaPerguntas(lista);
			
			super.preparaVisualizar();
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

	public String getActiveTab() {
		return activeTab;
	}

	public void setActiveTab(String activeTab) {
		this.activeTab = activeTab;
	}

	public SegmentoAdicional getSegmentoAdicional() {
		return segmentoAdicional;
	}

	public void setSegmentoAdicional(SegmentoAdicional segmentoAdicional) {
		this.segmentoAdicional = segmentoAdicional;
	}

	public List<SegmentoItem> getListaItens() {
		return listaItens;
	}

	public void setListaItens(List<SegmentoItem> listaItens) {
		this.listaItens = listaItens;
	}

	public SegmentoItem getSegmentoItem() {
		return segmentoItem;
	}

	public void setSegmentoItem(SegmentoItem segmentoItem) {
		this.segmentoItem = segmentoItem;
	}

	public List<SegmentoAdicional> getListaPerguntas() {
		return listaPerguntas;
	}

	public void setListaPerguntas(List<SegmentoAdicional> listaPerguntas) {
		this.listaPerguntas = listaPerguntas;
	}

	public List<SegmentoAdicional> getListaPerguntasBase() {
		return listaPerguntasBase;
	}

	public void setListaPerguntasBase(List<SegmentoAdicional> listaPerguntasBase) {
		this.listaPerguntasBase = listaPerguntasBase;
	}

	public List<Dominio> getListaSemana() {
		return listaSemana;
	}

	public void setListaSemana(List<Dominio> listaSemana) {
		this.listaSemana = listaSemana;
	}

	public BigInteger[] getDias() {
		return dias;
	}

	public void setDias(BigInteger[] dias) {
		this.dias = dias;
	}
}
