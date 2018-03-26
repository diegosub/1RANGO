package br.com.rango.vo;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

public class FavoritoVO implements Serializable
{
	private static final long serialVersionUID = 1L;

	private BigInteger idFavorito;
	private BigInteger idEstabelecimento;
	private BigInteger idUsuario;
	private Date dtCadastro;
	private String strDtCadastro;
	private String mensagem;
	
	
	public BigInteger getIdFavorito() {
		return idFavorito;
	}
	public void setIdFavorito(BigInteger idFavorito) {
		this.idFavorito = idFavorito;
	}
	public BigInteger getIdEstabelecimento() {
		return idEstabelecimento;
	}
	public void setIdEstabelecimento(BigInteger idEstabelecimento) {
		this.idEstabelecimento = idEstabelecimento;
	}
	public BigInteger getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(BigInteger idUsuario) {
		this.idUsuario = idUsuario;
	}
	public Date getDtCadastro() {
		return dtCadastro;
	}
	public void setDtCadastro(Date dtCadastro) {
		this.dtCadastro = dtCadastro;
	}
	public String getStrDtCadastro() {
		return strDtCadastro;
	}
	public void setStrDtCadastro(String strDtCadastro) {
		this.strDtCadastro = strDtCadastro;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
}
