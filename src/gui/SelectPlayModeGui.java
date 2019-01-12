package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import engine.GridParameters;
import gui.MainFrame;

public class SelectPlayModeGui extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	/**********		attributs		***********/
	
	private JFrame frameMode;
	private static JTextField textNumberMouses;
	@SuppressWarnings("rawtypes")
	private static JComboBox comboBoxGround,
						comboBoxFood,
						comboBoxObstacles;
	
	/**********		construct		**********/
	
	public SelectPlayModeGui() {
		initialize();
	}

	/**********		methodes		***********/
	
	public static void setGround(Object ground) {
		comboBoxGround.setSelectedItem(ground);
	}
	
	public static String getGround() {
		return comboBoxGround.getSelectedItem().toString();
	}
	
	public static Object getSelectedGround(){
		return comboBoxGround.getSelectedItem();
	}
	
	public static int getFreqFood() {
		int freqFood = valueOfFreq(comboBoxFood.getSelectedItem().toString());
		return freqFood;
	}
	public static int getFreqObstacles() {
		int freqObstacles = valueOfFreq(comboBoxObstacles.getSelectedItem().toString());
		return freqObstacles;
	}
	public static int getDimension() {
		return 21;
	}
	public static int getNumberOfMouses() {
		return Integer.parseInt(textNumberMouses.getText());
	}
				//others
	public static int valueOfFreq(String freq) {
		switch (freq) {
			case "high" :
				return 25;
			case "medium" :
				return 15;
			case "low" :
				return 3;
		}
		return 0;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initialize() {
		frameMode = new JFrame();
		frameMode.setTitle("select the game mode");
		frameMode.setResizable(false);
		frameMode.setBounds(100, 100,772, 325);
		frameMode.setVisible(true);
		frameMode.setBackground(Color.GRAY);
		frameMode.getContentPane().setBackground(Color.LIGHT_GRAY);
		frameMode.getContentPane().setLayout(null);
		frameMode.setLocationRelativeTo(frameMode.getParent());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JLabel lblSelectYourGame = new JLabel("Select your game mode");
		lblSelectYourGame.setForeground(Color.WHITE);
		lblSelectYourGame.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
		lblSelectYourGame.setBounds(276, 11, 400, 22);
		frameMode.getContentPane().add(lblSelectYourGame);
		
		JLabel lblGround = new JLabel("Ground :");
		lblGround.setForeground(Color.WHITE);
		lblGround.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		lblGround.setBounds(260, 58, 85, 14);
		frameMode.getContentPane().add(lblGround);
		
		comboBoxGround = new JComboBox();
		comboBoxGround.setForeground(Color.WHITE);
		comboBoxGround.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		comboBoxGround.setBackground(Color.DARK_GRAY);
		comboBoxGround.setBounds(355, 57, 100, 20);
		comboBoxGround.addItem("Grass");
		comboBoxGround.addItem("Desert");
		comboBoxGround.addItem("Snow");
		frameMode.getContentPane().add(comboBoxGround);
		
		JLabel lblFrequencefood = new JLabel("Frequence-\r\nFood :");
		lblFrequencefood.setForeground(Color.WHITE);
		lblFrequencefood.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		lblFrequencefood.setBounds(50, 124, 210, 22);
		frameMode.getContentPane().add(lblFrequencefood);
		
		comboBoxFood = new JComboBox();
		comboBoxFood.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		comboBoxFood.addItem("high");
		comboBoxFood.addItem("medium");
		comboBoxFood.addItem("low");
		comboBoxFood.setForeground(Color.WHITE);
		comboBoxFood.setBackground(Color.DARK_GRAY);
		comboBoxFood.setBounds(200, 127, 100, 20);
		frameMode.getContentPane().add(comboBoxFood);
		
		JLabel lblDensityOfObstacles = new JLabel("Density Of Obstacles :");
		lblDensityOfObstacles.setForeground(Color.WHITE);
		lblDensityOfObstacles.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		lblDensityOfObstacles.setBounds(428, 124, 225, 22);
		frameMode.getContentPane().add(lblDensityOfObstacles);
		
		comboBoxObstacles = new JComboBox();
		comboBoxObstacles.addItem("high");
		comboBoxObstacles.addItem("medium");
		comboBoxObstacles.addItem("low");
		comboBoxObstacles.setForeground(Color.WHITE);
		comboBoxObstacles.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		comboBoxObstacles.setBackground(Color.DARK_GRAY);
		comboBoxObstacles.setBounds(630, 127, 101, 20);
		frameMode.getContentPane().add(comboBoxObstacles);
		
		JLabel lblNumberOfMouses = new JLabel("Number Of Mouses :");
		lblNumberOfMouses.setForeground(Color.WHITE);
		lblNumberOfMouses.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		lblNumberOfMouses.setBounds(332, 175, 210, 22);
		frameMode.getContentPane().add(lblNumberOfMouses);
		
		textNumberMouses = new JTextField("10");
		textNumberMouses.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		textNumberMouses.setBounds(396, 195, 27, 20);
		frameMode.getContentPane().add(textNumberMouses);
		textNumberMouses.setColumns(10);
		
		JButton buttonRun = new JButton("");
		buttonRun.setIcon(new ImageIcon(SelectPlayModeGui.class.getResource("/images/Run.png")));
		buttonRun.addActionListener(this);
		buttonRun.setActionCommand("run");
		buttonRun.setBounds(388, 240, 45,45);
		frameMode.getContentPane().add(buttonRun);
		
		JButton buttonBack = new JButton("< back");
		buttonBack.addActionListener(this);
		buttonBack.setBounds(5,265,80,20);
		frameMode.getContentPane().add(buttonBack);
		
		
		JLabel labelSouris1 = new JLabel("");
		labelSouris1.setIcon(new ImageIcon(SelectPlayModeGui.class.getResource("/images/souris1.png")));
		labelSouris1.setBounds(573, 157, 72, 128);
		frameMode.getContentPane().add(labelSouris1);
		
		JLabel labelImage = new JLabel("");
		labelImage.setVerticalAlignment(SwingConstants.BOTTOM);
		labelImage.setIcon(new ImageIcon(SelectPlayModeGui.class.getResource("/images/city.jpg")));
		labelImage.setBounds(0, 0, 766, 296);
		frameMode.getContentPane().add(labelImage);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		try {
			if(Integer.parseInt(textNumberMouses.getText())<0 || Integer.parseInt(textNumberMouses.getText())>40) {
				JOptionPane.showMessageDialog(this, "You must enter a number between 0 and 40 !", "Error", JOptionPane.ERROR_MESSAGE);
				new StartGui();
			}
			else
				if(event.getActionCommand().equals("run")) {
					GridParameters.getInstance().setAll(getFreqObstacles(), getFreqFood(), getNumberOfMouses(), getDimension(),getGround());
					new MainFrame("Mouse Game");
				}else if(event.getActionCommand().equals("< back")) {
					new StartGui();
				}
				frameMode.dispose();
		} catch(NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "You must enter a number !", "Error", JOptionPane.ERROR_MESSAGE);
		}		
	}
}