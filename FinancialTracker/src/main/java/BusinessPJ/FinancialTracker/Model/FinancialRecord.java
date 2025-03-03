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
@Table(name = "Financial_record")
@Data
@NoArgsConstructor
public class FinancialRecord 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Record_id")
    private Integer record_id;

    @ManyToOne
    @JoinColumn(name="User_id", referencedColumnName = "Id")
    private User user_id;

    @Column(name = "Balance")
    private Float balance;

    @Column(name="Monthly_income")
    private Float monthly_income;

    @Column(name="Monthly_expenses")
    private Float monthly_expenses;

    @Column(name="Net_balance")
    private Float net_balance;

    @Column(name="Date")
    private Date date;
}
