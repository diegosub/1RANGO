package br.com.rango.ngc.service;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import br.com.rango.ngc.entity.EstabelecimentoBairro;
import br.com.rango.ngc.util.RangoHbNgc;
import br.com.rango.ngc.util.RestritorHb;

public class EstabelecimentoBairroService extends RangoHbNgc<EstabelecimentoBairro>
{
	private static EstabelecimentoBairroService instancia = null;
	
	public static final int JOIN_USUARIO_CAD = 1;
	
	public static final int JOIN_USUARIO_ALT = 2;
	
	public static final int JOIN_BAIRRO = 4;
	
	@SuppressWarnings("rawtypes")
	private static Map filtroPropriedade = new HashMap();
	
	@SuppressWarnings("rawtypes")
	private static Map restritores = new HashMap();
	
	public static EstabelecimentoBairroService getInstancia()
	{
		if (instancia == null)
		{
			instancia = new EstabelecimentoBairroService();
		}
		return instancia;
	}
	
	public EstabelecimentoBairroService()
	{
		adicionarFiltro("idEstabelecimentoBairro", RestritorHb.RESTRITOR_EQ, "idEstabelecimentoBairro");
		adicionarFiltro("idEstabelecimento", RestritorHb.RESTRITOR_EQ, "idEstabelecimento");
		adicionarFiltro("idBairro", RestritorHb.RESTRITOR_EQ, "idBairro");
		adicionarFiltro("bairro.fgAtivo", RestritorHb.RESTRITOR_EQ, "bairro.fgAtivo");
	}
	
	@Override
	protected Criteria montaCriteria(Session sessao, int join)
	{
		Criteria criteria = sessao.createCriteria(EstabelecimentoBairro.class);
		
		if ((join & JOIN_BAIRRO) != 0)
	    {
			criteria.createAlias("bairro", "bairro");			
	    }
				
		return criteria;
	}
	
	@Override
	protected void setarOrdenacao(Criteria criteria, EstabelecimentoBairro vo, int join)
	{
		if ((join & JOIN_BAIRRO) != 0)
	    {
			criteria.addOrder(Order.asc("bairro.dsBairro"));			
	    }
	}
	
	@Override
	@SuppressWarnings("rawtypes")
	protected Map restritores() 
	{
		return restritores;
	}

	@Override
	@SuppressWarnings("rawtypes")
	protected Map filtroPropriedade() 
	{
		return filtroPropriedade;
	}
}
