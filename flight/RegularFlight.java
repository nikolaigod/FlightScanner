package flightscanner.flight;
import flightscanner.airport.Airport;
import flightscanner.exception.FlightCapacityExceededException;
import flightscanner.exception.InvalidFlightException;
import flightscanner.passenger.Passenger;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class RegularFlight implements Flight{
    private final Airport from;
    private final Airport to;
    private final String id;
    private final int totalCapacity;

    private final Set<Passenger> passengers;
    private RegularFlight(String flightId, Airport from, Airport to, int totalCapacity){
        this.from = from;
        this.to = to;
        this.id = flightId;
        this.totalCapacity = totalCapacity;
        this.passengers = HashSet.newHashSet(totalCapacity);
    }
    RegularFlight of(String flightId, Airport from, Airport to, int totalCapacity){
        if(flightId == null || flightId.isBlank() || from == null || to == null || totalCapacity < 0){
            throw new IllegalArgumentException("Wrong flight arguments");
        }
        if(from.equals(to)){
            throw new InvalidFlightException("Same airports");
        }
        return new RegularFlight(flightId, from, to, totalCapacity);
    }

    @Override
    public Airport getFrom() {
        return this.from;
    }

    @Override
    public Airport getTo() {
        return this.to;
    }

    @Override
    public void addPassenger(Passenger passenger) throws FlightCapacityExceededException {
        if(passenger == null){
            throw new IllegalArgumentException("Passenger is null");
        }
        if(this.totalCapacity == this.passengers.size()){
            throw new FlightCapacityExceededException("Capacity exceeded");
        }
        this.passengers.add(passenger);
    }

    @Override
    public void addPassengers(Collection<Passenger> passengers) throws FlightCapacityExceededException {
        if(passengers.size() + this.passengers.size() > this.totalCapacity){
            throw new FlightCapacityExceededException("Capacity exceeded");
        }
        this.passengers.addAll(passengers);
    }

    @Override
    public Collection<Passenger> getAllPassengers() {
        return Set.copyOf(this.passengers);
    }

    @Override
    public int getFreeSeatsCount() {
        return this.totalCapacity - this.passengers.size();
    }

    @Override
    public String toString() {
        return this.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }
}
