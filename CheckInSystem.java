import java.util.List;
import java.util.ArrayList;

class Flight {
    String flightNumber;
    String departureLocation;
    String arrivalLocation;
    String flightDuration;
    List<Seat> seats = new ArrayList<>();

    Flight(String flightNumber, String depatureLocation, String arrivalLocation, String flightDuration) {
        this.flightNumber = flightNumber;
        this.departureLocation = depatureLocation;
        this.arrivalLocation = arrivalLocation;
        this.flightDuration = flightDuration;
    }

    public void addSeat(Seat seat) {

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

    Booking() {
        this("", "");
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

class CheckInSystem {
    String seat;
}

// Fhilip:

class CheckedInBooking extends Booking {
    String checkInTime;

    CheckedInBooking(String bookingNum, String bookingDate, String checkInTime) {
        super(bookingNum, bookingDate);
        this.checkInTime = checkInTime;
    }
}

class ConfirmedBooking extends Booking {
    enum SeatSelectionStatus {
        NOT_SELECTED,
        SELECTED
    }

    SeatSelectionStatus seatSelectionStatus = SeatSelectionStatus.NOT_SELECTED;
    String bookingDate;

    ConfirmedBooking(String bookingNum, String bookingDate) {
        super(bookingNum, bookingDate);
        this.bookingDate = bookingDate;
    }

    void confirmSeatSelection() {
        seatSelectionStatus = SeatSelectionStatus.NOT_SELECTED;
    }
}

class Baggage {
    int baggageID;
    float weight;
    boolean checkedIn;

    enum ContrabandFlag {
        UNKNOWN,
        CLEAR,
        FLAGGED
    }

    ContrabandFlag contrabandFlag = ContrabandFlag.UNKNOWN;
}

class BoardingPass {
    int boardingPassID;
}

// Jeson:

interface CheckInService {
    void selectSeat();

    void checkInBaggage();

    void createBoardingPass();

    void verifyIdentity();

    void handlePayment();
}

class CheckIn implements CheckInService {
    int checkInID;

    @Override
    public void selectSeat() {
    }

    @Override
    public void checkInBaggage() {
    }

    @Override
    public void createBoardingPass() {
    }

    @Override
    public void verifyIdentity() {
    }

    @Override
    public void handlePayment() {
    }
}

class SelfCheckIn extends CheckIn {
    String machineID;

    void startSelfCheckIn() {
    }
}

class CounterCheckIn extends CheckIn {
    CheckInAgent agent;
    String counterID;

    boolean verifyDocuments(Passenger passenger) {
        return false;
    }

    void assistCheckIn(Passenger passenger) {
    }

    void assistPassenger(Passenger passenger) {
    }
}

class CheckInAgent {
    String agentID;
    String name;

    boolean verifyDocuments(Passenger passenger) {
        return false;
    }

    void assistCheckIn(Passenger passenger) {
    }
}

// Javier:

enum PaymentStatus {
    PAID,
    PENDING,
    UNPAID
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

class ContrabandChecker {

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

}
