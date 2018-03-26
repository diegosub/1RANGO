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
 * @since 17/11/2017
 */

@Entity
@Table(name = "tb_estado", schema="rng")
@Proxy(lazy = true)
public class Estado implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id_estado")
	private BigInteger idEstado;
	
	@Column(name = "ds_estado")
	private String dsEstado;
	
	@Column(name = "ds_sigla")
	private String dsSigla;

	public BigInteger getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(BigInteger idEstado) {
		this.idEstado = idEstado;
	}

	public String getDsEstado() {
		return dsEstado;
	}

	public void setDsEstado(String dsEstado) {
		this.dsEstado = dsEstado;
	}

	public String getDsSigla() {
		return dsSigla;
	}

	public void setDsSigla(String dsSigla) {
		this.dsSigla = dsSigla;
	}
}
