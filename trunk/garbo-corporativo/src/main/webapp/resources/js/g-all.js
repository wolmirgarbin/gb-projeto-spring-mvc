var $dataTableHome = undefined;
var $dataTableUsuarios = undefined;

$(document).ready(function(){
	
	
	// evento que deixa o menu no topo do site
	var $containerDownload = $('#barraDownload').find(".container");
	if( $containerDownload.size() > 0 ) {
		var posTop = $("#table-list").offset().top;
		
		$(window).scroll(function(e) {
	    	if( $(this).scrollTop() >= posTop) {
	    		$containerDownload.addClass('fixo-topo shadow-black');
	    	} else {
	    		$containerDownload.removeClass('fixo-topo shadow-black');
	    	}
	    });
	}
	
	$("#menuEmpresas").hover(function(){
		$(this).find(".menu").show();
	}, function(){
		$(this).find(".menu").hide();
	});
	
	// customizar data
	$.datepicker.regional['pt-BR'] = {
            closeText: 			'Fechar',
            prevText: 			'&#x3c;Anterior',
            nextText: 			'Pr&oacute;ximo&#x3e;',
            currentText: 		'Hoje',
            monthNames: 		['Janeiro','Fevereiro','Mar&ccedil;o','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'],
            monthNamesShort:	['Jan','Fev','Mar','Abr','Mai','Jun','Jul','Ago','Set','Out','Nov','Dez'],
            dayNames: 			['Domingo','Segunda-feira','Ter&ccedil;a-feira','Quarta-feira','Quinta-feira','Sexta-feira','Sabado'],
            dayNamesShort: 		['Dom','Seg','Ter','Qua','Qui','Sex','Sab'],
            dayNamesMin: 		['Dom','Seg','Ter','Qua','Qui','Sex','Sab'],
            weekHeader: 		'Sm',
            dateFormat: 		'dd/mm/yy',
            firstDay: 			0,
            isRTL: 				false,
            showMonthAfterYear: false,
            yearSuffix: 		'',
            showOn: 			"button",
    		buttonImage: 		applicationPath +"resources/ui-theme-agro/images/calendar.png",
    		buttonImageOnly: 	true};
    $.datepicker.setDefaults($.datepicker.regional['pt-BR']);
    
	$(".date").datepicker();
	
	
	// numeros
	$(".numeric").setMask("numeric");
	$(".site").setMask("site");
	$(".mail").setMask("email");
	
	
	// modal
	$("#opemModalUpload").gmModal({tamanho : 660, render: '#modalUploadNotas', title: 'Upload de documentos fiscais'});
	
	
	// table home
	$dataTableHome = $("#table-list");
	if( $dataTableHome.size() > 0 ) {
		atualizaLista(true);
		
		// evento do btn de pesquisar
		$("#pesquisarFiltroHome").click(function(){
			atualizaLista(true);
		});
		$("#qtdRegistros").change(function(){
			atualizaLista(true);
		});
		
		// evento do btn de mais resultados
		$("#carregarMaisRegistrosHome").click(function(){
			atualizaLista(false);
		});
	}
	
	// download de varias notas ao mesmo tempo
	$("#commandDownload").bind("click", function(){
		var param = {};
		
		param.docXml 		= $("#doc-xml").is(":checked");
		param.docDanfe 		= $("#doc-danfe").is(":checked");
		param.docEventos	= $("#doc-eventos").is(":checked");
		
		// validações
		if( param.docXml == false && param.docDanfe == false && param.docEventos == false ) {
			addMessageAlert("Precisa ter pelo menos um tipo de arquivo selecionado!");
		} else {
			// pega os valores selecionado
			param.ids = "";
			var contador = 0;
			$("#table-list").find(".checkbox:checked").each(function(){
				contador++;
				if( param.ids != "" )
					param.ids += ","+ this.value;
				else
					param.ids += this.value;
			});
			
			if( contador == 0 ) {
				addMessageAlert("Não tem nenhum arquivo selecionado!");
			} else if( contador > 100 ) {
				addMessageAlert("Não pode fazer o download de mais de 100 arquivos!");
			} else {	
				var url = applicationPath +"download/grupo-documentos?";
				url += 'docXml='+ param.docXml;
				url += '&';
				url += 'docDanfe='+ param.docDanfe;
				url += '&';
				url += 'docEventos='+ param.docEventos;
				url += '&';
				url += 'ids='+ param.ids;
				
				window.open( url );
			}
		}
    });
	
	
	
	// tabela de usuarios
	$dataTableUsuarios = $("#table-usuarios");
	if( $dataTableUsuarios.size() > 0 ) {
		$dataTableUsuarios.gmTableUsuario();
		
		$("#pesquisarUsuarios").click(function(){
			atualizaListaUsuarios(true);
		});
		$("#carregarMaisRegistrosUsuario").click(function(){
			atualizaListaUsuarios(false);
		});
	}
	
	// clicar para enviar o email após aberta a modal
	var $modalEnviarNotas = $("#modalEnviarNotas");
	$modalEnviarNotas.find("button").click(function(){
		
		if( ! $(this).hasClass("disabled") ) {
			
			// para aguardar a requisição
			$(this).addClass("disabled");
			$("#loaderEnvioEmail").css("display","inline-block");
			
			var param = {};
			param.nota = $modalEnviarNotas.find(".id-documentos").val();
			
			if( $modalEnviarNotas.find(".email-principal").is(":checked") ) {
				param.emailPrincipal = $modalEnviarNotas.find(".email-principal").val();
			} else {
				param.emailPrincipal = null;
			}
			param.cc = [];
			$modalEnviarNotas.find(".email-cc").each(function(){
				if( $(this).val() != '' ) {
					param.cc.push( $(this).val() );
				}
			});
			
			$.ajax({
				type: "GET",
				url: applicationPath +"json/enviar-docs",
				data: param,
				dataType: "json",
				traditional:true,
				cache:true,
				contentType:"application/x-www-form-urlencoded; charset=UTF-8",
				timeout:45000
			}).done(function(json) {
				$('#gmModal').fadeOut("slow");
				
				$("#loaderEnvioEmail").css("display","none");
				$("#modalEnviarNotas").find("button").removeClass("disabled");
				
				if( json.permite ) {
					addMessageSuccess( json.mensagem );
				} else {
					addMessageAlert("Não tem permissão para enviar este arquivo!");
				}
			}).fail(function(){ 
				addMessageAlert("Erro ao enviar e-mail!");
			});
		}
	});
});


