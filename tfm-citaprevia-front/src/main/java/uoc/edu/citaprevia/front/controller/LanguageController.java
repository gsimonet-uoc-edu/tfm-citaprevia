package uoc.edu.citaprevia.front.controller;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

@Controller
public class LanguageController {

    @RequestMapping("/changeLang")
    public String changeLanguage(@RequestParam String lang,
                                 HttpServletRequest request,
                                 HttpServletResponse response) {

        // 1. Canviem l'idioma
        LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
        if (localeResolver != null) {
            localeResolver.setLocale(request, response, new Locale(lang));
        }

        // 2. Agafem el Referer i el netegem correctament
        String referer = request.getHeader("Referer");
        String redirectTo = "/";  // fallback

        if (referer != null && referer.contains("/citapreviafront")) {
            // Extreu només la part després del context path
            redirectTo = referer.substring(referer.indexOf("/citapreviafront") + 16); // 16 = longitud de "/citapreviafront"
            
            // Si té query string (?...), la mantenim
            if (referer.contains("?")) {
                String query = referer.substring(referer.indexOf("?"));
                if (!redirectTo.contains("?")) {
                    redirectTo += query;
                }
            }
        }

        // Si per algun motiu està buit o és changeLang → anem a l'inici
        if (redirectTo == null || redirectTo.isEmpty() || redirectTo.contains("changeLang")) {
            redirectTo = "/";
        }

        return "redirect:" + redirectTo;
    }
}