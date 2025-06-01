public class Seat_t {
    private final String prettyName;
    private int available;
    private final double pricePerPiece;

    public Seat_t(String prettyName, int available, double pricePerPiece) {
        this.prettyName = prettyName;
        this.available = available;
        this.pricePerPiece = pricePerPiece;
    }

    public String getPrettyName() {
        return prettyName;
    }

    public int getAvailable() {
        return available;
    }

    public double getPricePerPiece() {
        return pricePerPiece;
    }

    public void updateAvailable(int newValue) {
        available = newValue;
    }
}
