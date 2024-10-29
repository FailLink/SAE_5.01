package com.example.sae501.Model.Entity;

import java.io.Serializable;
import java.util.Objects;


public class CleCompositeMonstreLieux implements Serializable {
    private Long monstreId;
    private Long lieuxId;

    public CleCompositeMonstreLieux() {
    }

    public CleCompositeMonstreLieux(Long monstreId, Long lieuxId) {
        this.monstreId = monstreId;
        this.lieuxId = lieuxId;
    }

    public Long getMonstreId() {
        return monstreId;
    }

    public void setMonstreId(Long monstreId) {
        this.monstreId = monstreId;
    }

    public Long getLieuxId() {
        return lieuxId;
    }

    public void setLieuxId(Long lieuxId) {
        this.lieuxId = lieuxId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CleCompositeMonstreLieux that = (CleCompositeMonstreLieux) o;
        return Objects.equals(monstreId, that.monstreId) && Objects.equals(lieuxId, that.lieuxId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(monstreId, lieuxId);
    }
}
