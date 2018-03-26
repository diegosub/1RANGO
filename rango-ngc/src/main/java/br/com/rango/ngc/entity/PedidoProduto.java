package br.com.rango.ngc.entity;

import java.io.Serializable;
import java.math.BigInteger;
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

import org.hibernate.annotations.Proxy;

/** 
 * @author Carlos Diego
 * @since 21/01/2018
 */

@Entity
@Table(name = "tb_pedido_produto", schema="rng")
@SequenceGenerator(name = "SQ_PEDIDO_PRODUTO", sequenceName = "SQ_PEDIDO_PRODUTO", allocationSize = 1)
@Proxy(lazy = true)
public class PedidoProduto implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_PEDIDO_PRODUTO")
	@Column(name = "id_pedido_produto")
	private BigInteger idPedidoProduto;
	
	@Column(name = "id_pedido")
	private BigInteger idPedido;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_pedido", insertable = false, updatable = false)
	private Pedido pedido;
	
	@Column(name = "id_produto")
	private BigInteger idProduto;
	
	@Column(name = "ds_produto")
	private String dsProduto;
	
	@Column(name = "qt_produto")
	private BigInteger qtProduto;
	
	@Column(name = "vl_produto")
	private Double vlProduto;
	
	@Column(name = "ds_observacao")
	private String dsObservacao;
	
	@OneToMany(mappedBy="pedidoProduto", fetch = FetchType.LAZY)
	private List<PedidoProdutoAdicional> listaPedidoProdutoAdicional;
	

	public BigInteger getIdPedidoProduto() {
		return idPedidoProduto;
	}

	public void setIdPedidoProduto(BigInteger idPedidoProduto) {
		this.idPedidoProduto = idPedidoProduto;
	}

	public BigInteger getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(BigInteger idPedido) {
		this.idPedido = idPedido;
	}

	public BigInteger getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(BigInteger idProduto) {
		this.idProduto = idProduto;
	}

	public String getDsProduto() {
		return dsProduto;
	}

	public void setDsProduto(String dsProduto) {
		this.dsProduto = dsProduto;
	}

	public BigInteger getQtProduto() {
		return qtProduto;
	}

	public void setQtProduto(BigInteger qtProduto) {
		this.qtProduto = qtProduto;
	}

	public Double getVlProduto() {
		return vlProduto;
	}

	public void setVlProduto(Double vlProduto) {
		this.vlProduto = vlProduto;
	}

	public String getDsObservacao() {
		return dsObservacao;
	}

	public void setDsObservacao(String dsObservacao) {
		this.dsObservacao = dsObservacao;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public List<PedidoProdutoAdicional> getListaPedidoProdutoAdicional() {
		return listaPedidoProdutoAdicional;
	}

	public void setListaPedidoProdutoAdicional(List<PedidoProdutoAdicional> listaPedidoProdutoAdicional) {
		this.listaPedidoProdutoAdicional = listaPedidoProdutoAdicional;
	}
}
