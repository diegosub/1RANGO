package br.com.rango.vo;

import java.io.Serializable;
import java.util.List;

public class CardapioVO implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private EstabelecimentoVO estabelecimento;
	
	private List<SegmentoVO> listaSegmento;
	
	public EstabelecimentoVO getEstabelecimento() {
		return estabelecimento;
	}

	public void setEstabelecimento(EstabelecimentoVO estabelecimento) {
		this.estabelecimento = estabelecimento;
	}

	public List<SegmentoVO> getListaSegmento() {
		return listaSegmento;
	}

	public void setListaSegmento(List<SegmentoVO> listaSegmento) {
		this.listaSegmento = listaSegmento;
	}
}
