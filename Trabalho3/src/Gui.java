
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
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Semaphore;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import Utils.myFile;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class Gui extends Thread {

	private JFrame frmfsotp;
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

	private RobotPlayer robot;

	private Semaphore oEngTinhaRazao;

	private myFile file;
	private File f;
	private JComboBox comboBox;

	public void run() {

	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		Gui window = new Gui();
		window.frmfsotp.setVisible(true);
		window.start();
	}

	/**
	 * Iniciar variaveis
	 */

	public void init() {
		this.name = ""; // default - EV3
		this.offSetLeft = 0;
		this.offSetRight = 0;
		this.angle = 0;
		this.distance = 0;
		this.radius = 0;
		this.robotOn = false;

		this.txtNomeRobot.setText(name);
		this.txtOffsetLeft.setText(String.valueOf(offSetLeft));
		this.txtOffsetRight.setText(String.valueOf(offSetRight));
		this.txtRadius.setText(String.valueOf(radius));
		this.txtAngle.setText(String.valueOf(angle));
		this.txtDistance.setText(String.valueOf(distance));

		this.oEngTinhaRazao = new Semaphore(1);

	}

	/**
	 * Método para Ligar/Desligar o ROBOT
	 * 
	 * @throws InterruptedException
	 */
	private void connectRobot() throws InterruptedException {
		if (robotOn) {
			updateConnect(false);
			comboBox.setEnabled(true);
			robot.Close();
		} else {
			if (name.equals("") || name == null || name.length() <= 0) {
				txtNomeRobot.setBackground(Color.RED);
				logger("Nome do Robot vazio...");
			} else {
				robot = new RobotPlayer(comboBox.getSelectedItem().toString());
				logger("A iniciar ligação");
				logger("Aguarde...");
				boolean control = true;
				while (control) {
					if (robot.Open(name)) {
						updateConnect(true);
						control = false;
						this.vaguear = new Vaguear("Vaguear", this.oEngTinhaRazao, this.robot);
						this.vaguear.start();

						this.evitar = new Evitar("Evitar", this.oEngTinhaRazao, this.robot);
						this.evitar.start();

						this.fugir = new Fugir("Fugir", this.oEngTinhaRazao, this.robot, this.vaguear);
						this.fugir.start();
						comboBox.setEnabled(false);
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
			txtNomeRobot.setEditable(false);
			txtNomeRobot.setForeground(Color.gray);
			logger("Ligação ao Robot Concluída com Sucesso!");
			robotOn = true;
		} else {
			btnConectar.setText("Ligar");
			lblConectado.setBackground(Color.red);
			logger("Ligação ao Robot desligada com sucesso!");
			// vaguear.Stop();
			// evitar.Stop();
			chckbxEvitar.setSelected(false);
			chckbxVaguear.setSelected(false);
			txtNomeRobot.setEditable(true);
			txtNomeRobot.setForeground(Color.black);
			chckbxFugir.setSelected(false);
			robotOn = false;
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

	private void autoLoadConfiguration(String robotName) {
		try {
			file = new myFile(robotName, true);
			setConfinguration(file.read());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				file.closeChannel();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void saveConfigurations() {
		try {
			file = new myFile(this.name, false);

			file.write("robotName=" + this.name);
			file.write("offSetLeft=" + this.offSetLeft);
			file.write("offSetRight=" + this.offSetRight);
			file.write("angle=" + this.angle);
			file.write("distance=" + this.distance);
			file.write("radius=" + this.radius);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				file.closeChannel();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void readConfigurations() {
		JFileChooser fc = new JFileChooser();
		File dir = new File(System.getProperty("user.dir"));
		fc.setCurrentDirectory(dir);
		fc.setDialogTitle("Escolha uma configuração");
		int returnVal = fc.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			f = fc.getSelectedFile();
			try {
				file = new myFile(f.getName().substring(0, f.getName().lastIndexOf(".")), true);
				setConfinguration(file.read());
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					file.closeChannel();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	private void setConfinguration(String[] value) {
		for (int i = 0; i < value.length; i++) {
			String action = value[i].substring(0, value[i].lastIndexOf("="));
			String aValue = value[i].substring(value[i].lastIndexOf("=") + 1);
			switch (action) {
			case "robotName":
				this.name = aValue;
				this.txtNomeRobot.setText(aValue);
				break;
			case "offSetLeft":
				this.offSetLeft = Integer.parseInt(aValue);
				this.txtOffsetLeft.setText(aValue);
				break;
			case "offSetRight":
				this.offSetRight = Integer.parseInt(aValue);
				this.txtOffsetRight.setText(aValue);
				break;
			case "angle":
				this.angle = Integer.parseInt(aValue);
				this.txtAngle.setText(aValue);
				break;
			case "distance":
				this.distance = Integer.parseInt(aValue);
				this.txtDistance.setText(aValue);
				break;
			case "radius":
				this.radius = Integer.parseInt(aValue);
				this.txtRadius.setText(aValue);
				break;
			default:
				break;
			}
		}
		logger("Definições carregadas com sucesso...");
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
		frmfsotp = new JFrame();
		frmfsotp.setTitle("..:FSO-TP3:..");
		frmfsotp.setResizable(false);
		frmfsotp.getContentPane().setBackground(Color.BLACK);
		frmfsotp.setBounds(100, 100, 653, 605);
		frmfsotp.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmfsotp.getContentPane().setLayout(null);
		frmfsotp.setLocationRelativeTo(null);

		JPanel panelConeccao = new JPanel();
		panelConeccao.setBorder(
				new TitledBorder(null, "CONEC\u00C7\u00C3O", TitledBorder.LEFT, TitledBorder.TOP, null, Color.WHITE));
		panelConeccao.setBackground(Color.BLACK);
		panelConeccao.setBounds(10, 10, 630, 79);
		frmfsotp.getContentPane().add(panelConeccao);
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
					autoLoadConfiguration(name);
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
		btnConectar.setBounds(383, 31, 112, 19);
		panelConeccao.add(btnConectar);

		lblConectado = new JLabel("");
		lblConectado.setBackground(Color.RED);
		lblConectado.setOpaque(true);
		lblConectado.setForeground(Color.RED);
		lblConectado.setBounds(383, 52, 112, 3);
		panelConeccao.add(lblConectado);

		comboBox = new JComboBox();
		comboBox.setBounds(518, 31, 98, 23);
		panelConeccao.add(comboBox);
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "Simular", "EV3", "NXT" }));

		JPanel panelRobot = new JPanel();
		panelRobot.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "ROBOT",
				TitledBorder.LEFT, TitledBorder.TOP, null, new Color(255, 255, 255)));
		panelRobot.setBackground(Color.BLACK);
		panelRobot.setBounds(10, 99, 630, 214);
		frmfsotp.getContentPane().add(panelRobot);
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
		txtDistance.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyChar() == KeyEvent.VK_ENTER) {
					distance = Integer.parseInt(txtDistance.getText());
				}
			}
		});
		txtDistance.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				distance = Integer.parseInt(txtDistance.getText());
			}
		});
		txtDistance.setHorizontalAlignment(SwingConstants.CENTER);
		txtDistance.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtDistance.setBounds(80, 49, 76, 19);
		panelRobot.add(txtDistance);
		txtDistance.setColumns(10);

		txtAngle = new JTextField();
		txtAngle.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyChar() == KeyEvent.VK_ENTER) {
					angle = Integer.parseInt(txtAngle.getText());
				}
			}
		});
		txtAngle.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				angle = Integer.parseInt(txtAngle.getText());
			}
		});
		txtAngle.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtAngle.setHorizontalAlignment(SwingConstants.CENTER);
		txtAngle.setBounds(80, 111, 76, 19);
		panelRobot.add(txtAngle);
		txtAngle.setColumns(10);

		txtRadius = new JTextField();
		txtRadius.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyChar() == KeyEvent.VK_ENTER) {
					radius = Integer.parseInt(txtRadius.getText());
				}
			}
		});
		txtRadius.addFocusListener(new FocusAdapter() {

			@Override
			public void focusLost(FocusEvent e) {
				radius = Integer.parseInt(txtRadius.getText());
			}
		});
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
		txtOffsetLeft.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyChar() == KeyEvent.VK_ENTER) {
					offSetLeft = Integer.parseInt(txtOffsetLeft.getText());
				}
			}
		});
		txtOffsetLeft.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				offSetLeft = Integer.parseInt(txtOffsetLeft.getText());
			}
		});
		txtOffsetLeft.setHorizontalAlignment(SwingConstants.CENTER);
		txtOffsetLeft.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtOffsetLeft.setBounds(173, 49, 76, 19);
		panelRobot.add(txtOffsetLeft);
		txtOffsetLeft.setColumns(10);

		txtOffsetRight = new JTextField();
		txtOffsetRight.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyChar() == KeyEvent.VK_ENTER) {
					offSetRight = Integer.parseInt(txtOffsetRight.getText());
				}
			}
		});
		txtOffsetRight.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				offSetRight = Integer.parseInt(txtOffsetRight.getText());
			}
		});
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
			@Override
			public void mouseClicked(MouseEvent e) {
				if (chckbxVaguear.isSelected() && robotOn) {
					vaguear.Start();
					vaguear.setIsRunning(true);
				} else {
					try {
						vaguear.Stop();
						vaguear.setIsRunning(false);
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
		chckbxFugir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (chckbxFugir.isSelected() && robotOn) {
					fugir.Start();
				} else {
					try {
						fugir.Stop();
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
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
		frmfsotp.getContentPane().add(panelLogging);
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
		frmfsotp.setJMenuBar(menuBar);

		JMenu mnMore = new JMenu("More");
		menuBar.add(mnMore);

		JMenuItem mntmGravarConfiguraes = new JMenuItem("Gravar Configura\u00E7\u00F5es ...");
		mntmGravarConfiguraes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveConfigurations();
			}
		});
		mnMore.add(mntmGravarConfiguraes);

		JMenuItem mntmCarregarConfiguraes = new JMenuItem("Carregar Configura\u00E7\u00F5es ...");
		mntmCarregarConfiguraes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				readConfigurations();
			}
		});
		mnMore.add(mntmCarregarConfiguraes);
	}
}
