document.getElementById('loginForm').addEventListener('submit', function(event) {
    event.preventDefault();

    var email = document.getElementById('email').value;
    var password = document.getElementById('password').value;

    fetch('http://localhost:8080/api/users/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({email: email, password: password})
    })
    .then(response => response.json())
    .then(data => {
        if (data.error) {
            alert(data.error);
        } else {
            console.log('Login Completed');
            // Save user id to local storage
            localStorage.setItem('userId', data.userId);

            console.log(JSON.stringify({email: email, password: password}));
            alert(data.message);
            window.location.href = '/diary';
        }
    })
    .catch((error) => {
        console.error('Error:', error);
    });
});

function loginPopup() {
    var popup = document.getElementById("login__bg");
    popup.classList.add("show");
}

function loginPopdown() {
    var popup = document.getElementById("login__bg");
    popup.classList.remove("show");
}