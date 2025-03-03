package BusinessPJ.FinancialTracker.DTOs;

import java.sql.Date;

public interface UserTransaction 
{
    String getType();

    String getCategory();

    String getDescription();

    Float getAmount();
    
    Date getDate();
}
