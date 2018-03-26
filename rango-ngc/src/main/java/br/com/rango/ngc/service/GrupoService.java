package br.com.rango.ngc.service;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import br.com.rango.ngc.entity.Grupo;
import br.com.rango.ngc.util.RangoHbNgc;
import br.com.rango.ngc.util.RestritorHb;

public class GrupoService extends RangoHbNgc<Grupo>
{
	private static GrupoService instancia = null;

	public static final int JOIN_USUARIO_CAD = 1;
	
	public static final int GRUPO_ADMINISTRADOR = 1;
	public static final int GRUPO_ESTABELECIMENTO = 2;
	public static final int GRUPO_CLIENTE = 3;
	
	public static final Integer[] gruposAcesso = {GRUPO_ADMINISTRADOR, GRUPO_ESTABELECIMENTO, GRUPO_CLIENTE};
	
	@SuppressWarnings("rawtypes")
	private static Map filtroPropriedade = new HashMap();
	
	@SuppressWarnings("rawtypes")
	private static Map restritores = new HashMap();
	
	public static GrupoService getInstancia()
	{
		if (instancia == null)
		{
			instancia = new GrupoService();
		}
		return instancia;
	}
	
	public GrupoService()
	{
		adicionarFiltro("idGrupo", RestritorHb.RESTRITOR_EQ,"idGrupo");
		adicionarFiltro("dsGrupo", RestritorHb.RESTRITOR_LIKE, "dsGrupo");
		adicionarFiltro("fgAtivo", RestritorHb.RESTRITOR_EQ, "fgAtivo");
		adicionarFiltro("dsGrupo", RestritorHb.RESTRITOR_EQ, "filtroMap.dsGrupo");
	}
	
	@Override
	protected Criteria montaCriteria(Session sessao, int join)
	{
		Criteria criteria = sessao.createCriteria(Grupo.class);
		
		if ((join & JOIN_USUARIO_CAD) != 0)
	    {
	         criteria.createAlias("usuarioCad", "usuarioCad");
	    }
		
		return criteria;
	}
	
	@Override
	protected void setarOrdenacao(Criteria criteria, Grupo vo, int join)
	{
		criteria.addOrder(Order.asc("dsGrupo"));
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
