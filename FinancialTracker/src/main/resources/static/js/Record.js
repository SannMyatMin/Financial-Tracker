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

            // Fetch and display transactions
            const transactionsResponse = await fetch("/api/user/getTransactions", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ name })
            });

            if (!transactionsResponse.ok) {
                console.log("Failed to fetch transactions");
            } else {
                const transactions = await transactionsResponse.json();
                const tableBody = document.querySelector(".Tbody");
                tableBody.innerHTML = ""; // Clear existing rows
                    let i = 1;
                    transactions.forEach(transaction => {
                        const row = document.createElement("tr");
                        let rowClass = "";
                        let type = "";
                        if (transaction.type === "income") {
                            rowClass = "status-paid";
                            type = transaction.type.charAt(0).toUpperCase() + transaction.type.slice(1);
                        } else if (transaction.type === "expenses") {
                            rowClass = "status-pending";
                            type = transaction.type.charAt(0).toUpperCase() + transaction.type.slice(1);
                        }

                        row.innerHTML = `
                        <td>${i}</td>
                        <td>${transaction.category}</td>
                        <td>${transaction.description}</td> 
                        <td>
                        <div style="text-align: left;">$ ${transaction.amount}</td></div>
                        <td class="${rowClass}">${type}</td>
                        <td>${transaction.date}</td>
                    `;
                        i++;
                        tableBody.appendChild(row);
                    });
            }
        } catch (error) {
            console.log(error);
        }
    }
    if (!sessionStorage.getItem("TransactionStatus")) {
                const tableBody = document.querySelector(".Tbody");
                const row = document.createElement("tr")
                row.innerHTML = `
                <td> empty </td>
                <td>
                <div style="padding-left: 10px;"> empty </td></div>
                <td>
                <div style="padding-left: 20px;"> empty </td></div>
                <td>
                <div style="text-align: left;"> empty </td></div>
                <td> empty </td>
                <td> empty </td> `;
                tableBody.appendChild(row);
            }
});