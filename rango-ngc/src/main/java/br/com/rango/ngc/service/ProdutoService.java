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

import br.com.rango.ngc.entity.Produto;
import br.com.rango.ngc.util.HibernateUtil;
import br.com.rango.ngc.util.RangoHbNgc;
import br.com.rango.ngc.util.RestritorHb;
import br.com.rango.ngc.util.jsf.JSFUtil;

public class ProdutoService extends RangoHbNgc<Produto>
{
	private static ProdutoService instancia = null;
	
	public static final int JOIN_USUARIO_CAD = 1;
	
	public static final int JOIN_USUARIO_ALT = 2;
	
	public static final int JOIN_SEGMENTO = 4;
	
	private JSFUtil util = new JSFUtil();

	@SuppressWarnings("rawtypes")
	private static Map filtroPropriedade = new HashMap();
	
	@SuppressWarnings("rawtypes")
	private static Map restritores = new HashMap();
	
	public static ProdutoService getInstancia()
	{
		if (instancia == null)
		{
			instancia = new ProdutoService();
		}
		return instancia;
	}
	
	public ProdutoService()
	{
		adicionarFiltro("idProduto", RestritorHb.RESTRITOR_EQ, "idProduto");
		adicionarFiltro("idProduto", RestritorHb.RESTRITOR_NE, "filtroMap.idProdutoNotEq");
		adicionarFiltro("dsProduto", RestritorHb.RESTRITOR_LIKE, "dsProduto");
		adicionarFiltro("dsProduto", RestritorHb.RESTRITOR_EQ, "filtroMap.dsProduto");	
		adicionarFiltro("idEstabelecimento", RestritorHb.RESTRITOR_EQ, "idEstabelecimento");
		adicionarFiltro("idSegmento", RestritorHb.RESTRITOR_EQ, "idSegmento");
		adicionarFiltro("fgAtivo", RestritorHb.RESTRITOR_EQ, "fgAtivo");
	}
	
	@Override
	protected void validarInserir(Session sessao, Produto vo) throws Exception
	{
		Produto produto = new Produto();
		produto.setFgAtivo("S");
		produto.setFiltroMap(new HashMap<String, Object>());
		produto.getFiltroMap().put("dsProduto", vo.getDsProduto().trim());		
		
		produto = this.get(sessao, produto, 0);
		
		if(produto != null)
		{
			throw new Exception("Este produto já está cadastrado na sua base de dados!");
		}
	}
	
	@Override
	protected void validarAlterar(Session sessao, Produto vo) throws Exception
	{
		Produto produto = new Produto();
		produto.setFiltroMap(new HashMap<String, Object>());
		produto.getFiltroMap().put("idProdutoNotEq", vo.getIdProduto());
		produto.getFiltroMap().put("dsProduto", vo.getDsProduto().trim());
		produto.setFgAtivo(vo.getFgAtivo());
		
		produto = this.get(sessao, produto, 0);
		
		if(produto != null)
		{
			throw new Exception("Este produto já está cadastrado na sua base de dados!");
		}
	}
	
	public Produto inativar(Produto vo) throws Exception
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

	public Produto inativar(Session sessao, Produto vo) throws Exception
	{
		Produto produto = new Produto();
		produto.setIdProduto(vo.getIdProduto());
		produto = this.get(sessao, produto, 0);
				
		vo.setFgAtivo("N");
		vo.setIdUsuarioAlt(util.getUsuarioLogado().getIdUsuario());
		vo.setDtAlteracao(new Date());
		
		sessao.merge(vo);
		
		return vo;
	}
	
	public Produto ativar(Produto vo) throws Exception
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
	
	public Produto ativar(Session sessao, Produto vo) throws Exception
	{
		Produto produto = new Produto();
		produto.setIdProduto(vo.getIdProduto());
		produto = this.get(sessao, produto, 0);
		
		vo.setFgAtivo("S");
		vo.setIdUsuarioAlt(util.getUsuarioLogado().getIdUsuario());
		vo.setDtAlteracao(new Date());
		
		super.alterar(sessao, vo);
		
		return vo;
	}
	
	@Override
	protected Criteria montaCriteria(Session sessao, int join)
	{
		Criteria criteria = sessao.createCriteria(Produto.class);
				
		if ((join & JOIN_USUARIO_CAD) != 0)
	    {
			criteria.createAlias("usuarioCad", "usuarioCad");
	    }
		
		if ((join & JOIN_USUARIO_ALT) != 0)
	    {
			criteria.createAlias("usuarioAlt", "usuarioAlt", JoinType.LEFT_OUTER_JOIN);
	    }
		
		if ((join & JOIN_SEGMENTO) != 0)
	    {
			criteria.createAlias("segmento", "segmento");
	    }
		
		return criteria;
	}
	
	@Override
	protected void setarOrdenacao(Criteria criteria, Produto vo, int join)
	{
		criteria.addOrder(Order.asc("dsProduto"));
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
