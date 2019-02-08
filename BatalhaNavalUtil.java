import java.awt.Color;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Classe de suporte para o jogo Batalha Naval
 * @author Frederico Miranda
 * */
public class BatalhaNavalUtil {

	//Inicializa a matriz e cria uma referência para o panel.
	//Este método deve ser chamado uma ÚNICA VEZ
	public static void startMatriz(JPanel panel, JButton matriz[][], Color corMar) {
		
		int tamMatriz = matriz.length;
		
		for (int i = 0; i < tamMatriz; i++) {
			    	
			for (int j = 0; j < tamMatriz; j++){
	    		
	    		if (i==0) {
	    			matriz[i][j] = new JButton(j+"");
	    		}
	    		else if (j==0) {
	    			matriz[i][j] = new JButton(i+"");
	    		}
	    		else {
	    			matriz[i][j] = new JButton();
	    		}
	    		
	    	   // matriz[i][j].setFont(new Font("Arial", Font.PLAIN, 12));
	    	   matriz[i][j].setMargin(new Insets(0,0,0,0)); //tira espaço entre o "text" e a borda do botão
	    	   matriz[i][j].setBackground(corMar);
	    	   matriz[i][j].setForeground(Color.WHITE);
	    	   panel.add(matriz[i][j]);
	    	}
	    }
	}
	
	//Reseta as cores da matriz
	public static void limparMatriz(JButton matriz[][], int tamMatriz, Color corMar) {
		
		for (int linha=0;linha<tamMatriz;linha++) {
			for (int coluna=0;coluna<tamMatriz;coluna++) {
		    	matriz[linha][coluna].setBackground(corMar);
			}
		}
	}
	
	//Atualiza a matriz com novas posições
	public static void atualizarMatriz(JButton matriz[][], List<Posicao> lista) {
		
		//matriz[][] é uma matriz bidimensional que representa o "mar" e suas coordenadas
		
		Posicao posicao = null;
		for (int i=0;i<lista.size();i++) {
			
			posicao = lista.get(i);
			
			if ("Perola Negra".equals(posicao.getTime())) { //sua posição
				matriz[posicao.getX()][posicao.getY()].setBackground(Color.green);
			}
			else {//posição adversários
				matriz[posicao.getX()][posicao.getY()].setBackground(Color.yellow);
			}
		}
	}
	
	public static List<Posicao> gerarPosicoes() {
		
		Random r = new Random();
		List<Posicao> lista = new ArrayList<Posicao>();
			 
		Posicao posicao = null;
		for (int i=0;i<10;i++) {
				posicao = new Posicao("time"+i, r.nextInt(40)+1, r.nextInt(40)+1);
				lista.add(posicao);
		}
		return lista;
		
	}
	
}
