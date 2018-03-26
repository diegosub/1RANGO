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
 * @since 25/01/2018
 */

@Entity
@Table(name = "tb_favorito", schema="rng")
@SequenceGenerator(name = "SQ_FAVORITO", sequenceName = "SQ_FAVORITO", allocationSize = 1)
@Proxy(lazy = true)
public class Favorito implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_FAVORITO")
	@Column(name = "id_favorito")
	private BigInteger idFavorito;
	
	@Column(name = "id_estabelecimento")
	private BigInteger idEstabelecimento;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_estabelecimento", insertable = false, updatable = false)
	private Estabelecimento estabelecimento;
	
	@Column(name = "id_usuario")
	private BigInteger idUsuario;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_cadastro")
	private Date dtCadastro;
	

	public BigInteger getIdFavorito() {
		return idFavorito;
	}

	public void setIdFavorito(BigInteger idFavorito) {
		this.idFavorito = idFavorito;
	}

	public BigInteger getIdEstabelecimento() {
		return idEstabelecimento;
	}

	public void setIdEstabelecimento(BigInteger idEstabelecimento) {
		this.idEstabelecimento = idEstabelecimento;
	}

	public BigInteger getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(BigInteger idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Date getDtCadastro() {
		return dtCadastro;
	}

	public void setDtCadastro(Date dtCadastro) {
		this.dtCadastro = dtCadastro;
	}

	public Estabelecimento getEstabelecimento() {
		return estabelecimento;
	}

	public void setEstabelecimento(Estabelecimento estabelecimento) {
		this.estabelecimento = estabelecimento;
	}
}