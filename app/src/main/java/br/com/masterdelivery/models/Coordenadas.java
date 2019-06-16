/**
 * 
 */
package br.com.masterdelivery.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Coordenadas {

	@SerializedName("latitude")
	@Expose
	private Double latitude;

	@SerializedName("longitude")
	@Expose
	private Double longitude;

}
