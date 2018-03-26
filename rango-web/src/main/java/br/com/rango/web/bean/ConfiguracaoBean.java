package br.com.rango.web.bean;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.hibernate.Criteria;

import br.com.rango.ngc.entity.Bairro;
import br.com.rango.ngc.entity.Cidade;
import br.com.rango.ngc.entity.Dominio;
import br.com.rango.ngc.entity.Estabelecimento;
import br.com.rango.ngc.entity.EstabelecimentoBairro;
import br.com.rango.ngc.entity.EstabelecimentoBandeira;
import br.com.rango.ngc.entity.EstabelecimentoHorario;
import br.com.rango.ngc.entity.EstabelecimentoImagem;
import br.com.rango.ngc.entity.Estado;
import br.com.rango.ngc.service.BairroService;
import br.com.rango.ngc.service.CidadeService;
import br.com.rango.ngc.service.DominioService;
import br.com.rango.ngc.service.EstabelecimentoHorarioService;
import br.com.rango.ngc.service.EstabelecimentoImagemService;
import br.com.rango.ngc.service.EstabelecimentoService;
import br.com.rango.ngc.service.EstadoService;
import br.com.rango.ngc.util.jsf.AbstractBean;
import br.com.rango.ngc.util.jsf.JSFUtil;


@RequestScoped
@ManagedBean
public class ConfiguracaoBean extends AbstractBean<Estabelecimento, EstabelecimentoService>
{
	private boolean fgDelivery;
	private boolean fgRetiradaLocal;
	private boolean fgDinheiroDelivery;
	private boolean fgCartaoDelivery;
	private boolean fgDinheiroRetirada;
	private boolean fgCartaoRetirada;	
	
	private String activeTab;
	private String siglaEstado;
	
	private List<EstabelecimentoHorario> listaHorarioDefault;
	private List<Dominio> listaBandeira;
	private List<Estado> listaEstado;
	private List<Cidade> listaCidade;
	private List<Cidade> listaCidadeBairro;
	private BigInteger[] bandeirasDelivery;
	private BigInteger[] bandeirasRetirada;
	
	private BigInteger idEstado;
	private Cidade cidade;
	private BigInteger idCidade;
	
	private String caminho;
	private final String CAMINHO_SEM_FOTO = "../images/sem-foto.gif";
	
	private Part file;
	
	private JSFUtil util = new JSFUtil();
	
	
	public ConfiguracaoBean()
	{
		super(EstabelecimentoService.getInstancia());
		this.ACTION_SEARCH = "configuracao";
		this.pageTitle = "Configurações";
	}
	
	public String preparaConfiguracao()
	{
		try
		{
			this.setActiveTab("Geral");
			this.setCurrentState(STATE_EDIT);
			this.popularListaBandeira();
			this.popularListaEstado();
			this.iniciarCidade();
			this.setIdEstado(null);
			
			this.setarEstabelecimento();
			this.setCaminho(this.CAMINHO_SEM_FOTO);			
		}
		catch (Exception e)
		{
			FacesMessage message = new FacesMessage(e.getMessage());
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			if(e.getMessage() == null)
				FacesContext.getCurrentInstance().addMessage("", message);
			else
				FacesContext.getCurrentInstance().addMessage(null, message);
		}
		
		return ACTION_SEARCH;
	}
	
