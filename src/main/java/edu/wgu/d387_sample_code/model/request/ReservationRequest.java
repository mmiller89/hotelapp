package edu.wgu.d387_sample_code.model.request;

import edu.wgu.d387_sample_code.entity.UsersEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class ReservationRequest {

        private Long id;
        private Long roomId;

        private Long userId;
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDate checkin;
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDate checkout;

        public ReservationRequest() {
        }

        public ReservationRequest( Long roomId, Long userId, LocalDate checkin, LocalDate checkout) {
                this.roomId = roomId;
                this.userId = userId;
                this.checkin = checkin;
                this.checkout = checkout;
        }

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public Long getRoomId() {
                return roomId;
        }

        public void setRoomId(Long roomId) {
                this.roomId = roomId;
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

        public Long getUserId() {
                return userId;
        }

        public void setUserId(Long userId) {
                this.userId = userId;
        }
}