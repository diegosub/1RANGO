package br.com.rango.vo;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

public class EstabelecimentoVO implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private BigInteger idEstabelecimento;	
	private BigInteger idCidade;	
	private BigInteger idCategoria;	
	private String dsEstabelecimento;
	private String fgAtivo;
	private String dsFantasia;	
	private BigInteger vlDuracaoDeliveryInicio;
	private BigInteger vlDuracaoDeliveryFim;
	private BigInteger vlDuracaoRetiradaInicio;
	private BigInteger vlDuracaoRetiradaFim;	
	private Double vlTaxaEntregaInicio;	
	private Double vlTaxaEntregaFim;	
	private String fgDelivery;	
	private String fgRetiradaLocal;	
	private String fgDinheiroDelivery;	
	private String fgCartaoDelivery;
	private String fgDinheiroRetirada;	
	private String fgCartaoRetirada;
	private Double vlMinimo;
	private String dsImagem;
	private String dsDuracao;
	private String dsCategoria;
	private String dsStatus;
	private String dsIcone;
	private String dsClassificacao;
	private String dsCidade;
	
	private String dsTpEntrega;
	private String dsFormaPagamentoDelivery;
	private String dsFormaPagamentoRetirada;
	private String dsBandeiraDelivery;
	private String dsBandeiraRetirada;
	
	private String dsDelivery;
	private String dsRetirada;
	private String dsVlMinimo;
	private String dsEntrega;
	private String dsFavorito;
	
	private List<HorarioVO> listaHorario;	
	
	
	public BigInteger getIdEstabelecimento() {
		return idEstabelecimento;
	}
	public void setIdEstabelecimento(BigInteger idEstabelecimento) {
		this.idEstabelecimento = idEstabelecimento;
	}
	public BigInteger getIdCidade() {
		return idCidade;
	}
	public void setIdCidade(BigInteger idCidade) {
		this.idCidade = idCidade;
	}
	public BigInteger getIdCategoria() {
		return idCategoria;
	}
	public void setIdCategoria(BigInteger idCategoria) {
		this.idCategoria = idCategoria;
	}
	public String getDsEstabelecimento() {
		return dsEstabelecimento;
	}
	public void setDsEstabelecimento(String dsEstabelecimento) {
		this.dsEstabelecimento = dsEstabelecimento;
	}
	public String getFgAtivo() {
		return fgAtivo;
	}
	public void setFgAtivo(String fgAtivo) {
		this.fgAtivo = fgAtivo;
	}
	public String getDsFantasia() {
		return dsFantasia;
	}
	public void setDsFantasia(String dsFantasia) {
		this.dsFantasia = dsFantasia;
	}
	public BigInteger getVlDuracaoDeliveryInicio() {
		return vlDuracaoDeliveryInicio;
	}
	public void setVlDuracaoDeliveryInicio(BigInteger vlDuracaoDeliveryInicio) {
		this.vlDuracaoDeliveryInicio = vlDuracaoDeliveryInicio;
	}
	public BigInteger getVlDuracaoDeliveryFim() {
		return vlDuracaoDeliveryFim;
	}
	public void setVlDuracaoDeliveryFim(BigInteger vlDuracaoDeliveryFim) {
		this.vlDuracaoDeliveryFim = vlDuracaoDeliveryFim;
	}
	public BigInteger getVlDuracaoRetiradaInicio() {
		return vlDuracaoRetiradaInicio;
	}
	public void setVlDuracaoRetiradaInicio(BigInteger vlDuracaoRetiradaInicio) {
		this.vlDuracaoRetiradaInicio = vlDuracaoRetiradaInicio;
	}
	public BigInteger getVlDuracaoRetiradaFim() {
		return vlDuracaoRetiradaFim;
	}
	public void setVlDuracaoRetiradaFim(BigInteger vlDuracaoRetiradaFim) {
		this.vlDuracaoRetiradaFim = vlDuracaoRetiradaFim;
	}
	public Double getVlTaxaEntregaInicio() {
		return vlTaxaEntregaInicio;
	}
	public void setVlTaxaEntregaInicio(Double vlTaxaEntregaInicio) {
		this.vlTaxaEntregaInicio = vlTaxaEntregaInicio;
	}
	public Double getVlTaxaEntregaFim() {
		return vlTaxaEntregaFim;
	}
	public void setVlTaxaEntregaFim(Double vlTaxaEntregaFim) {
		this.vlTaxaEntregaFim = vlTaxaEntregaFim;
	}
	public String getFgDelivery() {
		return fgDelivery;
	}
	public void setFgDelivery(String fgDelivery) {
		this.fgDelivery = fgDelivery;
	}
	public String getFgRetiradaLocal() {
		return fgRetiradaLocal;
	}
	public void setFgRetiradaLocal(String fgRetiradaLocal) {
		this.fgRetiradaLocal = fgRetiradaLocal;
	}
	public Double getVlMinimo() {
		return vlMinimo;
	}
	public void setVlMinimo(Double vlMinimo) {
		this.vlMinimo = vlMinimo;
	}
	public String getDsImagem() {
		return dsImagem;
	}
	public void setDsImagem(String dsImagem) {
		this.dsImagem = dsImagem;
	}
	public String getDsDuracao() {
		return dsDuracao;
	}
	public void setDsDuracao(String dsDuracao) {
		this.dsDuracao = dsDuracao;
	}
	public String getDsStatus() {
		return dsStatus;
	}
	public void setDsStatus(String dsStatus) {
		this.dsStatus = dsStatus;
	}
	public String getDsCategoria() {
		return dsCategoria;
	}
	public void setDsCategoria(String dsCategoria) {
		this.dsCategoria = dsCategoria;
	}
	public String getDsIcone() {
		return dsIcone;
	}
	public void setDsIcone(String dsIcone) {
		this.dsIcone = dsIcone;
	}
	public String getDsClassificacao() {
		return dsClassificacao;
	}
	public void setDsClassificacao(String dsClassificacao) {
		this.dsClassificacao = dsClassificacao;
	}
	public String getDsCidade() {
		return dsCidade;
	}
	public void setDsCidade(String dsCidade) {
		this.dsCidade = dsCidade;
	}
	public String getDsDelivery() {
		return dsDelivery;
	}
	public void setDsDelivery(String dsDelivery) {
		this.dsDelivery = dsDelivery;
	}
	public String getDsRetirada() {
		return dsRetirada;
	}
	public void setDsRetirada(String dsRetirada) {
		this.dsRetirada = dsRetirada;
	}
	public String getDsVlMinimo() {
		return dsVlMinimo;
	}
	public void setDsVlMinimo(String dsVlMinimo) {
		this.dsVlMinimo = dsVlMinimo;
	}
	public String getDsEntrega() {
		return dsEntrega;
	}
	public void setDsEntrega(String dsEntrega) {
		this.dsEntrega = dsEntrega;
	}
	public List<HorarioVO> getListaHorario() {
		return listaHorario;
	}
	public void setListaHorario(List<HorarioVO> listaHorario) {
		this.listaHorario = listaHorario;
	}
	public String getDsTpEntrega() {
		return dsTpEntrega;
	}
	public void setDsTpEntrega(String dsTpEntrega) {
		this.dsTpEntrega = dsTpEntrega;
	}
	public String getFgDinheiroDelivery() {
		return fgDinheiroDelivery;
	}
	public void setFgDinheiroDelivery(String fgDinheiroDelivery) {
		this.fgDinheiroDelivery = fgDinheiroDelivery;
	}
	public String getFgCartaoDelivery() {
		return fgCartaoDelivery;
	}
	public void setFgCartaoDelivery(String fgCartaoDelivery) {
		this.fgCartaoDelivery = fgCartaoDelivery;
	}
	public String getFgDinheiroRetirada() {
		return fgDinheiroRetirada;
	}
	public void setFgDinheiroRetirada(String fgDinheiroRetirada) {
		this.fgDinheiroRetirada = fgDinheiroRetirada;
	}
	public String getFgCartaoRetirada() {
		return fgCartaoRetirada;
	}
	public void setFgCartaoRetirada(String fgCartaoRetirada) {
		this.fgCartaoRetirada = fgCartaoRetirada;
	}
	public String getDsFormaPagamentoDelivery() {
		return dsFormaPagamentoDelivery;
	}
	public void setDsFormaPagamentoDelivery(String dsFormaPagamentoDelivery) {
		this.dsFormaPagamentoDelivery = dsFormaPagamentoDelivery;
	}
	public String getDsFormaPagamentoRetirada() {
		return dsFormaPagamentoRetirada;
	}
	public void setDsFormaPagamentoRetirada(String dsFormaPagamentoRetirada) {
		this.dsFormaPagamentoRetirada = dsFormaPagamentoRetirada;
	}
	public String getDsBandeiraDelivery() {
		return dsBandeiraDelivery;
	}
	public void setDsBandeiraDelivery(String dsBandeiraDelivery) {
		this.dsBandeiraDelivery = dsBandeiraDelivery;
	}
	public String getDsBandeiraRetirada() {
		return dsBandeiraRetirada;
	}
	public void setDsBandeiraRetirada(String dsBandeiraRetirada) {
		this.dsBandeiraRetirada = dsBandeiraRetirada;
	}
	public String getDsFavorito() {
		return dsFavorito;
	}
	public void setDsFavorito(String dsFavorito) {
		this.dsFavorito = dsFavorito;
	}
}
