package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import engine.GridParameters;
import grid.BoxFactory;
import grid.Food;
import grid.Grid;
import grid.ObstacleType;
import stat.Statistics;
import used.Point;

public class InfosPanel extends JPanel{
	
	private Grid grid;
	private Scene scene;
	private Food food;
		
	private static final long serialVersionUID = 1L;
	private grid.Box BoxGrid;
	private Point BoxPoint;
		
	private JPanel pan = new JPanel();
		
	private Box boxBox = Box.createVerticalBox();
	private Box boxBox2 = Box.createHorizontalBox();
	private Box boxInfosBox = Box.createVerticalBox();
	private Box foodBox = Box.createHorizontalBox();
	private Box foodInfosBox = Box.createVerticalBox();
	
		
	private JPanel boxNameLabelPanel = new JPanel();
	private JPanel boxObstaclePanel = new JPanel();
	private JPanel boxFoodPanel = new JPanel();
	private JPanel boxUpdateButtonPanel = new JPanel();
	private JPanel foodNameLabelPanel = new JPanel();
	private JPanel quantityLabelPanel = new JPanel();
	private JPanel timeLabelPanel = new JPanel();
	private JPanel lifeLabelPanel = new JPanel();
	private JPanel numberOfMouseEatenLabelPanel = new JPanel();
	
	private JLabel obstacleLabel = new JLabel("Obstacle :");
	private JLabel foodLabel = new JLabel("Food : (0,0)");
	private JLabel foodNameLabel = new JLabel("Food : cheese");
	private JLabel quantityLabel = new JLabel("quantity : 10");
	private JLabel timeLabel = new JLabel("Time : 600");
	private JLabel numberOfMouseEatenLabel = new JLabel("Number Of Mice That Ate Here : 0");
	
	private JTextField foodField = new JTextField("");
	private JCheckBox obstacleCheckBox = new JCheckBox("");
		
	private JComboBox<String>obstacleTypeComboBox = new JComboBox<String>(ObstacleType.getTypeNames());
	private JButton foodButton = new JButton("Update");
	private JLabel boxNameLabel = new JLabel("box (0,0)");
		
	private GridBagLayout layout = new GridBagLayout();
	private GridBagConstraints cons = new GridBagConstraints();
		
	public InfosPanel(Grid grid, Scene scene) {
		super();
		this.grid=grid;
		this.scene=scene;
		initActions();
		init();
		this.setLayout(layout);
		this.add(pan);
	}
		
	public void init() {
			
		boxBox.add(boxNameLabelPanel);
		boxBox.add(boxBox2);
		boxBox2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		boxBox2.add(boxInfosBox);
		boxBox2.add(new JLabel("           "));
		boxInfosBox.add(boxObstaclePanel);
		boxInfosBox.add(boxFoodPanel);
		boxInfosBox.add(boxUpdateButtonPanel);
		
		foodBox.setPreferredSize(new Dimension(600,200));
		foodBox.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
		foodBox.add(foodInfosBox);
		foodInfosBox.add(foodNameLabelPanel);
		foodInfosBox.add(quantityLabelPanel);
		foodInfosBox.add(timeLabelPanel);
		foodInfosBox.add(lifeLabelPanel);
		foodInfosBox.add(numberOfMouseEatenLabelPanel);
		
		foodNameLabelPanel.add(foodNameLabel);
		quantityLabelPanel.add(quantityLabel);
		timeLabelPanel.add(timeLabel);
		numberOfMouseEatenLabelPanel.add(numberOfMouseEatenLabel);

		boxNameLabelPanel.add(boxNameLabel);

		boxObstaclePanel.add(obstacleLabel);
		boxObstaclePanel.add(obstacleCheckBox);
		boxObstaclePanel.add(obstacleTypeComboBox);
		obstacleTypeComboBox.setEnabled(false);
		boxFoodPanel.add(foodLabel);
		boxFoodPanel.add(foodField);
		foodField.setPreferredSize(new Dimension(50, 25));
		boxUpdateButtonPanel.add(foodButton);
	}
		
