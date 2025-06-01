import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Enumeration;

public class THImpl extends UnicastRemoteObject implements THInterface {
    private final Hashtable<String, Seat_t> seats;
    private final Hashtable<String, ArrayList<Booking_t>> bookings;
    private final Hashtable<String, String> mailingList;

    protected THImpl(Hashtable<String, Seat_t> seats, Hashtable<String, ArrayList<Booking_t>> bookings, Hashtable<String, String> mailingList) throws RemoteException {
        super();
        this.seats = seats;
        this.bookings = bookings;
        this.mailingList = mailingList;
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
                transactionResult = "__NON_EMPTY__";
            } else {
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

    public String cancelBooking(String id, int pieces, String name) {
        String transactionResponse = "Unknown error; please contact the service provider, sorry for the inconvenience";
        if(!bookings.containsKey(name)) {
            transactionResponse = "There are no bookings under the provided name";
        } else {
            ArrayList<Booking_t> personalBookings = bookings.get(name);
            for(Booking_t temp : personalBookings) {
                if(temp.getId().equals(id) && temp.getPieces() > pieces) {
                    temp.updatePieces(temp.getPieces() - pieces);
                    seats.get(id).updateAvailable(seats.get(id).getAvailable() + pieces);
                    transactionResponse = "Cancellation successful; " + pieces + " seat(s) removed from the booking";
                    break;
                } else if(temp.getId().equals(id)) {
                    seats.get(id).updateAvailable(seats.get(id).getAvailable() + pieces);
                    personalBookings.remove(temp);
                    transactionResponse = "Cancellation successful; booking fully undone";
                    break;
                }
            }

        }

        return transactionResponse;
    }

    public String addToMailingList(String id, String name) {
        String transactionResponse = "Unknown error; please contact the service provider, sorry for the inconvenience";
        try {
            String clientHost = RemoteServer.getClientHost();
            mailingList.put(name, clientHost);
            transactionResponse = "Successfully added to the mailing list";
        } catch(ServerNotActiveException e) {
            transactionResponse = "Error: couldn't get client host" + e.getMessage();
        }
        return transactionResponse;
    }
}
