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

	@OneToMany(mappedBy = "usersEntity", cascade = CascadeType.ALL, orphanRemoval=true, fetch = FetchType.LAZY)
	private Set<ReservationEntity> reservationEntities = new HashSet<>();



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

	public Set<ReservationEntity> getReservationEntities() {
		return reservationEntities;
	}

	public void setReservationEntities(Set<ReservationEntity> reservation){
		this.reservationEntities = reservation;
	}

	public void addReservationEntities(ReservationEntity reservationEntity) {
		if (reservationEntities == null){
			reservationEntities = new HashSet<>();
		}
		reservationEntities.add(reservationEntity);
	}
}