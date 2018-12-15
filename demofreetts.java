import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class demofreetts extends Thread{
	private String speaktext;
	private JTextArea editor;
	demofreetts()
	{
		super("Speakable Thread");
	}
	public void createGUI() {
		JFrame f = new JFrame("FreeTTS Demo");

		editor = new JTextArea();
		int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
		int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
		JScrollPane c = new JScrollPane(editor, v, h);
		f.add(c);
		f.setSize(400, 500);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menubar = new JMenuBar();
		JMenu menu = new JMenu("Edit");
		JMenuItem speak = new JMenuItem("Speak");
		speak.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
					speak();
			}
		});
		menu.add(speak);
		menubar.add(menu);
		f.setJMenuBar(menubar);
		f.setVisible(true);
	}
	public void speak(){
		this.start();
	}
	public void run(){
		try{
			dospeak(editor.getText(),"kevin16");
			Thread.sleep(10);
		}
		catch(Exception e){
			System.out.println("Error: "+e);
		}
	}
	public void dospeak(String speak, String voice) {
		speaktext = speak;
		try {
			VoiceManager voiceManager = VoiceManager.getInstance();
			Voice sp = voiceManager.getVoice(voice);
			if (sp == null) {
				System.out.println("No Voice Available");
			}
			// ========================
			sp.allocate();
			sp.speak(speaktext);
			sp.deallocate();
			// =========================
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		demofreetts obj = new demofreetts();
		try{
			obj.createGUI();
			Thread.sleep(1000);
		}
		catch(Exception e){
			System.out.println("Error: "+e);
		}
	}
}
