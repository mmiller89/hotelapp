package edu.wgu.d387_sample_code.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


@Entity
@Table(name = "Addition")
public class AdditionEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	private String name;

	@NotNull
	private String description;

	@NotNull
	private Integer price;

//	@ManyToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name = "room_id")
//	private RoomEntity roomEntity;

	@ManyToMany(cascade = CascadeType.ALL)
	private List<RoomEntity> roomEntities;

	public AdditionEntity() {
	}

	public AdditionEntity(String name, String description, Integer price) {
		this.name = name;
		this.description = description;
		this.price = price;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

//	public RoomEntity getRoomEntity() {
//		return roomEntity;
//	}
//
//	public void setRoomEntity(RoomEntity roomEntity) {
//		this.roomEntity = roomEntity;
//	}
	public void addRoomEntities(RoomEntity roomEntity) {
		if (roomEntities == null){
			roomEntities = new ArrayList<>();
		}
		roomEntities.add(roomEntity);
	}
}