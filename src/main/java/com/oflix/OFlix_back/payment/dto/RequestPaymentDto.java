package com.oflix.OFlix_back.payment.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RequestPaymentDto {
    private String paymentId;
    private String resultCode;
    private String movieTitle;
    private Long scheduleId;
    private List<Ticket> tickets;
    private String totalAmount;
    private String paymentMethod;
    private String userId;

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    public static class Ticket {
        private int seatIndex;
        private String seatLabel;
        private TicketType ticketType;

        public Ticket(int seatIndex, String seatLabel, TicketType ticketType) {
            this.seatIndex = seatIndex;
            this.seatLabel = seatLabel;
            this.ticketType = ticketType;
        }

        public int getSeatIndex() {
            return seatIndex;
        }

        public String getSeatLabel() {
            return seatLabel;
        }

        public TicketType getTicketType() {
            return ticketType;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    public static class TicketType {
        private String id;
        private String name;
        private int price;

        public TicketType(String id, String name, int price) {
            this.id = id;
            this.name = name;
            this.price = price;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getPrice() {
            return price;
        }
    }
}
