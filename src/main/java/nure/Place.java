package nure;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.persistence.*;

import java.util.List;
import java.util.Date;

public class Place {
    public String name_place;
    public Point place_cord;
    public String objectId;
    public String meta_info;
    public String ownerId;
    public String description;
    public String img_link;
    public Point getPlace_cord()
    {
        return place_cord;
    }

    public void setPlace_cord( Point place_cord )
    {
        this.place_cord = place_cord;
    }

    public String getObjectId()
    {
        return objectId;
    }

    public String getMeta_info()
    {
        return meta_info;
    }

    public void setMeta_info( String meta_info )
    {
        this.meta_info = meta_info;
    }

    public String getOwnerId()
    {
        return ownerId;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription( String description )
    {
        this.description = description;
    }

    public String getImg_link()
    {
        return img_link;
    }

    public void setImg_link( String img_link )
    {
        this.img_link = img_link;
    }


    public Place save()
    {
        return Backendless.Data.of( Place.class ).save( this );
    }

    public void saveAsync( AsyncCallback<Place> callback )
    {
        Backendless.Data.of( Place.class ).save( this, callback );
    }

    public Long remove()
    {
        return Backendless.Data.of( Place.class ).remove( this );
    }

    public void removeAsync( AsyncCallback<Long> callback )
    {
        Backendless.Data.of( Place.class ).remove( this, callback );
    }

    public static Place findById( String id )
    {
        return Backendless.Data.of( Place.class ).findById( id );
    }

    public static void findByIdAsync( String id, AsyncCallback<Place> callback )
    {
        Backendless.Data.of( Place.class ).findById( id, callback );
    }

    public static Place findFirst()
    {
        return Backendless.Data.of( Place.class ).findFirst();
    }

    public static void findFirstAsync( AsyncCallback<Place> callback )
    {
        Backendless.Data.of( Place.class ).findFirst( callback );
    }

    public static Place findLast()
    {
        return Backendless.Data.of( Place.class ).findLast();
    }

    public static void findLastAsync( AsyncCallback<Place> callback )
    {
        Backendless.Data.of( Place.class ).findLast( callback );
    }

    public static List<Place> find( DataQueryBuilder queryBuilder )
    {
        return Backendless.Data.of( Place.class ).find( queryBuilder );
    }

    public static void findAsync( DataQueryBuilder queryBuilder, AsyncCallback<List<Place>> callback )
    {
        Backendless.Data.of( Place.class ).find( queryBuilder, callback );
    }

    public static com.backendless.persistence.Point toPoint(org.locationtech.jts.geom.Point p){
        var p2 = new com.backendless.persistence.Point();
        p2.setX(p.getX());
        p2.setY(p.getY());
        return p2;
    }

    @Override
    public String toString() {
        return "Place{" +
                "name_place='" + name_place + '\'' +
                ", place_cord=" + place_cord +
                ", objectId='" + objectId + '\'' +
                ", meta_info='" + meta_info + '\'' +
                ", ownerId='" + ownerId + '\'' +
                ", description='" + description + '\'' +
                ", img_link='" + img_link + '\'' +
                '}';
    }
}
