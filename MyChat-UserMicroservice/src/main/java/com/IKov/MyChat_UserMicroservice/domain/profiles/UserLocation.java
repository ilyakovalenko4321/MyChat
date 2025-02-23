package com.IKov.MyChat_UserMicroservice.domain.profiles;

import jakarta.persistence.*;
import lombok.Data;
import org.locationtech.jts.geom.Point;


@Data
@Entity
@Table(name = "user_locations")
public class UserLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long user_id;

    private String city;
    private String country;
    private Point location;
}
