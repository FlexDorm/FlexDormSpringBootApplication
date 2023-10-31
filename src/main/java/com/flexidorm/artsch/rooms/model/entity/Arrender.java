package com.flexidorm.artsch.rooms.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "arrenders")
public class Arrender extends User {
    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean isVerified;

    /**
     * -Info: UN "arrendador" puede tener MUCHAS "habitaciones"
     * -MappedBy: estará mapeando por el atributo "arrender" de la clase Room
     * -Cascade all & OrphanRemoval: cada vez que se elimine un objeto (un user) automáticamente se eliminan todos los demás datos asociados a este
     */
    @OneToMany(mappedBy = "arrender", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Room> rooms = new HashSet<>();
}
