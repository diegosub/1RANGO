package br.com.rango.ngc.service;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import br.com.rango.ngc.entity.PedidoProdutoAdicional;
import br.com.rango.ngc.util.RangoHbNgc;
import br.com.rango.ngc.util.RestritorHb;

public class PedidoProdutoAdicionalService extends RangoHbNgc<PedidoProdutoAdicional>
{
	private static PedidoProdutoAdicionalService instancia = null;

	@SuppressWarnings("rawtypes")
	private static Map filtroPropriedade = new HashMap();
	
	@SuppressWarnings("rawtypes")
	private static Map restritores = new HashMap();
	
	public static PedidoProdutoAdicionalService getInstancia()
	{
		if (instancia == null)
		{
			instancia = new PedidoProdutoAdicionalService();
		}
		return instancia;
	}
	
	public PedidoProdutoAdicionalService()
	{
		adicionarFiltro("idPedidoProdutoAdicional", RestritorHb.RESTRITOR_EQ, "idPedidoProdutoAdicional");
	}
	
	@Override
	protected Criteria montaCriteria(Session sessao, int join)
	{
		Criteria criteria = sessao.createCriteria(PedidoProdutoAdicional.class);
		return criteria;
	}
	
	@Override
	protected void setarOrdenacao(Criteria criteria, PedidoProdutoAdicional vo, int join)
	{
		criteria.addOrder(Order.asc("dsPedidoProduto"));
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
