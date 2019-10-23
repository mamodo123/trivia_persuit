package InterfaceGrafica;

import InterfaceGrafica.view.board.BoardPanel;
import res.Values;

import java.awt.*;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Screen {

	private JFrame frame;
	private final Action action = new SwingAction();
	private final Action action_1 = new SwingAction_1();
	private final Action action_2 = new SwingAction_2();
	private AtorJogador atorJogador;

	private Dimension screenSize;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Screen window = new Screen();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Screen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		atorJogador = new AtorJogador();
		
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 434, 21);
		frame.getContentPane().add(menuBar);
		
		JMenu mnNewMenu = new JMenu("Jogo");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmConectar = new JMenuItem("conectar");
		mntmConectar.setAction(action);
		mnNewMenu.add(mntmConectar);
		
		JMenuItem mntmDesconectar = new JMenuItem("desconectar");
		mntmDesconectar.setAction(action_1);
		mnNewMenu.add(mntmDesconectar);
		
		JMenuItem mntmIniciarPartida = new JMenuItem("iniciar partida");
		mntmIniciarPartida.setAction(action_2);
		mnNewMenu.add(mntmIniciarPartida);

		setScreen();
	}
	private class SwingAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public SwingAction() {
			putValue(NAME, "conectar");
			putValue(SHORT_DESCRIPTION, "conectar a Netgames Server");
		}
		public void actionPerformed(ActionEvent e) {
			String servidor = JOptionPane.showInputDialog("Qual o servidor?");
			String name = JOptionPane.showInputDialog("Qual o seu nome?");
			String mensagem = atorJogador.conectar(servidor, name);
			JOptionPane.showMessageDialog(null, mensagem);
		}
	}
	private class SwingAction_1 extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public SwingAction_1() {
			putValue(NAME, "desconectar");
			putValue(SHORT_DESCRIPTION, "desconectar de Netgames Server");
		}
		public void actionPerformed(ActionEvent e) {
			String mensagem = atorJogador.desconectar();
			JOptionPane.showMessageDialog(null, mensagem);
		}
	}
	private class SwingAction_2 extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public SwingAction_2() {
			putValue(NAME, "iniciar partida");
			putValue(SHORT_DESCRIPTION, "iniciar partida do seu jogo");
		}
		public void actionPerformed(ActionEvent e) {
			String mensagem = atorJogador.iniciarPartida();
			JOptionPane.showMessageDialog(null, mensagem);
		}
	}

	private void setScreen() {
		frame.setTitle(Values.APP_NAME);
		setImage();

		setConfig();
		setSize();
		setComponents();

		frame.getContentPane().setBackground(Values.BG_COLOR);
	}
	private void setImage() {
		Image image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/res/images/triviando.png"));
		ImageIcon icon = new ImageIcon(image);
		frame.setIconImage(icon.getImage());
	}

	private void setSize() {
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setPreferredSize(screenSize);
		frame.setMinimumSize(new Dimension((int) (screenSize.width * 0.5), (int) (screenSize.height * 0.5)));
	}

	private void setConfig() {
		frame.setLocationRelativeTo(null);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}

	private void setComponents() {
		frame.setLayout(new FlowLayout(FlowLayout.CENTER));
		frame.add(new BoardPanel(new Dimension((int) (screenSize.getWidth() * 0.9), (int) (screenSize.getHeight() * 0.9))));

		Container ct = new Container();
		ct.setPreferredSize(new Dimension(50, 0));
		frame.add(ct);

		JButton button = new JButton("Rolar o dado");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				Random gerador = new Random();
				int random = gerador.nextInt(6);
				JOptionPane.showMessageDialog(null, "Você rolou o dado: " + (random + 1));
			}
		});

		frame.add(button);
	}
}
