package nl.makeitwork.Showmaster.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

/**
 * @author ****
 * deze klasse handelt foutmeldingen af
 */

@Controller
public class ErrorsController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());


            if(statusCode == HttpStatus.FORBIDDEN.value()) {
                model.addAttribute("errorBericht", "U heeft geen toegang tot deze pagina");
                return "error";
            }
            else if(statusCode == HttpStatus.NOT_FOUND.value()) {
                model.addAttribute("errorBericht", "De pagina die u probeert te bereiken bestaat niet.");
                return "error";
            }
        }
        model.addAttribute("errorBericht", "Oeps er ging iets fout");
        return "error";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}

