import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Main extends JFrame implements ActionListener {
    private JTextField textFieldID, textFieldName, textFieldPrice, textFieldAuthor;
    private JButton addButton, deleteButton, updateButton, showButton; // Tambahkan tombol baru
    private Mysql mySQL;

    public Main() {
        setTitle("Database Management System");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Database connection
        mySQL = new Mysql("5mn.h.filess.io", "tugasprojek_tradelocal", "3307", "tugasprojek_tradelocal",
                "dde43f794b09000a49cc9b9aac807a6af77197d3");

        // Components
        JLabel labelID = new JLabel("ID:");
        JLabel labelName = new JLabel("Name:");
        JLabel labelPrice = new JLabel("Price:");
        JLabel labelAuthor = new JLabel("Author:");

        textFieldID = new JTextField(10);
        textFieldName = new JTextField(20);
        textFieldPrice = new JTextField(10);
        textFieldAuthor = new JTextField(20);

        addButton = new JButton("Add");
        deleteButton = new JButton("Delete");
        updateButton = new JButton("Update");
        showButton = new JButton("Show Data"); // Inisialisasi tombol baru

        addButton.addActionListener(this);
        deleteButton.addActionListener(this);
        updateButton.addActionListener(this);
        showButton.addActionListener(this); // Tambahkan ActionListener untuk tombol baru

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2));
        inputPanel.add(labelID);
        inputPanel.add(textFieldID);
        inputPanel.add(labelName);
        inputPanel.add(textFieldName);
        inputPanel.add(labelPrice);
        inputPanel.add(textFieldPrice);
        inputPanel.add(labelAuthor);
        inputPanel.add(textFieldAuthor);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(showButton); // Tambahkan tombol baru ke panel

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(inputPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            addData();
        } else if (e.getSource() == deleteButton) {
            deleteData();
        } else if (e.getSource() == updateButton) {
            updateData();
        } else if (e.getSource() == showButton) { // Tambahkan kondisi untuk tombol baru
            showData();
        }
    }

    private void addData() {
        try {
            Connection conn = mySQL.connect();
            String sql = "INSERT INTO rak_buku (judul, penulis) VALUES (?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, textFieldName.getText());
            statement.setString(2, textFieldAuthor.getText());
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Data added successfully");
            }
            conn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void deleteData() {
        try {
            Connection conn = mySQL.connect();
            String sql = "DELETE FROM rak_buku WHERE id=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(textFieldID.getText()));
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(this, "Data deleted successfully");
            } else {
                JOptionPane.showMessageDialog(this, "No such ID exists");
            }
            conn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void updateData() {
        try {
            Connection conn = mySQL.connect();
            String sql = "UPDATE rak_buku SET judul=?, penulis=? WHERE id=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, textFieldName.getText());
            statement.setString(2, textFieldAuthor.getText());
            statement.setInt(3, Integer.parseInt(textFieldID.getText()));
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Data updated successfully");
            } else {
                JOptionPane.showMessageDialog(this, "No such ID exists");
            }
            conn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void showData() {
        try {
            Connection conn = mySQL.connect();
            Statement statement = conn.createStatement();
            String sql = "SELECT * FROM rak_buku";
            ResultSet resultSet = statement.executeQuery(sql);

            StringBuilder data = new StringBuilder("ID\tJudul\tPenulis\n");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String judul = resultSet.getString("judul");
                String penulis = resultSet.getString("penulis");
                data.append(id).append("\t").append(judul).append("\t").append(penulis).append("\n");
            }

            JOptionPane.showMessageDialog(this, new JTextArea(data.toString()));
            conn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}
