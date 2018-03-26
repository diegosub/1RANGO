package br.com.rango.vo;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

public class PedidoProdutoVO implements Serializable
{
	private static final long serialVersionUID = 1L;

	private BigInteger idPedidoProduto;
	private BigInteger idPedido;
	private BigInteger idProduto;
	private String dsProduto;
	private BigInteger qtProduto;
	private Double vlProduto;
	private String dsObservacao;
	
	private List<PedidoProdutoAdicionalVO> listaPedidoProdutoAdicional;
	
	
	public BigInteger getIdPedidoProduto() {
		return idPedidoProduto;
	}
	public void setIdPedidoProduto(BigInteger idPedidoProduto) {
		this.idPedidoProduto = idPedidoProduto;
	}
	public BigInteger getIdPedido() {
		return idPedido;
	}
	public void setIdPedido(BigInteger idPedido) {
		this.idPedido = idPedido;
	}
	public BigInteger getIdProduto() {
		return idProduto;
	}
	public void setIdProduto(BigInteger idProduto) {
		this.idProduto = idProduto;
	}
	public String getDsProduto() {
		return dsProduto;
	}
	public void setDsProduto(String dsProduto) {
		this.dsProduto = dsProduto;
	}
	public BigInteger getQtProduto() {
		return qtProduto;
	}
	public void setQtProduto(BigInteger qtProduto) {
		this.qtProduto = qtProduto;
	}
	public Double getVlProduto() {
		return vlProduto;
	}
	public void setVlProduto(Double vlProduto) {
		this.vlProduto = vlProduto;
	}
	public String getDsObservacao() {
		return dsObservacao;
	}
	public void setDsObservacao(String dsObservacao) {
		this.dsObservacao = dsObservacao;
	}
	public List<PedidoProdutoAdicionalVO> getListaPedidoProdutoAdicional() {
		return listaPedidoProdutoAdicional;
	}
	public void setListaPedidoProdutoAdicional(List<PedidoProdutoAdicionalVO> listaPedidoProdutoAdicional) {
		this.listaPedidoProdutoAdicional = listaPedidoProdutoAdicional;
	}
}
