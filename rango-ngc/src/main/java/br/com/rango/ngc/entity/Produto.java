package br.com.rango.ngc.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Proxy;

/** 
 * @author Carlos Diego
 * @since 28/11/2017
 */

@Entity
@Table(name = "tb_produto", schema="rng")
@SequenceGenerator(name = "SQ_PRODUTO", sequenceName = "SQ_PRODUTO", allocationSize = 1)
@Proxy(lazy = true)
public class Produto implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_PRODUTO")
	@Column(name = "id_produto")
	private BigInteger idProduto;
	
	@Column(name = "ds_produto")
	private String dsProduto;
	
	@Column(name = "ds_adicional")
	private String dsAdicional;
	
	@Column(name = "vl_produto")
	private Double vlProduto;
	
	@Column(name = "id_estabelecimento")
	private BigInteger idEstabelecimento;
	
	@Column(name = "id_segmento")
	private BigInteger idSegmento;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_segmento", insertable = false, updatable = false)
	private Segmento segmento;
	
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

	@Transient
	private String strVlProduto;
	
	@Transient
	private HashMap<String, Object> filtroMap;

	public BigInteger getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(BigInteger idProduto) {
		this.idProduto = idProduto;
	}

	public String getDsProduto() {
		return dsProduto;
	}

	public void setDsProduto(String dsProduto) {
		this.dsProduto = dsProduto;
	}

	public String getDsAdicional() {
		return dsAdicional;
	}

	public void setDsAdicional(String dsAdicional) {
		this.dsAdicional = dsAdicional;
	}

	public Double getVlProduto() {
		return vlProduto;
	}

	public void setVlProduto(Double vlProduto) {
		this.vlProduto = vlProduto;
	}

	public BigInteger getIdEstabelecimento() {
		return idEstabelecimento;
	}

	public void setIdEstabelecimento(BigInteger idEstabelecimento) {
		this.idEstabelecimento = idEstabelecimento;
	}

	public BigInteger getIdSegmento() {
		return idSegmento;
	}

	public void setIdSegmento(BigInteger idSegmento) {
		this.idSegmento = idSegmento;
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

	public HashMap<String, Object> getFiltroMap() {
		return filtroMap;
	}

	public void setFiltroMap(HashMap<String, Object> filtroMap) {
		this.filtroMap = filtroMap;
	}

	public String getStrVlProduto() {
		return strVlProduto;
	}

	public void setStrVlProduto(String strVlProduto) {
		this.strVlProduto = strVlProduto;
	}

	public Segmento getSegmento() {
		return segmento;
	}

	public void setSegmento(Segmento segmento) {
		this.segmento = segmento;
	}
}