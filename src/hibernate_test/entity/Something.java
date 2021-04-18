package hibernate_test.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="table1")
public class Something {

    //@id
    @Column(name = "idtable1")
    private int id;

    public Something(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Something(int id){
        this.id = id;
    }


}
