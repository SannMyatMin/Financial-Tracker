package BusinessPJ.FinancialTracker.Model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Transaction")
@Data
@NoArgsConstructor
public class Transaction 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Transaction_id")
    private Integer transaction_id;

    @ManyToOne
    @JoinColumn(name="User_id", referencedColumnName = "Id")
    private User user_id;

    @Column(name="Type")
    private String type;

    @Column(name="Category")
    private String category;

    @Column(name="Description")
    private String description;

    @Column(name="Amount")
    private Float amount;

    @Column(name="Date")
    private Date date;
}
