package br.com.rango.ngc.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

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

import org.hibernate.annotations.Proxy;

/** 
 * @author Carlos Diego
 * @since 11/01/2018
 */

@Entity
@Table(name = "tb_endereco", schema="rng")
@SequenceGenerator(name = "SQ_ENDERECO", sequenceName = "SQ_ENDERECO", allocationSize = 1)
@Proxy(lazy = true)
public class Endereco implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_ENDERECO")
	@Column(name = "id_endereco")
	private BigInteger idEndereco;
	
	@Column(name = "id_usuario")
	private BigInteger idUsuario;
	
	@Column(name = "ds_endereco")
	private String dsEndereco;
	
	@Column(name = "ds_cep")
	private String dsCep;
	
	@Column(name = "ds_rua")
	private String dsRua;
	
	@Column(name = "ds_numero")
	private String dsNumero;
	
	@Column(name = "ds_complemento")
	private String dsComplemento;
	
	@Column(name = "ds_bairro")
	private String dsBairro;
	
	@Column(name = "ds_cidade")
	private String dsCidade;
	
	@Column(name = "id_estado")
	private BigInteger idEstado;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_estado", insertable = false, updatable = false)
	private Estado estado;
	
	@Column(name = "ds_referencia")
	private String dsReferencia;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_cadastro")
	private Date dtCadastro;

	public BigInteger getIdEndereco() {
		return idEndereco;
	}

	public void setIdEndereco(BigInteger idEndereco) {
		this.idEndereco = idEndereco;
	}

	public BigInteger getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(BigInteger idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getDsEndereco() {
		return dsEndereco;
	}

	public void setDsEndereco(String dsEndereco) {
		this.dsEndereco = dsEndereco;
	}

	public String getDsCep() {
		return dsCep;
	}

	public void setDsCep(String dsCep) {
		this.dsCep = dsCep;
	}

	public String getDsRua() {
		return dsRua;
	}

	public void setDsRua(String dsRua) {
		this.dsRua = dsRua;
	}

	public String getDsNumero() {
		return dsNumero;
	}

	public void setDsNumero(String dsNumero) {
		this.dsNumero = dsNumero;
	}

	public String getDsComplemento() {
		return dsComplemento;
	}

	public void setDsComplemento(String dsComplemento) {
		this.dsComplemento = dsComplemento;
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

	public String getDsReferencia() {
		return dsReferencia;
	}

	public void setDsReferencia(String dsReferencia) {
		this.dsReferencia = dsReferencia;
	}

	public Date getDtCadastro() {
		return dtCadastro;
	}

	public void setDtCadastro(Date dtCadastro) {
		this.dtCadastro = dtCadastro;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}
}
