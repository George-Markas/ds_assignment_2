import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Enumeration;

public class THImpl extends UnicastRemoteObject implements THInterface {
    private final Hashtable<String, Seat_t> seats;
    private final Hashtable<String, ArrayList<Booking_t>> bookings;

    protected THImpl(Hashtable<String, Seat_t> seats, Hashtable<String, ArrayList<Booking_t>> bookings) throws RemoteException {
        super();
        this.seats = seats;
        this.bookings = bookings;
    }

    @Override
    public StringBuilder listSeats() {
        Enumeration<String> keys = seats.keys();
        StringBuilder info = new StringBuilder();
        while(keys.hasMoreElements()) {
            String key = keys.nextElement();
            Seat_t temp = seats.get(key);
            info.append(temp.getAvailable())
                    .append(" Seats ")
                    .append(temp.getPrettyName())
                    .append(" (ID: ")
                    .append(key)
                    .append(") ")
                    .append("- price: $")
                    .append(temp.getPricePerPiece())
                    .append("\n");
        }
        return info;
    }

    @Override
    public int getAvailableSeats(String id) {
        return seats.get(id).getAvailable();
    }

    @Override
    public String bookInitial(String id, int pieces, String name) {
        String transactionResult = "Unknown error; please contact the service provider, sorry for the inconvenience";

        if(seats.containsKey(id)) {
            Seat_t temp = seats.get(id);
            if(temp.getAvailable() >= pieces) {
                if(!bookings.containsKey(name)) {
                    bookings.put(name, new ArrayList<>());
                }
                bookings.get(name).add(new Booking_t(id, pieces));
                temp.updateAvailable(temp.getAvailable() - pieces);
                transactionResult = "Successful booking for " + temp.getPrettyName() + "; " + pieces + " seat(s)";
            } else if(temp.getAvailable() > 0) {
                transactionResult = "";
            }
        } else {
            transactionResult = "Error: the given ID doesn't correspond to any seat type";
        }

        return transactionResult;
    }

    @Override
    public String bookInsufficientResponse(String id, int pieces, String name) {
        if(!bookings.containsKey(name)) {
            bookings.put(name, new ArrayList<>());
        }
        bookings.get(name).add(new Booking_t(id, pieces));
        Seat_t temp = seats.get(id);
        String transactionResponse = "Successful booking for " + temp.getPrettyName() + "; "
                + temp.getAvailable() + " seat(s)";
        temp.updateAvailable(0);

        return transactionResponse;
    }

    public StringBuilder listGuests() {
        Enumeration<String> keys = bookings.keys();
        StringBuilder bookingInfo = new StringBuilder();
        ArrayList<Booking_t> tempBooking;
        while(keys.hasMoreElements()) {
            String key = keys.nextElement();
            tempBooking = bookings.get(key);
            for(Booking_t temp : tempBooking) {
                bookingInfo.append(key)
                        .append("; ")
                        .append(temp.getPieces())
                        .append(" for ")
                        .append(seats.get(temp.getId()).getPrettyName())
                        .append("; total: $")
                        .append(seats.get(temp.getId()).getPricePerPiece() * temp.getPieces())
                        .append("\n");
            }
        }
        return bookingInfo;
    }

//    public String cancelBooking(String name) {
//
//    }
}
