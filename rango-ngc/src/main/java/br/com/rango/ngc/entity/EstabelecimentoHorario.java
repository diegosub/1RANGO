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
 * @since 30/11/2017
 */

@Entity
@Table(name = "tb_estabelecimento_horario", schema="rng")
@SequenceGenerator(name = "SQ_ESTABELECIMENTO_HORARIO", sequenceName = "SQ_ESTABELECIMENTO_HORARIO", allocationSize = 1)
@Proxy(lazy = true)
public class EstabelecimentoHorario implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_ESTABELECIMENTO_HORARIO")
	@Column(name = "id_estabelecimento_horario")
	private BigInteger idEstabelecimentoHorario;
	
	@Column(name = "id_estabelecimento")
	private BigInteger idEstabelecimento;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_estabelecimento", insertable = false, updatable = false)
	private Estabelecimento estabelecimento;

	@Column(name = "ds_horario")
	private String dsHorario;
	
	@Column(name = "nm_horario")
	private BigInteger nmHorario;
	
	@Column(name = "hr_inicio")
	private String hrInicio;
	
	@Column(name = "hr_fim")
	private String hrFim;

	@Column(name = "fg_ativo")
	private Boolean fgAtivo;
	
	public BigInteger getIdEstabelecimentoHorario() {
		return idEstabelecimentoHorario;
	}

	public void setIdEstabelecimentoHorario(BigInteger idEstabelecimentoHorario) {
		this.idEstabelecimentoHorario = idEstabelecimentoHorario;
	}

	public BigInteger getIdEstabelecimento() {
		return idEstabelecimento;
	}

	public void setIdEstabelecimento(BigInteger idEstabelecimento) {
		this.idEstabelecimento = idEstabelecimento;
	}

	public Estabelecimento getEstabelecimento() {
		return estabelecimento;
	}

	public void setEstabelecimento(Estabelecimento estabelecimento) {
		this.estabelecimento = estabelecimento;
	}

	public String getDsHorario() {
		return dsHorario;
	}

	public void setDsHorario(String dsHorario) {
		this.dsHorario = dsHorario;
	}

	public BigInteger getNmHorario() {
		return nmHorario;
	}

	public void setNmHorario(BigInteger nmHorario) {
		this.nmHorario = nmHorario;
	}

	public String getHrInicio() {
		return hrInicio;
	}

	public void setHrInicio(String hrInicio) {
		this.hrInicio = hrInicio;
	}

	public String getHrFim() {
		return hrFim;
	}

	public void setHrFim(String hrFim) {
		this.hrFim = hrFim;
	}

	public Boolean getFgAtivo() {
		return fgAtivo;
	}

	public void setFgAtivo(Boolean fgAtivo) {
		this.fgAtivo = fgAtivo;
	}
}
