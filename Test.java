/* Test.java requires no other files. */

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
import java.text.NumberFormat;
import javax.swing.text.Segment;
public class Test extends JPanel implements ActionListener {
	protected JEditorPane textEditor;
	private String text ="";
	private char[] cdata;
	class TextLine{
		private int begin;
		private int end;

		public TextLine(int begin, int end){
			this.begin = begin;
			this.end = end;
		}

		public int getBegin(){
			return begin;
		}

		public int getEnd(){
			return end;
		}
	}

	public Test() {
		super(new GridBagLayout());
		long startTime = System.nanoTime(); 
		loadFile("allnotes.txt");
		System.out.format("Loading file and data manipulation taken: %f seconds%n", (System.nanoTime()-startTime)/1000000000.0);
		System.out.println(Mem.get());
		startTime = System.nanoTime(); 
		ArrayList<TextLine> l = new ArrayList<TextLine>();
		int beg = 0;
		for(int i=0; i<cdata.length; ++i){
			if(cdata[i] == '\n'){
				l.add(new TextLine(beg,i));
				beg = i;
			}
		}
		System.out.format("Fiding all newLines: %f seconds%n", (System.nanoTime()-startTime)/1000000000.0);
		int end = 1;
		System.out.println(Mem.get());
		startTime = System.nanoTime(); 
		for(TextLine t: l){
			if(beg != t.getBegin())
				beg = t.getBegin();
			if(end != t.getEnd())
				end = t.getEnd();
		}

		System.out.format("Iterating Trough lines: %f seconds%n", (System.nanoTime()-startTime)/1000000000.0);
		System.out.println("ok");
		textEditor = new JEditorPane("text/plain",text);
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
		add(new JComboBox());
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 1.0;
		add(scrollPane, c);
	}

	private final void loadFile(String filename){
		try{
			File file = new File(filename);
			InputStreamReader ir = new InputStreamReader(
					new FileInputStream(file), "UTF-8");
			cdata = new char[(int) file.length()];
			int out=0;
			int offset=0;
			while( out != -1){
				out = ir.read(cdata,offset,cdata.length-offset);
				offset += out;
			}
			ir.close();
		} catch (FileNotFoundException e){                                         

		} catch (UnsupportedEncodingException e){                                  

		} catch (IOException e) {                                                  
		}
	}

	private final void loadAllFile(String filename){
		//TODO
		//handle exceptions.
		//Handle empty file.
		try{
			File file = new File(filename);                                      
			FileInputStream fis = new FileInputStream(file);
			byte[] data = new byte[(int) file.length()];
			long startTime = System.nanoTime(); 
			fis.read(data);
			fis.close();
			cdata = new char[(int) file.length()];
			InputStreamReader ir = new InputStreamReader(
					new ByteArrayInputStream(data), "UTF-8");
			int out=0;
			int offset=0;
			while( out != -1){
				out = ir.read(cdata,offset,cdata.length-offset);
				offset += out;
			}
			/*startTime = System.nanoTime(); 
			  String s =new String(data, "UTF-8");	
			  System.out.format("To string: %f seconds%n", (System.nanoTime()-startTime)/1000000000.0);
			  startTime = System.nanoTime(); 
			  cdata = s.toCharArray();	
			  System.out.format("To char: %f seconds%n", (System.nanoTime()-startTime)/1000000000.0);*/
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

			frame.add(new Test());

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
