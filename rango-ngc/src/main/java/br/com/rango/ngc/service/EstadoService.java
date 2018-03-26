package br.com.rango.ngc.service;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import br.com.rango.ngc.entity.Estado;
import br.com.rango.ngc.util.RangoHbNgc;
import br.com.rango.ngc.util.RestritorHb;

public class EstadoService extends RangoHbNgc<Estado>
{
	private static EstadoService instancia = null;

	@SuppressWarnings("rawtypes")
	private static Map filtroPropriedade = new HashMap();
	
	@SuppressWarnings("rawtypes")
	private static Map restritores = new HashMap();
	
	public static EstadoService getInstancia()
	{
		if (instancia == null)
		{
			instancia = new EstadoService();
		}
		return instancia;
	}
	
	public EstadoService()
	{
		adicionarFiltro("idEstado", RestritorHb.RESTRITOR_EQ,"idEstado");
		adicionarFiltro("dsEstado", RestritorHb.RESTRITOR_LIKE, "dsEstado");
		adicionarFiltro("dsSigla", RestritorHb.RESTRITOR_EQ, "dsSigla");
	}
	
	@Override
	protected Criteria montaCriteria(Session sessao, int join)
	{
		Criteria criteria = sessao.createCriteria(Estado.class);
		return criteria;
	}
	
	@Override
	protected void setarOrdenacao(Criteria criteria, Estado vo, int join)
	{
		criteria.addOrder(Order.asc("dsEstado"));
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
