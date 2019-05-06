package br.com.masterdelivery.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Usuario {

    public String email;

    public String senha;
}
