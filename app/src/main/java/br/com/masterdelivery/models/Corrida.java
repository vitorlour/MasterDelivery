/**
 * 
 */
package br.com.masterdelivery.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Corrida {

	private Long id;

	private String nomeEstabelecimento;

	private String endEstabelecimento;

	private String cepEstabelecimento;

	private String nomeCliente;

	private String endCliente;

	private String cepCliente;

	private String valorEntrega;

	private Long statusCorrida;

	private Long plataforma;

	private String tokenCorrida;

	private String logoPath;

}

