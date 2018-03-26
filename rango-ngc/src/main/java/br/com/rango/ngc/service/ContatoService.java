package br.com.rango.ngc.service;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import br.com.rango.ngc.entity.Contato;
import br.com.rango.ngc.util.RangoHbNgc;
import br.com.rango.ngc.util.RestritorHb;

public class ContatoService extends RangoHbNgc<Contato>
{
	private static ContatoService instancia = null;
	
	public static final int JOIN_USUARIO_CAD = 1;
	
	public static final int JOIN_USUARIO_ALT = 2;
	
	public static final int JOIN_CIDADE = 4;
	
	public static final int JOIN_CIDADE_ESTADO = 8;
	

	@SuppressWarnings("rawtypes")
	private static Map filtroPropriedade = new HashMap();
	
	@SuppressWarnings("rawtypes")
	private static Map restritores = new HashMap();
	
	public static ContatoService getInstancia()
	{
		if (instancia == null)
		{
			instancia = new ContatoService();
		}
		return instancia;
	}
	
	public ContatoService()
	{
		adicionarFiltro("idContato", RestritorHb.RESTRITOR_EQ, "idContato");		
	}
	
	@Override
	protected Criteria montaCriteria(Session sessao, int join)
	{
		Criteria criteria = sessao.createCriteria(Contato.class);
		return criteria;
	}
	
	@Override
	protected void setarOrdenacao(Criteria criteria, Contato vo, int join)
	{
		criteria.addOrder(Order.desc("dtCadastro"));
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
