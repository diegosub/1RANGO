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
import javax.servlet.http.Part;

import org.hibernate.annotations.Proxy;

/** 
 * @author Carlos Diego
 * @since 06/12/2017
 */

@Entity
@Table(name = "tb_estabelecimento_imagem", schema="rng")
@SequenceGenerator(name = "SQ_ESTABELECIMENTO_IMAGEM", sequenceName = "SQ_ESTABELECIMENTO_IMAGEM", allocationSize = 1)
@Proxy(lazy = true)
public class EstabelecimentoImagem implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_ESTABELECIMENTO_IMAGEM")
	@Column(name = "id_estabelecimento_imagem")
	private BigInteger idEstabelecimentoImagem;
	
	@Column(name = "id_estabelecimento")
	private BigInteger idEstabelecimento;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_estabelecimento", insertable = false, updatable = false)
	private Estabelecimento estabelecimento;

	@Column(name = "ds_imagem")
	private String dsImagem;
	
	@Column(name = "ds_tipo")
	private String dsTipo;

	@Column(name = "vl_tamanho")
	private Long vlTamanho;
	
	@Column(name = "ds_imagem_completa")
	private String dsImagemCompleta;
	
	@Transient
	private Part file;
	
	@Transient
	private byte[] data;

	public BigInteger getIdEstabelecimentoImagem() {
		return idEstabelecimentoImagem;
	}

	public void setIdEstabelecimentoImagem(BigInteger idEstabelecimentoImagem) {
		this.idEstabelecimentoImagem = idEstabelecimentoImagem;
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

	public String getDsImagem() {
		return dsImagem;
	}

	public void setDsImagem(String dsImagem) {
		this.dsImagem = dsImagem;
	}

	public String getDsTipo() {
		return dsTipo;
	}

	public void setDsTipo(String dsTipo) {
		this.dsTipo = dsTipo;
	}

	public Long getVlTamanho() {
		return vlTamanho;
	}

	public void setVlTamanho(Long vlTamanho) {
		this.vlTamanho = vlTamanho;
	}

	public String getDsImagemCompleta() {
		return dsImagemCompleta;
	}

	public void setDsImagemCompleta(String dsImagemCompleta) {
		this.dsImagemCompleta = dsImagemCompleta;
	}

	public Part getFile() {
		return file;
	}

	public void setFile(Part file) {
		this.file = file;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
}
