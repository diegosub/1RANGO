package br.com.rango.vo;

import java.io.Serializable;
import java.math.BigInteger;

public class StatusVO implements Serializable
{
	private static final long serialVersionUID = 1L;

	private BigInteger idEstabelecimento;
	private String dsStatus;
	
	public BigInteger getIdEstabelecimento() {
		return idEstabelecimento;
	}
	public void setIdEstabelecimento(BigInteger idEstabelecimento) {
		this.idEstabelecimento = idEstabelecimento;
	}
	public String getDsStatus() {
		return dsStatus;
	}
	public void setDsStatus(String dsStatus) {
		this.dsStatus = dsStatus;
	}
}