	public void initActions() {
			
		foodButton.addActionListener(new ChangeFoodAction());
		foodField.setPreferredSize(new Dimension(30, 27));
		cons.fill = GridBagConstraints.BOTH;
		cons.weightx = 1;
		cons.weighty = 1;
		
		obstacleCheckBox.addItemListener(new ItemListener() {
		@Override
		public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				foodField.setEnabled(false);
				foodButton.setEnabled(false);
				foodLabel.setEnabled(false);
				obstacleTypeComboBox.setEnabled(true);
				if(!BoxGrid.getGroundType().isWall())
					if(BoxGrid.getGroundType().isFood()) {
						Food f = (Food) BoxGrid.getGroundType();
						grid.deleteFood(f);
						BoxGrid.setGroundType(BoxFactory.creatObstacle(BoxGrid.getGroundType().getAbscisse(), BoxGrid.getGroundType().getOrdonne(), obstacleTypeComboBox.getSelectedIndex()));
						BoxGrid.setIsFree(false);
						scene.repaint();
						updateInfos();
					}
					else {
						BoxGrid.setGroundType(BoxFactory.creatObstacle(BoxGrid.getGroundType().getAbscisse(), BoxGrid.getGroundType().getOrdonne(), obstacleTypeComboBox.getSelectedIndex()));
						BoxGrid.setIsFree(false);
						scene.repaint();
						updateInfos();
					}
			} else {
					foodField.setEnabled(true);
					foodButton.setEnabled(true);
					foodLabel.setEnabled(true);
					obstacleTypeComboBox.setEnabled(false);
					if (!BoxGrid.getIsFree() && !BoxGrid.getGroundType().isWall() && !BoxGrid.getGroundType().isFood()) {
						BoxGrid.setGroundType(BoxFactory.creatGrass(BoxGrid.getGroundType().getAbscisse(),BoxGrid.getGroundType().getOrdonne(), GridParameters.getInstance().getGround()));
						BoxGrid.setIsFree(true);
						scene.repaint();
						updateInfos();
					}
			}
		}
	});
			
		obstacleTypeComboBox.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(!BoxGrid.getIsFree()){
					BoxGrid.setGroundType(BoxFactory.creatObstacle(BoxGrid.getGroundType().getAbscisse(), BoxGrid.getGroundType().getOrdonne(), obstacleTypeComboBox.getSelectedIndex()));
					scene.repaint();
					updateInfos();
				}
			}	
		});
	}
		
	public void updateInfos(Point p, grid.Box b) {
		this.BoxPoint = p;
		this.BoxGrid = b;
		this.removeAll();
		this.add(boxBox);
		updateInfos();
	}
		
	public void updateInfos() {
		Border blackline = BorderFactory.createLineBorder(Color.black);
		this.setBorder(blackline);
		if (BoxGrid.getGroundType().isFood()) {
			boxNameLabel.setText("Food Source "+ BoxPoint.toString()+" Quantity : "+BoxGrid.getGroundType().getQuantity());
		} else if (!BoxGrid.getIsFree()) {
			boxNameLabel.setText("Obstacle "+ BoxPoint.toString());
		} else {
			boxNameLabel.setText("Empty box"+ BoxPoint.toString());
		}

		if (BoxGrid.getGroundType().isFood() || BoxGrid.getGroundType().isGrass()){
			obstacleCheckBox.setSelected(false);
			foodField.setText("" + BoxGrid.getGroundType().getQuantity());
		}
		else if (!BoxGrid.getIsFree()) {
			obstacleCheckBox.setSelected(true);
			obstacleTypeComboBox.setSelectedItem("Obstacle");
		}
		this.revalidate();
	}
	
	public void updateFoodInfos(Food f) {
		
		food = f;
		
		this.removeAll();
		this.add(foodBox);
		
		if(f.getType().toString() == "food1") {
			foodNameLabel.setText("Food : chees");
		}
		else if(f.getType().toString() == "food3") {
			foodNameLabel.setText("Food : carrot");
		}
		else {
			foodNameLabel.setText("Food : bread");
		}
		
		foodLabel.setText("Food : "+"("+f.getAbscisse()+","+f.getOrdonne()+")");
		quantityLabel.setText("quantity : "+f.getQuantity());
		timeLabel.setText(("Time : "+f.getTime()));
		numberOfMouseEatenLabel.setText("Number Of Mice That Ate Here :"+f.getNumberOfMouseEaten());
	}
	
	public void updateFoodInfos() {
		if(food != null)
			updateFoodInfos(food);
	}
	
	public void setFood(Food food) {
		this.food = food;
	}
		
	private class ChangeFoodAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(!BoxGrid.getGroundType().isFood()) {
				Statistics.getInstance().setNbrFoodSources(Statistics.getInstance().getNbrFoodSources()+1);
				BoxGrid.setGroundType(BoxFactory.creatFood(BoxGrid.getGroundType().getAbscisse(), BoxGrid.getGroundType().getOrdonne(),"Start"));
				BoxGrid.setIsFree(true);
				Food f = (Food) BoxGrid.getGroundType();
				grid.addFood(f);
				BoxGrid.getGroundType().setQuantity(Integer.parseInt(foodField.getText()));
			}
			scene.repaint();
		}
	}
}