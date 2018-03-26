package br.com.rango.ngc.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
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
import javax.persistence.Transient;

import org.hibernate.annotations.Proxy;

/** 
 * @author Carlos Diego
 * @since 17/11/2017
 */

@Entity
@Table(name = "tb_cidade", schema="rng")
@SequenceGenerator(name = "SQ_CIDADE", sequenceName = "SQ_CIDADE", allocationSize = 1)
@Proxy(lazy = true)
public class Cidade implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_CIDADE")
	@Column(name = "id_cidade")
	private BigInteger idCidade;
	
	@Column(name = "ds_cidade")
	private String dsCidade;
	
	@Column(name = "id_estado")
	private BigInteger idEstado;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_estado", insertable = false, updatable = false)
	private Estado estado;
	
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
	
	@OneToMany(mappedBy="cidade", fetch = FetchType.LAZY)  
	private List<Bairro> listaBairro;
	
	@OneToMany(mappedBy="cidade", fetch = FetchType.LAZY)  
	private List<Estabelecimento> listaEstabelecimento;
	
	@Transient
	private HashMap<String, Object> filtroMap;
	
	
	public BigInteger getIdCidade() {
		return idCidade;
	}

	public void setIdCidade(BigInteger idCidade) {
		this.idCidade = idCidade;
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

	public List<Bairro> getListaBairro() {
		return listaBairro;
	}

	public void setListaBairro(List<Bairro> listaBairro) {
		this.listaBairro = listaBairro;
	}

	public List<Estabelecimento> getListaEstabelecimento() {
		return listaEstabelecimento;
	}

	public void setListaEstabelecimento(List<Estabelecimento> listaEstabelecimento) {
		this.listaEstabelecimento = listaEstabelecimento;
	}
}
