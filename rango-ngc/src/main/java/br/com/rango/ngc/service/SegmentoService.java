package br.com.rango.ngc.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.sql.JoinType;

import br.com.rango.ngc.entity.Segmento;
import br.com.rango.ngc.entity.SegmentoAdicional;
import br.com.rango.ngc.entity.SegmentoDia;
import br.com.rango.ngc.entity.SegmentoItem;
import br.com.rango.ngc.util.HibernateUtil;
import br.com.rango.ngc.util.RangoHbNgc;
import br.com.rango.ngc.util.RestritorHb;
import br.com.rango.ngc.util.jsf.JSFUtil;

public class SegmentoService extends RangoHbNgc<Segmento>
{
	private static SegmentoService instancia = null;
	
	public static final int JOIN_USUARIO_CAD = 1;
	
	public static final int JOIN_USUARIO_ALT = 2;
	
	public static final int JOIN_SEGMENTO_DIA = 4;
	
	public static final int JOIN_PRODUTO = 8;
	
	private JSFUtil util = new JSFUtil();
	
	@SuppressWarnings("rawtypes")
	private static Map filtroPropriedade = new HashMap();
	
	@SuppressWarnings("rawtypes")
	private static Map restritores = new HashMap();
	
	public static SegmentoService getInstancia()
	{
		if (instancia == null)
		{
			instancia = new SegmentoService();
		}
		return instancia;
	}
	
	public SegmentoService()
	{
		adicionarFiltro("idSegmento", RestritorHb.RESTRITOR_EQ, "idSegmento");
		adicionarFiltro("dsSegmento", RestritorHb.RESTRITOR_EQ, "filtroMap.dsSegmento");
		adicionarFiltro("dsSegmento", RestritorHb.RESTRITOR_LIKE, "dsSegmento");
		adicionarFiltro("idEstabelecimento", RestritorHb.RESTRITOR_EQ, "idEstabelecimento");
		adicionarFiltro("fgAtivo", RestritorHb.RESTRITOR_EQ, "fgAtivo");
		adicionarFiltro("setProduto.fgAtivo", RestritorHb.RESTRITOR_EQ, "filtroMap.fgAtivoProduto");
		adicionarFiltro("listaSegmentoDia.idDia", RestritorHb.RESTRITOR_EQ, "filtroMap.idDia");
	}
	
	@Override
	protected void validarInserir(Session sessao, Segmento vo) throws Exception
	{
		Segmento segmento = new Segmento();
		segmento.setFgAtivo("S");
		segmento.setFiltroMap(new HashMap<String, Object>());
		segmento.getFiltroMap().put("dsSegmento", vo.getDsSegmento().trim());		
		
		segmento = this.get(sessao, segmento, 0);
		
		if(segmento != null)
		{
			throw new Exception("Já existe um segmento cadastrado com esse nome.");
		}
	}
	
	@Override
	public Segmento inserir(Session sessao, Segmento vo) throws Exception
	{
		super.inserir(sessao, vo);
		sessao.flush();
		
		//INSERINDO PERGUNTAS 
		if(vo.getListaSegmentoAdicional() != null
				&& vo.getListaSegmentoAdicional().size() > 0)
		{
			for (SegmentoAdicional obj : vo.getListaSegmentoAdicional())
			{
				SegmentoAdicional segmentoAdicional = new SegmentoAdicional();
				JSFUtil.copiarPropriedades(obj, segmentoAdicional);
				segmentoAdicional.setIdSegmento(vo.getIdSegmento());
				segmentoAdicional.setFgAtivo("S");
				segmentoAdicional.setDtCadastro(new Date());
				segmentoAdicional.setIdUsuarioCad(util.getUsuarioLogado().getIdUsuario());
				
				segmentoAdicional = SegmentoAdicionalService.getInstancia().inserir(sessao, segmentoAdicional);
				sessao.flush();
				
				//INSERINDO ITENS
				for (SegmentoItem objItem : obj.getListaSegmentoItem())
				{
					SegmentoItem segmentoItem = new SegmentoItem();
					JSFUtil.copiarPropriedades(objItem, segmentoItem);
					segmentoItem.setIdSegmentoAdicional(segmentoAdicional.getIdSegmentoAdicional());
					
					SegmentoItemService.getInstancia().inserir(sessao, segmentoItem);
				}
				
			}
		}
		
		//INSERINDO DIAS
		if(vo.getListaSegmentoDia() != null
				&& vo.getListaSegmentoDia().size() > 0)
		{
			for (SegmentoDia obj : vo.getListaSegmentoDia())
			{
				SegmentoDia segmentoDia = new SegmentoDia();
				segmentoDia.setIdSegmento(vo.getIdSegmento());
				segmentoDia.setIdDia(obj.getIdDia());
				
				SegmentoDiaService.getInstancia().inserir(sessao, segmentoDia);
			}
		}			
		
		return vo;
	}
	
