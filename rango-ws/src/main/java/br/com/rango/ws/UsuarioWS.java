package br.com.rango.ws;

import java.math.BigInteger;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.rango.ngc.entity.Usuario;
import br.com.rango.ngc.service.GrupoService;
import br.com.rango.ngc.service.UsuarioService;
import br.com.rango.ngc.util.criptografia.CriptoMD5;
import br.com.rango.ngc.util.jsf.JSFUtil;
import br.com.rango.vo.UsuarioVO;



@Path("/UsuarioWS")
public class UsuarioWS
{
	@POST
	@Path("/cadastrar")	
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public UsuarioVO cadastrar(UsuarioVO vo)
	{
		UsuarioVO usuarioVO = new UsuarioVO();
		
		try
		{
			Usuario usuario = new Usuario();
			JSFUtil.copiarPropriedades(vo, usuario);
			usuario.setDtCadastro(new Date());
			usuario.setIdUsuarioCad(new BigInteger("1"));
			usuario.setFgAtivo("S");
			usuario.setDsSenha(CriptoMD5.stringHexa(usuario.getDsSenha().toUpperCase()));
			usuario.setIdGrupo(new BigInteger(Integer.toString(GrupoService.GRUPO_CLIENTE)));
			
			usuario = UsuarioService.getInstancia().inserirViaApp(usuario);
			
			if(usuario != null)
			{
				JSFUtil.copiarPropriedades(usuario, usuarioVO);
			}
		}
		catch (Exception e)
		{
			usuarioVO.setMensagem(e.getMessage());
		}
		
		return usuarioVO;
	}
	
	@POST
	@Path("/atualizar")	
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public UsuarioVO atualizar(UsuarioVO vo)
	{
		UsuarioVO usuarioVO = new UsuarioVO();
		
		try
		{
			Usuario usuario = new Usuario();
			JSFUtil.copiarPropriedades(vo, usuario);
			
			usuario = UsuarioService.getInstancia().atualizarViaApp(usuario);
			
			if(usuario != null)
			{
				JSFUtil.copiarPropriedades(usuario, usuarioVO);
			}
		}
		catch (Exception e)
		{
			usuarioVO.setMensagem(e.getMessage());
		}
		
		return usuarioVO;
	}
	
	@POST
	@Path("/alterarSenha")	
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public UsuarioVO alterarSenha(UsuarioVO vo)
	{
		UsuarioVO usuarioVO = new UsuarioVO();
		
		try
		{
			Usuario usuario = new Usuario();
			JSFUtil.copiarPropriedades(vo, usuario);
			
			usuario = UsuarioService.getInstancia().alterarSenhaViaApp(usuario, vo.getDsSenha(), vo.getDsNovaSenha());
			
			if(usuario != null)
			{
				JSFUtil.copiarPropriedades(usuario, usuarioVO);
			}
		}
		catch (Exception e)
		{
			usuarioVO.setMensagem(e.getMessage());
		}
		
		return usuarioVO;
	}
	
	@POST
	@Path("/getUsuario")	
	@Produces(MediaType.APPLICATION_JSON)
	public UsuarioVO getUsuario(@FormParam("idUsuario") Integer idUsuario)
	{
		UsuarioVO usuarioVO = null;
		
		try
		{
			Usuario usuario = new Usuario();			
			usuario.setIdUsuario(new BigInteger(Integer.toString(idUsuario)));
			
			usuario = UsuarioService.getInstancia().get(usuario, 0);
			
			if(usuario != null)
			{
				usuarioVO = new UsuarioVO();
				JSFUtil.copiarPropriedades(usuario, usuarioVO);
			}
		}
		catch (Exception e)
		{
			usuarioVO.setMensagem(e.getMessage());
		}
		
		return usuarioVO;
	}
	
	@POST
	@Path("/recuperarSenha")	
	@Produces(MediaType.APPLICATION_JSON)
	public UsuarioVO recuperarSenha(@FormParam("dsEmail") String dsEmail)
	{
		UsuarioVO usuarioVO = new UsuarioVO();
		
		try
		{
			Usuario usuario = new Usuario();
			usuario.setDsEmail(dsEmail);			
			usuario = UsuarioService.getInstancia().recuperarSenhaViaApp(usuario);			
		}
		catch (Exception e)
		{
			usuarioVO.setMensagem(e.getMessage());
		}
		
		return usuarioVO;
	}
	
	@POST
	@Path("/login")	
	@Produces(MediaType.APPLICATION_JSON)
	public UsuarioVO login(@FormParam("dsEmail") String dsEmail, @FormParam("dsSenha") String dsSenha)
	{
		UsuarioVO usuarioVO = null;
		
		try
		{
			Usuario usuario = new Usuario();			
			usuario.setFgAtivo("S");
			usuario.setDsEmail(dsEmail);
			usuario.setDsSenha(CriptoMD5.stringHexa(dsSenha.toUpperCase()));
			usuario.setIdGrupo(new BigInteger(Integer.toString(GrupoService.GRUPO_CLIENTE)));
			
			usuario = UsuarioService.getInstancia().get(usuario, 0);
			
			if(usuario != null)
			{
				usuarioVO = new UsuarioVO();
				JSFUtil.copiarPropriedades(usuario, usuarioVO);
			}
		}
		catch (Exception e)
		{
			usuarioVO.setMensagem(e.getMessage());
		}
		
		return usuarioVO;
	}
}
