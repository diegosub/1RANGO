package br.com.rango.ngc.service;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import br.com.rango.ngc.entity.PedidoProduto;
import br.com.rango.ngc.util.RangoHbNgc;
import br.com.rango.ngc.util.RestritorHb;

public class PedidoProdutoService extends RangoHbNgc<PedidoProduto>
{
	private static PedidoProdutoService instancia = null;

	@SuppressWarnings("rawtypes")
	private static Map filtroPropriedade = new HashMap();
	
	@SuppressWarnings("rawtypes")
	private static Map restritores = new HashMap();
	
	public static PedidoProdutoService getInstancia()
	{
		if (instancia == null)
		{
			instancia = new PedidoProdutoService();
		}
		return instancia;
	}
	
	public PedidoProdutoService()
	{
		adicionarFiltro("idPedidoProduto", RestritorHb.RESTRITOR_EQ, "idPedidoProduto");
	}
	
	@Override
	protected Criteria montaCriteria(Session sessao, int join)
	{
		Criteria criteria = sessao.createCriteria(PedidoProduto.class);
		return criteria;
	}
	
	@Override
	protected void setarOrdenacao(Criteria criteria, PedidoProduto vo, int join)
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
