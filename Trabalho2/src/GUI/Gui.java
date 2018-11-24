package GUI;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import Comportamentos.Comportamentos;
import Comportamentos.Evitar;
import Comportamentos.Vaguear;
import myRobot.myRobot;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JCheckBoxMenuItem;

public class Gui extends Thread {

	private JFrame frame;
	private JTextField txtNomeRobot;
	private JTextField txtDistance;
	private JTextField txtAngle;
	private JTextField txtRadius;
	private JTextField txtOffsetLeft;
	private JTextField txtOffsetRight;
	private JTextArea txtrLogging;
	private JCheckBox chckbxCheckLogging;
	private JCheckBox chckbxVaguear;
	private JCheckBox chckbxEvitar;
	private JCheckBox chckbxFugir;
	private JButton btnConectar;
	private JLabel lblConectado;

	// Texto pre-definidos

	private final String NOTCONNECTED = "Ligação ao Robot não iniciada...";

	// Variaveis Globais
	private String name;
	private int offSetLeft, offSetRight, angle, distance, radius;
	boolean robotOn;

	Comportamentos vaguear, evitar, fugir;

	// TEstes
	Vaguear va;

	myRobot robot;
	private JCheckBoxMenuItem fugirSim;

	public void run() {

	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		Gui window = new Gui();
		window.frame.setVisible(true);
		window.start();
	}

	/**
	 * Iniciar variaveis
	 */

	public void init() {
		this.name = ""; // default - EV3
		this.offSetLeft = 0;
		this.offSetRight = 0;
		this.angle = 90;
		this.distance = 20;
		this.radius = 10;
		this.robotOn = false;

		this.txtNomeRobot.setText(name);
		this.txtOffsetLeft.setText(String.valueOf(offSetLeft));
		this.txtOffsetRight.setText(String.valueOf(offSetRight));
		this.txtRadius.setText(String.valueOf(radius));
		this.txtAngle.setText(String.valueOf(angle));
		this.txtDistance.setText(String.valueOf(distance));
		
		this.fugirSim.setEnabled(false);
		
		robot = new myRobot();
		
		vaguear = new Vaguear("Vaguear", robot);
		vaguear.start();
		evitar = new Evitar("Evitar", robot, vaguear);
		evitar.start();

	}

