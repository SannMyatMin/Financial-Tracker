package BusinessPJ.FinancialTracker.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Directory 
{
    @GetMapping("/home")
    public String home()
    {
        return "home";
    }

    @GetMapping("/SignIn")
    public String SignIn()
    {
        return "SignIn";
    }

    @GetMapping("/SignUp")
    public String SignUp()
    {
        return "SignUp";
    }

    @GetMapping("/AdminDashboard")
    public String AdminDashBoard()
    {
        return "adminDashboard";
    }

    @GetMapping("/Dashboard")
    public String UserDashboard()
    {
        return "Dashboard";
    }

    @GetMapping("/Profile")
    public String Profile()
    {
        return "Profile";
    }

    @GetMapping("/Transaction")
    public String Transaction()
    {
        return "Transaction";
    }

    @GetMapping("/Record")
    public String Record()
    {
        return "Record";
    }
}
