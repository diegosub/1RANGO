package br.com.rango.web.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import br.com.rango.ngc.entity.Estabelecimento;
import br.com.rango.ngc.entity.Estado;
import br.com.rango.ngc.entity.Grupo;
import br.com.rango.ngc.entity.Usuario;
import br.com.rango.ngc.service.EstabelecimentoService;
import br.com.rango.ngc.service.EstadoService;
import br.com.rango.ngc.service.GrupoService;
import br.com.rango.ngc.service.UsuarioService;
import br.com.rango.ngc.util.jsf.AbstractBean;
import br.com.rango.ngc.util.jsf.JSFUtil;

@RequestScoped
@ManagedBean
public class UsuarioBean extends AbstractBean<Usuario, UsuarioService>
{
	private List<Estado> listaEstado;
	private List<Grupo> listaGrupo;
	private List<Estabelecimento> listaEstabelecimento;

	private String siglaEstado;
	private String activeTab;
	private String login;

	private JSFUtil util = new JSFUtil();

	public UsuarioBean()
	{
		super(UsuarioService.getInstancia());
		this.ACTION_SEARCH = "usuario";
		this.pageTitle = "Usuario";
	}

	@Override
	protected void setValoresDefault() throws Exception
	{
		this.getSearchObject().setFgAtivo("T");
	}

	@Override
	protected void setDefaultInserir() throws Exception
	{
		this.setActiveTab("Dados Pessoais");
		this.getEntity().setFgSexo("M");
		this.setSiglaEstado(null);
	}

	@Override
	protected void setListaPesquisa() throws Exception
	{
		// LISTA DE ESTADOS
		List<Estado> resultEst = EstadoService.getInstancia().pesquisar(new Estado(), 0);

		Estado est = new Estado();
		est.setDsEstado("-- Escolha o Estado --");

		List<Estado> listaEstado = new ArrayList<Estado>();
		listaEstado.add(est);
		listaEstado.addAll(resultEst);

		this.setListaEstado(listaEstado);

		// LISTA DE GRUPOS
		Grupo grupo = new Grupo();
		grupo.setFgAtivo("S");
		List<Grupo> resultGrp = GrupoService.getInstancia().pesquisar(grupo, 0);

		Grupo grp = new Grupo();
		grp.setDsGrupo("-- Escolha o Grupo --");

		List<Grupo> listaGrupo = new ArrayList<Grupo>();
		listaGrupo.add(grp);
		listaGrupo.addAll(resultGrp);

		this.setListaGrupo(listaGrupo);
		
		// LISTA DE ESTABELECIMENTOS
		Estabelecimento estabelecimento = new Estabelecimento();
		estabelecimento.setFgAtivo("S");
		List<Estabelecimento> resultEstabelecimento = EstabelecimentoService.getInstancia().pesquisar(estabelecimento, 0);

		Estabelecimento estab = new Estabelecimento();
		estab.setDsEstabelecimento("-- Escolha o Estabelecimento --");

		List<Estabelecimento> listaEstabelecimento = new ArrayList<Estabelecimento>();
		listaEstabelecimento.add(estab);
		listaEstabelecimento.addAll(resultEstabelecimento);

		this.setListaEstabelecimento(listaEstabelecimento);
	}
	
