package gui;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import engine.GridParameters;
import engine.Simulation;
import grid.Box;
import grid.BoxFactory;
import grid.Food;
import log.LogPane;
import log.LogWriter;
import log.MouseEvent;
import log.StoryFrame;
import mouses.Cooperative;
import mouses.Memory;
import mouses.MiceFactory;
import mouses.Mouse;
import mouses.Selfish;
import stat.SatatPaneMice;
import stat.StatPane;
import stat.StatPaneCommunication;
import stat.Statistics;
import used.Direction;
import used.GenerateGameMode;
import used.Point;
import used.Random;

@SuppressWarnings("serial")
public class MainFrame extends JFrame implements Runnable,MouseListener,KeyListener{

	/*********		attributs		*********/
	private static final int THREAD_MAP = GenerateGameMode.threadMap(GridParameters.getInstance().getGround());
	private Simulation simulation;
	private StatPane statPn = new StatPane();
	private StatPaneCommunication miceStat = new StatPaneCommunication();
	private SatatPaneMice comunicationStat = new SatatPaneMice();
	private LogPane logPn = new LogPane();
	private Scene scene = new Scene();
	private StoryFrame storyFrame;
	private InfosPanel infosPanel;
	private MiceInfosPane infosMice;
	private JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	private JTabbedPane tabbedPaneStat = new JTabbedPane(JTabbedPane.TOP);
	private JButton playButton;
	private boolean stop = true;
	private JList<String> storyList;
	/*********		construct		*********/
	public MainFrame(String title) {
		super(title);
		setFocusable(true);
		addKeyListener(this);
		GridParameters parameters = GridParameters.getInstance();
		simulation = new Simulation(parameters);
		simulation.generatGrid();
		scene.setGrid(simulation.getGrid());
		infosPanel = new InfosPanel(simulation.getGrid(), scene);
		infosMice = new MiceInfosPane(scene);
		scene.addMouseListener(this);
		init();
		launchGUI();
	}
	/*********		methodes		*********/
	
				//others
	
	private void launchGUI() {
		stop = false;		
		Thread chronoThread = new Thread(this);
		chronoThread.start();
	}
	
