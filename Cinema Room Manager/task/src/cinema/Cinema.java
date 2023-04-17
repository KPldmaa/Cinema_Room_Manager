package cinema;


import java.util.Locale;
import java.util.Scanner;

public class Cinema {

    public static final int SMALL_ROOM_CAP = 60;
    public static final int START_ROW = 1;
    public static final int START_COL = 1;
    public static final int REDUCED_PRICE = 8;
    public static final int FULL_PRICE = 10;
    public static final char SEAT_AVAILABLE = 'S';
    public static final char SEAT_BOOKED = 'B';

    public static void main(String[] args) {
        // Write your code here
        Scanner scanner = new Scanner(System.in);
        Seat[][] cinemaRoom = createCinemaRoom(scanner);
        //printCinemaRoom(cinemaRoom);
        //bookAndPrintTicketPrice(scanner, cinemaRoom);
        //printCinemaRoom(cinemaRoom);

        boolean runProgram = true;

        while (runProgram) {
            cinemaMenu.printMenu();
            String input = scanner.nextLine();
            cinemaMenu menuItem = cinemaMenu.getMenuItemFromInput(input);

            switch (menuItem) {
                case SHOW_SEATS -> printCinemaRoom(cinemaRoom);
                case BUY_TICKETS -> bookAndPrintTicketPrice(scanner, cinemaRoom);
                case STATISTICS -> printStatistics(cinemaRoom);
                case EXIT -> {
                    System.out.println();
                    runProgram = false;
                }
            }
        }

        // printTotalIncome(cinemaRoom);
    }


    private static Seat[][] createCinemaRoom(Scanner scanner) {
        int rows = getRowsFromInput(scanner);
        int seats = getSeatsFromInput(scanner);

        Seat[][] cinemaRoom = new Seat[rows + START_ROW][seats + START_COL];

        boolean isLargeCinemaRoom = rows * seats > SMALL_ROOM_CAP;
        int firstReducedPriceRowIndex = (rows / 2) + START_ROW;

        for (int row = START_ROW; row < cinemaRoom.length; row++) {
            for (int col = START_COL; col < cinemaRoom[row].length; col++) {
                Seat seat = new Seat();
                if (isLargeCinemaRoom && row >= firstReducedPriceRowIndex) {
                    seat.setPrice(REDUCED_PRICE);
                }

                cinemaRoom[row][col] = seat;
            }
        }
        return cinemaRoom;

    }

    private static int getSeatsFromInput(Scanner scanner) {
        System.out.println("Enter the number of seats in each row:");
        return Integer.parseInt(scanner.nextLine());
    }

    private static int getRowsFromInput(Scanner scanner) {
        System.out.println("\nEnter the number of rows:");
        return Integer.parseInt(scanner.nextLine());
    }

    private static void printCinemaRoom(Seat[][] cinemaRoom) {
        printCinemaRoomHeader(cinemaRoom);
        printCinemaRoomSeats(cinemaRoom);

    }

    private static void printCinemaRoomHeader(Seat[][] cinemaRoom) {
        System.out.println("\nCinema:");
        System.out.print(" ");
        for (int col = START_COL; col < cinemaRoom[START_ROW].length; col++) {
            System.out.print(" " + col);
        }
        System.out.println();

    }

    private static void printCinemaRoomSeats(Seat[][] cinemaRoom) {
        for (int row = START_ROW; row < cinemaRoom.length; row++) {
            System.out.print(row);
            for (int col = START_COL; col < cinemaRoom[row].length; col++) {
                System.out.print(" " + cinemaRoom[row][col].getAvailabilityChar());
            }
            System.out.println();
        }
    }


