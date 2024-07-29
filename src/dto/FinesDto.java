package dto;

public class FinesDto {
    Integer id;
    Double late, damage, lost;
    
    public FinesDto() {
    }

    public FinesDto(Integer id, Double late, Double damage, Double lost) {
        this.id = id;
        this.late = late;
        this.damage = damage;
        this.lost = lost;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
