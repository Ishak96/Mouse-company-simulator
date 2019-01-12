package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import mouses.Mouse;


public class MiceInfosPane extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private Scene scene;
	
	private Mouse mouse;
	
	private JPanel pan = new JPanel();
	
	private Box squareMouseInfosBox = Box.createVerticalBox();
	
	private Box mouseBox = Box.createVerticalBox();
	private Box mouseBox2 = Box.createHorizontalBox();
	private Box mouseInfosBox = Box.createVerticalBox();
	private Box mouseParametersBox = Box.createVerticalBox();
	
	private JPanel squareMiceListLabelPanel = new JPanel();
	private JPanel squareMiceListPanel = new JPanel();

	private JPanel mouseNameLabelPanel = new JPanel();
	private JPanel ageLabelPanel = new JPanel();
	private JPanel sexeLabelPanel = new JPanel();
	private JPanel lifeLabelPanel = new JPanel();
	private JPanel TypeLabelPanel = new JPanel();
	private JPanel reliabilityLabelPanel = new JPanel();
	private JPanel visionLabelPanel = new JPanel();
	private JPanel trustLabelPane = new JPanel();
	private JPanel takeControlePanel = new JPanel();
	private JPanel visionPanel = new JPanel();
	private JPanel actMicePanel = new JPanel();
	
	
	private JLabel mouseNameLabel = new JLabel("ishak (0,0)");
	private JLabel sexeLabel = new JLabel("Sexe : ♀");
	private JLabel ageLabel = new JLabel("Age : 10");
	private JLabel lifeLabel = new JLabel("Life : 50");
	private JLabel typeLabel = new JLabel("Type : Reciptive");
	private JLabel behavoirLabel = new JLabel("Behavior : Cooperative");
	private JLabel visionLabel = new JLabel("Vision : 3");
	private JLabel trustLabel = new JLabel("trust : 10");
	private JLabel takeControleLabel = new JLabel("Take Contrle : ");
	private JLabel afficherVisionLabel = new JLabel("Vision of mouse : ");
	private JLabel miceActLabel = new JLabel("Act of Mice :");
	
	private MiceFace face = new MiceFace();
	
	private JCheckBox manupCheckBox = new JCheckBox("");
	private JCheckBox afficherVisionCheck = new JCheckBox("");
	private JCheckBox miceActCheck = new JCheckBox("");
	
	private GridBagLayout layout = new GridBagLayout();
	private JTextArea mouseInfos = new JTextArea("");
	
	public MiceInfosPane(Scene scene) {
		super();
		this.scene=scene;
		
		initActions();
		init();
		this.setLayout(layout);
		this.add(pan);
	}
	
	public void initActions() {
		manupCheckBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					mouse.setTakeControle(true);
					scene.repaint();
					afficherVisionCheck.setEnabled(false);
				} else {
					afficherVisionCheck.setEnabled(true);
					mouse.setTakeControle(false);
					scene.repaint();
				}
			}
		});
		afficherVisionCheck.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					scene.setMouseSelected(mouse);
					manupCheckBox.setEnabled(false);
					scene.setModeScene("vision");
					scene.repaint();
				} else {
					manupCheckBox.setEnabled(true);
					scene.setModeScene("global");
					scene.repaint();
				}
			}
		});
		
		miceActCheck.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) { 
					scene.setModeScene("miceAct");
					scene.repaint();
				} else {
					scene.setModeScene("global");
					scene.repaint();
				}
			}
		});
	}
	
	public void init() {
		squareMouseInfosBox.add(squareMiceListLabelPanel);
		squareMouseInfosBox.add(squareMiceListPanel);
		
		mouseBox.add(mouseNameLabelPanel);
		mouseBox.add(mouseBox2);
		mouseBox2.setPreferredSize(new Dimension(600,200));
		mouseBox2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		mouseBox2.add(mouseInfosBox);
		mouseBox2.add(mouseParametersBox);
		mouseInfosBox.add(sexeLabelPanel);
		mouseInfosBox.add(ageLabelPanel);
		mouseInfosBox.add(lifeLabelPanel);
		mouseInfosBox.add(TypeLabelPanel);
		mouseInfosBox.add(reliabilityLabelPanel);
		mouseInfosBox.add(visionLabelPanel);
		mouseInfosBox.add(trustLabelPane);
		mouseParametersBox.add(takeControlePanel);
		mouseParametersBox.add(visionPanel);
		mouseParametersBox.add(actMicePanel);
		mouseParametersBox.add(face);
		face.setPreferredSize(new Dimension(80, 80));

		mouseNameLabelPanel.add(mouseNameLabel);
		
		sexeLabelPanel.add(sexeLabel);
		ageLabelPanel.add(ageLabel);
		lifeLabelPanel.add(lifeLabel);
		TypeLabelPanel.add(typeLabel);
		reliabilityLabelPanel.add(behavoirLabel);
		visionLabelPanel.add(visionLabel);
		trustLabelPane.add(trustLabel);
		visionLabelPanel.add(visionLabel);
		visionPanel.add(afficherVisionLabel);
		visionPanel.add(afficherVisionCheck);
		actMicePanel.add(miceActLabel);
		actMicePanel.add(miceActCheck);
		takeControlePanel.add(takeControleLabel);
		takeControlePanel.add(manupCheckBox);
		
		this.add(mouseBox);
		
	}
	
	public void updateInfos(Mouse m) {
		this.mouse = m;
		this.add(mouseBox);
		
		scene.setSelectedMice(mouse.getPosition());
		
		mouseNameLabel.setText(mouse.getName() +" "+ mouse.getPosition().toString());
		
		if(mouse.getGender().equals("Male"))
			sexeLabel.setText("Sexe : " + mouse.getGender()+" ♂");
		else
			sexeLabel.setText("Sexe : " + mouse.getGender()+" ♀");
		
		ageLabel.setText("Age : " + mouse.getAge());
		lifeLabel.setText("Life : " + mouse.getLife());
		visionLabel.setText("Vision : " + mouse.getVision().getHorizon());
		trustLabel.setText("Trust : "+mouse.getTrust());
		if(m.isReciptive()) {
			typeLabel.setText("Type : Reciptive");
		}
		else {
			typeLabel.setText("Type : Nihilist");
		}
		behavoirLabel.setText("Behavior : "+m.getBehavior().type());
		if (mouse.getLife() <= 0)
			mouseInfos.setText(mouse.getName() + " est morte");
		
		face.setMouse(mouse);
		
		this.revalidate();
	}
	
	public void updateInfos() {
		if(mouse != null)
			updateInfos(mouse);
	}
	
	public Mouse getMouse() {
		return mouse;
	}
	
}
