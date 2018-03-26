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

import br.com.rango.ngc.entity.Bairro;
import br.com.rango.ngc.util.HibernateUtil;
import br.com.rango.ngc.util.RangoHbNgc;
import br.com.rango.ngc.util.RestritorHb;
import br.com.rango.ngc.util.jsf.JSFUtil;

public class BairroService extends RangoHbNgc<Bairro>
{
	private static BairroService instancia = null;
	
	public static final int JOIN_USUARIO_CAD = 1;
	
	public static final int JOIN_USUARIO_ALT = 2;
	
	public static final int JOIN_CIDADE = 4;
	
	public static final int JOIN_CIDADE_ESTADO = 8;
	
	private JSFUtil util = new JSFUtil();

	@SuppressWarnings("rawtypes")
	private static Map filtroPropriedade = new HashMap();
	
	@SuppressWarnings("rawtypes")
	private static Map restritores = new HashMap();
	
	public static BairroService getInstancia()
	{
		if (instancia == null)
		{
			instancia = new BairroService();
		}
		return instancia;
	}
	
	public BairroService()
	{
		adicionarFiltro("idBairro", RestritorHb.RESTRITOR_EQ, "idBairro");
		adicionarFiltro("idCidade", RestritorHb.RESTRITOR_EQ, "idCidade");
		adicionarFiltro("idBairro", RestritorHb.RESTRITOR_NE, "filtroMap.idBairroNotEq");
		adicionarFiltro("dsBairro", RestritorHb.RESTRITOR_LIKE, "dsBairro");
		adicionarFiltro("dsBairro", RestritorHb.RESTRITOR_EQ, "filtroMap.dsBairro");
		adicionarFiltro("fgAtivo", RestritorHb.RESTRITOR_EQ, "fgAtivo");
	}
	
	@Override
	protected void validarInserir(Session sessao, Bairro vo) throws Exception
	{
		Bairro bairro = new Bairro();
		bairro.setIdCidade(vo.getIdCidade());
		bairro.setFiltroMap(new HashMap<String, Object>());
		bairro.getFiltroMap().put("dsBairro", vo.getDsBairro().trim());
		
		bairro = this.get(sessao, bairro, 0);
		
		if(bairro != null)
		{
			throw new Exception("Este bairro já está cadastrado na sua base de dados!");
		}
	}
	
	@Override
	protected void validarAlterar(Session sessao, Bairro vo) throws Exception
	{
		Bairro bairro = new Bairro();
		bairro.setFiltroMap(new HashMap<String, Object>());
		bairro.getFiltroMap().put("idBairroNotEq", vo.getIdBairro());
		bairro.getFiltroMap().put("dsBairro", vo.getDsBairro().trim());
		bairro.setIdCidade(vo.getIdCidade());
		
		bairro = this.get(sessao, bairro, 0);
		
		if(bairro != null)
		{
			throw new Exception("Este bairro já está cadastrado na sua base de dados!");
		}
	}
	
	public Bairro inativar(Bairro vo) throws Exception
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

	public Bairro inativar(Session sessao, Bairro vo) throws Exception
	{
		Bairro bairro = new Bairro();
		bairro.setIdBairro(vo.getIdBairro());
		bairro = this.get(sessao, bairro, 0);
				
		vo.setFgAtivo("N");
		vo.setIdUsuarioAlt(util.getUsuarioLogado().getIdUsuario());
		vo.setDtAlteracao(new Date());
		
		sessao.merge(vo);
		
		return vo;
	}
	
	public Bairro ativar(Bairro vo) throws Exception
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
	
	public Bairro ativar(Session sessao, Bairro vo) throws Exception
	{
		Bairro bairro = new Bairro();
		bairro.setIdBairro(vo.getIdBairro());
		bairro = this.get(sessao, bairro, 0);
		
		vo.setFgAtivo("S");
		vo.setIdUsuarioAlt(util.getUsuarioLogado().getIdUsuario());
		vo.setDtAlteracao(new Date());
		
		super.alterar(sessao, vo);
		
		return vo;
	}
	
	@Override
	protected Criteria montaCriteria(Session sessao, int join)
	{
		Criteria criteria = sessao.createCriteria(Bairro.class);
			
		if ((join & JOIN_CIDADE) != 0)
	    {
			criteria.createAlias("cidade", "cidade");
			
			if ((join & JOIN_CIDADE_ESTADO) != 0)
		    {
				criteria.createAlias("cidade.estado", "estado");
		    }
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
	protected void setarOrdenacao(Criteria criteria, Bairro vo, int join)
	{
		criteria.addOrder(Order.asc("dsBairro"));
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
