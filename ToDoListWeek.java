package projects;


import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JLabel;

public class ToDoListWeek extends JFrame{
	
	private Map<String, List<Task>>weeklyTasks;
	private DefaultListModel<Task>listModel;
	private JList<Task>itemList;
	private JTextField itemField;
	private JComboBox<Task.Priority>priorityComboBox;
	private JComboBox<String>dayComboBox;
	
	public ToDoListWeek() {
		setTitle("To-Do List Weekly Application");
		setSize(500, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		weeklyTasks = new HashMap<>();
		initializeWeeklyTasks();
		
		listModel = new DefaultListModel<>();
		itemList = new JList<>(listModel);
		itemField = new JTextField(20);
		priorityComboBox = new JComboBox<>(Task.Priority.values());
		dayComboBox = new JComboBox<>(new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"});
	
		JScrollPane scrollPane = new JScrollPane(itemList);
		JButton addButton = new JButton("Add");
		JButton removeButton = new JButton("Remove");
		JButton markDoneButton = new JButton("Mark Done");
		JButton sortButton = new JButton("Sort");
		
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String newItem = itemField.getText().trim();
				if(!newItem.isEmpty()) {
					Task.Priority priority = (Task.Priority) priorityComboBox.getSelectedItem();
					String selectedDay = (String) dayComboBox.getSelectedItem();
					Task task = new Task(newItem, false, priority);
					weeklyTasks.get(selectedDay).add(task);
					updateList(selectedDay);
					itemField.setText("");
					
				}
				
			}
		});
		
		removeButton.addActionListener(new ActionListener(){
		
		public void actionPerformed(ActionEvent e){
		Integer selectedIndex = itemList.getSelectedIndex();
		if(selectedIndex != -1){
		String selectedDay = (String) dayComboBox.getSelectedItem();
		List<Task> tasks = weeklyTasks.get(selectedDay);
		tasks.remove(selectedIndex);
		updateList(selectedDay);
		}
		}
	});
	
	markDoneButton.addActionListener(new ActionListener(){
	  public void actionPerformed(ActionEvent e){
	  Integer selectedIndex = itemList.getSelectedIndex();
	  if(selectedIndex != -1){
	  String selectedDay = (String)dayComboBox.getSelectedItem();
	  Task task = itemList.getSelectedValue();
	  task.setDone(true);
	  updateList(selectedDay);
	  
	}
	}
	});
	
	sortButton.addActionListener(new ActionListener(){
	public void actionPerformed(ActionEvent e){
	String selectedDay = (String) dayComboBox.getSelectedItem();
	sortTasks(selectedDay);
	
	}
	});
	
	JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
	inputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
	inputPanel.add(new JLabel("Day:"));
	inputPanel.add(dayComboBox);
	inputPanel.add(new JLabel("Task:"));
	inputPanel.add(itemField);
	inputPanel.add(new JLabel("Priority:"));
	inputPanel.add(priorityComboBox);
	
	
	JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
	buttonPanel.setBorder(new EmptyBorder(0, 10, 10, 10));
	buttonPanel.add(addButton);
	buttonPanel.add(removeButton);
	buttonPanel.add(markDoneButton);
	buttonPanel.add(sortButton);
	
	JPanel controlPanel = new JPanel(new BorderLayout());
	controlPanel.add(inputPanel, BorderLayout.NORTH);
	controlPanel.add(buttonPanel, BorderLayout.CENTER);
	
	setLayout(new BorderLayout());
	add(controlPanel, BorderLayout.NORTH);
	add(scrollPane, BorderLayout.CENTER);
	
	}
	
	private void initializeWeeklyTasks(){
	String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
	for(String day : daysOfWeek){
	weeklyTasks.put(day, new ArrayList<>());
	
	}
	
	}
	
	private void updateList(String day){
	listModel.clear();
	List<Task> tasks = weeklyTasks.get(day);
	for(Task task : tasks){
	listModel.addElement(task);
	}}
	
	private void sortTasks(String day){
	List<Task> tasks = weeklyTasks.get(day);
	tasks.sort(Task::compareTo);
	updateList(day);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

     SwingUtilities.invokeLater(() -> {
        ToDoListWeek app = new ToDoListWeek();
        app.setVisible(true);
        
	});
	}

private static class Task implements Comparable<Task>{
private String name;
private boolean done;
private Priority priority;

public enum Priority{
LOW, NORMAL, HIGH
}
  public Task(String name, boolean done, Priority priority){
  this.name = name;
  this.done = done;
  this.priority = priority;
  
}

  public String getName(){
           return name;
}
   public boolean isDone(){
    return done;
    }
    
    public void setDone(boolean done){
      this.done = done;
      }
      
      public Priority getPriority(){
         return priority;
         }
      
public String toString(){
return name + "(Priority: " + priority + ", Done: " + done + ")";

}

public int compareTo(Task other){
  return this.priority.compareTo(other.priority);
  }
  }
  }