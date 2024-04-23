package br.com.rpx.budgetbuddy.entities;

import br.com.rpx.budgetbuddy.dto.GainDTO;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "gain")
@ToString
@NoArgsConstructor
public class Gain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate createDate;
    private BigDecimal gainValue;
    private String description;

    public Gain(BigDecimal gainValue, String description) {
        this.gainValue = gainValue;
        this.description = description;
        this.createDate = LocalDate.now();
    }

    public Gain(GainDTO obj) {
        setGainValue(obj.getValue());
        setDescription(obj.getDescription());
        setCreateDate(LocalDate.now());
    }

    public Long getId() {
        return id;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public void setGainValue(BigDecimal gainValue) {
        this.gainValue = gainValue;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public BigDecimal getGainValue() {
        return gainValue;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gain gain = (Gain) o;
        return Objects.equals(id, gain.id) && Objects.equals(createDate, gain.createDate) && Objects.equals(gainValue, gain.gainValue) && Objects.equals(description, gain.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createDate, gainValue, description);
    }
}
