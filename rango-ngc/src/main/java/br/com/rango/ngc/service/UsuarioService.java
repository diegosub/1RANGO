package br.com.rango.ngc.service;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;

import br.com.rango.ngc.entity.Parametro;
import br.com.rango.ngc.entity.RecuperaSenha;
import br.com.rango.ngc.entity.Usuario;
import br.com.rango.ngc.util.HibernateUtil;
import br.com.rango.ngc.util.RangoHbNgc;
import br.com.rango.ngc.util.RestritorHb;
import br.com.rango.ngc.util.criptografia.CriptoMD5;
import br.com.rango.ngc.util.jsf.JSFUtil;
import br.com.rango.ngc.util.outros.Email;
import br.com.rango.ngc.util.outros.SenhaRandom;
import br.com.rango.ngc.util.outros.email.CadastroEmail;
import br.com.rango.ngc.util.outros.email.RecuperarSenhaEmail;

public class UsuarioService extends RangoHbNgc<Usuario>
{
	private JSFUtil util = new JSFUtil();
	
	private static UsuarioService instancia = null;
	
	public static final int JOIN_GRUPO = 1;
		
	public static final int JOIN_USUARIO_CAD = 2;
	
	public static final int JOIN_USUARIO_ALT = 4;
	
	public static final int JOIN_ESTADO = 8;
	
	public static final int JOIN_ESTABELECIMENTO = 16;

	@SuppressWarnings("rawtypes")
	private static Map filtroPropriedade = new HashMap();
	
	@SuppressWarnings("rawtypes")
	private static Map restritores = new HashMap();
	
	public static UsuarioService getInstancia()
	{
		if (instancia == null)
		{
			instancia = new UsuarioService();
		}
		return instancia;
	}
	
	public UsuarioService()
	{
		adicionarFiltro("dsNome", RestritorHb.RESTRITOR_LIKE, "dsNome");
		adicionarFiltro("dsNome", RestritorHb.RESTRITOR_EQ, "filtroMap.dsNome");
		adicionarFiltro("idUsuario", RestritorHb.RESTRITOR_EQ,"idUsuario");
		adicionarFiltro("idUsuario", RestritorHb.RESTRITOR_NE, "filtroMap.idUsuarioNotEq");
		adicionarFiltro("dsLogin", RestritorHb.RESTRITOR_EQ, "dsLogin");
		adicionarFiltro("dsLogin", RestritorHb.RESTRITOR_LIKE, "filtroMap.likeLogin");
		adicionarFiltro("dsEmail", RestritorHb.RESTRITOR_EQ, "dsEmail");
		adicionarFiltro("dsSenha", RestritorHb.RESTRITOR_EQ, "dsSenha");
		adicionarFiltro("fgAtivo", RestritorHb.RESTRITOR_EQ, "fgAtivo");
		adicionarFiltro("idConselho", RestritorHb.RESTRITOR_EQ, "idConselho");
		adicionarFiltro("nmConselho", RestritorHb.RESTRITOR_EQ, "nmConselho");
		adicionarFiltro("dsCpf", RestritorHb.RESTRITOR_EQ, "dsCpf");
		adicionarFiltro("fgAtivo", RestritorHb.RESTRITOR_EQ, "fgAtivo");
		adicionarFiltro("grupo.fgAtivo", RestritorHb.RESTRITOR_EQ, "filtroMap.fgAtivoGrupo");
		adicionarFiltro("listaGrupoTelaAcao.fgAtivo", RestritorHb.RESTRITOR_EQ, "filtroMap.fgAtivoGrupoTelaAcao");
		adicionarFiltro("telaAcao.fgAtivo", RestritorHb.RESTRITOR_EQ, "filtroMap.fgAtivoTelaAcao");
		adicionarFiltro("idCliente", RestritorHb.RESTRITOR_EQ, "idCliente");
	}
	
