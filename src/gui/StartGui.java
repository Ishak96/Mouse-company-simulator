package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class StartGui extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		new StartGui();
	}
	/**
	 * Create the application.
	 */
	public StartGui() {
		super();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Mouses Game");
		frame.setResizable(false);
		frame.getContentPane().setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		frame.setBounds(100, 100, 565, 303);
		frame.getContentPane().setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(frame.getParent());
		
		JButton buttonSelectPlayMode = new JButton("Start Game");
		buttonSelectPlayMode.addActionListener(this);
		
		buttonSelectPlayMode.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		buttonSelectPlayMode.setBounds(185,80, 190, 23);
		frame.getContentPane().add(buttonSelectPlayMode);
		
		JButton btnInformations = new JButton("Informations");
		btnInformations.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		btnInformations.setBounds(185, 117, 190, 23);
		frame.getContentPane().add(btnInformations);
		btnInformations.addActionListener(this);
		
		JButton btnAboutUs = new JButton("About us");
		btnAboutUs.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		btnAboutUs.setBounds(185, 155, 190, 23);
		frame.getContentPane().add(btnAboutUs);
		btnAboutUs.addActionListener(this);
		
		JLabel labelImage = new JLabel("");
		labelImage.setIcon(new ImageIcon(StartGui.class.getResource("/images/Coopmouses.png")));
		labelImage.setBounds(0, 0, 559, 274);
		frame.getContentPane().add(labelImage);
		frame.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		
		
		if(event.getActionCommand().equals("Start Game")) {
			new SelectPlayModeGui();
			frame.dispose();
		}else if(event.getActionCommand().equals("Informations")) {
			new JOptionPane();
			JOptionPane.showMessageDialog(null,
										"Simulation game\n"
										+"Developed in JAVA\n"
										+"with Eclipse IDE\n"
										+"Created in April 2018"
										,"Informations"
										,JOptionPane.INFORMATION_MESSAGE);
		}else if(event.getActionCommand().equals("About us")) {
			new JOptionPane();
			JOptionPane.showMessageDialog(null,
					"Authors:\n"
					+"HACHOUD Rassem\n"
					+"AYAD Ishak \n"
					+"YAHIAOUI Anis\n"
					+"Students at Cergy-Pontoise university"
					,"About us"
					,JOptionPane.INFORMATION_MESSAGE);
		}
		
	}
}