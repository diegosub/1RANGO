package br.com.rango.ws;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hibernate.Criteria;

import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.GregorianCalendar;

import br.com.rango.ngc.entity.Estabelecimento;
import br.com.rango.ngc.entity.EstabelecimentoHorario;
import br.com.rango.ngc.entity.EstabelecimentoImagem;
import br.com.rango.ngc.entity.Parametro;
import br.com.rango.ngc.entity.Produto;
import br.com.rango.ngc.entity.Segmento;
import br.com.rango.ngc.service.EstabelecimentoService;
import br.com.rango.ngc.service.ParametroService;
import br.com.rango.ngc.service.SegmentoService;
import br.com.rango.ngc.util.jsf.JSFUtil;
import br.com.rango.ngc.util.outros.Utilitarios;
import br.com.rango.vo.CardapioVO;
import br.com.rango.vo.EstabelecimentoVO;
import br.com.rango.vo.ProdutoVO;
import br.com.rango.vo.SegmentoVO;



@Path("/CardapioWS")
public class CardapioWS
{
	@POST
	@Path("/getCardapio")	
	@Produces(MediaType.APPLICATION_JSON)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public CardapioVO getCardapio(@FormParam("idEstabelecimento") Integer idEstabelecimento)
	{
		CardapioVO cardapio = null;
		EstabelecimentoVO estabVO = null;
	
		try
		{
			Parametro parametro = new Parametro();
			parametro.setDsParametro("DIR_SERVIDOR_IMG");
			parametro = ParametroService.getInstancia().get(parametro, 0);
			
			GregorianCalendar gc = new GregorianCalendar();
			int dia = gc.get(Calendar.DAY_OF_WEEK);
			
			Estabelecimento estabelecimento = new Estabelecimento();
			estabelecimento.setIdEstabelecimento(new BigInteger(Integer.toString(idEstabelecimento)));
			estabelecimento.setFiltroMap(new HashMap<String, Object>());
			estabelecimento.getFiltroMap().put("nmHorario", new BigInteger(Integer.toString(dia)));

			estabelecimento = EstabelecimentoService.getInstancia().get(estabelecimento, EstabelecimentoService.JOIN_CATEGORIA
																					   | EstabelecimentoService.JOIN_ESTABELECIMENTO_IMAGEM
																					   | EstabelecimentoService.JOIN_ESTABELECIMENTO_HORARIO, Criteria.DISTINCT_ROOT_ENTITY);
			
			if(estabelecimento != null)
			{
				cardapio = new CardapioVO();
				estabVO = new EstabelecimentoVO();
				
				JSFUtil.copiarPropriedades(estabelecimento, estabVO);
				estabVO.setDsCategoria(estabelecimento.getCategoria().getDsCategoria());
				cardapio.setEstabelecimento(estabVO);
				
				cardapio.getEstabelecimento().setDsClassificacao(new DecimalFormat("#,##0.0").format(estabelecimento.getVlClassificacao()));
						
				//DELIVERY
				if(cardapio.getEstabelecimento().getFgDelivery() != null
						&& cardapio.getEstabelecimento().getFgDelivery().equals("S"))
				{
					if(cardapio.getEstabelecimento().getVlDuracaoDeliveryInicio().intValue() == cardapio.getEstabelecimento().getVlDuracaoDeliveryFim().intValue())
					{
						cardapio.getEstabelecimento().setDsDelivery(cardapio.getEstabelecimento().getVlDuracaoDeliveryFim() + " min");
					}
					else
					{
						cardapio.getEstabelecimento().setDsDelivery(cardapio.getEstabelecimento().getVlDuracaoDeliveryInicio() + " ~ " + cardapio.getEstabelecimento().getVlDuracaoDeliveryFim() + " min");
					}
				}
				else
				{
					cardapio.getEstabelecimento().setDsDelivery("-");
				}
				
				//RETIRADA
				if(cardapio.getEstabelecimento().getFgRetiradaLocal() != null
						&& cardapio.getEstabelecimento().getFgRetiradaLocal().equals("S"))
				{
					if(cardapio.getEstabelecimento().getVlDuracaoRetiradaInicio().intValue() == cardapio.getEstabelecimento().getVlDuracaoRetiradaFim().intValue())
					{
						cardapio.getEstabelecimento().setDsRetirada(cardapio.getEstabelecimento().getVlDuracaoRetiradaFim() + " min");
					}
					else
					{
						cardapio.getEstabelecimento().setDsRetirada(cardapio.getEstabelecimento().getVlDuracaoRetiradaInicio() + " ~ " + cardapio.getEstabelecimento().getVlDuracaoRetiradaFim() + " min");
					}
				}
				else
				{
					cardapio.getEstabelecimento().setDsRetirada("-");
				}
				
				//PEDIDO MÍNIMO
				if(cardapio.getEstabelecimento().getVlMinimo() != null)
				{
					cardapio.getEstabelecimento().setDsVlMinimo("R$ " + new DecimalFormat("#,##0.00").format(cardapio.getEstabelecimento().getVlMinimo()));
				}
				else
				{
					cardapio.getEstabelecimento().setDsVlMinimo("R$ 0,00");
				}
				
				//TAXA DE ENTREGA
				if(cardapio.getEstabelecimento().getFgDelivery() != null
						&& cardapio.getEstabelecimento().getFgDelivery().equals("S"))
				{
					if(cardapio.getEstabelecimento().getVlTaxaEntregaInicio().intValue() == cardapio.getEstabelecimento().getVlTaxaEntregaFim().intValue())
					{
						cardapio.getEstabelecimento().setDsEntrega(new DecimalFormat("#,##0.00").format(cardapio.getEstabelecimento().getVlTaxaEntregaFim()));
					}
					else
					{
						cardapio.getEstabelecimento().setDsEntrega(new DecimalFormat("#,##0.00").format(cardapio.getEstabelecimento().getVlTaxaEntregaInicio()) + " ~ " + new DecimalFormat("#,##0.00").format(cardapio.getEstabelecimento().getVlTaxaEntregaFim()));
					}
				}
				else
				{
					cardapio.getEstabelecimento().setDsEntrega("-");
				}
				
				
				
				//STATUS
				estabelecimento.setListaEstabelecimentoHorario(new ArrayList<EstabelecimentoHorario>(estabelecimento.getSetEstabelecimentoHorario()));
				
				cardapio.getEstabelecimento().setDsStatus("Fechado");
				cardapio.getEstabelecimento().setDsIcone("offline.ico");
				if(estabelecimento.getListaEstabelecimentoHorario() != null
						&& estabelecimento.getListaEstabelecimentoHorario().size() > 0)
				{
					EstabelecimentoHorario horario = estabelecimento.getListaEstabelecimentoHorario().get(0);
					Date hoje = new Date();
					
					GregorianCalendar gc1 = new GregorianCalendar();
					gc1.set(Calendar.HOUR_OF_DAY, Integer.parseInt(horario.getHrInicio().split(":")[0]));
					gc1.set(Calendar.MINUTE, Integer.parseInt(horario.getHrInicio().split(":")[1]));
					gc1.set(Calendar.SECOND, 0);
					gc1.set(Calendar.MILLISECOND, 0);
					
					Date dataInicio = gc1.getTime();
					
					GregorianCalendar gc2 = new GregorianCalendar();
					
					if(Utilitarios.horaInicioMaior(horario.getHrInicio(), horario.getHrFim()))
					{					
						gc2.add(Calendar.DAY_OF_MONTH, 1);									
					}
					
					gc2.set(Calendar.HOUR_OF_DAY, Integer.parseInt(horario.getHrFim().split(":")[0]));
					gc2.set(Calendar.MINUTE, Integer.parseInt(horario.getHrFim().split(":")[1]));
					gc2.set(Calendar.SECOND, 0);
					gc2.set(Calendar.MILLISECOND, 0);
					
					Date dataFim = gc2.getTime();
					
					if(estabelecimento.getFgStatus().equals("A")
							&& hoje.after(dataInicio)
							&& hoje.before(dataFim))
					{
						cardapio.getEstabelecimento().setDsStatus("Aberto");
						cardapio.getEstabelecimento().setDsIcone("online.ico");
					}
				}
									
				//IMAGEM
				estabelecimento.setListaEstabelecimentoImagem(new ArrayList<EstabelecimentoImagem>(estabelecimento.getSetEstabelecimentoImagem()));
				
				if(estabelecimento.getListaEstabelecimentoImagem() != null
						&& estabelecimento.getListaEstabelecimentoImagem().size() > 0)
				{
					cardapio.getEstabelecimento().setDsImagem(parametro.getVlParametro() + estabelecimento.getListaEstabelecimentoImagem().get(0).getDsImagemCompleta()+".jpg");
				}
				else
				{
					cardapio.getEstabelecimento().setDsImagem(parametro.getVlParametro() + "sem-foto.gif");
				}

				
				Segmento segmento = new Segmento();
	    		segmento.setIdEstabelecimento(estabelecimento.getIdEstabelecimento());
	    		segmento.setFgAtivo("S");
	    		segmento.setFiltroMap(new HashMap<String, Object>());
	    		segmento.getFiltroMap().put("idDia", new BigInteger(Integer.toString(dia)));
	    		segmento.getFiltroMap().put("fgAtivoProduto", "S");
	    		 
	    		 List<Segmento> lista = SegmentoService.getInstancia().pesquisar(segmento, SegmentoService.JOIN_PRODUTO
	    				 																 | SegmentoService.JOIN_SEGMENTO_DIA, Criteria.DISTINCT_ROOT_ENTITY);
	    		 
	    		 if(lista != null
	    				 && lista.size() > 0)
	    		 {
	    			 cardapio.setListaSegmento(new ArrayList<SegmentoVO>());
	    			 
	    			 for (Segmento obj : lista)
	    			 {
		    			 SegmentoVO segmentoVO = new SegmentoVO();
		    			 JSFUtil.copiarPropriedades(obj, segmentoVO);		    			 
		    			 
		    			 if(obj.getSetProduto() != null
		    					 && obj.getSetProduto().size() > 0)
		    			 {
		    				 obj.setListaProduto(new ArrayList<Produto>(obj.getSetProduto()));
		    				 segmentoVO.setListaProduto(new ArrayList<ProdutoVO>());
		    				 
		    				 for (Produto objProduto : obj.getListaProduto())
			    			 {
								ProdutoVO produtoVO = new ProdutoVO();
								JSFUtil.copiarPropriedades(objProduto, produtoVO);
								produtoVO.setDsVlrProduto(new DecimalFormat("#,##0.00").format(produtoVO.getVlProduto()));
								
								segmentoVO.getListaProduto().add(produtoVO);
								
								Collections.sort (segmentoVO.getListaProduto(), new Comparator() {
			 			            public int compare(Object o1, Object o2) {
			 			                ProdutoVO p1 = (ProdutoVO) o1;
			 			                ProdutoVO p2 = (ProdutoVO) o2;
			 			                return p1.getDsProduto().compareToIgnoreCase(p2.getDsProduto());
			 			            }
			 			        });
			    			 }
		    			 }
		    			 
		    			 cardapio.getListaSegmento().add(segmentoVO);
	    			 }	    			   		 
	    		 }
			}
			
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		return cardapio;
	}
}
