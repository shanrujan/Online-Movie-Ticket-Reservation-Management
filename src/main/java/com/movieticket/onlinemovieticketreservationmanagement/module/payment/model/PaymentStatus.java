package com.movieticket.onlinemovieticketreservationmanagement.module.payment.model;

public enum PaymentStatus {

    // Payment is waiting to be completed
    PENDING,

    // Payment completed successfully
    SUCCESS,

    // Payment process failed
    FAILED,

    // Payment amount refunded
    REFUNDED
}