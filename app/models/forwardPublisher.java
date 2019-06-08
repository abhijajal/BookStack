package models;

import controllers.Application;
import play.db.jpa.Model;

/**
 * Created by abhij on 03/29/2017.
 */
public class forwardPublisher extends Model{
    public forwardPublisher(String data,int noOfCols)
    {
        Application.dispByPublisher(data,noOfCols);
    }
}
