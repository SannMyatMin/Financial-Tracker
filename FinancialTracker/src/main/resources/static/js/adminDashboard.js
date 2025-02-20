// Mock data for demonstration
const mockData = {
    stats: {
        users: 1234,
        revenue: 45678,
        tasks: 89,
        messages: 23
    },
    recentActivity: [
        {
            icon: 'fas fa-user-plus',
            title: 'New user registered',
            time: '5 minutes ago'
        },
        {
            icon: 'fas fa-shopping-cart',
            title: 'New order received',
            time: '1 hour ago'
        },
        {
            icon: 'fas fa-comment',
            title: 'New comment on post',
            time: '3 hours ago'
        },
        {
            icon: 'fas fa-file',
            title: 'Report generated',
            time: '5 hours ago'
        }
    ]
};

// Update stats with animation
function animateValue(element, start, end, duration) {
    let startTimestamp = null;
    const step = (timestamp) => {
        if (!startTimestamp) startTimestamp = timestamp;
        const progress = Math.min((timestamp - startTimestamp) / duration, 1);
        const value = Math.floor(progress * (end - start) + start);
        element.innerHTML = element.id === 'revenue' ? `$${value.toLocaleString()}` : value.toLocaleString();
        if (progress < 1) {
            window.requestAnimationFrame(step);
        }
    };
    window.requestAnimationFrame(step);
}

// Initialize stats
document.addEventListener('DOMContentLoaded', () => {
    // Animate stats
    animateValue(document.getElementById('totalUsers'), 0, mockData.stats.users, 2000);
    animateValue(document.getElementById('revenue'), 0, mockData.stats.revenue, 2000);
    animateValue(document.getElementById('tasks'), 0, mockData.stats.tasks, 2000);
    animateValue(document.getElementById('messages'), 0, mockData.stats.messages, 2000);

    // Populate recent activity
    const activityList = document.getElementById('activityList');
    mockData.recentActivity.forEach(activity => {
        const activityItem = document.createElement('div');
        activityItem.className = 'activity-item';
        activityItem.innerHTML = `
            <div class="activity-icon">
                <i class="${activity.icon}"></i>
            </div>
            <div class="activity-details">
                <h4>${activity.title}</h4>
                <p>${activity.time}</p>
            </div>
        `;
        activityList.appendChild(activityItem);
    });

    // Add click handlers for navigation
    document.querySelectorAll('.nav-links li').forEach(item => {
        item.addEventListener('click', function() {
            document.querySelectorAll('.nav-links li').forEach(li => li.classList.remove('active'));
            this.classList.add('active');
        });
    });

    // Add notification handlers
    document.querySelectorAll('.header-icons i').forEach(icon => {
        icon.addEventListener('click', function() {
            alert('Notification clicked!');
        });
    });
});

// Search functionality
document.querySelector('.search-bar input').addEventListener('input', function(e) {
    console.log('Searching for:', e.target.value);
    // Add your search logic here
});