// TABELA DA HOME
function atualizaLista(clear) {
	if( clear ) {
		// limpa a tabela para nova pesquisa
		$dataTableHome.find("tbody").html("");
	}
	
	// cria os filtros
	var filter = {};
	filter.apartirDe = $dataTableHome.find(".line").size();
	filter.dataInicio = $("#dataInicial").val();
	filter.dataFim = $("#dataFinal").val();
	filter.situacao = $("#situacao").val();
	filter.nome = $("#nome").val();
	filter.identificacao = $("#identificacao").val();
	filter.tipoBusca = $("#tipoBusca").val();
	filter.chave = $("#chave").val();
	filter.qtdRegistros = $("#qtdRegistros").val();
	
	$.ajax({
		type: "GET",
		url: applicationPath +"json/nfe-cliente",
		data: filter,
		dataType: "json",
		traditional:true,
		cache:true,
		contentType:"application/x-www-form-urlencoded; charset=UTF-8",
		timeout:45000
	}).done(function(json) {
		$dataTableHome.find("tbody").append(template(json));
		$dataTableHome.gmTable();
		$dataTableHome.find(".checkbox").bind("click", function(){
			if( $(this).is(':checked') ){
				$("#barraDownload").show();
			} else {
				$("#checkAll").attr("checked", false);
				if( $dataTableHome.find(".checkbox:checked").length <= 0 ) {
					$("#barraDownload").hide();
				}
			}
		});
	}).fail(function(){ });
}

