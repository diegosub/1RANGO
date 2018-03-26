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
import javax.persistence.Transient;

import org.hibernate.annotations.Proxy;

/** 
 * @author Carlos Diego
 * @since 22/11/2017
 */

@Entity
@Table(name = "tb_segmento_item", schema="rng")
@SequenceGenerator(name = "SQ_SEGMENTO_ITEM", sequenceName = "SQ_SEGMENTO_ITEM", allocationSize = 1)
@Proxy(lazy = true)
public class SegmentoItem implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_SEGMENTO_ITEM")
	@Column(name = "id_segmento_item")
	private BigInteger idSegmentoItem;
	
	@Column(name = "id_segmento_adicional")
	private BigInteger idSegmentoAdicional;
	
	@Column(name = "ds_segmento_item")
	private String dsSegmentoItem;
	
	@Column(name = "vl_adicional")
	private Double vlAdicional;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_segmento_adicional", insertable = false, updatable = false)
	private SegmentoAdicional segmentoAdicional;
	
	@Transient
	private String strVlAdicional;

	public BigInteger getIdSegmentoItem() {
		return idSegmentoItem;
	}

	public void setIdSegmentoItem(BigInteger idSegmentoItem) {
		this.idSegmentoItem = idSegmentoItem;
	}

	public BigInteger getIdSegmentoAdicional() {
		return idSegmentoAdicional;
	}

	public void setIdSegmentoAdicional(BigInteger idSegmentoAdicional) {
		this.idSegmentoAdicional = idSegmentoAdicional;
	}

	public String getDsSegmentoItem() {
		return dsSegmentoItem;
	}

	public void setDsSegmentoItem(String dsSegmentoItem) {
		this.dsSegmentoItem = dsSegmentoItem;
	}

	public Double getVlAdicional() {
		return vlAdicional;
	}

	public void setVlAdicional(Double vlAdicional) {
		this.vlAdicional = vlAdicional;
	}

	public String getStrVlAdicional() {
		return strVlAdicional;
	}

	public void setStrVlAdicional(String strVlAdicional) {
		this.strVlAdicional = strVlAdicional;
	}

	public SegmentoAdicional getSegmentoAdicional() {
		return segmentoAdicional;
	}

	public void setSegmentoAdicional(SegmentoAdicional segmentoAdicional) {
		this.segmentoAdicional = segmentoAdicional;
	}
}
