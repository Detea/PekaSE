public class PK2SpriteAnimation {
	final int ANIMATION_MAX_SEQUENCES = 10;
	
	public byte[] sequence = new byte[ANIMATION_MAX_SEQUENCES];
	public int frames; // amount of frames
	public boolean loop; // whether the animations loops, or not
	
	public PK2SpriteAnimation(byte[] sequence, int frames, boolean loop) {
		this.sequence = sequence;
		this.frames = frames;
		this.loop = loop;
	}
}
