package BusinessPJ.FinancialTracker.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class TransactionCategorizer 
{
    // Define category rules (keywords for each category)
    private static final Map<String, List<String>> Expenses_CATEGORY_RULES = new HashMap<>();
    private static final Map<String, List<String>> Income_CATEGORY_RULES = new HashMap<>();
    private static Map<String, List<String>> CATEGORY_RULES = new HashMap<>();

    static {
            Expenses_CATEGORY_RULES.put("Food & Groceries",
                            List.of("grocery", "food", "supermarket", "restaurant", "cafe", "snacks",
                                            "vegetables", "fruits", "meat", "dairy", "bakery", "beverages"));
            Expenses_CATEGORY_RULES.put("Housing", List.of("rent", "housing", "mortgage", "apartment", "property tax",
                            "home insurance", "maintenance", "furniture", "repair", "home improvement"));
            Expenses_CATEGORY_RULES.put("Transportation",
                            List.of("gas", "fuel", "uber", "taxi", "bus", "train", "subway", "parking",
                                            "toll", "car rental", "car repair", "bike", "public transport"));
            Expenses_CATEGORY_RULES.put("Entertainment",
                            List.of("event", "cinema", "netflix", "spotify", "concert", "games",
                                            "books", "theater", "sports", "nightclub", "hobbies", "leisure"));
            Expenses_CATEGORY_RULES.put("Utilities",
                            List.of("electricity", "water", "internet", "phone bill", "gas bill", "sewage",
                                            "garbage", "cable TV", "heating", "cooling", "solar panel",
                                            "waste disposal"));
            Expenses_CATEGORY_RULES.put("Health & Fitness",
                            List.of("gym", "yoga", "doctor", "hospital", "medication", "pharmacy",
                                            "therapy", "dentist", "insurance", "optometrist", "wellness"));
            Expenses_CATEGORY_RULES.put("Shopping",
                            List.of("clothing", "electronics", "fashion", "shoes", "accessories", "beauty",
                                            "home decor", "gadget", "furniture", "appliances"));
            Expenses_CATEGORY_RULES.put("Education", List.of("school", "college", "university", "books", "courses",
                            "online learning", "tuition", "exam fees", "stationery", "backpacks", "library"));
            Expenses_CATEGORY_RULES.put("Travel",
                            List.of("hotel", "flight", "airfare", "vacation", "tour", "cruise", "resort",
                                            "visa", "luggage", "transportation"));
            Expenses_CATEGORY_RULES.put("Business & Work",
                            List.of("office", "software", "subscription", "salary", "investment",
                                            "startup", "freelance", "consulting", "business trip"));
            Expenses_CATEGORY_RULES.put("Other", List.of()); // Default category
    }
    
    static {
            Income_CATEGORY_RULES.put("Salary & Wages",
                            List.of("salary", "wages", "paycheck", "payroll", "stipend", "bonus", "overtime"));
            Income_CATEGORY_RULES.put("Business & Freelancing", List.of("freelance", "consulting", "contract",
                            "self-employment", "business income", "side hustle"));
            Income_CATEGORY_RULES.put("Investments", List.of("dividends", "stocks", "bonds", "mutual funds",
                            "capital gains", "real estate income"));
            Income_CATEGORY_RULES.put("Rental Income",
                            List.of("rent payment", "lease income", "property rental", "tenant payment"));
            Income_CATEGORY_RULES.put("Government Benefits", List.of("pension", "social security", "unemployment",
                            "disability benefits", "veteran benefits"));
            Income_CATEGORY_RULES.put("Gifts & Donations",
                            List.of("gift", "donation received", "inheritance", "financial aid", "scholarship"));
            Income_CATEGORY_RULES.put("Royalties & Licensing",
                            List.of("royalty", "patent", "copyright", "licensing fee"));
            Income_CATEGORY_RULES.put("Refunds & Reimbursements",
                            List.of("tax refund", "cashback", "rebate", "expense reimbursement"));
            Income_CATEGORY_RULES.put("Other", List.of()); // Default category
    }

    public String CategorizeTransaction(String transactionType,String description) {
        if (description == null || description.trim().isEmpty()) {
            return "Uncategorized"; // Handle empty descriptions
        }
        description = description.toLowerCase(); // Normalize input
        // Match description against category rules
        if (transactionType.equals("income")) {
                CATEGORY_RULES = Income_CATEGORY_RULES;
        }
        if (transactionType.equals("expenses")) {
                CATEGORY_RULES = Expenses_CATEGORY_RULES;
        }

        for (Map.Entry<String, List<String>> entry : CATEGORY_RULES.entrySet()) {
                for (String keyword : entry.getValue()) {
                        if (description.contains(keyword)) {
                                return entry.getKey(); // Return the first matching category
                        }
                }
        }
        return "Other"; // Default category if no match is found
    }
}
