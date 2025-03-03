package BusinessPJ.FinancialTracker.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Map;

import BusinessPJ.FinancialTracker.Model.User;
import BusinessPJ.FinancialTracker.Service.BalanceService;
import BusinessPJ.FinancialTracker.Service.FinancialResordService;
import BusinessPJ.FinancialTracker.Service.TransactionService;
import BusinessPJ.FinancialTracker.Service.UserService;

@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class UserController
{
    @Autowired
    private UserService userService;
    @Autowired
    private BalanceService balanceService;
    @Autowired
    private FinancialResordService recordService;
    @Autowired
    private TransactionService transactionService;

    @PostMapping("/signup")
    public ResponseEntity<Object> signUp(@RequestBody Map<String,String> signUpData) {
        String name = signUpData.get("name");
        String mail = signUpData.get("mail");
        String password = signUpData.get("password");
        if (userService.usedMail(mail)) {
            return ResponseEntity.status(401).body("used mail");
        } else if (userService.usedName(name)) {
            return ResponseEntity.status(401).body("used name");
        } else {
            try {
                InputStream inputS = getClass().getResourceAsStream("Default.jpg");
                byte[] imageByte   = inputS.readAllBytes();
                userService.addNewUser(name, mail, password, imageByte);
                balanceService.addUserBalance(name, (float) 0, (float) 0, (float) 0);
                return ResponseEntity.ok("success");
            } catch (Exception e) {
                return ResponseEntity.status(400).body("Fail to add");
            }
        }
    }

    @PostMapping("/updateData")
    public ResponseEntity<String> profileUpdate(@RequestParam("old_name") String old_name,
                                                @RequestParam("new_name") String new_name,
                                                @RequestParam("mail") String mail,
                                                @RequestParam(value = "image", required = false) MultipartFile photo,
                                                @RequestParam("balance") Float balance,
                                                @RequestParam("income") Float income,
                                                @RequestParam("expenses") Float expenses) 
    {
        byte[] imageByte;
        try {
            if (photo == null || photo.isEmpty()) {
                InputStream inputS = getClass().getResourceAsStream("Default.jpg");
                imageByte = inputS.readAllBytes();
            }
            else {
                imageByte = photo.getBytes();
            }
            userService.updateUser(old_name, new_name, mail, imageByte);
            balanceService.updateUserBalance(new_name, balance, income, expenses);
            User user = userService.getUserByName(old_name);
            recordService.addRecord(user, balance, income, expenses);
            recordService.addRecord(user, balance, income, expenses);
            return ResponseEntity.ok("success");
        }
        catch (Exception e) {
            return ResponseEntity.status(500).body("Server error: " + e.getMessage());
        }
    }

    @PostMapping("/Data")
    public ResponseEntity<Object> getUserData(@RequestBody Map<String, String> userData) {
        String name = userData.get("name");
        try {
            return ResponseEntity.ok(userService.getUserData(name));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(401).body("Fail");
        }
    }

    @PostMapping("/transaction")
    public ResponseEntity<String> addTransaction(@RequestBody Map<String, String> data) {
        String name = data.get("name");
        String transactionType = data.get("transactionType");
        Float amount = Float.parseFloat(data.get("amount"));
        String description = data.get("description");
        try {
            transactionService.addTransaction(name, transactionType, amount, description);
            balanceService.updateBalance(name, transactionType, amount);
            return ResponseEntity.ok("Transaction added sucessfully");
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Fail to add");
        }
    }

    
    @PostMapping("/getTransactions")
    public ResponseEntity<Object> getTransactions(@RequestBody Map<String,String> user) {
        String name = user.get("name");
        try {
            return ResponseEntity.ok(transactionService.getTransaction(name));
        }
        catch (Exception e) {
            return ResponseEntity.status(401).body("Fail");
        }
    }
    

}
