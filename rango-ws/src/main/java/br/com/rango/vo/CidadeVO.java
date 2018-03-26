package br.com.rango.vo;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

public class CidadeVO implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private BigInteger idCidade;	
	private String dsCidade;		
	private BigInteger idEstado;	
	private String fgAtivo;
	private String dsEstado;
	private String dsSiglaEstado;
	private Integer nrTotalEstabelecimento;
	
	private List<BairroVO> listaBairros;

	public BigInteger getIdCidade() {
		return idCidade;
	}

	public void setIdCidade(BigInteger idCidade) {
		this.idCidade = idCidade;
	}

	public String getDsCidade() {
		return dsCidade;
	}

	public void setDsCidade(String dsCidade) {
		this.dsCidade = dsCidade;
	}

	public BigInteger getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(BigInteger idEstado) {
		this.idEstado = idEstado;
	}

	public String getFgAtivo() {
		return fgAtivo;
	}

	public void setFgAtivo(String fgAtivo) {
		this.fgAtivo = fgAtivo;
	}

	public String getDsEstado() {
		return dsEstado;
	}

	public void setDsEstado(String dsEstado) {
		this.dsEstado = dsEstado;
	}

	public String getDsSiglaEstado() {
		return dsSiglaEstado;
	}

	public void setDsSiglaEstado(String dsSiglaEstado) {
		this.dsSiglaEstado = dsSiglaEstado;
	}

	public Integer getNrTotalEstabelecimento() {
		return nrTotalEstabelecimento;
	}

	public void setNrTotalEstabelecimento(Integer nrTotalEstabelecimento) {
		this.nrTotalEstabelecimento = nrTotalEstabelecimento;
	}

	public List<BairroVO> getListaBairros() {
		return listaBairros;
	}

	public void setListaBairros(List<BairroVO> listaBairros) {
		this.listaBairros = listaBairros;
	}
}
