package br.com.rango.web.bean;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.hibernate.Criteria;

import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.GregorianCalendar;

import br.com.rango.ngc.entity.Dominio;
import br.com.rango.ngc.entity.Produto;
import br.com.rango.ngc.entity.Segmento;
import br.com.rango.ngc.service.DominioService;
import br.com.rango.ngc.service.SegmentoService;
import br.com.rango.ngc.util.jsf.AbstractBean;
import br.com.rango.ngc.util.jsf.JSFUtil;


@RequestScoped
@ManagedBean
public class CardapioBean extends AbstractBean<Segmento, SegmentoService>
{
	private List<Dominio> listaDiaSemana;
	
	private Integer tpConsulta;
	private BigInteger idDiaSemana;
	private Date data;
	
	private JSFUtil util = new JSFUtil();
	
	
	public CardapioBean()
	{
		super(SegmentoService.getInstancia());
		this.ACTION_SEARCH = "cardapio";
		this.pageTitle = "Cardápio";		
	}
	
	@Override
	protected void setValoresDefault() throws Exception
	{
		this.popularDiasSemana();
		this.setTpConsulta(1);
	}
	
	private void popularDiasSemana() throws Exception
	{
		Dominio d1 = new Dominio();
		d1.setDsDominio("-- Escolha um Dia da Semana --");
		
		Dominio dominio = new Dominio();
		dominio.setNmCampo("DIA_SEMANA");
		
		List<Dominio> lista = DominioService.getInstancia().pesquisar(dominio, 0);
		this.setListaDiaSemana(lista);
		this.getListaDiaSemana().add(0, d1);
	}
	
	@Override
	public void pesquisar(ActionEvent event)
	{
		 try
         {
    		 validarPesquisar();
    		 
    		 BigInteger dia = null;
    		 
    		 if(this.getTpConsulta().intValue() == 2)
    		 {
    			 GregorianCalendar gc = new GregorianCalendar();
        		 gc.setTime(this.getData());
        		 
        		 dia = new BigInteger(gc.get(Calendar.DAY_OF_WEEK)+"");
    		 }
    		 else
    		 {
    			 dia = this.getIdDiaSemana();
    		 }
    		 
    		 Segmento segmento = new Segmento();
    		 segmento.setIdEstabelecimento(util.getUsuarioLogado().getIdEstabelecimento());
    		 segmento.setFgAtivo("S");
    		 segmento.setFiltroMap(new HashMap<String, Object>());
    		 segmento.getFiltroMap().put("idDia", dia);
    		 segmento.getFiltroMap().put("fgAtivoProduto", "S");
    		 
    		 List<Segmento> lista = SegmentoService.getInstancia().pesquisar(segmento, getJoinPesquisar(), Criteria.DISTINCT_ROOT_ENTITY);
    		 
    		 for (Segmento obj : lista)
    		 {
    			 obj.setListaProduto(new ArrayList<Produto>(obj.getSetProduto()));
    		 }
    		 
    		 this.setSearchResult(lista);    		 
    		 setCurrentState(STATE_SEARCH);
         }
         catch (Exception e)
         {
            FacesMessage message = new FacesMessage(e.getMessage());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            if(e.getMessage() == null)
           	 FacesContext.getCurrentInstance().addMessage("", message);
            else
           	 FacesContext.getCurrentInstance().addMessage(null, message);
            this.setSearchResult(new ArrayList<Segmento>());
         }
	}
	
	@Override
	protected int getJoinPesquisar()
	{
		return SegmentoService.JOIN_PRODUTO
			 | SegmentoService.JOIN_SEGMENTO_DIA;
	}
	
	@Override
	protected void validarPesquisar() throws Exception
	{
		if(this.getTpConsulta().intValue() == 1
				&& (this.getIdDiaSemana() == null
						|| this.getIdDiaSemana().intValue() <= 0))
		{
			throw new Exception("O campo Dia de Semana é obrigatório.");
		}
		
		if(this.getTpConsulta().intValue() == 2
				&& (this.getData() == null
						|| this.getData().toString().equals("")))
		{
			throw new Exception("O campo Data é obrigatório.");
		}
	}
	
	public void limparCamposConsulta()
	{
		this.setIdDiaSemana(null);
		this.setData(null);
	}

	public Integer getTpConsulta() {
		return tpConsulta;
	}

	public void setTpConsulta(Integer tpConsulta) {
		this.tpConsulta = tpConsulta;
	}

	public List<Dominio> getListaDiaSemana() {
		return listaDiaSemana;
	}

	public void setListaDiaSemana(List<Dominio> listaDiaSemana) {
		this.listaDiaSemana = listaDiaSemana;
	}

	public BigInteger getIdDiaSemana() {
		return idDiaSemana;
	}

	public void setIdDiaSemana(BigInteger idDiaSemana) {
		this.idDiaSemana = idDiaSemana;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
}
