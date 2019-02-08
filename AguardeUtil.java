import javax.swing.JButton;

/**
 * Classe para desabilitar um botão e habilitá-lo novamente em 5 segundos
 * @author Frederico Miranda
 * */
public class AguardeUtil{

	public static void dormir(JButton b) {
		new MyThread(b).start();
	}
}
	
class MyThread extends Thread{
	private JButton botao;
	
	public MyThread(JButton b) {
		botao = b;
	}
	
	public void run() {
	    try {
	    	botao.setEnabled(false);
			Thread.sleep(5000);
			botao.setEnabled(true);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
}

