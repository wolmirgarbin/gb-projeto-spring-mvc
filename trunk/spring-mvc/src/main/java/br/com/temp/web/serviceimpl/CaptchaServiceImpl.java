package br.com.viasoft.portaldef.serviceimpl;

import java.awt.image.BufferedImage;

import nl.captcha.Captcha;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import br.com.viasoft.portaldef.service.CaptchaService;

@Service
public class CaptchaServiceImpl implements CaptchaService {

	private static final long serialVersionUID = -3309037834041601149L;
	
	private static final String KEY_SESSION_CAPTCHA = "chave.session.captcha.portal.dfe";
	
	@Override
	public BufferedImage createCaptcha() {
		
		// cria o captcha
		final Captcha captcha = new Captcha.Builder(150, 38)
			.addText()
			.build();
		
		// adiciona na sessão
		RequestContextHolder.getRequestAttributes().setAttribute(KEY_SESSION_CAPTCHA, captcha, RequestAttributes.SCOPE_SESSION);
		
		return captcha.getImage();
		
		
		/**
	     * Write out the CAPTCHA image stored in the session. If not present,
	     * generate a new <code>Captcha</code> and write out its image.
	     * 
	     
	    @Override
	    public void doGet(HttpServletRequest req, HttpServletResponse resp)
	            throws ServletException, IOException {
	        HttpSession session = req.getSession();
	        Captcha captcha;
	        if (session.getAttribute(NAME) == null) {
	            captcha = new Captcha.Builder(_width, _height)
	              .addText()
	              .gimp()
	              .addBorder()
	                .addNoise()
	                .addBackground()
	                .build();

	            session.setAttribute(NAME, captcha);
	            CaptchaServletUtil.writeImage(resp, captcha.getImage());

	            return;
	        }

	        captcha = (Captcha) session.getAttribute(NAME);
	        CaptchaServletUtil.writeImage(resp, captcha.getImage());
	    }*/
	}

	
	@Override
	public boolean isCorrect(String answer) {
		// paga da sessão
		final Captcha captcha = (Captcha)RequestContextHolder.getRequestAttributes().getAttribute(KEY_SESSION_CAPTCHA, RequestAttributes.SCOPE_SESSION);
		
		if( captcha != null ) {
			// remove da sessão
			RequestContextHolder.getRequestAttributes().removeAttribute(KEY_SESSION_CAPTCHA, RequestAttributes.SCOPE_SESSION);
			// retorna se está correto
			return captcha.isCorrect(answer);
		}
		
		return false;
	}

}