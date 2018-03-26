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
 * @since 21/11/2017
 */

@Entity
@Table(name = "tb_segmento_dia", schema="rng")
@SequenceGenerator(name = "SQ_SEGMENTO_DIA", sequenceName = "SQ_SEGMENTO_DIA", allocationSize = 1)
@Proxy(lazy = true)
public class SegmentoDia implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_SEGMENTO_DIA")
	@Column(name = "id_segmento_dia")
	private BigInteger idSegmentoDia;
	
	@Column(name = "id_segmento")
	private BigInteger idSegmento;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_segmento", insertable = false, updatable = false)
	private Segmento segmento;
		
	@Column(name = "id_dia")
	private BigInteger idDia;

	
	public BigInteger getIdSegmentoDia() {
		return idSegmentoDia;
	}

	public void setIdSegmentoDia(BigInteger idSegmentoDia) {
		this.idSegmentoDia = idSegmentoDia;
	}

	public BigInteger getIdSegmento() {
		return idSegmento;
	}

	public void setIdSegmento(BigInteger idSegmento) {
		this.idSegmento = idSegmento;
	}

	public BigInteger getIdDia() {
		return idDia;
	}

	public void setIdDia(BigInteger idDia) {
		this.idDia = idDia;
	}

	public Segmento getSegmento() {
		return segmento;
	}

	public void setSegmento(Segmento segmento) {
		this.segmento = segmento;
	}
}
