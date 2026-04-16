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
    enum seatSelectionStatus {
        NOT_SELECTED,
        SELECTED,
        CONFIRMED
    }

    seatSelectionStatus seatSelectionStatus = seatSelectionStatus.NOT_SELECTED;
    String bookingDate;

    ConfirmedBooking(String bookingNum, String bookingDate) {
        super(bookingNum, bookingDate);
        this.bookingDate = bookingDate;
    }

    void confirmSeatSelection() {
        seatSelectionStatus = seatSelectionStatus.CONFIRMED;
    }
}

class Baggage {
    int baggageID;
    float weight;
    boolean checkedIn;

    enum contrabandFlag {
        UNKNOWN,
        CLEAR,
        FLAGGED
    }

    contrabandFlag contrabandFlag = contrabandFlag.UNKNOWN;
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

class CheckIn {

}

class SelfCheckIn {

}

class CounterCheckIn {

}

class CheckInAgent {

}

// Javier:

class Payment {

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
