package BusinessPJ.FinancialTracker.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import BusinessPJ.FinancialTracker.DTOs.DashboardData;
import BusinessPJ.FinancialTracker.DTOs.Udata;
import BusinessPJ.FinancialTracker.DTOs.UserData;
import BusinessPJ.FinancialTracker.Model.FinancialRecord;
import BusinessPJ.FinancialTracker.Model.User;
import BusinessPJ.FinancialTracker.Repository.UserRepository;

@Service
public class UserService 
{
    private final PasswordEncoder bcryptEncoder;

    public UserService(PasswordEncoder passwordEncoder) {
        this.bcryptEncoder = passwordEncoder;
    }

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private FinancialResordService recordService;

    public boolean usedMail(String mail){
        return userRepo.existsByMail(mail);
    }

    public boolean usedName(String name) {
        return userRepo.existsByName(name);
    }
    
    public User getUserByName(String name) {
        Optional<User> user = userRepo.findByName(name);
        return user.get();
    }

    public User getUserByMail(String mail) {
        Optional<User> User = userRepo.findByMail(mail);
        return User.get();
    }

    public boolean isPasswordCorrect(String mail, String password) {
        User user = getUserByMail(mail);
        return bcryptEncoder.matches(password, user.getPassword());
    }
    
    public Udata getUserNameMail(String mail) {
        Optional<Udata> u_data = userRepo.getUserNameMail(mail);
        return u_data.get();
    }

    public void updateUser(String old_name, String new_name, String mail, byte[] imageByte) {
        User user = getUserByName(old_name);
        user.setName(new_name);
        user.setMail(mail);
        user.setPhoto(imageByte);
        userRepo.save(user);
    }


    public void addNewUser(String name, String mail, String password, byte[] imageByte) {
        String encoded_password = bcryptEncoder.encode(password);
        User new_user           = new User();
        new_user.setName(name);
        new_user.setMail(mail);
        new_user.setPassword(encoded_password);
        new_user.setPhoto(imageByte);
        new_user.setJoinedDate(Date.valueOf(LocalDate.now()));
        userRepo.save(new_user);
    }

    public DashboardData getUserData(String name) {
        Optional<UserData> data     = userRepo.getUserDataByName(name);
        UserData U_data             = data.get();
        User user                   = getUserByName(name);
        FinancialRecord last_record = recordService.getRecord(user);
        Float netBalance  = U_data.getBalance() - last_record.getBalance();
        Float netIncome   = U_data.getIncome() - last_record.getMonthly_income();
        Float netExpenses = U_data.getExpenses() - last_record.getMonthly_expenses();

        DashboardData D_data        = new DashboardData(U_data.getName(),
                                                        U_data.getMail(),
                                                        U_data.getPhoto(),
                                                        U_data.getBalance(),
                                                        U_data.getIncome(),
                                                        U_data.getExpenses(),
                                                        netBalance,
                                                        netIncome,
                                                        netExpenses);
        return D_data;
    }

}
