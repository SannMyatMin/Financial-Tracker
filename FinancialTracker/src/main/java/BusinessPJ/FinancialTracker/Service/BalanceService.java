package BusinessPJ.FinancialTracker.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import BusinessPJ.FinancialTracker.Model.Balance;
import BusinessPJ.FinancialTracker.Model.User;
import BusinessPJ.FinancialTracker.Repository.BalanceRepository;

@Service
public class BalanceService 
{
    @Autowired
    private BalanceRepository balanceRepo;
    @Autowired
    private UserService userService;

    public void addUserBalance(String userName, Float balance, Float income, Float expenses) {
        User user = userService.getUserByName(userName);
        Float net_balances = income - expenses;
        Balance user_balance = new Balance();
        user_balance.setUserId(user);
        user_balance.setBalance(balance);
        user_balance.setMonthly_income(income);
        user_balance.setMonthly_expenses(expenses);
        user_balance.setNet_balance(net_balances);
        balanceRepo.save(user_balance);
    }

    public Balance findByU_id(Integer u_id) {
        Optional<Balance> balance = balanceRepo.findByUserId(u_id);
        return balance.get();
    }
    
    public void updateUserBalance(String userName, Float balance, Float income, Float expenses) {
        User user = userService.getUserByName(userName);
        Float net_balance = income - expenses;
        Balance current_Balance = findByU_id(user.getId());
        current_Balance.setBalance(balance);
        current_Balance.setMonthly_income(income);
        current_Balance.setMonthly_expenses(expenses);
        current_Balance.setNet_balance(net_balance);
        balanceRepo.save(current_Balance);
    }
    
    public void updateBalance(String name, String transactionType, Float amount) {
        User user       = userService.getUserByName(name);
        Balance balance = findByU_id(user.getId());
        if (transactionType.equals("income")) {
            balance.setBalance(balance.getBalance() + amount);
        }
        if (transactionType.equals( "expenses")) {
            balance.setBalance(balance.getBalance() - amount);
        }
        balanceRepo.save(balance);
    }

}
