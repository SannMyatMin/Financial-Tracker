document.addEventListener('DOMContentLoaded', () => {
    const modal = document.getElementById('transactionModal');
    const cancelBtn = document.getElementById('cancelTransaction');
    const transactionForm = document.getElementById('transactionForm');
    const typeButtons = document.querySelectorAll('.type-selector button');
    const amountInput = document.getElementById('amount');
    const descriptionInput = document.getElementById('description');
    const transactionTypeInput = document.getElementById("transactionType"); // Hidden input

    let selectedType = 'expenses';

    // Open modal automatically
    modal.classList.add('active');
    transactionForm.reset();
    setTransactionType('expenses');
    amountInput.focus();

    // Hide modal
    function closeModal() {
        modal.classList.remove('active');
        transactionForm.reset();
        clearValidationErrors();
    }

    cancelBtn.addEventListener('click', closeModal);

    // Close modal when clicking outside
    modal.addEventListener('click', (e) => {
        if (e.target === modal) {
            closeModal();
        }
    });

    // Handle transaction type selection
    function setTransactionType(type) {
        transactionTypeInput.value = type;
        typeButtons.forEach(btn => {
            btn.classList.remove('active');
            if (btn.dataset.type === type) {
                btn.classList.add('active');
            }
        });
    }

    typeButtons.forEach(button => {
        button.addEventListener('click', () => {
            setTransactionType(button.dataset.type);
        });
    });

    function clearValidationErrors() {
        const errorElements = document.querySelectorAll('.error-message');
        errorElements.forEach(el => el.remove());
        const invalidInputs = document.querySelectorAll('.invalid');
        invalidInputs.forEach(input => input.classList.remove('invalid'));
    }

    function showError(element, message) {
        clearError(element);
        element.classList.add('invalid');
        const errorDiv = document.createElement('div');
        errorDiv.className = 'error-message';
        errorDiv.textContent = message;
        element.parentNode.appendChild(errorDiv);
    }

    function clearError(element) {
        const errorMessage = element.parentNode.querySelector('.error-message');
        if (errorMessage) {
            errorMessage.remove();
        }
        element.classList.remove('invalid');
    }

    function validateForm() {
        let isValid = true;
        clearValidationErrors();

        if (!amountInput.value || parseFloat(amountInput.value) <= 0) {
            showError(amountInput, 'Please enter a valid amount greater than 0');
            isValid = false;
        }

        if (!descriptionInput.value.trim()) {
            showError(descriptionInput, 'Please enter a description');
            isValid = false;
        }

        return isValid;
    }

});


document.addEventListener('DOMContentLoaded', async function () {
    const name = sessionStorage.getItem("user_name");
    if (name) {
        try {
            const user = { name };
            const response = await fetch("/api/user/Data", {
                method : "POST",
                headers : { "Content-Type": "application/json" },
                body : JSON.stringify(user)
            })
            if (!response.ok) {
                console.log(response);
            }
            else {
                const data = await response.json();
                document.getElementById("username").textContent = data.name;
                document.getElementById("userphoto").src = `data:image/jpeg;base64,${data.photo}`;
            }
        }
        catch (error) {
            console.log(error);
        }
    }
})

document.addEventListener("DOMContentLoaded", function () {
    const typeButtons     = document.querySelectorAll(".type-selector button");
    const transactionType = document.getElementById("transactionType");
    typeButtons.forEach(button => {
        button.addEventListener("click", function () {
            typeButtons.forEach(btn => {
                btn.classList.remove("active");
            })
            this.classList.add("active");
            transactionType.value = this.getAttribute("data-type");
        })
    })
})

document.getElementById("transactionForm").addEventListener("submit", async function (event) {
    event.preventDefault();
    const form            = event.target;
    const name            = sessionStorage.getItem("user_name");  
    const transactionType = document.getElementById("transactionType").value;
    const amount          = document.getElementById("amount").value;
    const description     = document.getElementById("description").value;
    const data            = { name, transactionType, amount, description }
    const transactionData = JSON.stringify(data);
    try {
        const respond = await fetch("/api/user/transaction", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: transactionData
        });
        const message = await respond.text();
        if (respond.ok) {
            alert(message);
            form.reset();
        } 
        else {
            alert(message);
        }
        if (sessionStorage.getItem("user_name")) {
            sessionStorage.setItem("TransactionStatus", "exist");
        }
    }
    catch (error) {
        console.log(error);
    }

})