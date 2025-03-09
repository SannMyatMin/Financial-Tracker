package BusinessPJ.FinancialTracker.DTOs;

import java.sql.Date;

public interface Records 
{
    Float getBalance();

    Float getMonthly_income();

    Float getMonthly_expenses();

    Float getNet_balance();

    Date getDate();
}
