package flightscanner.airport;
import java.lang.IllegalArgumentException;
public record Airport(String id){
    public Airport {
        if(id == null || id.isEmpty() || id.isBlank()){
         throw new IllegalArgumentException("Id is null");
        }
    }

    @Override
    public String toString() {
        return "Airport{" +
                "id='" + id + '\'' +
                '}';
    }
}
