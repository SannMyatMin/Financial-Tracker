document.addEventListener('DOMContentLoaded', function() {
    const profileTrigger = document.querySelector('.profile-trigger');
    const profileBox = document.getElementById('profileBox');
    const editBtn = document.querySelector('.edit-btn');
    const logoutBtn = document.querySelector('.logout-btn');

    // Toggle profile box
    profileTrigger.addEventListener('click', function(e) {
        e.stopPropagation();
        profileBox.classList.toggle('active');
        
        // Position the profile box below the trigger
        const triggerRect = profileTrigger.getBoundingClientRect();
        profileBox.style.top = `${triggerRect.bottom + 10}px`;
        profileBox.style.left = `${triggerRect.left}px`;
    });

    // Close profile box when clicking outside
    document.addEventListener('click', function(e) {
        if (!profileBox.contains(e.target) && !profileTrigger.contains(e.target)) {
            profileBox.classList.remove('active');
        }
    });

    // Edit button click handler
    editBtn.addEventListener('click', function() {
        // Add your edit functionality here
        console.log('Edit profile clicked');
    });

    // Logout button click handler
    logoutBtn.addEventListener('click', function() {
        // Add your logout functionality here
        console.log('Logout clicked');
    });
});