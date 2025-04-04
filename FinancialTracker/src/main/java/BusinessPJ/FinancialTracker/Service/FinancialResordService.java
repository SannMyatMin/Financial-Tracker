package BusinessPJ.FinancialTracker.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import BusinessPJ.FinancialTracker.DTOs.Records;
import BusinessPJ.FinancialTracker.Model.Balance;
import BusinessPJ.FinancialTracker.Model.FinancialRecord;
import BusinessPJ.FinancialTracker.Model.User;
import BusinessPJ.FinancialTracker.Repository.BalanceRepository;
import BusinessPJ.FinancialTracker.Repository.FinancialRecordRepository;

@Service
public class FinancialResordService 
{
    @Autowired
    private FinancialRecordRepository recordRepo;
    @Autowired
    private BalanceRepository balanceRepo;
 

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

    public List<Records> getRecordsOfUser(User user) {
        return recordRepo.getRecordsOfUser(user.getId());
    }

    @Scheduled(cron = "0 0 0 1 * ?")
    public void addNewRecord() {
        List<User> users = recordRepo.getAllUsers();
        for (User U : users) {
            Float last_month_balance = recordRepo.getLastMonthbalance(U.getId());
            Float last_month_total_income = recordRepo.getLastMonthTotalIncome(U.getId());
            Float last_month_total_expenses = recordRepo.getLastMonthTotalExpenses(U.getId());
            Float net_balance = last_month_total_income - last_month_total_expenses;
            
            Optional<Balance> user_balance = balanceRepo.findById(U.getId());
            Balance new_balance = user_balance.get();
            new_balance.setBalance(last_month_balance);
            new_balance.setMonthly_income(last_month_total_income);
            new_balance.setMonthly_expenses(last_month_total_expenses);
            new_balance.setNet_balance(net_balance);
            balanceRepo.save(new_balance);

            FinancialRecord new_Record = new FinancialRecord();
            new_Record.setBalance(last_month_balance);
            new_Record.setMonthly_income(last_month_total_income);
            new_Record.setMonthly_expenses(last_month_total_expenses);
            new_Record.setNet_balance(net_balance);
            new_Record.setDate(Date.valueOf(LocalDate.now()));
            recordRepo.save(new_Record);  
        }
    }

}
