package br.com.rango.ngc.service;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import br.com.rango.ngc.entity.EstabelecimentoHorario;
import br.com.rango.ngc.util.RangoHbNgc;
import br.com.rango.ngc.util.RestritorHb;

public class EstabelecimentoHorarioService extends RangoHbNgc<EstabelecimentoHorario>
{
	private static EstabelecimentoHorarioService instancia = null;
	
	public static final int JOIN_USUARIO_CAD = 1;
	
	public static final int JOIN_USUARIO_ALT = 2;
	
	@SuppressWarnings("rawtypes")
	private static Map filtroPropriedade = new HashMap();
	
	@SuppressWarnings("rawtypes")
	private static Map restritores = new HashMap();
	
	public static EstabelecimentoHorarioService getInstancia()
	{
		if (instancia == null)
		{
			instancia = new EstabelecimentoHorarioService();
		}
		return instancia;
	}
	
	public EstabelecimentoHorarioService()
	{
		adicionarFiltro("idEstabelecimentoHorario", RestritorHb.RESTRITOR_EQ, "idEstabelecimentoHorario");
		adicionarFiltro("idEstabelecimento", RestritorHb.RESTRITOR_EQ, "idEstabelecimento");
		adicionarFiltro("nmHorario", RestritorHb.RESTRITOR_EQ, "nmHorario");
	}
	
	@Override
	protected Criteria montaCriteria(Session sessao, int join)
	{
		Criteria criteria = sessao.createCriteria(EstabelecimentoHorario.class);
		return criteria;
	}
	
	@Override
	protected void setarOrdenacao(Criteria criteria, EstabelecimentoHorario vo, int join)
	{
		criteria.addOrder(Order.asc("nmHorario"));
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
