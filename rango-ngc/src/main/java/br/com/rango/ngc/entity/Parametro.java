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
@Table(name = "tb_parametro", schema="rng")
@Proxy(lazy = true)
public class Parametro implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id_parametro")
	private BigInteger idParametro;
	
	@Column(name = "ds_parametro")
	private String dsParametro;
	
	@Column(name = "vl_parametro")
	private String vlParametro;

	public BigInteger getIdParametro() {
		return idParametro;
	}

	public void setIdParametro(BigInteger idParametro) {
		this.idParametro = idParametro;
	}

	public String getDsParametro() {
		return dsParametro;
	}

	public void setDsParametro(String dsParametro) {
		this.dsParametro = dsParametro;
	}

	public String getVlParametro() {
		return vlParametro;
	}

	public void setVlParametro(String vlParametro) {
		this.vlParametro = vlParametro;
	}
}
