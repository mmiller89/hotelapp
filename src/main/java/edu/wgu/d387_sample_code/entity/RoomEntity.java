package edu.wgu.d387_sample_code.entity;

import com.sun.istack.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;


@Entity
@Table(name = "Room")
public class RoomEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	private Integer roomNumber;

	@NotNull
	private String price;

//	@OneToMany(cascade = CascadeType.ALL, orphanRemoval=true, fetch = FetchType.LAZY)
//	private Set<AdditionEntity> additionEntities;

	@ManyToMany
	private Set<AdditionEntity> additionEntities;

	public RoomEntity() {
	}

	public RoomEntity(Integer roomNumber, String price) {
		this.roomNumber = roomNumber;
		this.price = price;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(Integer roomNumber) {
		this.roomNumber = roomNumber;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public Set<AdditionEntity> getAdditionEntities() {
		return additionEntities;
	}

	public void setAdditionEntities(Set<AdditionEntity> additionEntities) {
		this.additionEntities = additionEntities;
	}

	public void addAdditionEntries(AdditionEntity additionEntity) {
		if (additionEntities == null){
			additionEntities = new HashSet<>();
		}
		additionEntities.add(additionEntity);
	}
}
