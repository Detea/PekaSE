import java.awt.EventQueue;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class PekaSE {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					if (new File("PekaSE.paths").exists()) {
						DataInputStream dis = new DataInputStream(new FileInputStream("PekaSE.paths"));
						
						Settings.BASE_PATH = dis.readUTF();
						
						dis.close();
						
						Settings.setPaths();
						
						new PekaSEFrame().setVisible(true);
					} else {
						new SetPathDialog();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
