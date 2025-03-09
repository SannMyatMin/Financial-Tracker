package BusinessPJ.FinancialTracker.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class Directory {
    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/SignIn")
    public String SignIn() {
        return "SignIn";
    }

    @GetMapping("/SignUp")
    public String SignUp() {
        return "SignUp";
    }

    @GetMapping("/logout")
    public String LogOut(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null) {
            session.invalidate();
        }
        return "home";
    }

    @GetMapping("/AdminDashboard")
    public String AdminDashBoard() {
        return "adminDashboard";
    }

    @GetMapping("/Dashboard")
    public String UserDashboard() {
        return "Dashboard";
    }

    @GetMapping("/Profile")
    public String Profile() {
        return "Profile";
    }

    @GetMapping("/ProfileCopy")
    public String ProfileC() {
        return "ProfileCopy";
    }

    @GetMapping("/Transaction")
    public String Transaction() {
        return "Transaction";
    }

    @GetMapping("/Record")
    public String Record() {
        return "Record";
    }

    @GetMapping("/Report")
    public String Report() {
        return "Report";
    }

}
