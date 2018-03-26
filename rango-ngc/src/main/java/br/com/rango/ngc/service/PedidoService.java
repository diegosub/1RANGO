package br.com.rango.ngc.service;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import br.com.rango.ngc.entity.Pedido;
import br.com.rango.ngc.entity.PedidoProduto;
import br.com.rango.ngc.entity.PedidoProdutoAdicional;
import br.com.rango.ngc.util.RangoHbNgc;
import br.com.rango.ngc.util.RestritorHb;

public class PedidoService extends RangoHbNgc<Pedido>
{
	private static PedidoService instancia = null;
	
	public static final int JOIN_PEDIDO_PRODUTO = 1;
	
	public static final int JOIN_PEDIDO_PRODUTO_ADICIONAL = 2;
	
	public static final int JOIN_ESTABELECIMENTO = 4;

	@SuppressWarnings("rawtypes")
	private static Map filtroPropriedade = new HashMap();
	
	@SuppressWarnings("rawtypes")
	private static Map restritores = new HashMap();
	
	public static PedidoService getInstancia()
	{
		if (instancia == null)
		{
			instancia = new PedidoService();
		}
		return instancia;
	}
	
	public PedidoService()
	{
		adicionarFiltro("idPedido", RestritorHb.RESTRITOR_EQ, "idPedido");
	}

	@Override
	public Pedido inserir(Session sessao, Pedido vo) throws Exception
	{
		//INSERINDO PEDIDO
		sessao.save(vo);
		sessao.flush();
		
		//PEDIDO PRODUTO
		for (PedidoProduto pedidoProduto : vo.getListaPedidoProduto())
		{
			pedidoProduto.setIdPedido(vo.getIdPedido());
			PedidoProdutoService.getInstancia().inserir(sessao, pedidoProduto);
			
			//PEDIDO PRODUTO ADICIONAL
			for (PedidoProdutoAdicional pedidoProdutoAdicional : pedidoProduto.getListaPedidoProdutoAdicional())
			{
				pedidoProdutoAdicional.setIdPedidoProduto(pedidoProduto.getIdPedidoProduto());
				PedidoProdutoAdicionalService.getInstancia().inserir(sessao, pedidoProdutoAdicional);				
			}
		}
		
		return vo;
	}
		
	@Override
	protected Criteria montaCriteria(Session sessao, int join)
	{
		Criteria criteria = sessao.createCriteria(Pedido.class);
		
		if ((join & JOIN_PEDIDO_PRODUTO) != 0)
	    {
			criteria.createAlias("listaPedidoProduto", "listaPedidoProduto");
			
			if ((join & JOIN_PEDIDO_PRODUTO) != 0)
		    {
				criteria.createAlias("listaPedidoProduto.listaPedidoProdutoAdicional", "listaPedidoProdutoAdicional");
		    }
	    }
		
		if ((join & JOIN_ESTABELECIMENTO) != 0)
	    {
			criteria.createAlias("estabelecimento", "estabelecimento");
	    }
		
		return criteria;
	}
	
	@Override
	protected void setarOrdenacao(Criteria criteria, Pedido vo, int join)
	{
		criteria.addOrder(Order.desc("idPedido"));
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
