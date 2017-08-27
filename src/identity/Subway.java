package identity;

public class Subway {
    private String name;
    private String distX;
    private String distY;
    public Subway(String name,String distX,String distY){
        this.name=name;
        this.distX=distX;
        this.distY=distY;
    }
    public void setName(String name){
        this.name=name;
    }
    public void setDistX(String distX){
        this.distX=distX;
    }
    public void setDistY(String distY){
        this.distY=distY;
    }
    public String getName(){
        return this.name;
    }
    public String getDistX(){
        return this.distX;
    }
    public String getDistY(){
        return this.distY;
    }
}
