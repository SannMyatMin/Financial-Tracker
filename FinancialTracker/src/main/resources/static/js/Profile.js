function fillUserData() {
    const name = sessionStorage.getItem("user_name");
    const mail = sessionStorage.getItem("user_mail");
    const balance = sessionStorage.getItem("user_balance");
    const income = sessionStorage.getItem("user_income");
    const expenses = sessionStorage.getItem("user_expenses");

    const name_input = document.getElementById("name");
    const mail_input = document.getElementById("email");
    const balance_input = document.getElementById("balance");
    const income_input = document.getElementById("income");
    const expenses_input = document.getElementById("expenses")

    if(name){
        name_input.value = name; 
    }
    if(mail){
        mail_input.value = mail;
    }
    if (balance) {
        balance_input.value = balance;
    }
    if (income) {
        income_input.value = income;
    }
    if (expenses) {
        expenses_input.value = expenses;
    }
};
    
document.addEventListener("DOMContentLoaded", fillUserData);