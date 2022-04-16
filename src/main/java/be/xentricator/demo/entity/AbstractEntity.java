package be.xentricator.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.domain.Persistable;
import org.springframework.data.util.ProxyUtils;
import org.springframework.lang.Nullable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.io.Serializable;

@MappedSuperclass
public abstract class AbstractEntity<PK extends Serializable> implements Persistable<PK> {
    @Id
    @GeneratedValue
    @Nullable
    private PK id;

    protected AbstractEntity() {
    }

    @Nullable
    public PK getId() {
        return this.id;
    }

    public void setId(@Nullable PK id) {
        this.id = id;
        this.setPkClass((Class<PK>) id.getClass());
    }

    @Transient
    @JsonIgnore
    private Class<PK> pkClass;

    public Class<PK> getPkClass() {
        return pkClass;
    }

    public void setPkClass(Class<PK> pkClass) {
        this.pkClass = pkClass;
    }

    @Transient
    @JsonIgnore
    public boolean isNew() {
        return null == this.getId();
    }

    public String toString() {
        return String.format("Entity of type %s with id: %s", this.getClass().getName(), this.getId());
    }

    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        } else if (this == obj) {
            return true;
        } else if (!this.getClass().equals(ProxyUtils.getUserClass(obj))) {
            return false;
        } else {
            AbstractEntity<?> that = (AbstractEntity<?>) obj;
            return null != this.getId() && this.getId().equals(that.getId());
        }
    }

    public int hashCode() {
        int hashCode = 17;
        return hashCode + (null == this.getId() ? 0 : this.getId().hashCode() * 31) + (null == this.getId() ? 0 : this.getId().hashCode() * 31);
    }
}
