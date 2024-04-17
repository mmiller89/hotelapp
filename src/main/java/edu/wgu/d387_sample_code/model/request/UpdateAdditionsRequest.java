package edu.wgu.d387_sample_code.model.request;

import edu.wgu.d387_sample_code.entity.AdditionEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UpdateAdditionsRequest {
        private Long id;

        private Long userId;

        private Long reservationId;

        private Long roomId;
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDate checkin;
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDate checkout;

        private Integer roomNumber;

        private Integer roomPrice;

        private Set<AdditionEntity> additions = new HashSet<>();

        public UpdateAdditionsRequest() {
        }

        public UpdateAdditionsRequest(Long userId, Long reservationId, Long roomId, LocalDate checkin, LocalDate checkout, Integer roomNumber, Integer roomPrice, Set<AdditionEntity> additions) {
                this.userId = userId;
                this.reservationId = reservationId;
                this.roomId = roomId;
                this.checkin = checkin;
                this.checkout = checkout;
                this.roomNumber = roomNumber;
                this.roomPrice = roomPrice;
                this.additions = additions;

        }

        public LocalDate getCheckin() {
                return checkin;
        }

        public void setCheckin(LocalDate checkin) {
                this.checkin = checkin;
        }

        public LocalDate getCheckout() {
                return checkout;
        }

        public void setCheckout(LocalDate checkout) {
                this.checkout = checkout;
        }

        public Integer getRoomNumber() {
                return roomNumber;
        }

        public void setRoomNumber(Integer roomNumber) {
                this.roomNumber = roomNumber;
        }

        public Integer getRoomPrice() {
                return roomPrice;
        }

        public void setRoomPrice(Integer roomPrice) {
                this.roomPrice = roomPrice;
        }

        public Set<AdditionEntity> getAdditions() {
                return additions;
        }

        public void setAdditions(Set<AdditionEntity> additions) {
                this.additions = additions;
        }

        public Long getRoomId() {
                return roomId;
        }

        public void setRoomId(Long roomId) {
                this.roomId = roomId;
        }

        public Long getUserId() {
                return userId;
        }

        public void setUserId(Long userId) {
                this.userId = userId;
        }

        public Long getReservationId() {
                return reservationId;
        }

        public void setReservationId(Long reservationId) {
                this.reservationId = reservationId;
        }
}