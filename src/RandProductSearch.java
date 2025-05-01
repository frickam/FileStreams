import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class RandProductSearch extends JFrame {
    private JTextField searchField;
    private JTextArea resultArea;
    private RandomAccessFile file;

    public RandProductSearch() {
        super("Random Product Search");
        setLayout(new BorderLayout());

        // Initialize components
        searchField = new JTextField();
        JButton searchButton = new JButton("Search");
        resultArea = new JTextArea(15, 40);
        resultArea.setEditable(false);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(new JLabel("Enter partial product name:"), BorderLayout.WEST);
        topPanel.add(searchField, BorderLayout.CENTER);
        topPanel.add(searchButton, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);

        // Add button listener
        searchButton.addActionListener(e -> searchProducts());

        // File setup
        try {
            File dataFile = new File("products.dat");
            file = new RandomAccessFile(dataFile, "r");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error opening file: " + ex.getMessage());
        }

        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void searchProducts() {
        try {
            String searchTerm = searchField.getText().toLowerCase();
            if (searchTerm.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a search term.");
                return;
            }

            resultArea.setText(""); // Clear previous results
            RandomAccessProductManager manager = new RandomAccessProductManager();
            file.seek(0); // Start from the beginning of the file
            boolean found = false;

            while (file.getFilePointer() < file.length()) {
                Product product = manager.readProduct(file);
                if (product.getName().toLowerCase().contains(searchTerm)) {
                    resultArea.append(product.toString() + "\n");
                    found = true;
                }
            }

            if (!found) {
                resultArea.setText("No products found matching: " + searchTerm);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error searching products: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new RandProductSearch();
    }
}