	@Override
	protected void validarAlterar(Session sessao, Segmento vo) throws Exception
	{
		Segmento segmento = new Segmento();
		segmento.setFgAtivo("S");
		segmento.setFiltroMap(new HashMap<String, Object>());
		segmento.getFiltroMap().put("dsSegmento", vo.getDsSegmento().trim());		
		
		segmento = this.get(sessao, segmento, 0);
		
		if(segmento != null
				&& segmento.getIdSegmento().intValue() != vo.getIdSegmento().intValue())
		{
			throw new Exception("Já existe um segmento cadastrado com esse nome.");
		}
	}
	
	@Override
	public Segmento alterar(Session sessao, Segmento vo) throws Exception
	{
		super.alterar(sessao, vo);
		
		//ALTERANDO DIAS (REMOVE TUDO DA BASE E INSERE OS SELECIONADOS NA TELA)
		SegmentoDia sd = new SegmentoDia();
		sd.setIdSegmento(vo.getIdSegmento());
		List<SegmentoDia> lista = SegmentoDiaService.getInstancia().pesquisar(sessao, sd, 0);
		
		for (SegmentoDia obj : lista) 
		{
			sessao.delete(obj);
		}
		
		//INSERINDO DIAS
		if(vo.getListaSegmentoDia() != null
				&& vo.getListaSegmentoDia().size() > 0)
		{
			for (SegmentoDia obj : vo.getListaSegmentoDia())
			{
				SegmentoDia segmentoDia = new SegmentoDia();
				segmentoDia.setIdSegmento(vo.getIdSegmento());
				segmentoDia.setIdDia(obj.getIdDia());
				
				SegmentoDiaService.getInstancia().inserir(sessao, segmentoDia);
			}
		}
		
		
		//PERGUNTAS ADICIONADAS
		if(vo.getListaSegmentoAdicional() != null
				&& vo.getListaSegmentoAdicional().size() > 0)
		{
			for (SegmentoAdicional obj : vo.getListaSegmentoAdicional())
			{
				if(obj.getIdSegmentoAdicional() == null
						|| obj.getIdSegmentoAdicional().intValue() <= 0)
				{
					SegmentoAdicional segmentoAdicional = new SegmentoAdicional();
					JSFUtil.copiarPropriedades(obj, segmentoAdicional);
					segmentoAdicional.setIdSegmento(vo.getIdSegmento());
					segmentoAdicional.setFgAtivo("S");
					segmentoAdicional.setDtCadastro(new Date());
					segmentoAdicional.setIdUsuarioCad(util.getUsuarioLogado().getIdUsuario());
					
					segmentoAdicional = SegmentoAdicionalService.getInstancia().inserir(sessao, segmentoAdicional);
					sessao.flush();
					
					//INSERINDO ITENS
					for (SegmentoItem objItem : obj.getListaSegmentoItem())
					{
						SegmentoItem segmentoItem = new SegmentoItem();
						JSFUtil.copiarPropriedades(objItem, segmentoItem);
						segmentoItem.setIdSegmentoAdicional(segmentoAdicional.getIdSegmentoAdicional());						
						
						SegmentoItemService.getInstancia().inserir(sessao, segmentoItem);
					}
				}
			}
		}
		
		//PERGUNTAS REMOVIDAS E MANTIDAS
		if(vo.getListaSegmentoAdicionalBase() != null
				&& vo.getListaSegmentoAdicionalBase().size() > 0)
		{
			for (SegmentoAdicional obj : vo.getListaSegmentoAdicionalBase())
			{
				SegmentoAdicional segmentoAdicional = new SegmentoAdicional();
				JSFUtil.copiarPropriedades(obj, segmentoAdicional);			
				segmentoAdicional.setFgAtivo("N");
				segmentoAdicional.setDtAlteracao(new Date());
				segmentoAdicional.setIdUsuarioAlt(util.getUsuarioLogado().getIdUsuario());
				
				if(vo.getListaSegmentoAdicional() != null
						&& vo.getListaSegmentoAdicional().size() > 0)
				{
					for (SegmentoAdicional objBase : vo.getListaSegmentoAdicional())
					{	
						if(objBase.getIdSegmentoAdicional() != null &&
								(obj.getIdSegmentoAdicional().intValue() == objBase.getIdSegmentoAdicional().intValue()))
						{
							segmentoAdicional.setFgAtivo("S");
						}
					}
				}
				
				segmentoAdicional = SegmentoAdicionalService.getInstancia().alterar(sessao, segmentoAdicional);
			}
		}
		
		return vo;
	}
	
