package edu.wgu.d387_sample_code.entity;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "Users")
public class UsersEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	private String userName;

	@NotNull
	private String password;

	@NotNull
	private Integer rewardsPoints;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	private List<ReservationEntity> reservationList; //rename this to reservationList for clarity



	public UsersEntity() {
	}

	public UsersEntity(String userName, String password, Integer rewardsPoints) {
		this.userName = userName;
		this.password = password;
		this.rewardsPoints = rewardsPoints;

	}

	public void printInfo(){
		System.out.println("Username: " + this.userName + " Password: " + this.password + " Rewards Points: " + this.rewardsPoints);
	}


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getRewardsPoints() {
		return rewardsPoints;
	}

	public void setRewardsPoints(Integer rewardsPoints) {
		this.rewardsPoints = rewardsPoints;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<ReservationEntity> getReservedRooms() {
		return reservationList;
	}

	public void addReservedRooms(ReservationEntity reservationEntity) {
		if (null == reservationList)
			reservationList = new ArrayList<>();

		reservationList.add(reservationEntity);
	}
}