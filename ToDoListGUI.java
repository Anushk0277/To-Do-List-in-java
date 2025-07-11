import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class ToDoListGUI extends JFrame {
    private DefaultListModel<String> taskListModel;
    private JList<String> taskList;
    private JTextField taskInput;
    private final String FILE_NAME = "tasks.txt";

    public ToDoListGUI() {
        setTitle("🌟 To-Do List App");
        setSize(450, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Set app background
        getContentPane().setBackground(new Color(245, 248, 255));

        // Top Panel
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));
        inputPanel.setBackground(new Color(220, 230, 255));

        taskInput = new JTextField();
        taskInput.setFont(new Font("SansSerif", Font.PLAIN, 16));
        taskInput.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        inputPanel.add(taskInput, BorderLayout.CENTER);

        JButton addButton = new JButton("Add");
        addButton.setBackground(new Color(66, 133, 244));
        addButton.setForeground(Color.WHITE);
        addButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        addButton.setFocusPainted(false);
        inputPanel.add(addButton, BorderLayout.EAST);

        // Task List
        taskListModel = new DefaultListModel<>();
        taskList = new JList<>(taskListModel);
        taskList.setFont(new Font("SansSerif", Font.PLAIN, 16));
        taskList.setBackground(new Color(255, 255, 255));
        taskList.setSelectionBackground(new Color(255, 204, 153));
        JScrollPane scrollPane = new JScrollPane(taskList);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        // Delete Button
        JButton deleteButton = new JButton("Delete Selected");
        deleteButton.setBackground(new Color(219, 68, 55));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        deleteButton.setFocusPainted(false);
        deleteButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(245, 248, 255));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        bottomPanel.add(deleteButton);

        // Load tasks from file
        loadTasks();

        // Listeners
        addButton.addActionListener(e -> addTask());
        deleteButton.addActionListener(e -> deleteSelectedTask());

        // Save on close
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                saveTasks();
            }
        });

        // Add components to frame
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void addTask() {
        String task = taskInput.getText().trim();
        if (!task.isEmpty()) {
            taskListModel.addElement("📝 " + task);
            taskInput.setText("");
        }
    }

    private void deleteSelectedTask() {
        int index = taskList.getSelectedIndex();
        if (index != -1) {
            taskListModel.remove(index);
        }
    }

    private void loadTasks() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String task;
            while ((task = reader.readLine()) != null) {
                taskListModel.addElement(task);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "⚠️ Error loading tasks.");
        }
    }

    private void saveTasks() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (int i = 0; i < taskListModel.size(); i++) {
                writer.write(taskListModel.get(i));
                writer.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "⚠️ Error saving tasks.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ToDoListGUI().setVisible(true));
    }
}
