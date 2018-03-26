package br.com.rango.web.servlet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.rango.ngc.entity.EstabelecimentoImagem;
import br.com.rango.ngc.entity.Parametro;
import br.com.rango.ngc.service.EstabelecimentoImagemService;
import br.com.rango.ngc.service.ParametroService;

public class ImagemServlet extends HttpServlet
{

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    	try
    	{
    		String diretorio = "";
			String idEstabelecimento = request.getParameter("id");
			
			Parametro parametro = new Parametro();
			parametro.setDsParametro("DIR_IMAGENS_ESTABELECIMENTO");
			parametro = ParametroService.getInstancia().get(parametro, 0);
			
			if(idEstabelecimento != null
					&& !idEstabelecimento.trim().equals(""))
			{	
				EstabelecimentoImagem imagem = new EstabelecimentoImagem();
				imagem.setIdEstabelecimento(new BigInteger(idEstabelecimento));
				imagem = EstabelecimentoImagemService.getInstancia().get(imagem, EstabelecimentoImagemService.JOIN_ESTABELECIMENTO);
				
				if(imagem != null)
				{
					response.setContentType(imagem.getDsTipo());
					diretorio = parametro.getVlParametro() + "E" + imagem.getEstabelecimento().getIdEstabelecimento() + "C" + imagem.getEstabelecimento().getIdCidade() + ".jpg";
				}
				else
				{
					diretorio= parametro.getVlParametro() + "sem-foto.gif";
				}
			}
			else
			{
				diretorio= parametro.getVlParametro() + "sem-foto.gif";
			}
			
			File file= new File(diretorio);
			BufferedImage img = ImageIO.read(file);
			OutputStream out = response.getOutputStream();
			// Escreve a imagem no outputstream da response NO FORMATO PNG
			ImageIO.write(img,"JPG", out);
			
			if (out != null) {
				out.close();
			}			
    	}
    	catch (Exception e)
    	{
    		e.printStackTrace();
		}
    }
}