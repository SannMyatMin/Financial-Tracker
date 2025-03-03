package BusinessPJ.FinancialTracker.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Balance")
@Data
@NoArgsConstructor
public class Balance 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Balance_id")
    private Integer balanceId;

    @OneToOne
    @JoinColumn(name="User_id",referencedColumnName = "Id")
    private User userId;

    @Column(name="Balance")
    private Float balance;

    @Column(name="Monthly_income")
    private Float monthly_income;

    @Column(name="Monthly_expenses")
    private Float monthly_expenses;

    @Column(name="Net_balances")
    private Float net_balance;
}
