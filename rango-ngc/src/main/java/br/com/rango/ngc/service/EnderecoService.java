package br.com.rango.ngc.service;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;

import br.com.rango.ngc.entity.Endereco;
import br.com.rango.ngc.util.HibernateUtil;
import br.com.rango.ngc.util.RangoHbNgc;
import br.com.rango.ngc.util.RestritorHb;

public class EnderecoService extends RangoHbNgc<Endereco>
{
	private static EnderecoService instancia = null;
	
	public static final int JOIN_ESTADO = 1;
	
	
	@SuppressWarnings("rawtypes")
	private static Map filtroPropriedade = new HashMap();
	
	@SuppressWarnings("rawtypes")
	private static Map restritores = new HashMap();
	
	public static EnderecoService getInstancia()
	{
		if (instancia == null)
		{
			instancia = new EnderecoService();
		}
		return instancia;
	}
	
	public EnderecoService()
	{
		adicionarFiltro("idEndereco", RestritorHb.RESTRITOR_EQ, "idEndereco");
		adicionarFiltro("idUsuario", RestritorHb.RESTRITOR_EQ, "idUsuario");
	}
	
	public Endereco deletarEndereco(Endereco vo) throws Exception
	{
		Session sessao = HibernateUtil.getSession();
		sessao.setFlushMode(FlushMode.COMMIT);
		Transaction tx = sessao.beginTransaction();
		try
		{
			vo = deletarEndereco(sessao, vo);
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
	
	public Endereco deletarEndereco(Session sessao, Endereco vo) throws Exception
	{
		vo = this.get(sessao, vo, 0);		
		sessao.delete(vo);
		return vo;
	}
			
	@Override
	protected Criteria montaCriteria(Session sessao, int join)
	{
		Criteria criteria = sessao.createCriteria(Endereco.class);
		
		if ((join & JOIN_ESTADO) != 0)
	    {
			criteria.createAlias("estado", "estado");
	    }
		
		return criteria;
	}
	
	@Override
	protected void setarOrdenacao(Criteria criteria, Endereco vo, int join)
	{
		criteria.addOrder(Order.asc("dsEndereco"));
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
