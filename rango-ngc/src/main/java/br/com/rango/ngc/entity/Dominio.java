package br.com.rango.ngc.entity;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

/** 
 * @author Carlos Diego
 * @since 28/11/2017
 */

@Entity
@Table(name = "tb_dominio", schema="rng")
@Proxy(lazy = true)
public class Dominio implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id_dominio")
	private BigInteger idDominio;
	
	@Column(name = "ds_dominio")
	private String dsDominio;
	
	@Column(name = "vl_dominio")
	private String vlDominio;
	
	@Column(name = "nm_campo")
	private String nmCampo;

	public BigInteger getIdDominio() {
		return idDominio;
	}

	public void setIdDominio(BigInteger idDominio) {
		this.idDominio = idDominio;
	}

	public String getDsDominio() {
		return dsDominio;
	}

	public void setDsDominio(String dsDominio) {
		this.dsDominio = dsDominio;
	}

	public String getVlDominio() {
		return vlDominio;
	}

	public void setVlDominio(String vlDominio) {
		this.vlDominio = vlDominio;
	}

	public String getNmCampo() {
		return nmCampo;
	}

	public void setNmCampo(String nmCampo) {
		this.nmCampo = nmCampo;
	}
}
