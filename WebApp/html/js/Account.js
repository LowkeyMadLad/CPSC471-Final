const loginForm = document.getElementById("login-form");
const userProfile = document.getElementById("user-profile");
const loginBtn = document.getElementById("login-btn");
const logoutBtn = document.getElementById("logout-btn");

loginBtn.addEventListener("click", () => {
  // Replace this with your actual logic to verify the user's credentials
  // and fetch the user data from the server
  const username = document.getElementById("username").value;
  const password = document.getElementById("password").value;

  if (username && password) {
    // Display user data on the page
    document.getElementById("user-name").textContent = username;
    document.getElementById("user-mmr").textContent = "1200";
    document.getElementById("user-games-played").textContent = "50";
    document.getElementById("user-wins").textContent = "30";
    document.getElementById("user-losses").textContent = "20";

    // Hide login form and show user profile
    loginForm.classList.add("hidden");
    userProfile.classList.remove("hidden");
  }
});

logoutBtn.addEventListener("click", () => {
  // Clear the form inputs
  document.getElementById("username").value = "";
  document.getElementById("password").value = "";

  // Hide user profile and show login form
  userProfile.classList.add("hidden");
  loginForm.classList.remove("hidden");
});
