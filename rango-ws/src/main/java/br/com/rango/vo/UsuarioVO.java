package br.com.rango.vo;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

public class UsuarioVO implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private BigInteger idUsuario;
	private String dsNome;
	private String dsLogin;
	private String dsSenha;
	private String dsEmail;
	private String dsCpf;
	private String nmTelefone;
	private Date dtNascimento;
	
	private String dsNovaSenha;
	private String dsConfirmaNovaSenha;
	
	private String mensagem;
	
	
	public BigInteger getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(BigInteger idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getDsNome() {
		return dsNome;
	}
	public void setDsNome(String dsNome) {
		this.dsNome = dsNome;
	}
	public String getDsLogin() {
		return dsLogin;
	}
	public void setDsLogin(String dsLogin) {
		this.dsLogin = dsLogin;
	}
	public String getDsSenha() {
		return dsSenha;
	}
	public void setDsSenha(String dsSenha) {
		this.dsSenha = dsSenha;
	}
	public String getDsEmail() {
		return dsEmail;
	}
	public void setDsEmail(String dsEmail) {
		this.dsEmail = dsEmail;
	}
	public String getDsCpf() {
		return dsCpf;
	}
	public void setDsCpf(String dsCpf) {
		this.dsCpf = dsCpf;
	}
	public String getNmTelefone() {
		return nmTelefone;
	}
	public void setNmTelefone(String nmTelefone) {
		this.nmTelefone = nmTelefone;
	}
	public Date getDtNascimento() {
		return dtNascimento;
	}
	public void setDtNascimento(Date dtNascimento) {
		this.dtNascimento = dtNascimento;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public String getDsNovaSenha() {
		return dsNovaSenha;
	}
	public void setDsNovaSenha(String dsNovaSenha) {
		this.dsNovaSenha = dsNovaSenha;
	}
	public String getDsConfirmaNovaSenha() {
		return dsConfirmaNovaSenha;
	}
	public void setDsConfirmaNovaSenha(String dsConfirmaNovaSenha) {
		this.dsConfirmaNovaSenha = dsConfirmaNovaSenha;
	}
}
