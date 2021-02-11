package apiobjects;

import java.sql.*;

/**
 * Created on Mar, 2018
 **/

public class AbstractDBObject {
    public final Connection con;

    public AbstractDBObject(Connection con) {
        this.con = con;
    }

}