	private void setarEstabelecimento() throws Exception
	{
		Estabelecimento estabelecimento = new Estabelecimento();
		estabelecimento.setIdEstabelecimento(util.getUsuarioLogado().getIdEstabelecimento());
		estabelecimento.setFiltroMap(new HashMap<String, Object>());
		estabelecimento = EstabelecimentoService.getInstancia().get(estabelecimento, EstabelecimentoService.JOIN_ESTABELECIMENTO_BAIRRO
																				   | EstabelecimentoService.JOIN_ESTABELECIMENTO_BANDEIRA
																				   | EstabelecimentoService.JOIN_ESTABELECIMENTO_HORARIO
																				   | EstabelecimentoService.JOIN_ESTABELECIMENTO_BAIRRO_BAIRRO
																				   | EstabelecimentoService.JOIN_ESTABELECIMENTO_BAIRRO_BAIRRO_CIDADE
																				   | EstabelecimentoService.JOIN_ESTABELECIMENTO_BAIRRO_BAIRRO_CIDADE_ESTADO, Criteria.DISTINCT_ROOT_ENTITY);
		
		this.setEntity(estabelecimento);
		
		this.setFgCartaoDelivery((this.getEntity().getFgCartaoDelivery() != null && this.getEntity().getFgCartaoDelivery().equals("S")) ? true : false);
		this.setFgDinheiroDelivery((this.getEntity().getFgDinheiroDelivery() != null && this.getEntity().getFgDinheiroDelivery().equals("S")) ? true : false);
		
		this.setFgCartaoRetirada((this.getEntity().getFgCartaoRetirada() != null && this.getEntity().getFgCartaoRetirada().equals("S")) ? true : false);
		this.setFgDinheiroRetirada((this.getEntity().getFgDinheiroRetirada() != null && this.getEntity().getFgDinheiroRetirada().equals("S")) ? true : false);
		
		this.setFgDelivery(this.getEntity().getFgDelivery() != null && this.getEntity().getFgDelivery().equals("S") ? true : false);
		this.setFgRetiradaLocal(this.getEntity().getFgRetiradaLocal() != null && this.getEntity().getFgRetiradaLocal().equals("S") ? true : false);
		
		this.getEntity().setStrVlTaxaEntregaInicio(this.getEntity().getVlTaxaEntregaInicio() != null ? new DecimalFormat("#,##0.00").format(this.getEntity().getVlTaxaEntregaInicio()) : "");
		this.getEntity().setStrVlTaxaEntregaFim(this.getEntity().getVlTaxaEntregaFim() != null ? new DecimalFormat("#,##0.00").format(this.getEntity().getVlTaxaEntregaFim()) : "");
		
		this.getEntity().setStrVlMinimo(this.getEntity().getVlMinimo() != null ? new DecimalFormat("#,##0.00").format(this.getEntity().getVlMinimo()) : "");
		
		if (this.getEntity().getIdEstado() != null
				&& this.getEntity().getIdEstado().intValue() > 0)  
		{
			Estado estado = new Estado();
			estado.setIdEstado(this.getEntity().getIdEstado());				
			estado = EstadoService.getInstancia().get(estado, 0);
			this.setSiglaEstado(estado.getDsSigla());
		}
		
		//SEPARANDO LISTAS DE BANDEIRAS (DELIVERY E RETIRADA)
		List<EstabelecimentoBandeira> listaDelivery = null;
		List<EstabelecimentoBandeira> listaRetirada = null;
		
		if(this.getEntity().getSetEstabelecimentoBandeira() != null
				&& this.getEntity().getSetEstabelecimentoBandeira().size() > 0)
		{
			this.getEntity().setListaEstabelecimentoBandeira(new ArrayList<EstabelecimentoBandeira>(this.getEntity().getSetEstabelecimentoBandeira()));
			listaDelivery = new ArrayList<EstabelecimentoBandeira>();
			listaRetirada = new ArrayList<EstabelecimentoBandeira>();
			
			for (int i = 0; i < this.getEntity().getListaEstabelecimentoBandeira().size(); i++)
			{
				if(this.getEntity().getListaEstabelecimentoBandeira().get(i).getIdBandeiraDelivery() != null)
				{
					listaDelivery.add(this.getEntity().getListaEstabelecimentoBandeira().get(i));
				}
				
				if(this.getEntity().getListaEstabelecimentoBandeira().get(i).getIdBandeiraRetirada() != null)
				{
					listaRetirada.add(this.getEntity().getListaEstabelecimentoBandeira().get(i));
				}
			}
		}
		
		//SETANDO RESPECTIVAS LISTAS DE BANDEIRAS
		if(listaDelivery != null
				&& listaDelivery.size() > 0)
		{
			bandeirasDelivery = new BigInteger[listaDelivery.size()];
			
			for (int i = 0; i < listaDelivery.size(); i++)
			{
				bandeirasDelivery[i] = listaDelivery.get(i).getIdBandeiraDelivery();
			}
		}
		
		if(listaRetirada != null
				&& listaRetirada.size() > 0)
		{
			bandeirasRetirada = new BigInteger[listaRetirada.size()];
			
			for (int i = 0; i < listaRetirada.size(); i++)
			{
				bandeirasRetirada[i] = listaRetirada.get(i).getIdBandeiraRetirada();
			}
		}
		
		//HORARIO
		this.popularHorarioDefault();		
		
		//BAIRRO
		this.popularCidadeBairro();
	}
	
