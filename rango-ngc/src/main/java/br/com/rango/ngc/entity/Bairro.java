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
 * @since 17/11/2017
 */

@Entity
@Table(name = "tb_bairro", schema="rng")
@SequenceGenerator(name = "SQ_BAIRRO", sequenceName = "SQ_BAIRRO", allocationSize = 1)
@Proxy(lazy = true)
public class Bairro implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_BAIRRO")
	@Column(name = "id_bairro")
	private BigInteger idBairro;
	
	@Column(name = "ds_bairro")
	private String dsBairro;
	
	@Column(name = "id_cidade")
	private BigInteger idCidade;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cidade", insertable = false, updatable = false)
	private Cidade cidade;
	
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
	private	boolean fgSelecionado;
	
	@Transient
	private String strValorTaxa;
	
	@Transient
	private HashMap<String, Object> filtroMap;

	public BigInteger getIdBairro() {
		return idBairro;
	}

	public void setIdBairro(BigInteger idBairro) {
		this.idBairro = idBairro;
	}

	public String getDsBairro() {
		return dsBairro;
	}

	public void setDsBairro(String dsBairro) {
		this.dsBairro = dsBairro;
	}

	public BigInteger getIdCidade() {
		return idCidade;
	}

	public void setIdCidade(BigInteger idCidade) {
		this.idCidade = idCidade;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
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

	public boolean isFgSelecionado() {
		return fgSelecionado;
	}

	public void setFgSelecionado(boolean fgSelecionado) {
		this.fgSelecionado = fgSelecionado;
	}

	public String getStrValorTaxa() {
		return strValorTaxa;
	}

	public void setStrValorTaxa(String strValorTaxa) {
		this.strValorTaxa = strValorTaxa;
	}
}
