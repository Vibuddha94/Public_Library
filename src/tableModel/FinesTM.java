package tableModel;

public class FinesTM {
    Double late, damage, lost;

    public FinesTM(Double late, Double damage, Double lost) {
        
        this.late = late;
        this.damage = damage;
        this.lost = lost;
    }

    public FinesTM() {
    }

    public Double getLate() {
        return late;
    }

    public void setLate(Double late) {
        this.late = late;
    }

    public Double getDamage() {
        return damage;
    }

    public void setDamage(Double damage) {
        this.damage = damage;
    }

    public Double getLost() {
        return lost;
    }

    public void setLost(Double lost) {
        this.lost = lost;
    }

}
