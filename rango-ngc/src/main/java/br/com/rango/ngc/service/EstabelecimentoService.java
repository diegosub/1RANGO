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

import br.com.rango.ngc.entity.Bairro;
import br.com.rango.ngc.entity.Cidade;
import br.com.rango.ngc.entity.Estabelecimento;
import br.com.rango.ngc.entity.EstabelecimentoBairro;
import br.com.rango.ngc.entity.EstabelecimentoBandeira;
import br.com.rango.ngc.entity.EstabelecimentoHorario;
import br.com.rango.ngc.util.HibernateUtil;
import br.com.rango.ngc.util.RangoHbNgc;
import br.com.rango.ngc.util.RestritorHb;
import br.com.rango.ngc.util.jsf.JSFUtil;

public class EstabelecimentoService extends RangoHbNgc<Estabelecimento>
{
	private static EstabelecimentoService instancia = null;
	
	public static final int JOIN_CIDADE = 1;
	
	public static final int JOIN_CIDADE_ESTADO = 2;
	
	public static final int JOIN_CATEGORIA = 4;
	
	public static final int JOIN_USUARIO_CAD = 8;
	
	public static final int JOIN_USUARIO_ALT = 16;
	
	public static final int JOIN_ESTABELECIMENTO_BANDEIRA = 32;
	
	public static final int JOIN_ESTABELECIMENTO_HORARIO = 64;
	
	public static final int JOIN_ESTABELECIMENTO_BAIRRO = 128;
	
	public static final int JOIN_ESTABELECIMENTO_BAIRRO_BAIRRO = 256;
	
	public static final int JOIN_ESTABELECIMENTO_BAIRRO_BAIRRO_CIDADE = 512;
	
	public static final int JOIN_ESTABELECIMENTO_BAIRRO_BAIRRO_CIDADE_ESTADO = 1024;
	
	public static final int JOIN_ESTABELECIMENTO_IMAGEM = 2048;
	
	public static final int JOIN_FAVORITO = 4096;
	
	private JSFUtil util = new JSFUtil();

	@SuppressWarnings("rawtypes")
	private static Map filtroPropriedade = new HashMap();
	
	@SuppressWarnings("rawtypes")
	private static Map restritores = new HashMap();
	
	public static EstabelecimentoService getInstancia()
	{
		if (instancia == null)
		{
			instancia = new EstabelecimentoService();
		}
		return instancia;
	}
	
	public EstabelecimentoService()
	{
		adicionarFiltro("idEstabelecimento", RestritorHb.RESTRITOR_EQ, "idEstabelecimento");
		adicionarFiltro("dsEstabelecimento", RestritorHb.RESTRITOR_LIKE, "dsEstabelecimento");
		adicionarFiltro("dsFantasia", RestritorHb.RESTRITOR_EQ, "dsFantasia");
		adicionarFiltro("dsFantasia", RestritorHb.RESTRITOR_IS_NOTNULL, "filtroMap.notNullFantasia");
		adicionarFiltro("idEstabelecimento", RestritorHb.RESTRITOR_NE, "filtroMap.idEstabelecimentoNotEq");
		adicionarFiltro("idCidade", RestritorHb.RESTRITOR_EQ, "idCidade");
		adicionarFiltro("dsEstabelecimento", RestritorHb.RESTRITOR_EQ, "filtroMap.dsEstabelecimento");
		adicionarFiltro("fgAtivo", RestritorHb.RESTRITOR_EQ, "fgAtivo");
		adicionarFiltro("fgStatus", RestritorHb.RESTRITOR_EQ, "fgStatus");
		adicionarFiltro("cidade.fgAtivo", RestritorHb.RESTRITOR_EQ, "filtroMap.fgAtivoCidade");
		adicionarFiltro("bairro.fgAtivo", RestritorHb.RESTRITOR_EQ, "filtroMap.fgAtivoBairro");
		adicionarFiltro("setEstabelecimentoHorario.fgAtivo", RestritorHb.RESTRITOR_EQ, "filtroMap.flgAtivoHorario");
		adicionarFiltro("setEstabelecimentoHorario.nmHorario", RestritorHb.RESTRITOR_EQ, "filtroMap.nmHorario");
		adicionarFiltro("setFavorito.idUsuario", RestritorHb.RESTRITOR_EQ, "filtroMap.idUsuarioFavorito");
	}
	
