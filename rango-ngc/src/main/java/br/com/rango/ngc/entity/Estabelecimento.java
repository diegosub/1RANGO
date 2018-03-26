package br.com.rango.ngc.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Proxy;

/** 
 * @author Carlos Diego
 * @since 20/11/2017
 */

@Entity
@Table(name = "tb_estabelecimento", schema="rng")
@SequenceGenerator(name = "SQ_ESTABELECIMENTO", sequenceName = "SQ_ESTABELECIMENTO", allocationSize = 1)
@Proxy(lazy = true)
public class Estabelecimento implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_ESTABELECIMENTO")
	@Column(name = "id_estabelecimento")
	private BigInteger idEstabelecimento;
	
	@Column(name = "id_cidade")
	private BigInteger idCidade;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cidade", insertable = false, updatable = false)
	private Cidade cidade;
	
	@Column(name = "id_categoria")
	private BigInteger idCategoria;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_categoria", insertable = false, updatable = false)
	private Categoria categoria;
	
	@Column(name = "ds_estabelecimento")
	private String dsEstabelecimento;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_cadastro")
	private Date dtCadastro;
	
	@Column(name = "id_usuario_cad")
	private BigInteger idUsuarioCad;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario_cad", insertable = false, updatable = false)
	private Usuario usuarioCad;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_alteracao")
	private Date dtAlteracao;
	
	@Column(name = "id_usuario_alt")
	private BigInteger idUsuarioAlt;
	
	@Column(name = "fg_ativo")
	private String fgAtivo;
	
	@Column(name = "ds_fantasia")
	private String dsFantasia;
	
	@Column(name = "vl_duracao_delivery_inicio")
	private BigInteger vlDuracaoDeliveryInicio;

	@Column(name = "vl_duracao_delivery_fim")
	private BigInteger vlDuracaoDeliveryFim;
	
	@Column(name = "vl_duracao_retirada_inicio")
	private BigInteger vlDuracaoRetiradaInicio;

	@Column(name = "vl_duracao_retirada_fim")
	private BigInteger vlDuracaoRetiradaFim;
	
	@Column(name = "vl_taxa_entrega_inicio")
	private Double vlTaxaEntregaInicio;
	
	@Column(name = "vl_taxa_entrega_fim")
	private Double vlTaxaEntregaFim;
	
	@Column(name = "fg_delivery")
	private String fgDelivery;
	
	@Column(name = "fg_retirada_local")
	private String fgRetiradaLocal;
	
	@Column(name = "fg_dinheiro_delivery")
	private String fgDinheiroDelivery;
	
	@Column(name = "fg_cartao_delivery")
	private String fgCartaoDelivery;
	
	@Column(name = "fg_dinheiro_retirada")
	private String fgDinheiroRetirada;
	
	@Column(name = "fg_cartao_retirada")
	private String fgCartaoRetirada;
	
	@Column(name = "vl_minimo")
	private Double vlMinimo;
	
	@Column(name = "ds_cep")
	private String dsCep;

	@Column(name = "ds_logradouro")
	private String dsLogradouro;
	
	@Column(name = "nm_endereco")
	private BigInteger nmEndereco;
	
	@Column(name = "ds_bairro")
	private String dsBairro;
	
	@Column(name = "ds_cidade")
	private String dsCidade;
	
	@Column(name = "id_estado")
	private BigInteger idEstado;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_estado", insertable = false, updatable = false)
	private Estado estado;
	
	@Column(name = "ds_complemento")
	private String dsComplemento;
	
	@Column(name = "ds_referencia")
	private String dsReferencia;
	
	@Column(name = "fg_status")
	private String fgStatus;
	
	@Column(name = "vl_classificacao")
	private Double vlClassificacao;
	
	@OneToMany(mappedBy="estabelecimento", fetch = FetchType.LAZY)
	private Set<EstabelecimentoHorario> setEstabelecimentoHorario;
	
	@OneToMany(mappedBy="estabelecimento", fetch = FetchType.LAZY)
	private Set<EstabelecimentoBandeira> setEstabelecimentoBandeira;
	
	@OneToMany(mappedBy="estabelecimento", fetch = FetchType.LAZY)
	private Set<EstabelecimentoBairro> setEstabelecimentoBairro;
	
	@OneToMany(mappedBy="estabelecimento", fetch = FetchType.LAZY)
	private Set<EstabelecimentoImagem> setEstabelecimentoImagem;
	
	@OneToMany(mappedBy="estabelecimento", fetch = FetchType.LAZY)
	private Set<Favorito> setFavorito;
	
	@Transient
	private String strVlMinimo;
	
	@Transient
	private String strVlTaxaEntregaInicio;
	
	@Transient
	private String strVlTaxaEntregaFim;
	
	@Transient
	private List<Cidade> listaCidade;
	
	@Transient
	private List<EstabelecimentoHorario> listaEstabelecimentoHorario;
	
	@Transient
	private List<EstabelecimentoBandeira> listaEstabelecimentoBandeira;
	
	@Transient
	private List<EstabelecimentoBairro> listaEstabelecimentoBairro;
	
	@Transient
	private List<EstabelecimentoImagem> listaEstabelecimentoImagem;
	
	@Transient
	private List<Favorito> listaFavorito;
	
	@Transient
	private HashMap<String, Object> filtroMap;
	
	
	public BigInteger getIdEstabelecimento() {
		return idEstabelecimento;
	}

	public void setIdEstabelecimento(BigInteger idEstabelecimento) {
		this.idEstabelecimento = idEstabelecimento;
	}

	public BigInteger getIdCidade() {
		return idCidade;
	}

	public void setIdCidade(BigInteger idCidade) {
		this.idCidade = idCidade;
	}

	public BigInteger getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(BigInteger idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getDsEstabelecimento() {
		return dsEstabelecimento;
	}

	public void setDsEstabelecimento(String dsEstabelecimento) {
		this.dsEstabelecimento = dsEstabelecimento;
	}

	public Date getDtCadastro() {
		return dtCadastro;
	}

	public void setDtCadastro(Date dtCadastro) {
		this.dtCadastro = dtCadastro;
	}

	public BigInteger getIdUsuarioCad() {
		return idUsuarioCad;
	}

	public void setIdUsuarioCad(BigInteger idUsuarioCad) {
		this.idUsuarioCad = idUsuarioCad;
	}

	public Usuario getUsuarioCad() {
		return usuarioCad;
	}

	public void setUsuarioCad(Usuario usuarioCad) {
		this.usuarioCad = usuarioCad;
	}

	public Date getDtAlteracao() {
		return dtAlteracao;
	}

	public void setDtAlteracao(Date dtAlteracao) {
		this.dtAlteracao = dtAlteracao;
	}

	public BigInteger getIdUsuarioAlt() {
		return idUsuarioAlt;
	}

	public void setIdUsuarioAlt(BigInteger idUsuarioAlt) {
		this.idUsuarioAlt = idUsuarioAlt;
	}

	public String getFgAtivo() {
		return fgAtivo;
	}

	public void setFgAtivo(String fgAtivo) {
		this.fgAtivo = fgAtivo;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public String getDsFantasia() {
		return dsFantasia;
	}

	public void setDsFantasia(String dsFantasia) {
		this.dsFantasia = dsFantasia;
	}

	public Double getVlTaxaEntregaInicio() {
		return vlTaxaEntregaInicio;
	}

	public void setVlTaxaEntregaInicio(Double vlTaxaEntregaInicio) {
		this.vlTaxaEntregaInicio = vlTaxaEntregaInicio;
	}

	public Double getVlTaxaEntregaFim() {
		return vlTaxaEntregaFim;
	}

	public void setVlTaxaEntregaFim(Double vlTaxaEntregaFim) {
		this.vlTaxaEntregaFim = vlTaxaEntregaFim;
	}

	public String getFgDelivery() {
		return fgDelivery;
	}

	public void setFgDelivery(String fgDelivery) {
		this.fgDelivery = fgDelivery;
	}

	public String getFgRetiradaLocal() {
		return fgRetiradaLocal;
	}

	public void setFgRetiradaLocal(String fgRetiradaLocal) {
		this.fgRetiradaLocal = fgRetiradaLocal;
	}

	public Double getVlMinimo() {
		return vlMinimo;
	}

	public void setVlMinimo(Double vlMinimo) {
		this.vlMinimo = vlMinimo;
	}

	public String getFgDinheiroDelivery() {
		return fgDinheiroDelivery;
	}

	public void setFgDinheiroDelivery(String fgDinheiroDelivery) {
		this.fgDinheiroDelivery = fgDinheiroDelivery;
	}

	public String getFgCartaoDelivery() {
		return fgCartaoDelivery;
	}

	public void setFgCartaoDelivery(String fgCartaoDelivery) {
		this.fgCartaoDelivery = fgCartaoDelivery;
	}

	public String getFgDinheiroRetirada() {
		return fgDinheiroRetirada;
	}

	public void setFgDinheiroRetirada(String fgDinheiroRetirada) {
		this.fgDinheiroRetirada = fgDinheiroRetirada;
	}

	public String getFgCartaoRetirada() {
		return fgCartaoRetirada;
	}

	public void setFgCartaoRetirada(String fgCartaoRetirada) {
		this.fgCartaoRetirada = fgCartaoRetirada;
	}

	public String getDsCep() {
		return dsCep;
	}

	public void setDsCep(String dsCep) {
		this.dsCep = dsCep;
	}

	public String getDsLogradouro() {
		return dsLogradouro;
	}

	public void setDsLogradouro(String dsLogradouro) {
		this.dsLogradouro = dsLogradouro;
	}

	public BigInteger getNmEndereco() {
		return nmEndereco;
	}

	public void setNmEndereco(BigInteger nmEndereco) {
		this.nmEndereco = nmEndereco;
	}

	public String getDsBairro() {
		return dsBairro;
	}

	public void setDsBairro(String dsBairro) {
		this.dsBairro = dsBairro;
	}

	public String getDsCidade() {
		return dsCidade;
	}

	public void setDsCidade(String dsCidade) {
		this.dsCidade = dsCidade;
	}

	public BigInteger getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(BigInteger idEstado) {
		this.idEstado = idEstado;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public String getDsComplemento() {
		return dsComplemento;
	}

	public void setDsComplemento(String dsComplemento) {
		this.dsComplemento = dsComplemento;
	}

	public String getDsReferencia() {
		return dsReferencia;
	}

	public void setDsReferencia(String dsReferencia) {
		this.dsReferencia = dsReferencia;
	}

	public BigInteger getVlDuracaoDeliveryInicio() {
		return vlDuracaoDeliveryInicio;
	}

	public void setVlDuracaoDeliveryInicio(BigInteger vlDuracaoDeliveryInicio) {
		this.vlDuracaoDeliveryInicio = vlDuracaoDeliveryInicio;
	}

	public BigInteger getVlDuracaoDeliveryFim() {
		return vlDuracaoDeliveryFim;
	}

	public void setVlDuracaoDeliveryFim(BigInteger vlDuracaoDeliveryFim) {
		this.vlDuracaoDeliveryFim = vlDuracaoDeliveryFim;
	}

	public BigInteger getVlDuracaoRetiradaInicio() {
		return vlDuracaoRetiradaInicio;
	}

	public void setVlDuracaoRetiradaInicio(BigInteger vlDuracaoRetiradaInicio) {
		this.vlDuracaoRetiradaInicio = vlDuracaoRetiradaInicio;
	}

	public BigInteger getVlDuracaoRetiradaFim() {
		return vlDuracaoRetiradaFim;
	}

	public void setVlDuracaoRetiradaFim(BigInteger vlDuracaoRetiradaFim) {
		this.vlDuracaoRetiradaFim = vlDuracaoRetiradaFim;
	}

	public String getStrVlMinimo() {
		return strVlMinimo;
	}

	public void setStrVlMinimo(String strVlMinimo) {
		this.strVlMinimo = strVlMinimo;
	}

	public String getStrVlTaxaEntregaInicio() {
		return strVlTaxaEntregaInicio;
	}

	public void setStrVlTaxaEntregaInicio(String strVlTaxaEntregaInicio) {
		this.strVlTaxaEntregaInicio = strVlTaxaEntregaInicio;
	}

	public String getStrVlTaxaEntregaFim() {
		return strVlTaxaEntregaFim;
	}

	public void setStrVlTaxaEntregaFim(String strVlTaxaEntregaFim) {
		this.strVlTaxaEntregaFim = strVlTaxaEntregaFim;
	}

	public HashMap<String, Object> getFiltroMap() {
		return filtroMap;
	}

	public void setFiltroMap(HashMap<String, Object> filtroMap) {
		this.filtroMap = filtroMap;
	}

	public Set<EstabelecimentoHorario> getSetEstabelecimentoHorario() {
		return setEstabelecimentoHorario;
	}

	public void setSetEstabelecimentoHorario(Set<EstabelecimentoHorario> setEstabelecimentoHorario) {
		this.setEstabelecimentoHorario = setEstabelecimentoHorario;
	}

	public Set<EstabelecimentoBandeira> getSetEstabelecimentoBandeira() {
		return setEstabelecimentoBandeira;
	}

	public void setSetEstabelecimentoBandeira(Set<EstabelecimentoBandeira> setEstabelecimentoBandeira) {
		this.setEstabelecimentoBandeira = setEstabelecimentoBandeira;
	}

	public Set<EstabelecimentoBairro> getSetEstabelecimentoBairro() {
		return setEstabelecimentoBairro;
	}

	public void setSetEstabelecimentoBairro(Set<EstabelecimentoBairro> setEstabelecimentoBairro) {
		this.setEstabelecimentoBairro = setEstabelecimentoBairro;
	}

	public List<Cidade> getListaCidade() {
		return listaCidade;
	}

	public void setListaCidade(List<Cidade> listaCidade) {
		this.listaCidade = listaCidade;
	}

	public List<EstabelecimentoHorario> getListaEstabelecimentoHorario() {
		return listaEstabelecimentoHorario;
	}

	public void setListaEstabelecimentoHorario(List<EstabelecimentoHorario> listaEstabelecimentoHorario) {
		this.listaEstabelecimentoHorario = listaEstabelecimentoHorario;
	}

	public List<EstabelecimentoBandeira> getListaEstabelecimentoBandeira() {
		return listaEstabelecimentoBandeira;
	}

	public void setListaEstabelecimentoBandeira(List<EstabelecimentoBandeira> listaEstabelecimentoBandeira) {
		this.listaEstabelecimentoBandeira = listaEstabelecimentoBandeira;
	}

	public List<EstabelecimentoBairro> getListaEstabelecimentoBairro() {
		return listaEstabelecimentoBairro;
	}

	public void setListaEstabelecimentoBairro(List<EstabelecimentoBairro> listaEstabelecimentoBairro) {
		this.listaEstabelecimentoBairro = listaEstabelecimentoBairro;
	}

	public String getFgStatus() {
		return fgStatus;
	}

	public void setFgStatus(String fgStatus) {
		this.fgStatus = fgStatus;
	}

	public Set<EstabelecimentoImagem> getSetEstabelecimentoImagem() {
		return setEstabelecimentoImagem;
	}

	public void setSetEstabelecimentoImagem(Set<EstabelecimentoImagem> setEstabelecimentoImagem) {
		this.setEstabelecimentoImagem = setEstabelecimentoImagem;
	}

	public List<EstabelecimentoImagem> getListaEstabelecimentoImagem() {
		return listaEstabelecimentoImagem;
	}

	public void setListaEstabelecimentoImagem(List<EstabelecimentoImagem> listaEstabelecimentoImagem) {
		this.listaEstabelecimentoImagem = listaEstabelecimentoImagem;
	}

	public Double getVlClassificacao() {
		return vlClassificacao;
	}

	public void setVlClassificacao(Double vlClassificacao) {
		this.vlClassificacao = vlClassificacao;
	}

	public Set<Favorito> getSetFavorito() {
		return setFavorito;
	}

	public void setSetFavorito(Set<Favorito> setFavorito) {
		this.setFavorito = setFavorito;
	}

	public List<Favorito> getListaFavorito() {
		return listaFavorito;
	}

	public void setListaFavorito(List<Favorito> listaFavorito) {
		this.listaFavorito = listaFavorito;
	}
}
