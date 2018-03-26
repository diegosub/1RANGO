package br.com.rango.ngc.service;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.sql.JoinType;

import br.com.rango.ngc.entity.SegmentoAdicional;
import br.com.rango.ngc.util.RangoHbNgc;
import br.com.rango.ngc.util.RestritorHb;

public class SegmentoAdicionalService extends RangoHbNgc<SegmentoAdicional>
{
	private static SegmentoAdicionalService instancia = null;
	
	public static final int JOIN_SEGMENTO = 1;
	
	public static final int JOIN_SEGMENTO_ITEM = 2;
	
	public static final int JOIN_USUARIO_CAD = 4;
	
	public static final int JOIN_USUARIO_ALT = 8;
	
	@SuppressWarnings("rawtypes")
	private static Map filtroPropriedade = new HashMap();
	
	@SuppressWarnings("rawtypes")
	private static Map restritores = new HashMap();
	
	public static SegmentoAdicionalService getInstancia()
	{
		if (instancia == null)
		{
			instancia = new SegmentoAdicionalService();
		}
		return instancia;
	}
	
	public SegmentoAdicionalService()
	{
		adicionarFiltro("idSegmento", RestritorHb.RESTRITOR_EQ, "idSegmento");
		adicionarFiltro("fgAtivo", RestritorHb.RESTRITOR_EQ, "fgAtivo");
	}
	
	@Override
	protected Criteria montaCriteria(Session sessao, int join)
	{
		Criteria criteria = sessao.createCriteria(SegmentoAdicional.class);
		
		if ((join & JOIN_SEGMENTO) != 0)
	    {
			criteria.createAlias("segmento", "segmento");
	    }
		
		if ((join & JOIN_SEGMENTO_ITEM) != 0)
	    {
			criteria.createAlias("listaSegmentoItem", "listaSegmentoItem", JoinType.LEFT_OUTER_JOIN);
	    }
		
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
	protected void setarOrdenacao(Criteria criteria, SegmentoAdicional vo, int join)
	{
		criteria.addOrder(Order.asc("dsSegmentoAdicional"));
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
