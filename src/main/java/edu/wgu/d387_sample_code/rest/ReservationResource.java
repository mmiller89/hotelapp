package edu.wgu.d387_sample_code.rest;


import edu.wgu.d387_sample_code.controller.LoginController;
import edu.wgu.d387_sample_code.convertor.*;
import edu.wgu.d387_sample_code.entity.ReservationEntity;
import edu.wgu.d387_sample_code.entity.RoomEntity;
import edu.wgu.d387_sample_code.entity.UsersEntity;
import edu.wgu.d387_sample_code.model.request.ReservationRequest;
import edu.wgu.d387_sample_code.model.response.ReservableRoomResponse;
import edu.wgu.d387_sample_code.model.response.ReservationResponse;
//import edu.wgu.d387_sample_code.repository.PageableRoomRepository;
import edu.wgu.d387_sample_code.repository.ReservationRepository;
import edu.wgu.d387_sample_code.repository.RoomRepository;
import edu.wgu.d387_sample_code.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping(ResourceConstants.ROOM_RESERVATION_V1)
@CrossOrigin
public class ReservationResource {

    @Autowired
    ApplicationContext context;

       // @Autowired
       // PageableRoomRepository pageableRoomRepository;

        @Autowired
        RoomRepository roomRepository;

        @Autowired
        ReservationRepository reservationRepository;

        @Autowired
        ConversionService conversionService;

        @Autowired
        UsersRepository usersRepository;

        @Autowired
        private RoomEntityToReservableRoomResponseConverter converter;

    @RequestMapping(path ="", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ReservableRoomResponse> getAvailableRooms (
            @RequestParam(value = "checkin")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                    LocalDate checkin,
            @RequestParam(value = "checkout")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                    LocalDate checkout, Pageable pageable) {


        RoomService roomService=context.getBean(RoomServiceImpl.class);

        ReservationService reservationService=context.getBean(ReservationServiceImpl.class);

        List<RoomEntity> allRooms=roomService.findAll();

        List<ReservationEntity> allReservations=reservationService.findAll();

        for(ReservationEntity reservationEntity: allReservations){
            LocalDate rcheckin=reservationEntity.getCheckin();
            LocalDate rcheckout=reservationEntity.getCheckout();
            if(rcheckin.isBefore(checkin)&&rcheckout.isAfter(checkin))allRooms.remove(reservationEntity.getRoomEntity());
            else if(rcheckin.isAfter(checkin)&&rcheckin.isBefore(checkout)) allRooms.remove(reservationEntity.getRoomEntity());
            else if(rcheckin.isEqual(checkin)) allRooms.remove(reservationEntity.getRoomEntity());
        }

        Page<RoomEntity> page=new PageImpl<>(allRooms);

        return page.map(converter::convert);
    }

    @RequestMapping(path = "/{roomId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoomEntity> getRoomById(
            @PathVariable
                    Long roomId) {

        Optional<RoomEntity> result  = roomRepository.findById(roomId);
        RoomEntity roomEntity = null;

        if (result.isPresent()) {
            roomEntity= result.get();
        }
        else {
            // we didn't find the employee
            //throw new RuntimeException("Did not find part id - " + theId);
            return null;
        }

        return new ResponseEntity<>(roomEntity, HttpStatus.OK);
    }

    @RequestMapping(path = "/reservationlist/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<ReservationEntity>> displayUserReservations(
            @PathVariable
                Long userId) {

        Optional<UsersEntity> result  = usersRepository.findById(userId);
        UsersEntity usersEntity = null;

        if (result.isPresent()) {
            usersEntity= result.get();
        }
        else {
            // we didn't find the employee
            //throw new RuntimeException("Did not find part id - " + theId);
            return null;
        }

        return new ResponseEntity<>(usersEntity.getReservationEntities(), HttpStatus.OK);
    }

    @RequestMapping(path = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public  ResponseEntity<ReservationEntity> createReservation(
            @RequestBody
            ReservationRequest reservationRequest) {

        ReservationEntity reservationEntity = conversionService.convert(reservationRequest, ReservationEntity.class);

        reservationRepository.save(reservationEntity);

        Optional<UsersEntity> repo  = usersRepository.findById(reservationRequest.getUserId());
        UsersEntity usr = null;

        if (repo.isPresent()) {
            usr = repo.get();
        }
        else {
            return null;
        }

        usr.addReservationEntities(reservationEntity);
        usersRepository.save(usr);

        reservationEntity.setUsersEntity(usr);
        reservationRepository.save(reservationEntity);

        ReservationService repository=context.getBean(ReservationServiceImpl.class);
        reservationEntity=repository.findLast();
        Optional<RoomEntity> result  = roomRepository.findById(reservationRequest.getRoomId());
        RoomEntity roomEntity= null;

        if (result.isPresent()) {
            roomEntity= result.get();
        }
        else {
            // we didn't find the employee
            //throw new RuntimeException("Did not find part id - " + theId);
            return null;
        }

        roomRepository.save(roomEntity);
        reservationEntity.setRoomEntity(roomEntity);




        return new ResponseEntity<>(reservationEntity, HttpStatus.OK);

    }

    @RequestMapping(path = "", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReservableRoomResponse> updateReservation(
            @RequestBody
            ReservationRequest reservationRequest) {

        return new ResponseEntity<>(new ReservableRoomResponse(), HttpStatus.OK);
    }

    @RequestMapping(path = "/{reservationId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteReservation(
            @PathVariable
            long reservationId) {

        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }



}
