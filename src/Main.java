import java.util.*;

public class Main {
    static class Flight {
        String flightNumber;
        String source;
        String destination;
        int seatsAvailable;
        double price;

        Flight(String flightNumber, String source, String destination, int seatsAvailable, double price) {
            this.flightNumber = flightNumber;
            this.source = source;
            this.destination = destination;
            this.seatsAvailable = seatsAvailable;
            this.price = price;
        }

        void bookSeat() {
            if (seatsAvailable > 0) {
                seatsAvailable--;
                System.out.println("Booking successful! Remaining seats: " + seatsAvailable);
            } else {
                System.out.println("Sorry, no seats available.");
            }
        }

        @Override
        public String toString() {
            return "Flight: " + flightNumber + ", From: " + source + ", To: " + destination + ", Seats: " + seatsAvailable + ", Price: $" + price;
        }
    }

    static class BookingSystem {
        List<Flight> flights = new ArrayList<>();
        Map<String, String> users = new HashMap<>();
        Map<String, String> loggedInUsers = new HashMap<>();

        void addFlight(Flight flight) {
            flights.add(flight);
        }

        void showFlights(String destination) {
            for (int i = 0; i < flights.size(); i++) {
                if (flights.get(i).destination.equalsIgnoreCase(destination)) {
                    System.out.println((i + 1) + ". " + flights.get(i));
                }
            }
        }

        void bookTicket(int index) {
            if (index >= 0 && index < flights.size()) {
                flights.get(index).bookSeat();
            } else {
                System.out.println("Invalid flight selection.");
            }
        }

        boolean login(String username, String password) {
            if (users.containsKey(username) && users.get(username).equals(password)) {
                loggedInUsers.put(username, password);
                return true;
            }
            return false;
        }

        boolean register(String username, String password) {
            if (!users.containsKey(username)) {
                users.put(username, password);
                return true;
            }
            return false;
        }

        void logout(String username) {
            loggedInUsers.remove(username);
        }

        boolean isLoggedIn(String username) {
            return loggedInUsers.containsKey(username);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BookingSystem system = new BookingSystem();

        // Add some demo flights
        system.addFlight(new Flight("AI101", "New York", "London", 5, 450.00));
        system.addFlight(new Flight("BA202", "Paris", "Tokyo", 3, 800.00));
        system.addFlight(new Flight("DL303", "Sydney", "Dubai", 4, 620.00));

        String currentUser = null;

        while (true) {
            System.out.println("\n=== Online Air Ticket Booking System ===");

            if (currentUser == null) {
                System.out.println("1. Login");
                System.out.println("2. Register");
                System.out.println("3. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        System.out.print("Enter username: ");
                        String loginUsername = scanner.nextLine();
                        System.out.print("Enter password: ");
                        String loginPassword = scanner.nextLine();
                        if (system.login(loginUsername, loginPassword)) {
                            currentUser = loginUsername;
                            System.out.println("Login successful! Welcome " + currentUser);
                        } else {
                            System.out.println("Invalid username or password.");
                        }
                        break;

                    case 2:
                        System.out.print("Enter username: ");
                        String regUsername = scanner.nextLine();
                        System.out.print("Enter password: ");
                        String regPassword = scanner.nextLine();
                        if (system.register(regUsername, regPassword)) {
                            System.out.println("Registration successful! You can now log in.");
                        } else {
                            System.out.println("Username already exists. Please try again.");
                        }
                        break;

                    case 3:
                        System.out.println("Thank you for using the system. Goodbye!");
                        return;

                    default:
                        System.out.println("Invalid choice. Try again.");
                }

            } else {
                System.out.println("Welcome, " + currentUser);
                System.out.println("1. View Available Flights");
                System.out.println("2. Book a Ticket");
                System.out.println("3. View Customer Details");
                System.out.println("4. Logout");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        System.out.print("Enter destination to view flights (e.g., London, Tokyo, Dubai): ");
                        String destination = scanner.nextLine();
                        system.showFlights(destination);
                        break;

                    case 2:
                        System.out.print("Enter destination to view flights: ");
                        String dest = scanner.nextLine();
                        system.showFlights(dest);
                        System.out.print("Select flight number to book: ");
                        int flightIndex = scanner.nextInt() - 1;
                        scanner.nextLine(); // Consume newline
                        system.bookTicket(flightIndex);
                        break;

                    case 3:
                        System.out.println("Customer details for: " + currentUser);
                        System.out.println("Name: " + currentUser);
                        break;

                    case 4:
                        system.logout(currentUser);
                        System.out.println("Logged out successfully!");
                        currentUser = null;
                        break;

                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            }
        }
    }
}
