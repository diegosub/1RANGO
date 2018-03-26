package br.com.rango.vo;

import java.io.Serializable;
import java.math.BigInteger;

public class BairroVO implements Serializable
{
	private static final long serialVersionUID = 1L;

	private BigInteger idBairro;
	private String dsBairro;
	private BigInteger idCidade;
	private String fgAtivo;
	private Double vlTaxa;
	private String strValorTaxa;
	
	public BigInteger getIdBairro() {
		return idBairro;
	}
	public void setIdBairro(BigInteger idBairro) {
		this.idBairro = idBairro;
	}
	public String getDsBairro() {
		return dsBairro;
	}
	public void setDsBairro(String dsBairro) {
		this.dsBairro = dsBairro;
	}
	public BigInteger getIdCidade() {
		return idCidade;
	}
	public void setIdCidade(BigInteger idCidade) {
		this.idCidade = idCidade;
	}
	public String getFgAtivo() {
		return fgAtivo;
	}
	public void setFgAtivo(String fgAtivo) {
		this.fgAtivo = fgAtivo;
	}
	public String getStrValorTaxa() {
		return strValorTaxa;
	}
	public void setStrValorTaxa(String strValorTaxa) {
		this.strValorTaxa = strValorTaxa;
	}
	public Double getVlTaxa() {
		return vlTaxa;
	}
	public void setVlTaxa(Double vlTaxa) {
		this.vlTaxa = vlTaxa;
	}
}
