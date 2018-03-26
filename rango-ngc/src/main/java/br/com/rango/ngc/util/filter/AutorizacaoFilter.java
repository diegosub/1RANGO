package br.com.rango.ngc.util.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.rango.ngc.entity.Usuario;
import br.com.rango.ngc.service.GrupoService;

/**
 * @author Carlos Diego
 */

public class AutorizacaoFilter implements Filter
{
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
	{
	        Usuario usuario = (Usuario)((HttpServletRequest)request).getSession().getAttribute("loginUsuario");
	        
	        HttpServletRequest req = (HttpServletRequest) request;
	            
	        if (! (req).getRequestURI().contains("/login.jsf")
	        		&& ! (req).getRequestURI().contains("/recuperarSenha.jsf")
	        		&& ! (req).getRequestURI().contains("/recuperarSenhaExpired.jsf")
	        		&& (usuario == null || usuario.getIdUsuario() == null) 
	        		&& ( !(req).getRequestURI().contains(".js.jsf"))  
	        		&& ( !(req).getRequestURI().contains(".css.jsf")) 
	        		&& ( !(req).getRequestURI().contains(".ecss.jsf"))
	        		&& ( !(req).getRequestURI().contains(".png.jsf"))
	        		&&( !(req).getRequestURI().equals("/rango-web/"))  )
	        {	        		        	
	        	((HttpServletResponse) response).sendRedirect(req.getContextPath() + "/pages/login.jsf");
	        }
	        else
	        {
	        	if (usuario != null
	        			&& usuario.getIdGrupo().intValue() == GrupoService.GRUPO_ADMINISTRADOR)
				{	        		
	        		if((req).getRequestURI().contains("/configuracao.jsf")
	        				|| (req).getRequestURI().contains("/segmento.jsf")
	        				|| (req).getRequestURI().contains("/produto.jsf")
	        				|| (req).getRequestURI().contains("/pedido.jsf"))
	        		{
	        			((HttpServletResponse) response).sendRedirect(req.getContextPath() + "/pages/acessoNegado.jsf");
	        		}
				}
	        	else
	        	{
	        		if (usuario != null
		        			&& usuario.getIdGrupo().intValue() == GrupoService.GRUPO_ESTABELECIMENTO)
					{
	        			if((req).getRequestURI().contains("/cidade.jsf")
		        				|| (req).getRequestURI().contains("/categoria.jsf")
		        				|| (req).getRequestURI().contains("/estabelecimento.jsf")
		        				|| (req).getRequestURI().contains("/usuario.jsf")
		        				|| (req).getRequestURI().contains("/bairro.jsf"))
		        		{
	        				((HttpServletResponse) response).sendRedirect(req.getContextPath() + "/pages/acessoNegado.jsf");
		        		}
					}	        		
	        	}
	        	
	        	chain.doFilter(request, response);
	        }
	}

	public void init(FilterConfig config) throws ServletException {
		// Nothing to do here!
	}

	public void destroy() {
		// Nothing to do here!
	}

}