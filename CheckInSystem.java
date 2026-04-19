import java.util.List;
import java.util.ArrayList;

class CheckInSystem {
    public static void main(String[] args) {
        List<Flight> flights = new ArrayList<>();
        flights.add(new Flight("D7 221", "Sydney", "Kuala Lumpur", 550));
        flights.add(new Flight("JQ 501", "Sydney", "Melbourne", 100));
        flights.add(new Flight("QF 500", "Sydney", "Brisbane", 95));
        System.out.println(flights);

    }
}

enum ContrabandFlag {
    CLEAR,
    FLAGGED
}

enum SeatSelectionStatus {
    NOT_SELECTED,
    SELECTED
}

enum PaymentStatus {
    PAID,
    PENDING,
    UNPAID
}

class Flight {
    String flightNumber;
    String departureLocation;
    String arrivalLocation;
    float flightDuration;
    List<Seat> seats = new ArrayList<>();

    Flight(String flightNumber, String depatureLocation, String arrivalLocation, float flightDuration) {
        this.flightNumber = flightNumber;
        this.departureLocation = depatureLocation;
        this.arrivalLocation = arrivalLocation;
        this.flightDuration = flightDuration;

        String[] cols = { "A", "B", "C", "D", "E", "F" };
        for (int row = 1; row <= 30; row++) {
            for (String col : cols) {
                seats.add(new Seat(String.valueOf(row) + col, "Economy"));
            }
        }
    }

    public void addSeat(Seat seat) {
        if (seat != null) {
            seats.add(seat);
        }
    }

    public Seat findSeatByNumber(String seatNumber) {
        if (seatNumber == null) {
            return null;
        }
        for (Seat s : seats) {
            if (s.seatNumber.equals(seatNumber)) {
                return s;
            }
        }
        return null;
    }

    public String toString() {
        return "Flight{" +
                "flightNumber=" + flightNumber +
                ", departureLocation=" + departureLocation +
                ", arrivalLocation=" + arrivalLocation +
                ", flightDuration=" + flightDuration +
                ", seats=" + seats +
                '}';
    }

}

class Booking {
    String bookingNum;
    boolean baggageChecked;
    String bookingDate;

    private Seat assignedSeat;
    private boolean isConfirmed;
    private boolean isCancelled;

    Booking(String bookingNum, String bookingDate) {
        this.bookingNum = bookingNum;
        this.bookingDate = bookingDate;
        this.baggageChecked = false;
        this.isConfirmed = false;
        this.isCancelled = false;
    }

    void confirmBooking() {
        if (isCancelled) {
            return;
        }
        isConfirmed = true;
    }

    void cancelBooking() {
        isCancelled = true;
        isConfirmed = false;
        if (assignedSeat != null) {
            assignedSeat.releaseSeat();
            assignedSeat = null;
        }
    }

    void assignSeat(Seat seat) {
        if (seat == null || isCancelled) {
            return;
        }
        if (!seat.checkAvailability()) {
            return;
        }

        if (assignedSeat != null) {
            assignedSeat.releaseSeat();
        }

        seat.assignSeat();
        assignedSeat = seat;
    }

    @Override
    public String toString() {
        String seatNum = (assignedSeat == null) ? "None" : assignedSeat.seatNumber;
        return "Booking{bookingNum='" + bookingNum + "', baggageChecked=" + baggageChecked
                + ", bookingDate='" + bookingDate + "', confirmed=" + isConfirmed
                + ", cancelled=" + isCancelled + ", seat=" + seatNum + "}";
    }
}

class Passenger {
    String name;
    int age;

    Passenger(String name, int age) {
        this.name = name;
        this.age = age;
    }
}

class CheckedInBooking extends Booking {
    String checkInTime;

    CheckedInBooking(String bookingNum, String bookingDate, String checkInTime) {
        super(bookingNum, bookingDate);
        this.checkInTime = checkInTime;
    }
}

class ConfirmedBooking extends Booking {

    SeatSelectionStatus seatSelectionStatus = SeatSelectionStatus.NOT_SELECTED;
    String bookingDate;

