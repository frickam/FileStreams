import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.RandomAccessFile;

public class RandProductMaker extends JFrame {
    private JTextField nameField, descriptionField, idField, costField, recordCountField;
    private RandomAccessFile file;

    public RandProductMaker() {
        super("Random Product Maker");
        setLayout(new GridLayout(6, 2));

        // Initialize components
        nameField = new JTextField();
        descriptionField = new JTextField();
        idField = new JTextField();
        costField = new JTextField();
        recordCountField = new JTextField();
        recordCountField.setEditable(false);

        JButton addButton = new JButton("Add Product");

        // Add components to frame
        add(new JLabel("Name:"));
        add(nameField);
        add(new JLabel("Description:"));
        add(descriptionField);
        add(new JLabel("ID:"));
        add(idField);
        add(new JLabel("Cost:"));
        add(costField);
        add(new JLabel("Records Count:"));
        add(recordCountField);
        add(addButton);

        // Add button listener
        addButton.addActionListener(e -> addProduct());

        // File setup
        try {
            File dataFile = new File("products.dat");
            file = new RandomAccessFile(dataFile, "rw");
            updateRecordCount();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error opening file: " + ex.getMessage());
        }

        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void addProduct() {
        try {
            String name = nameField.getText();
            String description = descriptionField.getText();
            String id = idField.getText();
            double cost = Double.parseDouble(costField.getText());

            if (name.isEmpty() || description.isEmpty() || id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields must be filled!");
                return;
            }
            if (id.length() != 6) {
                JOptionPane.showMessageDialog(this, "ID must be 6 characters long!");
                return;
            }

            Product product = new Product(name, description, id, cost);
            file.seek(file.length());
            RandomAccessProductManager manager = new RandomAccessProductManager();
            manager.writeProduct(file, product);

            JOptionPane.showMessageDialog(this, "Product added successfully!");
            nameField.setText("");
            descriptionField.setText("");
            idField.setText("");
            costField.setText("");
            updateRecordCount();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error adding product: " + ex.getMessage());
        }
    }

    private void updateRecordCount() {
        try {
            RandomAccessProductManager manager = new RandomAccessProductManager();
            long count = manager.getRecordCount(file);
            recordCountField.setText(String.valueOf(count));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error updating record count: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new RandProductMaker();
    }
}