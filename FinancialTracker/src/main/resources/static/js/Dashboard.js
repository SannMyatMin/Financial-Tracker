function setActiveTab(tab) {
    const navItems = document.querySelectorAll(".nav-item");
    navItems.forEach((item) => item.classList.remove("active"));
  
    const activeItem = [...navItems].find((item) =>
      item.textContent.toLowerCase().includes(tab)
    );
    if (activeItem) activeItem.classList.add("active");
  }
  
  function updateCurrentDate() {
    const dateElement = document.getElementById('current-date');
    if (dateElement) {
      const today = new Date();
      const day = today.getDate();
      const month = today.toLocaleString('default', { month: 'long' });
      const year = today.getFullYear();
      dateElement.textContent = `${day} / ${month} / ${year}`;
    }
  }
  document.addEventListener('DOMContentLoaded', updateCurrentDate);
  
  
async function getUserData() {
  const name = sessionStorage.getItem("user_name");
  if (name) {
    try {
      const user_name = { name };
      const response = await fetch("/api/user/Data", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(user_name)
      })

      if (!response.ok) throw new Error(`HTTP error! Status: ${response.status}`);

      const data = await response.json();

      document.getElementById("username").textContent = data.name;
      document.getElementById("Username").textContent = data.name;
      document.getElementById("usermail").textContent = data.mail;
      document.getElementById("userphoto").src = `data:image/jpeg;base64,${data.photo}`;
      document.getElementById("Userphoto").src = `data:image/jpeg;base64,${data.photo}`;
      document.getElementById("balance").textContent = "$ " + data.balance;
      document.getElementById("income").textContent = "$ " + data.income;
      document.getElementById("expenses").textContent = "$ " + data.expenses;
      const netBalance = document.getElementById("net_balance");
      const netIncome = document.getElementById("net_income");
      const netExpenses = document.getElementById("net_expenses");

      if (data.netBalance >= 0) {
        netBalance.textContent = "+" + data.netBalance;
        netBalance.classList.remove("red");
        netBalance.classList.add("green");
      } else {
        netBalance.textContent = data.netBalance;
        netBalance.classList.remove("green");
        netBalance.classList.add("red");
      }
      
      if (data.netIncome >= 0) {
        netIncome.textContent = "+" + data.netIncome;
        netIncome.classList.remove("red");
        netIncome.classList.add("green");
      } else {
        netIncome.textContent = data.netIncome;
        netIncome.classList.remove("green");
        netIncome.classList.add("red");
      }

      if (data.netExpenses >= 0) {
        netExpenses.textContent = "+" + data.netExpenses;
        netExpenses.classList.remove("green");
        netExpenses.classList.add("red");
      } else {
        netExpenses.textContent = data.netExpenses;
        netExpenses.classList.remove("red");
        netExpenses.classList.add("green");
      }

      sessionStorage.setItem("user_balance", data.balance);
      sessionStorage.setItem("user_income", data.income);
      sessionStorage.setItem("user_expenses", data.expenses);

    }
    catch (error) {
      console.log(error);
    }
  }

  if (sessionStorage.getItem("TransactionStatus")) {
    const name = sessionStorage.getItem("user_name");
    try {
      const respond = await fetch("/api/user/getTransactions", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ name })
      })

      if (!respond.ok) {
        console.log("fail");  
      }
      else {
        const transactions = await respond.json();
        const transaction_container = document.querySelector(".transactions");

        transaction_container.innerHTML = `<h2>Recent Transactions</h2>`;

        income_icon  = ["svg/money-svgrepo-com.svg","svg/money_plant.svg","svg/money_in.svg"]
        expense_icon = ["svg/money_out.svg", "svg/exp_wal.svg", "svg/rent-icon.svg"]

        let income_index  = 0;
        let expense_index = 0;
        let amountClass, svg;  
      
        const loop_count = Math.min(3, Math.max(1,transactions.length))
        transactions.slice(0, loop_count).forEach(T => {
          const transactionDiv = document.createElement("div");
          transactionDiv.classList.add("transaction");
          let amount = T.amount;
          if (T.type === "income") {
            amountClass = "green";
            amount = "+" + amount;
            svg = income_icon[income_index % income_icon.length];
            income_index++;
          }
          if (T.type === "expenses") {
            amountClass = "red";
            amount = "-" + amount;
            svg = expense_icon[expense_index % expense_icon.length];
            expense_index++;
          }
          transactionDiv.innerHTML = `
                  <div class="transaction-info">
                      <img class="icon" src=${svg} alt="Icon" style="height: 30px; width: 30px;"/>
                      <div>
                          <p>${T.description}</p>
                          <small>${T.date}</small>
                      </div>
                  </div>
                  <span class="amount ${amountClass}"> ${amount} $</span>`;
          transaction_container.appendChild(transactionDiv);
        })
      }  
    }
    catch (error) {
      console.log(error);
    }
  }
}
document.addEventListener('DOMContentLoaded', getUserData);

function logout() {
  sessionStorage.clear();
  fetch('/logout', { method: "GET" })
    .then(response => { window.location.href = "/home" })
    .catch(error => console.log(error));
}

  
