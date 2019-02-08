import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * @author joaop
 *
 */
public class BatalhaNaval extends JFrame implements ActionListener {

	private JButton atualizar;
	private JButton ataque;
	private JButton mover;
	private JButton historico;

	private JTextField text01;
	private JTextField text02;
	private JTextField text03;
	private JTextField text04;

	private int tamMatriz = 41;

	private JButton matriz[][] = new JButton[tamMatriz][tamMatriz];
	private Color corMar = new Color(34, 34, 123);

	BancoDados bd = new BancoDados();

	private Color corMar2 = new Color(232, 206, 250);

	private int senha;

	public BatalhaNaval(String senha) {
		setSize(1200, 700);
		setLocation(0, 0);
		setTitle("Batalha Naval: lute ou morra!");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);

		text01 = new JTextField();
		text01.setBounds(180, 600, 140, 25);// coluna,linha,largura,altura
		text01.setBackground(corMar2);

		text02 = new JTextField();
		text02.setBounds(180, 630, 140, 25);// coluna,linha,largura,altura
		text02.setBackground(corMar2);

		text03 = new JTextField();
		text03.setBounds(325, 600, 140, 25);// coluna,linha,largura,altura
		text03.setBackground(corMar2);

		text04 = new JTextField();
		text04.setBounds(325, 630, 140, 25);
		text04.setBackground(corMar2);

		add(text01);
		add(text02);
		add(text03);
		add(text04);

		atualizar = new JButton("Atualizar Mapa");
		atualizar.setBounds(30, 600, 140, 55);// coluna,linha,largura,altura
		atualizar.addActionListener(this);
		// atualizar.setBackground(Color.BLACK);

		ataque = new JButton("Atacar");
		ataque.setBounds(475, 600, 140, 25);
		ataque.addActionListener(this);
		// ataque.setBackground(Color.BLACK);

		mover = new JButton("Mover");
		mover.setBounds(475, 630, 140, 25);
		mover.addActionListener(this);
		// mover.setBackground(Color.BLACK);

		historico = new JButton("Historico");
		historico.setBounds(800, 600, 140, 25);
		historico.addActionListener(this);

		add(atualizar);
		add(ataque);
		add(mover);
		add(historico);

		JPanel p = new JPanel();
		p.setBounds(40, 10, 1100, 570);// coluna,linha,largura,altura
		p.setLayout(new GridLayout(tamMatriz, tamMatriz));
		p.setBackground(Color.BLUE);
		add(p);

		BatalhaNavalUtil.startMatriz(p, matriz, corMar);
		this.senha = Integer.parseInt(senha);
		// BatalhaNavalUtil.atualizarMatriz(matriz, bd.atualizarMapa(this.senha));

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == atualizar) {
			System.out.println("cliquei no bot�o atualizar mapa!");

			BatalhaNavalUtil.limparMatriz(matriz, tamMatriz, corMar);

			BatalhaNavalUtil.atualizarMatriz(matriz, bd.atualizarMapa(this.senha));

			AguardeUtil.dormir(atualizar);
		}

		if (e.getSource() == ataque) {
			String a1 = text01.getText();
			String a2 = text03.getText();

			text01.setText(a1);
			text03.setText(a2);

			// System.out.println("Atacando na posi��o : " + a1 + " e " + a2);

			if (a1.length() == 0 || a2.length() == 0)
				JOptionPane.showMessageDialog(null, "Digite a posicao(x,y) a ser atacada!", "Erro",
						JOptionPane.ERROR_MESSAGE);
			else {
				int x = Integer.parseInt(text01.getText());
				int y = Integer.parseInt(text03.getText());

				if (x > tamMatriz - 1 || y > tamMatriz - 1 || x < 0 || y < 0)
					JOptionPane.showMessageDialog(null,
							"Apenas posicoes x e y de 0 a " + (tamMatriz - 1) + " sao aceitas!", "Erro",
							JOptionPane.ERROR_MESSAGE);
				else {

					matriz[x][y].setBackground(Color.RED);

					bd.atacar(x, y, senha);
					AguardeUtil.dormir(ataque);
				}
			}
		}

		if (e.getSource() == mover) {
			String m1 = text02.getText();
			String m2 = text04.getText();

			if (m1.length() == 0 || m2.length() == 0)
				JOptionPane.showMessageDialog(null,
						"Digite a porcentagem da nova posicao(x,y) do seu navio para ele ser movido!", "Erro",
						JOptionPane.ERROR_MESSAGE);
			else {
				int x = Integer.parseInt(text02.getText());
				int y = Integer.parseInt(text04.getText());

				text02.setText(m1);
				text04.setText(m2);
				// System.out.println("Movendo para : " + m1 + " e " + m2);

				bd.mover(x, y, senha);

				AguardeUtil.dormir(mover);
			}
		}
		if (e.getSource() == historico) {
			Posicao posicao = null;
			List<Posicao> ht = bd.historico();
			String navio[] = new String[ht.size()];
			int x[] = new int[ht.size()];
			int y[] = new int[ht.size()];
			String pos = null;
			for (int i = 0; i < ht.size(); i++) {
				posicao = ht.get(i);
				navio[i] = posicao.getTime();
				x[i] = posicao.getX();
				y[i] = posicao.getY();
				if (pos == null)
					pos = navio[i] + ": x: " + x[i] + " y: " + y[i] + "\n";
				else
					pos = pos + navio[i] + ": x: " + x[i] + " y: " + y[i] + "\n";
			}
			JTextArea textArea = new JTextArea(pos);
			JScrollPane scrollPane = new JScrollPane(textArea);
			textArea.setLineWrap(true);
			textArea.setWrapStyleWord(true);
			scrollPane.setPreferredSize(new Dimension(200, 300));

			JOptionPane.showMessageDialog(null, scrollPane, "Historico", JOptionPane.PLAIN_MESSAGE);
		}
	}
}
