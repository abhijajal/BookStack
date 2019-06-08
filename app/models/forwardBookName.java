package models;

import controllers.Application;
import play.db.jpa.Model;

/**
 * Created by abhij on 03/19/2017.
 */
public class forwardBookName extends Model {
    public forwardBookName(String data)
    {
        Application.orderPlaced(data);
    }
}
