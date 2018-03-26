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
 * @since 21/11/2017
 */

@Entity
@Table(name = "tb_categoria", schema="rng")
@SequenceGenerator(name = "SQ_CATEGORIA", sequenceName = "SQ_CATEGORIA", allocationSize = 1)
@Proxy(lazy = true)
public class Categoria implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_CATEGORIA")
	@Column(name = "id_categoria")
	private BigInteger idCategoria;
	
	@Column(name = "ds_categoria")
	private String dsCategoria;
	
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
	private HashMap<String, Object> filtroMap;
	

	public BigInteger getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(BigInteger idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getDsCategoria() {
		return dsCategoria;
	}

	public void setDsCategoria(String dsCategoria) {
		this.dsCategoria = dsCategoria;
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
}
