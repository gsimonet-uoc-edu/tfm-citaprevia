package uoc.edu.citaprevia.front.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import uoc.edu.citaprevia.util.Utils;

@Controller
public class LanguageController {

    @RequestMapping("/changeLang")
    public String changeLanguage(@RequestParam String lang,
                                 HttpServletRequest request,
                                 HttpServletResponse response) {

        // Canvi d'idioma
        LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
        if (localeResolver != null) {
            localeResolver.setLocale(request, response, new Locale(lang));
        }

        // Neteja del referer
        String referer = request.getHeader("Referer");
        String redirectTo = "/";

        if (!Utils.isEmpty(referer) && referer.contains("/citapreviafront")) {
            redirectTo = referer.substring(referer.indexOf("/citapreviafront") + 16);
            
            if (referer.contains("?")) {
                String query = referer.substring(referer.indexOf("?"));
                if (!redirectTo.contains("?")) {
                    redirectTo += query;
                }
            }
        }

        // Comprovar que redirectTo es null i anar a l'inici
        if (Utils.isEmpty(redirectTo) || redirectTo.contains("changeLang")) {
            redirectTo = "/";
        }

        return "redirect:" + redirectTo;
    }
}