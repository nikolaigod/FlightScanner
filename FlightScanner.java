package flightscanner;

import flightscanner.airport.Airport;
import flightscanner.flight.Flight;
import flightscanner.flight.FlightsSortedByDestinationComparator;
import flightscanner.flight.FlightsSortedByFreeSeatsComparator;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class FlightScanner implements FlightScannerAPI{

    private final Map<Airport, Set<Flight>> flights;
    public FlightScanner(){
        this.flights = new HashMap<>();
    }
    @Override
    public void add(Flight flight) {
        if (flight == null) {
            throw new IllegalArgumentException("flight cannot be null");
        }
        Airport from = flight.getFrom();
        this.flights.putIfAbsent(from, new HashSet<>());
        this.flights.get(from).add(flight);
    }

    @Override
    public void addAll(Collection<Flight> flights) {
        if(flights == null){
            throw new IllegalArgumentException("Can't be null");
        }
        for(Flight f: flights){
            this.add(f);
        }
    }

    @Override
    public List<Flight> searchFlights(Airport a, Airport b) {
        if (a == null || b == null || a.equals(b)) {
            throw new IllegalArgumentException("Arguments not OK");
        }
        return bfs(a, b);
    }

    private List<Flight> bfs(Airport current, Airport target) {
        Set<Airport> visited = new HashSet<>();
        Queue<Airport> queue = new LinkedList<>();
        Map<Airport, Flight> parentOf = new HashMap<>();

        visited.add(current);
        queue.add(current);
        parentOf.put(current, null);

        while (!queue.isEmpty()) {
            Airport airport = queue.poll();

            if (airport.equals(target)) {
                List<Flight> flightPlan = new LinkedList<>();

                while (parentOf.get(target) != null) {
                    flightPlan.add(parentOf.get(target));
                    target = parentOf.get(target).getFrom();
                }

                Collections.reverse(flightPlan);

                return flightPlan;
            }

            Set<Flight> outgoingFlights = flights.get(airport);

            if (outgoingFlights == null) {
                continue;
            }

            for (Flight outgoingFlight : outgoingFlights) {
                if (!visited.contains(outgoingFlight.getTo())) {
                    visited.add(outgoingFlight.getTo());
                    queue.add(outgoingFlight.getTo());
                    parentOf.put(outgoingFlight.getTo(), outgoingFlight);
                }
            }
        }

        return Collections.emptyList();
    }

    @Override
    public List<Flight> getFlightsSortedByFreeSeats(Airport from) {
        if (from == null) {
            throw new IllegalArgumentException("From airport cannot be null");
        }
        Set<Flight> res = flights.get(from);
        if(res == null){
            return List.of();
        }
        List<Flight> list = new ArrayList<>(res);
        Collections.sort(list, new FlightsSortedByFreeSeatsComparator());
        return List.copyOf(list);
    }

    @Override
    public List<Flight> getFlightsSortedByDestination(Airport from) {
        if (from == null) {
            throw new IllegalArgumentException("From airport cannot be null");
        }

        Set<Flight> res = flights.get(from);

        if (res == null) {
            return List.of();
        }

        List<Flight> sorted = new ArrayList<>(res);
        Collections.sort(sorted, new FlightsSortedByDestinationComparator());
        return List.copyOf(sorted);
    }
}
