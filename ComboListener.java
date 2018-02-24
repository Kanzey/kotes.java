import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

public class ComboListener extends KeyAdapter
{
	JComboBox cbListener;
	Vector<String> options;
	TextContentManager contentManager;
	
	public ComboListener(JComboBox cbListenerParam, Vector<String> options, TextContentManager contentManager)
	{
		cbListener = cbListenerParam;
		this.options = options;
		this.contentManager = contentManager;
	}

	public void keyPressed(KeyEvent evt){
		if(evt.getKeyCode() == KeyEvent.VK_ENTER){
			String text = ((JTextField)evt.getSource()).getText();
			contentManager.setTag(text);
			cbListener.setModel(new DefaultComboBoxModel(options));
			((JTextField)cbListener.getEditor().getEditorComponent()).setText(text);
			cbListener.hidePopup();
		}
	}

	public void keyReleased(KeyEvent evt)
	{
		if(evt.getKeyCode() == KeyEvent.VK_TAB ){
			if(cbListener.getItemCount() > 0)
				cbListener.setSelectedIndex(
						(cbListener.getSelectedIndex() +1) % cbListener.getItemCount());
			return;
		}
		if(!evt.isActionKey() && evt.getKeyCode() != KeyEvent.VK_ENTER){
			String text = ((JTextField)evt.getSource()).getText();
			cbListener.setModel(new DefaultComboBoxModel(getFilteredList(text)));
			cbListener.setSelectedIndex(-1);
			((JTextField)cbListener.getEditor().getEditorComponent()).setText(text);
			cbListener.showPopup();
		}
	}

	public Vector getFilteredList(String text)
	{
		Vector<String> v = new Vector<String>();
		if(text.length() == 0 )
			return options;
		for(String s:options)
		{
			int k = 0;
			for(int i=0; i<s.length(); ++i){
				if( s.charAt(i) == text.charAt(k)){
					++k;
					if(k == text.length()){
						v.add(s);
						break;
					}
				}
			}

		}
		return v;
	}
}
