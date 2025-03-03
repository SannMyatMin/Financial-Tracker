package BusinessPJ.FinancialTracker.DTOs;

import java.util.Base64;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DashboardData 
{
    private String name;
    private String mail;
    private String photo;
    private Float balance;
    private Float income;
    private Float expenses;
    private Float netBalance;
    private Float netIncome;
    private Float netExpenses;

    public DashboardData(String name,
                         String mail,
                         byte[] imageByte,
                         Float balance,
                         Float income,
                         Float expenses,
                         Float net_balance,
                         Float net_income,
                         Float net_expenses) 
    {
        this.name        = name;
        this.mail        = mail;
        this.photo       = Base64.getEncoder().encodeToString(imageByte);
        this.balance     = balance;
        this.income      = income;
        this.expenses    = expenses;
        this.netBalance  = net_balance;
        this.netIncome   = net_income;
        this.netExpenses = net_expenses;
    }

}
