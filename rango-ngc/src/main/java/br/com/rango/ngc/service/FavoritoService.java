package br.com.rango.ngc.service;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.rango.ngc.entity.Favorito;
import br.com.rango.ngc.util.HibernateUtil;
import br.com.rango.ngc.util.RangoHbNgc;
import br.com.rango.ngc.util.RestritorHb;

public class FavoritoService extends RangoHbNgc<Favorito>
{
	private static FavoritoService instancia = null;
	
	public static final int JOIN_USUARIO_CAD = 1;
	
	public static final int JOIN_USUARIO_ALT = 2;
	
	@SuppressWarnings("rawtypes")
	private static Map filtroPropriedade = new HashMap();
	
	@SuppressWarnings("rawtypes")
	private static Map restritores = new HashMap();
	
	public static FavoritoService getInstancia()
	{
		if (instancia == null)
		{
			instancia = new FavoritoService();
		}
		return instancia;
	}
	
	public FavoritoService()
	{
		adicionarFiltro("idFavorito", RestritorHb.RESTRITOR_EQ, "idFavorito");
		adicionarFiltro("idEstabelecimento", RestritorHb.RESTRITOR_EQ, "idEstabelecimento");
		adicionarFiltro("idUsuario", RestritorHb.RESTRITOR_EQ, "idUsuario");
	}
	
	public Favorito remover(Favorito vo) throws Exception
	{
		Session sessao = HibernateUtil.getSession();
		sessao.setFlushMode(FlushMode.COMMIT);
		Transaction tx = sessao.beginTransaction();
		try
		{
			vo = remover(sessao, vo);
			tx.commit();
			return vo;
		}
		catch (Exception e)
		{
			tx.rollback();
			throw e;
		}
		finally
		{
			sessao.close();
		}
	}
	
	public Favorito remover(Session sessao, Favorito vo) throws Exception
	{
		Favorito favorito = new Favorito();
		favorito.setIdEstabelecimento(vo.getIdEstabelecimento());
		favorito.setIdUsuario(vo.getIdUsuario());		
		favorito = this.get(sessao, favorito, 0);
		
		sessao.delete(favorito);
		return favorito;
	}
	
	@Override
	protected Criteria montaCriteria(Session sessao, int join)
	{
		Criteria criteria = sessao.createCriteria(Favorito.class);
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
