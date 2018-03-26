package br.com.rango.vo;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

public class EnderecoVO implements Serializable
{
	private static final long serialVersionUID = 1L;

	private BigInteger idEndereco;
	private BigInteger idUsuario;
	private String dsEndereco;
	private String dsCep;
	private String dsRua;
	private String dsNumero;
	private String dsComplemento;
	private String dsBairro;
	private String dsCidade;
	private BigInteger idEstado;
	private String dsReferencia;
	private Date dtCadastro;
	private String mensagem;
	private String siglaEstado;
	
	public BigInteger getIdEndereco() {
		return idEndereco;
	}
	public void setIdEndereco(BigInteger idEndereco) {
		this.idEndereco = idEndereco;
	}
	public BigInteger getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(BigInteger idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getDsEndereco() {
		return dsEndereco;
	}
	public void setDsEndereco(String dsEndereco) {
		this.dsEndereco = dsEndereco;
	}
	public String getDsCep() {
		return dsCep;
	}
	public void setDsCep(String dsCep) {
		this.dsCep = dsCep;
	}
	public String getDsRua() {
		return dsRua;
	}
	public void setDsRua(String dsRua) {
		this.dsRua = dsRua;
	}
	public String getDsNumero() {
		return dsNumero;
	}
	public void setDsNumero(String dsNumero) {
		this.dsNumero = dsNumero;
	}
	public String getDsComplemento() {
		return dsComplemento;
	}
	public void setDsComplemento(String dsComplemento) {
		this.dsComplemento = dsComplemento;
	}
	public String getDsBairro() {
		return dsBairro;
	}
	public void setDsBairro(String dsBairro) {
		this.dsBairro = dsBairro;
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
	public String getDsReferencia() {
		return dsReferencia;
	}
	public void setDsReferencia(String dsReferencia) {
		this.dsReferencia = dsReferencia;
	}
	public Date getDtCadastro() {
		return dtCadastro;
	}
	public void setDtCadastro(Date dtCadastro) {
		this.dtCadastro = dtCadastro;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public String getSiglaEstado() {
		return siglaEstado;
	}
	public void setSiglaEstado(String siglaEstado) {
		this.siglaEstado = siglaEstado;
	}
}
