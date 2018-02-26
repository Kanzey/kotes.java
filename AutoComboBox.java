import java.util.Vector;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

public class AutoComboBox extends JComboBox {

	private Vector<String> options;
    private TextContentManager ContentManager;

	public AutoComboBox(Vector<String> options, TextContentManager contentManager) {
		this.options = options;
		this.ContentManager = contentManager;
		setModel(new DefaultComboBoxModel(options));
		setSelectedIndex(-1);
		setEditable(true);
		JTextField text = (JTextField) this.getEditor().getEditorComponent();
		text.setFocusable(true);
		text.setFocusTraversalKeysEnabled(false);
		text.setText("");
		text.addKeyListener(new ComboListener(this, options, contentManager));
		addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if( e.getSource() instanceof AutoComboBox){
					AutoComboBox box = (AutoComboBox) e.getSource();
					String s = (String)box.getSelectedItem();
				if( s!= null)
					ContentManager.setTag(s);
				}
			}
		});
	}

}
