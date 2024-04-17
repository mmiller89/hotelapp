package edu.wgu.d387_sample_code;

import edu.wgu.d387_sample_code.entity.AdditionEntity;
import edu.wgu.d387_sample_code.entity.RoomEntity;
import edu.wgu.d387_sample_code.entity.UsersEntity;
import edu.wgu.d387_sample_code.repository.AdditionRepository;
import edu.wgu.d387_sample_code.repository.RoomRepository;
import edu.wgu.d387_sample_code.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class H2Bootstrap implements CommandLineRunner {

	@Autowired
	RoomRepository roomRepository;

	@Autowired
	UsersRepository usersRepository;

	@Autowired
	AdditionRepository additionRepository;
	
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub



		System.out.println("Bootstrapping data: ");
		RoomEntity room1= new RoomEntity(405, "200");
		room1.setId(1L);

		roomRepository.save(room1);

		RoomEntity room2= new RoomEntity(406, "220");
		room2.setId(2L);

		roomRepository.save(room2);

		RoomEntity room3= new RoomEntity(407, "260");
		room3.setId(3L);

		roomRepository.save(room3);

		RoomEntity room4= new RoomEntity(408, "280");
		room4.setId(4L);

		roomRepository.save(room4);


		Iterable<RoomEntity> itr = roomRepository.findAll();

		System.out.println("Printing out data: ");
		for(RoomEntity room : itr) {
			System.out.println(room.getRoomNumber());
		}

		//User Creation

		System.out.println("Creation of user...");

		UsersEntity user1 = new UsersEntity("Guest", "123", 1500);
		user1.setId(1L);
		usersRepository.save(user1);
		UsersEntity user2 = new UsersEntity("Executive", "777", 15000);
		user2.setId(2L);
		usersRepository.save(user2);




		AdditionEntity add1 = new AdditionEntity("Breakfast in Bed", "Cozy", 50);
		add1.setId(1L);
		additionRepository.save(add1);

		AdditionEntity add2 = new AdditionEntity("Premium Bed Sheets", "Luxurious", 30);
		add1.setId(2L);
		additionRepository.save(add2);

		AdditionEntity add3 = new AdditionEntity("VIP Pool Pass", "Cool", 150);
		add1.setId(3L);
		additionRepository.save(add3);

		System.out.println("Addition repository has..." + additionRepository.count() + " entities");



	}



}
