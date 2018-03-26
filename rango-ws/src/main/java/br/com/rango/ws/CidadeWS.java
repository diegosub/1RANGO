package br.com.rango.ws;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hibernate.Criteria;

import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.GregorianCalendar;

import br.com.rango.ngc.entity.Bairro;
import br.com.rango.ngc.entity.Cidade;
import br.com.rango.ngc.entity.Estabelecimento;
import br.com.rango.ngc.entity.EstabelecimentoBairro;
import br.com.rango.ngc.entity.EstabelecimentoHorario;
import br.com.rango.ngc.service.CidadeService;
import br.com.rango.ngc.service.EstabelecimentoBairroService;
import br.com.rango.ngc.service.EstabelecimentoService;
import br.com.rango.ngc.util.jsf.JSFUtil;
import br.com.rango.ngc.util.outros.Utilitarios;
import br.com.rango.vo.BairroVO;
import br.com.rango.vo.CidadeVO;



@Path("/CidadeWS")
public class CidadeWS
{
	@POST
	@Path("/getAll")	
	@Produces(MediaType.APPLICATION_JSON)
	public List<CidadeVO> getAll()
	{
		List<CidadeVO> lista = null;	
	
		try
		{	
			//LISTA CIDADES
			Cidade cidade = new Cidade();
			cidade.setFiltroMap(new HashMap<String, Object>());
			cidade.getFiltroMap().put("notNullFantasia", "NOTNULL");
			cidade.setFgAtivo("S");
			
			List<Cidade> listaResult = CidadeService.getInstancia().pesquisar(cidade, CidadeService.JOIN_ESTADO
																					| CidadeService.JOIN_ESTABELECIMENTO, Criteria.DISTINCT_ROOT_ENTITY);
			
			//ESTABELECIMENTOS POR CIDADE
			Map<BigInteger, Integer> mapCountCidade = new HashMap<BigInteger, Integer>();
			GregorianCalendar gc = new GregorianCalendar();
			int dia = gc.get(Calendar.DAY_OF_WEEK);			
			
			Estabelecimento estabelecimento = new Estabelecimento();
			estabelecimento.setFgAtivo("S");
			estabelecimento.setFgStatus("A");
			estabelecimento.setFiltroMap(new HashMap<String, Object>());
			estabelecimento.getFiltroMap().put("flgAtivoHorario", true);
			estabelecimento.getFiltroMap().put("fgAtivoCidade", "S");
			estabelecimento.getFiltroMap().put("nmHorario", new BigInteger(Integer.toString(dia)));
			
			List<Estabelecimento> listaEstabelecimento = EstabelecimentoService.getInstancia().pesquisar(estabelecimento, EstabelecimentoService.JOIN_CIDADE
																														| EstabelecimentoService.JOIN_ESTABELECIMENTO_HORARIO, Criteria.DISTINCT_ROOT_ENTITY);

			Integer count = 0;
			
			for (Estabelecimento obj : listaEstabelecimento)
			{				
				List<EstabelecimentoHorario> listaHorario = new ArrayList<EstabelecimentoHorario>(obj.getSetEstabelecimentoHorario());
				EstabelecimentoHorario horario = listaHorario.get(0);
				
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
				
				if((new Date().after(dataInicio) || new Date().equals(dataInicio))
						&& (new Date().before(dataFim) || new Date().equals(dataFim)))
				{
					count++;
					mapCountCidade.put(obj.getIdCidade(), count);
				}
			}
			
			
			//
			
			Set<BigInteger> setCountCidade = mapCountCidade.keySet();			
			
			if(listaResult != null
					&& listaResult.size() > 0)
			{
				lista = new ArrayList<CidadeVO>();
				
				for (Cidade obj : listaResult)
				{
					CidadeVO vo = new CidadeVO();
					JSFUtil.copiarPropriedades(obj, vo);
					vo.setDsEstado(obj.getEstado().getDsEstado());
					vo.setDsSiglaEstado(obj.getEstado().getDsSigla());
					vo.setNrTotalEstabelecimento(0);
					
					for (BigInteger chave : setCountCidade)
					{
						if(chave.intValue() == obj.getIdCidade().intValue())
						{
							vo.setNrTotalEstabelecimento(mapCountCidade.get(chave));
						}
					}
					
					lista.add(vo);
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
	@Path("/getCidadeBairro")	
	@Produces(MediaType.APPLICATION_JSON)	
	public List<BairroVO> getCidadeBairro(@FormParam("idEstabelecimento") Integer idEstabelecimento)
	{
		List<BairroVO> lista = new ArrayList<BairroVO>();	
	
		try
		{
			EstabelecimentoBairro estabelecimentoBairro = new EstabelecimentoBairro();
			estabelecimentoBairro.setIdEstabelecimento(new BigInteger(Integer.toString(idEstabelecimento)));
			estabelecimentoBairro.setBairro(new Bairro());
			estabelecimentoBairro.getBairro().setFgAtivo("S");			
			
			List<EstabelecimentoBairro> listaEstabelecimentoBairro = EstabelecimentoBairroService.getInstancia().pesquisar(estabelecimentoBairro, EstabelecimentoBairroService.JOIN_BAIRRO);
			
			if(listaEstabelecimentoBairro != null
					&& listaEstabelecimentoBairro.size() > 0)
			{
				lista = new ArrayList<BairroVO>();
				
				for (EstabelecimentoBairro obj : listaEstabelecimentoBairro)
				{
					BairroVO bairroVO = new BairroVO();
					JSFUtil.copiarPropriedades(obj.getBairro(), bairroVO);
					bairroVO.setStrValorTaxa(new DecimalFormat("#,##0.00").format(obj.getVlTaxa()));
					bairroVO.setVlTaxa(obj.getVlTaxa());
					lista.add(bairroVO);
				}
			}		
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		return lista;
	}
	
}
