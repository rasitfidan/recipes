package com.abn_amro.exercise.types;

/**
 * Amount types enum for the ingredient entities
 */
public enum AmountType {
    PIECE,GRAM,KILOGRAM,DOZEN,OUNCE,POUND,TABLESPOON,TEASPOON,GLASS,LITER,CUP;

    public static AmountType fromValue(String type) {
        if(type==null) {
            return null;
        }

        return AmountType.valueOf(type.toUpperCase());
    }
}
