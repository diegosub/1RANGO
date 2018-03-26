package br.com.rango.vo;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public class SegmentoVO implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private BigInteger idSegmento;	
	private String dsSegmento;
	private BigInteger idEstabelecimento;	
	private Date dtCadastro;	
	private BigInteger idUsuarioCad;
	private String fgAtivo;
	
	private List<ProdutoVO> listaProduto;
	
	
	public BigInteger getIdSegmento() {
		return idSegmento;
	}
	public void setIdSegmento(BigInteger idSegmento) {
		this.idSegmento = idSegmento;
	}
	public String getDsSegmento() {
		return dsSegmento;
	}
	public void setDsSegmento(String dsSegmento) {
		this.dsSegmento = dsSegmento;
	}
	public BigInteger getIdEstabelecimento() {
		return idEstabelecimento;
	}
	public void setIdEstabelecimento(BigInteger idEstabelecimento) {
		this.idEstabelecimento = idEstabelecimento;
	}
	public Date getDtCadastro() {
		return dtCadastro;
	}
	public void setDtCadastro(Date dtCadastro) {
		this.dtCadastro = dtCadastro;
	}
	public BigInteger getIdUsuarioCad() {
		return idUsuarioCad;
	}
	public void setIdUsuarioCad(BigInteger idUsuarioCad) {
		this.idUsuarioCad = idUsuarioCad;
	}
	public String getFgAtivo() {
		return fgAtivo;
	}
	public void setFgAtivo(String fgAtivo) {
		this.fgAtivo = fgAtivo;
	}
	public List<ProdutoVO> getListaProduto() {
		return listaProduto;
	}
	public void setListaProduto(List<ProdutoVO> listaProduto) {
		this.listaProduto = listaProduto;
	}
}