    private static void bookAndPrintTicketPrice(Scanner scanner, Seat[][] cinemaRoom) {

        while (true) {
            int price = 0;
            try {
                price = bookAndGetTicketPrice(scanner, cinemaRoom);
                System.out.println("\nTicket price: $" + price);
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }
    }


    private static int bookAndGetTicketPrice(Scanner scanner, Seat[][] cinemaRoom) throws Exception {
        int rowNumber = getRowNumberFromInput(scanner);
        int seatNumber = getSeatNumberFromInput(scanner);
        validateCorrectSeatInput(cinemaRoom, rowNumber, seatNumber);

        Seat seat = cinemaRoom[rowNumber][seatNumber];
        validateTicketIsAvailable(seat);

        seat.setBooked(true);
        seat.setAvailabilityChar(SEAT_BOOKED);

        return seat.getPrice();

    }



    private static int getRowNumberFromInput(Scanner scanner) {
        System.out.println("\nEnter a row number:");
        return Integer.parseInt(scanner.nextLine());
    }

    private static int getSeatNumberFromInput(Scanner scanner) {
        System.out.println("Enter a seat number in that row:");
        return Integer.parseInt(scanner.nextLine());
    }

    private static void validateTicketIsAvailable(Seat seat) throws Exception {
        if (seat.isBooked()) {
            throw new Exception("\nThat ticket has already been purchased!");
        }
    }

    private static void validateCorrectSeatInput(Seat[][] cinemaRoom, int rowNumber, int seatNumber) throws Exception {
        if (rowNumber >cinemaRoom.length - START_ROW || seatNumber > cinemaRoom[0].length - START_COL) {
            throw new Exception("Wrong input!");
        }
    }

    private static void printStatistics(Seat[][] cinemaRoom) {

        int purchasedTickets = getAndPrintNumberOfPurchasedTickets(cinemaRoom);
        int totalSeats = calculateTotalSeats(cinemaRoom);
        printPercentage(totalSeats, purchasedTickets);
        printCurrentIncome(cinemaRoom);
        printTotalIncome(cinemaRoom);
    }

    private static void printCurrentIncome(Seat[][] cinemaRoom) {
        int currentIncome = 0;
        for (int row = START_ROW; row < cinemaRoom.length; row++) {
            for (int col = START_COL; col < cinemaRoom[row].length; col++) {
                Seat seat = cinemaRoom[row][col];
                if (seat.isBooked()) {
                    currentIncome += seat.getPrice();
                }
            }
        }
        System.out.println("Current income: $" + currentIncome);

    }

    private static int calculateTotalSeats(Seat[][] cinemaRoom) {
        return (cinemaRoom.length - START_ROW) * (cinemaRoom[0].length - START_COL);
    }

    private static int getAndPrintNumberOfPurchasedTickets(Seat[][] cinemaRoom) {
        int purchasedTickets = 0;
        for (int row = START_ROW; row < cinemaRoom.length; row++) {
            for (int col = START_COL; col < cinemaRoom[row].length; col++) {
                Seat seat = cinemaRoom[row][col];
                if (seat.isBooked()) {
                    purchasedTickets++;
                }
            }
        }
        System.out.println("Number of purchased tickets: " + purchasedTickets);
        return purchasedTickets;
    }

    private static void printPercentage(int totalNumberOfSeats, int purchasedTickets) {
        float percentage = (float) purchasedTickets / totalNumberOfSeats * 100;
        System.out.printf(Locale.US, "Percentage: %.2f%%\n", percentage);
    }


    private static void printTotalIncome(Seat[][] cinemaRoom) {
        int totalIncome = 0;
        for (int row = START_ROW; row < cinemaRoom.length; row++) {
            for (int col = START_COL; col < cinemaRoom[row].length; col++) {
                Seat seat = cinemaRoom[row][col];
                int price = seat.getPrice();
                totalIncome += price;
            }
        }

        System.out.println("Total income: $" + totalIncome);
    }


    public static class Seat {
        private int price = FULL_PRICE;
        private boolean isBooked = false;
        private char availabilityChar = SEAT_AVAILABLE;

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public boolean isBooked() {
            return isBooked;
        }

        public void setBooked(boolean booked) {
            isBooked = booked;
        }

        public char getAvailabilityChar() {
            return availabilityChar;
        }

        public void setAvailabilityChar(char availabilityChar) {
            this.availabilityChar = availabilityChar;
        }
    }

    public enum cinemaMenu {
        SHOW_SEATS              ("1", "Show the seats"),
        BUY_TICKETS             ("2", "Buy a ticket"),
        STATISTICS              ("3", "Statistics"),
        EXIT                    ("0","Exit");

        private final String input;
        private final String text;

        cinemaMenu(String input, String text) {

            this.input = input;
            this.text = text;
        }

        public static void printMenu() {
            System.out.println();
            cinemaMenu[] menuItems = cinemaMenu.values();
            for (cinemaMenu menuItem : menuItems) {
                 System.out.println(menuItem.getInput() + ". " + menuItem.getText());
            }
        }

        public static cinemaMenu getMenuItemFromInput(String input) {
            cinemaMenu[] menuItems = cinemaMenu.values();
            for (cinemaMenu menuItem : menuItems) {
                if (menuItem.getInput().equals(input)) {
                    return menuItem;
                }
            }
            throw new RuntimeException("Wrong input!");
        }


        public String getInput() {
            return input;
        }

        public String getText() {
            return text;
        }
    }
}
