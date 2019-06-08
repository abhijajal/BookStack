package models;

import controllers.Application;
import play.db.jpa.Model;

/**
 * Created by abhij on 03/29/2017.
 */
public class forwardQauntity extends Model{
    public forwardQauntity(String data,int noOfCols){
        Application.dispByQuantity(data,noOfCols);
    }
}
