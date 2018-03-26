package br.com.rango.ngc.service;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;

import br.com.rango.ngc.entity.RecuperaSenha;
import br.com.rango.ngc.util.RangoHbNgc;
import br.com.rango.ngc.util.RestritorHb;

public class RecuperaSenhaService extends RangoHbNgc<RecuperaSenha>
{
	private static RecuperaSenhaService instancia = null;

	@SuppressWarnings("rawtypes")
	private static Map filtroPropriedade = new HashMap();
	
	@SuppressWarnings("rawtypes")
	private static Map restritores = new HashMap();
	
	public static RecuperaSenhaService getInstancia()
	{
		if (instancia == null)
		{
			instancia = new RecuperaSenhaService();
		}
		return instancia;
	}
	
	public RecuperaSenhaService()
	{
		adicionarFiltro("idRecuperaSenha", RestritorHb.RESTRITOR_EQ, "idRecuperaSenha");
		adicionarFiltro("idUsuario", RestritorHb.RESTRITOR_EQ, "idUsuario");
	}
	
	@Override
	protected Criteria montaCriteria(Session sessao, int join)
	{
		Criteria criteria = sessao.createCriteria(RecuperaSenha.class);
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
