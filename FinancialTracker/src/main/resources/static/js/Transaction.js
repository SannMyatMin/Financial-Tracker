document.addEventListener('DOMContentLoaded', () => {
    const modal = document.getElementById('transactionModal');
    const cancelBtn = document.getElementById('cancelTransaction');
    const transactionForm = document.getElementById('transactionForm');
    const typeButtons = document.querySelectorAll('.type-selector button');
    const amountInput = document.getElementById('amount');
    const categorySelect = document.getElementById('category');
    const customCategoryInput = document.getElementById('customCategory');
    const descriptionInput = document.getElementById('description');

    let selectedType = 'expense';

    // Open modal automatically
    modal.classList.add('active');
    transactionForm.reset();
    setTransactionType('expense');
    amountInput.focus();

    // Hide modal
    function closeModal() {
        modal.classList.remove('active');
        transactionForm.reset();
        clearValidationErrors();
        customCategoryInput.classList.add('hidden');
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
        selectedType = type;
        typeButtons.forEach(btn => {
            btn.classList.remove('active', 'active2');
            if (btn.dataset.type === type) {
                btn.classList.add(type === 'expense' ? 'active' : 'active2');
            }
        });
        updateCategoryOptions(type);
    }

    typeButtons.forEach(button => {
        button.addEventListener('click', () => {
            setTransactionType(button.dataset.type);
        });
    });

    function updateCategoryOptions(type) {
        const incomeCategories = [
            { value: 'salary', label: 'Salary' },
            { value: 'freelance', label: 'Freelance' },
            { value: 'investments', label: 'Investments' },
            { value: 'other_income', label: 'Other' }
        ];

        const expenseCategories = [
            { value: 'groceries', label: 'Groceries' },
            { value: 'rent', label: 'Rent' },
            { value: 'utilities', label: 'Utilities' },
            { value: 'entertainment', label: 'Entertainment' },
            { value: 'transport', label: 'Transport' },
            { value: 'healthcare', label: 'Healthcare' },
            { value: 'shopping', label: 'Shopping' },
            { value: 'other_expense', label: 'Other' }
        ];

        const categories = type === 'income' ? incomeCategories : expenseCategories;
        
        categorySelect.innerHTML = `
            <option value="">Select category</option>
            ${categories.map(cat => `
                <option value="${cat.value}">${cat.label}</option>
            `).join('')}
        `;

        customCategoryInput.classList.add('hidden');
        customCategoryInput.value = '';
    }

    // Handle category selection change
    categorySelect.addEventListener('change', () => {
        const isOther = categorySelect.value === 'other_income' || categorySelect.value === 'other_expense';
        customCategoryInput.classList.toggle('hidden', !isOther);
        if (isOther) {
            customCategoryInput.focus();
        }
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

        if (!categorySelect.value) {
            showError(categorySelect, 'Please select a category');
            isValid = false;
        }

        const isOther = categorySelect.value === 'other_income' || categorySelect.value === 'other_expense';
        if (isOther && !customCategoryInput.value.trim()) {
            showError(customCategoryInput, 'Please enter a custom category');
            isValid = false;
        }

        if (!descriptionInput.value.trim()) {
            showError(descriptionInput, 'Please enter a description');
            isValid = false;
        }

        return isValid;
    }

    // Handle form submission
    transactionForm.addEventListener('submit', (e) => {
        e.preventDefault();

        if (!validateForm()) {
            return;
        }

        const isOther = categorySelect.value === 'other_income' || categorySelect.value === 'other_expense';
        const finalCategory = isOther ? customCategoryInput.value.trim() : categorySelect.value;

        const transaction = {
            id: Date.now(),
            type: selectedType,
            amount: parseFloat(amountInput.value),
            category: finalCategory,
            description: descriptionInput.value.trim(),
            date: new Date().toISOString()
        };

        showNotification('Transaction added successfully!');
        closeModal();
        console.log('New transaction:', transaction);
    });

    function showNotification(message) {
        const notification = document.createElement('div');
        notification.className = 'notification';
        notification.textContent = message;
        document.body.appendChild(notification);

        setTimeout(() => {
            notification.remove();
        }, 3000);
    }

    // Initialize category options
    updateCategoryOptions('expense');
});