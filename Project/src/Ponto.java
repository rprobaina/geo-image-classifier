/**
 * GPS Image Finder
 * @author Ricardo Peixoto Robaina
 * @version 1.0
 * @since 01/11/2018
 */

/**
 * Classe Ponto
 *
 * Esta classe contém as propriedades de um ponto de interesse.
 */
public class Ponto {

    public double latitude;
    public double longitude;
    public String nome;

    /**
     * Construtor da classe Ponto.
     * @param latitude Valor de latitude em decimal.
     * @param longitude Valor da longitude em decimal.
     * @param nome Nome do ponto.
     */
    Ponto(double latitude, double longitude, String nome){
        this.nome = nome;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
