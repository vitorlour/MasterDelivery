package br.com.masterdelivery.utils;

public class  Constants{
    public interface TimeOut {
        int IMAGE_UPLOAD_CONNECTION_TIMEOUT = 120;
        int IMAGE_UPLOAD_SOCKET_TIMEOUT = 120;
        int SOCKET_TIME_OUT = 60;
        int CONNECTION_TIME_OUT = 60;
    }

    public interface UrlPath {
        String POST_CORRIDA = "corrida";
        String POST_VINCULAR_APPS = "usuario/vincularapps";
        String GET_CONTAS_APPS = "usuario/contasapps";
        String DELETE_CONTA_APP = "usuario/saircontaapp";


    }

    //Need unique flags for all apis in case if hitting multiple apis in same activity/fragment
    public interface ApiFlags {
        int GET_SOMETHING = 0;
        int POST_CORRIDAS = 1;
        int POST_VINCULAR_APPS = 2;
        int GET_CONTAS_APPS = 3;
        int DELETE_CONTA_APP = 4;


    }

    public interface ErrorClass {
        String CODE = "code";
        String STATUS = "status";
        String MESSAGE = "message";
        String DEVELOPER_MESSAGE = "developerMessage";
    }

    public interface LogosPath{
        String IFOOD_LOGO = "https://thumb.lovemondays.com.br/_QZT7-WjdVQcbt2r7BpMwphaQDM=/102x102/https://thumb.lovemondays.com.br/image/8abac013c27347519de624584d094279/logos/bf7f55/ifood.png";
        String RAPPI_LOGO = "https://www.paypalobjects.com/digitalassets/c/website/marketing/latam/br/rainbow6-siege/logo_rappi_210x170.png";
    }

    public interface PlataformaID{
        Long IFOOD_ID = 1L;
        Long RAPPI_ID = 2L;
    }

    public interface Plataforma{
        String IFOOD = "IFOOD";
        String RAPPI = "RAPPI";
    }
}