	@Override
	protected void validarInserir(Session sessao, Estabelecimento vo) throws Exception
	{
		Estabelecimento estabelecimento = new Estabelecimento();
		estabelecimento.setFgAtivo("S");
		estabelecimento.setIdCidade(vo.getIdCidade());
		estabelecimento.setFiltroMap(new HashMap<String, Object>());
		estabelecimento.getFiltroMap().put("dsEstabelecimento", vo.getDsEstabelecimento().trim());		
		
		estabelecimento = this.get(sessao, estabelecimento, 0);
		
		if(estabelecimento != null)
		{
			throw new Exception("Este estabelecimento já está cadastrado na sua base de dados!");
		}
	}
	
	@Override
	protected void validarAlterar(Session sessao, Estabelecimento vo) throws Exception
	{
		Estabelecimento estabelecimento = new Estabelecimento();
		estabelecimento.setFiltroMap(new HashMap<String, Object>());
		estabelecimento.getFiltroMap().put("idEstabelecimentoNotEq", vo.getIdEstabelecimento());
		estabelecimento.setDsFantasia((vo.getDsFantasia() == null || vo.getDsFantasia().trim().equals("")) ? vo.getDsEstabelecimento() : vo.getDsFantasia().trim());
		estabelecimento.setFgAtivo(vo.getFgAtivo());
		
		estabelecimento = this.get(sessao, estabelecimento, 0);
		
		if(estabelecimento != null)
		{
			throw new Exception("Este estabelecimento já está cadastrado na sua base de dados!");
		}
	}
	
	public Estabelecimento inativar(Estabelecimento vo) throws Exception
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

	public Estabelecimento inativar(Session sessao, Estabelecimento vo) throws Exception
	{
		Estabelecimento estabelecimento = new Estabelecimento();
		estabelecimento.setIdEstabelecimento(vo.getIdEstabelecimento());
		estabelecimento = this.get(sessao, estabelecimento, 0);
				
		vo.setFgAtivo("N");
		vo.setIdUsuarioAlt(util.getUsuarioLogado().getIdUsuario());
		vo.setDtAlteracao(new Date());
		
		sessao.merge(vo);
		
		return vo;
	}
	
	public Estabelecimento ativar(Estabelecimento vo) throws Exception
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
	
	public Estabelecimento ativar(Session sessao, Estabelecimento vo) throws Exception
	{
		Estabelecimento estabelecimento = new Estabelecimento();
		estabelecimento.setIdEstabelecimento(vo.getIdEstabelecimento());
		estabelecimento = this.get(sessao, estabelecimento, 0);
		
		vo.setFgAtivo("S");
		vo.setIdUsuarioAlt(util.getUsuarioLogado().getIdUsuario());
		vo.setDtAlteracao(new Date());
		
		super.alterar(sessao, vo);
		
		return vo;
	}
	
