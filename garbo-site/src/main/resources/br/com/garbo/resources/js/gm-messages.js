var $messageValidator = undefined;

$(document).ready(function(){
	
	$messageValidator = $("#messageValidator");
	
	if( $.trim($messageValidator.find(".mes-message").html()) != "" ) {
		showMessage();
	}
});

// mostra a mensagem
function showMessage(){
	$messageValidator.fadeIn("slow", function(){
		window.setTimeout(
			function(){ 
				$messageValidator.fadeOut("slow");
			}, 
			7000
		);
	});
}

// adiciona uma mensagem na tela
function addMessageAlert(texto) {
	$messageValidator.find(".mes-message").html(texto);
	$messageValidator.find(".mes-title").removeClass("sucesso");
	showMessage();
}

//adiciona uma mensagem na tela
function addMessageSuccess(texto) {
	$messageValidator.find(".mes-message").html(texto);
	$messageValidator.find(".mes-title").addClass("sucesso");
	showMessage();
}