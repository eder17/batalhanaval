
/**
 * Classe que representa uma posição na matriz(mar)
 * */
public class Posicao {

	private String time;
	private int x;
	private int y;
	
	public Posicao(String time, int x, int y) {
		this.time = time;
		this.x = x;
		this.y = y;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	
}
