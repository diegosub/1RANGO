package br.com.rango.ngc.util.outros.email;

public class CadastroEmail
{	
	public static String getEmailCadastro(String nome) 
	{
		String msg = "";

		msg += 	"<html><head><title>1Rango!</title></head><body><table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"                                                                                                                               ";
		msg += 	"	style=\"width: 600px\">                                                                                                                                                                     ";
		msg += 	"	<tbody>                                                                                                                                                                                     ";
		msg += 	"		<tr>                                                                                                                                                                                    ";
		msg += 	"			<td bgcolor=\"#c7191a\" style=\"padding: 10px;\">                                                                                                                                   ";
		msg += 	"				<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"                                                                                                                         ";
		msg += 	"					style=\"width: 100%\">                                                                                                                                                      ";
		msg += 	"					<tbody>                                                                                                                                                                     ";
		msg += 	"						<tr>                                                                                                                                                                    ";
		msg += 	"							<td style=\"width: 58px; padding-top: 10px; padding-bottom: 10px; text-align: center;\"><img src=\"www.1rango.com.br/img/mail_cad/logo1rango.png\" border=\"0\"></td>               							";
		msg += 	"						</tr>                                                                                                                                                                   ";
		msg += 	"					</tbody>                                                                                                                                                                    ";
		msg += 	"				</table>                                                                                                                                                                        ";
		msg += 	"			</td>                                                                                                                                                                               ";
		msg += 	"		</tr>                                                                                                                                                                                   ";
		msg += 	"		<tr>                                                                                                                                                                                    ";
		msg += 	"			<td>                                                                                                                                                                                ";
		msg += 	"				<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"                                                                                                        ";
		msg += 	"					style=\"width: 512px\">                                                                                                                                                     ";
		msg += 	"					<tbody>                                                                                                                                                                     ";
		msg += 	"						<tr>                                                                                                                                                                    ";
		msg += 	"							<td style=\"font-family: Times; color: black; font-size: 30px; font-weight: bold; font-style: italic; text-align: center; padding-top: 15px; padding-bottom: 15px; \">                   ";
		msg += 	"								Boas vindas                                                                                                                                                     ";
		msg += 	"							</td>                                                                                                                                                               ";
		msg += 	"						</tr>                                                                                                                                                                   ";
		msg += 	"						<tr>                                                                                                                                                                    ";
		msg += 	"							<td><img src=\"www.1rango.com.br/img/mail_cad/mail-cad-1.jpg\" border=\"0\" width=\"512\" style=\"width: 512px;\"></td>												";
		msg += 	"						</tr>                                                                                                                                                                   ";
		msg += 	"						<tr>                                                                                                                                                                    ";
		msg += 	"							<td style=\"font-family: Times; color: #777777; font-size: 20px; padding-top: 15px; text-align: center\">                                                          ";
		msg += 	"								Olá <b>"+nome+"</b>, seja bem vindo ao 1Rango! <br/>                                                                                                        	";
		msg += 	"								É um prazer ter você em nossa comunidade. <br/>                                                                                                                 ";
		msg += 	"								Faça seus pedidos sempre que bater aquela fome. <br/>                                                                                                           ";
		msg += 	"								Nunca esqueça.. Tá com fome!? Pede 1Rango!                                                                                                                      ";
		msg += 	"							</td>                                                                                                                                                               ";
		msg += 	"						</tr>                                                                                                                                                                   ";
		msg += 	"						<tr>																																									";
		msg += 	"							<td align=\"center\" style=\"padding: 10px;\"><a href=\"www.1rango.com.br\" target=\"_blank\"><img src=\"www.1rango.com.br/img/mail_cad/mail-cad-2.jpg\" border=\"0\" ></td>                                        ";
		msg += 	"						</tr>                                                                                                                                                                   ";
		msg += 	"					</tbody>                                                                                                                                                                    ";
		msg += 	"				</table>                                                                                                                                                                        ";
		msg += 	"			</td>                                                                                                                                                                               ";
		msg += 	"		</tr>                                                                                                                                                                                   ";
		msg += 	"		<tr>                                                                                                                                                                                    ";
		msg += 	"			<td bgcolor=\"#c7191a\">                                                                                                                                                            ";
		msg += 	"				<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"                                                                                                                         ";
		msg += 	"					style=\"width: 600px\">                                                                                                                                                     ";
		msg += 	"					<tbody>                                                                                                                                                                     ";
		msg += 	"						<tr>                                                                                                                                                                    ";
		msg += 	"							<td style=\"font-family: Arial; color: #ffffff; font-size: 11px; padding: 10px;\">                                                                                  ";
		msg += 	"								@1Rango! - Todos os direitos reservados.                                                                                                                        ";
		msg += 	"							</td>                                                                                                                                                               ";
		msg += 	"							<td style=\"width: 150px\">                                                                                                                                         ";
		msg += 	"								<table align=\"right\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"                                                                                         ";
		msg += 	"									style=\"width: 100px\">                                                                                                                                     ";
		msg += 	"									<tbody>                                                                                                                                                     ";
		msg += 	"										<tr>                                                                                                                                                    ";
		msg += 	"											<td><img src=\"www.1rango.com.br/img/mail_cad/4.jpg\" border=\"0\"></td> 																			";
		msg += 	"											<td><img src=\"www.1rango.com.br/img/mail_cad/5.jpg\" border=\"0\"></td> 																			";
		msg += 	"											<td><img src=\"www.1rango.com.br/img/mail_cad/6.jpg\" border=\"0\"></td> 																			";
		msg += 	"										</tr>                                                                                                                                                   ";
		msg += 	"									</tbody>                                                                                                                                                    ";
		msg += 	"								</table>                                                                                                                                                        ";
		msg += 	"							</td>                                                                                                                                                               ";
		msg += 	"						</tr>                                                                                                                                                                   ";
		msg += 	"					</tbody>                                                                                                                                                                    ";
		msg += 	"				</table>                                                                                                                                                                        ";
		msg += 	"			</td>                                                                                                                                                                               ";
		msg += 	"		</tr>                                                                                                                                                                                   ";
		msg += 	"	</tbody>                                                                                                                                                                                    ";
		msg += 	"</table> </body></html>                                                                                                                                                                        ";

		return msg;
		
		
	}
}