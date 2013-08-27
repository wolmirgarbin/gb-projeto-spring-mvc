(function($){
    
	$.fn.gmModal = function(settings){
        var config = {
            version	: '1.0',
            idHtmlModal : '#gmModal',
            tamanho : 500,
            render : '',
            click : undefined,
            complete : undefined,
            title : 'Modal'
        };
        if (settings){$.extend(config, settings);}

        // para cada tabela
        return this.each(function(){
            if( typeof config.idHtmlModal != 'undefined' ) {
            	
            	var $this = $(config.idHtmlModal);
            	
            	$(this).unbind("click.gmModal").bind("click.gmModal", function(){
            		
            		if( typeof config.click == 'function' ) {
            			config.click();
            		}
            		
            		// adiciona o titulo
            		$this.find(".mod-titulo").html( config.title );
            		
            		// organiza as telas dentro da modal e tamanho
            		$this.find(".conteudo-interno").css("width", config.tamanho +"px");
            		$this.find(".mod-html").css("width", (config.tamanho - 40) +"px");
            		$this.find(".mod-html > div").hide();
            		
            		// mostra a modal
            		$this.show();
            		$this.find( config.render ).show();
            		
            		// eventos para fechamento da modal
            		$this.find(".background-transparente").unbind("click").bind("click",function(){
            			$this.hide();
            		});
            		$this.find(".mod-close").unbind("click").bind("click",function(){
            			$this.hide();
            		});
            		
            		// complete
            		if( typeof config.complete == 'function' ) {
            			config.complete();
            		}
            	});
            }
        });
    };
    
})(jQuery);