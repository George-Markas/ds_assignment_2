import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class THClient {
    private static final Logger logger = Logger.getLogger("THClient");
    static {
        try {
            FileHandler fileHandler = new FileHandler("client.log", true);
            fileHandler.setFormatter(new SimpleFormatter()) ;
            logger.addHandler(fileHandler);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            if(args.length < 2) {
                System.out.println("""
                            \033[1m\033[3mUsage:\033[0m
                            THClient
                                list <hostname>
                            THClient
                                book <hostname> <type> <number> <name>
                            THClient
                                guests <hostname>
                            THClient
                                cancel <hostname> <type> <number> <name>
                            """);
                System.exit(0);
            }

            Registry registry = LocateRegistry.getRegistry(args[1], 2222);
            THInterface stub = (THInterface) registry.lookup("THInterface");
            switch (args[0]) {
                case "list": {
                    StringBuilder info = stub.listSeats();
                    System.out.println(info);
                    break;
                }

                case "book": {
                    String requestedId = args[2];
                    int requestedPieces = Integer.parseInt(args[3]);
                    String bookingName = args[4];
                    String transactionResponse = stub.bookInitial(requestedId, requestedPieces, bookingName);
                    if(!transactionResponse.isEmpty()) {
                        System.out.println(transactionResponse);
                    } else if(stub.getAvailableSeats(requestedId) > 0) {
                        System.out.print("There aren't enough seats available, would you like to book the remaining "
                                + stub.getAvailableSeats(requestedId) + " seat(s)? [\033[0;32my\033[0m/\033[0;31mn\033[0m] ");
                        Scanner scanner = new Scanner(System.in);
                        if(scanner.nextLine().equalsIgnoreCase("y")) {
                            transactionResponse = stub.bookInsufficientResponse(requestedId, requestedPieces,
                                    bookingName);
                            System.out.println(transactionResponse);
                        }
                    } else {
                        System.out.println("There aren't enough seats available. Would you like to be added to a mailing " +
                                "list to be notified if seats do become available?" +
                                " [\033[0;32my\033[0m/\033[0;31mn\033[0m] ");

                        Scanner scanner = new Scanner(System.in);
                        if(scanner.nextLine().equalsIgnoreCase("y")) {
                            transactionResponse = stub.addToMailingList(requestedId, bookingName);
                            System.out.println(transactionResponse);
                        }
                    }
                    break;
                }

                case "guests": {
                    StringBuilder bookingInfo = stub.listGuests();
                    System.out.println(bookingInfo);
                    break;
                }

                case "cancel": {
                    String requestedId = args[2];
                    int requestedPieces = Integer.parseInt(args[3]);
                    String bookingName = args[4];
                    String transactionResponse = stub.cancelBooking(requestedId, requestedPieces, bookingName);
                    System.out.println(transactionResponse);
                    break;
                }

                default: {
                    System.out.println("""
                            \033[1m\033[3mUsage:\033[0m
                            THClient
                                list <hostname>
                            THClient
                                book <hostname> <type> <number> <name>
                            THClient
                                guests <hostname>
                            THClient
                                cancel <hostname> <type> <number> <name>
                            """);
                    System.exit(0);
                }
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error has occurred", e);
        }
    }
}
