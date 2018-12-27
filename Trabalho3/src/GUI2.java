import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

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
import java.awt.CardLayout;
import javax.swing.JButton;

public class GUI2 {

	private JFrame frmTp;

	private RobotPlayer robot;
	private JCheckBox chckbxGravar;
	private JTextArea textArea;

	private final String isSaving = "Estado: A Gravar...";
	private final String isNotSaving = "Estado: A Espera...";
	private final String isPlaying = "Estado: A Reproduzir...";
	private boolean saving = false;
	private boolean replay = false;
	private boolean toBegin = false;

	/**
	 * Launch the application.
	 */
	// public static void main(String[] args) {
	// EventQueue.invokeLater(new Runnable() {
	// public void run() {
	// try {
	// GUI2 window = new GUI2(null);
	// window.frmTp.setVisible(true);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// });
	// }

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

	private void beginSaving() {
		if (saving || replay || toBegin) {
			Object[] options = { "Parar", "Cancelar" };
			int n = JOptionPane.showOptionDialog(null,
					"Encontra-se a Gravar/Reproduzir neste momento o que deseja fazer?", "Gravação/Reprodução em curso",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
			if (n == 0) {
				Stop();
			}
		} else {
			chckbxGravar.setText(isSaving);
			chckbxGravar.setSelected(true);
			saving = true;
			robot.startSaving();
		}
	}

	private void beginReplay() {
		if (saving || replay || toBegin) {
			Object[] options = { "Parar", "Cancelar" };
			int n = JOptionPane.showOptionDialog(null,
					"Encontra-se a Gravar/Reproduzir neste momento o que deseja fazer?", "Gravação/Reprodução em curso",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
			if (n == 0) {
				Stop();
			}
		} else {
			chckbxGravar.setText(isPlaying);
			chckbxGravar.setSelected(true);
			replay = true;
			robot.showTraj(false);
		}

	}

	private void toBegin() {
		if (saving || replay || toBegin) {
			Object[] options = { "Parar", "Cancelar" };
			int n = JOptionPane.showOptionDialog(null,
					"Encontra-se a Gravar/Reproduzir neste momento o que deseja fazer?", "Gravação/Reprodução em curso",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
			if (n == 0) {
				Stop();
			}
		} else {
			chckbxGravar.setText(isPlaying);
			chckbxGravar.setSelected(true);
			toBegin = true;
			robot.showTraj(true);
		}
	}

	private void Stop() {
		if (saving || replay || toBegin) {
			chckbxGravar.setText(isNotSaving);
			chckbxGravar.setSelected(false);
			robot.stopSaving();
			saving = false;
			replay = false;
			toBegin = false;
		}
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

		JButton btnGravarTrajetoria = new JButton("Gravar Trajetoria");
		btnGravarTrajetoria.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				beginSaving();
			}
		});
		btnGravarTrajetoria.setBounds(10, 39, 189, 23);
		panel.add(btnGravarTrajetoria);

		JButton btnReproduzirTrajetoria = new JButton("Reproduzir Trajetoria");
		btnReproduzirTrajetoria.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				beginReplay();
			}
		});
		btnReproduzirTrajetoria.setBounds(10, 73, 189, 23);
		panel.add(btnReproduzirTrajetoria);

		JButton btnVoltarAoPonto = new JButton("Voltar ao Ponto Inicial");
		btnVoltarAoPonto.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				toBegin();
			}
		});
		btnVoltarAoPonto.setBounds(10, 107, 189, 23);
		panel.add(btnVoltarAoPonto);

		JButton btnPararGravaoOu = new JButton("Parar Grava\u00E7\u00E3o ou Reprodu\u00E7\u00E3o");
		btnPararGravaoOu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Stop();
			}
		});
		btnPararGravaoOu.setBounds(10, 141, 189, 23);
		panel.add(btnPararGravaoOu);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(230, 17, 294, 179);
		frmTp.getContentPane().add(scrollPane);

		JPanel panel_1 = new JPanel();
		scrollPane.setColumnHeaderView(panel_1);
		panel_1.setLayout(new CardLayout(0, 0));

		chckbxGravar = new JCheckBox(isNotSaving);
		chckbxGravar.setEnabled(false);
		panel_1.add(chckbxGravar, "name_683772691285094");

		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		frmTp.setBounds(100, 100, 540, 235);
		frmTp.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}
}
