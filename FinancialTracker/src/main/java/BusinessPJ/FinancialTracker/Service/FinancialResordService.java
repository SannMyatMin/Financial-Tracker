package BusinessPJ.FinancialTracker.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import BusinessPJ.FinancialTracker.Model.FinancialRecord;
import BusinessPJ.FinancialTracker.Model.User;
import BusinessPJ.FinancialTracker.Repository.FinancialRecordRepository;

@Service
public class FinancialResordService 
{
    @Autowired
    private FinancialRecordRepository recordRepo;
 

    public FinancialRecord getRecord(User user) {
        Optional<FinancialRecord> record = recordRepo.findByUser_id(user.getId());
        return record.get();
    }

    
    public void addRecord(User user, Float balance, Float income, Float expenses) {
        FinancialRecord new_record = new FinancialRecord();
        new_record.setUser_id(user);
        new_record.setBalance(balance);
        new_record.setMonthly_income(income);
        new_record.setMonthly_expenses(expenses);
        new_record.setNet_balance(income - expenses);
        new_record.setDate(Date.valueOf(LocalDate.now()));
        recordRepo.save(new_record);
    }

}
