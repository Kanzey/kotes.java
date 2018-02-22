/* KotesPanel.java requires no other files. */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Vector;
public class KotesPanel extends JPanel implements ActionListener {
	private TextAreaContentManager contentManager;
	protected JComboBox comboBox;	
	protected JTextArea textArea;
	private List<Entry> Entries;
	private Vector<String> autocomplete;
	private String text ="";
	private final static String newline = "\n";

	public KotesPanel() {
		super(new GridBagLayout());
		Entries = new ArrayList<Entry>();
		loadFile("allnotes.txt");
		textArea = new JTextArea(text,5, 20);
		contentManager = new TextAreaContentManager(Entries, textArea);
		comboBox = new AutoComboBox(autocomplete, contentManager);
		//		comboBox.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
		//		comboBox.setFocusTraversalKeysEnabled(false);
			//textField.addActionListener(this);
		textArea.setEditable(true);
		JScrollPane scrollPane = new JScrollPane(textArea);

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
			String[] Tags = new String(data, "UTF-8").split("\\r?\\n#");
			for(String s: Tags){
				String[] Tag = s.split("\\r?\\n",2);
				String text = "";
				if(Tag.length == 2)
					text = Tag[1];
				Entries.add( new Entry(Tag[0].trim(), text));
			}
			Entries.get(0).repair();
			HashSet<String> hs= new HashSet<String>();
			for(Entry e: Entries){
				for(String s:e.getTags()){
					hs.add(s);
				}
			}
			autocomplete = new Vector<String>(hs);
			System.out.println(hs.size());
			for(String s: hs){
				System.out.println(s);
			}
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
			textArea.setText(Entries.get(i).getText());
			}catch(NumberFormatException e){
			}
			textField.selectAll();

		textArea.setCaretPosition(textArea.getDocument().getLength());
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
