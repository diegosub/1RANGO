package br.com.rango.ngc.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.rango.ngc.entity.EstabelecimentoImagem;
import br.com.rango.ngc.entity.Parametro;
import br.com.rango.ngc.util.HibernateUtil;
import br.com.rango.ngc.util.RangoHbNgc;
import br.com.rango.ngc.util.RestritorHb;


public class EstabelecimentoImagemService extends RangoHbNgc<EstabelecimentoImagem>
{
	private static EstabelecimentoImagemService instancia = null;
	
	public static final int JOIN_USUARIO_CAD = 1;
	
	public static final int JOIN_USUARIO_ALT = 2;

	public static final int JOIN_ESTABELECIMENTO = 4;
	
	@SuppressWarnings("rawtypes")
	private static Map filtroPropriedade = new HashMap();
	
	@SuppressWarnings("rawtypes")
	private static Map restritores = new HashMap();
	
	public static EstabelecimentoImagemService getInstancia()
	{
		if (instancia == null)
		{
			instancia = new EstabelecimentoImagemService();
		}
		return instancia;
	}
	
	public EstabelecimentoImagemService()
	{
		adicionarFiltro("idEstabelecimentoImagem", RestritorHb.RESTRITOR_EQ, "idEstabelecimentoImagem");
		adicionarFiltro("idEstabelecimento", RestritorHb.RESTRITOR_EQ, "idEstabelecimento");		
	}
	
	public EstabelecimentoImagem salvarImagem(EstabelecimentoImagem vo) throws Exception
	{
		Session sessao = HibernateUtil.getSession();
		sessao.setFlushMode(FlushMode.COMMIT);
		Transaction tx = sessao.beginTransaction();
		try
		{
			vo = salvarImagem(sessao, vo);
			tx.commit();
			return vo;
		}
		catch (Exception e)
		{
			tx.rollback();
			throw e;
		}
		finally
		{
			sessao.close();
		}
	}
	
	public EstabelecimentoImagem salvarImagem(Session sessao, EstabelecimentoImagem vo) throws Exception
	{
		//DIRETORIO DE ARMAZENAMENTO DA IMAGEM
		Parametro parametro = new Parametro();
		parametro.setDsParametro("DIR_IMAGENS_ESTABELECIMENTO");
		parametro = ParametroService.getInstancia().get(sessao, parametro, 0);
		
		String nomeArquivo = parametro.getVlParametro() + "E" + vo.getEstabelecimento().getIdEstabelecimento() + "C" +vo.getEstabelecimento().getCidade().getIdCidade();
		String dsReal = "E" + vo.getEstabelecimento().getIdEstabelecimento() + "C" +vo.getEstabelecimento().getCidade().getIdCidade();
		
		EstabelecimentoImagem estabelecimentoImagem = new EstabelecimentoImagem();
		estabelecimentoImagem.setIdEstabelecimento(vo.getIdEstabelecimento());		
		estabelecimentoImagem = this.get(sessao, estabelecimentoImagem, 0);
		
		if(estabelecimentoImagem != null)
		{
			estabelecimentoImagem.setDsImagem(vo.getDsImagem());
			estabelecimentoImagem.setDsTipo(vo.getDsTipo());
			estabelecimentoImagem.setVlTamanho(vo.getVlTamanho());
			estabelecimentoImagem.setDsImagemCompleta(dsReal);
			
			super.alterar(sessao, estabelecimentoImagem);
		}
		else
		{
			vo.setDsImagemCompleta(dsReal);
			super.inserir(sessao, vo);
		}
		
		//SALVAR IMAGEM NO DIRETORIO				
		try (InputStream is = vo.getFile().getInputStream();
				OutputStream out = new FileOutputStream(nomeArquivo+".jpg"))
		{
			int read = 0;
			byte[] bytes = new byte[2*1024*1024];

			while ((read = is.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
		}
		catch (IOException e)
		{
			throw e;
		}
		
		//SALVANDO NO FTP
		Parametro pServer = new Parametro();
		pServer.setDsParametro("FTP_SERVER");
		pServer = ParametroService.getInstancia().get(sessao, pServer, 0);
		
		Parametro pPort = new Parametro();
		pPort.setDsParametro("FTP_PORT");
		pPort = ParametroService.getInstancia().get(sessao, pPort, 0);
		
		Parametro pUsr = new Parametro();
		pUsr.setDsParametro("FTP_LOGIN");
		pUsr = ParametroService.getInstancia().get(sessao, pUsr, 0);
		
		Parametro pSnh = new Parametro();
		pSnh.setDsParametro("FTP_SENHA");
		pSnh = ParametroService.getInstancia().get(sessao, pSnh, 0);
		
		FTPClient ftp = new FTPClient();
		try
		{
			ftp.connect(pServer.getVlParametro(), Integer.parseInt(pPort.getVlParametro()));
		    ftp.login(pUsr.getVlParametro(), pSnh.getVlParametro());
		    ftp.enterLocalPassiveMode();
		    
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
		    
		    File arquivoFtp = new File(nomeArquivo+".jpg");
		    InputStream inputStream = new FileInputStream(arquivoFtp);
		    
		    boolean done = ftp.storeFile("files/" + dsReal + ".jpg", inputStream);
		    inputStream.close();
		    
		    if(!done)
		    {
		    	throw new Exception("Ocorreu um erro no upload da imagem! Favor entrar em contato com a administração.");
		    }    
		}
		catch (Exception e)
		{
			throw e;
		}
		finally
		{
			try {
                if (ftp.isConnected()) {
                    ftp.logout();
                    ftp.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
		}
		
		return vo;
	}
	
	@Override
	protected Criteria montaCriteria(Session sessao, int join)
	{
		Criteria criteria = sessao.createCriteria(EstabelecimentoImagem.class);
		
		if ((join & JOIN_ESTABELECIMENTO) != 0)
	    {
			criteria.createAlias("estabelecimento", "estabelecimento");
	    }
		
		return criteria;
	}
	
	@Override
	@SuppressWarnings("rawtypes")
	protected Map restritores() 
	{
		return restritores;
	}

	@Override
	@SuppressWarnings("rawtypes")
	protected Map filtroPropriedade() 
	{
		return filtroPropriedade;
	}
}
