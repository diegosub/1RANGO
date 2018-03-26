package br.com.rango.vo;

import java.io.Serializable;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PedidoVO implements Serializable
{
	private static final long serialVersionUID = 1L;

	private BigInteger idPedido;
	private BigInteger idEstabelecimento;
	private String dsEstabelecimento;
	private BigInteger idUsuario;
	private BigInteger idStatus;
	private String dsStatus;
	private BigInteger idTipoPedido;
	private BigInteger idFormaPagamento;
	private BigInteger idCartao;
	private BigInteger idEndereco;
	private BigInteger idBairro;
	private Double vlTotalPedido;
	private Double vlPagamento;
	private Double vlTaxaEntrega;
	private String dsObservacao;
	private Date dtCadastro;
	private Date dtRecebimento;
	private Date dtConclusao;
	private String mensagem;	
	
	private String strDtCadastro;
	
	private List<PedidoProdutoVO> listaPedidoProduto;
	
	
	public BigInteger getIdPedido() {
		return idPedido;
	}
	public void setIdPedido(BigInteger idPedido) {
		this.idPedido = idPedido;
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
	public BigInteger getIdStatus() {
		return idStatus;
	}
	public void setIdStatus(BigInteger idStatus) {
		this.idStatus = idStatus;
	}
	public BigInteger getIdTipoPedido() {
		return idTipoPedido;
	}
	public void setIdTipoPedido(BigInteger idTipoPedido) {
		this.idTipoPedido = idTipoPedido;
	}
	public BigInteger getIdFormaPagamento() {
		return idFormaPagamento;
	}
	public void setIdFormaPagamento(BigInteger idFormaPagamento) {
		this.idFormaPagamento = idFormaPagamento;
	}
	public BigInteger getIdCartao() {
		return idCartao;
	}
	public void setIdCartao(BigInteger idCartao) {
		this.idCartao = idCartao;
	}
	public BigInteger getIdEndereco() {
		return idEndereco;
	}
	public void setIdEndereco(BigInteger idEndereco) {
		this.idEndereco = idEndereco;
	}
	public BigInteger getIdBairro() {
		return idBairro;
	}
	public void setIdBairro(BigInteger idBairro) {
		this.idBairro = idBairro;
	}
	public Double getVlTotalPedido() {
		return vlTotalPedido;
	}
	public void setVlTotalPedido(Double vlTotalPedido) {
		this.vlTotalPedido = vlTotalPedido;
	}
	public Double getVlPagamento() {
		return vlPagamento;
	}
	public void setVlPagamento(Double vlPagamento) {
		this.vlPagamento = vlPagamento;
	}
	public Double getVlTaxaEntrega() {
		return vlTaxaEntrega;
	}
	public void setVlTaxaEntrega(Double vlTaxaEntrega) {
		this.vlTaxaEntrega = vlTaxaEntrega;
	}
	public String getDsObservacao() {
		return dsObservacao;
	}
	public void setDsObservacao(String dsObservacao) {
		this.dsObservacao = dsObservacao;
	}
	public Date getDtCadastro() {
		return dtCadastro;
	}
	public void setDtCadastro(Date dtCadastro) {
		this.dtCadastro = dtCadastro;
	}
	public Date getDtRecebimento() {
		return dtRecebimento;
	}
	public void setDtRecebimento(Date dtRecebimento) {
		this.dtRecebimento = dtRecebimento;
	}
	public Date getDtConclusao() {
		return dtConclusao;
	}
	public void setDtConclusao(Date dtConclusao) {
		this.dtConclusao = dtConclusao;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public List<PedidoProdutoVO> getListaPedidoProduto() {
		return listaPedidoProduto;
	}
	public void setListaPedidoProduto(List<PedidoProdutoVO> listaPedidoProduto) {
		this.listaPedidoProduto = listaPedidoProduto;
	}
	public String getStrDtCadastro() {		
		this.strDtCadastro = new SimpleDateFormat("dd/MM/yyyy hh:mm").format(this.getDtCadastro());
		return this.strDtCadastro;
	}
	public void setStrDtCadastro(String strDtCadastro) {
		this.strDtCadastro = strDtCadastro;
	}
	public String getDsEstabelecimento() {
		return dsEstabelecimento;
	}
	public void setDsEstabelecimento(String dsEstabelecimento) {
		this.dsEstabelecimento = dsEstabelecimento;
	}
	public String getDsStatus() {
		return dsStatus;
	}
	public void setDsStatus(String dsStatus) {
		this.dsStatus = dsStatus;
	}
}
