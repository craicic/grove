package org.motoc.gamelibrary.domain.enumeration;

/**
 * The general state of the game copy
 * ex : in activity, in repair, out of order
 */
public enum GeneralState {


    ACTIVE("Actif"),
    OUT_OF_ORDER("Hors service"),
    MISSING_PARTS("Pièces manquantes"),
    IN_REPAIR("En réparation"),
    READY_TO_PLAY("Prêt à jouer"),
    TO_BE_PROTECTED("À plastifier"),
    LOST("Perdu"),
    UNRETURNED("Non restitué"),
    RESTOCKING("Réassort"),
    DONATION("Don à une association"),
    RESERVED("Réservé Air De Jeux"),
    BROKEN("Cassé"),
    NOT_APPLICABLE("Sans objet");



    GeneralState(String frenchTranslation) {
    }

}
