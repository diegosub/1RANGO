package br.com.rango.vo;

import java.io.Serializable;
import java.math.BigInteger;

public class HorarioVO implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private BigInteger idEstabelecimentoHorario;
	private BigInteger idEstabelecimento;	
	private String dsHorario;
	private BigInteger nmHorario;
	private String hrInicio;
	private String hrFim;
	private Boolean fgAtivo;
	
	public BigInteger getIdEstabelecimentoHorario() {
		return idEstabelecimentoHorario;
	}
	public void setIdEstabelecimentoHorario(BigInteger idEstabelecimentoHorario) {
		this.idEstabelecimentoHorario = idEstabelecimentoHorario;
	}
	public BigInteger getIdEstabelecimento() {
		return idEstabelecimento;
	}
	public void setIdEstabelecimento(BigInteger idEstabelecimento) {
		this.idEstabelecimento = idEstabelecimento;
	}
	public String getDsHorario() {
		return dsHorario;
	}
	public void setDsHorario(String dsHorario) {
		this.dsHorario = dsHorario;
	}
	public BigInteger getNmHorario() {
		return nmHorario;
	}
	public void setNmHorario(BigInteger nmHorario) {
		this.nmHorario = nmHorario;
	}
	public String getHrInicio() {
		return hrInicio;
	}
	public void setHrInicio(String hrInicio) {
		this.hrInicio = hrInicio;
	}
	public String getHrFim() {
		return hrFim;
	}
	public void setHrFim(String hrFim) {
		this.hrFim = hrFim;
	}
	public Boolean getFgAtivo() {
		return fgAtivo;
	}
	public void setFgAtivo(Boolean fgAtivo) {
		this.fgAtivo = fgAtivo;
	}
}
