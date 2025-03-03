package BusinessPJ.FinancialTracker.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class TransactionCategorizer 
{
    // Define category rules (keywords for each category)
    private static final Map<String, List<String>> CATEGORY_RULES = new HashMap<>();

    static {
        CATEGORY_RULES.put("Food & Groceries", List.of("grocery", "food", "supermarket", "restaurant", "cafe", "snacks",
                 "vegetables", "fruits", "meat", "dairy", "bakery", "beverages"));
        CATEGORY_RULES.put("Housing", List.of("rent", "housing", "mortgage", "apartment", "property tax",
                "home insurance", "maintenance", "furniture", "repair", "home improvement"));
        CATEGORY_RULES.put("Transportation", List.of("gas", "fuel", "uber", "taxi", "bus", "train", "subway", "parking",
                "toll", "car rental", "car repair", "bike", "public transport"));
        CATEGORY_RULES.put("Entertainment", List.of("event", "cinema", "netflix", "spotify", "concert", "games",
                "books", "theater", "sports", "nightclub", "hobbies", "leisure"));
        CATEGORY_RULES.put("Utilities", List.of("electricity", "water", "internet", "phone bill", "gas bill", "sewage",
                "garbage", "cable TV", "heating", "cooling", "solar panel", "waste disposal"));
        CATEGORY_RULES.put("Health & Fitness", List.of("gym", "yoga", "doctor", "hospital", "medication", "pharmacy",
                "therapy", "dentist", "insurance", "optometrist", "wellness"));
        CATEGORY_RULES.put("Shopping", List.of("clothing", "electronics", "fashion", "shoes", "accessories", "beauty",
                "home decor", "gadget", "furniture", "appliances"));
        CATEGORY_RULES.put("Education", List.of("school", "college", "university", "books", "courses",
                "online learning", "tuition", "exam fees", "stationery", "backpacks", "library"));
        CATEGORY_RULES.put("Travel", List.of("hotel", "flight", "airfare", "vacation", "tour", "cruise", "resort",
                "visa", "luggage", "transportation"));
        CATEGORY_RULES.put("Business & Work", List.of("office", "software", "subscription", "salary", "investment",
                "startup", "freelance", "consulting", "business trip"));
        CATEGORY_RULES.put("Other", List.of()); // Default category
    }

    public String categorizeTransaction(String description) {
        if (description == null || description.trim().isEmpty()) {
            return "Uncategorized"; // Handle empty descriptions
        }

        description = description.toLowerCase(); // Normalize input

        // Match description against category rules
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