// template utilizado para a lista de NFes da home
function template(json) {
	var html = "";
	if( json.notas.length == 0 ) {
		
		$("#carregarMaisRegistrosHome").hide();
		
		html += "<tr class='line border'>";
		html += 	"<td colspan='6'>Não foram encontrados registros de acordo com a pesquisa</td>";
		html += "</tr>";
	} else {
		
		$("#carregarMaisRegistrosHome").show();
		
		for( i in json.notas ) {
			var nota = json.notas[i];
			html += "<tr class='line border'>";
			html += 	"<td>";
			html +=			"<span class='ui-icon black ui-icon-more expand-row'></span>";
			html +=			"<span class='text'>"+ nota.numero +"</span>";
			html += 	"</td>";
			html += 	"<td><span class='text center'>"+ nota.dtEmissao +"</span></td>";
			html += 	"<td><span class='text center'>"+ nota.carregamento +"</span></td>";
			html += 	"<td><span class='text icon-status tooltip-item-table' original-title='"+ nota.motivo +"'>"+ nota.status +"</span></td>";
			html += 	"<td><span class='text center'>"+ nota.situacao +"</span></td>";
			html += 	"<td><span class='text'>"+ nota.destinatario +"</span></td>";
			html += 	"<td><span class='text center'>"+ nota.identificacao +"</span></td>";
			html += 	"<td><input class='checkbox' type='checkbox' value='"+ nota.id +"'></td>";
			html += "</tr>";
			html += "<tr class='show-line border'>";
			html += 	"<td colspan='8'>";
			html += 		"<div class='parte'>";
			html += 			"<span class='title'>Download NF-e</span>";
			html += 			"<span class='value'><a href='javascript:void(0)' data-id='"+ nota.idNfe +"' class='d-xml chama-download'></a></span>";
			html += 		"</div>";
			
			// caso não tenha a danf não mostra a opção para download
			if( nota.idDanf != null ) {
				html += 		"<div class='parte'>";
				html += 			"<span class='title'>Download DANFE</span>";
				html += 			"<span class='value'><a href='javascript:void(0)' data-id='"+ nota.idDanf +"' class='d-pdf chama-download'></a></span>";
				html += 		"</div>";
			}
			
			if( nota.eventos != null ) {
				for( evt in nota.eventos ) {
					var evento = nota.eventos[evt];
					html += 		"<div class='parte'>";
					html += 			"<span class='title'>"+ evento.descricao +"</span>";
					html += 			"<span class='value'><a href='javascript:void(0)' data-id='"+ evento.id +"' class='d-xml chama-download'></a></span>";
					html += 		"</div>";
				}
			}
			
			// caso não tenha email não mostra a opção de reenviar
			//if( nota.email != '-' ) {
				html += 		"<div class='parte'>";
				html += 			"<span class='title'>Reenviar e-mail</span>";
				html += 			"<span class='value'><a href='javascript:void(0)' data-id='"+ nota.id +"' data-mail='"+ nota.mailPrincipal +"' title='Clique para escolher o e-mail a enviar' class='d-email send-mail'></a></span>";
				html += 		"</div>";
			//}
			
			html += 	"</td>";
			html += "</tr>";
		}
	}
	return html;
}



// TABELA DE USUARIOS
function atualizaListaUsuarios(clear) {
	
	if( clear ) {
		$dataTableUsuarios.find("tbody").html("");
	}
	
	// cria os filtros
	var filter = {};
	filter.apartirDe = $dataTableUsuarios.find(".line").size();
	filter.nome = $("#nome").val();
	filter.identificacao = $("#identificacao").val();
	
	
	var $ajaxUsuario = $.ajax({
		type: "GET",
		url: applicationPath +"json/list-usuarios",
		data: filter,
		dataType: "json",
		traditional:true,
		cache:true,
		contentType:"application/x-www-form-urlencoded; charset=UTF-8",
		timeout:45000
	}).done(function(json) {
		$dataTableUsuarios.find("tbody").append(templateUsuario(json));
		$dataTableUsuarios.gmTableUsuario();
		
		if( json.countPessoas > 0 && $dataTableUsuarios.find(".line").size() < json.countPessoas ){
			$("#carregarMaisRegistrosUsuario").show();
		} else {
			$("#carregarMaisRegistrosUsuario").hide();
		}
	}).fail(function(){
		
	});
}

function templateUsuario(json) {
	var html = "";
	
	if( json.usuarios.length == 0 ) {
		$("#carregarMaisRegistrosUsuario").hide();
		html += "<tr class='line border'>";
		html += 	"<td style='text-align: left;' colspan='3' alig>Não foram encontrados registros de acordo com a pesquisa</td>";
		html += "</tr>";
	} else {
		$("#carregarMaisRegistrosUsuario").show();
		for( i in json.usuarios ) {
			var usuario = json.usuarios[i];
			html += "<tr class='line border"+ (usuario.email == null || usuario.email == "" ? ' active' : '' ) +"'>";
			html += 	"<td class='nome'><span>"+ usuario.nome +"</span></td>";
			html += 	"<td><span>"+ usuario.identificacao +"</span></td>";
			html += 	"<td><input class='save-email' type='text' data-id='"+ usuario.id +"' value='"+ (usuario.email == null ? '' : usuario.email ) +"'></td>";
			html += "</tr>";
		}
	}
	return html;
}