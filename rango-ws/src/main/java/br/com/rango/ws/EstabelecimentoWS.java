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
import br.com.rango.ngc.entity.EstabelecimentoBandeira;
import br.com.rango.ngc.entity.EstabelecimentoHorario;
import br.com.rango.ngc.entity.EstabelecimentoImagem;
import br.com.rango.ngc.entity.Parametro;
import br.com.rango.ngc.service.EstabelecimentoService;
import br.com.rango.ngc.service.ParametroService;
import br.com.rango.ngc.util.jsf.JSFUtil;
import br.com.rango.ngc.util.outros.Utilitarios;
import br.com.rango.vo.EstabelecimentoVO;
import br.com.rango.vo.HorarioVO;
import br.com.rango.vo.StatusVO;



@Path("/EstabelecimentoWS")
public class EstabelecimentoWS
{
	@POST
	@Path("/getEstabelecimentos")	
	@Produces(MediaType.APPLICATION_JSON)
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<EstabelecimentoVO> getEstabelecimentos(@FormParam("idCidade") Integer idCidade)
	{
		List<EstabelecimentoVO> lista = new ArrayList<EstabelecimentoVO>();	
	
		try
		{			
			//LISTA TODOS OS ESTABELECIMENTOS DA CIDADE
			if(idCidade != null
					&& idCidade.intValue() > 0)
			{
				Parametro parametro = new Parametro();
				parametro.setDsParametro("DIR_SERVIDOR_IMG");
				parametro = ParametroService.getInstancia().get(parametro, 0);
								
				GregorianCalendar gc = new GregorianCalendar();
				int dia = gc.get(Calendar.DAY_OF_WEEK);	
				
				Estabelecimento estabelecimento = new Estabelecimento();
				estabelecimento.setFiltroMap(new HashMap<String, Object>());
				estabelecimento.getFiltroMap().put("notNullFantasia", "notnull");
				estabelecimento.getFiltroMap().put("nmHorario", new BigInteger(Integer.toString(dia)));
				estabelecimento.setIdCidade(new BigInteger(Integer.toString(idCidade)));
				estabelecimento.setFgAtivo("S");
								
				List<Estabelecimento> listaResult = EstabelecimentoService.getInstancia().pesquisar(estabelecimento, EstabelecimentoService.JOIN_CIDADE
																												   | EstabelecimentoService.JOIN_CIDADE_ESTADO
																												   | EstabelecimentoService.JOIN_ESTABELECIMENTO_IMAGEM
																												   | EstabelecimentoService.JOIN_ESTABELECIMENTO_HORARIO, Criteria.DISTINCT_ROOT_ENTITY);
							
				for (Estabelecimento obj : listaResult)
				{
					obj.setListaEstabelecimentoHorario(new ArrayList<EstabelecimentoHorario>(obj.getSetEstabelecimentoHorario()));
					EstabelecimentoVO vo = new EstabelecimentoVO();
					JSFUtil.copiarPropriedades(obj, vo);					
					
					vo.setDsCidade(obj.getCidade().getDsCidade() + " - " + obj.getCidade().getEstado().getDsSigla());
					vo.setDsCategoria("Bares, Restaurantes");
					vo.setDsClassificacao(new DecimalFormat("#,##0.0").format(obj.getVlClassificacao()));
										
					//STATUS
					vo.setDsStatus("Fechado");
					vo.setDsIcone("offline.ico");
					if(obj.getListaEstabelecimentoHorario() != null
							&& obj.getListaEstabelecimentoHorario().size() > 0)
					{
						EstabelecimentoHorario horario = obj.getListaEstabelecimentoHorario().get(0);
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
						
						if(obj.getFgStatus().equals("A")
								&& hoje.after(dataInicio)
								&& hoje.before(dataFim))
						{
							vo.setDsStatus("Aberto");
							vo.setDsIcone("online.ico");
						}
					}
										
					//IMAGEM
					obj.setListaEstabelecimentoImagem(new ArrayList<EstabelecimentoImagem>(obj.getSetEstabelecimentoImagem()));
					
					if(obj.getListaEstabelecimentoImagem() != null
							&& obj.getListaEstabelecimentoImagem().size() > 0)
					{
						vo.setDsImagem(parametro.getVlParametro() + obj.getListaEstabelecimentoImagem().get(0).getDsImagemCompleta()+".jpg");
					}
					else
					{
						vo.setDsImagem(parametro.getVlParametro() + "sem-foto.gif");
					}
					
					//DURACAO
					if(vo.getFgDelivery().equals("S"))
					{
						vo.setDsDuracao(vo.getVlDuracaoDeliveryInicio() + " - " + vo.getVlDuracaoDeliveryFim() + " min");
					}
					else
					{
						if(vo.getFgRetiradaLocal().equals("S"))
						{
							vo.setDsDuracao(vo.getVlDuracaoRetiradaInicio() + " - " + vo.getVlDuracaoRetiradaFim() + " min");
						}
					}
					
					lista.add(vo);
				}
				
				if(lista != null
						&& lista.size() > 0)
				{
					Collections.sort (lista, new Comparator() {
			            public int compare(Object o1, Object o2) {
			                EstabelecimentoVO p1 = (EstabelecimentoVO) o1;
			                EstabelecimentoVO p2 = (EstabelecimentoVO) o2;
			                return p1.getDsStatus().compareToIgnoreCase(p2.getDsStatus());
			            }
			        });
				}
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		return lista;
	}
	
	
	@POST
	@Path("/getInfoEstabelecimento")	
	@Produces(MediaType.APPLICATION_JSON)	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public EstabelecimentoVO getInfoEstabelecimento(@FormParam("idEstabelecimento") Integer idEstabelecimento)
	{
		EstabelecimentoVO estabelecimentoVO = null;
		
		try
		{
			Estabelecimento estabelecimento = new Estabelecimento();
			estabelecimento.setIdEstabelecimento(new BigInteger(Integer.toString(idEstabelecimento)));
			estabelecimento = EstabelecimentoService.getInstancia().get(estabelecimento, EstabelecimentoService.JOIN_ESTABELECIMENTO_HORARIO
																					   | EstabelecimentoService.JOIN_ESTABELECIMENTO_BANDEIRA , Criteria.DISTINCT_ROOT_ENTITY);
			
			if(estabelecimento != null)
			{
				estabelecimentoVO = new EstabelecimentoVO();
				JSFUtil.copiarPropriedades(estabelecimento, estabelecimentoVO);
				
				estabelecimentoVO.setDsVlMinimo("R$ " + new DecimalFormat("#,##0.00").format(estabelecimento.getVlMinimo()));
				
				//TIPO ENTREGA
				String dsTpEntrega = "";
				
				if(estabelecimento.getFgRetiradaLocal() != null
						&& estabelecimento.getFgRetiradaLocal().equals("S"))
				{
					dsTpEntrega += "Retirada no Local";
				}
				
				if(estabelecimento.getFgDelivery() != null
						&& estabelecimento.getFgDelivery().equals("S"))
				{
					dsTpEntrega += ", Delivery";
				}
				
				estabelecimentoVO.setDsTpEntrega(dsTpEntrega);
				
				
				//DELIVERY
				if(estabelecimentoVO.getFgDelivery() != null
						&& estabelecimentoVO.getFgDelivery().equals("S"))
				{
					if(estabelecimentoVO.getVlDuracaoDeliveryInicio().intValue() == estabelecimentoVO.getVlDuracaoDeliveryFim().intValue())
					{
						estabelecimentoVO.setDsDelivery(estabelecimentoVO.getVlDuracaoDeliveryFim() + " min");
					}
					else
					{
						estabelecimentoVO.setDsDelivery(estabelecimentoVO.getVlDuracaoDeliveryInicio() + " ~ " + estabelecimentoVO.getVlDuracaoDeliveryFim() + " min");
					}
				}
				else
				{
					estabelecimentoVO.setDsDelivery("-");
				}
				
				//RETIRADA
				if(estabelecimentoVO.getFgRetiradaLocal() != null
						&& estabelecimentoVO.getFgRetiradaLocal().equals("S"))
				{
					if(estabelecimentoVO.getVlDuracaoRetiradaInicio().intValue() == estabelecimentoVO.getVlDuracaoRetiradaFim().intValue())
					{
						estabelecimentoVO.setDsRetirada(estabelecimentoVO.getVlDuracaoRetiradaFim() + " min");
					}
					else
					{
						estabelecimentoVO.setDsRetirada(estabelecimentoVO.getVlDuracaoRetiradaInicio() + " ~ " + estabelecimentoVO.getVlDuracaoRetiradaFim() + " min");
					}
				}
				else
				{
					estabelecimentoVO.setDsRetirada("-");
				}
				
				
				//TAXA DE ENTREGA
				if(estabelecimentoVO.getFgDelivery() != null
						&& estabelecimentoVO.getFgDelivery().equals("S"))
				{
					if(estabelecimentoVO.getVlTaxaEntregaInicio().intValue() == estabelecimentoVO.getVlTaxaEntregaFim().intValue())
					{
						estabelecimentoVO.setDsEntrega(new DecimalFormat("#,##0.00").format(estabelecimentoVO.getVlTaxaEntregaFim()));
					}
					else
					{
						estabelecimentoVO.setDsEntrega(new DecimalFormat("#,##0.00").format(estabelecimentoVO.getVlTaxaEntregaInicio()) + " ~ " + new DecimalFormat("#,##0.00").format(estabelecimentoVO.getVlTaxaEntregaFim()));
					}
				}
				else
				{
					estabelecimentoVO.setDsEntrega("-");
				}
				
				//FORMA DE PAGAMENTO DELIVERY
				if(estabelecimentoVO.getFgDelivery().equals("S"))
				{
					String dsFormaPagamentoDelivery = "";
					
					if(estabelecimentoVO.getFgDinheiroDelivery() != null
							&& estabelecimentoVO.getFgDinheiroDelivery().equals("S"))
					{
						dsFormaPagamentoDelivery += "Dinheiro";
					}
					
					if(estabelecimentoVO.getFgCartaoDelivery() != null
							&& estabelecimentoVO.getFgCartaoDelivery().equals("S"))
					{
						dsFormaPagamentoDelivery += ", Cartão";
					}
					
					estabelecimentoVO.setDsFormaPagamentoDelivery(dsFormaPagamentoDelivery);
				}
				
				//FORMA DE PAGAMENTO RETIRADA
				if(estabelecimentoVO.getFgRetiradaLocal().equals("S"))
				{
					String dsFormaPagamentoRetirada = "";
					
					if(estabelecimentoVO.getFgDinheiroRetirada() != null
							&& estabelecimentoVO.getFgDinheiroRetirada().equals("S"))
					{
						dsFormaPagamentoRetirada += "Dinheiro";
					}
					
					if(estabelecimentoVO.getFgCartaoRetirada() != null
							&& estabelecimentoVO.getFgCartaoRetirada().equals("S"))
					{
						dsFormaPagamentoRetirada += ", Cartão";
					}
					
					estabelecimentoVO.setDsFormaPagamentoRetirada(dsFormaPagamentoRetirada);
				}
				
				//BANDEIRAS
				String dsBandeiraRetirada = "";
				String dsBandeiraDelivery = "";
				
				if(estabelecimento.getSetEstabelecimentoBandeira() != null
						&& estabelecimento.getSetEstabelecimentoBandeira().size() > 0)
				{
					estabelecimento.setListaEstabelecimentoBandeira(new ArrayList<EstabelecimentoBandeira>(estabelecimento.getSetEstabelecimentoBandeira()));
					
					for (int i = 0; i < estabelecimento.getListaEstabelecimentoBandeira().size(); i++)
					{
						if(estabelecimento.getListaEstabelecimentoBandeira().get(i).getIdBandeiraDelivery() != null)
						{
							dsBandeiraDelivery += estabelecimento.getListaEstabelecimentoBandeira().get(i).getDsBandeiraDelivery();
							dsBandeiraDelivery += ",";
						}
						
						if(estabelecimento.getListaEstabelecimentoBandeira().get(i).getIdBandeiraRetirada() != null)
						{
							dsBandeiraRetirada += estabelecimento.getListaEstabelecimentoBandeira().get(i).getDsBandeiraRetirada();
							dsBandeiraRetirada += ",";
						}
					}						
				}
				
				estabelecimentoVO.setDsBandeiraDelivery(dsBandeiraDelivery.substring(0, dsBandeiraDelivery.length()-1));
				estabelecimentoVO.setDsBandeiraRetirada(dsBandeiraRetirada.substring(0, dsBandeiraRetirada.length()-1));
								
				//HORARIO
				if(estabelecimento.getSetEstabelecimentoHorario() != null
						&& estabelecimento.getSetEstabelecimentoHorario().size() > 0)
				{
					estabelecimento.setListaEstabelecimentoHorario(new ArrayList<EstabelecimentoHorario>(estabelecimento.getSetEstabelecimentoHorario()));
					estabelecimentoVO.setListaHorario(new ArrayList<HorarioVO>());
					
					for (EstabelecimentoHorario obj : estabelecimento.getListaEstabelecimentoHorario())
					{
						HorarioVO horarioVO = new HorarioVO();
						JSFUtil.copiarPropriedades(obj, horarioVO);
						estabelecimentoVO.getListaHorario().add(horarioVO);
					}
					
					Collections.sort (estabelecimentoVO.getListaHorario(), new Comparator() {
			            public int compare(Object o1, Object o2) {
			                HorarioVO p1 = (HorarioVO) o1;
			                HorarioVO p2 = (HorarioVO) o2;
			                return p1.getNmHorario().intValue() < p2.getNmHorario().intValue() ? -1 : (p1.getNmHorario().intValue() > p2.getNmHorario().intValue() ? +1 : 0);
			            }
			        });
				}
			}
			
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		return estabelecimentoVO;
	}

	
	@POST
	@Path("/getStatusEstabelecimento")	
	@Produces(MediaType.APPLICATION_JSON)
	public StatusVO getStatusEstabelecimento(@FormParam("idEstabelecimento") Integer idEstabelecimento)
	{
		StatusVO statusVO = null;
		
		try
		{
			GregorianCalendar gc = new GregorianCalendar();
			int dia = gc.get(Calendar.DAY_OF_WEEK);	
			
			Estabelecimento estabelecimento = new Estabelecimento();
			estabelecimento.setFiltroMap(new HashMap<String, Object>());
			estabelecimento.getFiltroMap().put("nmHorario", new BigInteger(Integer.toString(dia)));
			estabelecimento.setIdEstabelecimento(new BigInteger(Integer.toString(idEstabelecimento)));
			estabelecimento = EstabelecimentoService.getInstancia().get(estabelecimento, EstabelecimentoService.JOIN_ESTABELECIMENTO_HORARIO, Criteria.DISTINCT_ROOT_ENTITY);
			
			//STATUS
			String dsStatus = "F";
			
			if(estabelecimento.getSetEstabelecimentoHorario() != null
					&& estabelecimento.getSetEstabelecimentoHorario().size() > 0)
			{
				estabelecimento.setListaEstabelecimentoHorario(new ArrayList<EstabelecimentoHorario>(estabelecimento.getSetEstabelecimentoHorario()));
				
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
					dsStatus = "A";
				}
				
				statusVO = new StatusVO();
				statusVO.setDsStatus(dsStatus);
				statusVO.setIdEstabelecimento(estabelecimento.getIdEstabelecimento());
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		return statusVO;
	}
}
	

