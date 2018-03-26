package br.com.rango.ngc.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Proxy;

/** 
 * @author Carlos Diego
 * @since 21/01/2018
 */

@Entity
@Table(name = "tb_pedido", schema="rng")
@SequenceGenerator(name = "SQ_PEDIDO", sequenceName = "SQ_PEDIDO", allocationSize = 1)
@Proxy(lazy = true)
public class Pedido implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_PEDIDO")
	@Column(name = "id_pedido")
	private BigInteger idPedido;
	
	@Column(name = "id_estabelecimento")
	private BigInteger idEstabelecimento;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_estabelecimento", insertable = false, updatable = false)
	private Estabelecimento estabelecimento;
	
	@Column(name = "id_usuario")
	private BigInteger idUsuario;
	
	@Column(name = "id_status")
	private BigInteger idStatus;
	
	@Column(name = "id_tipo_pedido")
	private BigInteger idTipoPedido;
	
	@Column(name = "id_forma_pagamento")
	private BigInteger idFormaPagamento;
	
	@Column(name = "id_cartao")
	private BigInteger idCartao;
	
	@Column(name = "id_endereco")
	private BigInteger idEndereco;
	
	@Column(name = "id_bairro")
	private BigInteger idBairro;
	
	@Column(name = "vl_total_pedido")
	private Double vlTotalPedido;
	
	@Column(name = "vl_pagamento")
	private Double vlPagamento;
	
	@Column(name = "vl_taxa_entrega")
	private Double vlTaxaEntrega;
	
	@Column(name = "ds_observacao")
	private String dsObservacao;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_cadastro")
	private Date dtCadastro;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_recebimento")
	private Date dtRecebimento;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_conclusao")
	private Date dtConclusao;
	
	@OneToMany(mappedBy="pedido", fetch = FetchType.LAZY)
	private List<PedidoProduto> listaPedidoProduto;
	
	@Formula("(select d.ds_dominio from rng.tb_dominio d where d.vl_dominio = id_status::varchar and d.nm_campo = 'STATUS_PEDIDO')")
	private String dsStatus;
	

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

	public List<PedidoProduto> getListaPedidoProduto() {
		return listaPedidoProduto;
	}

	public void setListaPedidoProduto(List<PedidoProduto> listaPedidoProduto) {
		this.listaPedidoProduto = listaPedidoProduto;
	}

	public Estabelecimento getEstabelecimento() {
		return estabelecimento;
	}

	public void setEstabelecimento(Estabelecimento estabelecimento) {
		this.estabelecimento = estabelecimento;
	}

	public String getDsStatus() {
		return dsStatus;
	}

	public void setDsStatus(String dsStatus) {
		this.dsStatus = dsStatus;
	}
}
