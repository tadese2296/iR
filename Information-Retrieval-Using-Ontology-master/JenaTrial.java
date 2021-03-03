import java.util.Iterator;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import org.apache.jena.arq.*;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.query.ParameterizedSparqlString;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.update.UpdateRequest;
import com.bbn.parliament.jena.joseki.client.RemoteModel;
import java.util.*;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTable;
import java.awt.Label;
import java.awt.TextArea;

import javax.swing.JTextArea;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTable;
import java.awt.Label;
import javax.swing.JTextArea;

import java.awt.Desktop;
import java.net.URI;
import java.net.URL;
import javax.swing.JList;

public class JenaTrial {

	private JFrame frame;
	private final JTextField textField = new JTextField();
	private JTextField textField_1;
	private JTextField textField_2;
	private JTable table;
	private JTable table_1;
	private JTextField textField_3;
	private JTextField textField_4;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JenaTrial window = new JenaTrial();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public JenaTrial() {
		initialize();
	}

	
	private void initialize() {
		
		frame = new JFrame();
		frame.setBounds(100, 100, 700, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		textField.setBounds(0, 362, 444, -84);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(117, 1, 239, 26);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		
		textField_2 = new JTextField();
		textField_2.setBounds(117, 56, 239, 26);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);
		
		TextArea textArea = new TextArea();
		textArea.setBounds(27, 162, 600, 200);
		frame.getContentPane().add(textArea);

		JButton btnNewButton_3 = new JButton("Get Map");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				 
				 
				 try
				 {
					 URI uri = new URL("file:///C:/Users/dell/Desktop/maps.html").toURI();
					 Desktop.getDesktop().browse(uri);
					 
					 
				 }
				 
				catch (IOException e1)
				{

					e1.printStackTrace();

				}
				 
				catch (Exception e2)
				{
	
					e2.printStackTrace();
	
				}				 
				 
				 finally
				 {
					 
				 }
				
			}
		});
		

		btnNewButton_3.setBounds(507, 128, 117, 29);
		frame.getContentPane().add(btnNewButton_3);
		
		JButton btnNewButton = new JButton("Cycle");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String src = textField_1.getText();
				String dest = textField_2.getText();
				
				HelloRDF myClass= new HelloRDF();
				ArrayList<Locations> tracePath= new ArrayList<Locations>();
				String ans = myClass.first(src,dest,"2",tracePath);
				
				textArea.insert(ans,0);
				
				CreateJsFile cjs= new CreateJsFile();
				cjs.create(tracePath);
				
				
			}
		});
		btnNewButton.setBounds(17, 128, 117, 29);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Walk");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String src = textField_1.getText();
				String dest = textField_2.getText();
				ArrayList<Locations> tracePath= new ArrayList<Locations>();
				
				HelloRDF myClass= new HelloRDF();
				
				String ans = myClass.first(src,dest,"3",tracePath);
				
				textArea.insert(ans,0);

				CreateJsFile cjs= new CreateJsFile();
				cjs.create(tracePath);
				
				
			}
		});
		btnNewButton_1.setBounds(137, 128, 117, 29);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Motor");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				

				
				String src = textField_1.getText();
				String dest = textField_2.getText();
				
				HelloRDF myClass= new HelloRDF();
				
				ArrayList<Locations> tracePath= new ArrayList<Locations>();
				
				String ans = myClass.first(src,dest,"1",tracePath);
				
				textArea.insert(ans,0);
				
				CreateJsFile cjs= new CreateJsFile();
				cjs.create(tracePath);
				
			}
		});
		btnNewButton_2.setBounds(267, 128, 117, 29);
		frame.getContentPane().add(btnNewButton_2);



		
		
		
		
		JLabel lblNewLabel = new JLabel("Destination");
		lblNewLabel.setBounds(17, 61, 86, 16);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Source");
		lblNewLabel_1.setBounds(17, 6, 61, 16);
		frame.getContentPane().add(lblNewLabel_1);

		
		
		
		
	}
}




