package org.motoc.gamelibrary.domain.enumeration;

/**
 * To describe and differentiate a Loan from a Reservation.
 */
public enum LoanStatus {


    /**
     * In creation
     */
    IN_CREATION,
    /**
     * Reservation, start and ending date are set, but article were not withdrew yet.
     */
    SCHEDULED,
    /**
     * Was a reservation, but canceled.
     */
    CANCELED,
    /**
     * Loan ongoing, article has been loaned by the adherent.
     */
    ONGOING,
    /**
     * Loan finished and close, article are returned.
     */
    CLOSED,
    /**
     * Loan finished and close, at least one article is missing.
     */
    CLOSED_WITH_NOTE

}
