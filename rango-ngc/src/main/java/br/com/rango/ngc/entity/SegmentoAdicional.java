package br.com.rango.ngc.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

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

import org.hibernate.annotations.Proxy;

/** 
 * @author Carlos Diego
 * @since 22/11/2017
 */

@Entity
@Table(name = "tb_segmento_adicional", schema="rng")
@SequenceGenerator(name = "SQ_SEGMENTO_ADICIONAL", sequenceName = "SQ_SEGMENTO_ADICIONAL", allocationSize = 1)
@Proxy(lazy = true)
public class SegmentoAdicional implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_SEGMENTO_ADICIONAL")
	@Column(name = "id_segmento_adicional")
	private BigInteger idSegmentoAdicional;
	
	@Column(name = "id_segmento")
	private BigInteger idSegmento;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_segmento", insertable = false, updatable = false)
	private Segmento segmento;
	
	@Column(name = "ds_segmento_adicional")
	private String dsSegmentoAdicional;
	
	@Column(name = "fg_multiplos_itens")
	private String fgMultiplosItens;
	
	@Column(name = "fg_obrigatorio")
	private String fgObrigatorio;
	
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
	
	@OneToMany(mappedBy="segmentoAdicional", fetch = FetchType.LAZY)
	private List<SegmentoItem> listaSegmentoItem;

	public BigInteger getIdSegmentoAdicional() {
		return idSegmentoAdicional;
	}

	public void setIdSegmentoAdicional(BigInteger idSegmentoAdicional) {
		this.idSegmentoAdicional = idSegmentoAdicional;
	}

	public BigInteger getIdSegmento() {
		return idSegmento;
	}

	public void setIdSegmento(BigInteger idSegmento) {
		this.idSegmento = idSegmento;
	}

	public String getDsSegmentoAdicional() {
		return dsSegmentoAdicional;
	}

	public void setDsSegmentoAdicional(String dsSegmentoAdicional) {
		this.dsSegmentoAdicional = dsSegmentoAdicional;
	}

	public String getFgMultiplosItens() {
		return fgMultiplosItens;
	}

	public void setFgMultiplosItens(String fgMultiplosItens) {
		this.fgMultiplosItens = fgMultiplosItens;
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

	public List<SegmentoItem> getListaSegmentoItem() {
		return listaSegmentoItem;
	}

	public void setListaSegmentoItem(List<SegmentoItem> listaSegmentoItem) {
		this.listaSegmentoItem = listaSegmentoItem;
	}

	public Segmento getSegmento() {
		return segmento;
	}

	public void setSegmento(Segmento segmento) {
		this.segmento = segmento;
	}

	public String getFgObrigatorio() {
		return fgObrigatorio;
	}

	public void setFgObrigatorio(String fgObrigatorio) {
		this.fgObrigatorio = fgObrigatorio;
	}
}
