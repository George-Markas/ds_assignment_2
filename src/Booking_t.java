public class Booking_t {
    private String id;
    private int pieces;

    public Booking_t(String id, int pieces) {
        this.id = id;
        this.pieces = pieces;
    }

    public String getId() {
        return id;
    }

    public int getPieces() {
        return pieces;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPieces(int pieces) {
        this.pieces = pieces;
    }
}
