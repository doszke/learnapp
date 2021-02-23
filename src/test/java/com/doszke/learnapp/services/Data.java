package com.doszke.learnapp.services;

public class Data {

    public static final String[][] CATEGORY_REPO_DATA = new String[][]{
            {"język japoński"},
            {"mechanika i wytrzymałość"},
            {"materiałoznawstwo"},
            {"chemia organiczna"}
    };

    public static final String[][] CARD_REPO_DATA = new String[][]{
            {"a", "a"},
            {"ab", "ab"},
            {"ac", "ac"},
            {"ad", "ad"},
    };

    public static final String[][] CARD_REPO_DATA_INVALID = new String[][]{
            {},
            {"ab"},
            {"ac", "ac", "ac"},
            {"ad", "ad", "ad", "ad"},
    };

    public static final String[][] CATEGORY_REPO_DATA_INVALID = new String[][]{
            {},
            {"ab", "ab"},
            {"ac", "ac", "ac", "ac"},
            {"ad", "ad", "ad", "ad", "ad"},
    };

    public static final String CATEGORY_ADDED = "Card category added. ";
    public static final String CATEGORY_DUPLICATE = "There is already such a category. " ;
    public static final String CATEGORY_NOT_FOUND = "There is no category defined by given data. ";
    public static final String CATEGORY_HAS_CARDS = "This category contains bound cards. Do you wish to remove this category nonetheless? ";
    public static final String CATEGORY_REMOVED = "Category removed. ";

    public static final String CARD_ADDED = "Card added. ";
    public static final String CARD_DUPLICATE = "There is already such a card. ";
    public static final String CARD_REMOVED = "Card removed. ";
    public static final String CARD_NOT_PRESENT = "There is no card specified by given data. ";

    public static final String INVALID_ARG_LEN = "Too much arguments passed. ";

}
