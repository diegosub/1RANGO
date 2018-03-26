package br.com.rango.ngc.service;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;

import br.com.rango.ngc.entity.EstabelecimentoBandeira;
import br.com.rango.ngc.util.RangoHbNgc;
import br.com.rango.ngc.util.RestritorHb;

public class EstabelecimentoBandeiraService extends RangoHbNgc<EstabelecimentoBandeira>
{
	private static EstabelecimentoBandeiraService instancia = null;
	
	public static final int JOIN_USUARIO_CAD = 1;
	
	public static final int JOIN_USUARIO_ALT = 2;
	
	@SuppressWarnings("rawtypes")
	private static Map filtroPropriedade = new HashMap();
	
	@SuppressWarnings("rawtypes")
	private static Map restritores = new HashMap();
	
	public static EstabelecimentoBandeiraService getInstancia()
	{
		if (instancia == null)
		{
			instancia = new EstabelecimentoBandeiraService();
		}
		return instancia;
	}
	
	public EstabelecimentoBandeiraService()
	{
		adicionarFiltro("idEstabelecimentoBandeira", RestritorHb.RESTRITOR_EQ, "idEstabelecimentoBandeira");
		adicionarFiltro("idEstabelecimento", RestritorHb.RESTRITOR_EQ, "idEstabelecimento");
		adicionarFiltro("idBandeiraDelivery", RestritorHb.RESTRITOR_IS_NOTNULL, "filtroMap.bandeiraDeliveryNotNull");
		adicionarFiltro("idBandeiraRetirada", RestritorHb.RESTRITOR_IS_NOTNULL, "filtroMap.bandeiraRetiradaNotNull");
	}
	
	@Override
	protected Criteria montaCriteria(Session sessao, int join)
	{
		Criteria criteria = sessao.createCriteria(EstabelecimentoBandeira.class);
		return criteria;
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
