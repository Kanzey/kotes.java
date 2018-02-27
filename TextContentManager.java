import java.util.List;
import java.util.Collection;
import java.util.ArrayList;
import java.lang.StringBuilder;
import javax.swing.text.*;
import javax.swing.JTabbedPane;
import java.util.Map;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;

public class TextContentManager{
	private JTextComponent textComponent;
	private Map<String,Tag> tagMap;
	private JTabbedPane tabbedPane;
	private Tag currentTag;

	public TextContentManager(Map<String,Tag> tagMap, JTextComponent textComponent, JTabbedPane tabbedPane){
		this.tagMap = tagMap;
		this.textComponent = textComponent;
		this.tabbedPane = tabbedPane;
	}

	public String setTag(String tagString){
		List<HighlightData> hList = new ArrayList<HighlightData>();
		StringBuilder sb = new StringBuilder();
		Tag mainTag = tagMap.get(tagString);
		tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(), tagString);
		Collection<Entry> entries;
		if(mainTag != null)
			entries = mainTag.getEntries();
		else{
			textComponent.setText( "#" + tagString +'\n');
			highlight(new HighlightData(0, tagString.length() +1, Tag.getDefaultPainter()));
			currentTag = mainTag;
			return null;
		}
		if (currentTag == mainTag)
			return tagString;
		currentTag = mainTag;
		for(Entry e:entries){
			for(Tag tag :e.getTags()){
				int begin = sb.length();
				sb.append("#");
				sb.append(tag);
				hList.add(new HighlightData(begin, sb.length(), tag.getPainter()));
				sb.append(" ");
			}
			sb.append("\n");
			sb.append(e.getText());
			sb.append("\n");
		}
		textComponent.setText(sb.toString());
		for(HighlightData hd: hList)
			highlight(hd);
		System.out.println(Mem.get());
		return tagString;
	}

	public void highlight(HighlightData hData){
		try{
			textComponent.getHighlighter()
				.addHighlight(hData.getBegin(),hData.getEnd(),hData.getPainter()); 

		} catch (BadLocationException ex){
		}
	}

	public class HighlightData{
		private int begin;
		private int end;
		private DefaultHighlightPainter painter;

		public HighlightData(int begin, int end, DefaultHighlightPainter painter){
			this.begin = begin;
			this.end = end;
			this.painter = painter;
		}

		int getBegin(){
			return begin;
		}

		int getEnd(){
			return end;
		}

		DefaultHighlightPainter getPainter(){
			return painter;
		}
	}
}
