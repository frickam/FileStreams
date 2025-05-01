import java.io.Serializable;
import java.util.Objects;

public class Product implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name = "";
    private String description = "";
    private String ID = "";
    private double cost = 0.00;

    // Fixed lengths for fields
    private static final int NAME_LENGTH = 35;
    private static final int DESCRIPTION_LENGTH = 75;
    private static final int ID_LENGTH = 6;

    public Product(String name, String description, String ID, double cost) {
        this.name = padString(name, NAME_LENGTH);
        this.description = padString(description, DESCRIPTION_LENGTH);
        this.ID = padString(ID, ID_LENGTH);
        this.cost = cost;
    }

    // Pad string to fixed length
    private String padString(String str, int length) {
        if (str.length() > length) {
            return str.substring(0, length);
        } else {
            return String.format("%-" + length + "s", str);
        }
    }

    // Trim string for processing
    private String trimString(String str) {
        return str.trim();
    }

    public String getName() {
        return trimString(name);
    }

    public void setName(String name) {
        this.name = padString(name, NAME_LENGTH);
    }

    public String getDescription() {
        return trimString(description);
    }

    public void setDescription(String description) {
        this.description = padString(description, DESCRIPTION_LENGTH);
    }

    public String getID() {
        return trimString(ID);
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", ID='" + getID() + '\'' +
                ", cost=" + cost +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.cost, cost) == 0 &&
                Objects.equals(name, product.name) &&
                Objects.equals(description, product.description) &&
                Objects.equals(ID, product.ID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, ID, cost);
    }
}