(function($){
    $.fn.gmTable = function(settings){
        var config = {
            version	: '1.0'
        };
        if (settings){$.extend(config, settings);}

        // para cada tabela
        return this.each(function(){
            var $this = $(this);
            
            // ajusta o ultimo titulo para não deixar a borda
            $this.find("th span:last").css("border-right", "none");
            
            // show all
            $this.find(".show-all").unbind("click").bind('click',function(){
            	var $more = $(this);
            	
            	// mostra a linha oculta
            	var $lines = $more.parents("table").find(".line");
            	var $icons = $more.parents("table").find(".ui-icon");
            	var $showLine = $more.parents("table").find(".show-line");
            	if( $more.hasClass("ui-icon-more") ) {
            		$lines.removeClass("border");
            		$showLine.show();
            		$icons.removeClass("ui-icon-more").addClass("ui-icon-less");
            		$more.removeClass("ui-icon-more").addClass("ui-icon-less");
            	} else {
            		$lines.addClass("border");
            		$showLine.hide();
            		$icons.removeClass("ui-icon-less").addClass("ui-icon-more");
            		$more.removeClass("ui-icon-less").addClass("ui-icon-more");
            	}
            });
            
            // evento para abrir os detalhes da linha
            $this.find(".expand-row").unbind("click").bind('click',function(){
            	var $more = $(this);
            	
            	// mostra a linha oculta
            	var $parentLine = $more.parents(".line");
            	var $showLine = $more.parents(".line").next(".show-line");
            	if( $showLine.is(":hidden") ) {
            		$parentLine.removeClass("border");
            		$showLine.show();
            		$more.removeClass("ui-icon-more").addClass("ui-icon-less");
            	} else {
            		$showLine.hide();
            		$parentLine.addClass("border");
            		$more.parents("table").find(".show-all").removeClass("ui-icon-less").addClass("ui-icon-more");
            		$more.removeClass("ui-icon-less").addClass("ui-icon-more");
            	}
            });
            
            // icones de download
            $this.find(".chama-download").unbind("click").bind('click',function(){
            	window.open(applicationPath +"json/download-arquivos/"+ $(this).data("id"));
            });
            
            
            // enviar email para usu�rio
            $this.find(".send-mail").unbind("click.carregamentoLista").bind('click.carregamentoLista',function(){
            	var $modalEnviarNotas = $("#modalEnviarNotas");
        		$modalEnviarNotas.find(".id-documentos").val( $(this).data("id") );
        		$modalEnviarNotas.find(".email-principal").val( $(this).data("mail") );
        		$modalEnviarNotas.find(".email-principal").next("span").html( $(this).data("mail") );
            }).gmModal({ tamanho: 660, render: '#modalEnviarNotas', title: 'Envio de e-mail' });
            
            
            $this.find(".tooltip-item-table").tipsy({gravity: 's'});
            
            $this.find("#checkAll").unbind("click").bind("click", function(){
            	if( $(this).is(':checked') ){
            		$("#barraDownload").show();
            		$this.find(".checkbox").each(function() {this.checked = true;});
            	} else {
            		$("#barraDownload").hide();
            		$this.find(".checkbox").each(function() {this.checked = false;});
            	}
            });
        });
    };
    
    $.fn.gmTableUsuario = function(settings){
        var config = {
            version	: '1.0'
        };
        if (settings){$.extend(config, settings);}

        // para cada tabela
        return this.each(function(){
            var $this = $(this);
            
            // ajusta o ultimo titulo para não deixar a borda
            $this.find("th span:last").css("border-right", "none");
            
            // evento para abrir os detalhes da linha
            $this.find(".save-email").unbind("change").bind('change',function(){
            	var $email = $(this);
            	
            	var param = {};
            	param.email = $email.val();
            	param.id = $email.data("id");
            	
            	$.ajax({
            		type: "GET",
            		url: applicationPath +"json/update-email-user",
            		data: param,
            		dataType: "json"
            	}).done(function(json) {
            		if( json.sucesso ) {
            			// controle de mostra linha em amarelo ou branca
            			if( json.salvouNull ) {
            				$email.parents(".line").addClass("active");
            			} else {
            				$email.parents(".line").removeClass("active");
            			}
            			// controle de motrar links no topo da tabela
            			if( json.quantidadeUsuarioSemEmail > 0 ) {
            				$(".mostrar-apenas-email").show();
            			} else {
            				$(".mostrar-apenas-email").hide();
            			}
            		} else {
            			alert("Erro ao atualizar e-mail");
            		}
            	}).fail(function(){
            		alert("Erro ao atualizar e-mail");
            	});
            });
        });
    };
})(jQuery);