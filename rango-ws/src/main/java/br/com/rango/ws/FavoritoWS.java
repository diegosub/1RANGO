package br.com.rango.ws;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.Consumes;
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
import br.com.rango.ngc.entity.Favorito;
import br.com.rango.ngc.entity.Parametro;
import br.com.rango.ngc.service.EstabelecimentoService;
import br.com.rango.ngc.service.FavoritoService;
import br.com.rango.ngc.service.ParametroService;
import br.com.rango.ngc.util.jsf.JSFUtil;
import br.com.rango.ngc.util.outros.Utilitarios;
import br.com.rango.vo.EstabelecimentoVO;
import br.com.rango.vo.FavoritoVO;



@Path("/FavoritoWS")
public class FavoritoWS
{
	@POST
	@Path("/cadastrar")	
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public FavoritoVO cadastrar(FavoritoVO vo)
	{
		FavoritoVO favoritoVO = new FavoritoVO();
		
		try
		{			
			Favorito favorito = new Favorito();
			JSFUtil.copiarPropriedades(vo, favorito);
			favorito.setDtCadastro(new Date());
	
			favorito = FavoritoService.getInstancia().inserir(favorito);
			
			if(favorito != null)
			{
				JSFUtil.copiarPropriedades(favorito, favoritoVO);
			}
		}
		catch (Exception e)
		{
			favoritoVO.setMensagem(e.getMessage());
		}
		
		return favoritoVO;
	}
	
	@POST
	@Path("/remover")	
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public FavoritoVO remover(FavoritoVO vo)
	{
		FavoritoVO favoritoVO = new FavoritoVO();
		
		try
		{			
			Favorito favorito = new Favorito();
			JSFUtil.copiarPropriedades(vo, favorito);			
	
			favorito = FavoritoService.getInstancia().remover(favorito);
			
			if(favorito != null)
			{
				JSFUtil.copiarPropriedades(favorito, favoritoVO);
			}
		}
		catch (Exception e)
		{
			favoritoVO.setMensagem(e.getMessage());
		}
		
		return favoritoVO;
	}
	
	@POST
	@Path("/getAll")	
	@Produces(MediaType.APPLICATION_JSON)	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<EstabelecimentoVO> getAll(@FormParam("idUsuario") Integer idUsuario)
	{
		List<EstabelecimentoVO> lista = new ArrayList<EstabelecimentoVO>();	
		
		try
		{			
			//LISTA TODOS OS ESTABELECIMENTOS DA CIDADE
			
			Parametro parametro = new Parametro();
			parametro.setDsParametro("DIR_SERVIDOR_IMG");
			parametro = ParametroService.getInstancia().get(parametro, 0);
							
			GregorianCalendar gc = new GregorianCalendar();
			int dia = gc.get(Calendar.DAY_OF_WEEK);	
			
			Estabelecimento estabelecimento = new Estabelecimento();
			estabelecimento.setFiltroMap(new HashMap<String, Object>());			
			estabelecimento.getFiltroMap().put("nmHorario", new BigInteger(Integer.toString(dia)));
			estabelecimento.getFiltroMap().put("idUsuarioFavorito", new BigInteger(Integer.toString(idUsuario)));
			estabelecimento.setFgAtivo("S");
							
			List<Estabelecimento> listaResult = EstabelecimentoService.getInstancia().pesquisar(estabelecimento, EstabelecimentoService.JOIN_CIDADE
																											   | EstabelecimentoService.JOIN_CIDADE_ESTADO
																											   | EstabelecimentoService.JOIN_FAVORITO
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
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		return lista;
	}
	
	@POST
	@Path("/getFavorito")	
	@Produces(MediaType.APPLICATION_JSON)	
	public EstabelecimentoVO getFavorito(@FormParam("idUsuario") Integer idUsuario, @FormParam("idEstabelecimento") Integer idEstabelecimento)
	{
		EstabelecimentoVO estabelecimentoVO = new EstabelecimentoVO();	
	
		try
		{
			Favorito favorito = new Favorito();
			favorito.setIdUsuario(new BigInteger(Integer.toString(idUsuario)));
			favorito.setIdEstabelecimento(new BigInteger(Integer.toString(idEstabelecimento)));
			
			favorito = FavoritoService.getInstancia().get(favorito, 0);
			
			if(favorito != null)
			{
				estabelecimentoVO.setDsFavorito("S");
			}
			else
			{
				estabelecimentoVO.setDsFavorito("N");
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		return estabelecimentoVO;
	}
}
