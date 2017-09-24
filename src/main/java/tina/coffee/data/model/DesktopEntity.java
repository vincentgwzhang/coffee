package tina.coffee.data.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="desktop")
public class DesktopEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int deskId;

    @Column(name="desk_no", nullable=false)
    private int deskNo;

    @Column(name="desk_enable", nullable=false)
    private boolean deskEnable;

    @Column(name="desk_occupied", nullable=false)
    private boolean deskOccupied = false; // Surely that new table has no customer

    public int getDeskId() {
        return deskId;
    }

    public void setDeskId(int deskId) {
        this.deskId = deskId;
    }

    public int getDeskNo() {
        return deskNo;
    }

    public void setDeskNo(int deskNo) {
        this.deskNo = deskNo;
    }

    public boolean isDeskEnable() {
        return deskEnable;
    }

    public void setDeskEnable(boolean deskEnable) {
        this.deskEnable = deskEnable;
    }

    public boolean isDeskOccupied() {
        return deskOccupied;
    }

    public void setDeskOccupied(boolean deskOccupied) {
        this.deskOccupied = deskOccupied;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DesktopEntity that = (DesktopEntity) o;
        return getDeskId() == that.getDeskId() &&
                getDeskNo() == that.getDeskNo() &&
                isDeskEnable() == that.isDeskEnable() &&
                isDeskOccupied() == that.isDeskOccupied();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getDeskId(), getDeskNo(), isDeskEnable(), isDeskOccupied());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("deskId", deskId)
                .add("deskNo", deskNo)
                .add("deskEnable", deskEnable)
                .add("deskOccupied", deskOccupied)
                .toString();
    }
}
