/**
 * 
 */
package br.com.masterdelivery.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author vitorlour
 *
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioFakeAppsDTO implements Serializable {

    @SerializedName("email")
    @Expose
	private String email;

    @SerializedName("senha")
    @Expose
    private String senha;

    @SerializedName("plataforma")
    @Expose
    private String plataforma;

    private String logoPath;
    
}
