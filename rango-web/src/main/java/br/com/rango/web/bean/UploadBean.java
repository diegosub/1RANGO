package br.com.rango.web.bean;

import java.util.Scanner;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import br.com.rango.ngc.entity.Estabelecimento;
import br.com.rango.ngc.entity.EstabelecimentoImagem;
import br.com.rango.ngc.service.EstabelecimentoImagemService;
import br.com.rango.ngc.service.EstabelecimentoService;
import br.com.rango.ngc.util.jsf.AbstractBean;
import br.com.rango.ngc.util.jsf.JSFUtil;


@RequestScoped
@ManagedBean
public class UploadBean extends AbstractBean<EstabelecimentoImagem, EstabelecimentoImagemService>
{
	private Part file;
	private Estabelecimento estabelecimento;
	
	private JSFUtil util = new JSFUtil();
	
	public UploadBean()
	{
		super(EstabelecimentoImagemService.getInstancia());
		this.ACTION_SEARCH = "upload";
		this.pageTitle = "Imagem";		
	}
	
	public String preparaUpload()
	{
		try
		{
			this.setCurrentState(STATE_EDIT);
			
			Estabelecimento estabelecimento = new Estabelecimento();
			estabelecimento.setIdEstabelecimento(util.getUsuarioLogado().getIdEstabelecimento());
			estabelecimento = EstabelecimentoService.getInstancia().get(estabelecimento, EstabelecimentoService.JOIN_CIDADE);
			
			this.setEstabelecimento(estabelecimento);
		}
		catch (Exception e)
		{
			FacesMessage message = new FacesMessage(e.getMessage());
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			if(e.getMessage() == null)
				FacesContext.getCurrentInstance().addMessage("", message);
			else
				FacesContext.getCurrentInstance().addMessage(null, message);
		}
		
		return ACTION_SEARCH;
	}
	
	public void validarArquivo(Part value) throws Exception
	{
        Part arquivo = (Part) value;

        if (arquivo.getSize() > (2*1024*1024))
        {
            throw new Exception("Arquivo muito grande. O arquivo deve ter o tamanho máximo de 2mb.");
        }

        if (!"image/jpg".equals(arquivo.getContentType())
        		&& !"image/jpeg".equals(arquivo.getContentType()))
        {
            throw new Exception("Tipo de arquivo inválido, O arquivo deve ser dos tipos: .JPG ou .JPEG");
        }
    }
	
	private String getFileName(Part part)
	{
		for (String cd : part.getHeader("content-disposition").split(";"))
		{
			if (cd.trim().startsWith("filename"))
			{
				return cd.substring(cd.indexOf('=') + 1).trim()
						.replace("\"", "");
			}
		}
		return null;
	}
	
	@SuppressWarnings("resource")
	public String importar()
	{
		try
		{	
			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			this.setFile(request.getPart("file"));
			
			if(this.getFileName(this.getFile()) == null
					|| this.getFileName(this.getFile()).equals(""))
			{
				throw new Exception("Selecione um arquivo para alterar a imagem.");
			}
			
			new Scanner(file.getInputStream()).useDelimiter("\\A").next();
			
			this.validarArquivo(file);
            
            EstabelecimentoImagem imagem = new EstabelecimentoImagem();
            imagem.setIdEstabelecimento(this.getEstabelecimento().getIdEstabelecimento());
            imagem.setEstabelecimento(this.getEstabelecimento());
            imagem.setDsImagem(this.getFileName(file));
            imagem.setDsTipo(file.getContentType());
            imagem.setVlTamanho(file.getSize());
            imagem.setFile(file);
            
            EstabelecimentoImagemService.getInstancia().salvarImagem(imagem);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Imagem atualizada com sucesso.", null));
        } 
		catch (Exception e)
		{
        	FacesMessage message = new FacesMessage(e.getMessage());
	        message.setSeverity(FacesMessage.SEVERITY_ERROR);
	        FacesContext.getCurrentInstance().addMessage(null, message);
        }
		
		return ACTION_SEARCH;
	}

	public Part getFile() {
		return file;
	}

	public void setFile(Part file) {
		this.file = file;
	}

	public Estabelecimento getEstabelecimento() {
		return estabelecimento;
	}

	public void setEstabelecimento(Estabelecimento estabelecimento) {
		this.estabelecimento = estabelecimento;
	}
}
