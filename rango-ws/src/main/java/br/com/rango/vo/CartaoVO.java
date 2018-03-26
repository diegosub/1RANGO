package br.com.rango.vo;

import java.io.Serializable;
import java.math.BigInteger;

public class CartaoVO implements Serializable
{
	private static final long serialVersionUID = 1L;

	private BigInteger idCartao;
	private String dsCartao;
	
	public BigInteger getIdCartao() {
		return idCartao;
	}
	public void setIdCartao(BigInteger idCartao) {
		this.idCartao = idCartao;
	}
	public String getDsCartao() {
		return dsCartao;
	}
	public void setDsCartao(String dsCartao) {
		this.dsCartao = dsCartao;
	}
}
