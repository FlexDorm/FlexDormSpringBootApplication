package com.flexidorm.artsch.rental_management.domain.entities;

        import jakarta.persistence.*;
        import lombok.AllArgsConstructor;
        import lombok.Data;
        import lombok.NoArgsConstructor;

        import java.util.HashSet;
        import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long roomId;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 200)
    private String description;

    @Column(nullable = false, length = 50)
    private String address;

    @Column(nullable = false)
    private double price;

    @Column(name = "status", columnDefinition = "VARCHAR(255) DEFAULT 'free' NOT NULL")
    private String status;


    private String imageUrl;

    private String nearUniversities;



    //MUCHAS "habitaciones" van a estar en UN "arrendador"
    @ManyToOne
    @JoinColumn(name = "arrender_id", nullable = false)
    private Arrender arrender;

}