	private void completarSalvar() throws Exception
	{
		this.getEntity().setIdEstabelecimento(util.getUsuarioLogado().getIdEstabelecimento());
		this.getEntity().setIdUsuarioAlt(util.getUsuarioLogado().getIdUsuario());
		this.getEntity().setDtAlteracao(new Date());
		this.getEntity().setListaCidade(this.getListaCidadeBairro());
		this.getEntity().setListaEstabelecimentoHorario(this.getListaHorarioDefault());
		
		if(this.getEntity().getStrVlTaxaEntregaInicio() != null
				&& !this.getEntity().getStrVlTaxaEntregaInicio().trim().equals("")
				&& this.getEntity().getStrVlTaxaEntregaFim() != null
				&& !this.getEntity().getStrVlTaxaEntregaFim().trim().equals(""))
		{
			this.getEntity().setVlTaxaEntregaInicio(new Double(this.getEntity().getStrVlTaxaEntregaInicio().toString().replace(".", "").replace(",", ".")));
			this.getEntity().setVlTaxaEntregaFim(new Double(this.getEntity().getStrVlTaxaEntregaFim().toString().replace(".", "").replace(",", ".")));
		}
		else
		{
			this.getEntity().setVlTaxaEntregaInicio(null);
			this.getEntity().setVlTaxaEntregaFim(null);
		}
		
		this.getEntity().setVlMinimo(new Double(this.getEntity().getStrVlMinimo().toString().replace(".", "").replace(",", ".")));
		
		this.getEntity().setFgDelivery(this.isFgDelivery() ? "S" : "N");
		this.getEntity().setFgRetiradaLocal(this.isFgRetiradaLocal() ? "S" : "N");
		this.getEntity().setFgDinheiroDelivery(this.isFgDinheiroDelivery() ? "S" : "N");
		this.getEntity().setFgCartaoDelivery(this.isFgCartaoDelivery() ? "S" : "N");
		this.getEntity().setFgDinheiroRetirada(this.isFgDinheiroRetirada() ? "S" : "N");
		this.getEntity().setFgCartaoRetirada(this.isFgCartaoRetirada() ? "S" : "N");
		
		if (this.getSiglaEstado() != null && !this.getSiglaEstado().equalsIgnoreCase(""))
		{
			Estado estado = new Estado();
			estado.setDsSigla(this.getSiglaEstado());
			estado = EstadoService.getInstancia().get(estado, 0);

			this.getEntity().setIdEstado(estado.getIdEstado());
		}
		
		List<EstabelecimentoBandeira> lista = new ArrayList<EstabelecimentoBandeira>();
		
		if(bandeirasDelivery != null
				&& bandeirasDelivery.length > 0)
		{
			for (BigInteger bandeira : bandeirasDelivery)
			{
				EstabelecimentoBandeira estabelecimentoBandeira = new EstabelecimentoBandeira();
				estabelecimentoBandeira.setIdEstabelecimento(this.getEntity().getIdEstabelecimento());
				estabelecimentoBandeira.setIdBandeiraDelivery(bandeira);
				lista.add(estabelecimentoBandeira);
			}
		}
		
		if(bandeirasRetirada != null
				&& bandeirasRetirada.length > 0)
		{
			for (BigInteger bandeira : bandeirasRetirada)
			{
				EstabelecimentoBandeira estabelecimentoBandeira = new EstabelecimentoBandeira();
				estabelecimentoBandeira.setIdEstabelecimento(this.getEntity().getIdEstabelecimento());
				estabelecimentoBandeira.setIdBandeiraRetirada(bandeira);
				lista.add(estabelecimentoBandeira);
			}
		}
		
		this.getEntity().setListaEstabelecimentoBandeira(lista);
	}
	
