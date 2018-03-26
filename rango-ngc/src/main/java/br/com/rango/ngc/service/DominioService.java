package br.com.rango.ngc.service;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;

import br.com.rango.ngc.entity.Dominio;
import br.com.rango.ngc.util.RangoHbNgc;
import br.com.rango.ngc.util.RestritorHb;

public class DominioService extends RangoHbNgc<Dominio>
{
	private static DominioService instancia = null;
	
	public static final int JOIN_USUARIO_CAD = 1;
	
	public static final int JOIN_USUARIO_ALT = 2;
	
	@SuppressWarnings("rawtypes")
	private static Map filtroPropriedade = new HashMap();
	
	@SuppressWarnings("rawtypes")
	private static Map restritores = new HashMap();
	
	public static DominioService getInstancia()
	{
		if (instancia == null)
		{
			instancia = new DominioService();
		}
		return instancia;
	}
	
	public DominioService()
	{
		adicionarFiltro("idDominio", RestritorHb.RESTRITOR_EQ, "idDominio");
		adicionarFiltro("nmCampo", RestritorHb.RESTRITOR_EQ, "nmCampo");
		adicionarFiltro("dsDominio", RestritorHb.RESTRITOR_EQ, "dsDominio");
	}
	
	@Override
	protected Criteria montaCriteria(Session sessao, int join)
	{
		Criteria criteria = sessao.createCriteria(Dominio.class);
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
