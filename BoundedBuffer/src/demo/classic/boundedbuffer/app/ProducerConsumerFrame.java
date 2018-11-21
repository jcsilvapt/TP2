package demo.classic.boundedbuffer.app;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

import demo.classic.boundedbuffer.IActorLifeCycle;
import demo.classic.boundedbuffer.IBoundedBuffer;
import demo.classic.boundedbuffer.IBoundedBufferGUI;
import demo.classic.boundedbuffer.actors.ThreadConsumer;
import demo.classic.boundedbuffer.actors.ThreadProducer;
import demo.classic.boundedbuffer.gui.IBoundedBufferGUIImpl;
import demo.classic.boundedbuffer.impl.*;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright (c) 2003-2018
 *
 * <p>
 * Company: ADEETC
 *
 * @author Carlos Gonçalves &amp; Nuno Oliveira
 * @version 1.0
 */
public class ProducerConsumerFrame extends JFrame implements IActorLifeCycle {

	private void algVer1() {
		try {
			this.bufferInterface.resetDisplay();
			this.buffer = new BoundedBufferImplVer1<>(IBoundedBuffer.DefaultDimBuffer, this.bufferInterface);
			this.setTitle("Problemas Clássicos - Produtor/Consumidor - " + this.buffer.description());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void algVer2() {
		try {
			this.bufferInterface.resetDisplay();
			this.buffer = new BoundedBufferImplVer2<Character>(IBoundedBuffer.DefaultDimBuffer, bufferInterface);
			this.setTitle("Problemas Clássicos - Produtor/Consumidor - " + this.buffer.description());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void algVer3() {
		try {
			this.bufferInterface.resetDisplay();
			this.buffer = new BoundedBufferImplVer3<Character>(IBoundedBuffer.DefaultDimBuffer, bufferInterface);
			this.setTitle("Problemas Clássicos - Produtor/Consumidor - " + this.buffer.description());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void algVer4() {
		try {
			this.bufferInterface.resetDisplay();
			this.buffer = new BoundedBufferImplVer4<Character>(IBoundedBuffer.DefaultDimBuffer, bufferInterface);
			this.setTitle("Problemas Clássicos - Produtor/Consumidor - " + this.buffer.description());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void algVer5() {
		try {
			this.bufferInterface.resetDisplay();
			this.buffer = new BoundedBufferImplVer5<Character>(IBoundedBuffer.DefaultDimBuffer, bufferInterface);
			this.setTitle("Problemas Clássicos - Produtor/Consumidor - " + this.buffer.description());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private static final long serialVersionUID = 1L;

	private int numProdutores = 0;

	private int numConsumidores = 0;

	private IBoundedBuffer<Character> buffer;

	private IBoundedBufferGUI bufferInterface;

	private JTextField estadoProdutores[] = null;

	private JSlider slidersProdutores[] = null;

	private JButton botoesStartProdutores[] = null;

	private JButton botoesStopProdutores[] = null;

	private JTextField estadoConsumidoras[] = null;

	private JTextField outputConsumidoras[] = null;

	private JSlider slidersConsumidores[] = null;

	private JButton botoesStartConsumidores[] = null;

	private JButton botoesStopConsumidores[] = null;

	private JTextField outputBuffer[] = null;

	private ThreadProducer produtores[] = new ThreadProducer[4];

	private ThreadConsumer consumidores[] = new ThreadConsumer[4];

	private void startProdutor(int index) {
		this.produtores[index] = new ThreadProducer(index, this, this.buffer, this.estadoProdutores[index]);
		this.produtores[index].start();

		this.botoesStartProdutores[index].setEnabled(false);
		this.botoesStopProdutores[index].setEnabled(true);
		this.slidersProdutores[index].setEnabled(true);

		this.buttonStartAll.setEnabled(false);
		this.buttonClose.setEnabled(false);

		++this.numProdutores;
		if (numConsumidores == 4 && numProdutores == 4)
			buttonStopAll.setEnabled(true);
	}

	private void stopProdutor(int index) {
		this.buttonStopAll.setEnabled(false);

		this.produtores[index].actorStop();
	}

	private void sleepTimeProdutor(int index) {
		int value = this.slidersProdutores[index].getValue() + 1;
		this.produtores[index].setSleepTime(value);
		this.slidersProdutores[index].setToolTipText("" + value + " ms");
	}

	private void sleepTimeConsumidor(int index) {
		int value = this.slidersConsumidores[index].getValue() + 1;
		this.consumidores[index].setSleepTime(value);
		this.slidersConsumidores[index].setToolTipText("" + value + " ms");
	}

	private void startConsumidor(int index) {
		this.consumidores[index] = new ThreadConsumer(index, this, this.buffer, this.estadoConsumidoras[index],
				this.outputConsumidoras[index]);
		this.consumidores[index].start();

		this.botoesStartConsumidores[index].setEnabled(false);
		this.botoesStopConsumidores[index].setEnabled(true);
		this.slidersConsumidores[index].setEnabled(true);

		this.buttonStartAll.setEnabled(false);
		this.buttonClose.setEnabled(false);

		++this.numConsumidores;
		if (numConsumidores == 4 && numProdutores == 4)
			buttonStopAll.setEnabled(true);
	}

	private void stopConsumidor(int index) {
		this.buttonStopAll.setEnabled(false);

		this.consumidores[index].actorStop();
	}

	@Override
	public void producerEnded(final int indexProdutor) {
		try {
			Runnable updateGui = new Runnable() {
				public void run() {
					botoesStartProdutores[indexProdutor].setEnabled(true);
					botoesStopProdutores[indexProdutor].setEnabled(false);
					slidersProdutores[indexProdutor].setEnabled(false);

					--numProdutores;
					if (numProdutores == 0 && numConsumidores == 0) {
						buttonStartAll.setEnabled(true);
						buttonStopAll.setEnabled(false);
						buttonClose.setEnabled(true);
					}
				}
			};

			SwingUtilities.invokeAndWait(updateGui);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void consumerEnded(final int indexConsumidor) {
		try {
			Runnable updateGui = new Runnable() {
				public void run() {
					botoesStartConsumidores[indexConsumidor].setEnabled(true);
					botoesStopConsumidores[indexConsumidor].setEnabled(false);
					slidersConsumidores[indexConsumidor].setEnabled(false);

					--numConsumidores;

					if (numConsumidores == 0 && numProdutores == 0) {
						buttonStartAll.setEnabled(true);
						buttonStopAll.setEnabled(false);
						buttonClose.setEnabled(true);
					}
				}
			};

			SwingUtilities.invokeAndWait(updateGui);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void myInit() throws Exception {
		this.estadoProdutores = new JTextField[] { this.jTextFieldStateThProd_0, this.jTextFieldStateThProd_1,
				this.jTextFieldStateThProd_2, this.jTextFieldStateThProd_3 };

		this.slidersProdutores = new JSlider[] { this.jSliderThProd_0, this.jSliderThProd_1, this.jSliderThProd_2,
				this.jSliderThProd_3 };

		this.botoesStartProdutores = new JButton[] { this.jButtonStartThProd_0, this.jButtonStartThProd_1,
				this.jButtonStartThProd_2, this.jButtonStartThProd_3 };

		this.botoesStopProdutores = new JButton[] { this.jButtonStopThProd_0, this.jButtonStopThProd_1,
				this.jButtonStopThProd_2, this.jButtonStopThProd_3 };

		this.estadoConsumidoras = new JTextField[] { this.jTextFieldStateThCon_0, this.jTextFieldStateThCon_1,
				this.jTextFieldStateThCon_2, this.jTextFieldStateThCon_3 };

		this.outputConsumidoras = new JTextField[] { this.jTextFieldCharThCon_0, this.jTextFieldCharThCon_1,
				this.jTextFieldCharThCon_2, this.jTextFieldCharThCon_3 };

		this.slidersConsumidores = new JSlider[] { this.jSliderThCon_0, this.jSliderThCon_1, this.jSliderThCon_2,
				this.jSliderThCon_3 };

		this.botoesStartConsumidores = new JButton[] { this.jButtonStartThCon_0, this.jButtonStartThCon_1,
				this.jButtonStartThCon_2, this.jButtonStartThCon_3 };

		this.botoesStopConsumidores = new JButton[] { this.jButtonStopThCon_0, this.jButtonStopThCon_1,
				this.jButtonStopThCon_2, this.jButtonStopThCon_3 };

		this.outputBuffer = new JTextField[] { this.buffer_00, this.buffer_01, this.buffer_02, this.buffer_03,
				this.buffer_04, this.buffer_05, this.buffer_06, this.buffer_07, this.buffer_08, this.buffer_09,
				this.buffer_10, this.buffer_11, this.buffer_12, this.buffer_13, this.buffer_14, this.buffer_15,
				this.buffer_16, this.buffer_17, this.buffer_18, this.buffer_19, this.buffer_20, this.buffer_21,
				this.buffer_22, this.buffer_23, this.buffer_24, this.buffer_25, this.buffer_26, this.buffer_27,
				this.buffer_28, this.buffer_29, this.buffer_30, this.buffer_31 };

		this.jSliderPut.setMaximum((IBoundedBuffer.DefaultDimBuffer - 2) * 1000);
		this.jSliderGet.setMaximum((IBoundedBuffer.DefaultDimBuffer - 2) * 1000);

		this.myInitVisualizadorGrafico();
		this.algVer1();
		;
	}

	private void myInitVisualizadorGrafico() {
		try {
			this.statusBar.setText("Visualização em modo gráfico");

			this.bufferInterface = new IBoundedBufferGUIImpl(this.jSliderPut, this.jSliderGet, this.outputBuffer,
					this.jTextFieldIndicePut, this.jTextFieldIndiceGet, this.jTextFieldIndiceSize,
					IBoundedBuffer.DefaultDimBuffer);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// By JBuilder
	JPanel contentPane;
	JPanel panelThreads = new JPanel();
	JPanel panelViewBuffer = new JPanel();
	TitledBorder titledBorderView;
	GridLayout layoutThreads = new GridLayout();
	GridLayout layoutThProdutoras = new GridLayout();
	JPanel panelThreadsProdutoras = new JPanel();
	TitledBorder titledBorderProdutoras;
	JPanel panelThProd_0 = new JPanel();
	JPanel panelThProd_1 = new JPanel();
	JPanel panelThProd_2 = new JPanel();
	JPanel panelThProd_3 = new JPanel();
	TitledBorder titledBorderThProd_1;
	TitledBorder titledBorderThProd_2;
	TitledBorder titledBorderThProd_3;
	TitledBorder titledBorderThProd_4;
	GridLayout layoutThProd_0 = new GridLayout();
	GridLayout layoutThProd_1 = new GridLayout();
	GridLayout layoutThProd_2 = new GridLayout();
	GridLayout layoutThProd_3 = new GridLayout();
	GridLayout layoutThConsumidoras = new GridLayout();
	JPanel panelThreadsConsumidoras = new JPanel();
	TitledBorder titledBorderConsumidoras;
	JPanel panelThCon_3 = new JPanel();
	JPanel panelThCon_2 = new JPanel();
	JPanel panelThCon_1 = new JPanel();
	JPanel panelThCon_0 = new JPanel();
	TitledBorder titledBorderThCon_1;
	TitledBorder titledBorderThCon_2;
	TitledBorder titledBorderThCon_3;
	TitledBorder titledBorderThCon_4;
	GridLayout layoutThCon_0 = new GridLayout();
	GridLayout layoutThCon_1 = new GridLayout();
	GridLayout layoutThCon_2 = new GridLayout();
	GridLayout layoutThCon_3 = new GridLayout();
	JTextField jTextFieldStateThProd_0 = new JTextField();
	JSlider jSliderThProd_0 = new JSlider();
	JButton jButtonStartThProd_0 = new JButton();
	JButton jButtonStopThProd_0 = new JButton();
	JTextField jTextFieldStateThProd_1 = new JTextField();
	JSlider jSliderThProd_1 = new JSlider();
	JButton jButtonStartThProd_1 = new JButton();
	JButton jButtonStopThProd_1 = new JButton();
	JTextField jTextFieldStateThProd_2 = new JTextField();
	JSlider jSliderThProd_2 = new JSlider();
	JButton jButtonStartThProd_2 = new JButton();
	JButton jButtonStopThProd_2 = new JButton();
	JTextField jTextFieldStateThProd_3 = new JTextField();
	JSlider jSliderThProd_3 = new JSlider();
	JButton jButtonStartThProd_3 = new JButton();
	JButton jButtonStopThProd_3 = new JButton();
	JTextField jTextFieldStateThCon_0 = new JTextField();
	JTextField jTextFieldCharThCon_0 = new JTextField();
	JSlider jSliderThCon_0 = new JSlider();
	JButton jButtonStartThCon_0 = new JButton();
	JButton jButtonStopThCon_0 = new JButton();
	JTextField jTextFieldStateThCon_1 = new JTextField();
	JTextField jTextFieldCharThCon_1 = new JTextField();
	JSlider jSliderThCon_1 = new JSlider();
	JButton jButtonStartThCon_1 = new JButton();
	JButton jButtonStopThCon_1 = new JButton();
	JTextField jTextFieldStateThCon_2 = new JTextField();
	JTextField jTextFieldCharThCon_2 = new JTextField();
	JSlider jSliderThCon_2 = new JSlider();
	JButton jButtonStartThCon_2 = new JButton();
	JButton jButtonStopThCon_2 = new JButton();
	JTextField jTextFieldStateThCon_3 = new JTextField();
	JTextField jTextFieldCharThCon_3 = new JTextField();
	JSlider jSliderThCon_3 = new JSlider();
	JButton jButtonStartThCon_3 = new JButton();
	JButton jButtonStopThCon_3 = new JButton();
	GridLayout layoutView = new GridLayout();
	JPanel panelControle = new JPanel();
	JSlider jSliderGet = new JSlider();
	JTextField buffer_09 = new JTextField();
	JTextField buffer_16 = new JTextField();
	JTextField buffer_06 = new JTextField();
	JTextField buffer_17 = new JTextField();
	JTextField buffer_28 = new JTextField();
	JTextField buffer_21 = new JTextField();
	JTextField buffer_31 = new JTextField();
	JTextField buffer_29 = new JTextField();
	JTextField buffer_18 = new JTextField();
	JTextField buffer_07 = new JTextField();
	JTextField buffer_23 = new JTextField();
	JTextField buffer_11 = new JTextField();
	JTextField buffer_08 = new JTextField();
	JTextField buffer_15 = new JTextField();
	JTextField buffer_01 = new JTextField();
	JTextField buffer_12 = new JTextField();
	JTextField buffer_10 = new JTextField();
	JTextField buffer_22 = new JTextField();
	JTextField buffer_27 = new JTextField();
	JTextField buffer_05 = new JTextField();
	JTextField buffer_00 = new JTextField();
	GridLayout layoutBuffer = new GridLayout();
	JPanel panelBuffer = new JPanel();
	JTextField buffer_02 = new JTextField();
	JTextField buffer_13 = new JTextField();
	JTextField buffer_24 = new JTextField();
	JTextField buffer_25 = new JTextField();
	JTextField buffer_14 = new JTextField();
	JTextField buffer_03 = new JTextField();
	JTextField buffer_20 = new JTextField();
	JTextField buffer_04 = new JTextField();
	JTextField buffer_19 = new JTextField();
	JTextField buffer_26 = new JTextField();
	JTextField buffer_30 = new JTextField();
	JSlider jSliderPut = new JSlider();
	JTextField jTextFieldIndiceSize = new JTextField();
	JLabel labelIndiceSize = new JLabel();
	JPanel auxPanelViewData = new JPanel();
	JTextField jTextFieldIndiceGet = new JTextField();
	GridLayout auxLayoutViewData = new GridLayout();
	JLabel labelIndicePut = new JLabel();
	JLabel labelIndiceGet = new JLabel();
	JTextField jTextFieldIndicePut = new JTextField();
	GridLayout layoutControle = new GridLayout();
	JButton buttonStartAll = new JButton();
	JButton buttonClose = new JButton();
	JButton buttonStopAll = new JButton();
	JMenuBar menuBarFrameProdCon = new JMenuBar();
	JMenu menuAlgoritmo = new JMenu();
	JMenuItem menuItemAlgoritmoVer1 = new JMenuItem();
	JMenuItem menuItemAlgoritmoVer2 = new JMenuItem();
	JMenuItem menuItemAlgoritmoVer3 = new JMenuItem();
	JMenuItem menuItemAlgoritmoVer4 = new JMenuItem();
	JMenuItem menuItemAlgoritmoVer5 = new JMenuItem();
	JPanel jPanelProdutorConsumidor = new JPanel();
	GridLayout layoutProdutorConsumidor = new GridLayout();
	BorderLayout borderLayoutProdutorConsumidor = new BorderLayout();
	JLabel statusBar = new JLabel();

	// Construct the frame
	public ProducerConsumerFrame() {
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		try {
			jbInit();

			myInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Component initialization
	private void jbInit() throws Exception {
		contentPane = (JPanel) this.getContentPane();
		contentPane.setLayout(borderLayoutProdutorConsumidor);

		jButtonStopThProd_0.setEnabled(false);
		titledBorderView = new TitledBorder("");
		titledBorderProdutoras = new TitledBorder("");
		titledBorderConsumidoras = new TitledBorder("");
		titledBorderThProd_1 = new TitledBorder("");
		titledBorderThProd_2 = new TitledBorder("");
		titledBorderThProd_3 = new TitledBorder("");
		titledBorderThProd_4 = new TitledBorder("");
		titledBorderThCon_1 = new TitledBorder("");
		titledBorderThCon_2 = new TitledBorder("");
		titledBorderThCon_3 = new TitledBorder("");
		titledBorderThCon_4 = new TitledBorder("");
		this.setJMenuBar(menuBarFrameProdCon);
		this.setSize(new Dimension(983, 722));
		this.setTitle("Problemas Clássicos - Produtor/Consumidor");
		panelViewBuffer.setBorder(titledBorderView);
		panelViewBuffer.setLayout(layoutView);
		titledBorderView.setTitle("   Buffer   ");
		titledBorderView.setBorder(BorderFactory.createLineBorder(Color.black));
		titledBorderView.setTitleFont(new java.awt.Font("Dialog", 0, 14));
		panelThreads.setLayout(layoutThreads);
		layoutThreads.setColumns(2);
		layoutThreads.setHgap(5);
		layoutThreads.setRows(1);
		layoutThreads.setVgap(10);
		panelThreadsProdutoras.setBorder(titledBorderProdutoras);
		panelThreadsProdutoras.setLayout(layoutThProdutoras);
		panelThreadsConsumidoras.setBorder(titledBorderConsumidoras);
		panelThreadsConsumidoras.setLayout(layoutThConsumidoras);
		titledBorderProdutoras.setTitle("   Threads Produtoras   ");
		titledBorderProdutoras.setBorder(BorderFactory.createLoweredBevelBorder());
		titledBorderProdutoras.setTitleJustification(4);
		titledBorderProdutoras.setTitleFont(new java.awt.Font("Dialog", 0, 14));
		titledBorderConsumidoras.setTitle("   Threads Consumidoras   ");
		titledBorderConsumidoras.setBorder(BorderFactory.createLoweredBevelBorder());
		titledBorderConsumidoras.setTitleJustification(4);
		titledBorderConsumidoras.setTitleFont(new java.awt.Font("Dialog", 0, 14));
		layoutThProdutoras.setColumns(1);
		layoutThProdutoras.setHgap(5);
		layoutThProdutoras.setRows(4);
		layoutThProdutoras.setVgap(10);
		layoutThConsumidoras.setColumns(1);
		layoutThConsumidoras.setHgap(5);
		layoutThConsumidoras.setRows(4);
		layoutThConsumidoras.setVgap(10);
		panelThProd_3.setLayout(layoutThProd_3);
		panelThProd_0.setLayout(layoutThProd_0);
		panelThProd_1.setLayout(layoutThProd_1);
		panelThProd_2.setLayout(layoutThProd_2);
		panelThProd_0.setBorder(titledBorderThProd_1);
		titledBorderThProd_1.setTitle("   Thread 1   ");
		titledBorderThProd_2.setTitle("   Thread 2   ");
		panelThProd_1.setBorder(titledBorderThProd_2);
		panelThProd_2.setBorder(titledBorderThProd_3);
		panelThProd_3.setBorder(titledBorderThProd_4);
		titledBorderThProd_3.setTitle("   Thread 3   ");
		titledBorderThProd_4.setTitle("   Thread 4   ");
		panelThCon_0.setBorder(titledBorderThCon_1);
		panelThCon_0.setLayout(layoutThCon_0);
		titledBorderThCon_1.setTitle("   Thread 1   ");
		titledBorderThCon_2.setTitle("   Thread 2   ");
		titledBorderThCon_3.setTitle("   Thread 3   ");
		titledBorderThCon_4.setTitle("   Thread 4   ");
		panelThCon_1.setBorder(titledBorderThCon_2);
		panelThCon_1.setLayout(layoutThCon_1);
		panelThCon_2.setBorder(titledBorderThCon_3);
		panelThCon_2.setLayout(layoutThCon_2);
		panelThCon_3.setBorder(titledBorderThCon_4);
		panelThCon_3.setLayout(layoutThCon_3);
		layoutThProd_0.setColumns(4);
		layoutThProd_0.setHgap(10);
		layoutThProd_1.setColumns(4);
		layoutThProd_1.setHgap(10);
		layoutThProd_2.setColumns(4);
		layoutThProd_2.setHgap(10);
		layoutThProd_3.setColumns(4);
		layoutThProd_3.setHgap(10);
		layoutThCon_0.setColumns(5);
		layoutThCon_0.setHgap(10);
		layoutThCon_1.setColumns(5);
		layoutThCon_1.setHgap(10);
		layoutThCon_2.setColumns(5);
		layoutThCon_2.setHgap(10);
		layoutThCon_3.setColumns(5);
		layoutThCon_3.setHgap(10);
		jButtonStopThProd_0.setText("Stop");
		jButtonStopThProd_0.addActionListener(new ProdutorConsumidorFrame_jButtonStopThProd_0_actionAdapter(this));
		jButtonStartThProd_0.setText("Start");
		jButtonStartThProd_0.addActionListener(new ProdutorConsumidorFrame_jButtonStartThProd_0_actionAdapter(this));
		jTextFieldStateThProd_0.setEditable(false);
		jTextFieldStateThProd_0.setText("");
		jTextFieldStateThProd_0.setHorizontalAlignment(SwingConstants.CENTER);
		jButtonStartThProd_1.setText("Start");
		jButtonStartThProd_1.addActionListener(new ProdutorConsumidorFrame_jButtonStartThProd_1_actionAdapter(this));
		jButtonStopThProd_1.setText("Stop");
		jButtonStopThProd_1.addActionListener(new ProdutorConsumidorFrame_jButtonStopThProd_1_actionAdapter(this));
		jButtonStopThProd_1.setEnabled(false);
		jTextFieldStateThProd_1.setText("");
		jTextFieldStateThProd_1.setEditable(false);
		jTextFieldStateThProd_1.setText("");
		jTextFieldStateThProd_1.setHorizontalAlignment(SwingConstants.CENTER);
		jTextFieldStateThProd_1.setEnabled(true);
		jButtonStopThProd_2.setEnabled(false);
		jButtonStopThProd_2.setSelected(false);
		jButtonStopThProd_2.setText("Stop");
		jButtonStopThProd_2.addActionListener(new ProdutorConsumidorFrame_jButtonStopThProd_2_actionAdapter(this));
		jButtonStartThProd_2.setText("Start");
		jButtonStartThProd_2.addActionListener(new ProdutorConsumidorFrame_jButtonStartThProd_2_actionAdapter(this));
		jTextFieldStateThProd_2.setEnabled(true);
		jTextFieldStateThProd_2.setText("");
		jTextFieldStateThProd_2.setEditable(false);
		jTextFieldStateThProd_2.setText("");
		jTextFieldStateThProd_2.setHorizontalAlignment(SwingConstants.CENTER);
		jButtonStopThProd_3.setText("Stop");
		jButtonStopThProd_3.addActionListener(new ProdutorConsumidorFrame_jButtonStopThProd_3_actionAdapter(this));
		jButtonStopThProd_3.setSelected(false);
		jButtonStopThProd_3.setEnabled(false);
		jButtonStartThProd_3.setText("Start");
		jButtonStartThProd_3.addActionListener(new ProdutorConsumidorFrame_jButtonStartThProd_3_actionAdapter(this));
		jTextFieldStateThProd_3.setText("");
		jTextFieldStateThProd_3.setEditable(false);
		jTextFieldStateThProd_3.setText("");
		jTextFieldStateThProd_3.setHorizontalAlignment(SwingConstants.CENTER);
		jTextFieldStateThProd_3.setEnabled(true);
		jButtonStartThCon_0.setText("Start");
		jButtonStartThCon_0.addActionListener(new ProdutorConsumidorFrame_jButtonStartThCon_0_actionAdapter(this));
		jButtonStopThCon_0.setEnabled(false);
		jButtonStopThCon_0.setSelected(false);
		jButtonStopThCon_0.setText("Stop");
		jButtonStopThCon_0.addActionListener(new ProdutorConsumidorFrame_jButtonStopThCon_0_actionAdapter(this));
		jButtonStartThCon_1.setText("Start");
		jButtonStartThCon_1.addActionListener(new ProdutorConsumidorFrame_jButtonStartThCon_1_actionAdapter(this));
		jButtonStopThCon_1.setEnabled(false);
		jButtonStopThCon_1.setSelected(false);
		jButtonStopThCon_1.setText("Stop");
		jButtonStopThCon_1.addActionListener(new ProdutorConsumidorFrame_jButtonStopThCon_1_actionAdapter(this));
		jButtonStartThCon_2.setText("Start");
		jButtonStartThCon_2.addActionListener(new ProdutorConsumidorFrame_jButtonStartThCon_2_actionAdapter(this));
		jButtonStopThCon_2.setEnabled(false);
		jButtonStopThCon_2.setSelected(false);
		jButtonStopThCon_2.setText("Stop");
		jButtonStopThCon_2.addActionListener(new ProdutorConsumidorFrame_jButtonStopThCon_2_actionAdapter(this));
		jButtonStartThCon_3.setText("Start");
		jButtonStartThCon_3.addActionListener(new ProdutorConsumidorFrame_jButtonStartThCon_3_actionAdapter(this));
		jButtonStopThCon_3.setEnabled(false);
		jButtonStopThCon_3.setSelected(false);
		jButtonStopThCon_3.setText("Stop");
		jButtonStopThCon_3.addActionListener(new ProdutorConsumidorFrame_jButtonStopThCon_3_actionAdapter(this));
		jTextFieldStateThCon_1.setEnabled(true);
		jTextFieldStateThCon_1.setText("");
		jTextFieldStateThCon_1.setEditable(false);
		jTextFieldStateThCon_1.setText("");
		jTextFieldStateThCon_1.setHorizontalAlignment(SwingConstants.CENTER);
		jTextFieldStateThCon_2.setEnabled(true);
		jTextFieldStateThCon_2.setText("");
		jTextFieldStateThCon_2.setEditable(false);
		jTextFieldStateThCon_2.setText("");
		jTextFieldStateThCon_2.setHorizontalAlignment(SwingConstants.CENTER);
		jTextFieldStateThCon_3.setEnabled(true);
		jTextFieldStateThCon_3.setText("");
		jTextFieldStateThCon_3.setEditable(false);
		jTextFieldStateThCon_3.setText("");
		jTextFieldStateThCon_3.setHorizontalAlignment(SwingConstants.CENTER);
		layoutView.setColumns(1);
		layoutView.setHgap(10);
		layoutView.setRows(5);
		layoutView.setVgap(10);
		jTextFieldStateThCon_0.setEnabled(true);
		jTextFieldStateThCon_0.setText("");
		jTextFieldStateThCon_0.setEditable(false);
		jTextFieldStateThCon_0.setText("");
		jTextFieldStateThCon_0.setHorizontalAlignment(SwingConstants.CENTER);
		jTextFieldCharThCon_0.setMinimumSize(new Dimension(6, 21));
		jTextFieldCharThCon_0.setPreferredSize(new Dimension(6, 21));
		jTextFieldCharThCon_0.setRequestFocusEnabled(true);
		jTextFieldCharThCon_0.setEditable(false);
		jTextFieldCharThCon_0.setText("");
		jTextFieldCharThCon_0.setHorizontalAlignment(SwingConstants.CENTER);
		jTextFieldCharThCon_1.setEditable(false);
		jTextFieldCharThCon_1.setText("");
		jTextFieldCharThCon_1.setHorizontalAlignment(SwingConstants.CENTER);
		jTextFieldCharThCon_2.setEditable(false);
		jTextFieldCharThCon_2.setText("");
		jTextFieldCharThCon_2.setHorizontalAlignment(SwingConstants.CENTER);
		jTextFieldCharThCon_3.setEditable(false);
		jTextFieldCharThCon_3.setText("");
		jTextFieldCharThCon_3.setHorizontalAlignment(SwingConstants.CENTER);
		jSliderThProd_0.setMajorTickSpacing(1000);
		jSliderThProd_0.setMaximum(2000);
		jSliderThProd_0.setMinorTickSpacing(500);
		jSliderThProd_0.setPaintLabels(true);
		jSliderThProd_0.setPaintTicks(true);
		jSliderThProd_0.setPaintTrack(true);
		jSliderThProd_0.setEnabled(false);
		jSliderThProd_0.setValue(490);
		jSliderThProd_0.addChangeListener(new ProdutorConsumidorFrame_jSliderThProd_0_changeAdapter(this));
		jSliderThProd_1.setMajorTickSpacing(1000);
		jSliderThProd_1.setMaximum(2000);
		jSliderThProd_1.setMinorTickSpacing(500);
		jSliderThProd_1.setPaintLabels(true);
		jSliderThProd_1.setPaintTicks(true);
		jSliderThProd_1.setEnabled(false);
		jSliderThProd_1.setValue(510);
		jSliderThProd_1.addChangeListener(new ProdutorConsumidorFrame_jSliderThProd_1_changeAdapter(this));
		jSliderThProd_2.setMajorTickSpacing(1000);
		jSliderThProd_2.setMaximum(2000);
		jSliderThProd_2.setMinorTickSpacing(500);
		jSliderThProd_2.setPaintLabels(true);
		jSliderThProd_2.setPaintTicks(true);
		jSliderThProd_2.setEnabled(false);
		jSliderThProd_2.setValue(480);
		jSliderThProd_2.addChangeListener(new ProdutorConsumidorFrame_jSliderThProd_2_changeAdapter(this));
		jSliderThProd_3.setMajorTickSpacing(1000);
		jSliderThProd_3.setMaximum(2000);
		jSliderThProd_3.setMinorTickSpacing(500);
		jSliderThProd_3.setPaintLabels(true);
		jSliderThProd_3.setPaintTicks(true);
		jSliderThProd_3.setEnabled(false);
		jSliderThProd_3.setValue(520);
		jSliderThProd_3.addChangeListener(new ProdutorConsumidorFrame_jSliderThProd_3_changeAdapter(this));
		jSliderThCon_0.setMajorTickSpacing(1000);
		jSliderThCon_0.setMaximum(2000);
		jSliderThCon_0.setMinorTickSpacing(500);
		jSliderThCon_0.setPaintLabels(true);
		jSliderThCon_0.setPaintTicks(true);
		jSliderThCon_0.setEnabled(false);
		jSliderThCon_0.setValue(510);
		jSliderThCon_0.addChangeListener(new ProdutorConsumidorFrame_jSliderThCon_0_changeAdapter(this));
		jSliderThCon_1.setMajorTickSpacing(1000);
		jSliderThCon_1.setMaximum(2000);
		jSliderThCon_1.setMinorTickSpacing(500);
		jSliderThCon_1.setPaintLabels(true);
		jSliderThCon_1.setPaintTicks(true);
		jSliderThCon_1.setEnabled(false);
		jSliderThCon_1.setValue(480);
		jSliderThCon_1.addChangeListener(new ProdutorConsumidorFrame_jSliderThCon_1_changeAdapter(this));
		jSliderThCon_2.setMajorTickSpacing(1000);
		jSliderThCon_2.setMaximum(2000);
		jSliderThCon_2.setMinorTickSpacing(500);
		jSliderThCon_2.setPaintLabels(true);
		jSliderThCon_2.setPaintTicks(true);
		jSliderThCon_2.setEnabled(false);
		jSliderThCon_2.setValue(520);
		jSliderThCon_2.addChangeListener(new ProdutorConsumidorFrame_jSliderThCon_2_changeAdapter(this));
		jSliderThCon_3.setMajorTickSpacing(1000);
		jSliderThCon_3.setMaximum(2000);
		jSliderThCon_3.setMinorTickSpacing(500);
		jSliderThCon_3.setPaintLabels(true);
		jSliderThCon_3.setPaintTicks(true);
		jSliderThCon_3.setEnabled(false);
		jSliderThCon_3.setValue(490);
		jSliderThCon_3.addChangeListener(new ProdutorConsumidorFrame_jSliderThCon_3_changeAdapter(this));
		menuAlgoritmo.setText("Algoritmo");

		menuItemAlgoritmoVer1.setText( (new BoundedBufferImplVer1<>(IBoundedBuffer.DefaultDimBuffer, this.bufferInterface)).description() );
		menuItemAlgoritmoVer1.addActionListener(new ProdutorConsumidorFrame_menuItemAlgoritmoVer1_actionAdapter(this));
		menuItemAlgoritmoVer2.setText( (new BoundedBufferImplVer2<>(IBoundedBuffer.DefaultDimBuffer, this.bufferInterface)).description() );
		menuItemAlgoritmoVer2.addActionListener(new ProdutorConsumidorFrame_menuItemAlgoritmoVer2_actionAdapter(this));
		menuItemAlgoritmoVer3.setText( (new BoundedBufferImplVer3<>(IBoundedBuffer.DefaultDimBuffer, this.bufferInterface)).description() );
		menuItemAlgoritmoVer3.addActionListener(new ProdutorConsumidorFrame_menuItemAlgoritmoVer3_actionAdapter(this));
		menuItemAlgoritmoVer4.setText( (new BoundedBufferImplVer4<>(IBoundedBuffer.DefaultDimBuffer, this.bufferInterface)).description() );
		menuItemAlgoritmoVer4.addActionListener(new ProdutorConsumidorFrame_menuItemAlgoritmoVer4_actionAdapter(this));
		menuItemAlgoritmoVer5.setText( (new BoundedBufferImplVer5<>(IBoundedBuffer.DefaultDimBuffer, this.bufferInterface)).description() );
		menuItemAlgoritmoVer5.addActionListener(new ProdutorConsumidorFrame_menuItemAlgoritmoVer5_actionAdapter(this));

		jSliderGet.setOrientation(JSlider.HORIZONTAL);
		jSliderGet.setInverted(false);
		jSliderGet.setMajorTickSpacing(0);
		jSliderGet.setMaximum(100);
		jSliderGet.setMinimum(0);
		jSliderGet.setMinorTickSpacing(0);
		jSliderGet.setPaintLabels(false);
		jSliderGet.setPaintTicks(false);
		jSliderGet.setPaintTrack(true);
		jSliderGet.setEnabled(false);
		buffer_09.setEditable(false);
		buffer_09.setHorizontalAlignment(SwingConstants.CENTER);
		buffer_16.setEditable(false);
		buffer_16.setHorizontalAlignment(SwingConstants.CENTER);
		buffer_06.setEditable(false);
		buffer_06.setHorizontalAlignment(SwingConstants.CENTER);
		buffer_17.setEditable(false);
		buffer_17.setHorizontalAlignment(SwingConstants.CENTER);
		buffer_28.setEditable(false);
		buffer_28.setHorizontalAlignment(SwingConstants.CENTER);
		buffer_21.setEditable(false);
		buffer_21.setHorizontalAlignment(SwingConstants.CENTER);
		buffer_31.setEditable(false);
		buffer_31.setHorizontalAlignment(SwingConstants.CENTER);
		buffer_29.setEditable(false);
		buffer_29.setHorizontalAlignment(SwingConstants.CENTER);
		buffer_18.setEditable(false);
		buffer_18.setHorizontalAlignment(SwingConstants.CENTER);
		buffer_07.setEditable(false);
		buffer_07.setHorizontalAlignment(SwingConstants.CENTER);
		buffer_23.setEditable(false);
		buffer_23.setHorizontalAlignment(SwingConstants.CENTER);
		buffer_11.setEditable(false);
		buffer_11.setHorizontalAlignment(SwingConstants.CENTER);
		buffer_08.setEditable(false);
		buffer_08.setHorizontalAlignment(SwingConstants.CENTER);
		buffer_15.setEditable(false);
		buffer_15.setHorizontalAlignment(SwingConstants.CENTER);
		buffer_01.setEditable(false);
		buffer_01.setHorizontalAlignment(SwingConstants.CENTER);
		buffer_12.setEditable(false);
		buffer_12.setHorizontalAlignment(SwingConstants.CENTER);
		buffer_10.setEditable(false);
		buffer_10.setHorizontalAlignment(SwingConstants.CENTER);
		buffer_22.setEditable(false);
		buffer_22.setHorizontalAlignment(SwingConstants.CENTER);
		buffer_27.setEditable(false);
		buffer_27.setHorizontalAlignment(SwingConstants.CENTER);
		buffer_05.setEditable(false);
		buffer_05.setHorizontalAlignment(SwingConstants.CENTER);
		buffer_00.setEditable(false);
		buffer_00.setHorizontalAlignment(SwingConstants.CENTER);
		layoutBuffer.setColumns(32);
		layoutBuffer.setHgap(0);
		layoutBuffer.setRows(1);
		panelBuffer.setBorder(BorderFactory.createLineBorder(Color.black));
		panelBuffer.setLayout(layoutBuffer);
		buffer_02.setEditable(false);
		buffer_02.setHorizontalAlignment(SwingConstants.CENTER);
		buffer_13.setEditable(false);
		buffer_13.setHorizontalAlignment(SwingConstants.CENTER);
		buffer_24.setEditable(false);
		buffer_24.setHorizontalAlignment(SwingConstants.CENTER);
		buffer_25.setEditable(false);
		buffer_25.setHorizontalAlignment(SwingConstants.CENTER);
		buffer_14.setEditable(false);
		buffer_14.setHorizontalAlignment(SwingConstants.CENTER);
		buffer_03.setEditable(false);
		buffer_03.setHorizontalAlignment(SwingConstants.CENTER);
		buffer_20.setEditable(false);
		buffer_20.setHorizontalAlignment(SwingConstants.CENTER);
		buffer_04.setEditable(false);
		buffer_04.setHorizontalAlignment(SwingConstants.CENTER);
		buffer_19.setEditable(false);
		buffer_19.setHorizontalAlignment(SwingConstants.CENTER);
		buffer_26.setEditable(false);
		buffer_26.setHorizontalAlignment(SwingConstants.CENTER);
		buffer_30.setEditable(false);
		buffer_30.setHorizontalAlignment(SwingConstants.CENTER);
		jSliderPut.setMajorTickSpacing(0);
		jSliderPut.setMinimum(0);
		jSliderPut.setMinorTickSpacing(0);
		jSliderPut.setPaintLabels(false);
		jSliderPut.setPaintTicks(false);
		jSliderPut.setEnabled(false);
		jTextFieldIndiceSize.setFont(new java.awt.Font("Dialog", 0, 16));
		jTextFieldIndiceSize.setEditable(false);
		jTextFieldIndiceSize.setText("---");
		jTextFieldIndiceSize.setHorizontalAlignment(SwingConstants.CENTER);
		labelIndiceSize.setFont(new java.awt.Font("Dialog", 0, 16));
		labelIndiceSize.setHorizontalAlignment(SwingConstants.RIGHT);
		labelIndiceSize.setText("size");
		auxPanelViewData.setLayout(auxLayoutViewData);
		jTextFieldIndiceGet.setFont(new java.awt.Font("Dialog", 0, 16));
		jTextFieldIndiceGet.setEditable(false);
		jTextFieldIndiceGet.setText("---");
		jTextFieldIndiceGet.setHorizontalAlignment(SwingConstants.CENTER);
		auxLayoutViewData.setColumns(6);
		auxLayoutViewData.setHgap(10);
		auxLayoutViewData.setRows(1);
		auxLayoutViewData.setVgap(10);
		labelIndicePut.setFont(new java.awt.Font("Dialog", 0, 16));
		labelIndicePut.setHorizontalAlignment(SwingConstants.RIGHT);
		labelIndicePut.setText("put");
		labelIndiceGet.setFont(new java.awt.Font("Dialog", 0, 16));
		labelIndiceGet.setHorizontalAlignment(SwingConstants.RIGHT);
		labelIndiceGet.setText("get");
		jTextFieldIndicePut.setFont(new java.awt.Font("Dialog", 0, 16));
		jTextFieldIndicePut.setToolTipText("");
		jTextFieldIndicePut.setEditable(false);
		jTextFieldIndicePut.setText("---");
		jTextFieldIndicePut.setHorizontalAlignment(SwingConstants.CENTER);
		panelControle.setLayout(layoutControle);
		layoutControle.setColumns(3);
		layoutControle.setHgap(120);
		buttonStartAll.setText("Start All");
		buttonStartAll.addActionListener(new ProdutorConsumidorFrame_buttonStartAll_actionAdapter(this));
		buttonClose.setText("Close");
		buttonClose.addActionListener(new ProdutorConsumidorFrame_buttonClose_actionAdapter(this));
		buttonStopAll.setEnabled(false);
		buttonStopAll.setText("Stop All");
		buttonStopAll.addActionListener(new ProdutorConsumidorFrame_buttonStopAll_actionAdapter(this));
		jPanelProdutorConsumidor.setLayout(layoutProdutorConsumidor);
		layoutProdutorConsumidor.setColumns(1);
		layoutProdutorConsumidor.setRows(2);
		statusBar.setText(" --- ");
		panelBuffer.add(buffer_00, null);
		panelBuffer.add(buffer_01, null);
		panelBuffer.add(buffer_02, null);
		panelBuffer.add(buffer_03, null);
		panelBuffer.add(buffer_04, null);
		panelBuffer.add(buffer_05, null);
		panelBuffer.add(buffer_06, null);
		panelBuffer.add(buffer_07, null);
		panelBuffer.add(buffer_08, null);
		panelBuffer.add(buffer_09, null);
		panelBuffer.add(buffer_10, null);
		panelBuffer.add(buffer_11, null);
		panelBuffer.add(buffer_12, null);
		panelBuffer.add(buffer_13, null);
		panelBuffer.add(buffer_14, null);
		panelBuffer.add(buffer_15, null);
		panelBuffer.add(buffer_16, null);
		panelBuffer.add(buffer_17, null);
		panelBuffer.add(buffer_18, null);
		panelBuffer.add(buffer_19, null);
		panelBuffer.add(buffer_20, null);
		panelBuffer.add(buffer_21, null);
		panelBuffer.add(buffer_22, null);
		panelBuffer.add(buffer_23, null);
		panelBuffer.add(buffer_24, null);
		panelBuffer.add(buffer_25, null);
		panelBuffer.add(buffer_26, null);
		panelBuffer.add(buffer_27, null);
		panelBuffer.add(buffer_28, null);
		panelBuffer.add(buffer_29, null);
		panelBuffer.add(buffer_30, null);
		panelBuffer.add(buffer_31, null);
		panelViewBuffer.add(auxPanelViewData, null);
		panelViewBuffer.add(jSliderGet, null);
		panelViewBuffer.add(panelBuffer, null);
		panelViewBuffer.add(jSliderPut, null);
		panelViewBuffer.add(panelControle, null);
		auxPanelViewData.add(labelIndicePut, null);
		jPanelProdutorConsumidor.add(panelThreads, null);
		jPanelProdutorConsumidor.add(panelViewBuffer, null);
		panelThCon_3.add(jTextFieldStateThCon_3, null);
		panelThCon_3.add(jTextFieldCharThCon_3, null);
		panelThCon_3.add(jSliderThCon_3, null);
		panelThCon_3.add(jButtonStartThCon_3, null);
		panelThCon_3.add(jButtonStopThCon_3, null);
		panelThreadsConsumidoras.add(panelThCon_2, null);
		panelThCon_2.add(jTextFieldStateThCon_2, null);
		panelThCon_2.add(jTextFieldCharThCon_2, null);
		panelThCon_2.add(jSliderThCon_2, null);
		panelThCon_2.add(jButtonStartThCon_2, null);
		panelThCon_2.add(jButtonStopThCon_2, null);
		panelThreadsConsumidoras.add(panelThCon_0, null);
		panelThCon_1.add(jTextFieldStateThCon_1, null);
		panelThCon_1.add(jTextFieldCharThCon_1, null);
		panelThCon_1.add(jSliderThCon_1, null);
		panelThCon_1.add(jButtonStartThCon_1, null);
		panelThCon_1.add(jButtonStopThCon_1, null);
		panelThreadsConsumidoras.add(panelThCon_3, null);
		panelThProd_0.add(jTextFieldStateThProd_0, null);
		panelThProd_0.add(jSliderThProd_0, null);
		panelThProd_0.add(jButtonStartThProd_0, null);
		panelThProd_0.add(jButtonStopThProd_0, null);
		panelThreadsProdutoras.add(panelThProd_2, null);
		panelThreadsProdutoras.add(panelThProd_1, null);
		panelThProd_1.add(jTextFieldStateThProd_1, null);
		panelThProd_1.add(jSliderThProd_1, null);
		panelThProd_1.add(jButtonStartThProd_1, null);
		panelThProd_1.add(jButtonStopThProd_1, null);
		panelThreadsProdutoras.add(panelThProd_0, null);
		panelThProd_2.add(jTextFieldStateThProd_2, null);
		panelThProd_2.add(jSliderThProd_2, null);
		panelThProd_2.add(jButtonStartThProd_2, null);
		panelThProd_2.add(jButtonStopThProd_2, null);
		panelThreadsProdutoras.add(panelThProd_3, null);
		panelThProd_3.add(jTextFieldStateThProd_3, null);
		panelThProd_3.add(jSliderThProd_3, null);
		panelThProd_3.add(jButtonStartThProd_3, null);
		panelThProd_3.add(jButtonStopThProd_3, null);
		panelThreads.add(panelThreadsConsumidoras, null);
		panelThreads.add(panelThreadsProdutoras, null);
		panelThCon_0.add(jTextFieldStateThCon_0, null);
		panelThCon_0.add(jTextFieldCharThCon_0, null);
		panelThCon_0.add(jSliderThCon_0, null);
		panelThCon_0.add(jButtonStartThCon_0, null);
		panelThCon_0.add(jButtonStopThCon_0, null);
		panelThreadsConsumidoras.add(panelThCon_1, null);
		menuBarFrameProdCon.add(menuAlgoritmo);
		menuAlgoritmo.add(menuItemAlgoritmoVer1);
		menuAlgoritmo.add(menuItemAlgoritmoVer2);
		menuAlgoritmo.add(menuItemAlgoritmoVer3);
		menuAlgoritmo.add(menuItemAlgoritmoVer4);
		menuAlgoritmo.add(menuItemAlgoritmoVer5);
		panelControle.add(buttonStartAll, null);
		panelControle.add(buttonStopAll, null);
		panelControle.add(buttonClose, null);
		auxPanelViewData.add(jTextFieldIndicePut, null);
		auxPanelViewData.add(labelIndiceSize, null);
		auxPanelViewData.add(jTextFieldIndiceSize, null);
		auxPanelViewData.add(labelIndiceGet, null);
		auxPanelViewData.add(jTextFieldIndiceGet, null);

		contentPane.add(jPanelProdutorConsumidor, BorderLayout.CENTER);

		contentPane.add(statusBar, BorderLayout.SOUTH);
	}

	// Overridden so we can exit when window is closed
	protected void processWindowEvent(WindowEvent e) {
		super.processWindowEvent(e);
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			System.exit(0);
		}
	}

	void jButtonStartThProd_0_actionPerformed(ActionEvent e) {
		this.startProdutor(0);
	}

	void jButtonStartThProd_1_actionPerformed(ActionEvent e) {
		this.startProdutor(1);
	}

	void jButtonStartThProd_2_actionPerformed(ActionEvent e) {
		this.startProdutor(2);
	}

	void jButtonStartThProd_3_actionPerformed(ActionEvent e) {
		this.startProdutor(3);
	}

	void jButtonStartThCon_0_actionPerformed(ActionEvent e) {
		this.startConsumidor(0);
	}

	void jButtonStartThCon_1_actionPerformed(ActionEvent e) {
		this.startConsumidor(1);
	}

	void jButtonStartThCon_2_actionPerformed(ActionEvent e) {
		this.startConsumidor(2);
	}

	void jButtonStartThCon_3_actionPerformed(ActionEvent e) {
		this.startConsumidor(3);
	}

	void jButtonStopThProd_0_actionPerformed(ActionEvent e) {
		this.stopProdutor(0);
	}

	void jButtonStopThProd_1_actionPerformed(ActionEvent e) {
		this.stopProdutor(1);
	}

	void jButtonStopThProd_2_actionPerformed(ActionEvent e) {
		this.stopProdutor(2);
	}

	void jButtonStopThProd_3_actionPerformed(ActionEvent e) {
		this.stopProdutor(3);
	}

	void jButtonStopThCon_0_actionPerformed(ActionEvent e) {
		this.stopConsumidor(0);
	}

	void jButtonStopThCon_1_actionPerformed(ActionEvent e) {
		this.stopConsumidor(1);
	}

	void jButtonStopThCon_2_actionPerformed(ActionEvent e) {
		this.stopConsumidor(2);
	}

	void jButtonStopThCon_3_actionPerformed(ActionEvent e) {
		this.stopConsumidor(3);
	}

	void jSliderThProd_0_stateChanged(ChangeEvent e) {
		this.sleepTimeProdutor(0);
	}

	void jSliderThProd_1_stateChanged(ChangeEvent e) {
		this.sleepTimeProdutor(1);
	}

	void jSliderThProd_2_stateChanged(ChangeEvent e) {
		this.sleepTimeProdutor(2);
	}

	void jSliderThProd_3_stateChanged(ChangeEvent e) {
		this.sleepTimeProdutor(3);
	}

	void jSliderThCon_0_stateChanged(ChangeEvent e) {
		this.sleepTimeConsumidor(0);
	}

	void jSliderThCon_1_stateChanged(ChangeEvent e) {
		this.sleepTimeConsumidor(1);
	}

	void jSliderThCon_2_stateChanged(ChangeEvent e) {
		this.sleepTimeConsumidor(2);
	}

	void jSliderThCon_3_stateChanged(ChangeEvent e) {
		this.sleepTimeConsumidor(3);
	}

	void menuItemAlgoritmoVer1_actionPerformed(ActionEvent e) {
		this.algVer1();
	}

	void menuItemAlgoritmoVer2_actionPerformed(ActionEvent e) {
		this.algVer2();
	}

	void menuItemAlgoritmoVer3_actionPerformed(ActionEvent e) {
		this.algVer3();
	}

	void menuItemAlgoritmoVer4_actionPerformed(ActionEvent e) {
		this.algVer4();
	}

	void menuItemAlgoritmoVer5_actionPerformed(ActionEvent e) {
		this.algVer5();
	}

	void buttonStartAll_actionPerformed(ActionEvent e) {
		for (int indexProdutor = 0; indexProdutor < this.produtores.length; ++indexProdutor)
			this.startProdutor(indexProdutor);

		for (int indexConsumidor = 0; indexConsumidor < this.produtores.length; ++indexConsumidor)
			this.startConsumidor(indexConsumidor);

		this.buttonStartAll.setEnabled(false);
		this.buttonStopAll.setEnabled(true);
		this.buttonClose.setEnabled(false);
	}

	void buttonStopAll_actionPerformed(ActionEvent e) {
		for (int indexProdutor = 0; indexProdutor < this.produtores.length; ++indexProdutor)
			this.stopProdutor(indexProdutor);

		for (int indexConsumidor = 0; indexConsumidor < this.produtores.length; ++indexConsumidor)
			this.stopConsumidor(indexConsumidor);
	}

	void buttonClose_actionPerformed(ActionEvent e) {
		System.exit(0);
	}

	void menuItemVisualizadorGrafico_actionPerformed(ActionEvent e) {
		this.myInitVisualizadorGrafico();
	}
}

class ProdutorConsumidorFrame_jButtonStartThProd_0_actionAdapter implements java.awt.event.ActionListener {
	ProducerConsumerFrame adaptee;

	ProdutorConsumidorFrame_jButtonStartThProd_0_actionAdapter(ProducerConsumerFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonStartThProd_0_actionPerformed(e);
	}
}

class ProdutorConsumidorFrame_jButtonStartThProd_1_actionAdapter implements java.awt.event.ActionListener {
	ProducerConsumerFrame adaptee;

	ProdutorConsumidorFrame_jButtonStartThProd_1_actionAdapter(ProducerConsumerFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonStartThProd_1_actionPerformed(e);
	}
}

class ProdutorConsumidorFrame_jButtonStartThProd_2_actionAdapter implements java.awt.event.ActionListener {
	ProducerConsumerFrame adaptee;

	ProdutorConsumidorFrame_jButtonStartThProd_2_actionAdapter(ProducerConsumerFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonStartThProd_2_actionPerformed(e);
	}
}

class ProdutorConsumidorFrame_jButtonStartThProd_3_actionAdapter implements java.awt.event.ActionListener {
	ProducerConsumerFrame adaptee;

	ProdutorConsumidorFrame_jButtonStartThProd_3_actionAdapter(ProducerConsumerFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonStartThProd_3_actionPerformed(e);
	}
}

class ProdutorConsumidorFrame_jButtonStartThCon_0_actionAdapter implements java.awt.event.ActionListener {
	ProducerConsumerFrame adaptee;

	ProdutorConsumidorFrame_jButtonStartThCon_0_actionAdapter(ProducerConsumerFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonStartThCon_0_actionPerformed(e);
	}
}

class ProdutorConsumidorFrame_jButtonStartThCon_1_actionAdapter implements java.awt.event.ActionListener {
	ProducerConsumerFrame adaptee;

	ProdutorConsumidorFrame_jButtonStartThCon_1_actionAdapter(ProducerConsumerFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonStartThCon_1_actionPerformed(e);
	}
}

class ProdutorConsumidorFrame_jButtonStartThCon_2_actionAdapter implements java.awt.event.ActionListener {
	ProducerConsumerFrame adaptee;

	ProdutorConsumidorFrame_jButtonStartThCon_2_actionAdapter(ProducerConsumerFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonStartThCon_2_actionPerformed(e);
	}
}

class ProdutorConsumidorFrame_jButtonStartThCon_3_actionAdapter implements java.awt.event.ActionListener {
	ProducerConsumerFrame adaptee;

	ProdutorConsumidorFrame_jButtonStartThCon_3_actionAdapter(ProducerConsumerFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonStartThCon_3_actionPerformed(e);
	}
}

class ProdutorConsumidorFrame_jButtonStopThProd_0_actionAdapter implements java.awt.event.ActionListener {
	ProducerConsumerFrame adaptee;

	ProdutorConsumidorFrame_jButtonStopThProd_0_actionAdapter(ProducerConsumerFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonStopThProd_0_actionPerformed(e);
	}
}

class ProdutorConsumidorFrame_jButtonStopThProd_1_actionAdapter implements java.awt.event.ActionListener {
	ProducerConsumerFrame adaptee;

	ProdutorConsumidorFrame_jButtonStopThProd_1_actionAdapter(ProducerConsumerFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonStopThProd_1_actionPerformed(e);
	}
}

class ProdutorConsumidorFrame_jButtonStopThProd_2_actionAdapter implements java.awt.event.ActionListener {
	ProducerConsumerFrame adaptee;

	ProdutorConsumidorFrame_jButtonStopThProd_2_actionAdapter(ProducerConsumerFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonStopThProd_2_actionPerformed(e);
	}
}

class ProdutorConsumidorFrame_jButtonStopThProd_3_actionAdapter implements java.awt.event.ActionListener {
	ProducerConsumerFrame adaptee;

	ProdutorConsumidorFrame_jButtonStopThProd_3_actionAdapter(ProducerConsumerFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonStopThProd_3_actionPerformed(e);
	}
}

class ProdutorConsumidorFrame_jButtonStopThCon_0_actionAdapter implements java.awt.event.ActionListener {
	ProducerConsumerFrame adaptee;

	ProdutorConsumidorFrame_jButtonStopThCon_0_actionAdapter(ProducerConsumerFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonStopThCon_0_actionPerformed(e);
	}
}

class ProdutorConsumidorFrame_jButtonStopThCon_1_actionAdapter implements java.awt.event.ActionListener {
	ProducerConsumerFrame adaptee;

	ProdutorConsumidorFrame_jButtonStopThCon_1_actionAdapter(ProducerConsumerFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonStopThCon_1_actionPerformed(e);
	}
}

class ProdutorConsumidorFrame_jButtonStopThCon_2_actionAdapter implements java.awt.event.ActionListener {
	ProducerConsumerFrame adaptee;

	ProdutorConsumidorFrame_jButtonStopThCon_2_actionAdapter(ProducerConsumerFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonStopThCon_2_actionPerformed(e);
	}
}

class ProdutorConsumidorFrame_jButtonStopThCon_3_actionAdapter implements java.awt.event.ActionListener {
	ProducerConsumerFrame adaptee;

	ProdutorConsumidorFrame_jButtonStopThCon_3_actionAdapter(ProducerConsumerFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonStopThCon_3_actionPerformed(e);
	}
}

class ProdutorConsumidorFrame_jSliderThProd_0_changeAdapter implements javax.swing.event.ChangeListener {
	ProducerConsumerFrame adaptee;

	ProdutorConsumidorFrame_jSliderThProd_0_changeAdapter(ProducerConsumerFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void stateChanged(ChangeEvent e) {
		adaptee.jSliderThProd_0_stateChanged(e);
	}
}

class ProdutorConsumidorFrame_jSliderThProd_1_changeAdapter implements javax.swing.event.ChangeListener {
	ProducerConsumerFrame adaptee;

	ProdutorConsumidorFrame_jSliderThProd_1_changeAdapter(ProducerConsumerFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void stateChanged(ChangeEvent e) {
		adaptee.jSliderThProd_1_stateChanged(e);
	}
}

class ProdutorConsumidorFrame_jSliderThProd_2_changeAdapter implements javax.swing.event.ChangeListener {
	ProducerConsumerFrame adaptee;

	ProdutorConsumidorFrame_jSliderThProd_2_changeAdapter(ProducerConsumerFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void stateChanged(ChangeEvent e) {
		adaptee.jSliderThProd_2_stateChanged(e);
	}
}

class ProdutorConsumidorFrame_jSliderThProd_3_changeAdapter implements javax.swing.event.ChangeListener {
	ProducerConsumerFrame adaptee;

	ProdutorConsumidorFrame_jSliderThProd_3_changeAdapter(ProducerConsumerFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void stateChanged(ChangeEvent e) {
		adaptee.jSliderThProd_3_stateChanged(e);
	}
}

class ProdutorConsumidorFrame_jSliderThCon_0_changeAdapter implements javax.swing.event.ChangeListener {
	ProducerConsumerFrame adaptee;

	ProdutorConsumidorFrame_jSliderThCon_0_changeAdapter(ProducerConsumerFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void stateChanged(ChangeEvent e) {
		adaptee.jSliderThCon_0_stateChanged(e);
	}
}

class ProdutorConsumidorFrame_jSliderThCon_1_changeAdapter implements javax.swing.event.ChangeListener {
	ProducerConsumerFrame adaptee;

	ProdutorConsumidorFrame_jSliderThCon_1_changeAdapter(ProducerConsumerFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void stateChanged(ChangeEvent e) {
		adaptee.jSliderThCon_1_stateChanged(e);
	}
}

class ProdutorConsumidorFrame_jSliderThCon_2_changeAdapter implements javax.swing.event.ChangeListener {
	ProducerConsumerFrame adaptee;

	ProdutorConsumidorFrame_jSliderThCon_2_changeAdapter(ProducerConsumerFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void stateChanged(ChangeEvent e) {
		adaptee.jSliderThCon_2_stateChanged(e);
	}
}

class ProdutorConsumidorFrame_jSliderThCon_3_changeAdapter implements javax.swing.event.ChangeListener {
	ProducerConsumerFrame adaptee;

	ProdutorConsumidorFrame_jSliderThCon_3_changeAdapter(ProducerConsumerFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void stateChanged(ChangeEvent e) {
		adaptee.jSliderThCon_3_stateChanged(e);
	}
}

class ProdutorConsumidorFrame_buttonStartAll_actionAdapter implements java.awt.event.ActionListener {
	ProducerConsumerFrame adaptee;

	ProdutorConsumidorFrame_buttonStartAll_actionAdapter(ProducerConsumerFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.buttonStartAll_actionPerformed(e);
	}
}

class ProdutorConsumidorFrame_buttonStopAll_actionAdapter implements java.awt.event.ActionListener {
	ProducerConsumerFrame adaptee;

	ProdutorConsumidorFrame_buttonStopAll_actionAdapter(ProducerConsumerFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.buttonStopAll_actionPerformed(e);
	}
}

class ProdutorConsumidorFrame_buttonClose_actionAdapter implements java.awt.event.ActionListener {
	ProducerConsumerFrame adaptee;

	ProdutorConsumidorFrame_buttonClose_actionAdapter(ProducerConsumerFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.buttonClose_actionPerformed(e);
	}
}

class ProdutorConsumidorFrame_menuItemVisualizadorGrafico_actionAdapter implements java.awt.event.ActionListener {
	ProducerConsumerFrame adaptee;

	ProdutorConsumidorFrame_menuItemVisualizadorGrafico_actionAdapter(ProducerConsumerFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.menuItemVisualizadorGrafico_actionPerformed(e);
	}
}

class ProdutorConsumidorFrame_menuItemAlgoritmoVer1_actionAdapter implements java.awt.event.ActionListener {
	ProducerConsumerFrame adaptee;

	ProdutorConsumidorFrame_menuItemAlgoritmoVer1_actionAdapter(ProducerConsumerFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.menuItemAlgoritmoVer1_actionPerformed(e);
	}
}

class ProdutorConsumidorFrame_menuItemAlgoritmoVer2_actionAdapter implements java.awt.event.ActionListener {
	ProducerConsumerFrame adaptee;

	ProdutorConsumidorFrame_menuItemAlgoritmoVer2_actionAdapter(ProducerConsumerFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.menuItemAlgoritmoVer2_actionPerformed(e);
	}
}

class ProdutorConsumidorFrame_menuItemAlgoritmoVer3_actionAdapter implements java.awt.event.ActionListener {
	ProducerConsumerFrame adaptee;

	ProdutorConsumidorFrame_menuItemAlgoritmoVer3_actionAdapter(ProducerConsumerFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.menuItemAlgoritmoVer3_actionPerformed(e);
	}
}

class ProdutorConsumidorFrame_menuItemAlgoritmoVer4_actionAdapter implements java.awt.event.ActionListener {
	ProducerConsumerFrame adaptee;

	ProdutorConsumidorFrame_menuItemAlgoritmoVer4_actionAdapter(ProducerConsumerFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.menuItemAlgoritmoVer4_actionPerformed(e);
	}
}

class ProdutorConsumidorFrame_menuItemAlgoritmoVer5_actionAdapter implements java.awt.event.ActionListener {
	ProducerConsumerFrame adaptee;

	ProdutorConsumidorFrame_menuItemAlgoritmoVer5_actionAdapter(ProducerConsumerFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.menuItemAlgoritmoVer5_actionPerformed(e);
	}
}
