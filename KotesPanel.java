/* KotesPanel.java requires no other files. */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
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

	public KotesPanel() {
		super(new GridBagLayout());
		Entries = new ArrayList<Entry>();
		loadFile("allnotes.txt");
		System.out.println(Mem.get());
		textEditor = new JEditorPane("text/plain",text);
		JTabbedPane tabbedPane = new JTabbedPane();
		contentManager = new TextContentManager(tagMap, textEditor, tabbedPane);
		comboBox = new AutoComboBox(autocomplete, contentManager);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		tabbedPane.addTab("",null);
		tabbedPane.addTab("+",null);
		tabbedPane.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				if( e.getSource() instanceof JTabbedPane){
					JTabbedPane pane = (JTabbedPane) e.getSource();
					int index = pane.getSelectedIndex();
					if(index != -1)
					if( index + 1 == pane.getTabCount()){
						pane.setSelectedIndex(-1);
						pane.insertTab("",null,null,null,index);	
						pane.setSelectedIndex(index);
					}else{
						contentManager.setTag(pane.getTitleAt(index));
					}
				}
			}
		});
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
		add(tabbedPane, c);
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
			Tags[0] = Tags[0].substring(1);
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
			if( evt.getSource() instanceof AbstractButton){
				String cmd = ((AbstractButton)evt.getSource()).getText();
				try{
					if(cmd.equals("Save")){
						FileWriter out = new FileWriter("tmp.txt");
						out.write(textEditor.getText());
						out.close();
					}
				}catch(Exception f){
					f.printStackTrace();
				}
			}
	 	}

		private static void createAndShowGUI() {
			JFrame frame = new JFrame("Kotes");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			KotesPanel kotes = new KotesPanel();
		JMenu menu = new JMenu("File");
		JMenuItem item = new JMenuItem("Save");
		item.addActionListener(kotes);
		menu.add(item);
		JMenuBar bar = new JMenuBar();
		bar.add(menu);
		frame.setJMenuBar(bar);
			frame.add(kotes);

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
