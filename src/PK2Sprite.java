
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.IndexColorModel;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class PK2Sprite {
	public int type;
	public char[] imageFile = new char[100];
	public char[][] soundFiles = new char[7][100];
	
	public char[] version13 = {0x31, 0x2E, 0x33, 0x00};
	public char[] version = new char[version13.length];
	
	public String ImageFileStr = "";
	
	public File filename;
	
	int[] sounds = new int[7];
	
	int frames = 1; // number of frames
	
	public PK2SpriteAnimation[] animation = new PK2SpriteAnimation[20];
	
	public int animations; // number of animations
	public int frameRate = 1;
	
	public int frameX, frameY;
	
	public int frameWidth;
	public int frameHeight;
	public int frameDistance = 0xCCCCCCCC;
	
	public char[] name = new char[33];
	public int width, height;
	
	public char[] transformationSprite = new char[100];
	public char[] bonusSprite = new char[100];
	
	double weight;
	
	boolean enemy;
	
	int energy;
	int damage;
	
	int damageType;
	int immunity;
	int score;
	
	int[] AI = new int[10];
	
	public char[] atkSprite1 = new char[100];
	public char[] atkSprite2 = new char[100];
	
	int maxJump;
	
	public double maxSpeed;
	
	int loadingTime;
	
	int color;
	
	boolean obstacle, boss, tileCheck;
	boolean wallUp, wallDown, wallLeft, wallRight;
	
	int destruction;
	
	boolean key;
	boolean shakes;
	
	int bonuses;
	
	int attack1Duration;
	int attack2Duration;
	
	public BufferedImage[] frameList;
	
	public BufferedImage image, fullImage;
	public int atkPause;
	public int parallaxFactor;
	public int soundFrequency;
	public boolean randomFrequency;
	public boolean glide;
	public boolean bonusAlways;
	public boolean swim;
	
	// version 1.4
	public String message = "";
	
	public int message_duration = 0;
	
	public boolean showWhenShot = false;
	public boolean showOnCollision = false;
	
	public int transformationValue = 0;
	public int attackPriority = 0;
	
	public PK2Sprite(File file) {
		filename = file;

		//loadFile(file);
		//loadBufferedImage();
	}
	
	public PK2Sprite() {
		byte[] sq = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		for (int i = 0; i < animation.length; i++) {
			animation[i] = new PK2SpriteAnimation(sq, 0, false);
		}
		
		color = 255;
	}
	
	public int checkVersion() {
		DataInputStream dis;
		
		int ret = -1;
		
		try {
			dis = new DataInputStream(new FileInputStream(filename));
			
			readAmount(version, dis);
			
//			TODO This is NOT the way to do it, this should be fixed in the future.
			if (version[2] == '3') {
				ret = 3;
			} else if (version[2] == '4') {
				ret = 4;
			}
			
			dis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return ret;
	}
	
	public boolean loadFile() {
		DataInputStream dis = null;
		
		boolean ok = false;
		
		try {
			dis = new DataInputStream(new FileInputStream(filename));
		
			int res = checkVersion();
			
			if (res == 4) {
				loadVersion14(dis);
			} else if (res == 3) {
				loadVersion13(dis);
			}
			
			if (new File(filename.getParentFile().getAbsolutePath() + "\\" + cleanString(imageFile)).exists()) {
				ImageFileStr = filename.getParentFile().getAbsolutePath() + "\\" + cleanString(imageFile);
			} else {
				ImageFileStr = Settings.SPRITE_PATH + cleanString(imageFile);
			}
		
			ok = loadBufferedImage();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
			JOptionPane.showMessageDialog(null, "Couldn't load file '" + filename.getName() + "'!\n" + e.getMessage(), "Couldn't find file", JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			e.printStackTrace();
			
			JOptionPane.showMessageDialog(null, "Couldn't load file '" + filename.getName() + "'!\n" + e.getMessage(), "Couldn't load file", JOptionPane.ERROR_MESSAGE);
		} finally {
			try {
				dis.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		return ok;
	}
	
	private String readString(DataInputStream dis) throws IOException {
		StringBuilder sb = new StringBuilder();
		
		byte len = dis.readByte();
		
		for (int i = 0; i < len; i++) {
			sb.append((char) dis.readByte());
		}
		
		return sb.toString();
	}
	
	private boolean loadVersion14(DataInputStream dis) throws IOException {
		boolean ok = false;
		
		char[] version = {'1', '.', '4', '\0'};
		
		for (int i = 0; i < version.length; i++) {
			version[i] = (char) dis.readByte();
		}

		type = dis.readByte();
		
		imageFile = readString(dis).toCharArray();
		name = readString(dis).toCharArray();
		
		frameX = Integer.reverseBytes(dis.readInt());
		frameY = Integer.reverseBytes(dis.readInt());
		frameWidth = Integer.reverseBytes(dis.readInt());
		frameHeight = Integer.reverseBytes(dis.readInt());
		
		color = dis.readByte() & 0xFF;
		
		for (int i = 0; i < 5; i++) {
			char[] amount = new char[soundFiles[0].length];
			readAmount(amount, dis);
			soundFiles[i] = amount;
		}

		frames = (int) (dis.readByte() & 0xFF);

		for (int i = 0; i < 10; i++) {
			byte[] sequence = new byte[10];
			int frames = 0;
			boolean loop = false;
			
			for (int j = 0; j < sequence.length; j++) {
				sequence[j] = dis.readByte();
			}
			
			frames = (int) (dis.readByte() & 0xFF);
			loop = dis.readBoolean();
			
			animation[i] = new PK2SpriteAnimation(sequence, frames, loop);
		}
		
		animations = (int) (dis.readByte() & 0xFF);
		frameRate = (int) (dis.readByte() & 0xFF);
		
		width = Integer.reverseBytes(dis.readInt());
		height = Integer.reverseBytes(dis.readInt());
		
		weight = dis.readDouble();
		
		ByteBuffer b = ByteBuffer.allocate(8);
		
		b.putDouble(weight);

		b.order(ByteOrder.LITTLE_ENDIAN);
		
		weight = b.getDouble(0);
		
		enemy = dis.readBoolean();
		
		energy = Integer.reverseBytes(dis.readInt());
		damage = Integer.reverseBytes(dis.readInt());
		
		damageType = dis.readByte() & 0xFF;
		immunity = dis.readByte() & 0xFF;

		score = Integer.reverseBytes(dis.readInt());
		
		for (int i = 0; i < 10; i++) {
			AI[i] = Integer.reverseBytes(dis.readInt());
		}
		
		maxJump = dis.readByte() & 0xFF;
		
		maxSpeed = dis.readDouble();
		
		ByteBuffer b2 = ByteBuffer.allocate(8);
		b2.putDouble(maxSpeed);
		b2.order(ByteOrder.LITTLE_ENDIAN);
		
		maxSpeed = b2.getDouble(0);
		
		loadingTime = Integer.reverseBytes(dis.readInt());
		
		obstacle = dis.readBoolean();

		destruction = Integer.reverseBytes(dis.readInt());
		
		key = dis.readBoolean();
		shakes = dis.readBoolean();
		
		bonuses = dis.readByte() & 0xFF;
		
		attack1Duration = Integer.reverseBytes(dis.readInt());
		attack2Duration = Integer.reverseBytes(dis.readInt());
		
		parallaxFactor = Integer.reverseBytes(dis.readInt());
		
		transformationSprite = readString(dis).toCharArray();
		
		bonusSprite = readString(dis).toCharArray();

		atkSprite1 = readString(dis).toCharArray();
		atkSprite2 = readString(dis).toCharArray();
				
		tileCheck = dis.readBoolean();
		
		soundFrequency = Integer.reverseBytes(dis.readInt());
		randomFrequency = dis.readBoolean();
		
		wallUp = dis.readBoolean();
		wallDown = dis.readBoolean();
		wallRight = dis.readBoolean();
		wallLeft = dis.readBoolean();

		atkPause = Integer.reverseBytes(dis.readInt());
		
		glide = dis.readBoolean();
		boss = dis.readBoolean();
		bonusAlways = dis.readBoolean();
		swim = dis.readBoolean();
		
		message = readString(dis);
		
		message_duration = Integer.reverseBytes(dis.readInt());
		showWhenShot = dis.readBoolean();
		showOnCollision = dis.readBoolean();
		transformationValue = Integer.reverseBytes(dis.readInt());
		attackPriority = Integer.reverseBytes(dis.readInt());
		
		return ok;
	}
	
	private boolean loadVersion13(DataInputStream dis) throws IOException {
		boolean ok = false;
		
		char[] version = {'1', '.', '3', '\0'};
		
		for (int i = 0; i < version.length; i++) {
			version[i] = (char) dis.readByte();
		}

		type = Integer.reverseBytes(dis.readInt());
		
		for (int i = 0; i < imageFile.length; i++) {
			imageFile[i] = (char) (dis.readByte() & 0xFF);
		}
		
		for (int i = 0; i < soundFiles.length; i++) {
			char[] amount = new char[soundFiles[0].length];
			readAmount(amount, dis);
			soundFiles[i] = amount;
		}
		

		// read data that isn't needed, but is yet present
		for (int i = 0; i < sounds.length; i++) {
			sounds[i] = dis.readInt();
		}
		
		frames = (int) (dis.readByte() & 0xFF);
		
		for (int i = 0; i < animation.length; i++) {
			byte[] sequence = new byte[10];
			int frames = 0;
			boolean loop = false;
			
			for (int j = 0; j < sequence.length; j++) {
				sequence[j] = dis.readByte();
			}
			
			frames = (int) (dis.readByte() & 0xFF);
			loop = dis.readBoolean();
			
			animation[i] = new PK2SpriteAnimation(sequence, frames, loop);
		}
		
		animations = (int) (dis.readByte() & 0xFF);
		frameRate = (int) (dis.readByte() & 0xFF);
		
		dis.readByte(); //Padding? not documented, though
		
		frameX = Integer.reverseBytes(dis.readInt());
		frameY = Integer.reverseBytes(dis.readInt());
		
		frameWidth = Integer.reverseBytes(dis.readInt());
		frameHeight = Integer.reverseBytes(dis.readInt());
		frameDistance = Integer.reverseBytes(dis.readInt());
		
		// read the sprite's name
		//readAmount(name, dis);
		for (int i = 0; i < 32; i++) {
			name[i] = (char) dis.readByte();
		}
		
		width = Integer.reverseBytes(dis.readInt());
		height = Integer.reverseBytes(dis.readInt());
		
		weight = dis.readDouble();
		
		ByteBuffer b = ByteBuffer.allocate(8);
		
		b.putDouble(weight);

		b.order(ByteOrder.LITTLE_ENDIAN);
		
		weight = b.getDouble(0);
		
		enemy = dis.readBoolean();
		
		dis.readByte();
		dis.readByte();
		dis.readByte();
		
		energy = Integer.reverseBytes(dis.readInt());
		damage = Integer.reverseBytes(dis.readInt());
		
		damageType = dis.readByte() & 0xFF;
		immunity = dis.readByte() & 0xFF;
		
		dis.readByte();
		dis.readByte();
		
		score = Integer.reverseBytes(dis.readInt());
		
		for (int i = 0; i < 10; i++) {
			AI[i] = Integer.reverseBytes(dis.readInt());
		}
		
		maxJump = dis.readByte() & 0xFF;
		
		dis.readByte();
		dis.readByte();
		dis.readByte();
		
		maxSpeed = dis.readDouble();
		
		ByteBuffer b2 = ByteBuffer.allocate(8);
		b2.putDouble(maxSpeed);
		b2.order(ByteOrder.LITTLE_ENDIAN);
		
		maxSpeed = b2.getDouble(0);
		
		loadingTime = Integer.reverseBytes(dis.readInt());
		
		color = dis.readByte() & 0xFF;
		
		obstacle = dis.readBoolean();
		
		dis.readByte();
		dis.readByte();
		
		destruction = Integer.reverseBytes(dis.readInt());
		
		key = dis.readBoolean();
		shakes = dis.readBoolean();
		
		bonuses = dis.readByte() & 0xFF;
		
		dis.readByte();
		
		attack1Duration = Integer.reverseBytes(dis.readInt());
		attack2Duration = Integer.reverseBytes(dis.readInt());
		
		parallaxFactor = Integer.reverseBytes(dis.readInt());
		
		readAmount(transformationSprite, dis);
		readAmount(bonusSprite, dis);

		readAmount(atkSprite1, dis);
		readAmount(atkSprite2, dis);
		
		tileCheck = dis.readBoolean();
		
		dis.readByte();
		dis.readByte();
		dis.readByte();
		
		soundFrequency = Integer.reverseBytes(dis.readInt());
		randomFrequency = dis.readBoolean();
		
		wallUp = dis.readBoolean();
		wallDown = dis.readBoolean();
		wallRight = dis.readBoolean();
		wallLeft = dis.readBoolean();
		
		dis.readByte();
		dis.readByte();
		dis.readByte();
		
		atkPause = Integer.reverseBytes(dis.readInt());
		
		glide = dis.readBoolean();
		boss = dis.readBoolean();
		bonusAlways = dis.readBoolean();
		swim = dis.readBoolean();
		
		return ok;
	}
	
	public boolean saveFile(File file) {
		DataOutputStream dis = null;
		
		boolean ok = false;
		
		if (file == null) {
			return false;
		}
		
		try {
			if (file.exists()) {
				file.delete();
			}
			
			dis = new DataOutputStream(new FileOutputStream(file));
			
			if (Settings.use14) {
				ok = saveFile14(dis);
			} else {
				ok = saveFile13(dis);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
			ok = false;
			
			JOptionPane.showMessageDialog(null, "Couldn't save file!\n" + e.getMessage(), "Couldn't save!", JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			e.printStackTrace();
			
			ok = false;
			
			JOptionPane.showMessageDialog(null, "Couldn't save file!\n" + e.getMessage(), "Couldn't save!", JOptionPane.ERROR_MESSAGE);
		} finally {
			try {
				dis.close();
			} catch (IOException e) {
				ok = false;
				
				e.printStackTrace();
			}
		}
		
		return ok;
	}
	
	private boolean saveFile14(DataOutputStream dis) throws IOException {
		boolean ok = false;
		
		char[] version = {'1', '.', '4', '\0'};
		
		for (int i = 0; i < version.length; i++) {
			dis.writeByte(version[i]);
		}
		
		dis.writeByte(type);
		
		// TODO change ImageFile to string
		writeString(new String(imageFile), dis);
		// TODO change name to strings
		writeString(new String(name), dis);
		
		dis.writeInt(Integer.reverseBytes(frameX));
		dis.writeInt(Integer.reverseBytes(frameY));
		dis.writeInt(Integer.reverseBytes(frameWidth));
		dis.writeInt(Integer.reverseBytes(frameHeight));
		
		dis.writeByte(color);
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100; j++) {
				dis.writeByte(soundFiles[i][j]);
			}
		}

		dis.writeByte(frames);
		
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < animation[i].sequence.length; j++) {
				dis.writeByte(animation[i].sequence[j]);
			}
			
			dis.writeByte(animation[i].frames);
			dis.writeBoolean(animation[i].loop);
		}
		
		dis.writeByte(animations);
		dis.writeByte(frameRate);
		
		dis.writeInt(Integer.reverseBytes(width));
		dis.writeInt(Integer.reverseBytes(height));
		
		ByteBuffer b = ByteBuffer.allocate(8);
		
		b.putDouble(weight);

		b.order(ByteOrder.LITTLE_ENDIAN);
		
		dis.writeDouble(b.getDouble(0));
		
		dis.writeBoolean(enemy);
		
		dis.writeInt(Integer.reverseBytes(energy));
		dis.writeInt(Integer.reverseBytes(damage));
		
		dis.writeByte(damageType);
		dis.writeByte(immunity);

		dis.writeInt(Integer.reverseBytes(score));
		
		for (int i = 0; i < 10; i++) {
			dis.writeInt(Integer.reverseBytes(AI[i]));
		}
		
		dis.writeByte(maxJump);
		
		ByteBuffer b2 = ByteBuffer.allocate(8);
		b2.putDouble(maxSpeed);
		b2.order(ByteOrder.LITTLE_ENDIAN);
		
		dis.writeDouble(b2.getDouble(0));
		
		dis.writeInt(Integer.reverseBytes(loadingTime));
		
		dis.writeBoolean(obstacle);
		
		dis.writeInt(Integer.reverseBytes(destruction));
		
		dis.writeBoolean(key);
		dis.writeBoolean(shakes);
		
		dis.writeByte(bonuses);
		
		dis.writeInt(Integer.reverseBytes(attack1Duration));
		dis.writeInt(Integer.reverseBytes(attack2Duration));
		
		dis.writeInt(Integer.reverseBytes(parallaxFactor));
		
		// TODO change to str, blah blah
		writeString(new String(transformationSprite), dis);

		// TODO ... string
		writeString(new String(bonusSprite), dis);
		
		// TODO ...
		writeString(new String(atkSprite1), dis);
		writeString(new String(atkSprite2), dis);		

		dis.writeBoolean(tileCheck);
		
		dis.writeInt(Integer.reverseBytes(soundFrequency));
		dis.writeBoolean(randomFrequency);
		
		dis.writeBoolean(wallUp);
		dis.writeBoolean(wallDown);
		dis.writeBoolean(wallRight);
		dis.writeBoolean(wallLeft);
	
		dis.writeInt(Integer.reverseBytes(atkPause));
		
		dis.writeBoolean(glide);
		dis.writeBoolean(boss);
		dis.writeBoolean(bonusAlways);
		dis.writeBoolean(swim);

		writeString(message, dis);
		
		dis.writeInt(Integer.reverseBytes(message_duration));
		dis.writeBoolean(showWhenShot);
		dis.writeBoolean(showOnCollision);
		dis.writeInt(Integer.reverseBytes(transformationValue));
		dis.writeInt(Integer.reverseBytes(attackPriority));
		
		dis.flush();
		dis.close();
		
		ok = true;

		return ok;
	}
	
	private boolean saveFile13(DataOutputStream dis) throws IOException {
		boolean ok = false;
		
		char[] version = {'1', '.', '3', '\0'};
		
		for (int i = 0; i < version.length; i++) {
			dis.writeByte(version[i]);
		}
		
		dis.writeInt(Integer.reverseBytes(type));
		
		int len = 0;
		for (int i = 0; i < imageFile.length; i++) {
			if (imageFile[i] != 0) {
				len++;
			}
		}
		
		for (int i = 0; i < len; i++) {
			dis.writeByte(imageFile[i]);
		}
		
		dis.writeByte(0);
		
		for (int i = len + 1; i < imageFile.length; i++) {
			dis.writeByte(0xCC);
		}

		for (int i = 0; i < soundFiles.length; i++) {
			for (int j = 0; j < 100; j++) {
				dis.writeByte(soundFiles[i][j]);
			}
		}

		// read data that isn't needed, but is yet present
		for (int i = 0; i < sounds.length; i++) {
			dis.writeInt(0xFFFFFFFF);
		}
		
		dis.writeByte(frames);
		
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < animation[i].sequence.length; j++) {
				dis.writeByte(animation[i].sequence[j]);
			}
			
			dis.writeByte(animation[i].frames);
			dis.writeBoolean(animation[i].loop);
		}
		
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				dis.writeByte(0);
			}
			
			dis.writeByte(0);
			dis.writeBoolean(false);
		}
		
		dis.writeByte(animations);
		dis.writeByte(frameRate);
		
		dis.writeByte(0xCC);
		
		dis.writeInt(Integer.reverseBytes(frameX));
		dis.writeInt(Integer.reverseBytes(frameY));
		dis.writeInt(Integer.reverseBytes(frameWidth));
		dis.writeInt(Integer.reverseBytes(frameHeight));
		dis.writeInt(Integer.reverseBytes(frameDistance));
		
		len = 0;
		for (int i = 0; i < name.length; i++) {
			if (name[i] != 0) {
				len++;
			}
		}
		
		for (int i = 0; i < len; i++) {
			dis.writeByte(name[i]);
		}
		
		dis.writeByte(0);

		for (int i = len + 1; i < 32; i++) {
			dis.writeByte(0xCC);
		}
		
		dis.writeInt(Integer.reverseBytes(width));
		dis.writeInt(Integer.reverseBytes(height));
		
		ByteBuffer b = ByteBuffer.allocate(8);
		
		b.putDouble(weight);

		b.order(ByteOrder.LITTLE_ENDIAN);
		
		dis.writeDouble(b.getDouble(0));
		
		dis.writeBoolean(enemy);
		
		dis.writeByte(0xCC);
		dis.writeByte(0xCC);
		dis.writeByte(0xCC);
		
		dis.writeInt(Integer.reverseBytes(energy));
		dis.writeInt(Integer.reverseBytes(damage));
		
		dis.writeByte(damageType);
		dis.writeByte(immunity);
		
		dis.writeByte(0xcC);
		dis.writeByte(0xCC);
		
		dis.writeInt(Integer.reverseBytes(score));
		
		for (int i = 0; i < 10; i++) {
			dis.writeInt(Integer.reverseBytes(AI[i]));
		}
		
		dis.writeByte(maxJump);
		
		dis.writeByte(0xCC);
		dis.writeByte(0xCC);
		dis.writeByte(0xCC);
		
		ByteBuffer b2 = ByteBuffer.allocate(8);
		b2.putDouble(maxSpeed);
		b2.order(ByteOrder.LITTLE_ENDIAN);
		
		dis.writeDouble(b2.getDouble(0));
		
		dis.writeInt(Integer.reverseBytes(loadingTime));
		
		dis.writeByte(color);
		
		dis.writeBoolean(obstacle);
		
		dis.writeByte(0xCC);
		dis.writeByte(0xCC);
		
		dis.writeInt(Integer.reverseBytes(destruction));
		
		dis.writeBoolean(key);
		dis.writeBoolean(shakes);
		
		dis.writeByte(bonuses);
		
		dis.writeByte(0xCC);
		
		dis.writeInt(Integer.reverseBytes(attack1Duration));
		dis.writeInt(Integer.reverseBytes(attack2Duration));
		
		dis.writeInt(Integer.reverseBytes(parallaxFactor));
		
		len = 0;
		for (int i = 0; i < transformationSprite.length; i++) {
			if (transformationSprite[i] != 0) {
				len++;
			}
		}
		
		for (int i = 0; i < len; i++) {
			dis.writeByte(transformationSprite[i]);
		}
		
		dis.writeByte(0);

		for (int i = len + 1; i < transformationSprite.length; i++) {
			dis.writeByte(0xCC);
		}
		
		len = 0;
		for (int i = 0; i < bonusSprite.length; i++) {
			if (bonusSprite[i] != 0) {
				len++;
			}
		}
		
		for (int i = 0; i < len; i++) {
			dis.writeByte(bonusSprite[i]);
		}
		
		dis.writeByte(0);

		for (int i = len + 1; i < bonusSprite.length; i++) {
			dis.writeByte(0xCC);
		}
		
		len = 0;
		for (int i = 0; i < atkSprite1.length; i++) {
			if (atkSprite1[i] != 0) {
				len++;
			}
		}
		
		for (int i = 0; i < len; i++) {
			dis.writeByte(atkSprite1[i]);
		}
		
		dis.writeByte(0);

		for (int i = len + 1; i < atkSprite1.length; i++) {
			dis.writeByte(0xCC);
		}
		
		len = 0;
		for (int i = 0; i < atkSprite2.length; i++) {
			if (atkSprite2[i] != 0) {
				len++;
			}
		}
		
		for (int i = 0; i < len; i++) {
			dis.writeByte(atkSprite2[i]);
		}
		
		dis.writeByte(0);

		for (int i = len + 1; i < atkSprite2.length; i++) {
			dis.writeByte(0xCC);
		}
		
		dis.writeBoolean(tileCheck);
		
		dis.writeByte(0xCC);
		dis.writeByte(0xCC);
		dis.writeByte(0xCC);
		
		dis.writeInt(Integer.reverseBytes(soundFrequency));
		dis.writeBoolean(randomFrequency);
		
		dis.writeBoolean(wallUp);
		dis.writeBoolean(wallDown);
		dis.writeBoolean(wallRight);
		dis.writeBoolean(wallLeft);
		
		dis.writeByte(0x0);
		dis.writeByte(0x0);
		dis.writeByte(0xCC);
		
		dis.writeInt(Integer.reverseBytes(atkPause));
		
		dis.writeBoolean(glide);
		dis.writeBoolean(boss);
		dis.writeBoolean(bonusAlways);
		dis.writeBoolean(swim);

		dis.writeByte(0xCC);
		dis.writeByte(0xCC);
		dis.writeByte(0xCC);
		dis.writeByte(0xCC);
		
		dis.flush();
		dis.close();
		
		ok = true;
		
		return ok;
	}
	
	private void writeString(String str, DataOutputStream dos) throws IOException {
		dos.writeByte(str.length());
	
		for (int i = 0; i < str.length(); i++) {
			dos.writeByte((byte) str.charAt(i));
		}
	}
	
	private void readAmount(char[] array, DataInputStream dis) throws IOException {
		for (int i = 0; i < array.length; i++) {
			array[i] = (char) dis.readByte();
		}
	}
	
	public String getName() {
		return cleanString(name);
	}
	
	public boolean loadBufferedImage() {
		FileInputStream stream = null;
		
		boolean ok = false;
		
		try {
			frameList = new BufferedImage[frames];
			
			stream = new FileInputStream(new File(ImageFileStr));
			
			BufferedImage image = ImageIO.read(stream);
			BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_INDEXED, (IndexColorModel) image.getColorModel());

		    byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		    
		    if (color != 255) {
		    	int col;
		    	
		    	for (int i = 0; i < image.getWidth(); i++) {
		    		for (int j = 0; j < image.getHeight(); j++) {
		    			if ((col = data[i + j * image.getWidth()]) != 255) {
		    				col &= 0xFF;
		    				
		    				if (image.getRGB(i, j) != image.getColorModel().getRGB(255)) {
			    				col %= 32;
			    				col += color;
			    				
			    				data[i + j * image.getWidth()] = (byte) col;
		    				}
		    			}
		    		}
		    	}
		    }
		    
		    result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		    
		    for (int i = 0; i < image.getWidth(); i++) {
		    	for (int j = 0; j < image.getHeight(); j++) {
		    		if (image.getRGB(i, j) != image.getColorModel().getRGB(255)) {
		    			result.setRGB(i, j, image.getRGB(i, j));
		    		}
		    	}
		    }
		    
		    int fx = frameX, fy = frameY;
		    for (int i = 0; i < frames; i++) {
		    	if (fx + frameWidth > 640) {
		    		fy += frameHeight + 3;
		    		fx = frameX;
		    	}
		    	
		    	if (fx + frameWidth < result.getWidth() && fx + frameWidth < 640) {
		    		if (fy + frameHeight < result.getHeight()) {
		    			frameList[i] = result.getSubimage(fx, fy, frameWidth, frameHeight);
		    		}
		    	}
		    	
		    	fx += frameWidth + 3;
		    }
			
		    this.image = result.getSubimage(frameX, frameY, frameWidth, frameHeight);
		    
		    ok = true;
		} catch (IOException e) {
			e.printStackTrace();
			
			JOptionPane.showMessageDialog(null, "Couldn't load image file!\n" + e.getMessage(), "Couldn't load image!", JOptionPane.ERROR_MESSAGE);
		} finally {
			 try {
				stream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return true;
	}
	
	private String cleanString(char[] array) {
		StringBuilder sb = new StringBuilder();
		
		try {
			int i = 0;
			while (array[i] != 0x0) {
				sb.append(array[i]);
				
				i++;
			}
		} catch (Exception ex) {
		}
		
		return sb.toString();
	}
}
