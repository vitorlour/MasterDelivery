package br.com.masterdelivery.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import br.com.masterdelivery.R;

import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

public class RentabilidadeActivity extends AppCompatActivity {
    WebView wvGrafico;
    String strURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rentabilidade);

        /*String lista[] = new String[]{"bola1","bola2", "bola3", "bola4", "bola5", "bola6", "bola7", "bola8", "bola9", "bola10", "bola11", "bola12", "bola13", "bola14", "bola15", "bola16", "bola17", "bola18", "bola19", "bola20", "bola21", "bola22", "bola23", "bola24", "bola25", "bola26", "bola27", "bola28", "bola29", "bola30", "bola31", "bola32", "bola33", "bola34", "bola35", "bola36", "bola37", "bola38", "bola39"};
        GridView gv = (GridView) findViewById(R.id.grdEntregas);

        gv.setAdapter(new RecyclerViewAdapterCorridas(this,lista));*/

        //mostrarGrafico();

        strURL = "https://chart.googleapis.com/chart?" +
                "cht=lc&" + //define o tipo do gráfico "linha"
                "chxt=x,y&" + //imprime os valores dos eixos X, Y
                "chs=350x350&" + //define o tamanho da imagem
                "chd=t:1000,850,1100,980,830,1230&" + //valor de cada coluna do gráfico
                "chl=25/10|26/10|27/10|28/10|29/10|30/10&" + //rótulo para cada coluna
                "chdl=Rentabilidade&" + //legenda do gráfico
                "chxr=1,0,2000&" + //define o valor de início e fim do eixo
                "chds=0,2000&" + //define o valor de escala dos dados
                "chg=0,10,0,0&" + //desenha linha horizontal na grade
                "chco=3D7930&" + //cor da linha do gráfico
                "chtt=Rentabilidade das Entregas&" + //cabeçalho do gráfico
                //"chm=B,C5D4B5BB,0,0,0"; //fundo verde
                "chm=v,FF0000,0,::.10,4"; //fundo vermelho

        strURL = "https://chart.apis.google.com/chart?cht=bhg&chs=550x400&chd=t:100,50,115,80&chxt=x,y&chxl=1:|iFood|UberEats|Rappy|James&chxr=0,0,120&chds=0,120&chco=FF0000&chbh=35,0,15&chg=8.33,0,5,0&chco=FF0000,0A8C8A,EBB671&chtt=Rentabilidade das Entregas";

        //Abaixo seguem outros exemplo de gráfico:
        //strURL = "https://chart.googleapis.com/chart?cht=lc&chxt=x,y&chs=300x300&chd=t:10,45,5,10,13,26&chl=Janeiro|Fevereiro|Marco|Abril|Maio|Junho&chdl=Vendas%20&chxr=1,0,50&chds=0,25&chg=0,5,0,0&chco=3D7930&chtt=Vendas+x+1000&chm=v,FF0000,0,::.10,4";
        //strURL = "http://chart.apis.google.com/chart?cht=bhg&chs=550x400&chd=t:100,50,115,80|10,20,15,30&chxt=x,y&chxl=1:|Janeiro|Fevereiro|Marco|Abril&chxr=0,0,120&chds=0,120&chco=4D89F9&chbh=35,0,15&chg=8.33,0,5,0&chco=0A8C8A,EBB671&chdl=Vendas|Compras";
        //strURL = "https://chart.googleapis.com/chart?cht=lc&chxt=x,y&chs=700x350&chd=t:10,45,5,10|30,35,30,15|10,10.5,30,35&chl=Janeiro|Fevereiro|Marco|Abril&chdl=Vendas|Compras|Outros&chxr=1,0,50&chds=0,50&chg=0,5,0,0&chco=DA3B15,3072F3,000000&chtt=grafico+de+vendas";
        //strURL = "https://chart.googleapis.com/chart?cht=p3&chs=200x90&chd=t:40,45,5,10&chl=Jan|Fev|Mar|Abr";
        //strURL = "http://chart.apis.google.com/chart?chxl=0:|Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec&chxt=x,y&chs=300x300&cht=r&chco=FF0000&chd=t:63,64,67,73,77,81,85,86,85,81,74,67,63&chls=2,4,0&chm=B,FF000080,0,0,0";

        wvGrafico = (WebView)findViewById(R.id.wvGrafico);
        wvGrafico.loadUrl(strURL);


    }

    private void mostrarGrafico() {
        //ValueLineChart mCubicValueLineChart = (ValueLineChart) findViewById(R.id.desempenhoChart);
        //mCubicValueLineChart.setLineStroke(0.2f);
        ValueLineSeries series = new ValueLineSeries();
        series.setColor(0xFFFF3366);
        //series.setColor(0xFF56B7F1);

        series.addPoint(new ValueLinePoint("", 0f));
        series.addPoint(new ValueLinePoint("Dom", 2.4f));
        series.addPoint(new ValueLinePoint("Seg", 3.4f));
        series.addPoint(new ValueLinePoint("Ter", .4f));
        series.addPoint(new ValueLinePoint("Qua", 1.2f));
        series.addPoint(new ValueLinePoint("Qui", 10.6f));
        series.addPoint(new ValueLinePoint("Sex", 1.0f));
        series.addPoint(new ValueLinePoint("Sab", 3.5f));
        series.addPoint(new ValueLinePoint("", 0f));

        //mCubicValueLineChart.addSeries(series);
        //mCubicValueLineChart.startAnimation();
    }
}
