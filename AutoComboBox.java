import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

public class AutoComboBox extends JComboBox {

	Vector<String> options;

	public AutoComboBox(Vector<String> options, TextContentManager contentManager) {
		this.options = options;
		setModel(new DefaultComboBoxModel(options));
		setSelectedIndex(-1);
		setEditable(true);
		JTextField text = (JTextField) this.getEditor().getEditorComponent();
		text.setFocusable(true);
		text.setFocusTraversalKeysEnabled(false);
		text.setText("");
		text.addKeyListener(new ComboListener(this, options, contentManager));
	}

}
