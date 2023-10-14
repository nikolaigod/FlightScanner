package flightscanner.flight;

import flightscanner.airport.Airport;
import flightscanner.exception.FlightCapacityExceededException;
import flightscanner.passenger.Passenger;

import java.util.Collection;

public interface Flight {
    Airport getFrom();
    Airport getTo();
    void addPassenger(Passenger passenger) throws FlightCapacityExceededException;
    void addPassengers(Collection<Passenger> passengers) throws FlightCapacityExceededException;
    Collection<Passenger> getAllPassengers();
    int getFreeSeatsCount();

}