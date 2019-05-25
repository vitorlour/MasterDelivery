package br.com.masterdelivery.transformer;

import java.util.ArrayList;
import java.util.List;

import br.com.masterdelivery.models.UsuarioFakeAppsDTO;
import br.com.masterdelivery.utils.Constants;

public class UsuarioFakeAppsDTOTransformer {

    public static List<UsuarioFakeAppsDTO> transform(List<UsuarioFakeAppsDTO> usuario){
        List<UsuarioFakeAppsDTO> usu = new ArrayList<>();

        if(!usuario.isEmpty()){
            for (UsuarioFakeAppsDTO u : usuario) {
                if(u.getPlataforma().equals(String.valueOf(Constants.PlataformaID.IFOOD_ID))){
                    u.setLogoPath(Constants.LogosPath.IFOOD_LOGO);
                    u.setPlataforma(Constants.Plataforma.IFOOD);
                }else if(u.getPlataforma().equals(String.valueOf(Constants.PlataformaID.RAPPI_ID))){
                    u.setLogoPath(Constants.LogosPath.RAPPI_LOGO);
                    u.setPlataforma(Constants.Plataforma.RAPPI);
                }
                usu.add(u);
            }
        }
        return usu;
    }
}
