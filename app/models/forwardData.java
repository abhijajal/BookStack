package models;

import com.sun.org.apache.xpath.internal.operations.Mod;
import controllers.Application;
import play.db.jpa.Model;

/**
 * Created by abhij on 03/19/2017.
 */
public class forwardData extends Model {
    public forwardData(String data,int noOfCols)
    {
        Application.viewBooks(data,noOfCols);
    }
}
