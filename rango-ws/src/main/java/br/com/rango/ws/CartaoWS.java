package br.com.rango.ws;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.rango.ngc.entity.EstabelecimentoBandeira;
import br.com.rango.ngc.service.EstabelecimentoBandeiraService;
import br.com.rango.vo.CartaoVO;


@Path("/CartaoWS")
public class CartaoWS
{
	@POST
	@Path("/getCartoesDelivery")	
	@Produces(MediaType.APPLICATION_JSON)	
	public List<CartaoVO> getCartoesDelivery(@FormParam("idEstabelecimento") Integer idEstabelecimento)
	{
		List<CartaoVO> lista = new ArrayList<CartaoVO>();
		
		try
		{
			EstabelecimentoBandeira estabelecimentoBandeira = new EstabelecimentoBandeira();
			estabelecimentoBandeira.setIdEstabelecimento(new BigInteger(Integer.toString(idEstabelecimento)));
			estabelecimentoBandeira.setFiltroMap(new HashMap<String, Object>());
			estabelecimentoBandeira.getFiltroMap().put("bandeiraDeliveryNotNull", "NOTNULL");
			
			List<EstabelecimentoBandeira> listaBandeira = EstabelecimentoBandeiraService.getInstancia().pesquisar(estabelecimentoBandeira, 0);
			
			if(listaBandeira != null)
			{
				lista = new ArrayList<CartaoVO>();
				
				for (EstabelecimentoBandeira obj : listaBandeira)
				{
					CartaoVO cartaoVO = new CartaoVO();
					cartaoVO.setIdCartao(obj.getIdBandeiraDelivery());
					cartaoVO.setDsCartao(obj.getDsBandeiraDelivery());
					
					lista.add(cartaoVO);
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
	@Path("/getCartoesRetirada")	
	@Produces(MediaType.APPLICATION_JSON)	
	public List<CartaoVO> getCartoesRetirada(@FormParam("idEstabelecimento") Integer idEstabelecimento)
	{
		List<CartaoVO> lista = new ArrayList<CartaoVO>();
		
		try
		{
			EstabelecimentoBandeira estabelecimentoBandeira = new EstabelecimentoBandeira();
			estabelecimentoBandeira.setIdEstabelecimento(new BigInteger(Integer.toString(idEstabelecimento)));
			estabelecimentoBandeira.setFiltroMap(new HashMap<String, Object>());
			estabelecimentoBandeira.getFiltroMap().put("bandeiraRetiradaNotNull", "NOTNULL");
			
			List<EstabelecimentoBandeira> listaBandeira = EstabelecimentoBandeiraService.getInstancia().pesquisar(estabelecimentoBandeira, 0);
			
			if(listaBandeira != null)
			{
				lista = new ArrayList<CartaoVO>();
				
				for (EstabelecimentoBandeira obj : listaBandeira)
				{
					CartaoVO cartaoVO = new CartaoVO();
					cartaoVO.setIdCartao(obj.getIdBandeiraRetirada());
					cartaoVO.setDsCartao(obj.getDsBandeiraRetirada());
					
					lista.add(cartaoVO);
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
