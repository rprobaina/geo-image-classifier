/**
 * GPS Image Finder
 * @author Ricardo Peixoto Robaina
 * @version 1.0
 * @since 01/11/2018
 */

/**
 * Classe FotoGeo
 *
 * Esta classe contém as propriedades de uma imagem georreferenciada.
 */
public class FotoGeo {
    public String nome;
    public double[]coordenadas;

    /**
     * Construtor da Classe FotoGeo.
     * @param nome Nome da imagem.
     * @param coordenadas Vetor com as coordenadas recebidos dos metadados da imagem.
     */
    public FotoGeo(String nome, double[] coordenadas) {
        this.nome = nome;
        this.coordenadas = coordenadas;
    }

}
