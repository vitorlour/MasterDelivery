/**
 * 
 */
package br.com.masterdelivery.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Builder;
import lombok.Getter;

/**
 * @author vitorlour
 *
 */

@Getter
@Builder
public class AceitarCorridaDTO {


	@SerializedName("tokenCorrida")
	@Expose
	private String tokenCorrida;

}
