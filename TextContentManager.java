import java.util.List;
import java.util.ArrayList;
import java.lang.StringBuilder;
import javax.swing.text.*;
import java.awt.Color;
import java.util.Map;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;

public class TextContentManager{
	private JTextComponent textComponent;
	private Map<String,Tag> tagMap;

	public TextContentManager(Map<String,Tag> tagMap, JTextComponent textComponent){
		this.tagMap = tagMap;
		this.textComponent = textComponent;
	}

	public void setTag(String tagString){
		List<HighlightData> hList = new ArrayList<HighlightData>();
		StringBuilder sb = new StringBuilder();
		Tag mainTag = tagMap.get(tagString);

		for(Entry e:mainTag.getEntries()){
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
