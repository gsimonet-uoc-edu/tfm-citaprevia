package uoc.edu.citaprevia.front.service;
import java.io.IOException;
import java.io.InputStream;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import uoc.edu.citaprevia.front.dto.CampConfigDto;

@Service
@RequiredArgsConstructor
public class MetacamapService {

    private final Map<String, CampConfigDto> configMap = new HashMap<>();

    public List<CampConfigDto> getCampos(String subaplCoa, Locale locale) {
        configMap.clear();
        String lang = locale.getLanguage();
        String filename = "metacamps_" + lang + ".properties";

        InputStream is = getResourceAsStream(filename);

        // Fallback a español
        if (is == null) {
            is = getResourceAsStream("metacamps_es.properties");
        }

        if (is == null) {
            return List.of();
        }

        try  {
            loadProperties(is, subaplCoa.toUpperCase());
        } catch (IOException e) {
            // log
        }

        return configMap.values().stream()
                .sorted(Comparator.comparing(CampConfigDto::getName))
                .toList();
    }

    private InputStream getResourceAsStream(String filename) {
        return getClass().getClassLoader().getResourceAsStream(filename);
    }

    private void loadProperties(InputStream is, String subaplCoa) throws IOException {
        Properties props = new Properties();
        props.load(is);

        props.forEach((key, value) -> {
            String k = key.toString();
            if (!k.matches("lit[1-9][0-9]?[a-z]_" + Pattern.quote(subaplCoa))) return;

            String camp = k.substring(0, k.indexOf("_"));
            String[] parts = value.toString().split("\\|", 4);
            if (parts.length < 4 || !"S".equals(parts[0].trim())) return;

            CampConfigDto cc = new CampConfigDto();
            cc.setName(camp);
            cc.setLabel(parts[1].trim());
            cc.setType(parts[2].trim());
            cc.setValidation(parts[3].trim());
            configMap.put(camp, cc);
        });
    }
}