	public void init() {
		
		new JFrame("Mouse Game");
		setResizable(false);
		getContentPane().setBackground(Color.darkGray);
		setSize(1000,635);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		playButton = new JButton(" Pause ");
		playButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (stop) {
					playButton.setText(" Pause ");
					launchGUI();
				} else {
					stop = true; 
					playButton.setText(" Play ");
					playButton.setFocusable(false);
				}
			}			
		});
		
		JButton homeButton = new JButton("");
		homeButton.setFocusable(false);
		homeButton.setIcon(new ImageIcon(MainFrame.class.getResource("/images/home.png")));
		homeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				stop = true;
				Statistics.getInstance().init();
				LogWriter.getInstance().clear();
				MainFrame.this.dispose();
				new StartGui();
			}			
		});

		
		JButton nextTurnButton = new JButton("Next");
		nextTurnButton.setFocusable(false);
		nextTurnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				simulation.simulationNextTurn();
				updateGUI();
			}
		});
						
		JPanel operationZone = new JPanel();
		operationZone.setLayout(null);
		operationZone.setBounds(600,315,390,280);
		operationZone.add(playButton);
		operationZone.add(homeButton);
		operationZone.add(nextTurnButton);
		operationZone.add(tabbedPane);
		
		JPanel statPanel = new JPanel();
		statPanel.setBounds(600,5,390,305);
		statPanel.add(tabbedPaneStat);
		add(statPanel);
		
		JLabel backgroundStoryLabel = new JLabel();
		backgroundStoryLabel.setBounds(0,0,370,245);
		String imagePath1 = "/images/log/bgstorypane"+SelectPlayModeGui.getGround()+".png";
		ImageIcon backgroundStory = new ImageIcon(getClass().getResource(imagePath1));
		backgroundStoryLabel.setIcon(backgroundStory);
		
		JPanel historyPane = new JPanel();
		historyPane.setLayout(null);
		historyPane.add(logPn);
		
		
		JPanel generPane = new JPanel();
		generPane.setLayout(null);
					
		JLabel lblFrequencefood = new JLabel("Food sources :");
		lblFrequencefood.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		lblFrequencefood.setBounds(20,140,130,20);
		generPane.add(lblFrequencefood);
		
		JTextField tfFood = new JTextField();
		tfFood.setText(""+SelectPlayModeGui.getFreqFood());
		tfFood.setBounds(180,140,50,25);
		generPane.add(tfFood);
		
		JLabel lblDensityOfObstacles = new JLabel("Obstacles number:");
		lblDensityOfObstacles.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		lblDensityOfObstacles.setBounds(20,180,140,20);
		generPane.add(lblDensityOfObstacles);
		
		JTextField tfObstacles = new JTextField();
		tfObstacles.setText(""+SelectPlayModeGui.getFreqObstacles());
		tfObstacles.setBounds(180,180,50,25);
		generPane.add(tfObstacles);
	
		JLabel lblNumberOfMouses = new JLabel("Mice number :");
		lblNumberOfMouses.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		lblNumberOfMouses.setBounds(20,20,120,20); 
		generPane.add(lblNumberOfMouses);
		
		JTextField tfMouse = new JTextField();
		tfMouse.setText(""+SelectPlayModeGui.getNumberOfMouses());
		tfMouse.setBounds(180,20,50,25); 
		generPane.add(tfMouse);
		
		
		ButtonGroup behaviorbuttons = new ButtonGroup();
		ButtonGroup typeMousebuttons = new ButtonGroup();
		
		JRadioButton cooperativeRadButn = new JRadioButton("Cooperative",true);
		cooperativeRadButn.setActionCommand("Cooperative");
		cooperativeRadButn.setBounds(75,60,100,25);
		behaviorbuttons.add(cooperativeRadButn);
		generPane.add(cooperativeRadButn);		
		
		JRadioButton selfishRadButn = new JRadioButton("Selfish");
		selfishRadButn.setActionCommand("Selfish");
		selfishRadButn.setBounds(175,60,90,25);
		behaviorbuttons.add(selfishRadButn);
		generPane.add(selfishRadButn);		

		JRadioButton nehilistRadButn = new JRadioButton("Nihilist");
		nehilistRadButn.setActionCommand("Nihilist");
		nehilistRadButn.setBounds(175,100,90,25);
		typeMousebuttons.add(nehilistRadButn);
		generPane.add(nehilistRadButn);
		
		JRadioButton receptiveRadButn = new JRadioButton("Receptive",true);
		receptiveRadButn.setActionCommand("Receptive");
		receptiveRadButn.setBounds(75,100,90,25);
		typeMousebuttons.add(receptiveRadButn);
		generPane.add(receptiveRadButn);
		
		JButton addMouseButn = new JButton("add");
		addMouseButn.setFocusable(false);
		addMouseButn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Mouse mouse;
				stop=true;
				
				try {
					int miceNumber = Integer.parseInt(tfMouse.getText());
						for(int i = 0; i< miceNumber; i++) {
							Point choosePoin=null;
							int x,y;
							do {
								x= Random.randomInt(1,SelectPlayModeGui.getDimension()-2);
								y= Random.randomInt(1,SelectPlayModeGui.getDimension()-2);
							}while(!simulation.getGrid().getBoxAt(x, y).getIsFree());
							choosePoin = new Point(x, y);	
					
							if(receptiveRadButn.isSelected()) {
								Statistics.getInstance().incrementReciptive();
								if(selfishRadButn.isSelected()) {
									Statistics.getInstance().incrementSelfish();
									mouse  = MiceFactory.creatReciptiveMouse(choosePoin,new Memory(),new Selfish(0),0,GenerateGameMode.genderOfMice(Random.randomInt(1, 2)),5);
								}else {
									Statistics.getInstance().incrementCooperatice();
									mouse = MiceFactory.creatReciptiveMouse(choosePoin,new Memory(),new Cooperative(0),0,GenerateGameMode.genderOfMice(Random.randomInt(1, 2)),10);
								}
							}else {
								Statistics.getInstance().incrementNihilist();
								if(selfishRadButn.isSelected()) {
									Statistics.getInstance().incrementSelfish();
									mouse  = MiceFactory.creatReciptiveMouse(choosePoin,new Memory(),new Selfish(0),0,GenerateGameMode.genderOfMice(Random.randomInt(1, 2)),5);
								}else {
									Statistics.getInstance().incrementCooperatice();
									mouse = MiceFactory.creatReciptiveMouse(choosePoin,new Memory(),new Cooperative(0),0,GenerateGameMode.genderOfMice(Random.randomInt(1, 2)),10);
					
								}
							}
							simulation.getGrid().addMouse(mouse);;
						}
				}catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null,"You must enter a number !", "Error", JOptionPane.ERROR_MESSAGE);
				}
				updateGUI();
				stop=false;
			}			
		});
		addMouseButn.setBounds(231,20,55,24);
		generPane.add(addMouseButn);
		
		JButton addFoodButn = new JButton("add");
		addFoodButn.setFocusable(false);
		addFoodButn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				stop=true;
				try {
					int foodNumber = Integer.parseInt(tfFood.getText());
						int x,y;
						for(int i = 0 ; i<foodNumber ; i++ ) {
					
							do
							{
								x= Random.randomInt(1,simulation.getParameters().getDimension()-2);
								y= Random.randomInt(1,simulation.getParameters().getDimension()-2);
							}
							while(!simulation.getGrid().getBoxAt(x, y).getIsFree() && !simulation.getGrid().isFoodPosition(new Point(x,y)));
								Food f = BoxFactory.creatFood(x, y,"Start");
								simulation.getGrid().getBoxAt(x, y).setGroundType(f);
								simulation.getGrid().addFood(f);
						}
				}catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null,"You must enter a number !", "Error", JOptionPane.ERROR_MESSAGE);
				}
				updateGUI();
				stop=false;
			}
		});
		addFoodButn.setBounds(231,140,55,24);
		generPane.add(addFoodButn);
		
		JButton addObstacleButn = new JButton("add");
		addObstacleButn.setFocusable(false);
		addObstacleButn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				stop=true;
				try {
					int obstacleNumber = Integer.parseInt(tfObstacles.getText());
					if(obstacleNumber+simulation.getGrid().getObstacle().size() > 30) {
						int number = 30-simulation.getGrid().getObstacle().size();
						JOptionPane.showMessageDialog(null,"you have exceeded the number of obstacles allowed. You can add : "+number);
					}else {
						simulation.addObstaclesRandomly(obstacleNumber);
					}
				}catch (NumberFormatException ex) {
						JOptionPane.showMessageDialog(null,"You must enter a number !", "Error", JOptionPane.ERROR_MESSAGE);
				}
				updateGUI();
				stop=false;
			}			
		});
		addObstacleButn.setBounds(231,180,55,24);
		generPane.add(addObstacleButn);
		
		JPanel storyPane = new JPanel();
		storyPane.setLayout(null);
		storyList = new JList<String>();
		JScrollPane listStoryScroll = new JScrollPane(storyList);
		listStoryScroll.setBounds(10,10,100,200);
		storyPane.add(listStoryScroll);
		storyPane.add(backgroundStoryLabel);
		infosMice.setBounds(120,0,250,250);

		tabbedPane.addTab("History", null,historyPane,null);
		tabbedPane.addTab("Generate", null,generPane,null);
		tabbedPane.addTab("Stories", null,storyPane,null);
		tabbedPane.addTab("Mouse Infos", null,infosMice,null);
		tabbedPane.addTab("Box Infos", null,infosPanel,null);
		
		tabbedPaneStat.addTab("Global Stat", null, statPn,null);
		tabbedPaneStat.addTab("Communication Stat", null, miceStat,null);
		tabbedPaneStat.addTab("Mice Stat", null, comunicationStat,null);
		
		storyList.addMouseListener(this);
		scene.setBounds(5,5,590,590);
		logPn.setBounds(1,0,370,213);	
		playButton.setBounds(25,251,100,24);
		homeButton.setBounds(265,251,100,24);
		nextTurnButton.setBounds(145,251,100,24);
		tabbedPane.setBounds(7,0,375,245);
		
		
		getContentPane().add(scene);
		getContentPane().add(operationZone);
				
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void updateListStory() {
		ArrayList<Mouse> deadMice = simulation.getMousesToKill();
		DefaultListModel<String> jModel = new DefaultListModel<String>();
   			for(int i=0;i<deadMice.size();i++) {
    			jModel.addElement(deadMice.get(i).getName());
    		}
   		storyList.setModel(jModel);
	}
	
	public StoryFrame getStoryFrame() {
		return storyFrame;
	}
	
	@Override
	public void mouseClicked(java.awt.event.MouseEvent e) {
	}
	@Override
	public void mouseEntered(java.awt.event.MouseEvent e) {
		
	}
	@Override
	public void mouseExited(java.awt.event.MouseEvent e) {
		
	}
	@Override
	public void mouseReleased(java.awt.event.MouseEvent e) {
		
	}

	@Override
	public void mousePressed(java.awt.event.MouseEvent e) {
		if(e.getSource().equals(storyList)) {
			ArrayList<Mouse> deadMice = simulation.getMousesToKill();
			if(!deadMice.isEmpty()) {
				stop=true;
				playButton.setText(" Play ");
				int index = storyList.getSelectedIndex();
				if(index<=deadMice.size()) {
					Mouse m = deadMice.get(index);
					ArrayList<MouseEvent> events = m.getMemory().getCareer();
					storyFrame = new StoryFrame(m.getName()+" story",events);
				}
			}
		}else {
			Point p = new Point(e.getX()/28, e.getY()/28);
			
			if(e.getClickCount() == 1) {
				infosPanel.setFood(null);
				scene.setSelectedBox(p);
				infosPanel.updateInfos(p, simulation.getGrid().getBoxAt(p.getAbscisse(), p.getOrdonne()));
				tabbedPane.setSelectedIndex(4);
				repaint();
			}
			
			if(e.getClickCount() == 2) {
				infosPanel.setFood(null);
				Box b = simulation.getGrid().getBoxAt(p.getAbscisse(), p.getOrdonne());
				if(b.getGroundType().isFood()) {
					scene.setSelectedBox(p);
					infosPanel.updateFoodInfos(simulation.getGrid().getFoodAt(p.getAbscisse(), p.getOrdonne()));
					tabbedPane.setSelectedIndex(4);
				}
				else if(simulation.getGrid().isMousePosition(p)){
					scene.setSelectedMice(p);
					infosMice.updateInfos(simulation.getGrid().getMouseAt(p));
					tabbedPane.setSelectedIndex(3);
				}
				repaint();
			}	
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		Mouse mouse = infosMice.getMouse();
		int keyValue = e.getKeyCode();
		
		Point nextPos = null,
				mPos = null;
		if(mouse != null)
			if(mouse.getTakeControle()) {
				mPos = mouse.getPosition();
				switch(keyValue) {
					case KeyEvent.VK_UP :
						nextPos = new Point(mPos.getAbscisse(), mPos.getOrdonne() - 1);
						mouse.setLastDirection(Direction.Up);
						break;
					case KeyEvent.VK_DOWN :
						nextPos = new Point(mPos.getAbscisse(), mPos.getOrdonne() + 1);
						mouse.setLastDirection(Direction.Down);
						break;
					case KeyEvent.VK_RIGHT :
						nextPos = new Point(mPos.getAbscisse() + 1, mPos.getOrdonne());
						mouse.setLastDirection(Direction.Right);
						break;
					case KeyEvent.VK_LEFT :
						nextPos = new Point(mPos.getAbscisse() -1, mPos.getOrdonne());
						mouse.setLastDirection(Direction.Left);
						break;
					default :
						nextPos = mPos;
						break;
				}
			}
	
		if((keyValue == KeyEvent.VK_UP || keyValue == KeyEvent.VK_DOWN || keyValue == KeyEvent.VK_RIGHT || keyValue == KeyEvent.VK_LEFT) && mouse != null)	
			if(simulation.canWeMoveit(nextPos,mouse)) {
				mouse.setPosition(nextPos);
			}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	public void updateGUI() {
		statPn.updateChart();
		miceStat.updateChart(Simulation.getSimulationTurn());
		comunicationStat.updateChart();
		logPn.updateText();
		updateListStory();
		infosMice.updateInfos();
		infosPanel.updateFoodInfos();
		scene.updateUI();
		scene.repaint();
	}
	
	public void run() {
		while(!stop) {	
			simulation.simulationNextTurn();
			updateGUI();
			try {
				Thread.sleep(THREAD_MAP);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}