	public void salvar()
	{
		try
		{
			this.validarInserir();
			this.completarSalvar();
			EstabelecimentoService.getInstancia().salvar(this.getEntity());
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Configuração salva com sucesso.", null));
			this.preparaConfiguracao(); 
		}
		catch (Exception e)
		{
			FacesMessage message = new FacesMessage(e.getMessage());
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}
	
	public void selecionarDia()
	{
		if(this.getListaHorarioDefault() != null)
		{
			for (EstabelecimentoHorario horario : this.getListaHorarioDefault())
			{
				if(!horario.getFgAtivo().booleanValue())
				{
					horario.setHrInicio(null);
					horario.setHrFim(null);
				}
			}
		}
	}
	
	private void iniciarCidade() throws Exception
	{
		Cidade cIni = new Cidade();
		cIni.setDsCidade("-- Selecione uma Cidade --");
		
		List<Cidade> lista = new ArrayList<Cidade>();
		lista.add(cIni);
		
		this.setListaCidade(lista);
	}
	
	public void popularCidade() throws Exception
	{
		List<Cidade> listaAll = new ArrayList<Cidade>();
		
		Cidade cIni = new Cidade();
		cIni.setDsCidade("-- Selecione uma Cidade --");
		listaAll.add(cIni);
		
		if(this.getIdEstado() != null
				&& this.getIdEstado().intValue() > 0)
		{
			Cidade cidade = new Cidade();
			cidade.setIdEstado(this.getIdEstado());
			cidade.setFgAtivo("S");
			
			List<Cidade> lista = CidadeService.getInstancia().pesquisar(cidade, 0);
			listaAll.addAll(lista);
		}		

		this.setListaCidade(listaAll);
		this.setIdCidade(null);
	}
	
	private void popularListaBandeira() throws Exception
	{
		//LISTA BANDEIRAS
		Dominio dominio = new Dominio();
		dominio.setNmCampo("BANDEIRA_CARTAO");
		List<Dominio> listaBandeira = DominioService.getInstancia().pesquisar(dominio, 0);
		this.setListaBandeira(listaBandeira);
	}
	
	private void popularListaEstado() throws Exception
	{
		// LISTA DE ESTADOS
		List<Estado> resultEst = EstadoService.getInstancia().pesquisar(new Estado(), 0);

		Estado est = new Estado();
		est.setDsEstado("-- Escolha o Estado --");

		List<Estado> listaEstado = new ArrayList<Estado>();
		listaEstado.add(est);
		listaEstado.addAll(resultEst);

		this.setListaEstado(listaEstado);
	}
	
	private void popularHorarioDefault() throws Exception
	{
		//
		EstabelecimentoHorario estabelecimentoHorario = new EstabelecimentoHorario();
		estabelecimentoHorario.setIdEstabelecimento(util.getUsuarioLogado().getIdEstabelecimento());
		List<EstabelecimentoHorario> lista = EstabelecimentoHorarioService.getInstancia().pesquisar(estabelecimentoHorario, 0);
		
		if(lista != null
				&& lista.size() > 0)
		{
			this.setListaHorarioDefault(lista);
		}
		else
		{		
			Dominio dominio = new Dominio();
			dominio.setNmCampo("DIA_SEMANA");
			List<Dominio> listaSemana = DominioService.getInstancia().pesquisar(dominio, 0);
			
			for (Dominio obj : listaSemana)
			{
				EstabelecimentoHorario horario = new EstabelecimentoHorario();
				horario.setDsHorario(obj.getDsDominio());
				horario.setNmHorario(new BigInteger(obj.getVlDominio()));
				horario.setFgAtivo(false);
				
				lista.add(horario);
			}
			 
			this.setListaHorarioDefault(lista);
		}
	}
	
	private void popularCidadeBairro() throws Exception
	{
		List<Cidade> lista = new ArrayList<Cidade>();
		
		
		if(this.getEntity().getSetEstabelecimentoBairro() != null
				&& this.getEntity().getSetEstabelecimentoBairro().size() > 0)
		{	
			this.getEntity().setListaEstabelecimentoBairro(new ArrayList<EstabelecimentoBairro>(this.getEntity().getSetEstabelecimentoBairro()));
			
			for (EstabelecimentoBairro obj : this.getEntity().getListaEstabelecimentoBairro())
			{
				if(obj.getBairro().getFgAtivo().trim().equals("S")
						&& obj.getBairro().getCidade().getFgAtivo().trim().equals("S"))
				{
					//MONTANDO A LISTA DE CIDADES
					if(lista != null
							&& lista.size() > 0)
					{
						boolean flag = false;
						
						for (Cidade cidade : lista)
						{
							if(cidade.getIdCidade().intValue() == obj.getBairro().getCidade().getIdCidade().intValue())
							{
								flag = true;
							}
						}
						
						if(!flag)
						{
							Cidade cidade = new Cidade();
							JSFUtil.copiarPropriedades(obj.getBairro().getCidade(), cidade);
							cidade.setListaBairro(this.listarBairros(cidade.getIdCidade()));
							lista.add(cidade);
						}
					}
					else
					{
						Cidade cidade = new Cidade();
						JSFUtil.copiarPropriedades(obj.getBairro().getCidade(), cidade);
						cidade.setListaBairro(this.listarBairros(cidade.getIdCidade()));
						
						for (Bairro bairro : cidade.getListaBairro())
						{
							if(bairro.getIdBairro().intValue() == obj.getIdBairro().intValue())
							{
								bairro.setFgSelecionado(true);
							}
						}
						
						lista.add(cidade);
					}
				}
				
				if(lista != null
						&& lista.size() > 0)
				{
					for (Cidade cidade : lista)
					{
						if(cidade.getListaBairro() != null
								&& cidade.getListaBairro().size() > 0)
						{
							for (Bairro bairro : cidade.getListaBairro())
							{
								if(bairro.getIdBairro().intValue() == obj.getIdBairro().intValue())
								{
									bairro.setFgSelecionado(true);
									bairro.setStrValorTaxa(new DecimalFormat("#,##0.00").format(obj.getVlTaxa()));
								}
							}
						}
					}
				}
			}
		}
		
		this.setListaCidadeBairro(lista);
	}

	private List<Bairro> listarBairros(BigInteger idCidade) throws Exception
	{
		Bairro bairro = new Bairro();
		bairro.setIdCidade(idCidade);
		bairro.setFgAtivo("S");
		
		List<Bairro> lista = BairroService.getInstancia().pesquisar(bairro, 0);
		
		return lista;
	}
	
	public void adicionarCidade()
	{
		try
		{
			Cidade cidade = new Cidade();		
			cidade.setIdCidade(this.getIdCidade());
			cidade.setFiltroMap(new HashMap<String, Object>());
			cidade.getFiltroMap().put("fgAtivoBairro", "S");
			cidade.setFgAtivo("S");
			cidade = CidadeService.getInstancia().get(cidade, CidadeService.JOIN_ESTADO
															| CidadeService.JOIN_BAIRRO, Criteria.DISTINCT_ROOT_ENTITY);
			
			if(cidade == null
					|| (cidade != null
						&& (cidade.getListaBairro() == null
							|| cidade.getListaBairro().size() <= 0)))
			{
				throw new Exception("Não existe bairro cadastrado para esta cidade.");
			}
			else
			{
				for (Cidade obj : this.getListaCidadeBairro())
				{
					if(obj.getIdCidade().intValue() == this.getIdCidade().intValue())
					{
						throw new Exception("Esta cidade já foi adicionada.");
					}
				}
				
				this.getListaCidadeBairro().add(0, cidade);
			}
		}
		catch (Exception e) 
		{
			FacesMessage message = new FacesMessage(e.getMessage());
	        message.setSeverity(FacesMessage.SEVERITY_ERROR);
	        FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}
	
	public void limparValorDelivery()
	{
		this.getEntity().setVlDuracaoDeliveryInicio(null);
		this.getEntity().setVlDuracaoDeliveryFim(null);
		
		this.getEntity().setStrVlTaxaEntregaInicio(null);
		this.getEntity().setStrVlTaxaEntregaFim(null);
		
		this.setFgCartaoDelivery(false);
		this.setFgDinheiroDelivery(false);
		this.limparBandeiraDelivery();
	}
	
	public void limparValorRetirada()
	{
		this.getEntity().setVlDuracaoRetiradaInicio(null);
		this.getEntity().setVlDuracaoRetiradaFim(null);
		
		this.setFgCartaoRetirada(false);
		this.setFgDinheiroRetirada(false);
		this.limparBandeiraRetirada();
	}
	
	public void limparBandeiraDelivery()
	{
		this.bandeirasDelivery = null;
	}
	
	public void limparBandeiraRetirada()
	{
		this.bandeirasRetirada = null;
	}
	
	@Override
	protected void validarInserir() throws Exception
	{
		if(this.getEntity() == null
				|| this.getEntity().getDsFantasia() == null
				|| this.getEntity().getDsFantasia().trim().equals(""))
		{
			throw new Exception("O campo Estabelecimento é obrigatório.");
		}
		
		if(!this.isFgDelivery()
				&& !this.isFgRetiradaLocal())
		{
			throw new Exception("O campo Tipo de Entrega é obrigatório.");
		}
		
		if(this.isFgDelivery()
				&& (this.getEntity().getVlDuracaoDeliveryInicio() == null
				   || this.getEntity().getVlDuracaoDeliveryInicio().intValue() <= 0
				   || this.getEntity().getVlDuracaoDeliveryFim() == null
				   || this.getEntity().getVlDuracaoDeliveryFim().intValue() <= 0))
		{
			throw new Exception("O campo Tempo Médio Delivery é obrigatório quando o Tipo de Entrega for Delivery.");
		}
		
		if(this.isFgDelivery()
				&& this.getEntity().getVlDuracaoDeliveryInicio().intValue() > this.getEntity().getVlDuracaoDeliveryFim().intValue())
		{
			throw new Exception("O campo Tempo Médio Delivery Início deve ser maior ou igual ao campo Tempo Médio Delivery Fim.");
		}			
		
		if(this.isFgRetiradaLocal()
				&& (this.getEntity().getVlDuracaoRetiradaInicio() == null
				   || this.getEntity().getVlDuracaoRetiradaInicio().intValue() <= 0
				   || this.getEntity().getVlDuracaoRetiradaFim() == null
				   || this.getEntity().getVlDuracaoRetiradaFim().intValue() <= 0))
		{
			throw new Exception("O campo Tempo Médio Retirada é obrigatório quando o Tipo de Entrega for Retirada no Local.");
		}
		
		if(this.isFgRetiradaLocal()
				&& this.getEntity().getVlDuracaoRetiradaInicio().intValue() > this.getEntity().getVlDuracaoRetiradaFim().intValue())
		{
			throw new Exception("O campo Tempo Médio Retirada Início deve ser maior ou igual ao campo Tempo Médio Retirada Fim.");
		}
		
		if(this.isFgDelivery()
				&& (this.getEntity().getStrVlTaxaEntregaInicio() == null
				   || this.getEntity().getStrVlTaxaEntregaInicio().trim().equals("")
				   || this.getEntity().getStrVlTaxaEntregaFim() == null
				   || this.getEntity().getStrVlTaxaEntregaFim().equals("")))
		{
			throw new Exception("O campo Taxa de Entrega é obrigatório quando o Tipo de Entrega for Delivery.");
		}
		
		//TX ENTREGA INI > TX ENTRE
		if(this.getEntity().getStrVlTaxaEntregaInicio() != null
				&& !this.getEntity().getStrVlTaxaEntregaInicio().equals("")
				&& this.getEntity().getStrVlTaxaEntregaFim() != null
				&& !this.getEntity().getStrVlTaxaEntregaFim().equals(""))
		{
			double txEntregaIni = new Double(this.getEntity().getStrVlTaxaEntregaInicio().toString().replace(".", "").replace(",", "."));
			double txEntregaFim = new Double(this.getEntity().getStrVlTaxaEntregaFim().toString().replace(".", "").replace(",", "."));
			
			if(txEntregaFim < txEntregaIni)
			{
				throw new Exception("A campo Taxa de Entrega Início deve ser menor que o campo Taxa de Entrega Fim.");
			}
		}
		
		
		if(this.isFgDelivery()
				&& !this.isFgCartaoDelivery()
				&& !this.isFgDinheiroDelivery())
		{
			throw new Exception("O campo Forma de Pagamento Delivery é obrigatório quando o Tipo de Entrega for Delivery.");
		}
		
		if(this.isFgCartaoDelivery()
				&& (this.getBandeirasDelivery() == null
						|| this.getBandeirasDelivery().length <= 0))
		{
			throw new Exception("O campo Bandeira Delivery é obrigatório quando a Forma de Pagamento Delivery for Cartão.");
		}
		
		if(this.isFgRetiradaLocal()
				&& !this.isFgCartaoRetirada()
				&& !this.isFgDinheiroRetirada())
		{
			throw new Exception("O campo Forma de Pagamento Retirada é obrigatório quando o Tipo de Entrega for Retirada no Local.");
		}
		
		if(this.isFgCartaoRetirada()
				&& (this.getBandeirasRetirada() == null
						|| this.getBandeirasRetirada().length <= 0))
		{
			throw new Exception("O campo Bandeira Retirada é obrigatório quando a Forma de Pagamento Retirada for Cartão.");
		}
			
		
		if(this.getEntity() == null
				|| this.getEntity().getStrVlMinimo() == null
				|| this.getEntity().getStrVlMinimo().trim().equals(""))
		{
			throw new Exception("O campo Valor Mínimo Pedido é obrigatório.");
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
			throw new Exception("O campo Número é obrigatório.");
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
		
		if(this.getListaHorarioDefault() != null
				&& this.getListaHorarioDefault().size() > 0)
		{
			boolean flag = false;
			
			for (EstabelecimentoHorario obj : this.getListaHorarioDefault())
			{
				if(obj.getFgAtivo().booleanValue())
				{
					flag = true;
				}
			}
			
			if(!flag)
			{
				throw new Exception("O estabelecimento deve funcionar pelo menos um dia da semana.");
			}
		}
		
		this.validarHorario();
		
		if(this.isFgDelivery())
		{
			if(this.getListaCidadeBairro() == null
					|| this.getListaCidadeBairro().size() <= 0)
			{
				throw new Exception("O estabelecimento deve atender pelo menos um bairro se o Tipo de Entrega for Delivery.");
			}
			
			boolean flag = false;
			
			for (Cidade cidade : this.getListaCidadeBairro())
			{
				for (Bairro bairro : cidade.getListaBairro())
				{
					if(bairro.isFgSelecionado())
					{
						flag = true;
					}
				}
			}
			
			if(!flag)
			{
				throw new Exception("O estabelecimento deve atender pelo menos um bairro se o Tipo de Entrega for Delivery.");
			}
		}
		
		if(this.getListaCidadeBairro() != null
				&& this.getListaCidadeBairro().size() > 0)
		{
			for (Cidade cidade : this.getListaCidadeBairro())
			{
				if(cidade.getListaBairro() != null
						&& cidade.getListaBairro().size() > 0)
				{
					for (Bairro bairro : cidade.getListaBairro())
					{
						if(bairro.isFgSelecionado())
						{
							if(bairro.getStrValorTaxa() == null || bairro.getStrValorTaxa().equals(""))
							{
								throw new Exception("A taxa de entrega deve ser informada para os bairros selecionados.");
							}
							
							double valor = new Double(bairro.getStrValorTaxa().replace(".", "").replace(",", "."));
							double txEntregaIni = new Double(this.getEntity().getStrVlTaxaEntregaInicio().toString().replace(".", "").replace(",", "."));
							double txEntregaFim = new Double(this.getEntity().getStrVlTaxaEntregaFim().toString().replace(".", "").replace(",", "."));
							
							if(valor < txEntregaIni
									|| valor > txEntregaFim)
							{
								throw new Exception("As taxas para os bairros devem estar no intervalo entre " + this.getEntity().getStrVlTaxaEntregaInicio().toString() + " e " + this.getEntity().getStrVlTaxaEntregaFim().toString());
							}
						}
					}
				}
			}
		}
	}
	
	@SuppressWarnings("resource")
	public String importar()
	{
		try
		{	
			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			this.setFile(request.getPart("file"));
			
			new Scanner(file.getInputStream()).useDelimiter("\\A").next();
			
			this.validarArquivo(file);
            
            EstabelecimentoImagem imagem = new EstabelecimentoImagem();
            imagem.setIdEstabelecimento(this.getEntity().getIdEstabelecimento());
            imagem.setEstabelecimento(this.getEntity());
            imagem.setDsImagem(this.getFileName(file));
            imagem.setDsTipo(file.getContentType());
            imagem.setVlTamanho(file.getSize());
            imagem.setFile(file);
            
            EstabelecimentoImagemService.getInstancia().salvarImagem(imagem);
            this.preparaConfiguracao();
        } 
		catch (Exception e)
		{
        	FacesMessage message = new FacesMessage(e.getMessage());
	        message.setSeverity(FacesMessage.SEVERITY_ERROR);
	        FacesContext.getCurrentInstance().addMessage(null, message);
        }
		
		return ACTION_SEARCH;
	}
	
	private String getFileName(Part part)
	{
		for (String cd : part.getHeader("content-disposition").split(";"))
		{
			if (cd.trim().startsWith("filename"))
			{
				return cd.substring(cd.indexOf('=') + 1).trim()
						.replace("\"", "");
			}
		}
		return null;
	}
	
	public void validarArquivo(Part value) throws Exception
	{
        Part arquivo = (Part) value;

        if (arquivo.getSize() > (2*1024*1024))
        {
            throw new Exception("Arquivo muito grande. O arquivo deve ter o tamanho máximo de 2mb.");
        }

        if (!"image/jpg".equals(arquivo.getContentType())
        		&& !"image/jpeg".equals(arquivo.getContentType())
        		&& !"image/png".equals(arquivo.getContentType()))
        {
            throw new Exception("Tipo de arquivo inválido, O arquivo deve ser dos tipos: .JPG, .JPEG ou .PNG.");
        }
    }
	
	private void validarHorario() throws Exception
	{
		if(this.getListaHorarioDefault() != null)
		{
			for (EstabelecimentoHorario horario : this.getListaHorarioDefault())
			{
				if(horario.getFgAtivo().booleanValue())
				{
					if(horario.getHrInicio() == null || horario.getHrInicio().equals("") 
							|| horario.getHrFim() == null || horario.getHrFim().equals(""))
					{
						throw new Exception("Ao selecionar um dia da semana, os campos Horário Início e Horário Fim devem ser informados.");
					}
					
					//INICIO
					String[] arrayInicio = horario.getHrInicio().split(":");
					
					Integer horaIni = Integer.parseInt(arrayInicio[0]);
					
					if(horaIni > 23)
					{
						throw new Exception("A hora " + horario.getHrInicio() + " é inválida!");
					}
					
					//FIM
					String[] arrayFim = horario.getHrFim().split(":");
					
					Integer horaFim = Integer.parseInt(arrayFim[0]);
					
					if(horaFim > 23)
					{
						throw new Exception("A hora " + horario.getHrFim() + " é inválida!");
					}
					
					if(horaIni == horaFim)
					{
						throw new Exception("O tempo mínimo de intervalo da hora inicial para a hora final é de no mínimo 1 hora (60 minutos).");
					}
				}
			}
		}
	}
	
	@Override
	public String getFullTitle()
	{
		return this.pageTitle;
	}
	
	public void removerCidade()
	{
		this.getListaCidadeBairro().remove(this.getCidade());
	}
	
	public boolean isFgDelivery() {
		return fgDelivery;
	}

	public void setFgDelivery(boolean fgDelivery) {
		this.fgDelivery = fgDelivery;
	}

	public boolean isFgRetiradaLocal() {
		return fgRetiradaLocal;
	}

	public void setFgRetiradaLocal(boolean fgRetiradaLocal) {
		this.fgRetiradaLocal = fgRetiradaLocal;
	}

	public boolean isFgDinheiroDelivery() {
		return fgDinheiroDelivery;
	}

	public void setFgDinheiroDelivery(boolean fgDinheiroDelivery) {
		this.fgDinheiroDelivery = fgDinheiroDelivery;
	}

	public boolean isFgCartaoDelivery() {
		return fgCartaoDelivery;
	}

	public void setFgCartaoDelivery(boolean fgCartaoDelivery) {
		this.fgCartaoDelivery = fgCartaoDelivery;
	}

	public boolean isFgDinheiroRetirada() {
		return fgDinheiroRetirada;
	}

	public void setFgDinheiroRetirada(boolean fgDinheiroRetirada) {
		this.fgDinheiroRetirada = fgDinheiroRetirada;
	}

	public boolean isFgCartaoRetirada() {
		return fgCartaoRetirada;
	}

	public void setFgCartaoRetirada(boolean fgCartaoRetirada) {
		this.fgCartaoRetirada = fgCartaoRetirada;
	}

	public List<Dominio> getListaBandeira() {
		return listaBandeira;
	}

	public void setListaBandeira(List<Dominio> listaBandeira) {
		this.listaBandeira = listaBandeira;
	}

	public BigInteger[] getBandeirasDelivery() {
		return bandeirasDelivery;
	}

	public void setBandeirasDelivery(BigInteger[] bandeirasDelivery) {
		this.bandeirasDelivery = bandeirasDelivery;
	}

	public BigInteger[] getBandeirasRetirada() {
		return bandeirasRetirada;
	}

	public void setBandeirasRetirada(BigInteger[] bandeirasRetirada) {
		this.bandeirasRetirada = bandeirasRetirada;
	}

	public String getActiveTab() {
		return activeTab;
	}

	public void setActiveTab(String activeTab) {
		this.activeTab = activeTab;
	}

	public String getSiglaEstado() {
		return siglaEstado;
	}

	public void setSiglaEstado(String siglaEstado) {
		this.siglaEstado = siglaEstado;
	}

	public List<Estado> getListaEstado() {
		return listaEstado;
	}

	public void setListaEstado(List<Estado> listaEstado) {
		this.listaEstado = listaEstado;
	}

	public List<EstabelecimentoHorario> getListaHorarioDefault() {
		return listaHorarioDefault;
	}

	public void setListaHorarioDefault(List<EstabelecimentoHorario> listaHorarioDefault) {
		this.listaHorarioDefault = listaHorarioDefault;
	}

	public BigInteger getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(BigInteger idEstado) {
		this.idEstado = idEstado;
	}

	public List<Cidade> getListaCidade() {
		return listaCidade;
	}

	public void setListaCidade(List<Cidade> listaCidade) {
		this.listaCidade = listaCidade;
	}

	public List<Cidade> getListaCidadeBairro() {
		return listaCidadeBairro;
	}

	public void setListaCidadeBairro(List<Cidade> listaCidadeBairro) {
		this.listaCidadeBairro = listaCidadeBairro;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public Part getFile() {
		return file;
	}

	public void setFile(Part file) {
		this.file = file;
	}

	public String getCaminho() {
		return caminho;
	}

	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}

	public BigInteger getIdCidade() {
		return idCidade;
	}

	public void setIdCidade(BigInteger idCidade) {
		this.idCidade = idCidade;
	}
}
