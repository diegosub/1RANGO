package br.com.rango.vo;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

public class ProdutoVO implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private BigInteger idProduto;
	private String dsProduto;
	private String dsAdicional;
	private Double vlProduto;
	private BigInteger idEstabelecimento;
	private BigInteger idSegmento;
	private String fgAtivo;
	private String dsVlrProduto;
	
	private List<SegmentoAdicionalVO> listaSegmentoAdicional;

	
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
	public String getDsAdicional() {
		return dsAdicional;
	}
	public void setDsAdicional(String dsAdicional) {
		this.dsAdicional = dsAdicional;
	}
	public Double getVlProduto() {
		return vlProduto;
	}
	public void setVlProduto(Double vlProduto) {
		this.vlProduto = vlProduto;
	}
	public BigInteger getIdEstabelecimento() {
		return idEstabelecimento;
	}
	public void setIdEstabelecimento(BigInteger idEstabelecimento) {
		this.idEstabelecimento = idEstabelecimento;
	}
	public BigInteger getIdSegmento() {
		return idSegmento;
	}
	public void setIdSegmento(BigInteger idSegmento) {
		this.idSegmento = idSegmento;
	}
	public String getFgAtivo() {
		return fgAtivo;
	}
	public void setFgAtivo(String fgAtivo) {
		this.fgAtivo = fgAtivo;
	}
	public String getDsVlrProduto() {
		return dsVlrProduto;
	}
	public void setDsVlrProduto(String dsVlrProduto) {
		this.dsVlrProduto = dsVlrProduto;
	}
	public List<SegmentoAdicionalVO> getListaSegmentoAdicional() {
		return listaSegmentoAdicional;
	}
	public void setListaSegmentoAdicional(List<SegmentoAdicionalVO> listaSegmentoAdicional) {
		this.listaSegmentoAdicional = listaSegmentoAdicional;
	}
}
