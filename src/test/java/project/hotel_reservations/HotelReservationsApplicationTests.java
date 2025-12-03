package project.hotel_reservations;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Disabled("Disabled in CI because full context loading requires environment vars")
class HotelReservationsApplicationTests {

	@Test
	void contextLoads() {
	}

}
