import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import javax.swing.JCheckBox;
import javax.swing.JToggleButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GUI2 {

	private JFrame frmTp;
	private JToggleButton tglbtnGravarTrajetoria;

	private RobotPlayer robot;
	private JTextArea textArea;
	private JToggleButton tglbtnReproduzirTrajetoria;
	private JToggleButton tglbtnVoltarPontoInicial;
	private JToggleButton tglbtnPararGravaoOu;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI2 window = new GUI2(null);
					window.frmTp.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI2(RobotPlayer robot) {
		initialize();
		this.robot = robot;
	}

	public void setVisible() {
		frmTp.setVisible(true);
	}

	public void RobotLogger(String text) {
		textArea.setEditable(true);
		textArea.append(text + "\n");
		textArea.setEditable(false);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTp = new JFrame();
		frmTp.setResizable(false);
		frmTp.setTitle("..:FSO-TP3 - RobotPlayer:..");
		frmTp.getContentPane().setBackground(Color.BLACK);
		frmTp.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setForeground(Color.WHITE);
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "RobotPlayer Options",
				TitledBorder.LEADING, TitledBorder.TOP, null, Color.WHITE));
		panel.setBackground(Color.BLACK);
		panel.setBounds(10, 11, 209, 189);
		frmTp.getContentPane().add(panel);
		panel.setLayout(null);

		tglbtnGravarTrajetoria = new JToggleButton("Gravar Trajetoria");
		tglbtnGravarTrajetoria.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (tglbtnGravarTrajetoria.isSelected()) {
					robot.startSaving();
				} else {
					robot.stopSaving();
				}
			}
		});
		tglbtnGravarTrajetoria.setBounds(10, 39, 189, 23);
		panel.add(tglbtnGravarTrajetoria);

		tglbtnReproduzirTrajetoria = new JToggleButton("Reproduzir Trajetoria");
		tglbtnReproduzirTrajetoria.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(tglbtnGravarTrajetoria.isSelected()) {
					robot.showTraj();
				}
			}
		});
		tglbtnReproduzirTrajetoria.setBounds(10, 73, 189, 23);
		panel.add(tglbtnReproduzirTrajetoria);

		tglbtnVoltarPontoInicial = new JToggleButton("Voltar Ponto Inicial");
		tglbtnVoltarPontoInicial.setBounds(10, 107, 189, 23);
		panel.add(tglbtnVoltarPontoInicial);

		tglbtnPararGravaoOu = new JToggleButton("Parar Grava\u00E7\u00E3o ou Reprodu\u00E7\u00E3o");
		tglbtnPararGravaoOu.setBounds(10, 141, 189, 23);
		panel.add(tglbtnPararGravaoOu);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(230, 17, 294, 179);
		frmTp.getContentPane().add(scrollPane);

		textArea = new JTextArea();
		textArea.setBackground(Color.WHITE);
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		frmTp.setBounds(100, 100, 540, 235);
		frmTp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