    ConfirmedBooking(String bookingNum, String bookingDate) {
        super(bookingNum, bookingDate);
        this.bookingDate = bookingDate;
    }

    void confirmSeatSelection() {
        seatSelectionStatus = SeatSelectionStatus.SELECTED;
    }
}

class Baggage {
    String baggageID;
    double weight;
    boolean checkedIn;
    ContrabandFlag contrabandFlag;

    public Baggage(String baggageID, double weight) {
        this.baggageID = baggageID;
        this.weight = weight;
        checkedIn = false;
        contrabandFlag = ContrabandFlag.CLEAR;
    }

    void updateWeight(double weight) {

    }

    void markedCheckIn() {

    }

    void flagContraband() {
        contrabandFlag = contrabandFlag.FLAGGED;
    }

}

class BoardingPass {
    int boardingPassID;
    Seat seat;
    Flight flight;

    public BoardingPass(int boardingPassID, Seat seat, Flight flight) {
        this.boardingPassID = boardingPassID;
        this.seat = seat;
        this.flight = flight;
    }

    void generatePass() {

    }
    // public String toString(){

    // }

}

interface CheckInService {
    void selectSeat();

    void checkInBaggage();

    void createBoardingPass();

    void verifyIdentity();

    void handlePayment();
}

class CheckIn implements CheckInService {
    int checkInID;
    Flight flight;

    public CheckIn(int checkInID) {
        this.checkInID = checkInID;
    }

    public CheckIn(int checkInID, Flight flight) {
        this.checkInID = checkInID;
        this.flight = flight;
    }

    @Override
    public void selectSeat() {
        if (flight == null) {
            System.out.println("No flight assigned; cannot select seat.");
            return;
        }
        System.out.print("Enter seat row (1-30): ");
        int row = In.nextInt();
        System.out.print("Enter seat column (A-F): ");
        char col = In.nextUpperChar();
        String seatNumber = String.valueOf(row) + col;
        Seat seat = flight.findSeatByNumber(seatNumber);
        if (seat == null) {
            System.out.println("No such seat on this flight: " + seatNumber);
            return;
        }
        selectSeat(seat);
    }

    public void selectSeat(Seat seat) {
        if ((seat != null) && (seat.checkAvailability())) {
            seat.assignSeat();
            System.out.println("Seat selected successfully");
        } else {
            System.out.println("Seat is not available");
        }
    }

    @Override
    public void checkInBaggage() {
        System.out.print("Enter baggage ID: ");
        String baggageID = In.nextLine();
        System.out.print("Enter baggage weight (kg): ");
        double weight = In.nextDouble();
        checkInBaggage(new Baggage(baggageID, weight));
    }

    public void checkInBaggage(Baggage baggage) {
        if (baggage != null) {
            baggage.markedCheckIn();
            System.out.println("Baggage checked in");
        } else {
            System.out.println("No baggage");
        }
    }

    @Override
    public void createBoardingPass() {
        if (flight == null) {
            System.out.println("No flight assigned; cannot create boarding pass.");
            return;
        }
        System.out.print("Enter boarding pass ID: ");
        int boardingPassID = In.nextInt();
        System.out.print("Enter seat row (1-30) for boarding pass: ");
        int row = In.nextInt();
        System.out.print("Enter seat column (A-F): ");
        char col = In.nextUpperChar();
        String seatNumber = String.valueOf(row) + col;
        Seat seat = flight.findSeatByNumber(seatNumber);
        if (seat == null) {
            System.out.println("No such seat on this flight: " + seatNumber);
            return;
        }
        createBoardingPass(boardingPassID, seat, flight);
    }

    public void createBoardingPass(int boardingPassID, Seat seat, Flight flight) {
        BoardingPass boardingPass = new BoardingPass(boardingPassID, seat, flight);
    }

    @Override
    public void verifyIdentity() {
        System.out.println("Identity verified");
    }

