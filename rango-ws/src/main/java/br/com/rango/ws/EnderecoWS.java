package br.com.rango.ws;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.rango.ngc.entity.Endereco;
import br.com.rango.ngc.entity.Estado;
import br.com.rango.ngc.service.EnderecoService;
import br.com.rango.ngc.service.EstadoService;
import br.com.rango.ngc.util.jsf.JSFUtil;
import br.com.rango.vo.EnderecoVO;



@Path("/EnderecoWS")
public class EnderecoWS
{
	@POST
	@Path("/cadastrar")	
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public EnderecoVO cadastrar(EnderecoVO vo)
	{
		EnderecoVO enderecoVO = new EnderecoVO();
		
		try
		{
			Endereco endereco = new Endereco();
			JSFUtil.copiarPropriedades(vo, endereco);
			endereco.setDtCadastro(new Date());
			
			//SETANDO ESTADO
			Estado estado = new Estado();
			estado.setDsSigla(vo.getSiglaEstado());
			estado = EstadoService.getInstancia().get(estado, 0);
			
			if(estado != null)
			{
				endereco.setIdEstado(estado.getIdEstado());
			}
			
			endereco = EnderecoService.getInstancia().inserir(endereco);
			
			if(endereco != null)
			{
				JSFUtil.copiarPropriedades(endereco, enderecoVO);
			}
		}
		catch (Exception e)
		{
			enderecoVO.setMensagem(e.getMessage());
		}
		
		return enderecoVO;
	}
	
	@POST
	@Path("/getEnderecos")	
	@Produces(MediaType.APPLICATION_JSON)	
	public List<EnderecoVO> getEnderecos(@FormParam("idUsuario") Integer idUsuario)
	{
		List<EnderecoVO> lista = new ArrayList<EnderecoVO>();	
	
		try
		{
			Endereco endereco = new Endereco();
			endereco.setIdUsuario(new BigInteger(Integer.toString(idUsuario)));
			
			List<Endereco> listaEndereco = EnderecoService.getInstancia().pesquisar(endereco, EnderecoService.JOIN_ESTADO);
			
			if(listaEndereco != null
					&& listaEndereco.size() > 0)
			{
				lista = new ArrayList<EnderecoVO>();
				
				for (Endereco obj : listaEndereco)
				{
					EnderecoVO enderecoVO = new EnderecoVO();
					JSFUtil.copiarPropriedades(obj, enderecoVO);
					enderecoVO.setSiglaEstado(obj.getEstado().getDsSigla());
					
					lista.add(enderecoVO);
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
	@Path("/deletarEndereco")	
	@Produces(MediaType.APPLICATION_JSON)	
	public EnderecoVO deletarEndereco(@FormParam("idEndereco") Integer idEndereco)
	{
		try
		{
			Endereco endereco = new Endereco();
			endereco.setIdEndereco(new BigInteger(Integer.toString(idEndereco)));		
			EnderecoService.getInstancia().deletarEndereco(endereco);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		return null;
	}
}
