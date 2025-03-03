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

                transactions.forEach(transaction => {
                    const row = document.createElement("tr");
                    let rowClass = "";
                    if (transaction.type === "income") {
                        rowClass = "status-paid";
                    } else if (transaction.type === "expenses") {
                        rowClass = "status-pending";
                    }

                    row.innerHTML = `
                        <td>${transaction.category}</td>
                        <td>${transaction.description}</td> 
                        <td>$ ${transaction.amount}</td>
                        <td class="${rowClass}">${transaction.type}</td>
                        <td>${transaction.date}</td>
                    `;
                    tableBody.appendChild(row);
                });
            }
        } catch (error) {
            console.log(error);
        }
    }
});