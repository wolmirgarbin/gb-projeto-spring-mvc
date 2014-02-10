<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<div class="capa-area">
	<div class="container clearfix">
		<div class="texto-index">
			<h1 class="texto-h1">O portal corporativo que faltava em sua empresa.</h1>
		</div>
		<form method="POST" class="capa-form-acesso" autocomplete="off" action="${ applicationPath }" accept-charset="UTF-8">
	        <div class="field">
	        	<input type="text" placeholder="Seu e-mail" name="usuario">
	        </div>
	        <div class="field">
	        	<input type="password" placeholder="Sua senha" name="senha">
	        </div>
	        <input type="checkbox" name="manterConectado" id="conectado">
	        <label for="conectado" class="colorf">Mantenha-me conectado no sistema.</label>
	        <div class="field">
	        	<button type="submit" class="button primary button-block">Acessar</button>
	        </div>
	        <p class="colorf">Clicando em "Acessar", você está aceitando 
	          <a target="_blank" href="#">termos do serviço</a> e nossa 
	          <a target="_blank" href="#">politica de privacidade</a>.
	        </p>
		</form>
		
		<div class="capa-texto">
		
		</div>
	</div>
</div>
<div class="capa-banner">
	
</div>