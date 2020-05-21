package org.motoc.gamelibrary.model.enumeration;

/**
 * The general state of the game copy
 * ex : in activity, in repair, out of order
 *
 * @author RouzicJ
 */
public enum GeneralStateEnum {


    IN_ACTIVITY("En activité"),
    OUT_OF_ORDER("Hors service"),
    MISSING_PARTS("Pièces manquantes"),
    IN_REPAIR("En réparation"),
    READY_TO_PLAY("Prêt à jouer"),
    TO_BE_PROTECTED("À protéger"),
    LOST("Perdu"),
    UNRETURNED("Non restitué"),
    RESTOCKING("Réassort"),
    DONATION("Don à une association");

    GeneralStateEnum(String frenchTranslation) {
    }

}
