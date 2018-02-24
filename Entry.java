import java.util.List;

public class Entry{
	private List<Tag> Tags;
	private String Text;

	public Entry(){

	}

	public Entry(List<Tag> tags, String text){
		this.Tags = tags; 
		this.Text =	text;
	}

	public List<Tag> getTags(){
		return this.Tags;
	}

	public void setTags(List<Tag> tags){
		this.Tags = tags;
	}

	public String getText(){
		return this.Text;
	}

	public void setText(String text){
		this.Text = text;
	}
}
