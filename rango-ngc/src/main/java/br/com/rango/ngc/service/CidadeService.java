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

import br.com.rango.ngc.entity.Cidade;
import br.com.rango.ngc.util.HibernateUtil;
import br.com.rango.ngc.util.RangoHbNgc;
import br.com.rango.ngc.util.RestritorHb;
import br.com.rango.ngc.util.jsf.JSFUtil;

public class CidadeService extends RangoHbNgc<Cidade>
{
	private static CidadeService instancia = null;
	
	public static final int JOIN_ESTADO = 1;
	
	public static final int JOIN_USUARIO_CAD = 2;
	
	public static final int JOIN_USUARIO_ALT = 4;
	
	public static final int JOIN_BAIRRO = 8;
	
	public static final int JOIN_ESTABELECIMENTO = 16;
	
	private JSFUtil util = new JSFUtil();

	@SuppressWarnings("rawtypes")
	private static Map filtroPropriedade = new HashMap();
	
	@SuppressWarnings("rawtypes")
	private static Map restritores = new HashMap();
	
	public static CidadeService getInstancia()
	{
		if (instancia == null)
		{
			instancia = new CidadeService();
		}
		return instancia;
	}
	
	public CidadeService()
	{
		adicionarFiltro("idCidade", RestritorHb.RESTRITOR_EQ, "idCidade");
		adicionarFiltro("idEstado", RestritorHb.RESTRITOR_EQ, "idEstado");
		adicionarFiltro("idCidade", RestritorHb.RESTRITOR_NE, "filtroMap.idCidadeNotEq");
		adicionarFiltro("dsCidade", RestritorHb.RESTRITOR_LIKE, "dsCidade");
		adicionarFiltro("dsCidade", RestritorHb.RESTRITOR_EQ, "filtroMap.dsCidade");
		adicionarFiltro("fgAtivo", RestritorHb.RESTRITOR_EQ, "fgAtivo");
		adicionarFiltro("listaBairro.fgAtivo", RestritorHb.RESTRITOR_EQ, "filtroMap.fgAtivoBairro");
		adicionarFiltro("listaBairro.fgAtivo", RestritorHb.RESTRITOR_EQ, "filtroMap.fgAtivoBairro");
		adicionarFiltro("listaEstabelecimento.dsFantasia", RestritorHb.RESTRITOR_IS_NOTNULL, "filtroMap.notNullFantasia");
	}
	
	public Cidade inativar(Cidade vo) throws Exception
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

	public Cidade inativar(Session sessao, Cidade vo) throws Exception
	{
		Cidade cidade = new Cidade();
		cidade.setIdCidade(vo.getIdCidade());
		cidade = this.get(sessao, cidade, 0);
				
		vo.setFgAtivo("N");
		vo.setIdUsuarioAlt(util.getUsuarioLogado().getIdUsuario());
		vo.setDtAlteracao(new Date());
		
		sessao.merge(vo);
		
		return vo;
	}
	
	public Cidade ativar(Cidade vo) throws Exception
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
	
	@Override
	protected void validarInserir(Session sessao, Cidade vo) throws Exception
	{
		Cidade cidade = new Cidade();
		cidade.setFiltroMap(new HashMap<String, Object>());
		cidade.getFiltroMap().put("dsCidade", vo.getDsCidade().trim());
		cidade.setIdEstado(vo.getIdEstado());
		
		cidade = this.get(sessao, cidade, 0);
		
		if(cidade != null)
		{
			throw new Exception("Esta cidade já está cadastrada na sua base de dados!");
		}
	}
	
	@Override
	protected void validarAlterar(Session sessao, Cidade vo) throws Exception
	{
		Cidade cidade = new Cidade();
		cidade.setFiltroMap(new HashMap<String, Object>());
		cidade.getFiltroMap().put("idCidadeNotEq", vo.getIdCidade());
		cidade.getFiltroMap().put("dsCidade", vo.getDsCidade().trim());
		cidade.setIdEstado(vo.getIdEstado());
		
		cidade = this.get(sessao, cidade, 0);
		
		if(cidade != null)
		{
			throw new Exception("Esta cidade já está cadastrada na sua base de dados!");
		}
	}
	
	public Cidade ativar(Session sessao, Cidade vo) throws Exception
	{
		Cidade cidade = new Cidade();
		cidade.setIdCidade(vo.getIdCidade());
		cidade = this.get(sessao, cidade, 0);
		
		vo.setFgAtivo("S");
		vo.setIdUsuarioAlt(util.getUsuarioLogado().getIdUsuario());
		vo.setDtAlteracao(new Date());
		
		super.alterar(sessao, vo);
		
		return vo;
	}
	
	@Override
	protected Criteria montaCriteria(Session sessao, int join)
	{
		Criteria criteria = sessao.createCriteria(Cidade.class);
		
		if ((join & JOIN_ESTADO) != 0)
	    {
			criteria.createAlias("estado", "estado");
	    }
		
		if ((join & JOIN_BAIRRO) != 0)
	    {
			criteria.createAlias("listaBairro", "listaBairro", JoinType.LEFT_OUTER_JOIN);
	    }
		
		if ((join & JOIN_USUARIO_CAD) != 0)
	    {
			criteria.createAlias("usuarioCad", "usuarioCad");
	    }
		
		if ((join & JOIN_USUARIO_ALT) != 0)
	    {
			criteria.createAlias("usuarioAlt", "usuarioAlt", JoinType.LEFT_OUTER_JOIN);
	    }
		
		if ((join & JOIN_ESTABELECIMENTO) != 0)
	    {
			criteria.createAlias("listaEstabelecimento", "listaEstabelecimento");
	    }
		
		return criteria;
	}
	
	@Override
	protected void setarOrdenacao(Criteria criteria, Cidade vo, int join)
	{
		if ((join & JOIN_ESTADO) != 0)
	    {
			criteria.addOrder(Order.asc("estado.dsSigla"));
	    }
		
		criteria.addOrder(Order.asc("dsCidade"));
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
