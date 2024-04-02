package com.example.hospital.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;
import java.util.Date;
@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Setter @Getter
public class Patient {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal(TemporalType.DATE)
    private Date birthDay;
    private String name;
    private boolean sick;
    @OneToMany (mappedBy = "patient", fetch = FetchType.LAZY)
    private Collection<RendezVous> rendezVous;

    public String toString(){
        return "ID: " + id + "\n" + "Name: " + name + "\n" + "Birth Day: " + birthDay + "\n" +
                (sick? "Patient is sick" : "Patient is healthy");
    }
}