package assignment2;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JLabel;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import javax.swing.table.DefaultTableCellRenderer;

public class Display extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	JPanel mainMenu, completeScreen, mainScreen, outputPanel, errorPanel;
	Stack<JPanel> panels;
	Container contentPane;
	
	public Display()
	{
		contentPane = getContentPane();
		panels = new Stack<JPanel>();
		
		displayMainScreen();
		
	}
	
	// Construct main screen
	public void displayMainScreen() 
	{
		
		contentPane.removeAll();
		
		String[] columnNames = {"Product ID", "Product Name", "Unit Price", "Quantity", "Total Price"};
		
		mainScreen = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		JTable transactionsTable = new JTable(CashRegister.convertedItems, columnNames);
		
		DefaultTableCellRenderer rightAlign = new DefaultTableCellRenderer();
		rightAlign.setHorizontalAlignment(JLabel.RIGHT);
		transactionsTable.getColumnModel().getColumn(2).setCellRenderer(rightAlign);
		
		JScrollPane scrollPane = new JScrollPane(transactionsTable);
		gbc.gridx = 0;
		gbc.gridy = 0;
		mainScreen.add(scrollPane);

		outputPanel = new JPanel();
		errorPanel = new JPanel();
		
		addPanel(mainScreen);
	}
	
	// Adds new panel on top of current one
	public void addPanel(JPanel newPanel) 
	{
		if (newPanel != mainScreen) 
		{
			panels.peek().setVisible(false);
		}
		
		contentPane.add(newPanel);
		panels.push(newPanel);
		contentPane.repaint();
		contentPane.revalidate();
	}
	
	
	// Displays message at bottom of screen
	public void display(String text)
	{
		outputPanel.removeAll();
		outputPanel.add(new JLabel(text));	
		
		getContentPane().add(outputPanel, BorderLayout.SOUTH);
		
		contentPane.repaint();
		contentPane.revalidate();
	}
	
	// Displays error message
	public void displayError(String text)
	{
		errorPanel.removeAll();
		errorPanel.add(new JLabel(text));	
		
		getContentPane().add(errorPanel, BorderLayout.EAST);
		
		contentPane.repaint();
		contentPane.revalidate();
	}
	
	// Clears all messages and refreshes the screen
	public void refresh() {
		
		errorPanel.removeAll();
		outputPanel.removeAll();
		contentPane.repaint();
		contentPane.revalidate();
	}
	
}
