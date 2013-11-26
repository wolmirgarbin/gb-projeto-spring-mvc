package br.com.viasoft.portaldef.job;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.viasoft.portaldef.configure.ConfigureApp;
import br.com.viasoft.portaldef.entities.Contratante;
import br.com.viasoft.portaldef.service.ContratanteService;
import br.com.viasoft.portaldef.service.DfeService;
import br.com.viasoft.portaldef.service.MailSendService;
import br.com.viasoft.portaldef.web.to.EmpresaTO;
import br.com.viasoft.util.DateUtil;


@Service
public class JobNotificaUso {

	private static final String TITULO = "Informativo de cota X pacote | Portal DF-e";

	@Autowired
	private ContratanteService contratanteService;

	@Autowired
	private DfeService dfeService;

	@Autowired
	private MailSendService mailSendService;


	public void execute(){
		final List<Contratante> lsContratante = contratanteService.findAll();
		final Date current = new Date();
		final Integer dia = DateUtil.day( current );
		final Integer mes = DateUtil.month( current );
		final Integer ano = DateUtil.year( current );

		for (final Contratante contratante : lsContratante) {
			final List<EmpresaTO> relConsumoMes = dfeService.relConsumoMes(mes, ano, contratante.getId());

			Integer soma = 0;
			for (final EmpresaTO empresaTO : relConsumoMes) {
				soma += empresaTO.getQuantidade();
			}

			// verifica se a soma já alcançou 80% do pacote
			if( contratante.getPacote() != null && contratante.getPacote() > 0 ) {

				final Double percentual = (double) ((soma * 100) / contratante.getPacote());

				if( percentual >= 80 ) {
					final StringBuilder send = new StringBuilder();

					send.append( "<br>" );
					send.append( "Seu pacote de arquivos no portal DF-e é de: " ).append( contratante.getPacote() );
					send.append( "<br>" );
					send.append( "A quantidade de arquivos carregados no sistema até o momento foi de " ).append( soma ).append(" documentos, já foi utilizado ").append(percentual).append("% da cota.");
					send.append( "<br>" );

					/*send.append("<table>");
					for (final EmpresaTO empresaTO : relConsumoMes) {
						soma += empresaTO.getQuantidade();
						send.append("<tr>");
							send.append("<td>");
								send.append( empresaTO.getNome() ).append( " ( " ).append( empresaTO.getIdentificacao() ).append(" )");
							send.append("</td>");
							send.append("<td>");
								send.append( empresaTO.getQuantidade() );
							send.append("</td>");
						send.append("</tr>");
					}
					send.append("</table>");*/

					mailSendService.sendRelatorioCotaMensal(new String[]{ contratante.getEmail() }, ConfigureApp.getInstance().getEmailPadrao(), TITULO, send.toString());
				}
			}
		}
	}

}