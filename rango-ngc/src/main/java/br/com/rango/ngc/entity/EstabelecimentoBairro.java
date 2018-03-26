package br.com.rango.ngc.entity;

import java.io.Serializable;
import java.math.BigInteger;

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

import org.hibernate.annotations.Proxy;

/** 
 * @author Carlos Diego
 * @since 04/12/2017
 */

@Entity
@Table(name = "tb_estabelecimento_bairro", schema="rng")
@SequenceGenerator(name = "SQ_ESTABELECIMENTO_BAIRRO", sequenceName = "SQ_ESTABELECIMENTO_BAIRRO", allocationSize = 1)
@Proxy(lazy = true)
public class EstabelecimentoBairro implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_ESTABELECIMENTO_BAIRRO")
	@Column(name = "id_estabelecimento_bairro")
	private BigInteger idEstabelecimentoBairro;
	
	@Column(name = "id_estabelecimento")
	private BigInteger idEstabelecimento;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_estabelecimento", insertable = false, updatable = false)
	private Estabelecimento estabelecimento;
		
	@Column(name = "id_bairro")
	private BigInteger idBairro;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_bairro", insertable = false, updatable = false)
	private Bairro bairro;
	
	@Column(name = "vl_taxa")
	private Double vlTaxa;
	

	public BigInteger getIdEstabelecimentoBairro() {
		return idEstabelecimentoBairro;
	}

	public void setIdEstabelecimentoBairro(BigInteger idEstabelecimentoBairro) {
		this.idEstabelecimentoBairro = idEstabelecimentoBairro;
	}

	public BigInteger getIdEstabelecimento() {
		return idEstabelecimento;
	}

	public void setIdEstabelecimento(BigInteger idEstabelecimento) {
		this.idEstabelecimento = idEstabelecimento;
	}

	public Estabelecimento getEstabelecimento() {
		return estabelecimento;
	}

	public void setEstabelecimento(Estabelecimento estabelecimento) {
		this.estabelecimento = estabelecimento;
	}

	public BigInteger getIdBairro() {
		return idBairro;
	}

	public void setIdBairro(BigInteger idBairro) {
		this.idBairro = idBairro;
	}

	public Bairro getBairro() {
		return bairro;
	}

	public void setBairro(Bairro bairro) {
		this.bairro = bairro;
	}

	public Double getVlTaxa() {
		return vlTaxa;
	}

	public void setVlTaxa(Double vlTaxa) {
		this.vlTaxa = vlTaxa;
	}
}