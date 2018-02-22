import java.util.List;
import java.lang.StringBuilder;
import javax.swing.JTextArea;

public class TextAreaContentManager{
	private List<Entry> entries;
	private JTextArea textArea;


	public TextAreaContentManager(List<Entry> entries, JTextArea textArea){
		this.entries = entries;
		this.textArea = textArea;
	}

	public void setTag(String tag){
		StringBuilder sb = new StringBuilder();
		for(Entry e:entries){
			for(String s:e.getTags()){
				if(s.equals(tag)){
					for(String st:e.getTags()){
						sb.append("#");
						sb.append(st);
						sb.append(" ");
					}
					sb.append("\n");
					sb.append(e.getText());
					break;
				}
			}
		}
		textArea.setText(sb.toString());
	}
}
