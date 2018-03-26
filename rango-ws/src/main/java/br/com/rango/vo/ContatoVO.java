package br.com.rango.vo;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

public class ContatoVO implements Serializable
{
	private static final long serialVersionUID = 1L;

	private BigInteger idContato;
	private String dsNome;
	private String dsEmail;
	private String dsMensagem;
	private String fgLido;
	private String mensagem;
	private Date dtCadastro;
	
	public BigInteger getIdContato() {
		return idContato;
	}
	public void setIdContato(BigInteger idContato) {
		this.idContato = idContato;
	}
	public String getDsNome() {
		return dsNome;
	}
	public void setDsNome(String dsNome) {
		this.dsNome = dsNome;
	}
	public String getDsEmail() {
		return dsEmail;
	}
	public void setDsEmail(String dsEmail) {
		this.dsEmail = dsEmail;
	}
	public String getDsMensagem() {
		return dsMensagem;
	}
	public void setDsMensagem(String dsMensagem) {
		this.dsMensagem = dsMensagem;
	}
	public String getFgLido() {
		return fgLido;
	}
	public void setFgLido(String fgLido) {
		this.fgLido = fgLido;
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
}
