package br.com.rango.ngc.entity;

import java.io.Serializable;
import java.math.BigInteger;
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
import javax.persistence.Transient;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Proxy;

/** 
 * @author Carlos Diego
 * @since 30/11/2017
 */

@Entity
@Table(name = "tb_estabelecimento_bandeira", schema="rng")
@SequenceGenerator(name = "SQ_ESTABELECIMENTO_BANDEIRA", sequenceName = "SQ_ESTABELECIMENTO_BANDEIRA", allocationSize = 1)
@Proxy(lazy = true)
public class EstabelecimentoBandeira implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_ESTABELECIMENTO_BANDEIRA")
	@Column(name = "id_estabelecimento_bandeira")
	private BigInteger idEstabelecimentoBandeira;
	
	@Column(name = "id_estabelecimento")
	private BigInteger idEstabelecimento;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_estabelecimento", insertable = false, updatable = false)
	private Estabelecimento estabelecimento;
		
	@Column(name = "id_bandeira_retirada")
	private BigInteger idBandeiraRetirada;
	
	@Formula("(select d.ds_dominio from rng.tb_dominio d where d.vl_dominio = id_bandeira_retirada::varchar and d.nm_campo = 'BANDEIRA_CARTAO')")
	private String dsBandeiraRetirada;
	
	@Column(name = "id_bandeira_delivery")
	private BigInteger idBandeiraDelivery;
	
	@Formula("(select d.ds_dominio from rng.tb_dominio d where d.vl_dominio = id_bandeira_delivery::varchar and d.nm_campo = 'BANDEIRA_CARTAO')")
	private String dsBandeiraDelivery;
	
	@Transient
	private HashMap<String, Object> filtroMap;
	

	public BigInteger getIdEstabelecimentoBandeira() {
		return idEstabelecimentoBandeira;
	}

	public void setIdEstabelecimentoBandeira(BigInteger idEstabelecimentoBandeira) {
		this.idEstabelecimentoBandeira = idEstabelecimentoBandeira;
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

	public BigInteger getIdBandeiraRetirada() {
		return idBandeiraRetirada;
	}

	public void setIdBandeiraRetirada(BigInteger idBandeiraRetirada) {
		this.idBandeiraRetirada = idBandeiraRetirada;
	}

	public BigInteger getIdBandeiraDelivery() {
		return idBandeiraDelivery;
	}

	public void setIdBandeiraDelivery(BigInteger idBandeiraDelivery) {
		this.idBandeiraDelivery = idBandeiraDelivery;
	}

	public String getDsBandeiraDelivery() {
		return dsBandeiraDelivery;
	}

	public void setDsBandeiraDelivery(String dsBandeiraDelivery) {
		this.dsBandeiraDelivery = dsBandeiraDelivery;
	}

	public String getDsBandeiraRetirada() {
		return dsBandeiraRetirada;
	}

	public void setDsBandeiraRetirada(String dsBandeiraRetirada) {
		this.dsBandeiraRetirada = dsBandeiraRetirada;
	}

	public HashMap<String, Object> getFiltroMap() {
		return filtroMap;
	}

	public void setFiltroMap(HashMap<String, Object> filtroMap) {
		this.filtroMap = filtroMap;
	}
}
