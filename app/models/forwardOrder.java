package models;

import controllers.Application;
import play.db.jpa.Model;

/**
 * Created by abhij on 03/24/2017.
 */
public class forwardOrder extends Model {
    public forwardOrder(String order,int price,int x){
        Application.purchase(order,price,x);
    }
}
