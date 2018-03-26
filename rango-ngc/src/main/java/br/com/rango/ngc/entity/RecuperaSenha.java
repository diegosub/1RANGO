package br.com.rango.ngc.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Proxy;

/** 
 * @author Carlos Diego
 * @since 31/01/2018
 */

@Entity
@Table(name = "tb_recupera_senha", schema="rng")
@SequenceGenerator(name = "SQ_RECUPERA_SENHA", sequenceName = "SQ_RECUPERA_SENHA", allocationSize = 1)
@Proxy(lazy = true)
public class RecuperaSenha implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_RECUPERA_SENHA")
	@Column(name = "id_recupera_senha")
	private BigInteger idRecuperaSenha;
	
	@Column(name = "id_usuario")
	private BigInteger idUsuario;
	
	@Column(name = "ds_hash")
	private String dsHash;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_cadastro")
	private Date dtSolicitacao;
	

	public BigInteger getIdRecuperaSenha() {
		return idRecuperaSenha;
	}

	public void setIdRecuperaSenha(BigInteger idRecuperaSenha) {
		this.idRecuperaSenha = idRecuperaSenha;
	}

	public BigInteger getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(BigInteger idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getDsHash() {
		return dsHash;
	}

	public void setDsHash(String dsHash) {
		this.dsHash = dsHash;
	}

	public Date getDtSolicitacao() {
		return dtSolicitacao;
	}

	public void setDtSolicitacao(Date dtSolicitacao) {
		this.dtSolicitacao = dtSolicitacao;
	}
}
