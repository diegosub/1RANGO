package br.com.rango.vo;

import java.io.Serializable;
import java.math.BigInteger;

public class SegmentoItemVO implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private BigInteger idSegmentoItem;
	private BigInteger idSegmentoAdicional;
	private String dsSegmentoItem;	
	private Double vlAdicional;
	private String strVlAdicional;
	private Boolean fgSelecionada;
	
	
	public BigInteger getIdSegmentoItem() {
		return idSegmentoItem;
	}
	public void setIdSegmentoItem(BigInteger idSegmentoItem) {
		this.idSegmentoItem = idSegmentoItem;
	}
	public BigInteger getIdSegmentoAdicional() {
		return idSegmentoAdicional;
	}
	public void setIdSegmentoAdicional(BigInteger idSegmentoAdicional) {
		this.idSegmentoAdicional = idSegmentoAdicional;
	}
	public String getDsSegmentoItem() {
		return dsSegmentoItem;
	}
	public void setDsSegmentoItem(String dsSegmentoItem) {
		this.dsSegmentoItem = dsSegmentoItem;
	}
	public Double getVlAdicional() {
		return vlAdicional;
	}
	public void setVlAdicional(Double vlAdicional) {
		this.vlAdicional = vlAdicional;
	}
	public String getStrVlAdicional() {
		return strVlAdicional;
	}
	public void setStrVlAdicional(String strVlAdicional) {
		this.strVlAdicional = strVlAdicional;
	}
	public Boolean getFgSelecionada() {
		return fgSelecionada;
	}
	public void setFgSelecionada(Boolean fgSelecionada) {
		this.fgSelecionada = fgSelecionada;
	}
}
