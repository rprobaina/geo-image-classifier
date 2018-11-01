/**
 * GPS Image Finder
 * @author Ricardo Peixoto Robaina
 * @version 1.0
 * @since 01/11/2018
 */
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javaxt.io.Image;
import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.Executors;


/**
 * Classe UI
 *
 * Esta classe implementa a interface gráfica do programa.
 */
public class UI {

    //Delaração dos elementos de UI.
    public JPanel mainPanel;
    private JButton btnIn;
    private JCheckBox checkIn;
    private JButton btnOut;
    private JCheckBox checkOut;
    private JButton btnPontos;
    private JCheckBox checkPontos;
    private JButton btnIniciar;
    private JProgressBar progressBar;
    private JLabel infoLabel;
    private JLabel Label;

    //File finders (janela de seleção de pasta/arquivo) para cada botao do GUI
    JFileChooser fIn;
    JFileChooser fOut;
    JFileChooser fPontos;

    //String do arquivo de log
    static String log = new String();

    //Método principal. Apenas instancia a GUI.
    public static void main(String[] args) {
        //Criação da interface grafica do programa
        JFrame frame = new JFrame("GPS Image Finder");
        frame.setContentPane(new UI().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null); //cria a UI no centro da tela
        frame.setVisible(true); //torna a UI visivel
    }




    public UI() {


        checkIn.setEnabled(false);
        checkOut.setEnabled(false);
        checkPontos.setEnabled(false);

        fIn = new JFileChooser();
        fIn.setDialogTitle("Selecione a pasta de entrada.");
        fOut = new JFileChooser();
        fOut.setDialogTitle("Selecione a pasta de saída.");

        fPontos = new JFileChooser();
        fIn.setDialogTitle("Selecione o arquivo de pontos.");

        progressBar.setString(" ");
        /**
         * Evento de clique
         * */
        btnIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fIn.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fIn.showSaveDialog(null);
                checkIn.setSelected(true);

            }
        });
        btnOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fOut.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fOut.showSaveDialog(null);

                checkOut.setSelected(true);
            }
        });
        btnPontos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fPontos.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fPontos.showSaveDialog(null);
                checkPontos.setSelected(true);
            }
        });
        btnIniciar.addActionListener(new ActionListener() {
            float progresso = 0;

            @Override
            public void actionPerformed(ActionEvent e) {


                progressBar.setString(" ");
                infoLabel.setText("Executando...");
                progressBar.setIndeterminate(true);

                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {

                        //Lista de Imagens
                        ArrayList<FotoGeo> fotos = new ArrayList<FotoGeo>();




                        //Lista de pontos (função que le os os pontos do aquivo)
                        ArrayList<Ponto> pontos = lerArquivoPontos();



                        File folder = fIn.getSelectedFile();
                        File[] listaDeImagens = folder.listFiles();



                        //preenche a lista de imagens
                        for(File imagem: listaDeImagens){
                            Image image = new Image(imagem);
                            FotoGeo foto = new FotoGeo(imagem.getName(), image.getGPSCoordinate());
                            fotos.add(foto);


                        }



                        //Aplica o algoritmo a todoas as imagens e pontos  (Força bruta)
                        for (Ponto ponto : pontos) {
                            findImage(ponto, fotos, fIn.getSelectedFile().toString(), fOut.getSelectedFile().toString());


                        }

                        try {
                            salvarArquivoLog(log);

                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }

                        infoLabel.setText("Concluído!");
                        progressBar.setIndeterminate(false);
                        progressBar.setValue(100);
                        progressBar.setString("Concluído");
                    }
                });




            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }


    /**
     * Método findImage
     *
     * Este método encontra, em um conjutnto de imagens, a imagem cujas coordenadas mais se aproximam da coordenada do ponto.
     * @param ponto Ponto de interesse.
     * @param fotos Conjunto de imgagens do tipo FotoGeo.
     * @param pastaEntrada Caminho da pasta de entrada.
     * @param pastaSaida Caminho da pasta de saída.
     */
    private static void findImage(Ponto ponto, ArrayList<FotoGeo> fotos, String pastaEntrada, String pastaSaida){
        double erroDaFoto = 999999999;
        String nomeDaFoto = "";
        FotoGeo fotoSelecionada = null;
        double taxaErro;

        //Encontra a imagem com o menor erro, utilizando a técnica de força bruta.
        for (FotoGeo item : fotos) {
            taxaErro = calcularErro(ponto.latitude, ponto.longitude, item.coordenadas);
            String nome = item.nome;
            if (taxaErro < erroDaFoto) {
                erroDaFoto = taxaErro;
                nomeDaFoto = nome;
                fotoSelecionada = item;
            }
        }

        //Incrementa a string de log
        log += "++++++++++++++++++++++++++++++++++++++++++++++++++++\n";
        log += ("Nome da foto original: " + nomeDaFoto + "\n");
        log += ("Nome da foto de saida: " +  ponto.nome + ".JPG \n");
        log += ("Erro da foto: " + erroDaFoto + "% \n\n");

        //Salva a imagem selecionada na pasta de saida com o nome do ponto
        File saida = new File(pastaSaida + "\\" + ponto.nome + ".JPG");
        File fotoOriginal = new File(pastaEntrada.toString() + "\\" + fotoSelecionada.nome);
        Image arquivoOriginal = new Image(fotoOriginal);
        arquivoOriginal.saveAs(saida);
    }

    /**
     * Método que calcula a diferença entre os valores x e y e as coordenadas.
     * @param x Valor x.
     * @param y Valor y.
     * @param coordenadas Coordenadas.
     * @return
     */
    private static double calcularErro(double x, double y, double[] coordenadas) {
        double erro = Math.abs((Math.abs(x) - Math.abs(coordenadas[0])) + (Math.abs(y) - Math.abs(coordenadas[1])));
        return erro;
    }

    /**
     * Método que realizada a leitura do arquivo de pontos.
     * @return conjunto de pontos (array da classe Ponto).
     */
    private ArrayList<Ponto> lerArquivoPontos() {

        ArrayList<Ponto> pontos = new ArrayList<Ponto>();
        File arq = fPontos.getSelectedFile();

        try {
            FileReader fileReader = new FileReader(arq);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String linha = "";
            while ((linha = bufferedReader.readLine()) != null) {
                String[] palavras = linha.split(" ");
                Ponto novoPonto = new Ponto(Double.parseDouble(palavras[0]),Double.parseDouble(palavras[1]),palavras[2]);
                pontos.add(novoPonto);
            }
            fileReader.close();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pontos;
    }

    /**
     * Método que realiza o salvamento do arquivo de log.
     * @param log String que contém as informações de log.
     */
    private void salvarArquivoLog(String log) throws IOException {

        BufferedWriter bw = null;
        FileWriter fw = null;

        try {
            fw = new FileWriter(fOut.getSelectedFile() + "\\log.txt");
            bw = new BufferedWriter(fw);
            bw.write(log);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null)
                    bw.close();

                if (fw != null)
                    fw.close();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