	@SuppressWarnings("unchecked")
	protected void validarInserir(Session sessao, Usuario vo) throws Exception
	{
		Criteria criteria = sessao.createCriteria(Usuario.class);
		Disjunction or = Restrictions.disjunction();
		or.add(Restrictions.eq("dsNome", vo.getDsNome()).ignoreCase());
		or.add(Restrictions.eq("dsEmail",vo.getDsEmail()).ignoreCase());
		or.add(Restrictions.eq("dsLogin",vo.getDsLogin()).ignoreCase());
		or.add(Restrictions.eq("dsCpf",vo.getDsCpf()).ignoreCase());
		criteria.add(or);
		
		List<Usuario> lista = criteria.list();
		
		if(lista != null
				&& lista.size() > 0)
		{
			if(vo.getDsNome().equalsIgnoreCase(lista.get(0).getDsNome()))
			{
				throw new Exception("Já existe um usuário cadastrado com este Nome.");
			}
			if(vo.getDsEmail().equalsIgnoreCase(lista.get(0).getDsEmail()))
			{
				throw new Exception("Já existe um usuário cadastrado com este E-mail.");
			}
			if(vo.getDsLogin().equalsIgnoreCase(lista.get(0).getDsLogin()))
			{
				throw new Exception("Já existe um usuário cadastrado com este Login.");
			}
			
			if(vo.getDsCpf().equalsIgnoreCase(lista.get(0).getDsCpf()))
			{
				throw new Exception("Já existe um usuário cadastrado com este Cpf.");
			}
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	protected void validarAlterar(Session sessao, Usuario vo) throws Exception
	{
		Criteria criteria = sessao.createCriteria(Usuario.class);
		Disjunction or = Restrictions.disjunction();
		or.add(Restrictions.eq("dsNome", vo.getDsNome()).ignoreCase());
		or.add(Restrictions.eq("dsEmail",vo.getDsEmail()).ignoreCase());
		or.add(Restrictions.eq("dsLogin",vo.getDsLogin()).ignoreCase());
		or.add(Restrictions.eq("dsCpf",vo.getDsCpf()).ignoreCase());
		criteria.add(or);
		
		List<Usuario> lista = criteria.list();
		
		if(lista != null
				&& lista.size() > 0)
		{
			for (Usuario usuario : lista)
			{
				if(usuario.getIdUsuario().intValue() != vo.getIdUsuario().intValue())
				{
					if(vo.getDsNome().equalsIgnoreCase(usuario.getDsNome()))
					{
						throw new Exception("Já existe um usuário cadastrado com este Nome.");
					}
					if(vo.getDsEmail().equalsIgnoreCase(usuario.getDsEmail()))
					{
						throw new Exception("Já existe um usuário cadastrado com este E-mail.");
					}
					if(vo.getDsLogin().equalsIgnoreCase(usuario.getDsLogin()))
					{
						throw new Exception("Já existe um usuário cadastrado com este Login.");
					}
					
					if(vo.getDsCpf().equalsIgnoreCase(usuario.getDsCpf()))
					{
						throw new Exception("Já existe um usuário cadastrado com este Cpf.");
					}
				}
			}
		}
	}
	
//	@Override
//	public Usuario alterar(Session sessao, Usuario vo) throws Exception
//	{
//		this.validarAlterar(sessao, vo);
//		
//		Usuario usuario = new Usuario();
//		usuario.setIdUsuario(vo.getIdUsuario());		
//		usuario = this.get(sessao, usuario, 0);
//		
//		UsuarioLog usuarioLog = new UsuarioLog();
//		JSFUtil.copiarPropriedades(usuario, usuarioLog);
//		UsuarioServiceLog.getInstancia().inserir(sessao, usuarioLog);
//		
//		sessao.merge(vo);
//		
//		return vo;
//	}
	
	@Override
	public Usuario inserir(Session sessao, Usuario vo) throws Exception
	{
		this.validarInserir(sessao, vo);
		
		//GERAR SENHA AUTOMATICA E INSERIR USUARIO
		String senha = SenhaRandom.getSenhaRandom();
		vo.setDsSenha(CriptoMD5.stringHexa(senha));
		sessao.save(vo);
		sessao.flush();
		
		//ENVIAR EMAIL
		this.enviarEmailSenha(vo, senha);
		
		return vo;
	}
	
	public void enviarEmailCadastro(Usuario vo, String msg) throws Exception
	{
		Email email = new Email();
		
		String assunto = vo.getDsNome() + ", Bem-vindo ao 1Rango!";
		String texto = msg;
		
		String to = vo.getDsEmail();
		
		email.enviar(to, assunto, texto);		
	}
	
	private void enviarEmailSenha(Usuario vo, String senha) throws Exception
	{
		Email email = new Email();
		
		String assunto = "Acesso 1Rango - Vai 1RanGO ai?";
		String texto = "Nome: "+ vo.getDsNome() +" \n" +
				   "Login: "+ vo.getDsLogin() +" \n" +
				   "Senha: "+ senha;
		
		String to = vo.getDsEmail();
		
		email.enviar(to, assunto, texto);		
	}
	
	private void enviarEmailRecuperaSenha(Usuario vo, String link) throws Exception
	{
		Email email = new Email();
		
		String assunto = "1Rango! - Recuperação de Senha";
		String msg = RecuperarSenhaEmail.getEmailRecuperarSenha(link);
		
		String to = vo.getDsEmail();
		
		email.enviar(to, assunto, msg);		
	}
	
	public Usuario inativar(Usuario vo) throws Exception
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

	public Usuario inativar(Session sessao, Usuario vo) throws Exception
	{
		Usuario usuario = new Usuario();
		usuario.setIdUsuario(vo.getIdUsuario());
		usuario = this.get(sessao, usuario, 0);
				
		vo.setFgAtivo("N");
		vo.setIdUsuarioAlt(util.getUsuarioLogado().getIdUsuario());
		vo.setDtAlteracao(new Date());
		
		sessao.merge(vo);
		
		return vo;
	}
	
	public Usuario ativar(Usuario vo) throws Exception
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
	
	public Usuario ativar(Session sessao, Usuario vo) throws Exception
	{
		Usuario usuario = new Usuario();
		usuario.setIdUsuario(vo.getIdUsuario());
		usuario = this.get(sessao, usuario, 0);
		
		vo.setFgAtivo("S");
		vo.setIdUsuarioAlt(util.getUsuarioLogado().getIdUsuario());
		vo.setDtAlteracao(new Date());
		
		super.alterar(sessao, vo);
		
		return vo;
	}
	
	public Usuario alterarSenha(Usuario vo, String senhaAtual, String novaSenha) throws Exception
	{
		Session sessao = HibernateUtil.getSession();
		sessao.setFlushMode(FlushMode.COMMIT);
		Transaction tx = sessao.beginTransaction();
		try
		{
			vo = alterarSenha(sessao, vo, senhaAtual, novaSenha);
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
	
	public Usuario alterarSenha(Session sessao, Usuario vo, String senhaAtual, String novaSenha) throws Exception
	{
		Usuario usuario = new Usuario();
		usuario.setIdUsuario(vo.getIdUsuario());
		usuario.setDsLogin(vo.getDsLogin().toUpperCase().trim());
		usuario.setDsSenha(CriptoMD5.stringHexa(senhaAtual.toUpperCase()));
		
		usuario = this.get(sessao, usuario, 0);
		
		if(usuario != null)
		{
			usuario.setDsSenha(CriptoMD5.stringHexa(novaSenha.toUpperCase()));
			usuario.setIdUsuarioAlt(vo.getIdUsuarioAlt());
			usuario.setDtAlteracao(vo.getDtAlteracao());
			
			usuario = this.alterar(sessao, usuario);
		}
		else
		{
			throw new Exception("O campo Senha Atual está incorreto.");
		}
		
		return usuario;
	}
	
	public Usuario recuperarSenhaViaApp(Usuario vo) throws Exception
	{
		Session sessao = HibernateUtil.getSession();
		sessao.setFlushMode(FlushMode.COMMIT);
		Transaction tx = sessao.beginTransaction();
		try
		{
			vo = recuperarSenhaViaApp(sessao, vo);
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
	
	public Usuario recuperarSenhaViaApp(Session sessao, Usuario vo) throws Exception
	{
		Usuario usuario = new Usuario();
		usuario.setDsEmail(vo.getDsEmail());
		usuario.setFgAtivo("S");
		usuario.setIdGrupo(new BigInteger(Integer.toString(GrupoService.GRUPO_CLIENTE)));
		
		usuario = this.get(sessao, usuario, 0);
		
		if(usuario != null)
		{
			Parametro parametro = new Parametro();
			parametro.setDsParametro("LINK_RANGO_WEB");
			parametro = ParametroService.getInstancia().get(sessao, parametro, 0);
			
			String hash = CriptoMD5.stringHexa(usuario.getIdUsuario() + "/" + new SimpleDateFormat("ddMMyyyy").format(new Date()) + "/1rango");
			String link = parametro.getVlParametro() + "pages/recuperarSenha.jsf?id=" + hash;  
			
			//VERIFICA SE JA EXISTE LINK GERADO (CASO EXISTA, ATUALIZA PARA O NOVO.)			
			RecuperaSenha recuperaSenha = new RecuperaSenha();
			recuperaSenha.setIdUsuario(usuario.getIdUsuario());
			recuperaSenha = RecuperaSenhaService.getInstancia().get(sessao, recuperaSenha, 0);
			
			if(recuperaSenha != null) 
			{
				//ATUALIZAR HASH
				recuperaSenha.setDsHash(hash);
				recuperaSenha.setDtSolicitacao(new Date());
				
				RecuperaSenhaService.getInstancia().alterar(sessao, recuperaSenha);
			}
			else
			{
				//INSERIR REGISTRO
				recuperaSenha = new RecuperaSenha();
				recuperaSenha.setIdUsuario(usuario.getIdUsuario());
				recuperaSenha.setDsHash(hash);
				recuperaSenha.setDtSolicitacao(new Date());
				
				RecuperaSenhaService.getInstancia().inserir(sessao, recuperaSenha);
			}
						
			//ENVIAR EMAIL COM O LINK
			this.enviarEmailRecuperaSenha(usuario, link);
		}
		else
		{
			throw new Exception("Não existe usuário cadastrado com este e-mail.");
		}
		
		return usuario;
	}
	
	
	
	//APP
	public Usuario inserirViaApp(Usuario vo) throws Exception
	{
		Session sessao = HibernateUtil.getSession();
		sessao.setFlushMode(FlushMode.COMMIT);
		Transaction tx = sessao.beginTransaction();
		try
		{
			vo = inserirViaApp(sessao, vo);
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
	
	public Usuario inserirViaApp(Session sessao, Usuario vo) throws Exception
	{
		//VERIFICAR EMAIL
		Usuario usuario = new Usuario();
		usuario.setIdGrupo(new BigInteger(Integer.toString(GrupoService.GRUPO_CLIENTE)));
		usuario.setDsEmail(vo.getDsEmail());
		
		List<Usuario> lista = this.pesquisar(usuario, 0);
		
		if(lista != null
				&& lista.size() > 0)
		{
			throw new Exception("Já existe um usuário cadastrado com este e-mail.");
		}
		
		sessao.saveOrUpdate(vo);
		
		String msgEmail = CadastroEmail.getEmailCadastro(vo.getDsNome());
		this.enviarEmailCadastro(vo, msgEmail);
		
		return vo;
	}
	
	//APP
	public Usuario atualizarViaApp(Usuario vo) throws Exception
	{
		Session sessao = HibernateUtil.getSession();
		sessao.setFlushMode(FlushMode.COMMIT);
		Transaction tx = sessao.beginTransaction();
		try
		{
			vo = atualizarViaApp(sessao, vo);
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
	
	public Usuario atualizarViaApp(Session sessao, Usuario vo) throws Exception
	{	
		//VERIFICAR EMAIL
		Usuario usuario = new Usuario();
		usuario.setIdGrupo(new BigInteger(Integer.toString(GrupoService.GRUPO_CLIENTE)));
		usuario.setDsEmail(vo.getDsEmail());
		usuario.setFiltroMap(new HashMap<String, Object>());
		usuario.getFiltroMap().put("idUsuarioNotEq", vo.getIdUsuario());
		
		List<Usuario> lista = this.pesquisar(usuario, 0);
		
		if(lista != null
				&& lista.size() > 0)
		{
			throw new Exception("Já existe um usuário cadastrado com este e-mail.");
		}
		
		//ATUALIZAR
		Usuario objUpdate = new Usuario();
		objUpdate.setIdUsuario(vo.getIdUsuario());
		objUpdate = this.get(sessao, objUpdate, 0);
		
		objUpdate.setDsNome(vo.getDsNome());
		objUpdate.setDsEmail(vo.getDsEmail());
		objUpdate.setNmTelefone(vo.getNmTelefone());
		
		sessao.merge(objUpdate);		
		return objUpdate;
	}
	
	public Usuario alterarSenhaViaApp(Usuario vo, String senhaAtual, String novaSenha) throws Exception
	{
		Session sessao = HibernateUtil.getSession();
		sessao.setFlushMode(FlushMode.COMMIT);
		Transaction tx = sessao.beginTransaction();
		try
		{
			vo = alterarSenhaViaApp(sessao, vo, senhaAtual, novaSenha);
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
	
	public Usuario alterarSenhaViaApp(Session sessao, Usuario vo, String senhaAtual, String novaSenha) throws Exception
	{
		Usuario usuario = new Usuario();
		usuario.setIdUsuario(vo.getIdUsuario());		
		usuario.setDsSenha(CriptoMD5.stringHexa(senhaAtual.toUpperCase()));
		
		usuario = this.get(sessao, usuario, 0);
		
		if(usuario != null)
		{
			usuario.setDsSenha(CriptoMD5.stringHexa(novaSenha.toUpperCase()));
			usuario.setIdUsuarioAlt(vo.getIdUsuarioAlt());
			usuario.setDtAlteracao(vo.getDtAlteracao());
			
			sessao.merge(usuario);
		}
		else
		{
			throw new Exception("O campo Senha Atual está incorreto.");
		}
		
		return usuario;
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

	@Override
	protected Criteria montaCriteria(Session sessao, int join)
	{
		Criteria criteria = sessao.createCriteria(Usuario.class);
		
		if ((join & JOIN_GRUPO) != 0)
	    {
			criteria.createAlias("grupo", "grupo");			
	    }
		
		if ((join & JOIN_USUARIO_CAD) != 0)
	    {
	         criteria.createAlias("usuarioCad", "usuarioCad");
	    }
		
		if ((join & JOIN_USUARIO_ALT) != 0)
	    {
	         criteria.createAlias("usuarioAlt", "usuarioAlt", JoinType.LEFT_OUTER_JOIN);
	    }
		
		if ((join & JOIN_ESTADO) != 0)
	    {
	         criteria.createAlias("estado", "estado", JoinType.LEFT_OUTER_JOIN);
	    }
		
		if ((join & JOIN_ESTABELECIMENTO) != 0)
	    {
	         criteria.createAlias("estabelecimento", "estabelecimento", JoinType.LEFT_OUTER_JOIN);
	    }
		
		return criteria;
	}
	
	@Override
	protected void setarOrdenacao(Criteria criteria, Usuario vo, int join)
	{
		criteria.addOrder(Order.asc("dsNome"));
	}
}