    @Override
    public void handlePayment() {
        System.out.print("Enter payment ID: ");
        String paymentID = In.nextLine();
        System.out.print("Enter amount: ");
        double amount = In.nextDouble();
        System.out.print("Enter payment type: ");
        String paymentType = In.nextLine();
        handlePayment(new Payment(paymentID, amount, paymentType));
    }

    public void handlePayment(Payment payment) {
        if (payment != null) {
            boolean success = payment.processPayment();
            if (success) {
                System.out.println("Payment completed successfully");
            } else {
                System.out.println("Payment failed. Please try again");
            }
        } else {
            System.out.println("No payment required");
        }
    }
}

class SelfCheckIn extends CheckIn {
    String machineID;

    public SelfCheckIn(int checkInID, String machineID, Flight flight) {
        super(checkInID, flight);
        this.machineID = machineID;
    }

    void startSelfCheckIn() {
        System.out.println("Starting self cehck-in at machine: " + machineID);

        verifyIdentity();
        selectSeat();
        checkInBaggage();
        handlePayment();
        createBoardingPass();

        System.out.println("Self check-in completed.");
    }
}

class CounterCheckIn extends CheckIn {
    CheckInAgent agent;
    String counterID;

    public CounterCheckIn(CheckInAgent agent, String counterID) {
        super(0, null);
        this.agent = agent;
        this.counterID = counterID;
    }

    boolean verifyDocuments(Passenger passenger) {
        return agent != null && agent.verifyDocuments(passenger);
    }

    void assistCheckIn(Booking booking, Passenger passenger) {
        System.out.println("Check-in at counter: " + counterID);

        if (verifyDocuments(passenger)) {
            System.out.println("Documents verified");
            System.out.println("Processing booking...");
            System.out.println("Check-in successfull");
        } else {
            System.out.println("Verification failed");
        }
    }

    void assistPassenger(Passenger passenger) {
        if (agent != null) {
            agent.assistCheckIn(passenger);
        }
    }
}

class CheckInAgent {
    String agentID;
    String name;

    boolean verifyDocuments(Passenger passenger) {
        System.out.println("Verifying documents for " + passenger);
        return passenger != null;
    }

    void assistCheckIn(Passenger passenger) {
        System.out.println("Assisting passenger : " + passenger);
    }
}

class Payment {
    String paymentID;
    double amount;
    String paymentType;
    PaymentStatus status;

    public Payment(String paymentID, double amount, String paymentType) {
        this.paymentID = paymentID;
        this.amount = amount;
        this.paymentType = paymentType;
        this.status = PaymentStatus.UNPAID;
    }

    public boolean processPayment() {
        if (amount <= 0) {
            System.out.println("Payment " + paymentID + " failed: invalid amount.");
            status = PaymentStatus.PENDING;
            return false;
        }
        status = PaymentStatus.PAID;
        System.out.println("Payment " + paymentID + " processed successfully. Amount: $" + amount);
        return true;
    }

    void refundPayment() {
        if (status == PaymentStatus.PAID) {
            status = PaymentStatus.UNPAID;
            System.out.println("Payment " + paymentID + " has been refunded.");
        } else {
            System.out.println("Payment " + paymentID + " cannot be refunded (current status: " + status + ").");
        }
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentID='" + paymentID + '\'' +
                ", amount=" + amount +
                ", paymentType='" + paymentType + '\'' +
                ", status=" + status +
                '}';
    }
}

class Seat {
    String seatNumber;
    String seatClass;
    boolean isAvailable;

    Seat(String seatNumber, String seatClass) {
        this.seatNumber = seatNumber;
        this.seatClass = seatClass;
        this.isAvailable = true;
    }

    void releaseSeat() {
        this.isAvailable = true;
    }

    void assignSeat() {
        this.isAvailable = false;
    }

    boolean checkAvailability() {
        return this.isAvailable;
    }

    public String toString() {
        return "Seat{" +
                "seatNumber=" + seatNumber +
                ", seatClass=" + seatClass +
                ", isAvailable=" + isAvailable +
                '}';
    }

}
