import javax.swing.JOptionPane;

public class MainBatalhaNaval {
	
	private static boolean isNumber(String text) {
		for (int i = 0; i < text.length(); i++) {
		      if (!Character.isDigit(text.charAt(i)))
		    	  return false;	  
		}
		return true;
	}
	
	public static void main(String[] args) {
		boolean executar = true;
		BancoDados bd = new BancoDados();
		while(executar) {
			//Sem asteriscos
			String senha = JOptionPane.showInputDialog(null, "Senha da batalha", "Batalha Naval", JOptionPane.PLAIN_MESSAGE);
			if(senha.length() > 0) {
				if(isNumber(senha) && bd.verificarSenha(senha)) {
					BatalhaNaval batalhaNaval = new BatalhaNaval(senha);
					batalhaNaval.setVisible(true);
					executar = false;
				}
				else JOptionPane.showMessageDialog(null, "Digite a senha numerica correta!", "Erro", JOptionPane.ERROR_MESSAGE);
			}
			
			else JOptionPane.showMessageDialog(null, "Digite a senha numerica correta!", "Erro", JOptionPane.ERROR_MESSAGE);		
		}		
	}
}

