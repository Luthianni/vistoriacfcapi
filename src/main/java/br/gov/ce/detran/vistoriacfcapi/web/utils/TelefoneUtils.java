package br.gov.ce.detran.vistoriacfcapi.web.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TelefoneUtils {

    public static List<String> separarTelefones(String telefone) {
        List<String> telefones = new ArrayList<>();

        if (telefone == null || telefone.trim().isEmpty()) {
            return telefones;
        }

        // Expressão regular para capturar padrões de telefone (8 ou 9 dígitos após DDD)
        Pattern pattern = Pattern.compile("(\\d{2})(\\d{8,9})");
        Matcher matcher = pattern.matcher(telefone);

        while (matcher.find()) {
            telefones.add(matcher.group(1) + matcher.group(2));
        }

        return telefones;
    }
}
