package edu.wgu.d387_sample_code;

import edu.wgu.d387_sample_code.entity.ReservationEntity;
import edu.wgu.d387_sample_code.entity.RoomEntity;
import edu.wgu.d387_sample_code.entity.UsersEntity;
import edu.wgu.d387_sample_code.repository.ReservationRepository;
import edu.wgu.d387_sample_code.repository.RoomRepository;
import edu.wgu.d387_sample_code.repository.UsersRepository;

import static java.util.Objects.isNull;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

@SpringBootTest
class D387SampleCodeApplicationTests {
	
	
	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private ReservationRepository reservationRepository;

	@Autowired
	private RoomRepository roomRepository;


	@Test
    public void userAddedCorrectly(){
		UsersEntity usr = new UsersEntity("Michael", "456", 750);
		usersRepository.save(usr);

		Assert.isTrue(usersRepository.count() > 0, "User not added correctly!");
	}

	@Test
	public void reservationsAssignedCorrectly(){
		UsersEntity usr = new UsersEntity("Michael", "456", 750);
		usersRepository.save(usr);
		UsersEntity usr2 = new UsersEntity("John", "999", 7500);
		usersRepository.save(usr2);

		LocalDate checkin = LocalDate.of( 2024, 4, 16 );
		LocalDate checkout = LocalDate.of( 2024, 4, 22 );

		ReservationEntity rsv1 = new ReservationEntity(checkin, checkout);

		usr.addReservationEntities(rsv1);
		rsv1.setUsersEntity(usr);

		usersRepository.save(usr);
		reservationRepository.save(rsv1);

		Assert.isTrue(rsv1.getCheckin().equals(checkin) && rsv1.getCheckout().equals(checkout), "Dates not added correctly");
		Assert.isTrue(usr.getReservationEntities().contains(rsv1), "Reservation not added to Michael.");
	}

	@Test
	public void correctDeletionOfEntities(){

		LocalDate checkin = LocalDate.of( 2024, 4, 16 );
		LocalDate checkout = LocalDate.of( 2024, 4, 22 );

		ReservationEntity rsv1 = new ReservationEntity(checkin, checkout);
		reservationRepository.save(rsv1);

		reservationRepository.deleteById(rsv1.getId());
		Optional<ReservationEntity> rsrFind = reservationRepository.findById(rsv1.getId());
		Assert.isTrue(rsrFind.isEmpty(), "Reservation persists!");
	}

	@Test

	public void correctUpdate(){
		RoomEntity rm1 = new RoomEntity(444, "125", false);

		roomRepository.save(rm1);

		rm1.setReserved(true);
		rm1.setPrice("500");
		rm1.setRoomNumber(325);

		roomRepository.save(rm1);

		Assert.isTrue(Objects.equals(rm1.getPrice(), "500"), "Price is not updated");
		Assert.isTrue(Objects.equals(rm1.isReserved(), true), "Reserved is not updated");
		Assert.isTrue(Objects.equals(rm1.getRoomNumber(), 325), "Price is not updated");

	}

	@Test
	void contextLoads() {

	}

}
