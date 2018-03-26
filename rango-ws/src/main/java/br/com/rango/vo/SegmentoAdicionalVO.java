package br.com.rango.vo;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

public class SegmentoAdicionalVO implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private BigInteger idSegmentoAdicional;
	private BigInteger idSegmento;
	private String dsSegmentoAdicional;
	private String fgMultiplosItens;
	private String fgObrigatorio;
	private String fgAtivo;
	private String dsSelecionado;
	
	List<SegmentoItemVO> listaSegmentoItem;
	
	
	public BigInteger getIdSegmentoAdicional() {
		return idSegmentoAdicional;
	}
	public void setIdSegmentoAdicional(BigInteger idSegmentoAdicional) {
		this.idSegmentoAdicional = idSegmentoAdicional;
	}
	public BigInteger getIdSegmento() {
		return idSegmento;
	}
	public void setIdSegmento(BigInteger idSegmento) {
		this.idSegmento = idSegmento;
	}
	public String getDsSegmentoAdicional() {
		return dsSegmentoAdicional;
	}
	public void setDsSegmentoAdicional(String dsSegmentoAdicional) {
		this.dsSegmentoAdicional = dsSegmentoAdicional;
	}
	public String getFgMultiplosItens() {
		return fgMultiplosItens;
	}
	public void setFgMultiplosItens(String fgMultiplosItens) {
		this.fgMultiplosItens = fgMultiplosItens;
	}
	public String getFgAtivo() {
		return fgAtivo;
	}
	public void setFgAtivo(String fgAtivo) {
		this.fgAtivo = fgAtivo;
	}
	public List<SegmentoItemVO> getListaSegmentoItem() {
		return listaSegmentoItem;
	}
	public void setListaSegmentoItem(List<SegmentoItemVO> listaSegmentoItem) {
		this.listaSegmentoItem = listaSegmentoItem;
	}
	public String getDsSelecionado() {
		return dsSelecionado;
	}
	public void setDsSelecionado(String dsSelecionado) {
		this.dsSelecionado = dsSelecionado;
	}
	public String getFgObrigatorio() {
		return fgObrigatorio;
	}
	public void setFgObrigatorio(String fgObrigatorio) {
		this.fgObrigatorio = fgObrigatorio;
	}
	
}
