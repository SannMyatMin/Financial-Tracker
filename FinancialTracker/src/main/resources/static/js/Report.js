document.addEventListener('DOMContentLoaded', async function () {
    // Fetch user data
    const name = sessionStorage.getItem("user_name");
    if (name) {
        try {
            const user = { name };
            const data = JSON.stringify(user);
            const response = await fetch("/api/user/Data", {
                method : "POST",
                headers : { "Content-Type": "application/json" },
                body : data
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

    

});