import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class THClient {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry(null, 2222);
            THInterface stub = (THInterface) registry.lookup("THInterface");
            switch (args[0]) {
                case "list":
                    StringBuilder info = stub.list();
                    System.out.println(info);
                    break;
                case "book":
                    String requestedId = args[1];
                    int requestedPieces = Integer.parseInt(args[2]);
                    String bookingName = args[3];
                    String[] transactionResponse = stub.bookInitial(requestedId, requestedPieces, bookingName);
                    if(transactionResponse[1].isEmpty()) {
                        System.out.println(transactionResponse[0]);
                    } else {
                        System.out.print("There aren't enough seats available, would you like to book the remaining "
                                + transactionResponse[2] + " seat(s)? [\033[0;32my\033[0m/\033[0;31mn\033[0m] ");
                        Scanner scanner = new Scanner(System.in);
                        if(scanner.nextLine().equalsIgnoreCase("y")) {
                            transactionResponse[0] = stub.bookInsufficientResponse(requestedId, requestedPieces,
                                    bookingName, transactionResponse);
                            System.out.println(transactionResponse[0]);
                        }
                    }
                case "guests":
                    // code
                    break;
                case "cancel":
                    // code
                    break;
                default:
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
        } catch (Exception e) {
            System.err.println("Client exception: " + e);
            e.printStackTrace();
        }
    }
}
