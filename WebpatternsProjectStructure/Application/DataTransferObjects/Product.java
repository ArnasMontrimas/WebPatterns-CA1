package DataTransferObjects;

import java.util.Objects;

/**
 * @author Michelle
 */
public class Product 
{
    private String code;
    private String name;
    private String line;
    private String scale;
    private String vendor;
    private String description;
    private int qtyInStock;
    private double buyPrice;
    private double msrp;

    public Product()
    {
        
    }
    
    public Product(String code, String name, String line, String scale, String vendor, String description, int qtyInStock, double buyPrice, double msrp) {
        this.code = code;
        this.name = name;
        this.line = line;
        this.scale = scale;
        this.vendor = vendor;
        this.description = description;
        this.qtyInStock = qtyInStock;
        this.buyPrice = buyPrice;
        this.msrp = msrp;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQtyInStock() {
        return qtyInStock;
    }

    public void setQtyInStock(int qtyInStock) {
        this.qtyInStock = qtyInStock;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public double getMsrp() {
        return msrp;
    }

    public void setMsrp(double msrp) {
        this.msrp = msrp;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Product other = (Product) obj;
        if (!Objects.equals(this.code, other.code)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Product{" + "code=" + code + ", name=" + name + ", line=" + line + ", scale=" + scale + ", vendor=" + vendor + ", description=" + description + ", qtyInStock=" + qtyInStock + ", buyPrice=" + buyPrice + ", msrp=" + msrp + '}';
    }
}
