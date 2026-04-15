import java.util.List;
import java.util.ArrayList;

interface CheckInService {
    void selectSeat();

    void checkInBaggage();

    void createBoardingPass();

    void verifyIdentity();

    void handlePayment();
}

class Seat {
    String seatNumber;
    String seatClass;
    boolean isAvailable;

    Seat(String seatNumber, String seatClass);
}

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
    String bookingDate;
    String bookingNumber;
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

class CheckedInBooking {

}

class ConfirmedBooking {

}

class Baggage {

}

class CheckIn {

}

class SelfCheckIn {

}

class CounterCheckIn {

}

class CheckInAgent {

}

class BoardingPass {

}

class Payment {

}

class Seat {

}

interface CheckInService {

}

class ContrabandChecker {

}

class CheckedInBooking {

}

class ConfirmedBooking {

}

class Baggage {

}

class CheckIn {

}

class SelfCheckIn {

}

class CounterCheckIn {

}

class CheckInAgent {

}

class BoardingPass {

}

class Payment {

}

class Seat {

}

interface CheckInService {

}

class ContrabandChecker {

}