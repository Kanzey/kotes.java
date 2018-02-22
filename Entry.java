

public class Entry{
	private String[] Tags;
	private String Text;

	public Entry(){

	}

	public Entry(String tags, String text){
		this.Tags = tags.split("\\s+#");
		this.Text =	text;
	}

	public String[] getTags(){
		return this.Tags;
	}

	public void setTags(String[] tags){
		this.Tags = tags;
	}

	public String getText(){
		return this.Text;
	}

	public void setText(String text){
		this.Text = text;
	}

	public void repair(){
		Tags[0] = Tags[0].substring(1);
	}
}
