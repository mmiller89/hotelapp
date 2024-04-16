package edu.wgu.d387_sample_code.convertor;




import edu.wgu.d387_sample_code.entity.ReservationEntity;
import edu.wgu.d387_sample_code.entity.RoomEntity;

import edu.wgu.d387_sample_code.entity.UsersEntity;
import edu.wgu.d387_sample_code.model.request.ReservationRequest;
import edu.wgu.d387_sample_code.repository.UsersRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

@Component
public class ReservationRequestToReservationEntityConverter implements Converter<ReservationRequest, ReservationEntity> {
private final ApplicationContext context;

    public ReservationRequestToReservationEntityConverter(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public ReservationEntity convert(ReservationRequest source) {

        ReservationEntity reservationEntity = new ReservationEntity();
        reservationEntity.setCheckin(source.getCheckin());
        reservationEntity.setCheckout(source.getCheckout());

        RoomService repository=context.getBean(RoomServiceImpl.class);
        RoomEntity associatedRoom=repository.findById(source.getRoomId());


        reservationEntity.setRoomEntity(associatedRoom);

        UsersService repo = context.getBean(UsersServiceImpl.class);
        UsersEntity associatedUser=repo.findUserById(source.getUserId());


        reservationEntity.setUsersEntity(associatedUser);




        return reservationEntity;
    }
}