	public Segmento inativar(Segmento vo) throws Exception
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

	public Segmento inativar(Session sessao, Segmento vo) throws Exception
	{
		Segmento segmento = new Segmento();
		segmento.setIdSegmento(vo.getIdSegmento());
		segmento = this.get(sessao, segmento, 0);
				
		vo.setFgAtivo("N");
		vo.setIdUsuarioAlt(util.getUsuarioLogado().getIdUsuario());
		vo.setDtAlteracao(new Date());
		
		sessao.merge(vo);
		
		return vo;
	}
	
	public Segmento ativar(Segmento vo) throws Exception
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
	
	public Segmento ativar(Session sessao, Segmento vo) throws Exception
	{
		Segmento segmento = new Segmento();
		segmento.setIdSegmento(vo.getIdSegmento());
		segmento = this.get(sessao, segmento, 0);
		
		vo.setFgAtivo("S");
		vo.setIdUsuarioAlt(util.getUsuarioLogado().getIdUsuario());
		vo.setDtAlteracao(new Date());
		
		super.alterar(sessao, vo);
		
		return vo;
	}
	
	@Override
	protected Criteria montaCriteria(Session sessao, int join)
	{
		Criteria criteria = sessao.createCriteria(Segmento.class);
				
		if ((join & JOIN_USUARIO_CAD) != 0)
	    {
			criteria.createAlias("usuarioCad", "usuarioCad");
	    }
		
		if ((join & JOIN_USUARIO_ALT) != 0)
	    {
			criteria.createAlias("usuarioAlt", "usuarioAlt", JoinType.LEFT_OUTER_JOIN);
	    }
	
		if ((join & JOIN_SEGMENTO_DIA) != 0)
	    {
			criteria.createAlias("listaSegmentoDia", "listaSegmentoDia", JoinType.LEFT_OUTER_JOIN);
	    }
		
		if ((join & JOIN_PRODUTO) != 0)
	    {
			criteria.createAlias("setProduto", "setProduto", JoinType.LEFT_OUTER_JOIN);
	    }
		
		return criteria;
	}
	
	@Override
	protected void setarOrdenacao(Criteria criteria, Segmento vo, int join)
	{
		criteria.addOrder(Order.asc("dsSegmento"));
		
		if ((join & JOIN_PRODUTO) != 0)
	    {
			criteria.addOrder(Order.asc("setProduto.dsProduto"));
	    }
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

