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
 * @since 22/11/2017
 */

@Entity
@Table(name = "tb_segmento", schema="rng")
@SequenceGenerator(name = "SQ_SEGMENTO", sequenceName = "SQ_SEGMENTO", allocationSize = 1)
@Proxy(lazy = true)
public class Segmento implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_SEGMENTO")
	@Column(name = "id_segmento")
	private BigInteger idSegmento;
	
	@Column(name = "ds_segmento")
	private String dsSegmento;

	@Column(name = "id_estabelecimento")
	private BigInteger idEstabelecimento;
	
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario_alt", insertable = false, updatable = false)
	private Usuario usuarioAlt;
	
	@Column(name = "fg_ativo")
	private String fgAtivo;
	
	@OneToMany(mappedBy="segmento", fetch = FetchType.LAZY)  
	private List<SegmentoAdicional> listaSegmentoAdicional;
	
	@OneToMany(mappedBy="segmento", fetch = FetchType.LAZY)  
	private List<SegmentoDia> listaSegmentoDia;
	
	@OneToMany(mappedBy="segmento", fetch = FetchType.LAZY)  
	private Set<Produto> setProduto;
	
	@Transient
	private List<SegmentoAdicional> listaSegmentoAdicionalBase;
	
	@Transient
	private List<Produto> listaProduto;
	
	@Transient
	private HashMap<String, Object> filtroMap;

	public BigInteger getIdSegmento() {
		return idSegmento;
	}

	public void setIdSegmento(BigInteger idSegmento) {
		this.idSegmento = idSegmento;
	}

	public String getDsSegmento() {
		return dsSegmento;
	}

	public void setDsSegmento(String dsSegmento) {
		this.dsSegmento = dsSegmento;
	}

	public BigInteger getIdEstabelecimento() {
		return idEstabelecimento;
	}

	public void setIdEstabelecimento(BigInteger idEstabelecimento) {
		this.idEstabelecimento = idEstabelecimento;
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

	public Usuario getUsuarioAlt() {
		return usuarioAlt;
	}

	public void setUsuarioAlt(Usuario usuarioAlt) {
		this.usuarioAlt = usuarioAlt;
	}

	public HashMap<String, Object> getFiltroMap() {
		return filtroMap;
	}

	public void setFiltroMap(HashMap<String, Object> filtroMap) {
		this.filtroMap = filtroMap;
	}

	public List<SegmentoAdicional> getListaSegmentoAdicional() {
		return listaSegmentoAdicional;
	}

	public void setListaSegmentoAdicional(List<SegmentoAdicional> listaSegmentoAdicional) {
		this.listaSegmentoAdicional = listaSegmentoAdicional;
	}

	public List<SegmentoAdicional> getListaSegmentoAdicionalBase() {
		return listaSegmentoAdicionalBase;
	}

	public void setListaSegmentoAdicionalBase(List<SegmentoAdicional> listaSegmentoAdicionalBase) {
		this.listaSegmentoAdicionalBase = listaSegmentoAdicionalBase;
	}

	public List<SegmentoDia> getListaSegmentoDia() {
		return listaSegmentoDia;
	}

	public void setListaSegmentoDia(List<SegmentoDia> listaSegmentoDia) {
		this.listaSegmentoDia = listaSegmentoDia;
	}

	public Set<Produto> getSetProduto() {
		return setProduto;
	}

	public void setSetProduto(Set<Produto> setProduto) {
		this.setProduto = setProduto;
	}

	public List<Produto> getListaProduto() {
		return listaProduto;
	}

	public void setListaProduto(List<Produto> listaProduto) {
		this.listaProduto = listaProduto;
	}
}
