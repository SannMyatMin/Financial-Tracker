package BusinessPJ.FinancialTracker.Model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "User")
@Data
@NoArgsConstructor
public class User 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Id")
    private Integer id;

    @Column(name="Name")
    private String name;

    @Column(name="Mail")
    private String mail;

    @Column(name="Password")
    private String password;

    @Lob
    @Column(name="Photo", columnDefinition="LONGBLOB", nullable=true)
    private byte[] photo;

    @Column(name="Joined_date")
    private Date joinedDate;
}
