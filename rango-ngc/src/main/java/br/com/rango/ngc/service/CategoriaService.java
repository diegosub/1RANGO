package br.com.rango.ngc.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.sql.JoinType;

import br.com.rango.ngc.entity.Categoria;
import br.com.rango.ngc.util.HibernateUtil;
import br.com.rango.ngc.util.RangoHbNgc;
import br.com.rango.ngc.util.RestritorHb;
import br.com.rango.ngc.util.jsf.JSFUtil;

public class CategoriaService extends RangoHbNgc<Categoria>
{
	private static CategoriaService instancia = null;
	
	public static final int JOIN_USUARIO_CAD = 1;
	
	public static final int JOIN_USUARIO_ALT = 2;
	
	private JSFUtil util = new JSFUtil();

	@SuppressWarnings("rawtypes")
	private static Map filtroPropriedade = new HashMap();
	
	@SuppressWarnings("rawtypes")
	private static Map restritores = new HashMap();
	
	public static CategoriaService getInstancia()
	{
		if (instancia == null)
		{
			instancia = new CategoriaService();
		}
		return instancia;
	}
	
	public CategoriaService()
	{
		adicionarFiltro("idCategoria", RestritorHb.RESTRITOR_EQ, "idCategoria");
		adicionarFiltro("idCategoria", RestritorHb.RESTRITOR_NE, "filtroMap.idCategoriaNotEq");
		adicionarFiltro("dsCategoria", RestritorHb.RESTRITOR_LIKE, "dsCategoria");
		adicionarFiltro("dsCategoria", RestritorHb.RESTRITOR_EQ, "filtroMap.dsCategoria");
		adicionarFiltro("fgAtivo", RestritorHb.RESTRITOR_EQ, "fgAtivo");
	}
	
	@Override
	protected void validarInserir(Session sessao, Categoria vo) throws Exception
	{
		Categoria categoria = new Categoria();
		categoria.setFgAtivo("S");
		categoria.setFiltroMap(new HashMap<String, Object>());
		categoria.getFiltroMap().put("dsCategoria", vo.getDsCategoria().trim());		
		
		categoria = this.get(sessao, categoria, 0);
		
		if(categoria != null)
		{
			throw new Exception("Esta categoria já está cadastrada na sua base de dados!");
		}
	}
	
	@Override
	protected void validarAlterar(Session sessao, Categoria vo) throws Exception
	{
		Categoria categoria = new Categoria();
		categoria.setFiltroMap(new HashMap<String, Object>());
		categoria.getFiltroMap().put("idCategoriaNotEq", vo.getIdCategoria());
		categoria.getFiltroMap().put("dsCategoria", vo.getDsCategoria().trim());
		categoria.setFgAtivo(vo.getFgAtivo());
		
		categoria = this.get(sessao, categoria, 0);
		
		if(categoria != null)
		{
			throw new Exception("Esta categoria já está cadastrada na sua base de dados!");
		}
	}
	
	public Categoria inativar(Categoria vo) throws Exception
	{
		Session sessao = HibernateUtil.getSession();
		sessao.setFlushMode(FlushMode.COMMIT);
		Transaction tx = sessao.beginTransaction();
		try
		{
			vo = inativar(sessao, vo);
			tx.commit();
			return vo;
		}
		catch (Exception e)
		{
			vo.setFgAtivo("S");
			tx.rollback();
			throw e;
		}
		finally
		{
			sessao.close();
		}
	}

	public Categoria inativar(Session sessao, Categoria vo) throws Exception
	{
		Categoria categoria = new Categoria();
		categoria.setIdCategoria(vo.getIdCategoria());
		categoria = this.get(sessao, categoria, 0);
				
		vo.setFgAtivo("N");
		vo.setIdUsuarioAlt(util.getUsuarioLogado().getIdUsuario());
		vo.setDtAlteracao(new Date());
		
		sessao.merge(vo);
		
		return vo;
	}
	
	public Categoria ativar(Categoria vo) throws Exception
	{
		Session sessao = HibernateUtil.getSession();
		sessao.setFlushMode(FlushMode.COMMIT);
		Transaction tx = sessao.beginTransaction();
		try
		{
			vo = ativar(sessao, vo);
			tx.commit();
			return vo;
		}
		catch (Exception e)
		{
			vo.setFgAtivo("N");
			tx.rollback();
			throw e;
		}
		finally
		{
			sessao.close();
		}
	}
	
	public Categoria ativar(Session sessao, Categoria vo) throws Exception
	{
		Categoria categoria = new Categoria();
		categoria.setIdCategoria(vo.getIdCategoria());
		categoria = this.get(sessao, categoria, 0);
		
		vo.setFgAtivo("S");
		vo.setIdUsuarioAlt(util.getUsuarioLogado().getIdUsuario());
		vo.setDtAlteracao(new Date());
		
		super.alterar(sessao, vo);
		
		return vo;
	}
	
	@Override
	protected Criteria montaCriteria(Session sessao, int join)
	{
		Criteria criteria = sessao.createCriteria(Categoria.class);
				
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
	protected void setarOrdenacao(Criteria criteria, Categoria vo, int join)
	{
		criteria.addOrder(Order.asc("dsCategoria"));
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
