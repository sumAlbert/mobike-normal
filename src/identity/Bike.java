package identity;

public class Bike {
    private String distX;
    private String distY;
    private String id;
    public Bike(String distX,String distY,String id){
        this.id=id;
        this.distY=distY;
        this.distX=distX;
    }
    public void setDistX(String distX){
        this.distX=distX;
    }
    public void setDistY(String distY){
        this.distY=distY;
    }
    public void setId(String id){
        this.id=id;
    }
    public String getDistX(){
        return this.distX;
    }
    public String getDistY(){
        return this.distY;
    }
    public String getId(){
        return this.id;
    }
}
