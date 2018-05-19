

public class PK2SpriteAnimation {
	final int ANIMATION_MAX_SEQUENCES = 10;
	
	public int[] sequence = new int[ANIMATION_MAX_SEQUENCES];
	public int frames; // amount of frames
	public boolean loop; // wether the animations loops, or not
	
	public PK2SpriteAnimation(int[] sequence, int frames, boolean loop) {
		this.sequence = sequence;
		this.frames = frames;
		this.loop = loop;
	}
}
