import java.util.Set;
import java.util.HashSet;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;

public class Tag{
	private String name;
	private Set<Entry> entrySet;
	private DefaultHighlightPainter painter;
	static private PastelColorGenerator colorGenerator = new PastelColorGenerator(); 
	static private DefaultHighlightPainter defaultPainter;

	public Tag(String name){
		this.name = name;
		entrySet = new HashSet<Entry>();
		painter = new DefaultHighlightPainter(colorGenerator.nextColor());
	}

	public void addEntry(Entry entry){
		entrySet.add(entry);
	}

	public Set<Entry> getEntries(){
		return entrySet;
	}

	public void removeEntry(Entry entry){
		entrySet.remove(entry);		
	}

	public DefaultHighlightPainter getPainter(){
		return painter;
	}

	public String toString(){
		return name;
	}

	static public DefaultHighlightPainter getDefaultPainter(){
		if(defaultPainter == null)
			defaultPainter = new DefaultHighlightPainter(colorGenerator.nextColor());
		return defaultPainter;
	}
}
