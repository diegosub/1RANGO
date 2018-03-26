package br.com.rango.ngc.entity;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

/** 
 * @author Carlos Diego
 * @since 21/01/2018
 */

@Entity
@Table(name = "tb_pedido_produto_adicional", schema="rng")
@SequenceGenerator(name = "SQ_PEDIDO_PRODUTO_ADICIONAL", sequenceName = "SQ_PEDIDO_PRODUTO_ADICIONAL", allocationSize = 1)
@Proxy(lazy = true)
public class PedidoProdutoAdicional implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_PEDIDO_PRODUTO_ADICIONAL")
	@Column(name = "id_pedido_produto_adicional")
	private BigInteger idPedidoProdutoAdicional;
	
	@Column(name = "id_pedido_produto")
	private BigInteger idPedidoProduto;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_pedido_produto", insertable = false, updatable = false)
	private Pedido pedidoProduto;
	
	@Column(name = "ds_adicional")
	private String dsAdicional;
	
	@Column(name = "vl_adicional")
	private Double vlAdicional;
	

	public BigInteger getIdPedidoProdutoAdicional() {
		return idPedidoProdutoAdicional;
	}

	public void setIdPedidoProdutoAdicional(BigInteger idPedidoProdutoAdicional) {
		this.idPedidoProdutoAdicional = idPedidoProdutoAdicional;
	}

	public BigInteger getIdPedidoProduto() {
		return idPedidoProduto;
	}

	public void setIdPedidoProduto(BigInteger idPedidoProduto) {
		this.idPedidoProduto = idPedidoProduto;
	}

	public String getDsAdicional() {
		return dsAdicional;
	}

	public void setDsAdicional(String dsAdicional) {
		this.dsAdicional = dsAdicional;
	}

	public Double getVlAdicional() {
		return vlAdicional;
	}

	public void setVlAdicional(Double vlAdicional) {
		this.vlAdicional = vlAdicional;
	}

	public Pedido getPedidoProduto() {
		return pedidoProduto;
	}

	public void setPedidoProduto(Pedido pedidoProduto) {
		this.pedidoProduto = pedidoProduto;
	}
}
