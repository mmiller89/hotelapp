package edu.wgu.d387_sample_code.entity;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


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

	@OneToMany(mappedBy = "usersEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ReservationEntity> reservationEntities = new ArrayList<>();



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

	public List<ReservationEntity> getReservationEntities() {
		return reservationEntities;
	}

	public void setReservationEntities(List<ReservationEntity> reservation){
		this.reservationEntities = reservation;
	}

	public void addReservationEntities(ReservationEntity reservationEntity) {
		if (reservationEntities == null){
			reservationEntities = new ArrayList<>();
		}
		reservationEntities.add(reservationEntity);
	}
}