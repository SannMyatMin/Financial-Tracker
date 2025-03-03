package BusinessPJ.FinancialTracker.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import BusinessPJ.FinancialTracker.DTOs.UserTransaction;
import BusinessPJ.FinancialTracker.Model.Transaction;
import BusinessPJ.FinancialTracker.Model.User;
import BusinessPJ.FinancialTracker.Repository.TransactionRepository;

@Service
public class TransactionService 
{
    @Autowired
    private TransactionRepository transactionRepo;
    @Autowired
    private UserService userService;
    @Autowired
    private TransactionCategorizer categorizer;

    public void addTransaction(String name, String transactionType, Float amount, String description) {
        User user = userService.getUserByName(name);
        String category = categorizer.categorizeTransaction(description);
        Transaction new_transaction = new Transaction();
        new_transaction.setUser_id(user);
        new_transaction.setCategory(category);
        new_transaction.setType(transactionType);
        new_transaction.setAmount(amount);
        new_transaction.setDescription(description);
        new_transaction.setDate(Date.valueOf(LocalDate.now()));
        transactionRepo.save(new_transaction);
    }

    public List<UserTransaction> getTransaction(String name) {
        User user = userService.getUserByName(name);
        return transactionRepo.getUserTransaction(user.getId());
    }


}