	@Override
	public void preparaVisualizar()
	{
		try
		{
			if (this.getEntityAlt().getIdEstado() != null
					&& this.getEntityAlt().getIdEstado().intValue() > 0)  
			{
				Estado estado = new Estado();
				estado.setIdEstado(this.getEntityAlt().getIdEstado());				
				estado = EstadoService.getInstancia().get(estado, 0);
				this.setSiglaEstado(estado.getDsSigla());
			}
			
			super.preparaVisualizar();
			this.setActiveTab("Dados Pessoais");
		}
		catch (Exception e)
		{
			FacesMessage message = new FacesMessage(e.getMessage());
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}
	
	@Override
	public void preparaAlterar()
	{
		try
		{
			if (this.getEntityAlt().getIdEstado() != null
					&& this.getEntityAlt().getIdEstado().intValue() > 0)  
			{
				Estado estado = new Estado();
				estado.setIdEstado(this.getEntityAlt().getIdEstado());				
				estado = EstadoService.getInstancia().get(estado, 0);
				this.setSiglaEstado(estado.getDsSigla());
			}
			
			super.preparaAlterar();
			this.setActiveTab("Dados Pessoais");
		}
		catch (Exception e)
		{
			FacesMessage message = new FacesMessage(e.getMessage());
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}

	@Override
	protected void completarPesquisar() throws Exception
	{
		this.getSearchObject().setFiltroMap(new HashMap<String, Object>());
		this.getSearchObject().getFiltroMap().put("likeLogin", this.getLogin());

		if (this.getSearchObject().getFgAtivo() != null && this.getSearchObject().getFgAtivo().equals("T"))
		{
			this.getSearchObject().setFgAtivo(null);
		}
	}

	@Override
	protected void completarInserir() throws Exception
	{
		this.getEntity().setDtCadastro(new Date());
		this.getEntity().setIdUsuarioCad(util.getUsuarioLogado().getIdUsuario());
		this.getEntity().setFgAtivo("S");

		if (this.getSiglaEstado() != null && !this.getSiglaEstado().equalsIgnoreCase(""))
		{
			Estado estado = new Estado();
			estado.setDsSigla(this.getSiglaEstado());
			estado = EstadoService.getInstancia().get(estado, 0);

			this.getEntity().setIdEstado(estado.getIdEstado());
		}
	}

	@Override
	protected void completarAlterar() throws Exception
	{
		this.validarInserir();
		this.getEntity().setDtAlteracao(new Date());
		this.getEntity().setIdUsuarioAlt(util.getUsuarioLogado().getIdUsuario());
	}

	public void atualizarEstabelecimento()
	{
		this.getEntity().setIdEstabelecimento(null);
	}
	
	@Override
	public void clean(ActionEvent event)
	{
		super.clean(event);
		this.getSearchObject().setFgAtivo("T");
	}

	public void inativar()
	{
		try
		{
			if (this.getEntity() != null)
			{
				UsuarioService.getInstancia().inativar(this.getEntity());

				FacesMessage message = new FacesMessage("Registro inativado com sucesso!");
				message.setSeverity(FacesMessage.SEVERITY_INFO);
				FacesContext.getCurrentInstance().addMessage(null, message);
			}
		}
		catch (Exception e)
		{
			FacesMessage message = new FacesMessage(e.getMessage());
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}

	public void ativar()
	{
		try
		{
			if (this.getEntity() != null)
			{
				UsuarioService.getInstancia().ativar(this.getEntity());

				FacesMessage message = new FacesMessage("Registro ativado com sucesso!");
				message.setSeverity(FacesMessage.SEVERITY_INFO);
				FacesContext.getCurrentInstance().addMessage(null, message);
			}
		}
		catch (Exception e)
		{
			FacesMessage message = new FacesMessage(e.getMessage());
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}

	@Override
	protected void validarInserir() throws Exception
	{
		if (this.getEntity().getDsNome() == null || this.getEntity().getDsNome().trim().equals(""))
		{
			throw new Exception("O campo Nome é obrigatório.");
		}

		if (this.getEntity().getDsEmail() == null || this.getEntity().getDsEmail().trim().equals(""))
		{
			throw new Exception("O campo E-mail é obrigatório.");
		}

		if (this.getEntity().getDsLogin() == null || this.getEntity().getDsLogin().trim().equals(""))
		{
			throw new Exception("O campo Login é obrigatório.");
		}

		if (this.getEntity().getDsCpf() == null || this.getEntity().getDsCpf().trim().equals(""))
		{
			throw new Exception("O campo Cpf é obrigatório.");
		}

		if (this.getEntity().getNmTelefone() == null || this.getEntity().getNmTelefone().trim().equals(""))
		{
			throw new Exception("O campo Telefone é obrigatório.");
		}

		if (this.getEntity().getDtNascimento() == null || this.getEntity().getDtNascimento().toString().equals(""))
		{
			throw new Exception("O campo Nascimento é obrigatório.");
		}

		if (this.getEntity().getDsCep() == null || this.getEntity().getDsCep().trim().equals(""))
		{
			throw new Exception("O campo Cep é obrigatório.");
		}

		if (this.getEntity().getDsLogradouro() == null || this.getEntity().getDsLogradouro().trim().equals(""))
		{
			throw new Exception("O campo Logradouro é obrigatório.");
		}

		if (this.getEntity().getNmEndereco() == null || this.getEntity().getNmEndereco().longValue() <= 0)
		{
			throw new Exception("O campo Número da Residência é obrigatório.");
		}

		if (this.getEntity().getDsBairro() == null || this.getEntity().getDsBairro().trim().equals(""))
		{
			throw new Exception("O campo Bairro é obrigatório.");
		}

		if (this.getEntity().getDsCidade() == null || this.getEntity().getDsCidade().trim().equals(""))
		{
			throw new Exception("O campo Cidade é obrigatório.");
		}

		if (this.getSiglaEstado() == null || this.getSiglaEstado().trim().equals(""))
		{
			throw new Exception("O campo Estado é obrigatório.");
		}

		if (this.getEntity().getIdGrupo() == null || this.getEntity().getIdGrupo().longValue() <= 0)
		{
			throw new Exception("O campo Grupo é obrigatório.");
		}
		
		if(this.getEntity().getIdGrupo() != null
				&& this.getEntity().getIdGrupo().intValue() == GrupoService.GRUPO_ESTABELECIMENTO
				&& (this.getEntity().getIdEstabelecimento() == null || this.getEntity().getIdEstabelecimento().intValue() <= 0))
		{
			throw new Exception("O campo Estabelecimento é obrigatório.");
		}
	}

	public List<Estado> getListaEstado()
	{
		return listaEstado;
	}

	public void setListaEstado(List<Estado> listaEstado)
	{
		this.listaEstado = listaEstado;
	}

	public String getSiglaEstado() {
		return siglaEstado;
	}

	public void setSiglaEstado(String siglaEstado)
	{
		this.siglaEstado = siglaEstado;
	}

	public List<Grupo> getListaGrupo()
	{
		return listaGrupo;
	}

	public void setListaGrupo(List<Grupo> listaGrupo)
	{
		this.listaGrupo = listaGrupo;
	}

	public String getActiveTab()
	{
		return activeTab;
	}

	public void setActiveTab(String activeTab)
	{
		this.activeTab = activeTab;
	}

	public String getLogin()
	{
		return login;
	}

	public void setLogin(String login)
	{
		this.login = login;
	}

	public List<Estabelecimento> getListaEstabelecimento() {
		return listaEstabelecimento;
	}

	public void setListaEstabelecimento(List<Estabelecimento> listaEstabelecimento) {
		this.listaEstabelecimento = listaEstabelecimento;
	}
}
