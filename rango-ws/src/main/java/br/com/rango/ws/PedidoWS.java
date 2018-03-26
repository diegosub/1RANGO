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

import org.hibernate.Criteria;

import br.com.rango.ngc.entity.Pedido;
import br.com.rango.ngc.entity.PedidoProduto;
import br.com.rango.ngc.entity.PedidoProdutoAdicional;
import br.com.rango.ngc.service.PedidoService;
import br.com.rango.ngc.util.jsf.JSFUtil;
import br.com.rango.vo.PedidoProdutoAdicionalVO;
import br.com.rango.vo.PedidoProdutoVO;
import br.com.rango.vo.PedidoVO;



@Path("/PedidoWS")
public class PedidoWS
{
	@POST
	@Path("/cadastrar")	
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public PedidoVO cadastrar(PedidoVO vo)
	{
		PedidoVO pedidoVO = new PedidoVO();
		
		try
		{
			this.configurarPedido(vo);
			
			Pedido pedido = new Pedido();
			JSFUtil.copiarPropriedades(vo, pedido);
			pedido.setIdStatus(new BigInteger("1"));
			pedido.setDtCadastro(new Date());
			pedido.setListaPedidoProduto(new ArrayList<PedidoProduto>());
			
			for (PedidoProdutoVO pedidoProdutoVO : vo.getListaPedidoProduto())
			{
				PedidoProduto pedidoProduto = new PedidoProduto();
				JSFUtil.copiarPropriedades(pedidoProdutoVO, pedidoProduto);				
				pedido.getListaPedidoProduto().add(pedidoProduto);
				
				if(pedidoProdutoVO.getListaPedidoProdutoAdicional() != null
						&& pedidoProdutoVO.getListaPedidoProdutoAdicional().size() > 0)
				{
					pedidoProduto.setListaPedidoProdutoAdicional(new ArrayList<PedidoProdutoAdicional>());
					
					for (PedidoProdutoAdicionalVO pedidoProdutoAdicionalVO : pedidoProdutoVO.getListaPedidoProdutoAdicional())
					{
						PedidoProdutoAdicional pedidoProdutoAdicional = new PedidoProdutoAdicional();
						JSFUtil.copiarPropriedades(pedidoProdutoAdicionalVO, pedidoProdutoAdicional);
						pedidoProduto.getListaPedidoProdutoAdicional().add(pedidoProdutoAdicional);
					}
				}
			}
	
			pedido = PedidoService.getInstancia().inserir(pedido);
			
			if(pedido != null)
			{
				JSFUtil.copiarPropriedades(pedido, pedidoVO);
			}
		}
		catch (Exception e)
		{
			pedidoVO.setMensagem(e.getMessage());
		}
		
		return pedidoVO;
	}
	
	@POST
	@Path("/getPedidos")	
	@Produces(MediaType.APPLICATION_JSON)	
	public List<PedidoVO> getPedidos(@FormParam("idUsuario") Integer idUsuario)
	{
		List<PedidoVO> lista = new ArrayList<PedidoVO>();	
	
		try
		{
			Pedido pedido = new Pedido();
			pedido.setIdUsuario(new BigInteger(Integer.toString(idUsuario)));
			
			List<Pedido> listaPedido = PedidoService.getInstancia().pesquisar(pedido, PedidoService.JOIN_PEDIDO_PRODUTO
																					| PedidoService.JOIN_ESTABELECIMENTO
																					| PedidoService.JOIN_PEDIDO_PRODUTO_ADICIONAL, Criteria.DISTINCT_ROOT_ENTITY);
			
			if(listaPedido != null
					&& listaPedido.size() > 0)
			{
				lista = new ArrayList<PedidoVO>();
				
				for (Pedido obj : listaPedido)
				{
					PedidoVO pedidoVO = new PedidoVO();
					JSFUtil.copiarPropriedades(obj, pedidoVO);
					pedidoVO.setDsEstabelecimento(obj.getEstabelecimento().getDsEstabelecimento());
					
					pedidoVO.setListaPedidoProduto(new ArrayList<PedidoProdutoVO>());
					
					lista.add(pedidoVO);
				}
			}		
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		return lista;
	}
	
	private void configurarPedido(PedidoVO vo)
	{
		vo.setIdTipoPedido((vo.getIdTipoPedido() != null && vo.getIdTipoPedido().intValue() == 0) ? null : vo.getIdTipoPedido());
		vo.setIdFormaPagamento((vo.getIdFormaPagamento() != null && vo.getIdFormaPagamento().intValue() == 0) ? null : vo.getIdFormaPagamento());
		vo.setIdCartao((vo.getIdCartao() != null && vo.getIdCartao().intValue() == 0) ? null : vo.getIdCartao());
		vo.setIdEndereco((vo.getIdEndereco() != null && vo.getIdEndereco().intValue() == 0) ? null : vo.getIdEndereco());
		vo.setIdBairro((vo.getIdBairro() != null && vo.getIdBairro().intValue() == 0) ? null : vo.getIdBairro());
	}
}
