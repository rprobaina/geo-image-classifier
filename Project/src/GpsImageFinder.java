import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javaxt.io.Image;

public class GpsImageFinder {


    public static void main(String[] args){

        ArrayList<Ponto> pontos = new ArrayList<Ponto>();
        ArrayList<FotoGeo> fotos = new ArrayList<FotoGeo>();

        pontos.add(new Ponto(-53.989974,	    -31.320993425,	    "WPT044"));
        pontos.add(new Ponto(-53.989959512,	-31.320535362,	"WPT045"));
        pontos.add(new Ponto(-53.989945025,	-31.320077299,	"WPT046"));
        pontos.add(new Ponto(-53.989930537,	-31.319619236,	"WPT047"));
        pontos.add(new Ponto(-53.989916051,	-31.319161174,	"WPT048"));
        pontos.add(new Ponto(-53.990435131,	-31.318690673,	"WPT043"));
        pontos.add(new Ponto(-53.989396965,	-31.319631672,	"WPT050"));
        pontos.add(new Ponto(-53.989411449,	-31.320089735,	"WPT049"));
        pontos.add(new Ponto(-53.993175475,	-31.32091876,	"WPT001"));
        pontos.add(new Ponto(-53.993160972,	-31.320460699,	"WPT002"));
        pontos.add(new Ponto(-53.993146469,	-31.320002637,	"WPT003"));
        pontos.add(new Ponto(-53.992656398,	-31.321389271,	"WPT004"));
        pontos.add(new Ponto(-53.992641897,	-31.32093121,	"WPT005"));
        pontos.add(new Ponto(-53.992627396,	-31.320473148,	"WPT006"));
        pontos.add(new Ponto(-53.992612896,	-31.320015086,	"WPT007"));
        pontos.add(new Ponto(-53.992598396,	-31.319557025,	"WPT008"));
        pontos.add(new Ponto(-53.992583896,	-31.319098963,	"WPT009"));
        pontos.add(new Ponto(-53.992569397,	-31.318640902,	"WPT010"));
        pontos.add(new Ponto(-53.992137315,	-31.32185978,	"WPT011"));
        pontos.add(new Ponto(-53.992122817,	-31.321401719,	"WPT012"));
        pontos.add(new Ponto(-53.992108318,	-31.320943657,	"WPT013"));
        pontos.add(new Ponto(-53.99209382,	-31.320485595,	"WPT014"));
        pontos.add(new Ponto(-53.992079322,	-31.320027533,	"WPT015"));
        pontos.add(new Ponto(-53.992064825,	-31.319569472,	"WPT016"));
        pontos.add(new Ponto(-53.992050328,	-31.31911141,	"WPT017"));
        pontos.add(new Ponto(-53.992035831,	-31.318653348,	"WPT018"));
        pontos.add(new Ponto(-53.992021335,	-31.318195286,	"WPT019"));
        pontos.add(new Ponto(-53.991603731,	-31.321872226,	"WPT020"));
        pontos.add(new Ponto(-53.991589235,	-31.321414164,	"WPT021"));
        pontos.add(new Ponto(-53.991574739,	-31.320956102,	"WPT022"));
        pontos.add(new Ponto(-53.991560244,	-31.32049804,	"WPT023"));
        pontos.add(new Ponto(-53.991545748,	-31.320039978,	"WPT024"));
        pontos.add(new Ponto(-53.991531254,	-31.319581916,	"WPT025"));
        pontos.add(new Ponto(-53.991516759,	-31.319123854,	"WPT026"));
        pontos.add(new Ponto(-53.991502265,	-31.318665792,	"WPT027"));
        pontos.add(new Ponto(-53.991487771,	-31.31820773,	"WPT028"));
        pontos.add(new Ponto(-53.991070146,	-31.32188467,	"WPT029"));
        pontos.add(new Ponto(-53.991055653,	-31.321426607,	"WPT030"));
        pontos.add(new Ponto(-53.99104116,	-31.320968545,	"WPT031"));
        pontos.add(new Ponto(-53.991026667,	-31.320510483,	"WPT032"));
        pontos.add(new Ponto(-53.991012174,	-31.320052421,	"WPT033"));
        pontos.add(new Ponto(-53.990997682,	-31.319594358,	"WPT034"));
        pontos.add(new Ponto(-53.99098319,	-31.319136296,	"WPT035"));
        pontos.add(new Ponto(-53.990968698,	-31.318678234,	"WPT036"));
        pontos.add(new Ponto(-53.990536562,	-31.321897111,	"WPT037"));
        pontos.add(new Ponto(-53.990522071,	-31.321439048,	"WPT038"));
        pontos.add(new Ponto(-53.99049309,	-31.320522924,	"WPT039"));
        pontos.add(new Ponto(-53.9904786,	-31.320064861,	"WPT040"));
        pontos.add(new Ponto(-53.99046411,	-31.319606799,	"WPT041"));
        pontos.add(new Ponto(-53.99044962,	-31.319148736,	"WPT042"));



        File folder = new File("C:\\Users\\Ricardo\\Desktop\\Imagem");
        File[] listaDeImagens = folder.listFiles();

        System.out.println("Running");
        System.out.println("Criando lista de imagens!");

        int cont = 0;
        for(File imagem: listaDeImagens){
            Image image = new Image(imagem);
            FotoGeo foto = new FotoGeo(imagem.getName().toString(), image.getGPSCoordinate());
            fotos.add(foto);
            if(cont == 10){
                System.out.println();
                cont = 0;
            }else{
                System.out.print(".");
                cont ++;
            }


        }
        System.out.println("\nLista de imagens criada!");
        System.out.println("Procurando os pontos e salvando as imagens...");
        for (Ponto ponto : pontos) {
            findImage(ponto, fotos);
        }
        //findImage(ponto, listaDeImagens);


    }


