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
  
  // Initialize date when the page loads
  document.addEventListener('DOMContentLoaded', updateCurrentDate);