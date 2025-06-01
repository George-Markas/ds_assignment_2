public class Booking_t {
    private String id;
    private int pieces;
    private String name;

    public Booking_t(String id, int pieces, String name) {
        this.id = id;
        this.pieces = pieces;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public int getPieces() {
        return pieces;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPieces(int pieces) {
        this.pieces = pieces;
    }

    public void setName(String name) {
        this.name = name;
    }
}
