import java.io.File;
import javaxt.io.Image;

public class GpsImageFinder {


    double lg = 53.989974;
    double lt = 31.320993425;





    public static void main(String[] args){
        String nomePonto = "Ponto 1";
        //System.out.println("Ola mundo");
        File folder = new File("C:\\Users\\Ricardo\\Desktop\\Imagem");
        File[] listaDeImagens = folder.listFiles();
        String nomeDaFoto = "";
        double erroDaFoto = 999999999;
        //File fotoSelecionada = new File("C:\\Users\\Ricardo\\Pontos\\" + nomePonto + ".jpg");
        double taxaErro;

        System.out.print("Running");
        int cont = 0;
        for (File file : listaDeImagens){

            if(cont < 10){
                System.out.print(".");
                cont++;
            }else{
                System.out.print("\n");
                cont = 0;
            }

            Image image = new Image(file);
            taxaErro = calcularErro(-53.990522071,-31.321439048, image);
            String nome = file.getName();
           // System.out.println("Nome: " + nome + " Taxa de Erro: " + taxaErro + "%\n");
            if(taxaErro < erroDaFoto){
                erroDaFoto = taxaErro;
                nomeDaFoto = nome;
               // fotoSelecionada.renameTo(file);
            }
        }

        System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
        System.out.println("Nome da foto: " + nomeDaFoto);
        System.out.println("Erro da foto: " + erroDaFoto + "%");
        //fotoSelecionada.renameTo();
        //Image image = new Image("C:\\Users\\Ricardo\\Desktop\\img.jpg");
        /*
        System.out.println("Taxa de Erro: " + taxaErro + "%");
        taxaErro = calcularErro(53.989065862,31.329325334, image);
        System.out.println("Taxa de Erro: " + taxaErro + "%");
        */
    }


    private static double calcularErro(double x, double y, Image imagem){
        double[] coordenadas;
        if(imagem != null){
            coordenadas = imagem.getGPSCoordinate();
            //System.out.println(coordenadas[0] + "  " + coordenadas[1]);
        }else{
            //System.out.println("Nenhuma imagem passada como parêmtro");
            return 9.99999999999999999;
        }
        double erro = Math.abs(( Math.abs(x) - Math.abs(coordenadas[0]) ) + ( Math.abs(y) - Math.abs(coordenadas[1]) ));
        return erro;
    }


}
