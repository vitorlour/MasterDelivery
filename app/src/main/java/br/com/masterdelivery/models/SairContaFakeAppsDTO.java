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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SairContaFakeAppsDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8806175403977456363L;

	@SerializedName("email")
	@Expose
	private String email;

	@SerializedName("plataforma")
	@Expose
	private String plataforma;

}
