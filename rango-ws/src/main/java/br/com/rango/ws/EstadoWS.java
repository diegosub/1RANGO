package br.com.rango.ws;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.rango.ngc.entity.Estado;
import br.com.rango.ngc.service.EstadoService;
import br.com.rango.ngc.util.jsf.JSFUtil;
import br.com.rango.vo.EstadoVO;


@Path("/EstadoWS")
public class EstadoWS
{
	@POST
	@Path("/getEstados")	
	@Produces(MediaType.APPLICATION_JSON)	
	public List<EstadoVO> getEstados()
	{
		List<EstadoVO> lista = null;
		
		try
		{
			Estado estado = new Estado();
			List<Estado> listaEstado = EstadoService.getInstancia().pesquisar(estado, 0);
			
			if(listaEstado != null)
			{
				lista = new ArrayList<EstadoVO>();
				
				for (Estado obj : listaEstado)
				{
					EstadoVO estadoVO = new EstadoVO();
					JSFUtil.copiarPropriedades(obj, estadoVO);
					
					lista.add(estadoVO);
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
