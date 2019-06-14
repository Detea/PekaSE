import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;

public class PekaSEFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField txtFieldName;
	private JTextField textFieldAmmoSpr1;
	private JTextField textFieldAmmoSpr2;
	
	private JLabel lblStatus;
	
	private File currentFile, lastImgPath, lastSpritePath, lastSoundPath;
	private PK2Sprite spriteFile;

	private JSpinner spinner, spinner_1, spinner_2, spinner_3, spinnerEnergy, spinnerDmg, spinnerScore, spinnerLoadDur, spinnerMaxJump, spinnerAtkPause;
	private JSpinner spinnerAnimations, spinnerFrameRate, spinnerFrames, spinnerAtk1Dur, spinnerAtk2Dur, spinnerParaFactor, spinnerSndFrq;
	private JSpinner spinnerMaxSpeed, spinnerWeight, spinnerSprWidth, spinnerSprHeight;
	
	private JComboBox comboBoxType, comboBoxColor, comboBoxDmg, comboBoxImmunity;
	
	private JCheckBox chckbxEnemy, chckbxBoss, chckbxKey, chckbxObstacle, chckbxRandomFrequency;
	
	private int[] colors = {
			255, 0, 32, 64, 96, 128, 160, 192
	};
	
	private JComboBox comboBoxAi1, comboBoxAi2, comboBoxAi3, comboBoxAi4, comboBoxAi5, comboBoxAi6, comboBoxAi7, comboBoxAi8, comboBoxAi9, comboBoxAi10, comboBoxDestruction, comboBoxDestruct2;
	
	private File rawFile;
	
	private ArrayList<JTextField> txtfd = new ArrayList<JTextField>();
	private ArrayList<JCheckBox> chbx = new ArrayList<JCheckBox>();
	
	private File lastFile;
	
	Calendar cal;
	SimpleDateFormat sdf;
	
	private JTextField textFieldSndKnockOut;
	private JTextField textFieldSndDmg;
	private JTextField textFieldSndAtk1;
	private JTextField textFieldSndAtk2;
	private JTextField textFieldAtkRnd;
	private JTextField textFieldAniStill;
	private JTextField textFieldAniWalk;
	private JTextField textFieldAniJumpUp;
	private JTextField textFieldAniJumpDown;
	private JTextField textFieldAniDuck;
	private JTextField textFieldAniDmg;
	private JTextField textFieldAniKnockOut;
	private JTextField textFieldAniAtk1;
	private JTextField textFieldAniAtk2;
	private JTextField textFieldTransformSprite;
	private JTextField textFieldBonusSprite;
	private JCheckBox chckbxTileCheck;
	private JCheckBox chckbxShakes;
	private JCheckBox chckbxWallUp;
	private JCheckBox chckbxWallDown;
	private JCheckBox chckbxWallLeft;
	private JCheckBox chckbxWallRight;
	private JCheckBox chckbxAlwaysBonus;
	private JCheckBox chckbxSwim;
	private JCheckBox chckbxGlide;
	private JSpinner spinnerBonus;
	private JCheckBox chckbxStillLoop;
	private JCheckBox chckbxWalkingLoop;
	private JCheckBox chckbxDuckLoop;
	private JCheckBox chckbxJumpUpLoop;
	private JCheckBox chckbxJumpDownLoop;
	private JCheckBox chckbxDmgLoop;
	private JCheckBox chckbxKnockOutLoop;
	private JCheckBox chckbxAtk1Loop;
	private JCheckBox chckbxAtk2Loop;
	
	private JTabbedPane tabbedPane;
	
	private BufferedImage test;
	int i = 0;
	boolean animate = false, loop = false;
	int index;
	private JPanel panel_4, panel_3;
	
	private int sprScaleHeight, sprScaleWidth;
	private int sprScaleHeight2, sprScaleWidth2;
	
	private JSpinner spinner_4, spinner_5;
	private JPanel panel_7;
	
	private JTextArea textArea;
	private JCheckBox chckbxCollidingWithPlayer, chckbxShotByPlayer;
	
	public PekaSEFrame() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		spriteFile = new PK2Sprite();
		
		sdf = new SimpleDateFormat("HH:mm:ss");
		
		
		panel_3 = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				
				g.setColor(Color.BLACK);
				
				// @Todo: Fix proportions
				if (spriteFile.image != null) {
					if (spriteFile.frameHeight > 80 || spriteFile.frameWidth > 80) {
						g.drawImage(spriteFile.image, 40 - (sprScaleWidth2 / 2), 40 - (sprScaleHeight2 / 2), sprScaleWidth2, sprScaleHeight2, null);
					} else {
						g.drawImage(spriteFile.image, 40 - (spriteFile.frameWidth / 2), 40 - (spriteFile.frameHeight / 2), null);
					}
				}
				
				g.drawRect(0, 0, 80, 80);
			}
		};
		
		panel_3.setBackground(Color.LIGHT_GRAY); //new Color(0.988f, 0.988f, 0.988f, 1f)
		panel_3.setBounds(569, 11, 81, 81);
		
		setTitle("PekaSE");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 699, 496);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmNew = new JMenuItem("New");
		mntmNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				newFile();
			}
		});
		mnFile.add(mntmNew);
		
		JMenuItem mntmOpen = new JMenuItem("Open");
		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				
				fc.setDialogTitle("Open sprite file...");
				
				if (lastSpritePath != null) {
					fc.setCurrentDirectory(lastSpritePath);
				} else if (new File(Settings.BASE_PATH).exists()) {
					fc.setCurrentDirectory(new File(Settings.SPRITE_PATH));
				}
				
				fc.setFileFilter(new FileFilter() {

					@Override
					public boolean accept(File f) {
						return f.isDirectory() || f.getName().endsWith("spr") || (Settings.use14 && f.getName().endsWith("cespr"));
					}

					@Override
					public String getDescription() {
						return "Pekka Kana 2 Sprite file (*.spr | *.cespr)";
					}
					
				});
				
				int res = fc.showOpenDialog(null);
				
				if (res == JFileChooser.APPROVE_OPTION) {
					currentFile = fc.getSelectedFile();
					
					if (!currentFile.getName().endsWith("spr")) {
						currentFile = new File(fc.getSelectedFile().getAbsolutePath() + ".spr");
					}
					
					lastSpritePath = fc.getSelectedFile().getParentFile();
					
					PK2Sprite sp = new PK2Sprite(currentFile);
					
					int v = sp.checkVersion();
					
					if (v == 3 || v == 4) {
						loadFile();
					} else {
						JOptionPane.showMessageDialog(null, "Only sprites version 1.3 or 1.4 supported!", "Wrong sprite version", JOptionPane.OK_OPTION);
					}
					
					panel_3.repaint();
				}
			}
		});
		mnFile.add(mntmOpen);
		
		JMenuItem miSave = new JMenuItem("Save");
		miSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (spriteFile == null) {
					JFileChooser fc = new JFileChooser("Save sprite...");
					
					fc.setFileFilter(new FileFilter() {

						@Override
						public boolean accept(File f) {
							return f.isDirectory() || f.getName().endsWith("spr")  || (Settings.use14 && f.getName().endsWith("cespr"));
						}

						@Override
						public String getDescription() {
							return "Pekka Kana 2 Sprite file (*.spr | *.cespr)";
						}
						
					});
					
					fc.setSelectedFile(lastFile);
					fc.setCurrentDirectory(lastSpritePath);
					
					int res = fc.showSaveDialog(null);
					
					if (res == JFileChooser.APPROVE_OPTION) {
						File f = fc.getSelectedFile();
						
						if (!Settings.use14) {
							if (!f.getName().endsWith("spr")) {
								f = new File(fc.getSelectedFile().getAbsolutePath() + ".spr");
							}
						} else {
							if (!f.getName().endsWith("cespr")) {
								f = new File(fc.getSelectedFile().getAbsolutePath() + ".cespr");
							}
						}
						
						
						if (saveFile(f)) {
							setFrameTitle("PekaSE - " + currentFile.getAbsolutePath());
							
							lastFile = fc.getSelectedFile();
							lastSpritePath = fc.getSelectedFile().getParentFile();
							
							cal = Calendar.getInstance();
							
							lblStatus.setText("File saved! (" + sdf.format(cal.getTime()) + ")");
						}
					}
				} else {
					if (saveFile(currentFile)) {
						cal = Calendar.getInstance();
						
			        	lblStatus.setText("File saved! (" + sdf.format(cal.getTime()) + ")");
					}
				}
			}
			
		});
		
		JMenuItem mntmSaveAs = new JMenuItem("Save as...");
		mntmSaveAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser("Save sprite...");
				
				fc.setFileFilter(new FileFilter() {

					@Override
					public boolean accept(File f) {
						return f.isDirectory() || f.getName().endsWith("spr") || f.getName().endsWith("cespr");
					}

					@Override
					public String getDescription() {
						return "Pekka Kana 2 Sprite file (*.spr | *.cespr)";
					}
					
				});
				
				fc.setSelectedFile(currentFile);
				fc.setCurrentDirectory(lastSpritePath);
				
				int res = fc.showSaveDialog(null);
				
				if (res == JFileChooser.APPROVE_OPTION) {
					File f = fc.getSelectedFile();
					
					if (!Settings.use14) {
						if (!f.getName().endsWith("spr")) {
							f = new File(fc.getSelectedFile().getAbsolutePath() + ".spr");
						}
					} else {
						if (!f.getName().endsWith("cespr")) {
							f = new File(fc.getSelectedFile().getAbsolutePath() + ".cespr");
						}
					}
					
					if (f.getName().length() < 13 || Settings.use14) {
						if (saveFile(f)) {
							setFrameTitle("PekaSE - " + currentFile.getAbsolutePath());
							
							lastFile = fc.getSelectedFile();
							lastSpritePath = fc.getSelectedFile().getParentFile();
							
							cal = Calendar.getInstance();
							
							lblStatus.setText("File saved! (" + sdf.format(cal.getTime()) + ")");
						}
					} else {
						JOptionPane.showMessageDialog(null, "Sprite file name, including '.spr', can't exceed 12 characters.", "File name too long", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		
		mnFile.add(miSave);
		mnFile.add(mntmSaveAs);
		
		JMenu mnRules = new JMenu("Sprite version");
		menuBar.add(mnRules);
		
		JCheckBoxMenuItem menuItem_1 = new JCheckBoxMenuItem("1.4");
		JCheckBoxMenuItem menuItem = new JCheckBoxMenuItem("1.3");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Settings.use14 = false;
				
				menuItem_1.setSelected(false);
				
				spinner_4.setEnabled(false);
				tabbedPane.remove(4);
				
				File f2 = new File("PekaSE.paths");
				
				try {
					if (f2.exists()) {
						f2.delete();
					}
					
					DataOutputStream dos = new DataOutputStream(new FileOutputStream("PekaSE.paths"));
					
					dos.writeUTF(Settings.BASE_PATH);
					dos.writeBoolean(Settings.use14);
					
					dos.flush();
					dos.close();
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Couldn't create settings file.\n" + e1.getMessage(), "Error", JOptionPane.OK_OPTION);
					
					e1.printStackTrace();
				}
			}
		});
		
		menuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Settings.use14 = true;
				
				menuItem.setSelected(false);
				tabbedPane.addTab("More", null, panel_7, null);
				spinner_4.setEnabled(true);
				
				File f2 = new File("PekaSE.paths");
				
				try {
					if (f2.exists()) {
						f2.delete();
					}

					DataOutputStream dos = new DataOutputStream(new FileOutputStream("PekaSE.paths"));
					
					dos.writeUTF(Settings.BASE_PATH);
					dos.writeBoolean(Settings.use14);
					
					dos.flush();
					dos.close();
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Couldn't create settings file.\n" + e1.getMessage(), "Error", JOptionPane.OK_OPTION);
					
					e1.printStackTrace();
				}
			}
		});
		
		mnRules.add(menuItem);
		mnRules.add(menuItem_1);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Properties", null, panel, null);
		panel.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(67, 11, 385, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel lblImageFile = new JLabel("Image:");
		lblImageFile.setToolTipText("The image sheet of frames");
		lblImageFile.setBounds(10, 14, 47, 14);
		panel.add(lblImageFile);
		
		JButton btnBrowse = new JButton("Browse");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				
				fc.setDialogTitle("Open image file...");
				
				if (lastImgPath != null) {
					fc.setCurrentDirectory(lastImgPath);
				} else if (new File(Settings.BASE_PATH).exists()) {
					fc.setCurrentDirectory(new File(Settings.SPRITE_PATH));
				}
				
				fc.setFileFilter(new FileFilter() {

					@Override
					public boolean accept(File f) {
						return f.isDirectory() | f.getName().endsWith("bmp");
					}

					@Override
					public String getDescription() {
						return "Windows bitmap file (*.bmp)";
					}
					
				});
				
				int res = fc.showOpenDialog(null);
				
				if (res == JFileChooser.APPROVE_OPTION) {
					String pstr = "";
					
					if (fc.getSelectedFile().getParent().equals("sprites\\")) {
						pstr = "sprites\\" + fc.getSelectedFile().getName();
					} else {
						pstr = fc.getSelectedFile().getParentFile().getName() + "\\" + fc.getSelectedFile().getName();
					}
					
					rawFile = fc.getSelectedFile();
					
					textField.setText(pstr);
					
					spriteFile.ImageFileStr = fc.getSelectedFile().getAbsolutePath();
					
					spriteFile.imageFile = fc.getSelectedFile().getName().toCharArray();
					
					spriteFile.frameX = (int) spinner.getValue();
					spriteFile.frameY = (int) spinner_1.getValue();
					
					spriteFile.frameWidth = (int) spinner_2.getValue();
					spriteFile.frameHeight = (int) spinner_3.getValue();
					
					BufferedImage img = null;
					
					try {
						img = ImageIO.read(rawFile);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
					if (spriteFile.frameX > img.getWidth() || spriteFile.frameY > img.getHeight() || spriteFile.frameX + spriteFile.frameWidth > img.getWidth() || spriteFile.frameY + spriteFile.frameHeight > img.getHeight()) {
						JOptionPane.showMessageDialog(null, "Frame X or Y was out of bounds.\n\tResetting values.");
					
						spriteFile.frameX = 0;
						spriteFile.frameY = 0;
						spriteFile.frameWidth = 1;
						spriteFile.frameHeight = 1;
						
						spinner.setValue(0);
						spinner_1.setValue(0);
						spinner_2.setValue(1);
						spinner_3.setValue(1);
					}
					
					spriteFile.loadBufferedImage();
					
					panel_3.repaint();
				}
			}
		});
		btnBrowse.setBounds(462, 10, 89, 23);
		panel.add(btnBrowse);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setToolTipText("Name of sprite will only show in editor");
		lblName.setBounds(51, 123, 34, 17);
		panel.add(lblName);
		
		txtFieldName = new JTextField();
		txtFieldName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				spriteFile.name = textFieldSndKnockOut.getText().toCharArray();
			}
		});
		txtFieldName.setBounds(97, 121, 170, 20);
		panel.add(txtFieldName);
		txtFieldName.setColumns(10);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 107, 664, 6);
		panel.add(separator);
		
		JLabel lblType = new JLabel("Type:");
		lblType.setBounds(10, 243, 40, 14);
		panel.add(lblType);
		
		comboBoxType = new JComboBox();
		comboBoxType.setModel(new DefaultComboBoxModel(new String[] {"Character", "Bonus Item", "Ammo", "Teleport", "Background", "Collectable", "Checkpoint", "Exit"}));
		comboBoxType.setBounds(51, 240, 170, 20);
		panel.add(comboBoxType);
		
		JLabel lblOffsetX = new JLabel("Position X:");
		lblOffsetX.setBounds(10, 51, 60, 14);
		panel.add(lblOffsetX);
		
		JLabel lblOffsetY = new JLabel("Position Y:");
		lblOffsetY.setBounds(10, 79, 60, 14);
		panel.add(lblOffsetY);
		
		spinner = new JSpinner();
		spinner.setBounds(77, 48, 47, 20);
		panel.add(spinner);
		
		spinner_1 = new JSpinner();
		spinner_1.setBounds(77, 76, 47, 20);
		panel.add(spinner_1);
		
		JLabel lblNewLabel = new JLabel("Frame width:");
		lblNewLabel.setBounds(142, 51, 79, 14);
		panel.add(lblNewLabel);
		
		spinner_2 = new JSpinner();
		spinner_2.setBounds(219, 48, 47, 20);
		panel.add(spinner_2);
		
		JLabel lblHeight = new JLabel("Frame height:");
		lblHeight.setBounds(142, 79, 77, 14);
		panel.add(lblHeight);
		
		spinner_3 = new JSpinner();
		spinner_3.setBounds(219, 76, 47, 20);
		panel.add(spinner_3);
		
		JLabel lblColor = new JLabel("Color:");
		lblColor.setBounds(10, 281, 34, 14);
		panel.add(lblColor);
		
		comboBoxColor = new JComboBox();
		comboBoxColor.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (comboBoxColor.getSelectedIndex() > -1) {
					if (spriteFile.image != null) {
						spriteFile.color = colors[comboBoxColor.getSelectedIndex()];
						
						spriteFile.loadBufferedImage();
						
						test = spriteFile.frameList[0];
						
						panel_3.repaint();
						panel_4.repaint();
					}
				}
			}
		});
		
		comboBoxColor.setEditable(true);
		comboBoxColor.setModel(new DefaultComboBoxModel(new String[] {"Original", "Grey", "Blue", "Red", "Green", "Orange", "Violet", "Turquoise"}));
		comboBoxColor.setBounds(51, 278, 170, 20);
		panel.add(comboBoxColor);
		
		JLabel lblDamageType = new JLabel("Damage Type:");
		lblDamageType.setBounds(8, 328, 77, 14);
		panel.add(lblDamageType);
		
		comboBoxDmg = new JComboBox();
		comboBoxDmg.setEditable(true);
		comboBoxDmg.setModel(new DefaultComboBoxModel(new String[] {"No damage", "Punch", "Fall (stomp)", "Noise", "Fire", "Water", "Snow", "Bonus", "Electricity", "Self destruction", "Squeeze", "Smell", "All", "Sting"}));
		comboBoxDmg.setBounds(86, 325, 135, 20);
		panel.add(comboBoxDmg);
		
		JLabel lblImmunity = new JLabel("Immunity:");
		lblImmunity.setBounds(10, 360, 67, 14);
		panel.add(lblImmunity);
		
		comboBoxImmunity = new JComboBox();
		comboBoxImmunity.setEditable(true);
		comboBoxImmunity.setModel(new DefaultComboBoxModel(new String[] {"No damage", "Punch", "Fall (stomp)", "Noise", "Fire", "Water", "Snow", "Bonus", "Electricity", "Self destruction", "Squeeze", "Smell", "All", "Sting"}));
		comboBoxImmunity.setBounds(86, 357, 135, 20);
		panel.add(comboBoxImmunity);
		
		JLabel lblWeight = new JLabel("Weight:");
		lblWeight.setBounds(365, 243, 46, 14);
		panel.add(lblWeight);
		
		JLabel lblEnergy = new JLabel("Energy:");
		lblEnergy.setBounds(365, 268, 46, 14);
		panel.add(lblEnergy);
		
		spinnerEnergy = new JSpinner();
		spinnerEnergy.setBounds(421, 265, 60, 20);
		panel.add(spinnerEnergy);
		
		JLabel lblDamage = new JLabel("Damage:");
		lblDamage.setBounds(365, 293, 46, 14);
		panel.add(lblDamage);
		
		spinnerDmg = new JSpinner();
		spinnerDmg.setBounds(421, 290, 60, 20);
		panel.add(spinnerDmg);
		
		JLabel lblScore = new JLabel("Score:");
		lblScore.setBounds(365, 319, 46, 14);
		panel.add(lblScore);
		
		spinnerScore = new JSpinner();
		spinnerScore.setBounds(421, 316, 60, 20);
		panel.add(spinnerScore);
		
		JLabel lblMaxSpeed = new JLabel("Max speed:");
		lblMaxSpeed.setBounds(506, 243, 77, 14);
		panel.add(lblMaxSpeed);
		
		JLabel lblReloadTime = new JLabel("Reload time:");
		lblReloadTime.setBounds(506, 293, 77, 14);
		panel.add(lblReloadTime);
		
		spinnerLoadDur = new JSpinner();
		spinnerLoadDur.setBounds(590, 290, 60, 20);
		panel.add(spinnerLoadDur);
		
		chckbxEnemy = new JCheckBox("Enemy");
		chckbxEnemy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				spriteFile.enemy = chckbxEnemy.isSelected();
			}
		});
		chckbxEnemy.setBounds(362, 356, 62, 23);
		panel.add(chckbxEnemy);
		
		chckbxBoss = new JCheckBox("Boss");
		chckbxBoss.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				spriteFile.boss = chckbxBoss.isSelected();
			}
		});
		chckbxBoss.setBounds(437, 356, 60, 23);
		panel.add(chckbxBoss);
		
		chckbxKey = new JCheckBox("Key");
		chckbxKey.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				spriteFile.key = chckbxKey.isSelected();
			}
		});
		chckbxKey.setBounds(501, 356, 50, 23);
		panel.add(chckbxKey);
		
		chckbxObstacle = new JCheckBox("Obstacle");
		chckbxObstacle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				spriteFile.obstacle = chckbxObstacle.isSelected();
			}
		});
		chckbxObstacle.setBounds(553, 356, 97, 23);
		panel.add(chckbxObstacle);
		
		JLabel lblAttackSprite = new JLabel("Ammo Sprite 1:");
		lblAttackSprite.setBounds(11, 161, 76, 14);
		panel.add(lblAttackSprite);
		
		JLabel lblAmmoSprite = new JLabel("Ammo Sprite 2:");
		lblAmmoSprite.setBounds(10, 186, 77, 14);
		panel.add(lblAmmoSprite);
		
		textFieldAmmoSpr1 = new JTextField();
		textFieldAmmoSpr1.setBounds(97, 158, 314, 20);
		panel.add(textFieldAmmoSpr1);
		textFieldAmmoSpr1.setColumns(10);
		
		textFieldAmmoSpr2 = new JTextField();
		textFieldAmmoSpr2.setBounds(97, 183, 314, 20);
		panel.add(textFieldAmmoSpr2);
		textFieldAmmoSpr2.setColumns(10);
		
		JButton btnBrowse_1 = new JButton("Browse");
		btnBrowse_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				
				fc.setDialogTitle("Open sprite file...");
				
				if (lastSpritePath != null) {
					fc.setCurrentDirectory(lastSpritePath);
				} else if (new File(Settings.BASE_PATH).exists()) {
					fc.setCurrentDirectory(new File(Settings.SPRITE_PATH));
				}
				
				fc.setFileFilter(new FileFilter() {

					@Override
					public boolean accept(File f) {
						return f.isDirectory() || f.getName().endsWith("spr");
					}

					@Override
					public String getDescription() {
						return "Pekka Kana 2 Sprite file (*.spr)";
					}
					
				});
				
				int res = fc.showOpenDialog(null);
				
				if (res == JFileChooser.APPROVE_OPTION) {
					textFieldAmmoSpr1.setText(fc.getSelectedFile().getParentFile().getName() + "\\" + fc.getSelectedFile().getName());
					
					spriteFile.atkSprite1 = textFieldAmmoSpr1.getText().toCharArray();
					
					lastSpritePath = fc.getSelectedFile().getParentFile();
				}
			}
		});
		btnBrowse_1.setBounds(421, 157, 89, 23);
		panel.add(btnBrowse_1);
		
		JButton btnNewButton = new JButton("Browse");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				
				fc.setDialogTitle("Open sprite file...");
				
				if (lastSpritePath != null) {
					fc.setCurrentDirectory(lastSpritePath);
				} else if (new File(Settings.BASE_PATH).exists()) {
					fc.setCurrentDirectory(new File(Settings.SPRITE_PATH));
				}
				
				fc.setFileFilter(new FileFilter() {

					@Override
					public boolean accept(File f) {
						return f.isDirectory() || f.getName().endsWith("spr");
					}

					@Override
					public String getDescription() {
						return "Pekka Kana 2 Sprite file (*.spr)";
					}
					
				});
				
				int res = fc.showOpenDialog(null);
				
				if (res == JFileChooser.APPROVE_OPTION) {
					textFieldAmmoSpr2.setText(fc.getSelectedFile().getParentFile().getName() + "\\" + fc.getSelectedFile().getName());
					
					spriteFile.atkSprite2 = textFieldAmmoSpr2.getText().toCharArray();
					
					lastSpritePath = fc.getSelectedFile().getParentFile();
				}
			}
		});
		btnNewButton.setBounds(421, 182, 89, 23);
		panel.add(btnNewButton);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Animation", null, panel_1, null);
		panel_1.setLayout(null);
		
		panel_4 = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);

				if (spriteFile.image != null) {
					if (spriteFile.frameHeight > 130 || spriteFile.frameWidth > 130) {
						g.drawImage(test, 65 - (sprScaleWidth / 2), 65 - (sprScaleHeight / 2), sprScaleWidth, sprScaleHeight, null);
					} else {
						g.drawImage(test, 65 - (spriteFile.frameWidth / 2), 65 - (spriteFile.frameHeight / 2), null);
					}
				}
				
				g.setColor(Color.black);
				g.drawRect(0, 0, 129, 129);
			}
		};
		
		panel_4.setBounds(535, 9, 130, 130);
	
		panel_4.setBackground(Color.lightGray);
		
		panel_1.add(panel_4);
		
		
		Thread t = new Thread(new Runnable() {

			private int j = 0;
			private int sleep = 17;
			
			public void run() {
				while (true) {
					try {
						Thread.sleep(sleep);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					if (spriteFile.frameList != null && animate && spriteFile.frames >= 1) {
						int ani = 0;
						int a = 0;
						
						while (spriteFile.animation[index].sequence[a] > 0) {
							ani++;
							
							if (a + 1 < spriteFile.animation[index].sequence.length) {
								a++;
							} else {
								break;
							}
						}
						
						
						if (spriteFile.animation[index].sequence[i] > 0) {
							j = spriteFile.animation[index].sequence[i];

							if (j - 1 < spriteFile.frames) {
								test = spriteFile.frameList[j - 1];
							}
							
							if (i + 1 > ani - 1) {
								if (loop) {
									i = 0;
								}
							} else {
								i++;
							}
							
							sleep = (85 / spriteFile.frameRate) * 10;

							panel_4.repaint();
						}
					}
				}
			}
		});
		
		t.start();
		
		JLabel lblFrameRate = new JLabel("Frame rate:");
		lblFrameRate.setBounds(10, 18, 69, 14);
		panel_1.add(lblFrameRate);
		
		JLabel lblAnimations = new JLabel("Animations:");
		lblAnimations.setBounds(10, 73, 63, 14);
		panel_1.add(lblAnimations);
		
		spinnerFrameRate = new JSpinner();
		spinnerFrameRate.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		spinnerFrameRate.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				spriteFile.frameRate = (int) spinnerFrameRate.getValue();
			}
		});
		spinnerFrameRate.setBounds(72, 15, 50, 20);
		panel_1.add(spinnerFrameRate);
		
		spinnerAnimations = new JSpinner();
		spinnerAnimations.setBounds(72, 70, 50, 20);
		panel_1.add(spinnerAnimations);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(0, 171, 696, 8);
		panel_1.add(separator_1);
		
		JButton btnStill = new JButton("Still");
		btnStill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playAnimation(0);
			}
		});
		btnStill.setBounds(132, 9, 89, 23);
		panel_1.add(btnStill);
		
		JButton btnWalking = new JButton("Walking");
		btnWalking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playAnimation(1);
			}
		});
		btnWalking.setBounds(132, 37, 89, 23);
		panel_1.add(btnWalking);
		
		JButton btnJumpUp = new JButton("Jump up");
		btnJumpUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playAnimation(3);
			}
		});
		btnJumpUp.setBounds(231, 9, 89, 23);
		panel_1.add(btnJumpUp);
		
		JButton btnJumpDown = new JButton("Jump down");
		btnJumpDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playAnimation(4);
			}
		});
		btnJumpDown.setBounds(231, 37, 89, 23);
		panel_1.add(btnJumpDown);
		
		JButton btnDuck = new JButton("Duck");
		btnDuck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playAnimation(2);
			}
		});
		btnDuck.setBounds(132, 67, 89, 23);
		panel_1.add(btnDuck);
		
		JButton btnDamage = new JButton("Damage");
		btnDamage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playAnimation(5);
			}
		});
		btnDamage.setBounds(330, 9, 89, 23);
		panel_1.add(btnDamage);
		
		JButton btnKnockOut = new JButton("Knock out");
		btnKnockOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playAnimation(6);
			}
		});
		btnKnockOut.setBounds(330, 37, 89, 23);
		panel_1.add(btnKnockOut);
		
		JButton btnAttack = new JButton("Attack 1");
		btnAttack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playAnimation(7);
			}
		});
		btnAttack.setBounds(429, 9, 89, 23);
		panel_1.add(btnAttack);
		
		JButton btnAttack_1 = new JButton("Attack 2");
		btnAttack_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playAnimation(8);
			}
		});
		btnAttack_1.setBounds(429, 37, 89, 23);
		panel_1.add(btnAttack_1);
		
		JButton btnStop = new JButton("Stop");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				animate = false;
			}
		});
		btnStop.setBounds(429, 67, 89, 23);
		panel_1.add(btnStop);
		
		JButton btnNewButton_1 = new JButton("Show frames");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new FramesDialog(spriteFile);
			}
		});
		btnNewButton_1.setBounds(500, 340, 112, 23);
		panel_1.add(btnNewButton_1);
		
		JLabel lblStill = new JLabel("Still:");
		lblStill.setBounds(27, 190, 28, 14);
		panel_1.add(lblStill);
		
		textFieldAniStill = new JTextField();
		textFieldAniStill.setBounds(89, 190, 188, 20);
		panel_1.add(textFieldAniStill);
		textFieldAniStill.setColumns(10);
		
		JLabel lblWalking = new JLabel("Walking:");
		lblWalking.setBounds(27, 218, 46, 14);
		panel_1.add(lblWalking);
		
		textFieldAniWalk = new JTextField();
		textFieldAniWalk.setBounds(89, 215, 188, 20);
		panel_1.add(textFieldAniWalk);
		textFieldAniWalk.setColumns(10);
		
		JLabel lblJumpUp = new JLabel("Jump up:");
		lblJumpUp.setBounds(27, 287, 46, 14);
		panel_1.add(lblJumpUp);
		
		textFieldAniJumpUp = new JTextField();
		textFieldAniJumpUp.setBounds(89, 284, 188, 20);
		panel_1.add(textFieldAniJumpUp);
		textFieldAniJumpUp.setColumns(10);
		
		JLabel lblJumpDown = new JLabel("Jump down:");
		lblJumpDown.setBounds(27, 312, 63, 14);
		panel_1.add(lblJumpDown);
		
		textFieldAniJumpDown = new JTextField();
		textFieldAniJumpDown.setBounds(89, 309, 188, 20);
		panel_1.add(textFieldAniJumpDown);
		textFieldAniJumpDown.setColumns(10);
		
		JLabel lblDuck = new JLabel("Duck:");
		lblDuck.setBounds(27, 243, 46, 14);
		panel_1.add(lblDuck);
		
		textFieldAniDuck = new JTextField();
		textFieldAniDuck.setBounds(89, 240, 188, 20);
		panel_1.add(textFieldAniDuck);
		textFieldAniDuck.setColumns(10);
		
		JLabel lblDamage_2 = new JLabel("Damage:");
		lblDamage_2.setBounds(362, 190, 46, 14);
		panel_1.add(lblDamage_2);
		
		textFieldAniDmg = new JTextField();
		textFieldAniDmg.setBounds(424, 190, 188, 20);
		panel_1.add(textFieldAniDmg);
		textFieldAniDmg.setColumns(10);
		
		JLabel lblKnockOut_1 = new JLabel("Knock out:");
		lblKnockOut_1.setBounds(362, 218, 63, 14);
		panel_1.add(lblKnockOut_1);
		
		textFieldAniKnockOut = new JTextField();
		textFieldAniKnockOut.setBounds(424, 215, 188, 20);
		panel_1.add(textFieldAniKnockOut);
		textFieldAniKnockOut.setColumns(10);
		
		JLabel lblAttack_2 = new JLabel("Attack 1:");
		lblAttack_2.setBounds(362, 287, 46, 14);
		panel_1.add(lblAttack_2);
		
		JLabel lblAttack_3 = new JLabel("Attack 2:");
		lblAttack_3.setBounds(362, 312, 46, 14);
		panel_1.add(lblAttack_3);
		
		textFieldAniAtk1 = new JTextField();
		textFieldAniAtk1.setColumns(10);
		textFieldAniAtk1.setBounds(424, 284, 188, 20);
		panel_1.add(textFieldAniAtk1);
		
		textFieldAniAtk2 = new JTextField();
		textFieldAniAtk2.setColumns(10);
		textFieldAniAtk2.setBounds(424, 309, 188, 20);
		panel_1.add(textFieldAniAtk2);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(0, 374, 674, 14);
		panel_1.add(separator_2);
		
		JLabel lblAtkDuration = new JLabel("Attack 1 duration:");
		lblAtkDuration.setBounds(10, 111, 94, 14);
		panel_1.add(lblAtkDuration);
		
		JLabel lblAttackDuration = new JLabel("Attack 2 duration:");
		lblAttackDuration.setBounds(10, 146, 94, 14);
		panel_1.add(lblAttackDuration);
		
		spinnerAtk1Dur = new JSpinner();
		spinnerAtk1Dur.setBounds(132, 111, 46, 20);
		panel_1.add(spinnerAtk1Dur);
		
		spinnerAtk2Dur = new JSpinner();
		spinnerAtk2Dur.setBounds(132, 140, 46, 20);
		panel_1.add(spinnerAtk2Dur);
		
		JLabel lblFrames = new JLabel("Frames:");
		lblFrames.setBounds(10, 48, 46, 14);
		panel_1.add(lblFrames);
		
		spinnerFrames = new JSpinner();
		spinnerFrames.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		spinnerFrames.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				spriteFile.frames = (int) spinnerFrames.getValue();
				
				if (spriteFile.image != null) {
					spriteFile.loadBufferedImage();
					
					test = spriteFile.frameList[0];
					
					panel_4.repaint();
				}
			}
		});
		spinnerFrames.setBounds(72, 42, 50, 20);
		panel_1.add(spinnerFrames);
		
		chckbxStillLoop = new JCheckBox("Loop");
		chckbxStillLoop.setBounds(283, 189, 50, 23);
		panel_1.add(chckbxStillLoop);
		
		chckbxWalkingLoop = new JCheckBox("Loop");
		chckbxWalkingLoop.setBounds(283, 214, 50, 23);
		panel_1.add(chckbxWalkingLoop);
		
		chckbxDuckLoop = new JCheckBox("Loop");
		chckbxDuckLoop.setBounds(283, 239, 50, 23);
		panel_1.add(chckbxDuckLoop);
		
		chckbxJumpUpLoop = new JCheckBox("Loop");
		chckbxJumpUpLoop.setBounds(283, 283, 50, 23);
		panel_1.add(chckbxJumpUpLoop);
		
		chckbxJumpDownLoop = new JCheckBox("Loop");
		chckbxJumpDownLoop.setBounds(283, 308, 50, 23);
		panel_1.add(chckbxJumpDownLoop);
		
		chckbxDmgLoop = new JCheckBox("Loop");
		chckbxDmgLoop.setBounds(615, 186, 50, 23);
		panel_1.add(chckbxDmgLoop);
		
		chckbxKnockOutLoop = new JCheckBox("Loop");
		chckbxKnockOutLoop.setBounds(615, 214, 50, 23);
		panel_1.add(chckbxKnockOutLoop);
		
		chckbxAtk1Loop = new JCheckBox("Loop");
		chckbxAtk1Loop.setBounds(618, 283, 50, 23);
		panel_1.add(chckbxAtk1Loop);
		
		chckbxAtk2Loop = new JCheckBox("Loop");
		chckbxAtk2Loop.setBounds(618, 308, 50, 23);
		panel_1.add(chckbxAtk2Loop);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Sounds", null, panel_2, null);
		panel_2.setLayout(null);
		
		JLabel lblDamage_1 = new JLabel("Damage:");
		lblDamage_1.setBounds(91, 44, 52, 14);
		panel_2.add(lblDamage_1);
		
		textFieldSndKnockOut = new JTextField();
		textFieldSndKnockOut.setBounds(147, 74, 287, 20);
		panel_2.add(textFieldSndKnockOut);
		textFieldSndKnockOut.setColumns(10);
		
		panel.add(panel_3);
		
		JLabel lblMaxJump_1 = new JLabel("Max Jump:");
		lblMaxJump_1.setBounds(506, 268, 67, 14);
		panel.add(lblMaxJump_1);
		
		spinnerMaxJump = new JSpinner();
		spinnerMaxJump.setBounds(590, 265, 60, 20);
		panel.add(spinnerMaxJump);
		
		JLabel label_10 = new JLabel("Attack pause:");
		label_10.setBounds(506, 319, 67, 14);
		panel.add(label_10);
		
		spinnerAtkPause = new JSpinner();
		spinnerAtkPause.setBounds(590, 316, 60, 20);
		panel.add(spinnerAtkPause);
		
		spinnerMaxSpeed = new JSpinner();
		spinnerMaxSpeed.setModel(new SpinnerNumberModel(new Double(0), null, null, new Double(1)));
		spinnerMaxSpeed.setBounds(590, 240, 60, 20);
		panel.add(spinnerMaxSpeed);
		
		spinnerWeight = new JSpinner();
		spinnerWeight.setModel(new SpinnerNumberModel(new Double(0), null, null, new Double(1)));
		spinnerWeight.setBounds(421, 240, 60, 20);
		panel.add(spinnerWeight);
		
		JLabel lblSpriteWidth = new JLabel("Sprite width:");
		lblSpriteWidth.setBounds(295, 51, 67, 14);
		panel.add(lblSpriteWidth);
		
		JLabel lblSpriteHeight = new JLabel("Sprite height:");
		lblSpriteHeight.setBounds(295, 79, 67, 14);
		panel.add(lblSpriteHeight);
		
		spinnerSprWidth = new JSpinner();
		
		spinnerSprWidth.setBounds(365, 48, 46, 20);
		panel.add(spinnerSprWidth);
		
		spinnerSprHeight = new JSpinner();
		spinnerSprHeight.setBounds(365, 76, 46, 20);
		panel.add(spinnerSprHeight);
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//spriteFile.ImageFileStr = Settings.BASE_PATH + "\\" + textField.getText();
				spriteFile.ImageFileStr = rawFile.getAbsolutePath();
				
				spriteFile.imageFile = textField.getText().toCharArray();
				
				spriteFile.frameX = (int) spinner.getValue();
				spriteFile.frameY = (int) spinner_1.getValue();
				
				spriteFile.frameWidth = (int) spinner_2.getValue();
				spriteFile.frameHeight = (int) spinner_3.getValue();
				
				BufferedImage img = null;
				try {
					img = ImageIO.read(rawFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				if (spriteFile.frameX > img.getWidth() || spriteFile.frameY > img.getHeight() || spriteFile.frameX + spriteFile.frameWidth > img.getWidth() || spriteFile.frameY + spriteFile.frameHeight > img.getHeight()) {
					JOptionPane.showMessageDialog(null, "Frame X or Y was out of bounds.\n\tResetting values..");
				
					spriteFile.frameX = 0;
					spriteFile.frameY = 0;
					spriteFile.frameWidth = 1;
					spriteFile.frameHeight = 1;
					
					spinner.setValue(0);
					spinner_1.setValue(0);
					spinner_2.setValue(1);
					spinner_3.setValue(1);
				}
				
				spriteFile.loadBufferedImage();
				
				panel_3.repaint();
			}
		});
		btnRefresh.setBounds(462, 47, 89, 23);
		panel.add(btnRefresh);
		
		JButton btnSet = new JButton("Set");
		btnSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ImageDimensionDialog idd = new ImageDimensionDialog(rawFile, spinner, spinner_1, spinner_2, spinner_3, spriteFile, panel_3);
			}
		});
		btnSet.setBounds(462, 75, 89, 23);
		panel.add(btnSet);
		
		JButton btnBrowse_2 = new JButton("Browse");
		btnBrowse_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String f = loadSoundFile();
				
				if (!f.isEmpty()) {
					textFieldSndDmg.setText(f);
				}
			}
		});
		btnBrowse_2.setBounds(444, 36, 80, 25);
		panel_2.add(btnBrowse_2);
		
		JButton btnPlay = new JButton("Play");
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!textFieldSndDmg.getText().isEmpty()) {
					playSound(textFieldSndDmg.getText());
				}
			}
		});
		btnPlay.setBounds(534, 36, 80, 25);
		panel_2.add(btnPlay);
		
		JLabel lblKnockOut = new JLabel("Knock out:");
		lblKnockOut.setBounds(91, 77, 60, 14);
		panel_2.add(lblKnockOut);
		
		JButton button = new JButton("Browse");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String f = loadSoundFile();
				
				if (!f.isEmpty()) {
					textFieldSndKnockOut.setText(f);
				}
			}
		});
		button.setBounds(444, 69, 80, 25);
		panel_2.add(button);
		
		JButton button_1 = new JButton("Play");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!textFieldSndKnockOut.getText().isEmpty()) {
					playSound(textFieldSndKnockOut.getText());
				}
			}
		});
		button_1.setBounds(534, 69, 80, 25);
		panel_2.add(button_1);
		
		JLabel lblAttack = new JLabel("Attack 1:");
		lblAttack.setBounds(91, 110, 60, 14);
		panel_2.add(lblAttack);
		
		JLabel lblAttack_1 = new JLabel("Attack 2:");
		lblAttack_1.setBounds(91, 143, 60, 14);
		panel_2.add(lblAttack_1);
		
		JLabel lblRandom = new JLabel("Random:");
		lblRandom.setBounds(91, 180, 60, 14);
		panel_2.add(lblRandom);
		
		textFieldSndDmg = new JTextField();
		textFieldSndDmg.setColumns(10);
		textFieldSndDmg.setBounds(147, 41, 287, 20);
		panel_2.add(textFieldSndDmg);
		
		textFieldSndAtk1 = new JTextField();
		textFieldSndAtk1.setColumns(10);
		textFieldSndAtk1.setBounds(147, 107, 287, 20);
		panel_2.add(textFieldSndAtk1);
		
		JButton button_2 = new JButton("Browse");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String f = loadSoundFile();
				
				if (!f.isEmpty()) {
					textFieldSndAtk1.setText(f);
				}
			}
		});
		button_2.setBounds(444, 105, 80, 25);
		panel_2.add(button_2);
		
		JButton button_3 = new JButton("Play");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!textFieldSndAtk1.getText().isEmpty()) {
					playSound(textFieldSndAtk1.getText());
				}
			}
		});
		button_3.setBounds(534, 105, 80, 25);
		panel_2.add(button_3);
		
		textFieldSndAtk2 = new JTextField();
		textFieldSndAtk2.setColumns(10);
		textFieldSndAtk2.setBounds(147, 142, 287, 20);
		panel_2.add(textFieldSndAtk2);
		
		JButton button_4 = new JButton("Browse");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String f = loadSoundFile();
				
				if (!f.isEmpty()) {
					textFieldSndAtk2.setText(f);
				}
			}
		});
		button_4.setBounds(444, 141, 80, 25);
		panel_2.add(button_4);
		
		JButton button_5 = new JButton("Play");
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!textFieldSndAtk2.getText().isEmpty()) {
					playSound(textFieldSndAtk2.getText());
				}
			}
		});
		button_5.setBounds(534, 141, 80, 25);
		panel_2.add(button_5);
		
		textFieldAtkRnd = new JTextField();
		textFieldAtkRnd.setColumns(10);
		textFieldAtkRnd.setBounds(147, 177, 287, 20);
		panel_2.add(textFieldAtkRnd);
		
		JButton button_6 = new JButton("Browse");
		button_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String f = loadSoundFile();
				
				if (!f.isEmpty()) {
					textFieldAtkRnd.setText(f);
				}
			}
		});
		button_6.setBounds(444, 177, 80, 25);
		panel_2.add(button_6);
		
		JButton button_7 = new JButton("Play");
		button_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!textFieldAtkRnd.getText().isEmpty()) {
					playSound(textFieldAtkRnd.getText());
				}
			}
		});
		button_7.setBounds(534, 177, 80, 25);
		panel_2.add(button_7);
		
		JLabel lblSoundFrequency = new JLabel("Sound frequency:");
		lblSoundFrequency.setBounds(183, 286, 98, 14);
		panel_2.add(lblSoundFrequency);
		
		spinnerSndFrq = new JSpinner();
		spinnerSndFrq.setModel(new SpinnerNumberModel(new Integer(22050), null, null, new Integer(1)));
		spinnerSndFrq.setBounds(273, 283, 60, 20);
		panel_2.add(spinnerSndFrq);
		
		chckbxRandomFrequency = new JCheckBox("Random frequency");
		chckbxRandomFrequency.setBounds(356, 282, 142, 23);
		panel_2.add(chckbxRandomFrequency);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(0, 248, 674, 14);
		panel_2.add(separator_3);
		
		JPanel panel_5 = new JPanel();
		tabbedPane.addTab("Other", null, panel_5, null);
		panel_5.setLayout(null);
		
		JLabel lblDestruction = new JLabel("Destruction:");
		lblDestruction.setBounds(10, 11, 59, 14);
		panel_5.add(lblDestruction);
		
		comboBoxDestruction = new JComboBox();
		comboBoxDestruct2 = new JComboBox();
		
		comboBoxDestruction.setModel(new DefaultComboBoxModel(new String[] {"None", "Animation"}));
		comboBoxDestruction.setBounds(79, 8, 122, 20);
		panel_5.add(comboBoxDestruction);
		comboBoxDestruct2.setModel(new DefaultComboBoxModel(new String[] {"Indestructible", "Feathers", "Grey stars", "Blue stars", "Red stars", "Green stars", "Orange stars", "Violet stars", "Turquoise stars", "Grey explotion", "Blue explotion", "Red explotion", "Green explotion", "Orange explotion", "Violet explotion", "Turquoise explotion", "Grey smoke\t", "Blue smoke", "Red smoke", "Green smoke", "Orange smoke", "Violet smoke", "Turquoise smoke", "Smoke clouds"}));
		comboBoxDestruct2.setBounds(209, 8, 122, 20);
		panel_5.add(comboBoxDestruct2);
		
		JLabel lblBonuses = new JLabel("Bonuses:");
		lblBonuses.setBounds(326, 119, 46, 14);
		panel_5.add(lblBonuses);
		
		spinnerBonus = new JSpinner();
		spinnerBonus.setBounds(380, 116, 46, 20);
		panel_5.add(spinnerBonus);
		
		chckbxShakes = new JCheckBox("Shakes");
		chckbxShakes.setBounds(459, 11, 97, 23);
		panel_5.add(chckbxShakes);
		
		chckbxTileCheck = new JCheckBox("Tile check");
		chckbxTileCheck.setBounds(459, 37, 97, 23);
		panel_5.add(chckbxTileCheck);
		
		chckbxWallUp = new JCheckBox("Wall up");
		chckbxWallUp.setBounds(571, 11, 97, 23);
		panel_5.add(chckbxWallUp);
		
		chckbxWallDown = new JCheckBox("Wall down");
		chckbxWallDown.setBounds(571, 37, 97, 23);
		panel_5.add(chckbxWallDown);
		
		chckbxWallLeft = new JCheckBox("Wall left");
		chckbxWallLeft.setBounds(571, 63, 97, 23);
		panel_5.add(chckbxWallLeft);
		
		chckbxWallRight = new JCheckBox("Wall right");
		chckbxWallRight.setBounds(571, 89, 97, 23);
		panel_5.add(chckbxWallRight);
		
		chckbxAlwaysBonus = new JCheckBox("Always bonus");
		chckbxAlwaysBonus.setBounds(459, 115, 97, 23);
		panel_5.add(chckbxAlwaysBonus);
		
		chckbxSwim = new JCheckBox("Swim");
		chckbxSwim.setBounds(459, 63, 97, 23);
		panel_5.add(chckbxSwim);
		
		chckbxGlide = new JCheckBox("Glide");
		chckbxGlide.setBounds(459, 89, 97, 23);
		panel_5.add(chckbxGlide);
		
		JLabel lblTransformSprite = new JLabel("Transformation sprite:");
		lblTransformSprite.setBounds(10, 48, 112, 14);
		panel_5.add(lblTransformSprite);
		
		textFieldTransformSprite = new JTextField();
		textFieldTransformSprite.setBounds(122, 45, 209, 20);
		panel_5.add(textFieldTransformSprite);
		textFieldTransformSprite.setColumns(10);
		
		JButton btnBrowse_3 = new JButton("Browse");
		btnBrowse_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				
				fc.setDialogTitle("Open sprite file...");
				
				if (lastSpritePath != null) {
					fc.setCurrentDirectory(lastSpritePath);
				} else if (new File(Settings.BASE_PATH).exists()) {
					fc.setCurrentDirectory(new File(Settings.SPRITE_PATH));
				}
				
				fc.setFileFilter(new FileFilter() {

					@Override
					public boolean accept(File f) {
						return f.isDirectory() || f.getName().endsWith("spr");
					}

					@Override
					public String getDescription() {
						return "Pekka Kana 2 Sprite file (*.spr)";
					}
					
				});
				
				int res = fc.showOpenDialog(null);
				
				if (res == JFileChooser.APPROVE_OPTION) {
					if (fc.getSelectedFile().getName().length() < 100) {
						textFieldTransformSprite.setText(fc.getSelectedFile().getParentFile().getName() + "\\" + fc.getSelectedFile().getName());
						
						lastSpritePath = fc.getSelectedFile().getParentFile();
					} else {
						JOptionPane.showMessageDialog(null, "Sprite file name, including '.spr', can't exceed 100 characters.", "File name too long", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		btnBrowse_3.setBounds(337, 44, 89, 23);
		panel_5.add(btnBrowse_3);
		
		JLabel lblParallaxFactor = new JLabel("Parallax factor:");
		lblParallaxFactor.setBounds(10, 119, 82, 14);
		panel_5.add(lblParallaxFactor);
		
		spinnerParaFactor = new JSpinner();
		spinnerParaFactor.setBounds(89, 116, 46, 20);
		panel_5.add(spinnerParaFactor);
		
		JSeparator separator_4 = new JSeparator();
		separator_4.setBounds(0, 158, 674, 20);
		panel_5.add(separator_4);
		
		JLabel lblAiPatterns = new JLabel("AI patterns:");
		lblAiPatterns.setBounds(10, 164, 59, 14);
		panel_5.add(lblAiPatterns);
		
		comboBoxAi1 = new JComboBox();
		comboBoxAi1.setEditable(true);
		comboBoxAi1.setModel(new DefaultComboBoxModel(new String[] {"No AI", "Chicken", "An Egg", "Baby Chicken", "Bonus (B)", "Jumper", "Basic AI (B)", "Turn horizontally", "Looks Out for Cliffs", "Random Horizontal Change of Direction", "Random Jumping", "Follow the Player", "Random Horizontal Change of Direction", "Follow the Player if Player is in Front", "Transformation If Energy Below 2 (P)", "Transformation If Energy above 1 (P)", "Start Directions Towards Player", "Ammonition", "Non Stop", "Attack 1 If Damaged", "Self Destruction", "Attack 1 If Player is in Front", "Attack 1 If Player is Below", "Damaged by Water (P)", "Attack 2 If Player is in Front", "Kill 'em all", "Affected by Friction", "Hide", "Return to Start Position X", "Return to Start Position Y", "Teleport", "Throwable Weapon", "Falls When Shaken (B)", "Change Trap Stones If KO'ed", "Change Trap Stones If Damaged", "Self Destructs With Mother Sprite", "Moves X COS", "Moves Y COS", "Moves X SIN", "Moves Y SIN", "Moves X COS Fast", "Moves Y SIN Fast", "Moves X COS Slow", "Moves Y SIN Slow", "Moves Y SIN Free", "Random Turning", "Jump If Player Is Above", "Transformation Timer (B)", "Falls If Switch 1 Is Pressed (B)", "Falls If Switch 2 Is Pressed (B)", "Falls If Switch 3 Is Pressed (B)", "Moves Down If Switch 1 Is Pressed", "Moves Up If Switch 1 Is Pressed", "Moves Right If Switch 1 Is Pressed", "Moves Left If Switch 1 Is Pressed", "Moves Down If Switch 2 Is Pressed", "Moves Up If Switch 2 Is Pressed", "Moves Right If Switch 2 Is Pressed", "Moves Left If Switch 2 Is Pressed", "Moves Down If Switch 3 Is Pressed", "Moves Up If Switch 3 Is Pressed", "Moves Right If Switch 3 Is Pressed", "Moves Left If Switch 3 Is Pressed", "Turns Vertically From Obstacle", "Random Vertical Starting Direction", "Starting Direction Towards Player", "Climber", "Climber Type 2", "Runs Away From Player If Sees Player", "Reborn (B)", "Arrow Left", "Arrow Right", "Arrow Up", "Arrow Down", "Move to Arrows Direction", "Background Sprite Moon", "Background Sprite Moves to Left", "Background Sprite Moves to Right", "Add Time to Clock", "Make Player Invisible", "Follow the Player Vertic. and Horiz.", "Follow the Player Vertic. and Horiz., if Player is in Front", "Random Move Vertic. and Horiz.", "Frog Jump 1", "Frog Jump 2", "Frog Jump 3", "Attack 2 if Damaged", "Attack 1 Non Stop", "Attack 2 Non Stop", "Turn if Damaged", "Evil One", "Info 1", "Info 2", "Info 3", "Info 4", "Info 5", "Info 6", "Info 7", "Info 8", "Info 9", "Info 10", "Info 11", "Info 12", "Info 13", "Info 14", "Info 15", "Info 16", "Info 17", "Info 18", "Info 19"}));
		comboBoxAi1.setBounds(44, 197, 287, 20);
		panel_5.add(comboBoxAi1);
		
		comboBoxAi2 = new JComboBox();
		comboBoxAi2.setModel(new DefaultComboBoxModel(new String[] {"No AI", "Chicken", "An Egg", "Baby Chicken", "Bonus (B)", "Jumper", "Basic AI (B)", "Turn horizontally", "Looks Out for Cliffs", "Random Horizontal Change of Direction", "Random Jumping", "Follow the Player", "Random Horizontal Change of Direction", "Follow the Player if Player is in Front", "Transformation If Energy Below 2 (P)", "Transformation If Energy above 1 (P)", "Start Directions Towards Player", "Ammonition", "Non Stop", "Attack 1 If Damaged", "Self Destruction", "Attack 1 If Player is in Front", "Attack 1 If Player is Below", "Damaged by Water (P)", "Attack 2 If Player is in Front", "Kill 'em all", "Affected by Friction", "Hide", "Return to Start Position X", "Return to Start Position Y", "Teleport", "Throwable Weapon", "Falls When Shaken (B)", "Change Trap Stones If KO'ed", "Change Trap Stones If Damaged", "Self Destructs With Mother Sprite", "Moves X COS", "Moves Y COS", "Moves X SIN", "Moves Y SIN", "Moves X COS Fast", "Moves Y SIN Fast", "Moves X COS Slow", "Moves Y SIN Slow", "Moves Y SIN Free", "Random Turning", "Jump If Player Is Above", "Transformation Timer (B)", "Falls If Switch 1 Is Pressed (B)", "Falls If Switch 2 Is Pressed (B)", "Falls If Switch 3 Is Pressed (B)", "Moves Down If Switch 1 Is Pressed", "Moves Up If Switch 1 Is Pressed", "Moves Right If Switch 1 Is Pressed", "Moves Left If Switch 1 Is Pressed", "Moves Down If Switch 2 Is Pressed", "Moves Up If Switch 2 Is Pressed", "Moves Right If Switch 2 Is Pressed", "Moves Left If Switch 2 Is Pressed", "Moves Down If Switch 3 Is Pressed", "Moves Up If Switch 3 Is Pressed", "Moves Right If Switch 3 Is Pressed", "Moves Left If Switch 3 Is Pressed", "Turns Vertically From Obstacle", "Random Vertical Starting Direction", "Starting Direction Towards Player", "Climber", "Climber Type 2", "Runs Away From Player If Sees Player", "Reborn (B)", "Arrow Left", "Arrow Right", "Arrow Up", "Arrow Down", "Move to Arrows Direction", "Background Sprite Moon", "Background Sprite Moves to Left", "Background Sprite Moves to Right", "Add Time to Clock", "Make Player Invisible", "Follow the Player Vertic. and Horiz.", "Follow the Player Vertic. and Horiz., if Player is in Front", "Random Move Vertic. and Horiz.", "Frog Jump 1", "Frog Jump 2", "Frog Jump 3", "Attack 2 if Damaged", "Attack 1 Non Stop", "Attack 2 Non Stop", "Turn if Damaged", "Evil One", "Info 1", "Info 2", "Info 3", "Info 4", "Info 5", "Info 6", "Info 7", "Info 8", "Info 9", "Info 10", "Info 11", "Info 12", "Info 13", "Info 14", "Info 15", "Info 16", "Info 17", "Info 18", "Info 19"}));
		comboBoxAi2.setEditable(true);
		comboBoxAi2.setBounds(44, 228, 287, 20);
		panel_5.add(comboBoxAi2);
		
		comboBoxAi3 = new JComboBox();
		comboBoxAi3.setModel(new DefaultComboBoxModel(new String[] {"No AI", "Chicken", "An Egg", "Baby Chicken", "Bonus (B)", "Jumper", "Basic AI (B)", "Turn horizontally", "Looks Out for Cliffs", "Random Horizontal Change of Direction", "Random Jumping", "Follow the Player", "Random Horizontal Change of Direction", "Follow the Player if Player is in Front", "Transformation If Energy Below 2 (P)", "Transformation If Energy above 1 (P)", "Start Directions Towards Player", "Ammonition", "Non Stop", "Attack 1 If Damaged", "Self Destruction", "Attack 1 If Player is in Front", "Attack 1 If Player is Below", "Damaged by Water (P)", "Attack 2 If Player is in Front", "Kill 'em all", "Affected by Friction", "Hide", "Return to Start Position X", "Return to Start Position Y", "Teleport", "Throwable Weapon", "Falls When Shaken (B)", "Change Trap Stones If KO'ed", "Change Trap Stones If Damaged", "Self Destructs With Mother Sprite", "Moves X COS", "Moves Y COS", "Moves X SIN", "Moves Y SIN", "Moves X COS Fast", "Moves Y SIN Fast", "Moves X COS Slow", "Moves Y SIN Slow", "Moves Y SIN Free", "Random Turning", "Jump If Player Is Above", "Transformation Timer (B)", "Falls If Switch 1 Is Pressed (B)", "Falls If Switch 2 Is Pressed (B)", "Falls If Switch 3 Is Pressed (B)", "Moves Down If Switch 1 Is Pressed", "Moves Up If Switch 1 Is Pressed", "Moves Right If Switch 1 Is Pressed", "Moves Left If Switch 1 Is Pressed", "Moves Down If Switch 2 Is Pressed", "Moves Up If Switch 2 Is Pressed", "Moves Right If Switch 2 Is Pressed", "Moves Left If Switch 2 Is Pressed", "Moves Down If Switch 3 Is Pressed", "Moves Up If Switch 3 Is Pressed", "Moves Right If Switch 3 Is Pressed", "Moves Left If Switch 3 Is Pressed", "Turns Vertically From Obstacle", "Random Vertical Starting Direction", "Starting Direction Towards Player", "Climber", "Climber Type 2", "Runs Away From Player If Sees Player", "Reborn (B)", "Arrow Left", "Arrow Right", "Arrow Up", "Arrow Down", "Move to Arrows Direction", "Background Sprite Moon", "Background Sprite Moves to Left", "Background Sprite Moves to Right", "Add Time to Clock", "Make Player Invisible", "Follow the Player Vertic. and Horiz.", "Follow the Player Vertic. and Horiz., if Player is in Front", "Random Move Vertic. and Horiz.", "Frog Jump 1", "Frog Jump 2", "Frog Jump 3", "Attack 2 if Damaged", "Attack 1 Non Stop", "Attack 2 Non Stop", "Turn if Damaged", "Evil One", "Info 1", "Info 2", "Info 3", "Info 4", "Info 5", "Info 6", "Info 7", "Info 8", "Info 9", "Info 10", "Info 11", "Info 12", "Info 13", "Info 14", "Info 15", "Info 16", "Info 17", "Info 18", "Info 19"}));
		comboBoxAi3.setEditable(true);
		comboBoxAi3.setBounds(44, 259, 287, 20);
		panel_5.add(comboBoxAi3);
		
		comboBoxAi4 = new JComboBox();
		comboBoxAi4.setModel(new DefaultComboBoxModel(new String[] {"No AI", "Chicken", "An Egg", "Baby Chicken", "Bonus (B)", "Jumper", "Basic AI (B)", "Turn horizontally", "Looks Out for Cliffs", "Random Horizontal Change of Direction", "Random Jumping", "Follow the Player", "Random Horizontal Change of Direction", "Follow the Player if Player is in Front", "Transformation If Energy Below 2 (P)", "Transformation If Energy above 1 (P)", "Start Directions Towards Player", "Ammonition", "Non Stop", "Attack 1 If Damaged", "Self Destruction", "Attack 1 If Player is in Front", "Attack 1 If Player is Below", "Damaged by Water (P)", "Attack 2 If Player is in Front", "Kill 'em all", "Affected by Friction", "Hide", "Return to Start Position X", "Return to Start Position Y", "Teleport", "Throwable Weapon", "Falls When Shaken (B)", "Change Trap Stones If KO'ed", "Change Trap Stones If Damaged", "Self Destructs With Mother Sprite", "Moves X COS", "Moves Y COS", "Moves X SIN", "Moves Y SIN", "Moves X COS Fast", "Moves Y SIN Fast", "Moves X COS Slow", "Moves Y SIN Slow", "Moves Y SIN Free", "Random Turning", "Jump If Player Is Above", "Transformation Timer (B)", "Falls If Switch 1 Is Pressed (B)", "Falls If Switch 2 Is Pressed (B)", "Falls If Switch 3 Is Pressed (B)", "Moves Down If Switch 1 Is Pressed", "Moves Up If Switch 1 Is Pressed", "Moves Right If Switch 1 Is Pressed", "Moves Left If Switch 1 Is Pressed", "Moves Down If Switch 2 Is Pressed", "Moves Up If Switch 2 Is Pressed", "Moves Right If Switch 2 Is Pressed", "Moves Left If Switch 2 Is Pressed", "Moves Down If Switch 3 Is Pressed", "Moves Up If Switch 3 Is Pressed", "Moves Right If Switch 3 Is Pressed", "Moves Left If Switch 3 Is Pressed", "Turns Vertically From Obstacle", "Random Vertical Starting Direction", "Starting Direction Towards Player", "Climber", "Climber Type 2", "Runs Away From Player If Sees Player", "Reborn (B)", "Arrow Left", "Arrow Right", "Arrow Up", "Arrow Down", "Move to Arrows Direction", "Background Sprite Moon", "Background Sprite Moves to Left", "Background Sprite Moves to Right", "Add Time to Clock", "Make Player Invisible", "Follow the Player Vertic. and Horiz.", "Follow the Player Vertic. and Horiz., if Player is in Front", "Random Move Vertic. and Horiz.", "Frog Jump 1", "Frog Jump 2", "Frog Jump 3", "Attack 2 if Damaged", "Attack 1 Non Stop", "Attack 2 Non Stop", "Turn if Damaged", "Evil One", "Info 1", "Info 2", "Info 3", "Info 4", "Info 5", "Info 6", "Info 7", "Info 8", "Info 9", "Info 10", "Info 11", "Info 12", "Info 13", "Info 14", "Info 15", "Info 16", "Info 17", "Info 18", "Info 19"}));
		comboBoxAi4.setEditable(true);
		comboBoxAi4.setBounds(44, 290, 287, 20);
		panel_5.add(comboBoxAi4);
		
		comboBoxAi5 = new JComboBox();
		comboBoxAi5.setModel(new DefaultComboBoxModel(new String[] {"No AI", "Chicken", "An Egg", "Baby Chicken", "Bonus (B)", "Jumper", "Basic AI (B)", "Turn horizontally", "Looks Out for Cliffs", "Random Horizontal Change of Direction", "Random Jumping", "Follow the Player", "Random Horizontal Change of Direction", "Follow the Player if Player is in Front", "Transformation If Energy Below 2 (P)", "Transformation If Energy above 1 (P)", "Start Directions Towards Player", "Ammonition", "Non Stop", "Attack 1 If Damaged", "Self Destruction", "Attack 1 If Player is in Front", "Attack 1 If Player is Below", "Damaged by Water (P)", "Attack 2 If Player is in Front", "Kill 'em all", "Affected by Friction", "Hide", "Return to Start Position X", "Return to Start Position Y", "Teleport", "Throwable Weapon", "Falls When Shaken (B)", "Change Trap Stones If KO'ed", "Change Trap Stones If Damaged", "Self Destructs With Mother Sprite", "Moves X COS", "Moves Y COS", "Moves X SIN", "Moves Y SIN", "Moves X COS Fast", "Moves Y SIN Fast", "Moves X COS Slow", "Moves Y SIN Slow", "Moves Y SIN Free", "Random Turning", "Jump If Player Is Above", "Transformation Timer (B)", "Falls If Switch 1 Is Pressed (B)", "Falls If Switch 2 Is Pressed (B)", "Falls If Switch 3 Is Pressed (B)", "Moves Down If Switch 1 Is Pressed", "Moves Up If Switch 1 Is Pressed", "Moves Right If Switch 1 Is Pressed", "Moves Left If Switch 1 Is Pressed", "Moves Down If Switch 2 Is Pressed", "Moves Up If Switch 2 Is Pressed", "Moves Right If Switch 2 Is Pressed", "Moves Left If Switch 2 Is Pressed", "Moves Down If Switch 3 Is Pressed", "Moves Up If Switch 3 Is Pressed", "Moves Right If Switch 3 Is Pressed", "Moves Left If Switch 3 Is Pressed", "Turns Vertically From Obstacle", "Random Vertical Starting Direction", "Starting Direction Towards Player", "Climber", "Climber Type 2", "Runs Away From Player If Sees Player", "Reborn (B)", "Arrow Left", "Arrow Right", "Arrow Up", "Arrow Down", "Move to Arrows Direction", "Background Sprite Moon", "Background Sprite Moves to Left", "Background Sprite Moves to Right", "Add Time to Clock", "Make Player Invisible", "Follow the Player Vertic. and Horiz.", "Follow the Player Vertic. and Horiz., if Player is in Front", "Random Move Vertic. and Horiz.", "Frog Jump 1", "Frog Jump 2", "Frog Jump 3", "Attack 2 if Damaged", "Attack 1 Non Stop", "Attack 2 Non Stop", "Turn if Damaged", "Evil One", "Info 1", "Info 2", "Info 3", "Info 4", "Info 5", "Info 6", "Info 7", "Info 8", "Info 9", "Info 10", "Info 11", "Info 12", "Info 13", "Info 14", "Info 15", "Info 16", "Info 17", "Info 18", "Info 19"}));
		comboBoxAi5.setEditable(true);
		comboBoxAi5.setBounds(44, 321, 287, 20);
		panel_5.add(comboBoxAi5);
		
		comboBoxAi10 = new JComboBox();
		comboBoxAi10.setModel(new DefaultComboBoxModel(new String[] {"No AI", "Chicken", "An Egg", "Baby Chicken", "Bonus (B)", "Jumper", "Basic AI (B)", "Turn horizontally", "Looks Out for Cliffs", "Random Horizontal Change of Direction", "Random Jumping", "Follow the Player", "Random Horizontal Change of Direction", "Follow the Player if Player is in Front", "Transformation If Energy Below 2 (P)", "Transformation If Energy above 1 (P)", "Start Directions Towards Player", "Ammonition", "Non Stop", "Attack 1 If Damaged", "Self Destruction", "Attack 1 If Player is in Front", "Attack 1 If Player is Below", "Damaged by Water (P)", "Attack 2 If Player is in Front", "Kill 'em all", "Affected by Friction", "Hide", "Return to Start Position X", "Return to Start Position Y", "Teleport", "Throwable Weapon", "Falls When Shaken (B)", "Change Trap Stones If KO'ed", "Change Trap Stones If Damaged", "Self Destructs With Mother Sprite", "Moves X COS", "Moves Y COS", "Moves X SIN", "Moves Y SIN", "Moves X COS Fast", "Moves Y SIN Fast", "Moves X COS Slow", "Moves Y SIN Slow", "Moves Y SIN Free", "Random Turning", "Jump If Player Is Above", "Transformation Timer (B)", "Falls If Switch 1 Is Pressed (B)", "Falls If Switch 2 Is Pressed (B)", "Falls If Switch 3 Is Pressed (B)", "Moves Down If Switch 1 Is Pressed", "Moves Up If Switch 1 Is Pressed", "Moves Right If Switch 1 Is Pressed", "Moves Left If Switch 1 Is Pressed", "Moves Down If Switch 2 Is Pressed", "Moves Up If Switch 2 Is Pressed", "Moves Right If Switch 2 Is Pressed", "Moves Left If Switch 2 Is Pressed", "Moves Down If Switch 3 Is Pressed", "Moves Up If Switch 3 Is Pressed", "Moves Right If Switch 3 Is Pressed", "Moves Left If Switch 3 Is Pressed", "Turns Vertically From Obstacle", "Random Vertical Starting Direction", "Starting Direction Towards Player", "Climber", "Climber Type 2", "Runs Away From Player If Sees Player", "Reborn (B)", "Arrow Left", "Arrow Right", "Arrow Up", "Arrow Down", "Move to Arrows Direction", "Background Sprite Moon", "Background Sprite Moves to Left", "Background Sprite Moves to Right", "Add Time to Clock", "Make Player Invisible", "Follow the Player Vertic. and Horiz.", "Follow the Player Vertic. and Horiz., if Player is in Front", "Random Move Vertic. and Horiz.", "Frog Jump 1", "Frog Jump 2", "Frog Jump 3", "Attack 2 if Damaged", "Attack 1 Non Stop", "Attack 2 Non Stop", "Turn if Damaged", "Evil One", "Info 1", "Info 2", "Info 3", "Info 4", "Info 5", "Info 6", "Info 7", "Info 8", "Info 9", "Info 10", "Info 11", "Info 12", "Info 13", "Info 14", "Info 15", "Info 16", "Info 17", "Info 18", "Info 19"}));
		comboBoxAi10.setEditable(true);
		comboBoxAi10.setBounds(381, 321, 287, 20);
		panel_5.add(comboBoxAi10);
		
		comboBoxAi9 = new JComboBox();
		comboBoxAi9.setModel(new DefaultComboBoxModel(new String[] {"No AI", "Chicken", "An Egg", "Baby Chicken", "Bonus (B)", "Jumper", "Basic AI (B)", "Turn horizontally", "Looks Out for Cliffs", "Random Horizontal Change of Direction", "Random Jumping", "Follow the Player", "Random Horizontal Change of Direction", "Follow the Player if Player is in Front", "Transformation If Energy Below 2 (P)", "Transformation If Energy above 1 (P)", "Start Directions Towards Player", "Ammonition", "Non Stop", "Attack 1 If Damaged", "Self Destruction", "Attack 1 If Player is in Front", "Attack 1 If Player is Below", "Damaged by Water (P)", "Attack 2 If Player is in Front", "Kill 'em all", "Affected by Friction", "Hide", "Return to Start Position X", "Return to Start Position Y", "Teleport", "Throwable Weapon", "Falls When Shaken (B)", "Change Trap Stones If KO'ed", "Change Trap Stones If Damaged", "Self Destructs With Mother Sprite", "Moves X COS", "Moves Y COS", "Moves X SIN", "Moves Y SIN", "Moves X COS Fast", "Moves Y SIN Fast", "Moves X COS Slow", "Moves Y SIN Slow", "Moves Y SIN Free", "Random Turning", "Jump If Player Is Above", "Transformation Timer (B)", "Falls If Switch 1 Is Pressed (B)", "Falls If Switch 2 Is Pressed (B)", "Falls If Switch 3 Is Pressed (B)", "Moves Down If Switch 1 Is Pressed", "Moves Up If Switch 1 Is Pressed", "Moves Right If Switch 1 Is Pressed", "Moves Left If Switch 1 Is Pressed", "Moves Down If Switch 2 Is Pressed", "Moves Up If Switch 2 Is Pressed", "Moves Right If Switch 2 Is Pressed", "Moves Left If Switch 2 Is Pressed", "Moves Down If Switch 3 Is Pressed", "Moves Up If Switch 3 Is Pressed", "Moves Right If Switch 3 Is Pressed", "Moves Left If Switch 3 Is Pressed", "Turns Vertically From Obstacle", "Random Vertical Starting Direction", "Starting Direction Towards Player", "Climber", "Climber Type 2", "Runs Away From Player If Sees Player", "Reborn (B)", "Arrow Left", "Arrow Right", "Arrow Up", "Arrow Down", "Move to Arrows Direction", "Background Sprite Moon", "Background Sprite Moves to Left", "Background Sprite Moves to Right", "Add Time to Clock", "Make Player Invisible", "Follow the Player Vertic. and Horiz.", "Follow the Player Vertic. and Horiz., if Player is in Front", "Random Move Vertic. and Horiz.", "Frog Jump 1", "Frog Jump 2", "Frog Jump 3", "Attack 2 if Damaged", "Attack 1 Non Stop", "Attack 2 Non Stop", "Turn if Damaged", "Evil One", "Info 1", "Info 2", "Info 3", "Info 4", "Info 5", "Info 6", "Info 7", "Info 8", "Info 9", "Info 10", "Info 11", "Info 12", "Info 13", "Info 14", "Info 15", "Info 16", "Info 17", "Info 18", "Info 19"}));
		comboBoxAi9.setEditable(true);
		comboBoxAi9.setBounds(381, 290, 287, 20);
		panel_5.add(comboBoxAi9);
		
		comboBoxAi8 = new JComboBox();
		comboBoxAi8.setModel(new DefaultComboBoxModel(new String[] {"No AI", "Chicken", "An Egg", "Baby Chicken", "Bonus (B)", "Jumper", "Basic AI (B)", "Turn horizontally", "Looks Out for Cliffs", "Random Horizontal Change of Direction", "Random Jumping", "Follow the Player", "Random Horizontal Change of Direction", "Follow the Player if Player is in Front", "Transformation If Energy Below 2 (P)", "Transformation If Energy above 1 (P)", "Start Directions Towards Player", "Ammonition", "Non Stop", "Attack 1 If Damaged", "Self Destruction", "Attack 1 If Player is in Front", "Attack 1 If Player is Below", "Damaged by Water (P)", "Attack 2 If Player is in Front", "Kill 'em all", "Affected by Friction", "Hide", "Return to Start Position X", "Return to Start Position Y", "Teleport", "Throwable Weapon", "Falls When Shaken (B)", "Change Trap Stones If KO'ed", "Change Trap Stones If Damaged", "Self Destructs With Mother Sprite", "Moves X COS", "Moves Y COS", "Moves X SIN", "Moves Y SIN", "Moves X COS Fast", "Moves Y SIN Fast", "Moves X COS Slow", "Moves Y SIN Slow", "Moves Y SIN Free", "Random Turning", "Jump If Player Is Above", "Transformation Timer (B)", "Falls If Switch 1 Is Pressed (B)", "Falls If Switch 2 Is Pressed (B)", "Falls If Switch 3 Is Pressed (B)", "Moves Down If Switch 1 Is Pressed", "Moves Up If Switch 1 Is Pressed", "Moves Right If Switch 1 Is Pressed", "Moves Left If Switch 1 Is Pressed", "Moves Down If Switch 2 Is Pressed", "Moves Up If Switch 2 Is Pressed", "Moves Right If Switch 2 Is Pressed", "Moves Left If Switch 2 Is Pressed", "Moves Down If Switch 3 Is Pressed", "Moves Up If Switch 3 Is Pressed", "Moves Right If Switch 3 Is Pressed", "Moves Left If Switch 3 Is Pressed", "Turns Vertically From Obstacle", "Random Vertical Starting Direction", "Starting Direction Towards Player", "Climber", "Climber Type 2", "Runs Away From Player If Sees Player", "Reborn (B)", "Arrow Left", "Arrow Right", "Arrow Up", "Arrow Down", "Move to Arrows Direction", "Background Sprite Moon", "Background Sprite Moves to Left", "Background Sprite Moves to Right", "Add Time to Clock", "Make Player Invisible", "Follow the Player Vertic. and Horiz.", "Follow the Player Vertic. and Horiz., if Player is in Front", "Random Move Vertic. and Horiz.", "Frog Jump 1", "Frog Jump 2", "Frog Jump 3", "Attack 2 if Damaged", "Attack 1 Non Stop", "Attack 2 Non Stop", "Turn if Damaged", "Evil One", "Info 1", "Info 2", "Info 3", "Info 4", "Info 5", "Info 6", "Info 7", "Info 8", "Info 9", "Info 10", "Info 11", "Info 12", "Info 13", "Info 14", "Info 15", "Info 16", "Info 17", "Info 18", "Info 19"}));
		comboBoxAi8.setEditable(true);
		comboBoxAi8.setBounds(381, 259, 287, 20);
		panel_5.add(comboBoxAi8);
		
		comboBoxAi7 = new JComboBox();
		comboBoxAi7.setModel(new DefaultComboBoxModel(new String[] {"No AI", "Chicken", "An Egg", "Baby Chicken", "Bonus (B)", "Jumper", "Basic AI (B)", "Turn horizontally", "Looks Out for Cliffs", "Random Horizontal Change of Direction", "Random Jumping", "Follow the Player", "Random Horizontal Change of Direction", "Follow the Player if Player is in Front", "Transformation If Energy Below 2 (P)", "Transformation If Energy above 1 (P)", "Start Directions Towards Player", "Ammonition", "Non Stop", "Attack 1 If Damaged", "Self Destruction", "Attack 1 If Player is in Front", "Attack 1 If Player is Below", "Damaged by Water (P)", "Attack 2 If Player is in Front", "Kill 'em all", "Affected by Friction", "Hide", "Return to Start Position X", "Return to Start Position Y", "Teleport", "Throwable Weapon", "Falls When Shaken (B)", "Change Trap Stones If KO'ed", "Change Trap Stones If Damaged", "Self Destructs With Mother Sprite", "Moves X COS", "Moves Y COS", "Moves X SIN", "Moves Y SIN", "Moves X COS Fast", "Moves Y SIN Fast", "Moves X COS Slow", "Moves Y SIN Slow", "Moves Y SIN Free", "Random Turning", "Jump If Player Is Above", "Transformation Timer (B)", "Falls If Switch 1 Is Pressed (B)", "Falls If Switch 2 Is Pressed (B)", "Falls If Switch 3 Is Pressed (B)", "Moves Down If Switch 1 Is Pressed", "Moves Up If Switch 1 Is Pressed", "Moves Right If Switch 1 Is Pressed", "Moves Left If Switch 1 Is Pressed", "Moves Down If Switch 2 Is Pressed", "Moves Up If Switch 2 Is Pressed", "Moves Right If Switch 2 Is Pressed", "Moves Left If Switch 2 Is Pressed", "Moves Down If Switch 3 Is Pressed", "Moves Up If Switch 3 Is Pressed", "Moves Right If Switch 3 Is Pressed", "Moves Left If Switch 3 Is Pressed", "Turns Vertically From Obstacle", "Random Vertical Starting Direction", "Starting Direction Towards Player", "Climber", "Climber Type 2", "Runs Away From Player If Sees Player", "Reborn (B)", "Arrow Left", "Arrow Right", "Arrow Up", "Arrow Down", "Move to Arrows Direction", "Background Sprite Moon", "Background Sprite Moves to Left", "Background Sprite Moves to Right", "Add Time to Clock", "Make Player Invisible", "Follow the Player Vertic. and Horiz.", "Follow the Player Vertic. and Horiz., if Player is in Front", "Random Move Vertic. and Horiz.", "Frog Jump 1", "Frog Jump 2", "Frog Jump 3", "Attack 2 if Damaged", "Attack 1 Non Stop", "Attack 2 Non Stop", "Turn if Damaged", "Evil One", "Info 1", "Info 2", "Info 3", "Info 4", "Info 5", "Info 6", "Info 7", "Info 8", "Info 9", "Info 10", "Info 11", "Info 12", "Info 13", "Info 14", "Info 15", "Info 16", "Info 17", "Info 18", "Info 19"}));
		comboBoxAi7.setEditable(true);
		comboBoxAi7.setBounds(381, 228, 287, 20);
		panel_5.add(comboBoxAi7);
		
		comboBoxAi6 = new JComboBox();
		comboBoxAi6.setModel(new DefaultComboBoxModel(new String[] {"No AI", "Chicken", "An Egg", "Baby Chicken", "Bonus (B)", "Jumper", "Basic AI (B)", "Turn horizontally", "Looks Out for Cliffs", "Random Horizontal Change of Direction", "Random Jumping", "Follow the Player", "Random Horizontal Change of Direction", "Follow the Player if Player is in Front", "Transformation If Energy Below 2 (P)", "Transformation If Energy above 1 (P)", "Start Directions Towards Player", "Ammonition", "Non Stop", "Attack 1 If Damaged", "Self Destruction", "Attack 1 If Player is in Front", "Attack 1 If Player is Below", "Damaged by Water (P)", "Attack 2 If Player is in Front", "Kill 'em all", "Affected by Friction", "Hide", "Return to Start Position X", "Return to Start Position Y", "Teleport", "Throwable Weapon", "Falls When Shaken (B)", "Change Trap Stones If KO'ed", "Change Trap Stones If Damaged", "Self Destructs With Mother Sprite", "Moves X COS", "Moves Y COS", "Moves X SIN", "Moves Y SIN", "Moves X COS Fast", "Moves Y SIN Fast", "Moves X COS Slow", "Moves Y SIN Slow", "Moves Y SIN Free", "Random Turning", "Jump If Player Is Above", "Transformation Timer (B)", "Falls If Switch 1 Is Pressed (B)", "Falls If Switch 2 Is Pressed (B)", "Falls If Switch 3 Is Pressed (B)", "Moves Down If Switch 1 Is Pressed", "Moves Up If Switch 1 Is Pressed", "Moves Right If Switch 1 Is Pressed", "Moves Left If Switch 1 Is Pressed", "Moves Down If Switch 2 Is Pressed", "Moves Up If Switch 2 Is Pressed", "Moves Right If Switch 2 Is Pressed", "Moves Left If Switch 2 Is Pressed", "Moves Down If Switch 3 Is Pressed", "Moves Up If Switch 3 Is Pressed", "Moves Right If Switch 3 Is Pressed", "Moves Left If Switch 3 Is Pressed", "Turns Vertically From Obstacle", "Random Vertical Starting Direction", "Starting Direction Towards Player", "Climber", "Climber Type 2", "Runs Away From Player If Sees Player", "Reborn (B)", "Arrow Left", "Arrow Right", "Arrow Up", "Arrow Down", "Move to Arrows Direction", "Background Sprite Moon", "Background Sprite Moves to Left", "Background Sprite Moves to Right", "Add Time to Clock", "Make Player Invisible", "Follow the Player Vertic. and Horiz.", "Follow the Player Vertic. and Horiz., if Player is in Front", "Random Move Vertic. and Horiz.", "Frog Jump 1", "Frog Jump 2", "Frog Jump 3", "Attack 2 if Damaged", "Attack 1 Non Stop", "Attack 2 Non Stop", "Turn if Damaged", "Evil One", "Info 1", "Info 2", "Info 3", "Info 4", "Info 5", "Info 6", "Info 7", "Info 8", "Info 9", "Info 10", "Info 11", "Info 12", "Info 13", "Info 14", "Info 15", "Info 16", "Info 17", "Info 18", "Info 19"}));
		comboBoxAi6.setEditable(true);
		comboBoxAi6.setBounds(381, 197, 287, 20);
		panel_5.add(comboBoxAi6);
		
		JLabel label = new JLabel("1:");
		label.setBounds(21, 200, 22, 14);
		panel_5.add(label);
		
		JLabel label_1 = new JLabel("2:");
		label_1.setBounds(21, 231, 22, 14);
		panel_5.add(label_1);
		
		JLabel label_2 = new JLabel("3:");
		label_2.setBounds(21, 259, 22, 14);
		panel_5.add(label_2);
		
		JLabel label_3 = new JLabel("4:");
		label_3.setBounds(21, 293, 22, 14);
		panel_5.add(label_3);
		
		JLabel label_4 = new JLabel("5:");
		label_4.setBounds(21, 324, 22, 14);
		panel_5.add(label_4);
		
		JLabel label_5 = new JLabel("6:");
		label_5.setBounds(349, 200, 22, 14);
		panel_5.add(label_5);
		
		JLabel label_6 = new JLabel("7:");
		label_6.setBounds(349, 231, 22, 14);
		panel_5.add(label_6);
		
		JLabel label_7 = new JLabel("8:");
		label_7.setBounds(349, 262, 22, 14);
		panel_5.add(label_7);
		
		JLabel label_8 = new JLabel("9:");
		label_8.setBounds(349, 293, 22, 14);
		panel_5.add(label_8);
		
		JLabel label_9 = new JLabel("10:");
		label_9.setBounds(349, 324, 22, 14);
		panel_5.add(label_9);
		
		JLabel lblBonusSprite = new JLabel("Bonus Sprite:");
		lblBonusSprite.setBounds(10, 84, 82, 14);
		panel_5.add(lblBonusSprite);
		
		textFieldBonusSprite = new JTextField();
		textFieldBonusSprite.setBounds(122, 80, 209, 20);
		panel_5.add(textFieldBonusSprite);
		textFieldBonusSprite.setColumns(10);
		
		txtfd.add(textFieldAniStill);
		txtfd.add(textFieldAniWalk);
		txtfd.add(textFieldAniDuck);
		txtfd.add(textFieldAniJumpUp);
		txtfd.add(textFieldAniJumpDown);
		txtfd.add(textFieldAniDmg);
		txtfd.add(textFieldAniKnockOut);
		txtfd.add(textFieldAniAtk1);
		txtfd.add(textFieldAniAtk2);
		
		chbx.add(chckbxStillLoop);
		chbx.add(chckbxWalkingLoop);
		chbx.add(chckbxDuckLoop);
		chbx.add(chckbxJumpUpLoop);
		chbx.add(chckbxJumpDownLoop);
		chbx.add(chckbxDmgLoop);
		chbx.add(chckbxKnockOutLoop);
		chbx.add(chckbxAtk1Loop);
		chbx.add(chckbxAtk2Loop);
		
		JButton button_8 = new JButton("Browse");
		button_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				
				fc.setDialogTitle("Open sprite file...");
				
				if (lastSpritePath != null) {
					fc.setCurrentDirectory(lastSpritePath);
				} else if (new File(Settings.BASE_PATH).exists()) {
					fc.setCurrentDirectory(new File(Settings.SPRITE_PATH));
				}
				
				fc.setFileFilter(new FileFilter() {

					@Override
					public boolean accept(File f) {
						return f.isDirectory() || f.getName().endsWith("spr");
					}

					@Override
					public String getDescription() {
						return "Pekka Kana 2 Sprite file (*.spr)";
					}
					
				});
				
				int res = fc.showOpenDialog(null);
				
				if (res == JFileChooser.APPROVE_OPTION) {
					if (fc.getSelectedFile().getName().length() < 100) {
						textFieldBonusSprite.setText(fc.getSelectedFile().getParentFile().getName() + "\\" + fc.getSelectedFile().getName());
						
						lastSpritePath = fc.getSelectedFile().getParentFile();
					} else {
						JOptionPane.showMessageDialog(null, "Sprite file name, including '.spr', can't exceed 100 characters.", "File name too long", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		button_8.setBounds(337, 78, 89, 23);
		panel_5.add(button_8);
		
		JLabel lblTransformValue = new JLabel("Transform value:");
		lblTransformValue.setBounds(157, 119, 82, 14);
		panel_5.add(lblTransformValue);
		
		spinner_4 = new JSpinner();
		spinner_4.setBounds(249, 116, 52, 20);
		panel_5.add(spinner_4);
		
		panel_7 = new JPanel();
		tabbedPane.addTab("More", null, panel_7, null);
		panel_7.setLayout(null);
		
		JLabel lblMessage = new JLabel("Message:");
		lblMessage.setBounds(37, 11, 46, 14);
		panel_7.add(lblMessage);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(37, 36, 600, 183);
		panel_7.add(scrollPane);
		
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		
		JLabel lblDurationframes = new JLabel("Duration:");
		lblDurationframes.setBounds(37, 235, 46, 14);
		panel_7.add(lblDurationframes);
		
		spinner_5 = new JSpinner();
		spinner_5.setModel(new SpinnerNumberModel(new Integer(700), new Integer(0), null, new Integer(1)));
		spinner_5.setBounds(92, 232, 64, 20);
		panel_7.add(spinner_5);
		
		JLabel lblShowMessageWhen = new JLabel("Show message when:");
		lblShowMessageWhen.setBounds(37, 260, 119, 14);
		panel_7.add(lblShowMessageWhen);
		
		chckbxCollidingWithPlayer = new JCheckBox("Colliding with player");
		chckbxCollidingWithPlayer.setBounds(37, 282, 131, 23);
		panel_7.add(chckbxCollidingWithPlayer);
		
		chckbxShotByPlayer = new JCheckBox("Shot by player");
		chckbxShotByPlayer.setBounds(37, 307, 97, 23);
		panel_7.add(chckbxShotByPlayer);
		
		JPanel panel_6 = new JPanel();
		contentPane.add(panel_6, BorderLayout.SOUTH);
		panel_6.setLayout(new BorderLayout(0, 0));
		
		lblStatus = new JLabel("Created a new file!");
		panel_6.add(lblStatus);
		
		if (Settings.use14) {
			menuItem_1.setSelected(true);
		} else {
			menuItem.setSelected(true);
		}
		
		Image img = null;
		try {
			img = ImageIO.read(getClass().getResource("/pekase.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		setIconImage(img);
		
		newFile();
	}
	
	private void loadFile() {
		spriteFile = new PK2Sprite(currentFile);
		
		if (spriteFile.loadFile()) {
			setTitle("PekaSE - " + currentFile.getAbsolutePath());
			
			String pstr = "";
			File ft = new File(new File(spriteFile.ImageFileStr).getParent() + "\\" + cleanString(spriteFile.imageFile));

			if (ft.exists()) {
				pstr = new File(spriteFile.ImageFileStr).getParentFile().getName() + "\\" + cleanString(spriteFile.imageFile);
				
				rawFile = new File(Settings.BASE_PATH + "\\" + pstr);
			} else {
				pstr = "sprites\\" + cleanString(spriteFile.imageFile);
				
				rawFile = new File(Settings.BASE_PATH + "\\" + cleanString(spriteFile.imageFile));
			}

			textField.setText(pstr);
			txtFieldName.setText(spriteFile.getName());

			spinner.setValue(spriteFile.frameX);
			spinner_1.setValue(spriteFile.frameY);
			spinner_2.setValue(spriteFile.width);
			spinner_3.setValue(spriteFile.height);

			spinnerSprWidth.setValue(spriteFile.width);
			spinnerSprHeight.setValue(spriteFile.height);

			comboBoxType.setSelectedIndex(spriteFile.type - 1);

			int index = 0;

			for (int i = 0; i < colors.length; i++) {
				if (spriteFile.color == colors[i]) {
					index = i;

					break;
				}
			}

			comboBoxColor.setSelectedIndex(index);

			comboBoxDmg.setSelectedIndex(spriteFile.damageType);
			comboBoxImmunity.setSelectedIndex(spriteFile.immunity);

			chckbxObstacle.setSelected(spriteFile.obstacle);

			spinner.setValue(spriteFile.frameX);
			spinner_1.setValue(spriteFile.frameY);
			spinner_2.setValue(spriteFile.frameWidth);
			spinner_3.setValue(spriteFile.frameHeight);

			txtFieldName.setText(spriteFile.getName());

			String st = "";

			if (spriteFile.atkSprite1.length > 0 && spriteFile.atkSprite1[0] != 0) {
				if (new File(currentFile.getParentFile().getAbsolutePath() + "\\" + cleanString(spriteFile.atkSprite1)).exists()) {
					st = currentFile.getParentFile().getName() + "\\" + cleanString(spriteFile.atkSprite1);
				} else {
					st = "sprites\\" + cleanString(spriteFile.atkSprite1);
				}
			}

			textFieldAmmoSpr1.setText(st);

			st = "";

			if (spriteFile.atkSprite2.length > 0 && spriteFile.atkSprite2[0] != 0) {
				if (new File(currentFile.getParentFile().getAbsolutePath() + "\\" + cleanString(spriteFile.atkSprite2)).exists()) {
					st = currentFile.getParentFile().getName() + "\\" + cleanString(spriteFile.atkSprite2);
				} else {
					st = "sprites\\" + cleanString(spriteFile.atkSprite2);
				}
			}

			textFieldAmmoSpr2.setText(st);

			comboBoxType.setSelectedIndex(spriteFile.type - 1);

			if (spriteFile.color == 255) {
				comboBoxColor.setSelectedIndex(0);
			} else {
				for (int i = 0; i < colors.length; i++) {
					if (colors[i] == spriteFile.color) {
						comboBoxColor.setSelectedIndex(i);

						break;
					}
				}
			}

			comboBoxDmg.setSelectedIndex(spriteFile.damageType);
			comboBoxImmunity.setSelectedIndex(spriteFile.immunity);

			spinnerWeight.setValue((double) spriteFile.weight);
			spinnerEnergy.setValue(spriteFile.energy);
			spinnerDmg.setValue(spriteFile.damage);
			spinnerScore.setValue(spriteFile.score);

			spinnerMaxSpeed.setValue((double) spriteFile.maxSpeed);
			spinnerMaxJump.setValue(spriteFile.maxJump);

			spinnerLoadDur.setValue(spriteFile.loadingTime);

			spinnerAtkPause.setValue(spriteFile.atkPause);

			spinnerFrameRate.setValue(spriteFile.frameRate);
			spinnerFrames.setValue(spriteFile.frames);
			spinnerAnimations.setValue(spriteFile.animations);

			spinnerAtk1Dur.setValue(spriteFile.attack1Duration);
			spinnerAtk2Dur.setValue(spriteFile.attack2Duration);

			chckbxEnemy.setSelected(spriteFile.enemy);
			chckbxBoss.setSelected(spriteFile.boss);
			chckbxKey.setSelected(spriteFile.key);
			chckbxObstacle.setSelected(spriteFile.obstacle);

			ArrayList<JTextField> sndList = new ArrayList<JTextField>();
			sndList.add(textFieldSndDmg);
			sndList.add(textFieldSndKnockOut);
			sndList.add(textFieldSndAtk1);
			sndList.add(textFieldSndAtk2);
			sndList.add(textFieldAtkRnd);

			st = "";

			for (int i = 0; i < sndList.size(); i++) {
				if (spriteFile.soundFiles[i][0] != 0) {
					if (new File(currentFile.getParentFile().getAbsolutePath() + "\\" + cleanString(spriteFile.soundFiles[i])).exists()) {
						st = currentFile.getParentFile().getName() + "\\" + cleanString(spriteFile.soundFiles[i]);
					} else {
						st = "sprites\\" + cleanString(spriteFile.soundFiles[i]);
					}

					sndList.get(i).setText(st);
				}
			}

			st = "";

			String stillStr = "";

			String str = "";
			for (int i = 0; i < 9; i++) {
				str = "";

				for (int j = 0; j < spriteFile.animation[i].sequence.length; j++) {
					str += spriteFile.animation[i].sequence[j] + ",";
				}

				txtfd.get(i).setText(str.substring(0, str.length() - 1));

				chbx.get(i).setSelected(spriteFile.animation[i].loop);
			}

			st = "";

			if (spriteFile.transformationSprite.length > 0 && spriteFile.transformationSprite[0] != 0) {
				if (new File(currentFile.getParentFile().getAbsolutePath() + "\\" + cleanString(spriteFile.transformationSprite)).exists()) {
					st = currentFile.getParentFile().getName() + "\\" + cleanString(spriteFile.transformationSprite);
				} else {
					st = "sprites\\" + cleanString(spriteFile.transformationSprite);
				}
			}

			textFieldTransformSprite.setText(st);

			st = "";

			if (spriteFile.bonusSprite.length > 0 && spriteFile.bonusSprite[0] != 0) {
				if (new File(currentFile.getParentFile().getAbsolutePath() + "\\" + cleanString(spriteFile.bonusSprite)).exists()) {
					st = currentFile.getParentFile().getName() + "\\" + cleanString(spriteFile.bonusSprite);
				} else {
					st = "sprites\\" + cleanString(spriteFile.bonusSprite);
				}
			}

			textFieldBonusSprite.setText(st);

			spinnerSndFrq.setValue(spriteFile.soundFrequency);
			chckbxRandomFrequency.setSelected(spriteFile.randomFrequency);

			chckbxGlide.setSelected(spriteFile.glide);
			chckbxShakes.setSelected(spriteFile.shakes);
			chckbxSwim.setSelected(spriteFile.swim);
			chckbxTileCheck.setSelected(spriteFile.tileCheck);
			chckbxWallDown.setSelected(spriteFile.wallDown);
			chckbxWallUp.setSelected(spriteFile.wallUp);
			chckbxWallLeft.setSelected(spriteFile.wallLeft);
			chckbxWallRight.setSelected(spriteFile.wallRight);
			chckbxAlwaysBonus.setSelected(spriteFile.bonusAlways);

			spinnerParaFactor.setValue(spriteFile.parallaxFactor);
			spinnerBonus.setValue(spriteFile.bonuses);

			ArrayList<JComboBox> cbAni = new ArrayList<JComboBox>();
			cbAni.add(comboBoxAi1);
			cbAni.add(comboBoxAi2);
			cbAni.add(comboBoxAi3);
			cbAni.add(comboBoxAi4);
			cbAni.add(comboBoxAi5);
			cbAni.add(comboBoxAi6);
			cbAni.add(comboBoxAi7);
			cbAni.add(comboBoxAi8);
			cbAni.add(comboBoxAi9);
			cbAni.add(comboBoxAi10);

			for (int i = 0; i < spriteFile.AI.length; i++) {
				if (spriteFile.AI[i] <= 30) {
					cbAni.get(i).setSelectedIndex(spriteFile.AI[i]);
				} else if (spriteFile.AI[i] >= 35 && spriteFile.AI[i] <= 40) { 
					cbAni.get(i).setSelectedIndex(spriteFile.AI[i] - 4);
				} else if (spriteFile.AI[i] >= 41 && spriteFile.AI[i] <= 67) { 
					cbAni.get(i).setSelectedIndex(spriteFile.AI[i] - 4);
				} else if (spriteFile.AI[i] >= 70 && spriteFile.AI[i] <= 81) {
					cbAni.get(i).setSelectedIndex(spriteFile.AI[i] - 7);
				} else if (spriteFile.AI[i] == 101) {
					cbAni.get(i).setSelectedIndex(75);
				} else if (spriteFile.AI[i] == 102) {
					cbAni.get(i).setSelectedIndex(76);
				} else if (spriteFile.AI[i] == 103) {
					cbAni.get(i).setSelectedIndex(77);
				} else if (spriteFile.AI[i] == 120) {
					cbAni.get(i).setSelectedIndex(78);
				} else if (spriteFile.AI[i] == 121) {
					cbAni.get(i).setSelectedIndex(79);
				} else if (spriteFile.AI[i] == 130) {
					cbAni.get(i).setSelectedIndex(80);
				} else if (spriteFile.AI[i] == 131) {
					cbAni.get(i).setSelectedIndex(81);
				} else if (spriteFile.AI[i] == 132) {
					cbAni.get(i).setSelectedIndex(82);
				} else if (spriteFile.AI[i] == 133) {
					cbAni.get(i).setSelectedIndex(83);
				} else if (spriteFile.AI[i] == 134) {
					cbAni.get(i).setSelectedIndex(84);
				} else if (spriteFile.AI[i] == 135) {
					cbAni.get(i).setSelectedIndex(85);
				} else if (spriteFile.AI[i] == 136) {
					cbAni.get(i).setSelectedIndex(86);
				} else if (spriteFile.AI[i] == 137) {
					cbAni.get(i).setSelectedIndex(87);
				} else if (spriteFile.AI[i] == 138) {
					cbAni.get(i).setSelectedIndex(88);
				} else if (spriteFile.AI[i] == 139) {
					cbAni.get(i).setSelectedIndex(89);
				} else if (spriteFile.AI[i] == 140) {
					cbAni.get(i).setSelectedIndex(90);
				} else if (spriteFile.AI[i] == 201) {
					cbAni.get(i).setSelectedIndex(91);
				} else if (spriteFile.AI[i] == 202) {
					cbAni.get(i).setSelectedIndex(92);
				} else if (spriteFile.AI[i] == 203) {
					cbAni.get(i).setSelectedIndex(93);
				} else if (spriteFile.AI[i] == 204) {
					cbAni.get(i).setSelectedIndex(94);
				} else if (spriteFile.AI[i] == 205) {
					cbAni.get(i).setSelectedIndex(95);
				} else if (spriteFile.AI[i] == 206) {
					cbAni.get(i).setSelectedIndex(96);
				} else if (spriteFile.AI[i] == 207) {
					cbAni.get(i).setSelectedIndex(97);
				} else if (spriteFile.AI[i] == 208) {
					cbAni.get(i).setSelectedIndex(98);
				} else if (spriteFile.AI[i] == 209) {
					cbAni.get(i).setSelectedIndex(99);
				} else if (spriteFile.AI[i] == 210) {
					cbAni.get(i).setSelectedIndex(100);
				} else if (spriteFile.AI[i] == 211) {
					cbAni.get(i).setSelectedIndex(101);
				} else if (spriteFile.AI[i] == 212) {
					cbAni.get(i).setSelectedIndex(102);
				} else if (spriteFile.AI[i] == 213) {
					cbAni.get(i).setSelectedIndex(103);
				} else if (spriteFile.AI[i] == 214) {
					cbAni.get(i).setSelectedIndex(104);
				} else if (spriteFile.AI[i] == 215) {
					cbAni.get(i).setSelectedIndex(105);
				} else if (spriteFile.AI[i] == 216) {
					cbAni.get(i).setSelectedIndex(106);
				} else if (spriteFile.AI[i] == 217) {
					cbAni.get(i).setSelectedIndex(107);
				} else if (spriteFile.AI[i] == 218) {
					cbAni.get(i).setSelectedIndex(108);
				} else if (spriteFile.AI[i] == 219) {
					cbAni.get(i).setSelectedIndex(109);
				}
			}

			if (spriteFile.destruction >= 100) {
				comboBoxDestruction.setSelectedIndex(1);

				comboBoxDestruct2.setSelectedIndex(spriteFile.destruction - 100);
			} else {
				comboBoxDestruction.setSelectedIndex(0);
				comboBoxDestruct2.setSelectedIndex(spriteFile.destruction);
			}

			test = spriteFile.frameList[0];

			if (Settings.use14) {
				textArea.setText(spriteFile.message);
				
				spinner_4.setValue(spriteFile.transformationValue);
				spinner_5.setValue(spriteFile.message_duration);
				
				chckbxShotByPlayer.setSelected(spriteFile.showWhenShot);
				chckbxCollidingWithPlayer.setSelected(spriteFile.showOnCollision);
			}
			
			float fsprScaleHeight = (spriteFile.image.getHeight() / 130f) * 65;
			float fsprScaleWidth = (spriteFile.image.getWidth() / 130f) * 65;

			sprScaleHeight = (int) fsprScaleHeight;
			sprScaleWidth = (int) fsprScaleWidth;

			fsprScaleHeight = (spriteFile.image.getHeight() / 80f) * 40;
			fsprScaleWidth = (spriteFile.image.getWidth() / 80f) * 40;

			sprScaleHeight2 = (int) fsprScaleHeight;
			sprScaleWidth2 = (int) fsprScaleWidth;

			panel_4.repaint();
			
			cal = Calendar.getInstance();
			
			lblStatus.setText("Loaded file! (" + sdf.format(cal.getTime()) + ")");
		}
	}
	
	private void playSound(String file) {
		try {
			if (!new File(file).exists()) {
				if (file.split("\\\\")[0].equals("sprites")) {
					file = Settings.BASE_PATH + "\\" + file;
				} else {
					file = currentFile.getParentFile().getAbsolutePath() + "\\" + file.split("\\\\")[1];
				}
			}
			
			AudioInputStream stream = AudioSystem.getAudioInputStream(new File(file));
			
			DataLine.Info info = new DataLine.Info(Clip.class, stream.getFormat());
			
			Clip c = (Clip) AudioSystem.getLine(info);
			c.open(stream);
			c.start();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
			e1.printStackTrace();
		}
	}
	
	private String loadSoundFile() {
		JFileChooser fc = new JFileChooser();
		
		if (lastSoundPath != null) {
			fc.setCurrentDirectory(lastSoundPath);
		} else {
			fc.setCurrentDirectory(new File(Settings.BASE_PATH + File.separatorChar + "sprites"));
		}
		
		fc.setFileFilter(new FileFilter() {

			@Override
			public boolean accept(File e) {
				return e.isDirectory() || e.getName().endsWith("wav");
			}

			@Override
			public String getDescription() {
				return "Wav sound file (*.wav)";
			}
			
		});
		
		int res = fc.showOpenDialog(null);
		
		if (res == JFileChooser.APPROVE_OPTION) {
			lastSoundPath = fc.getSelectedFile().getParentFile();
			
			return fc.getSelectedFile().getParentFile().getName() + "\\" + fc.getSelectedFile().getName();
		}
		
		return "";
	}
	
	private boolean saveFile(File file) {
		spriteFile.imageFile = new char[100];
		
		String[] tmp1 = textField.getText().split("\\\\");
		String tmp2 = tmp1[tmp1.length - 1];
		for (int i = 0; i < tmp2.length(); i++) {
			spriteFile.imageFile[i] = tmp2.charAt(i);
		}
		
		try {
			spinner.commitEdit();
			spinner_1.commitEdit();
			spinner_2.commitEdit();
			spinner_3.commitEdit();
			spinnerSprWidth.commitEdit();
			spinnerSprHeight.commitEdit();
			spinnerWeight.commitEdit();
			spinnerEnergy.commitEdit();
			spinnerDmg.commitEdit();
			spinnerScore.commitEdit();
			spinnerLoadDur.commitEdit();
			spinnerMaxJump.commitEdit();
			spinnerAtkPause.commitEdit();
			spinnerMaxSpeed.commitEdit();
			spinnerAnimations.commitEdit();
			spinnerFrameRate.commitEdit();
			spinnerFrames.commitEdit();
			spinnerAtk1Dur.commitEdit();
			spinnerAtk2Dur.commitEdit();
			spinnerParaFactor.commitEdit();
			spinnerSndFrq.commitEdit();
			spinnerBonus.commitEdit();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		spriteFile.frameX = (int) spinner.getValue();
		spriteFile.frameY = (int) spinner_1.getValue();
		spriteFile.frameWidth = (int) spinner_2.getValue();
		spriteFile.frameHeight = (int) spinner_3.getValue();
		
		spriteFile.width = (int) spinnerSprWidth.getValue();
		spriteFile.height = (int) spinnerSprHeight.getValue();
		
		spriteFile.name = new char[32];
		
		int len = 32;
		
		if (txtFieldName.getText().length() < 32) {
			len = txtFieldName.getText().length();
		}
		
		for (int i = 0; i < len; i++) {
			spriteFile.name[i] = txtFieldName.getText().charAt(i);
		}
		
		spriteFile.atkSprite1 = new char[100];
		
		tmp1 = textFieldAmmoSpr1.getText().split("\\\\");
		tmp2 = tmp1[tmp1.length - 1];
		for (int i = 0; i < tmp2.length(); i++) {
			spriteFile.atkSprite1[i] = tmp2.charAt(i);
		}
		
		spriteFile.atkSprite2 = new char[100];
		
		tmp1 = textFieldAmmoSpr2.getText().split("\\\\");
		tmp2 = tmp1[tmp1.length - 1];
		for (int i = 0; i < tmp2.length(); i++) {
			spriteFile.atkSprite2[i] = tmp2.charAt(i);
		}
		
		spriteFile.type = comboBoxType.getSelectedIndex() + 1;
		
		if (comboBoxColor.getSelectedIndex() == 0) {
			spriteFile.color = 255;
		} else {
			spriteFile.color = colors[comboBoxColor.getSelectedIndex()];
		}
		
		spriteFile.damageType = comboBoxDmg.getSelectedIndex();
		spriteFile.immunity = comboBoxImmunity.getSelectedIndex();
		
		spriteFile.weight = (double) spinnerWeight.getValue();
		spriteFile.energy = (int) spinnerEnergy.getValue();
		spriteFile.damage = (int) spinnerDmg.getValue();
		spriteFile.score = (int) spinnerScore.getValue();
		
		spriteFile.maxSpeed = (double) spinnerMaxSpeed.getValue();
		spriteFile.maxJump = (int) spinnerMaxJump.getValue();
		
		spriteFile.loadingTime = (int) spinnerLoadDur.getValue();
		
		spriteFile.atkPause = (int) spinnerAtkPause.getValue();
		
		spriteFile.frameRate = (int) spinnerFrameRate.getValue();
		spriteFile.frames = (int) spinnerFrames.getValue();
		spriteFile.animations = (int) spinnerAnimations.getValue();
		
		spriteFile.attack1Duration = (int) spinnerAtk1Dur.getValue();
		spriteFile.attack2Duration = (int) spinnerAtk2Dur.getValue();
		
		spriteFile.obstacle = chckbxObstacle.isSelected();
		
		spriteFile.enemy = chckbxEnemy.isSelected();
		spriteFile.boss = chckbxBoss.isSelected();
		spriteFile.key = chckbxKey.isSelected();
		
		ArrayList<JTextField> txtSnd = new ArrayList<JTextField>();
		txtSnd.add(textFieldSndDmg);
		txtSnd.add(textFieldSndKnockOut);
		txtSnd.add(textFieldSndAtk1);
		txtSnd.add(textFieldSndAtk2);
		txtSnd.add(textFieldAtkRnd);
		
		for (int i = 0; i < spriteFile.soundFiles.length; i++) {
			for (int j = 0; j < spriteFile.soundFiles[0].length; j++) {
				spriteFile.soundFiles[i][j] = 0xCC;
			}
			
			spriteFile.soundFiles[i][0] = 0x0;
		}
		
		for (int i = 0; i < 5; i++) {
			if (i < txtSnd.size()) {
				tmp1 = txtSnd.get(i).getText().split("\\\\");
				tmp2 = tmp1[tmp1.length - 1];
				
				for (int j = 0; j < tmp2.length(); j++) {
					spriteFile.soundFiles[i][j] = tmp2.charAt(j);
				}
			}
			
			spriteFile.soundFiles[i][tmp2.length()] = 0x0;
		}
		
		// Hacky, but I just got fed up with this bullshit
		
		int fr = 0;
		String[] s;
		
		byte[] a = new byte[10];
		PK2SpriteAnimation[] b = new PK2SpriteAnimation[spriteFile.animation.length];
		
		for (int j = 0; j < 10; j++) {
			a[j] = 0;
		}
		
		for (int i = 0; i < b.length; i++) {
			b[i] = new PK2SpriteAnimation(a, 0, false);
		}
		
		for (int i = 0; i < txtfd.size(); i++) {
			s = txtfd.get(i).getText().split(",");
			
			for (int j = 0; j < 10; j++) {
				if (Byte.parseByte(s[j]) != 0) {
					a[j] = Byte.parseByte(s[j]);
					
					fr++;
				} else {
					a[j] = 0;
				}
			}
		
			b[i].sequence = Arrays.copyOf(a, 10);
			
			b[i].frames = fr;
			b[i].loop = chbx.get(i).isSelected();
			
			fr = 0;
		}
		
		spriteFile.animation = Arrays.copyOf(b, spriteFile.animation.length);
		
		spriteFile.transformationSprite = new char[100];
		
		tmp1 = textFieldTransformSprite.getText().split("\\\\");
		tmp2 = tmp1[tmp1.length - 1];
		for (int i = 0; i < tmp2.length(); i++) {
			spriteFile.transformationSprite[i]= tmp2.charAt(i);
		}
		
		spriteFile.bonusSprite = new char[100];
		
		tmp1 = textFieldBonusSprite.getText().split("\\\\");
		tmp2 = tmp1[tmp1.length - 1];
		for (int i = 0; i < tmp2.length(); i++) {
			spriteFile.bonusSprite[i]= tmp2.charAt(i);
		}
		
		spriteFile.soundFrequency = (int) spinnerSndFrq.getValue();
		spriteFile.randomFrequency = chckbxRandomFrequency.isSelected();
		
		spriteFile.glide = chckbxGlide.isSelected();
		spriteFile.shakes = chckbxShakes.isSelected();
		spriteFile.swim = chckbxSwim.isSelected();
		spriteFile.tileCheck = chckbxTileCheck.isSelected();
		spriteFile.wallDown = chckbxWallDown.isSelected();
		spriteFile.wallUp = chckbxWallUp.isSelected();
		spriteFile.wallLeft = chckbxWallLeft.isSelected();
		spriteFile.wallRight = chckbxWallRight.isSelected();
		spriteFile.bonusAlways = chckbxAlwaysBonus.isSelected();
		
		spriteFile.parallaxFactor = (int) spinnerParaFactor.getValue();
		spriteFile.bonuses = (int) spinnerBonus.getValue();
	
		ArrayList<JComboBox> cbAni = new ArrayList<JComboBox>();
		cbAni.add(comboBoxAi1);
		cbAni.add(comboBoxAi2);
		cbAni.add(comboBoxAi3);
		cbAni.add(comboBoxAi4);
		cbAni.add(comboBoxAi5);
		cbAni.add(comboBoxAi6);
		cbAni.add(comboBoxAi7);
		cbAni.add(comboBoxAi8);
		cbAni.add(comboBoxAi9);
		cbAni.add(comboBoxAi10);
		
		for (int i = 0; i < cbAni.size(); i++) {
			 int in = cbAni.get(i).getSelectedIndex();
			 
			 if (in <= 30) {
				 spriteFile.AI[i] = in;
			 } else if (in >= 31 && in <= 40) {
				 spriteFile.AI[i] = in + 4;
			 } else if (in >= 41 && in < 63) {
				 spriteFile.AI[i] = in + 5;
			 } else if (in >= 63 && in <= 75) {
				 spriteFile.AI[i] = in + 7;
			 }
			
			 // Why no switch?
			 
			 if (in == 75) {
				 spriteFile.AI[i] = 101;
			 } else if (in == 76) {
				 spriteFile.AI[i] = 102;
			 } else if (in == 77) {
				 spriteFile.AI[i] = 103;
			 } else if (in == 78) {
				 spriteFile.AI[i] = 120;
			 } else if (in == 79) {
				 spriteFile.AI[i] = 121;
			 } else if (in == 80) {
				 spriteFile.AI[i] = 130;
			 } else if (in == 81) {
				 spriteFile.AI[i] = 131;
			 } else if (in == 82) {
				 spriteFile.AI[i] = 132;
			 } else if (in == 83) {
				 spriteFile.AI[i] = 133;
			 } else if (in == 84) {
				 spriteFile.AI[i] = 134;
			 } else if (in == 85) {
				 spriteFile.AI[i] = 135;
			 } else if (in == 86) {
				 spriteFile.AI[i] = 136;
			 } else if (in == 87) {
				 spriteFile.AI[i] = 137;
			 } else if (in == 88) {
				 spriteFile.AI[i] = 138;
			 } else if (in == 89) {
				 spriteFile.AI[i] = 139;
			 } else if (in == 90) {
				 spriteFile.AI[i] = 140;
			 } else if (in == 91) {
				 spriteFile.AI[i] = 201;
			 } else if (in == 92) {
				 spriteFile.AI[i] = 202;
			 } else if (in == 93) {
				 spriteFile.AI[i] = 203;
			 } else if (in == 94) {
				 spriteFile.AI[i] = 204;
			 } else if (in == 95) {
				 spriteFile.AI[i] = 205;
			 } else if (in == 96) {
				 spriteFile.AI[i] = 206;
			 } else if (in == 97) {
				 spriteFile.AI[i] = 207;
			 } else if (in == 98) {
				 spriteFile.AI[i] = 208;
			 } else if (in == 99) {
				 spriteFile.AI[i] = 209;
			 } else if (in == 100) {
				 spriteFile.AI[i] = 210;
			 } else if (in == 101) {
				 spriteFile.AI[i] = 211;
			 } else if (in == 102) {
				 spriteFile.AI[i] = 212;
			 } else if (in == 103) {
				 spriteFile.AI[i] = 213;
			 } else if (in == 104) {
				 spriteFile.AI[i] = 214;
			 } else if (in == 105) {
				 spriteFile.AI[i] = 215;
			 } else if (in == 106) {
				 spriteFile.AI[i] = 216;
			 } else if (in == 107) {
				 spriteFile.AI[i] = 217;
			 } else if (in == 108) {
				 spriteFile.AI[i] = 218;
			 } else if (in == 109) {
				 spriteFile.AI[i] = 219;
			 }
		}
		
		if (comboBoxDestruction.getSelectedIndex() == 1) {
			spriteFile.destruction = 100 + comboBoxDestruct2.getSelectedIndex();
		} else {
			spriteFile.destruction = comboBoxDestruct2.getSelectedIndex();
		}
		
		try {
			spinner_4.commitEdit();
			spinner_5.commitEdit();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		spriteFile.message = textArea.getText();
		spriteFile.transformationValue = (int) spinner_4.getValue();
		spriteFile.message_duration = (int) spinner_5.getValue();
		spriteFile.showWhenShot = chckbxShotByPlayer.isSelected();
		spriteFile.showOnCollision = chckbxCollidingWithPlayer.isSelected();
		
		boolean ok = spriteFile.saveFile(file);

		currentFile = file;
		
		return ok;
	}
	
	private void playAnimation(int index) {
		if (spriteFile.animation[index] != null) {
			loop = chbx.get(index).isSelected();
			animate = true;
		
			i = 0;
			
			this.index = index;
			
			String[] s = txtfd.get(index).getText().split(",");
			
			int fr = 0;
			
			for (int i = 0; i < s.length; i++) {
				if (spriteFile.animation[index].sequence[i] != 0) {
					fr++;
				}
			}
			
			spriteFile.animation[index].frames = fr;
			
			for (int i = 0; i < s.length; i++) {
				spriteFile.animation[index].sequence[i] = Byte.parseByte(s[i].trim());
			}
		}
	}
	
	private void newFile() {
		setTitle("PekaSE - unnamed");
		
		spriteFile = new PK2Sprite();
		currentFile = null;
	
		textField.setText("");
		txtFieldName.setText("");
		
		spinner.setValue(1);
		spinner_1.setValue(1);
		spinner_2.setValue(1);
		spinner_3.setValue(1);
		
		spinnerSprWidth.setValue(1);
		spinnerSprHeight.setValue(1);
		
		comboBoxType.setSelectedIndex(0);

		comboBoxColor.setSelectedIndex(0);
		
		comboBoxDmg.setSelectedIndex(0);
		comboBoxImmunity.setSelectedIndex(0);
		
		chckbxObstacle.setSelected(false);
		
		textField.setText("");

		txtFieldName.setText("unnamed");

		textFieldAmmoSpr1.setText("");
		textFieldAmmoSpr2.setText("");

		comboBoxType.setSelectedIndex(0);

		comboBoxDmg.setSelectedIndex(0);
		comboBoxImmunity.setSelectedIndex(0);
		spinnerEnergy.setValue(0);
		spinnerDmg.setValue(0);
		spinnerScore.setValue(0);
		spinnerMaxJump.setValue(0);
		spinnerWeight.setValue(0d);
		spinnerMaxSpeed.setValue(0d);

		spinnerLoadDur.setValue(0);

		spinnerAtkPause.setValue(0);

		spinnerFrameRate.setValue(1);
		spinnerFrames.setValue(1);
		spinnerAnimations.setValue(0);

		spinnerAtk1Dur.setValue(0);
		spinnerAtk2Dur.setValue(0);
		
		chckbxEnemy.setSelected(false);
		chckbxBoss.setSelected(false);
		chckbxKey.setSelected(false);
		chckbxObstacle.setSelected(false);
		
		textFieldSndDmg.setText("");
		textFieldSndKnockOut.setText("");
		textFieldSndAtk1.setText("");
		textFieldSndAtk2.setText("");
		textFieldAtkRnd.setText("");
		
		String stillStr = "";
		
		String str = "";
		for (int i = 0; i < 9; i++) {
			str = "";
			
			for (int j = 0; j < 10; j++) {
				str += "0,";
			}
			
			txtfd.get(i).setText(str.substring(0, str.length() - 1));
			
			chbx.get(i).setSelected(false);
		}
		
		textFieldTransformSprite.setText("");
		textFieldBonusSprite.setText("");
		
		spinnerSndFrq.setValue(22050);
		chckbxRandomFrequency.setSelected(false);
		
		chckbxGlide.setSelected(false);
		chckbxShakes.setSelected(false);
		chckbxSwim.setSelected(false);
		chckbxTileCheck.setSelected(false);
		chckbxWallDown.setSelected(false);
		chckbxWallUp.setSelected(false);
		chckbxWallLeft.setSelected(false);
		chckbxWallRight.setSelected(false);
		chckbxAlwaysBonus.setSelected(false);
		
		spinnerParaFactor.setValue(0);
		spinnerBonus.setValue(0);
	
		ArrayList<JComboBox> cbAni = new ArrayList<JComboBox>();
		cbAni.add(comboBoxAi1);
		cbAni.add(comboBoxAi2);
		cbAni.add(comboBoxAi3);
		cbAni.add(comboBoxAi4);
		cbAni.add(comboBoxAi5);
		cbAni.add(comboBoxAi6);
		cbAni.add(comboBoxAi7);
		cbAni.add(comboBoxAi8);
		cbAni.add(comboBoxAi9);
		cbAni.add(comboBoxAi10);
		
		for (int i = 0; i < spriteFile.AI.length; i++) {
			cbAni.get(i).setSelectedIndex(0);
		}
		
		comboBoxDestruction.setSelectedIndex(0);
		comboBoxDestruct2.setSelectedIndex(0);
		
		spriteFile.frameList = null;
		
		test = null;
		
		panel_3.repaint();
		panel_4.repaint();
		
		lblStatus.setText("Create a new file!");
	}
	
	private String cleanString(char[] array) {
		StringBuilder sb = new StringBuilder();
		
		try {
			int i = 0;
			while (i < array.length && array[i] != 0x0) {
				sb.append(array[i]);
				
				i++;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return sb.toString();
	}
	
	private void setFrameTitle(String title) {
		setTitle(title);
	}
}
