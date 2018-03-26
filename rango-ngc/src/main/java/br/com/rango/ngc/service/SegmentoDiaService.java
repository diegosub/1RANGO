package br.com.rango.ngc.service;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;

import br.com.rango.ngc.entity.SegmentoDia;
import br.com.rango.ngc.util.RangoHbNgc;
import br.com.rango.ngc.util.RestritorHb;

public class SegmentoDiaService extends RangoHbNgc<SegmentoDia>
{
	private static SegmentoDiaService instancia = null;
	
	public static final int JOIN_USUARIO_CAD = 1;
	
	public static final int JOIN_USUARIO_ALT = 2;
	
	@SuppressWarnings("rawtypes")
	private static Map filtroPropriedade = new HashMap();
	
	@SuppressWarnings("rawtypes")
	private static Map restritores = new HashMap();
	
	public static SegmentoDiaService getInstancia()
	{
		if (instancia == null)
		{
			instancia = new SegmentoDiaService();
		}
		return instancia;
	}
	
	public SegmentoDiaService()
	{
		adicionarFiltro("idSegmentoDia", RestritorHb.RESTRITOR_EQ, "idSegmentoDia");
		adicionarFiltro("idSegmento", RestritorHb.RESTRITOR_EQ, "idSegmento");
	}
	
	@Override
	protected Criteria montaCriteria(Session sessao, int join)
	{
		Criteria criteria = sessao.createCriteria(SegmentoDia.class);
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
