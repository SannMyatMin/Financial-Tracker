document.addEventListener('DOMContentLoaded', async function () {
    const name = sessionStorage.getItem("user_name");
    if (name) {
        try {
            // Fetch user data
            const user = { name };
            const response = await fetch("/api/user/Data", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(user)
            });
            if (!response.ok) {
                console.log(response);
            } else {
                const data = await response.json();
                document.getElementById("username").textContent = data.name;
                document.getElementById("userphoto").src = `data:image/jpeg;base64,${data.photo}`;
            }
            
        } catch (error) {
            console.log(error);
        }
    }

});


document.addEventListener('DOMContentLoaded', () => {
    // Sample data for reports
    const transactions = [
        { date: '2024-01-15', description: 'salary deposit', category: 'Income', amount: 5000 },
        { date: '2024-01-20', description: 'grocery shopping', category: 'Food & Groceries', amount: -200 },
        { date: '2024-02-10', description: 'transportation', category: 'Transportation', amount: -100 },
        { date: '2024-03-05', description: 'entertainment', category: 'Entertainment', amount: -150 },
        { date: '2024-04-12', description: 'utilities', category: 'Utilities', amount: -300 },
        { date: '2024-05-04', description: 'dentist', category: 'Health & Fitness', amount: -300 },
        { date: '2024-05-21', description: 'college tuition', category: 'Education', amount: -200 },
        { date: '2024-05-30', description: 'gas refill', category: 'Transportation', amount: -100 },
        { date: '2024-06-28', description: 'sports', category: 'Entertainment', amount: -180 },
        { date: '2024-07-01', description: 'electricity bill', category: 'Utilities', amount: -150 },
        { date: '2024-07-15', description: 'salary Deposit', category: 'Income', amount: 5500 },
        { date: '2024-08-20', description: 'grocery Shopping', category: 'Food & Groceries', amount: -250 },
        { date: '2024-09-10', description: 'transportation', category: 'Transportation', amount: -120 },
        { date: '2024-10-05', description: 'entertainment', category: 'Entertainment', amount: -180 },
        { date: '2024-11-12', description: 'utilities', category: 'Utilities', amount: -350 },
    ];

    // Initialize charts
    const incomeExpenseCtx = document.getElementById('incomeExpenseChartJanToDec').getContext('2d');
    const categoryCtx = document.getElementById('categoryChart').getContext('2d');

    let incomeExpenseChart, categoryChart;

    // Function to filter transactions by date range
    function filterTransactionsByDateRange(transactions, startDate, endDate) {
        return transactions.filter(transaction => {
            const transactionDate = new Date(transaction.date);
            return (!startDate || transactionDate >= new Date(startDate)) &&
                   (!endDate || transactionDate <= new Date(endDate));
        });
    }

    // Function to filter transactions by month
    function filterTransactionsByMonth(transactions, month) {
        return transactions.filter(transaction => {
            const transactionDate = new Date(transaction.date);
            return transactionDate.getMonth() === month;
        });
    }

    // Function to calculate income and expenses for each month
    function calculateMonthlyIncomeExpenses(transactions) {
        const monthlyData = Array(12).fill().map(() => ({ income: 0, expenses: 0 }));

        transactions.forEach(transaction => {
            const month = new Date(transaction.date).getMonth();
            if (transaction.amount > 0) {
                monthlyData[month].income += transaction.amount;
            } else {
                monthlyData[month].expenses += Math.abs(transaction.amount);
            }
        });

        return monthlyData;
    }

    // Function to update charts
    function updateReports() {
        // Filter transactions for Jan to Dec
        const transactionsJanToDec = filterTransactionsByDateRange(transactions, '2024-01-01', '2024-12-31');
        const monthlyData = calculateMonthlyIncomeExpenses(transactionsJanToDec);

        // Update Income vs Expenses Chart (Jan to Dec)
        incomeExpenseChart.data.labels = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
        incomeExpenseChart.data.datasets[0].data = monthlyData.map(data => data.income);
        incomeExpenseChart.data.datasets[1].data = monthlyData.map(data => data.expenses);
        incomeExpenseChart.update();

        // Filter transactions for the current month
        const currentMonth = new Date().getMonth();
        const transactionsThisMonth = filterTransactionsByMonth(transactions, currentMonth);
        const categorySpending = transactionsThisMonth.reduce((acc, t) => {
            if (t.amount < 0) {
                acc[t.category] = (acc[t.category] || 0) + Math.abs(t.amount);
            }
            return acc;
        }, {});

        // Update Spending by Category Chart
        categoryChart.data.labels = Object.keys(categorySpending);
        categoryChart.data.datasets[0].data = Object.values(categorySpending);
        categoryChart.update();
    }

    // Initialize Income vs Expenses Chart (Jan to Dec)
    incomeExpenseChart = new Chart(incomeExpenseCtx, {
        type: 'bar',
        data: {
            labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
            datasets: [
                {
                    label: 'Income',
                    data: Array(12).fill(0), // Initial data
                    backgroundColor: 'rgba(75, 192, 192, 0.2)', // Income color
                    borderColor: 'rgba(75, 192, 192, 1)',
                    borderWidth: 1,
                },
                {
                    label: 'Expenses',
                    data: Array(12).fill(0), // Initial data
                    backgroundColor: 'rgba(255, 99, 132, 0.2)', // Expense color
                    borderColor: 'rgba(255, 99, 132, 1)',
                    borderWidth: 1,
                },
            ],
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true,
                },
            },
        },
    });
 
    // Initialize Spending by Category Chart
    categoryChart = new Chart(categoryCtx, {
        type: 'pie',
        data: {
            labels: [], // Initial labels
            datasets: [{
                label: 'Spending',
                data: [], // Initial data
                backgroundColor: [
                    '#FF6384', '#36A2EB', '#FFCE56', '#4BC0C0', '#9966FF', '#FF9F40',
                ],
            }],
        },
    });

    // Initialize reports with all transactions
    updateReports();
});