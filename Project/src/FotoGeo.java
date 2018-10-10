import javaxt.io.File;
import javaxt.io.Image;

import java.awt.image.RenderedImage;

public class FotoGeo {
    public String nome;
    public double[]coordenadas;
   // public Image arquivoOriginal;


    public FotoGeo(String nome, double[] coordenadas) {
        this.nome = nome;
        this.coordenadas = coordenadas;
       // this.arquivoOriginal = new Image(imagem);
    }

}
