/**
 * 
 */
package br.com.masterdelivery.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * @author vitorlour
 *
 */
@Getter
@Setter
public class Coordenadas {

	@SerializedName("latitude")
	@Expose
	private Double latitude;

	@SerializedName("longitude")
	@Expose
	private Double longitude;

}
