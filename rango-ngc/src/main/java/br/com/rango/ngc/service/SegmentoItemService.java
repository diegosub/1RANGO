package br.com.rango.ngc.service;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.sql.JoinType;

import br.com.rango.ngc.entity.SegmentoItem;
import br.com.rango.ngc.util.RangoHbNgc;

public class SegmentoItemService extends RangoHbNgc<SegmentoItem>
{
	private static SegmentoItemService instancia = null;
	
	public static final int JOIN_USUARIO_CAD = 1;
	
	public static final int JOIN_USUARIO_ALT = 2;
	
	@SuppressWarnings("rawtypes")
	private static Map filtroPropriedade = new HashMap();
	
	@SuppressWarnings("rawtypes")
	private static Map restritores = new HashMap();
	
	public static SegmentoItemService getInstancia()
	{
		if (instancia == null)
		{
			instancia = new SegmentoItemService();
		}
		return instancia;
	}
	
	public SegmentoItemService()
	{
	}
	
	@Override
	protected Criteria montaCriteria(Session sessao, int join)
	{
		Criteria criteria = sessao.createCriteria(SegmentoItem.class);
				
		if ((join & JOIN_USUARIO_CAD) != 0)
	    {
			criteria.createAlias("usuarioCad", "usuarioCad");
	    }
		
		if ((join & JOIN_USUARIO_ALT) != 0)
	    {
			criteria.createAlias("usuarioAlt", "usuarioAlt", JoinType.LEFT_OUTER_JOIN);
	    }
		
		return criteria;
	}
	
	@Override
	protected void setarOrdenacao(Criteria criteria, SegmentoItem vo, int join)
	{
		criteria.addOrder(Order.asc("dsSegmentoItem"));
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
