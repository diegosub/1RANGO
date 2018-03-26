package br.com.rango.ngc.service;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;

import br.com.rango.ngc.entity.Parametro;
import br.com.rango.ngc.util.RangoHbNgc;
import br.com.rango.ngc.util.RestritorHb;

public class ParametroService extends RangoHbNgc<Parametro>
{
	private static ParametroService instancia = null;
	
	public static final int JOIN_USUARIO_CAD = 1;
	
	public static final int JOIN_USUARIO_ALT = 2;
	
	@SuppressWarnings("rawtypes")
	private static Map filtroPropriedade = new HashMap();
	
	@SuppressWarnings("rawtypes")
	private static Map restritores = new HashMap();
	
	public static ParametroService getInstancia()
	{
		if (instancia == null)
		{
			instancia = new ParametroService();
		}
		return instancia;
	}
	
	public ParametroService()
	{
		adicionarFiltro("idParametro", RestritorHb.RESTRITOR_EQ, "idParametro");
		adicionarFiltro("dsParametro", RestritorHb.RESTRITOR_EQ, "dsParametro");		
	}
	
	@Override
	protected Criteria montaCriteria(Session sessao, int join)
	{
		Criteria criteria = sessao.createCriteria(Parametro.class);
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