	/**
	 * Método para Ligar/Desligar o ROBOT
	 * @throws InterruptedException 
	 */
	private void connectRobot() throws InterruptedException {
		if (robotOn) {
			updateConnect(false);
			robot.CloseEV3();
		} else {
			if (name.equals("") || name == null || name.length() <= 0) {
				txtNomeRobot.setBackground(Color.RED);
				logger("Nome do Robot vazio...");
			} else {
				logger("A iniciar ligação");
				logger("Aguarde...");
				boolean control = true;
				while (control) {
					if (robot.OpenEV3(name)) {
						updateConnect(true);
						control = false;
					} else {
						try {
							Thread.sleep(250);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

	/**
	 * 
	 * Método para alterar gráficamente a GUI.
	 * 
	 * @param value:
	 *            - true = Altera gráficamente a GUI para indicar que a há
	 *            Ligação ao Robot. - false = Altera gráficamente a GUI para
	 *            indicar que não há Ligação ao Robot.
	 * @throws InterruptedException 
	 */
	private void updateConnect(boolean value) throws InterruptedException {
		if (value) {
			btnConectar.setText("Desligar");
			lblConectado.setBackground(Color.GREEN);
			logger("Ligação ao Robot Concluída com Sucesso!");
			robotOn = true;
			this.fugirSim.setEnabled(true);
		} else {
			btnConectar.setText("Ligar");
			lblConectado.setBackground(Color.red);
			logger("Ligação ao Robot desligada com sucesso!");
			vaguear.Stop();
			evitar.Stop();
			chckbxEvitar.setSelected(false);
			chckbxVaguear.setSelected(false);
			chckbxFugir.setSelected(false);
			robotOn = false;
			this.fugirSim.setEnabled(false);
		}
	}

	/**
	 * 
	 * @param backwards:
	 *            - False = Distnace value takes its integral value - True =
	 *            Distance value is negated
	 */
	private void move(boolean backwards) {
		int dis = distance;
		if (robotOn) {
			if (backwards) {
				dis = dis * -1;
			}
			robot.Reta(dis);
		} else {
			logger(NOTCONNECTED);
		}
	}

	/**
	 * 
	 * Método para fazer o Robot Virar...
	 * 
	 * @param right
	 *            - True - Virar a direita - False - Virar a Esquerda
	 */
	private void turn(boolean right) {
		if (robotOn) {
			if (right) {
				robot.CurvarDireita(radius, angle);
			} else {
				robot.CurvarEsquerda(radius, angle);
			}
			robot.Parar(false);
		} else {
			logger(NOTCONNECTED);
		}
	}

	/**
	 * Método para PARA logo o robot.
	 */
	private void stopMove() {
		if (robotOn) {
			robot.Parar(true);
		} else {
			logger(NOTCONNECTED);
		}
	}

	/**
	 * Função que regista o texto no logger caso este esteja activo
	 * 
	 * @param text
	 *            - text - Texto para ser inserido no logger (gráfico)
	 */
	public void logger(String text) {
		if (txtrLogging.isEnabled()) {
			txtrLogging.append(text + "\n");
		}
	}

	public void clearLog() {
		if (!chckbxCheckLogging.isEnabled()) {
			txtrLogging.setEnabled(true);
			txtrLogging.setText("");
			txtrLogging.setEnabled(false);
		} else {
			txtrLogging.setText("");
		}
	}

	/**
	 * Create the application.
	 */
	public Gui() {
		super("GUI");
		initialize();
		init();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("..:FSO-TP1:..");
		frame.setResizable(false);
		frame.getContentPane().setBackground(Color.BLACK);
		frame.setBounds(100, 100, 658, 609);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);

		JPanel panelConeccao = new JPanel();
		panelConeccao.setBorder(
				new TitledBorder(null, "CONEC\u00C7\u00C3O", TitledBorder.LEFT, TitledBorder.TOP, null, Color.WHITE));
		panelConeccao.setBackground(Color.BLACK);
		panelConeccao.setBounds(10, 10, 630, 79);
		frame.getContentPane().add(panelConeccao);
		panelConeccao.setLayout(null);

		JLabel lblNomeRobot = new JLabel("Nome");
		lblNomeRobot.setHorizontalAlignment(SwingConstants.LEFT);
		lblNomeRobot.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNomeRobot.setForeground(Color.WHITE);
		lblNomeRobot.setBounds(53, 22, 47, 40);
		panelConeccao.add(lblNomeRobot);

		txtNomeRobot = new JTextField();
		txtNomeRobot.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				name = txtNomeRobot.getText();
				if (name.length() > 0) {
					txtNomeRobot.setBackground(Color.WHITE);
				}
			}
		});
		txtNomeRobot.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyChar() == KeyEvent.VK_ENTER) {
					name = txtNomeRobot.getText();
					if (name.length() > 0) {
						txtNomeRobot.setBackground(Color.WHITE);
					}
				}
			}
		});
		txtNomeRobot.setHorizontalAlignment(SwingConstants.CENTER);
		txtNomeRobot.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtNomeRobot.setBounds(110, 32, 226, 21);
		panelConeccao.add(txtNomeRobot);
		txtNomeRobot.setColumns(10);

		btnConectar = new JButton("Ligar");
		btnConectar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					connectRobot();
				} catch (InterruptedException e1) {

					e1.printStackTrace();
				}
			}
		});
		btnConectar.setToolTipText("Ligar Gestor");
		btnConectar.setEnabled(true);
		btnConectar.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnConectar.setBounds(471, 31, 112, 19);
		panelConeccao.add(btnConectar);

		lblConectado = new JLabel("");
		lblConectado.setBackground(Color.RED);
		lblConectado.setOpaque(true);
		lblConectado.setForeground(Color.RED);
		lblConectado.setBounds(471, 52, 112, 3);
		panelConeccao.add(lblConectado);

		JPanel panelRobot = new JPanel();
		panelRobot.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "ROBOT",
				TitledBorder.LEFT, TitledBorder.TOP, null, new Color(255, 255, 255)));
		panelRobot.setBackground(Color.BLACK);
		panelRobot.setBounds(10, 99, 630, 214);
		frame.getContentPane().add(panelRobot);
		panelRobot.setLayout(null);

		JLabel lblDistancia = new JLabel("Dist\u00E2ncia");
		lblDistancia.setHorizontalAlignment(SwingConstants.LEFT);
		lblDistancia.setForeground(Color.WHITE);
		lblDistancia.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblDistancia.setBounds(10, 33, 60, 50);
		panelRobot.add(lblDistancia);

		JLabel lblAngulo = new JLabel("\u00C2ngulo");
		lblAngulo.setHorizontalAlignment(SwingConstants.LEFT);
		lblAngulo.setForeground(Color.WHITE);
		lblAngulo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblAngulo.setBounds(10, 93, 60, 50);
		panelRobot.add(lblAngulo);

		JLabel lblRaio = new JLabel("Raio");
		lblRaio.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblRaio.setForeground(Color.WHITE);
		lblRaio.setBounds(10, 153, 60, 50);
		panelRobot.add(lblRaio);

		txtDistance = new JTextField();
		txtDistance.setHorizontalAlignment(SwingConstants.CENTER);
		txtDistance.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtDistance.setBounds(80, 49, 76, 19);
		panelRobot.add(txtDistance);
		txtDistance.setColumns(10);

		txtAngle = new JTextField();
		txtAngle.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtAngle.setHorizontalAlignment(SwingConstants.CENTER);
		txtAngle.setBounds(80, 111, 76, 19);
		panelRobot.add(txtAngle);
		txtAngle.setColumns(10);

		txtRadius = new JTextField();
		txtRadius.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtRadius.setHorizontalAlignment(SwingConstants.CENTER);
		txtRadius.setBounds(80, 171, 76, 19);
		panelRobot.add(txtRadius);
		txtRadius.setColumns(10);

		JButton btnAvancar = new JButton("Avan\u00E7ar");
		btnAvancar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				distance = Integer.parseInt(txtDistance.getText());
				move(false);
			}
		});
		btnAvancar.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnAvancar.setBounds(272, 44, 76, 32);
		panelRobot.add(btnAvancar);

		JButton btnParar = new JButton("Parar");
		btnParar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				stopMove();
			}
		});
		btnParar.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnParar.setBounds(272, 104, 76, 32);
		panelRobot.add(btnParar);

		JButton btnRecuar = new JButton("Recuar");
		btnRecuar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				distance = Integer.parseInt(txtDistance.getText());
				move(true);
			}
		});
		btnRecuar.setFont(new Font("Tahoma", Font.PLAIN, 10));

		btnRecuar.setBounds(272, 164, 76, 32);
		panelRobot.add(btnRecuar);

		JButton btnEsquerda = new JButton("Esquerda");
		btnEsquerda.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				radius = Integer.parseInt(txtRadius.getText());
				angle = Integer.parseInt(txtAngle.getText());
				turn(false);
			}
		});
		btnEsquerda.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnEsquerda.setBounds(173, 104, 76, 32);
		panelRobot.add(btnEsquerda);

		JButton btnDireita = new JButton("Direita");
		btnDireita.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				radius = Integer.parseInt(txtRadius.getText());
				angle = Integer.parseInt(txtAngle.getText());
				turn(true);
			}
		});
		btnDireita.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnDireita.setBounds(371, 104, 76, 32);
		panelRobot.add(btnDireita);

		JLabel lblOffsetEsq = new JLabel("Offset Esquerdo");
		lblOffsetEsq.setVerticalAlignment(SwingConstants.TOP);
		lblOffsetEsq.setForeground(Color.WHITE);
		lblOffsetEsq.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblOffsetEsq.setHorizontalAlignment(SwingConstants.CENTER);
		lblOffsetEsq.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		lblOffsetEsq.setBounds(155, 20, 113, 19);
		panelRobot.add(lblOffsetEsq);

		txtOffsetLeft = new JTextField();
		txtOffsetLeft.setHorizontalAlignment(SwingConstants.CENTER);
		txtOffsetLeft.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtOffsetLeft.setBounds(173, 49, 76, 19);
		panelRobot.add(txtOffsetLeft);
		txtOffsetLeft.setColumns(10);

		txtOffsetRight = new JTextField();
		txtOffsetRight.setHorizontalAlignment(SwingConstants.CENTER);
		txtOffsetRight.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtOffsetRight.setBounds(371, 49, 76, 19);
		panelRobot.add(txtOffsetRight);
		txtOffsetRight.setColumns(10);

		JLabel lblOffsetDrt = new JLabel("Offset Direito");
		lblOffsetDrt.setForeground(Color.WHITE);
		lblOffsetDrt.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblOffsetDrt.setHorizontalAlignment(SwingConstants.CENTER);
		lblOffsetDrt.setVerticalAlignment(SwingConstants.TOP);
		lblOffsetDrt.setBounds(359, 20, 95, 19);
		panelRobot.add(lblOffsetDrt);

		JPanel panel = new JPanel();
		panel.setForeground(Color.WHITE);
		panel.setBackground(Color.BLACK);
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Comportamentos",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 255, 255)));
		panel.setBounds(464, 33, 145, 153);
		panelRobot.add(panel);
		panel.setLayout(null);

		chckbxEvitar = new JCheckBox("Evitar");
		chckbxEvitar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (chckbxEvitar.isSelected() && robotOn) {
					evitar.Start();
				} else {
					try {
						evitar.Stop();
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		chckbxEvitar.setBounds(6, 68, 81, 21);
		panel.add(chckbxEvitar);
		chckbxEvitar.setEnabled(true);
		chckbxEvitar.setForeground(Color.WHITE);
		chckbxEvitar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		chckbxEvitar.setBackground(Color.BLACK);

		chckbxVaguear = new JCheckBox("Vaguear");
		chckbxVaguear.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void mouseClicked(MouseEvent e) {
				if (chckbxVaguear.isSelected() && robotOn) {
					vaguear.Start();
				} else {
					try {
						vaguear.Stop();
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}

			}
		});
		chckbxVaguear.setBounds(6, 33, 81, 21);
		panel.add(chckbxVaguear);
		chckbxVaguear.setEnabled(true);
		chckbxVaguear.setFont(new Font("Tahoma", Font.PLAIN, 15));
		chckbxVaguear.setForeground(Color.WHITE);
		chckbxVaguear.setBackground(Color.BLACK);

		chckbxFugir = new JCheckBox("Fugir");
		chckbxFugir.setBounds(6, 105, 81, 21);
		panel.add(chckbxFugir);
		chckbxFugir.setForeground(Color.WHITE);
		chckbxFugir.setBackground(Color.BLACK);
		chckbxFugir.setFont(new Font("Tahoma", Font.PLAIN, 15));

		JPanel panelLogging = new JPanel();
		panelLogging
				.setBorder(new TitledBorder(null, "LOGGING", TitledBorder.LEFT, TitledBorder.TOP, null, Color.WHITE));
		panelLogging.setBackground(Color.BLACK);
		panelLogging.setBounds(10, 323, 630, 228);
		frame.getContentPane().add(panelLogging);
		panelLogging.setLayout(null);

		chckbxCheckLogging = new JCheckBox("Ativar Logger");
		chckbxCheckLogging.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (chckbxCheckLogging.isSelected()) {
					txtrLogging.setEnabled(true);
				} else {
					txtrLogging.setEnabled(false);
				}
			}
		});
		chckbxCheckLogging.setSelected(true);
		chckbxCheckLogging.setFont(new Font("Tahoma", Font.PLAIN, 15));
		chckbxCheckLogging.setForeground(Color.WHITE);
		chckbxCheckLogging.setBackground(Color.BLACK);
		chckbxCheckLogging.setBounds(6, 17, 157, 27);
		panelLogging.add(chckbxCheckLogging);

		JButton btnClearLogging = new JButton("Limpar Logger");
		btnClearLogging.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				clearLog();
			}
		});
		btnClearLogging.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnClearLogging.setBounds(510, 22, 110, 21);
		panelLogging.add(btnClearLogging);

		JScrollPane spLogging = new JScrollPane();
		spLogging.setFont(new Font("Tahoma", Font.PLAIN, 15));
		spLogging.setBackground(Color.BLACK);
		spLogging.setBounds(10, 50, 610, 168);
		panelLogging.add(spLogging);

		txtrLogging = new JTextArea();
		txtrLogging.setEditable(false);
		txtrLogging.setLineWrap(true);
		spLogging.setViewportView(txtrLogging);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnHelpers = new JMenu("helpers");
		menuBar.add(mnHelpers);
		
		fugirSim = new JCheckBoxMenuItem("Simulate RUN");
		mnHelpers.add(fugirSim);
	}
}
