package br.com.masterdelivery.transformer;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import br.com.masterdelivery.models.Corrida;
import br.com.masterdelivery.utils.Constants;

public class CorridaTransformer {

    private static Locale localeBR = new Locale( "pt", "BR" );

    private final static String JOGO_DA_VELHA = "#";

    public static List<Corrida> transform(List<Corrida> corrida){
        List<Corrida> cor = new ArrayList<>();

        NumberFormat moedaBR = NumberFormat.getCurrencyInstance(localeBR);

        if(!corrida.isEmpty()){
            for (Corrida c : corrida  ) {

                c.setValorEntrega(moedaBR.format(Double.valueOf(c.getValorEntrega())));
                c.setId(JOGO_DA_VELHA + c.getId());

                if(c.getPlataforma().equals(Constants.PlataformaID.IFOOD_ID)){
                    c.setLogoPath(Constants.LogosPath.IFOOD_LOGO);
                }else if(c.getPlataforma().equals(Constants.PlataformaID.RAPPI_ID)){
                    c.setLogoPath(Constants.LogosPath.RAPPI_LOGO);
                }
                cor.add(c);
            }
        }
        return cor;
    }
}