	public Estabelecimento salvar(Estabelecimento vo) throws Exception
	{
		Session sessao = HibernateUtil.getSession();
		sessao.setFlushMode(FlushMode.COMMIT);
		Transaction tx = sessao.beginTransaction();
		try
		{
			vo = salvar(sessao, vo);
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
	
	public Estabelecimento salvar(Session sessao, Estabelecimento vo) throws Exception
	{		
		super.alterar(sessao, vo);
		
		//BANDEIRA
		EstabelecimentoBandeira estabelecimentoBandeira = new EstabelecimentoBandeira();
		estabelecimentoBandeira.setIdEstabelecimento(vo.getIdEstabelecimento());
		List<EstabelecimentoBandeira> listaBandeira = EstabelecimentoBandeiraService.getInstancia().pesquisar(sessao, estabelecimentoBandeira, 0);
		
		for (EstabelecimentoBandeira objDelete : listaBandeira)
		{
			sessao.delete(objDelete);
		}
		
		if(vo.getListaEstabelecimentoBandeira() != null
				&& vo.getListaEstabelecimentoBandeira().size() > 0)
		{
			for (EstabelecimentoBandeira bandeira : vo.getListaEstabelecimentoBandeira())
			{
				sessao.save(bandeira);
			}
		}
		
		//HORARIOS
		if(vo.getListaEstabelecimentoHorario() != null
				&& vo.getListaEstabelecimentoHorario().size() > 0)
		{
			for (EstabelecimentoHorario horario : vo.getListaEstabelecimentoHorario())
			{
				EstabelecimentoHorario objPersist = new EstabelecimentoHorario();
				objPersist.setIdEstabelecimento(vo.getIdEstabelecimento());
				objPersist.setNmHorario(horario.getNmHorario());
				
				objPersist = EstabelecimentoHorarioService.getInstancia().get(sessao, objPersist, 0);
				
				if(objPersist != null)
				{
					objPersist.setHrInicio(horario.getHrInicio());
					objPersist.setHrFim(horario.getHrFim());
					objPersist.setFgAtivo(horario.getFgAtivo());
					
					EstabelecimentoHorarioService.getInstancia().alterar(sessao, objPersist);
				}
				else
				{
					objPersist = new EstabelecimentoHorario();
					
					JSFUtil.copiarPropriedades(horario, objPersist);
					objPersist.setIdEstabelecimento(vo.getIdEstabelecimento());
					EstabelecimentoHorarioService.getInstancia().inserir(sessao, objPersist);
				}
			}
		}
				
		//BAIRROS
		EstabelecimentoBairro estabelecimentoBairro = new EstabelecimentoBairro();
		estabelecimentoBairro.setIdEstabelecimento(vo.getIdEstabelecimento());
		List<EstabelecimentoBairro> lista = EstabelecimentoBairroService.getInstancia().pesquisar(sessao, estabelecimentoBairro, 0);
		
		for (EstabelecimentoBairro objDelete : lista)
		{
			sessao.delete(objDelete);
		}
		
		if(vo.getListaCidade() != null
				&& vo.getListaCidade().size() > 0)
		{
			for (Cidade cidade : vo.getListaCidade())
			{
				for (Bairro bairro : cidade.getListaBairro())
				{
					if(bairro.isFgSelecionado())
					{
						EstabelecimentoBairro objInsert = new EstabelecimentoBairro();
						objInsert.setIdEstabelecimento(vo.getIdEstabelecimento());
						objInsert.setIdBairro(bairro.getIdBairro());
						objInsert.setVlTaxa(new Double(bairro.getStrValorTaxa().toString().replace(".", "").replace(",", ".")));
						
						EstabelecimentoBairroService.getInstancia().inserir(sessao, objInsert);
					}
				}
			}
		}
		
		return vo;
	}
	
	public void ativarStatusEstabelecimento() throws Exception
	{
		Session sessao = HibernateUtil.getSession();
		sessao.setFlushMode(FlushMode.COMMIT);
		Transaction tx = sessao.beginTransaction();
		try
		{
			this.ativarStatusEstabelecimento(sessao);
			tx.commit();			
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
	
	public void ativarStatusEstabelecimento(Session sessao) throws Exception
	{
		Estabelecimento estabelecimento = new Estabelecimento();
		estabelecimento.setIdEstabelecimento(util.getUsuarioLogado().getIdEstabelecimento());
		estabelecimento = this.get(sessao, estabelecimento, 0);
		
		if(estabelecimento != null)
		{
			estabelecimento.setFgStatus("A");
			estabelecimento.setDtAlteracao(new Date());
			estabelecimento.setIdUsuarioAlt(util.getUsuarioLogado().getIdUsuario());
			
			estabelecimento = super.alterar(sessao, estabelecimento);
			
			util.getUsuarioLogado().setEstabelecimento(estabelecimento);
		}
	}
	
	public void inativarStatusEstabelecimento() throws Exception
	{
		Session sessao = HibernateUtil.getSession();
		sessao.setFlushMode(FlushMode.COMMIT);
		Transaction tx = sessao.beginTransaction();
		try
		{
			this.inativarStatusEstabelecimento(sessao);
			tx.commit();			
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
	
	public void inativarStatusEstabelecimento(Session sessao) throws Exception
	{
		Estabelecimento estabelecimento = new Estabelecimento();
		estabelecimento.setIdEstabelecimento(util.getUsuarioLogado().getIdEstabelecimento());
		estabelecimento = this.get(sessao, estabelecimento, 0);
		
		if(estabelecimento != null)
		{
			estabelecimento.setFgStatus("I");
			estabelecimento.setDtAlteracao(new Date());
			estabelecimento.setIdUsuarioAlt(util.getUsuarioLogado().getIdUsuario());
			
			estabelecimento = super.alterar(sessao, estabelecimento);
			
			util.getUsuarioLogado().setEstabelecimento(estabelecimento);
		}
	}
	
	@Override
	protected Criteria montaCriteria(Session sessao, int join)
	{
		Criteria criteria = sessao.createCriteria(Estabelecimento.class);
		
		if ((join & JOIN_CIDADE) != 0)
	    {
			criteria.createAlias("cidade", "cidade");
			
			if ((join & JOIN_CIDADE_ESTADO) != 0)
		    {
				criteria.createAlias("cidade.estado", "estado");
		    }
	    }
		
		if ((join & JOIN_CATEGORIA) != 0)
	    {
			criteria.createAlias("categoria", "categoria");
	    }
		
		if ((join & JOIN_USUARIO_CAD) != 0)
	    {
			criteria.createAlias("usuarioCad", "usuarioCad");
	    }
		
		if ((join & JOIN_USUARIO_ALT) != 0)
	    {
			criteria.createAlias("usuarioAlt", "usuarioAlt", JoinType.LEFT_OUTER_JOIN);
	    }
		
		if ((join & JOIN_ESTABELECIMENTO_BAIRRO) != 0)
	    {
			criteria.createAlias("setEstabelecimentoBairro", "setEstabelecimentoBairro", JoinType.LEFT_OUTER_JOIN);
			
			if ((join & JOIN_ESTABELECIMENTO_BAIRRO_BAIRRO) != 0)
		    {
				criteria.createAlias("setEstabelecimentoBairro.bairro", "bairro", JoinType.LEFT_OUTER_JOIN);
				
				if ((join & JOIN_ESTABELECIMENTO_BAIRRO_BAIRRO_CIDADE) != 0)
			    {
					criteria.createAlias("bairro.cidade", "cidade", JoinType.LEFT_OUTER_JOIN);
					
					if ((join & JOIN_ESTABELECIMENTO_BAIRRO_BAIRRO_CIDADE_ESTADO) != 0)
				    {
						criteria.createAlias("cidade.estado", "estado", JoinType.LEFT_OUTER_JOIN);
				    }
			    }
		    }
	    }
		
		if ((join & JOIN_ESTABELECIMENTO_BANDEIRA) != 0)
	    {
			criteria.createAlias("setEstabelecimentoBandeira", "setEstabelecimentoBandeira", JoinType.LEFT_OUTER_JOIN);
	    }
		
		if ((join & JOIN_ESTABELECIMENTO_HORARIO) != 0)
	    {
			criteria.createAlias("setEstabelecimentoHorario", "setEstabelecimentoHorario", JoinType.LEFT_OUTER_JOIN);
	    }
		
		if ((join & JOIN_ESTABELECIMENTO_IMAGEM) != 0)
	    {
			criteria.createAlias("setEstabelecimentoImagem", "setEstabelecimentoImagem", JoinType.LEFT_OUTER_JOIN);
	    }
		
		if ((join & JOIN_FAVORITO) != 0)
	    {
			criteria.createAlias("setFavorito", "setFavorito");
	    }
		
		return criteria;
	}
	
	@Override
	protected void setarOrdenacao(Criteria criteria, Estabelecimento vo, int join)
	{
		criteria.addOrder(Order.asc("dsEstabelecimento"));
		
		if ((join & JOIN_ESTABELECIMENTO_HORARIO) != 0)
	    {
			criteria.addOrder(Order.asc("setEstabelecimentoHorario.nmHorario"));
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
