package br.com.rango.vo;

import java.io.Serializable;
import java.math.BigInteger;

public class EstadoVO implements Serializable
{
	private static final long serialVersionUID = 1L;

	private BigInteger idEstado;
	private String dsEstado;
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
