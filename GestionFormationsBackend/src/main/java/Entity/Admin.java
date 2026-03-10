package Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "admins")
@PrimaryKeyJoinColumn(name = "id")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Admin extends Person {

    @Column(name = "permissions")
    private int permissions;

    @Column(name = "is_default", nullable = false)
    private boolean isDefault;
}