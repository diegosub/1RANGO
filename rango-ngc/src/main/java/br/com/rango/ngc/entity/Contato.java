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
 * @since 28/01/2018
 */

@Entity
@Table(name = "tb_contato", schema="rng")
@SequenceGenerator(name = "SQ_CONTATO", sequenceName = "SQ_CONTATO", allocationSize = 1)
@Proxy(lazy = true)
public class Contato implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_CONTATO")
	@Column(name = "id_contato")
	private BigInteger idContato;
	
	@Column(name = "ds_nome")
	private String dsNome;
	
	@Column(name = "ds_email")
	private String dsEmail;
	
	@Column(name = "ds_mensagem")
	private String dsMensagem;
	
	@Column(name = "fg_lido")
	private String fgLido;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_cadastro")
	private Date dtCadastro;
	
	
	public BigInteger getIdContato() {
		return idContato;
	}

	public void setIdContato(BigInteger idContato) {
		this.idContato = idContato;
	}

	public String getDsNome() {
		return dsNome;
	}

	public void setDsNome(String dsNome) {
		this.dsNome = dsNome;
	}

	public String getDsEmail() {
		return dsEmail;
	}

	public void setDsEmail(String dsEmail) {
		this.dsEmail = dsEmail;
	}

	public String getDsMensagem() {
		return dsMensagem;
	}

	public void setDsMensagem(String dsMensagem) {
		this.dsMensagem = dsMensagem;
	}

	public String getFgLido() {
		return fgLido;
	}

	public void setFgLido(String fgLido) {
		this.fgLido = fgLido;
	}

	public Date getDtCadastro() {
		return dtCadastro;
	}

	public void setDtCadastro(Date dtCadastro) {
		this.dtCadastro = dtCadastro;
	}
}