    private static void findImage(Ponto ponto, ArrayList<FotoGeo> fotos){
        double erroDaFoto = 999999999;
        String nomeDaFoto = "";
        FotoGeo fotoSelecionada = null;
        double taxaErro;
        for (FotoGeo item : fotos) {
            //Image image = new Image(file);
            taxaErro = calcularErro(ponto.latitude, ponto.longitude, item.coordenadas);
            //System.out.println("Taxa de Erro: " + taxaErro);
            String nome = item.nome;
            if (taxaErro < erroDaFoto) {
                erroDaFoto = taxaErro;
                nomeDaFoto = nome;
                fotoSelecionada = item;
            }
        }

        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
        System.out.println("Nome da foto original: " + nomeDaFoto);
        System.out.println("Nome da foto de saida: " +  ponto.nome + ".JPG");
        System.out.println("Erro da foto: " + erroDaFoto + "%");
        File saida = new File("C:\\Users\\Ricardo\\Desktop\\Out\\" + ponto.nome + ".JPG");

        //Cria a foto
        File fotoOriginal = new File("C:\\Users\\Ricardo\\Desktop\\Imagem\\" + fotoSelecionada.nome);
        Image arquivoOriginal = new Image(fotoOriginal);
        arquivoOriginal.saveAs(saida);
    }

    private static double calcularErro(double x, double y, double[] coordenadas) {
        // double[] coordenadas;
        /*
        if (imagem != null) {
            coordenadas = imagem.getGPSCoordinate();
            //System.out.println(coordenadas[0] + "  " + coordenadas[1]);
        } else {
            //System.out.println("Nenhuma imagem passada como parêmtro");
            return 9.99999999999999999;
        }
        */
        //System.out.println("Coordenadas: " + coordenadas[0] + ":" + coordenadas[1]);
        double erro = Math.abs((Math.abs(x) - Math.abs(coordenadas[0])) + (Math.abs(y) - Math.abs(coordenadas[1])));
        return erro;
    }

}
