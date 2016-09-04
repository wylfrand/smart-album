package com.mycompany.smartalbum.back.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mycompany.smartalbum.back.resetPassword.ResetPwdForm;

@Controller
@RequestMapping("/oublieMotDePasse")
public class ResetPwdController extends ABaseController{
    
    // LOGS
	private final static transient Logger LOG = LoggerFactory.getLogger(AlbumsController.class);
    
    
    /**
     * Temporary method to call popupView.
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/popup")
    public ModelAndView popupView(final HttpServletRequest request) {
        final String urlPopupResetPwd = request.getContextPath() + "/xxx.html";
        request.setAttribute("layerResetPwdPath", urlPopupResetPwd);
        return new ModelAndView("");
    }
    
    /**
     * Call first page of reinitialisation for all users.
     * 
     * @param request
     * @param response
     * @return ModelAndView : LOGIN_VIEW
     */
    @RequestMapping("/identifiant")
    public ModelAndView loginView(final HttpServletRequest request, final HttpServletResponse response) {
        //this.logger.debug("Entree dans la methode ResetPwdController.loginView()");
       // mAv.addObject("resetPwdForm", new ResetPwdForm());
        return null;
        
    }
    
    /**
     * Called when mobile users forget their secret question.
     * 
     * @param request
     * @param response
     * @return ModelAndView : FORGET_QUESTION_VIEW
     */
    @RequestMapping("/oubliQuestionSecrete")
    public ModelAndView forgetQuestionView(final HttpServletRequest request, final HttpServletResponse response) {
       
        return null;
    }
    
    /**
     * Check the user profile (ADSL/MOBILE). Initialize resetPwd process.
     * 
     * @param resetPwdForm
     * @param result
     * @param request
     * @param response
     * @return ModelAndView : : Return the appropriate view in case of success or error
     */
    @RequestMapping("/envoi")
    public ModelAndView startResetPwd(@ModelAttribute("resetPwdForm") final ResetPwdForm resetPwdForm,
                                      final BindingResult result, final HttpServletRequest request,
                                      final HttpServletResponse response) {

                // Cas nominal=> lancement du process de reinitialisation
                //viewForward = this.resetPwdUIService.startResetPwd(login, resetPwdForm, result, request);
               // return viewForward;
    	return null;
    }
    
    /**
     * Validate secret question answer for mobile users and reinitialize Pwd.
     * 
     * @param resetPwdForm
     * @param result
     * @param request
     * @param response
     * @return ModelAndView : : Return the appropriate view in case of success or error
     */
    @RequestMapping("/confirmation")
    public ModelAndView validateAnswer(@ModelAttribute("resetPwdForm") final ResetPwdForm resetPwdForm,
                                       final BindingResult result, final HttpServletRequest request,
                                       final HttpServletResponse response) {
//        this.logger.debug("Appel a la methode ResetPwdController.validateAnswer()");
//        ModelAndView viewForward = null;
//        this.resetPwdUIServiceUtils.initViews(request);
//        final String questionView = this.resetPwdUIServiceUtils.getQuestionView();
//        
//        this.determineReturnUrl(request);
//        new SecretQuestionFormValidation().validate(resetPwdForm, result);
//        if (result.hasErrors()) {
//            viewForward = new ModelAndView(questionView);
//            return viewForward;
//        }
//        viewForward = this.resetPwdUIService.validateNotificationAnswer(resetPwdForm, result, request);
//        return viewForward;
    	return null;
    }
    
    /**
     * Validate user answer and reinit via SMS.
     * 
     * @param resetPwdForm
     * @param result
     * @param request
     * @param response
     * @return ModelAndView : : Return the appropriate view in case of success or error
     */
    @RequestMapping("/validerEnvoiSMS")
    public ModelAndView sendBySMS(@ModelAttribute("resetPwdForm") final ResetPwdForm resetPwdForm,
                                  final BindingResult result, final HttpServletRequest request,
                                  final HttpServletResponse response) {
//        this.logger.debug("Appel a la methode ResetPwdController.validateAnswer()");
//        ModelAndView viewForward = null;
//        this.resetPwdUIServiceUtils.initViews(request);
//        this.determineReturnUrl(request);
//        viewForward = this.resetPwdUIService.validateNotificationAnswer(resetPwdForm, result, request);
//        return viewForward;
    	return null;
    }

	@Override
	protected Logger getLoger() {
		return LOG;
	}
}
