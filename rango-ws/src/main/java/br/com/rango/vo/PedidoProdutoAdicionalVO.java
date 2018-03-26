package br.com.rango.vo;

import java.io.Serializable;
import java.math.BigInteger;

public class PedidoProdutoAdicionalVO implements Serializable
{
	private static final long serialVersionUID = 1L;

	private BigInteger idPedidoProdutoAdicional;
	private BigInteger idPedidoProduto;
	private String dsAdicional;
	private Double vlAdicional;
	
	
	public BigInteger getIdPedidoProdutoAdicional() {
		return idPedidoProdutoAdicional;
	}
	public void setIdPedidoProdutoAdicional(BigInteger idPedidoProdutoAdicional) {
		this.idPedidoProdutoAdicional = idPedidoProdutoAdicional;
	}
	public BigInteger getIdPedidoProduto() {
		return idPedidoProduto;
	}
	public void setIdPedidoProduto(BigInteger idPedidoProduto) {
		this.idPedidoProduto = idPedidoProduto;
	}
	public String getDsAdicional() {
		return dsAdicional;
	}
	public void setDsAdicional(String dsAdicional) {
		this.dsAdicional = dsAdicional;
	}
	public Double getVlAdicional() {
		return vlAdicional;
	}
	public void setVlAdicional(Double vlAdicional) {
		this.vlAdicional = vlAdicional;
	}


}
