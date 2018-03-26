package br.com.rango.ws;

import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.rango.ngc.entity.Contato;
import br.com.rango.ngc.service.ContatoService;
import br.com.rango.ngc.util.jsf.JSFUtil;
import br.com.rango.vo.ContatoVO;



@Path("/ContatoWS")
public class ContatoWS
{
	@POST
	@Path("/cadastrar")	
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ContatoVO cadastrar(ContatoVO vo)
	{
		ContatoVO contatoVO = new ContatoVO();
		
		try
		{
			Contato contato = new Contato();
			JSFUtil.copiarPropriedades(vo, contato);
			contato.setDtCadastro(new Date());
			
			contato = ContatoService.getInstancia().inserir(contato);
			
			if(contato != null)
			{
				JSFUtil.copiarPropriedades(contato, contatoVO);
			}
		}
		catch (Exception e)
		{
			contatoVO.setMensagem(e.getMessage());
		}
		
		return contatoVO;
	}
}
