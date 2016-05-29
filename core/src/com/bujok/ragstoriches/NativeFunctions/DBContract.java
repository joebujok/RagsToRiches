package com.bujok.ragstoriches.NativeFunctions;



/**
 * Created by joebu on 06/01/2016.
 */
public class DBContract {

    public static final  int    DATABASE_VERSION   = 1;
    public static final  String DATABASE_NAME      = "database.db";
    private static final String TEXT_TYPE          = " TEXT";
    private static final String COMMA_SEP          = ",";
    public static final String KEY_PRODUCTID = "ProductID";

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private DBContract() {}



    public interface BaseColumns
    {
        /**
         * The unique ID for a row.
         * <P>Type: INTEGER (long)</P>
         */
        public static final String _ID = "_id";

        /**
         * The count of rows in a directory.
         * <P>Type: INTEGER</P>
         */
        public static final String _COUNT = "_count";
    }
}
