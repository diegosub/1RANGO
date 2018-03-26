package br.com.rango.ws;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hibernate.Criteria;

import br.com.rango.ngc.entity.Produto;
import br.com.rango.ngc.entity.SegmentoAdicional;
import br.com.rango.ngc.entity.SegmentoItem;
import br.com.rango.ngc.service.ProdutoService;
import br.com.rango.ngc.service.SegmentoAdicionalService;
import br.com.rango.ngc.util.jsf.JSFUtil;
import br.com.rango.vo.ProdutoVO;
import br.com.rango.vo.SegmentoAdicionalVO;
import br.com.rango.vo.SegmentoItemVO;



@Path("/AdicionalWS")
public class AdicionalWS
{
	@POST
	@Path("/getAdicional")	
	@Produces(MediaType.APPLICATION_JSON)
	public ProdutoVO getAdicional(@FormParam("idProduto") Integer idProduto)
	{
		ProdutoVO produtoVO = null;	
	
		try
		{	
			Produto produto = new Produto();
			produto.setIdProduto(new BigInteger(Integer.toString(idProduto)));
			
			produto = ProdutoService.getInstancia().get(produto, 0);
			
			if(produto != null)
			{
				produtoVO = new ProdutoVO();
				JSFUtil.copiarPropriedades(produto, produtoVO);
				produtoVO.setDsVlrProduto("R$ " + new DecimalFormat("#,##0.00").format(produto.getVlProduto()));
								
				//SEGMENTO ADICIONAL
				SegmentoAdicional segmentoAdicional = new SegmentoAdicional();
				segmentoAdicional.setIdSegmento(produto.getIdSegmento());
				segmentoAdicional.setFgAtivo("S");
				
				List<SegmentoAdicional> listaSegmentoAdicional = SegmentoAdicionalService.getInstancia().pesquisar(segmentoAdicional, SegmentoAdicionalService.JOIN_SEGMENTO_ITEM,
																																					Criteria.DISTINCT_ROOT_ENTITY);
				
				if(listaSegmentoAdicional != null
						&& listaSegmentoAdicional.size() > 0)
				{
					produtoVO.setListaSegmentoAdicional(new ArrayList<SegmentoAdicionalVO>());
					
					for (SegmentoAdicional objSegmentoAdicional : listaSegmentoAdicional)
					{
						SegmentoAdicionalVO segmentoAdicionalVO = new SegmentoAdicionalVO(); 
						JSFUtil.copiarPropriedades(objSegmentoAdicional, segmentoAdicionalVO);						
						
						if(objSegmentoAdicional.getListaSegmentoItem() != null
								&& objSegmentoAdicional.getListaSegmentoItem().size() > 0)
						{
							segmentoAdicionalVO.setListaSegmentoItem(new ArrayList<SegmentoItemVO>());
							
							for (SegmentoItem objSegmentoItem : objSegmentoAdicional.getListaSegmentoItem())
							{
								SegmentoItemVO segmentoItemVO = new SegmentoItemVO();
								JSFUtil.copiarPropriedades(objSegmentoItem, segmentoItemVO);
								segmentoItemVO.setStrVlAdicional("R$ " + new DecimalFormat("#,##0.00").format(segmentoItemVO.getVlAdicional()));
								segmentoAdicionalVO.getListaSegmentoItem().add(segmentoItemVO);
							}
						}
						
						produtoVO.getListaSegmentoAdicional().add(segmentoAdicionalVO);
					}
				}
				
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		return produtoVO;
	}
}
