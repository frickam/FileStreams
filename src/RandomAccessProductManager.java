import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessProductManager {
    private static final int RECORD_SIZE = 122; // 35 (name) + 75 (description) + 6 (ID) + 8 (double cost)

    public void writeProduct(RandomAccessFile file, Product product) throws IOException {
        file.writeBytes(product.getName());
        file.writeBytes(product.getDescription());
        file.writeBytes(product.getID());
        file.writeDouble(product.getCost());
    }

    public Product readProduct(RandomAccessFile file) throws IOException {
        byte[] nameBytes = new byte[35];
        byte[] descriptionBytes = new byte[75];
        byte[] idBytes = new byte[6];

        file.read(nameBytes);
        file.read(descriptionBytes);
        file.read(idBytes);
        double cost = file.readDouble();

        return new Product(new String(nameBytes).trim(), new String(descriptionBytes).trim(), new String(idBytes).trim(), cost);
    }

    public long getRecordCount(RandomAccessFile file) throws IOException {
        return file.length() / RECORD_SIZE;
    }
}