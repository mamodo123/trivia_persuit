package InterfaceGrafica;

import InterfaceGrafica.view.board.BoardPanel;
import model.Player;
import res.Values;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Screen {

	private JFrame frame;
	private BoardPanel game_panel;
	private Action action;
	private Action action_1;
	private Action action_2;

	private Dimension screenSize;

	private ActionListener random_listener;

	/**
	 * Create the application.
	 * @param random_listener
	 */
	public Screen(ActionListener random_listener, Action action, Action action_1, Action action_2) {

		this.random_listener = random_listener;
		this.action = action;
		this.action_1 = action_1;
		this.action_2 = action_2;

		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
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
		game_panel = new BoardPanel(new Dimension((int) (screenSize.getWidth() * 0.9), (int) (screenSize.getHeight() * 0.9)));
		frame.add(game_panel);

		Container ct = new Container();
		ct.setPreferredSize(new Dimension(50, 0));
		frame.add(ct);

		JButton button = new JButton("Rolar o dado");
		button.addActionListener(random_listener);

		frame.add(button);
	}

	public void updateState(ArrayList<Player> players) {
		game_panel.updateState(players);
	}

	public JFrame getFrame() {
		return frame;
	}
}
