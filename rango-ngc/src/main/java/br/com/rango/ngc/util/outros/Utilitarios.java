package br.com.rango.ngc.util.outros;

import java.io.File;

public class Utilitarios
{
	public static void removerArquivos(String dir)
	{
		File file = new File(dir);
		
		if (file.isDirectory())
		{
            File[] files = file.listFiles();
            
            for (File f : files)
            {
                    f.delete();
            }
		}
	}
	
	public static boolean horaInicioMaior(String h1, String h2)
	{
		boolean flag = false;
		
		int horaInicio = Integer.parseInt(h1.replace(":", ""));
		int horaFim = Integer.parseInt(h2.replace(":", ""));
		
		if(horaInicio > horaFim)
		{
			flag = true;
		}
		
		return flag;
	}
}
