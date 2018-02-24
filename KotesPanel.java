/* KotesPanel.java requires no other files. */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Vector;
import java.util.Map;
import java.util.HashMap;

public class KotesPanel extends JPanel implements ActionListener {
	private TextContentManager contentManager;
	protected JComboBox comboBox;	
	protected JEditorPane textEditor;
	private List<Entry> Entries;
	private Map<String, Tag> tagMap;
	private Vector<String> autocomplete;
	private String text ="";
	private final static String newline = "\n";

	public KotesPanel() {
		super(new GridBagLayout());
		Entries = new ArrayList<Entry>();
		loadFile("allnotes.txt");
		textEditor = new JEditorPane("text/plain",text);
		contentManager = new TextContentManager(tagMap, textEditor);
		comboBox = new AutoComboBox(autocomplete, contentManager);
		//		comboBox.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
		//		comboBox.setFocusTraversalKeysEnabled(false);
		//textField.addActionListener(this);
		textEditor.setEditable(true);
		JScrollPane scrollPane = new JScrollPane(textEditor);

		//Add Components to this panel.
		GridBagConstraints c = new GridBagConstraints();
		c.gridwidth = GridBagConstraints.REMAINDER;

		c.fill = GridBagConstraints.HORIZONTAL;
		//add(textField, c);

		add(comboBox,c);
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 1.0;
		add(scrollPane, c);
	}

	private final void loadFile(String filename){
		//TODO
		//handle exceptions.
		//Handle empty file.
		try{
			File file = new File(filename);                                      
			FileInputStream fis = new FileInputStream(file);
			byte[] data = new byte[(int) file.length()];
			fis.read(data);
			fis.close();
			tagMap = new HashMap<String,Tag>(); 
			String[] Tags = new String(data, "UTF-8").split("\\r?\\n#");
			for(String s: Tags){
				String[] tag = s.split("\\r?\\n",2);
				String text = "";
				if(tag.length == 2)
					text = tag[1];
				List<Tag> list = new ArrayList<Tag>();
				for(String ss: tag[0].trim().split("\\s+#") ){
					Tag t = tagMap.get(ss);
					if(t == null){
						t = new Tag(ss);
						tagMap.put(ss,t);
					}
					list.add(t);
				}
				Entry e = new Entry(list, text);
				Entries.add( e );
				for(Tag t:list)
					t.addEntry(e);
			}
			autocomplete = new Vector<String>(tagMap.keySet());
		} catch (FileNotFoundException e){                                         

		} catch (UnsupportedEncodingException e){                                  

		} catch (IOException e) {                                                  
		}
	}


	public void actionPerformed(ActionEvent evt) {
		/*	String text = textField.getText();
			int i=0;
			try{
			i = Integer.parseInt(text);
			textEditor.setText(Entries.get(i).getText());
			}catch(NumberFormatException e){
			}
			textField.selectAll();

			textEditor.setCaretPosition(textEditor.getDocument().getLength());
			*/	}

		private static void createAndShowGUI() {
			JFrame frame = new JFrame("Kotes");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			frame.add(new KotesPanel());

			frame.pack();
			frame.setVisible(true);
		}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}
