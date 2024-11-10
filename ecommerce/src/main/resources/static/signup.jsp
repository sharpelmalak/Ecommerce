<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" href="css/signup.css">
	<title>Registration Form</title>




	

	<link rel="stylesheet" href="css/linearicons.css">
	<link rel="stylesheet" href="css/themify-icons.css">
	<link rel="stylesheet" href="css/bootstrap.css">
	<link rel="stylesheet" href="css/magnific-popup.css">
	<link rel="stylesheet" href="css/main.css">
	<!-- Include intl-tel-input CSS -->
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/intl-tel-input/17.0.19/css/intlTelInput.css">

	<!-- Include intl-tel-input JavaScript -->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/intl-tel-input/17.0.19/js/intlTelInput.min.js"></script>

	<style>
		.form-holder input {
			flex: 1;
			border: #b3b1b1 solid 1px;
			background-color: transparent;
			font-size: 16px;
			margin-left: 50px;
		}
		.interest-bubble {
			display: inline-block;
			margin: 5px;
			position: relative;
		}

		.interest-bubble input[type="checkbox"] {
			display: none;
		}

		.interest-bubble label {
			display: inline-flex;
			align-items: center;
			justify-content: center;
			padding: 10px 20px;
			background-color: #f8f9fa;
			border: 2px solid #dee2e6;
			border-radius: 25px;
			cursor: pointer;
			transition: all 0.3s ease;
		}

		.interest-bubble input[type="checkbox"]:checked + label {
			background-color: #28a745;
			border-color: #28a745;
			color: white;
		}

		.error-message {
			color: red;
			font-size: 0.8em;
			margin-top: 5px;
		}
	</style>
</head>
<body>
<%@ include file="/common/header.jsp" %>

<div class="wrapper" style="background-image: url('img/signup.png');">
	<div class="inner">
		<form id="registrationForm" action="register" method="post">
			<h3>Sign Up</h3>

			<!-- First and Last Name Fields -->
			<div class="form-group">
				<div class="form-wrapper">
					<label for="firstName">First Name</label>
					<div class="form-holder">
						<i class="fas fa-user"></i>
						<input type="text" id="firstName" name="firstName" class="form-control" required placeholder="First Name">
					</div>
				</div>
				<div class="form-wrapper">
					<label for="lastName">Last Name</label>
					<div class="form-holder">
						<i class="fas fa-user"></i>
						<input type="text" id="lastName" name="lastName" class="form-control" required placeholder="Last Name">
					</div>
				</div>
			</div>

			<!-- Username -->
			<div class="form-wrapper">
				<label for="username">Username</label>
				<div class="form-holder">
					<i class="fas fa-user"></i>
					<input type="text" id="username" name="username" class="form-control" required placeholder="Username" oninput="checkAvailability('username', this.value)">
				</div>
				<small id="usernameFeedback"></small> <!-- Feedback for username -->
				<div id="usernameError" class="error-message"></div>
			</div>

			<!-- Email -->
			<div class="form-wrapper">
				<label for="email">Email</label>
				<div class="form-holder">
					<i class="fas fa-envelope"></i>
					<input type="email" id="email" name="email" class="form-control" required placeholder="Email Address" oninput="checkAvailability('email', this.value)">
				</div>
				<small id="emailFeedback"></small> <!-- Feedback for email -->
				<div id="emailError" class="error-message"></div>
			</div>

			<!-- Password -->
			<div class="form-wrapper">
				<label for="password">Password</label>
				<div class="form-holder">
					<i class="fas fa-lock"></i>
					<input type="password" id="password" name="password" class="form-control" required placeholder="Password" onblur="validatePassword()">
				</div>
				<div id="passwordError" class="error-message"></div>
			</div>

			<!-- Birthdate -->
			<div class="form-wrapper">
				<label for="birthdate">Date of Birth</label>
				<div class="form-holder">
					<i class="fas fa-calendar-alt"></i>
					<input type="date" id="birthdate" name="birthdate" class="form-control" required onblur="validateBirthdate()">
				</div>
				<div id="birthdateError" class="error-message"></div>
			</div>

			<!-- Credit Limit -->
			<div class="form-wrapper">
				<label for="creditLimit">Credit Limit</label>
				<div class="form-holder">
					<i class="fas fa-dollar-sign"></i>
					<input type="number" id="creditLimit" name="creditLimit" class="form-control" required placeholder="Credit Limit" onblur="validateCreditLimit()">
				</div>
				<div id="creditLimitError" class="error-message"></div>
			</div>

			<!-- Address Section (Country, City, Street) -->
			<div class="form-group">
				<div class="form-wrapper">
					<label for="country">Country</label>
					<div class="form-holder">
						<i class="fas fa-flag"></i>
						<input type="text" id="country" name="country" class="form-control" required placeholder="Country" onblur="validateCountry()">
					</div>
					<div id="countryError" class="error-message"></div>
				</div>
				<div class="form-wrapper">
					<label for="city">City</label>
					<div class="form-holder">
						<i class="fas fa-city"></i>
						<input type="text" id="city" name="city" class="form-control" required placeholder="City" onblur="validateCity()">
					</div>
					<div id="cityError" class="error-message"></div>
				</div>
			</div>

			<div class="form-wrapper">
				<label for="street">Street</label>
				<div class="form-holder">
					<i class="fas fa-road"></i>
					<input type="text" id="street" name="street" class="form-control" required placeholder="Street Address" onblur="validateStreet()">
				</div>
				<div id="streetError" class="error-message"></div>
			</div>

			<!-- Phone with Country -->
			<div class="form-wrapper">
				<label for="phone">Phone Number</label>
				<div class="form-holder">
					<input type="tel" id="phone" name="phone" class="form-control" required>
				</div>
				<div id="phoneError" class="error-message"></div>
			</div>


			<!-- Interests (Categories) -->
			<div class="form-group">
				<label><b>Select Your Interests</b></label>
				<div class="d-flex flex-wrap">
					<c:forEach items="${categories}" var="category">
						<div class="interest-bubble">
							<input type="checkbox"
								   id="category${category.getId()}"
								   name="categories"
								   value="${category.getId()}">
							<label for="category${category.getId()}">${category.getName()}</label>
						</div>
					</c:forEach>
				</div>
				<small id="categoriesHelp" class="form-text text-muted">
					Select your interests (categories).
				</small>
				<div id="categoriesError" class="error-message"></div>
			</div>

			<div class="form-end">
				<button type="submit">Register</button>
			</div>
		</form>
	</div>
</div>
<%@ include file="/common/footer.jsp" %>

<!-- JavaScript for AJAX-based validation and form validation -->
<script>
	function checkAvailability(field, value) {
		const feedbackElement = document.getElementById(field + 'Feedback');
		const xhr = new XMLHttpRequest();
		xhr.open("POST", "CheckUsernameEmailAjax", true);
		xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

		xhr.onload = function() {
			if (xhr.status === 200) {
				const responseText = xhr.responseText.trim();
				if (responseText.includes("available")) {
					feedbackElement.innerText = field.charAt(0).toUpperCase() + field.slice(1) + " is available";
					feedbackElement.style.color = "green";
				} else {
					feedbackElement.innerText = responseText;
					feedbackElement.style.color = "red";
				}
			}
		};

		xhr.send("field=" + field + "&value=" + value);
	}

	function validatePassword() {
		const password = document.getElementById('password').value;
		const passwordError = document.getElementById('passwordError');
		if (password.length < 6) {
			passwordError.innerText = 'Password must be at least 6 characters long';
			return false;
		}
		passwordError.innerText = '';
		return true;
	}

	function validateBirthdate() {
		const birthdate = document.getElementById('birthdate').value;
		const birthdateError = document.getElementById('birthdateError');
		if (new Date(birthdate) > new Date()) {
			birthdateError.innerText = 'Birthdate cannot be in the future';
			return false;
		}
		birthdateError.innerText = '';
		return true;
	}

	function validateCreditLimit() {
		const creditLimit = document.getElementById('creditLimit').value;
		const creditLimitError = document.getElementById('creditLimitError');
		if (creditLimit <= 0) {
			creditLimitError.innerText = 'Credit limit must be a positive number';
			return false;
		}
		creditLimitError.innerText = '';
		return true;
	}

	function validateCountry() {
		const country = document.getElementById('country').value;
		const countryError = document.getElementById('countryError');
		if (country.trim() === '') {
			countryError.innerText = 'Country is required';
			return false;
		}
		countryError.innerText = '';
		return true;
	}

	function validateCity() {
		const city = document.getElementById('city').value;
		const cityError = document.getElementById('cityError');
		if (city.trim() === '') {
			cityError.innerText = 'City is required';
			return false;
		}
		cityError.innerText = '';
		return true;
	}

	function validateStreet() {
		const street = document.getElementById('street').value;
		const streetError = document.getElementById('streetError');
		if (street.trim() === '') {
			streetError.innerText = 'Street address is required';
			return false;
		}
		streetError.innerText = '';
		return true;
	}

	function validatePhone() {
		const phone = document.getElementById('phone').value;
		if (phoneInput.length < 8) {
			phoneError.innerText = 'Phone number must be 10 digits';
			return false;
		}
		phoneError.innerText = '';
		return true;
	}

	function validateForm() {
		const isUsernameValid = document.getElementById('usernameFeedback').innerText.trim() === "";
		const isEmailValid = document.getElementById('emailFeedback').innerText.trim() === "";

		const isPasswordValid = validatePassword();
		const isBirthdateValid = validateBirthdate();
		const isCreditLimitValid = validateCreditLimit();
		const isCountryValid = validateCountry();
		const isCityValid = validateCity();
		const isStreetValid = validateStreet();
		const isPhoneValid = validatePhone();

		const isAnyFieldInvalid = !(
				isUsernameValid &&
				isEmailValid &&
				isPasswordValid &&
				isBirthdateValid &&
				isCreditLimitValid &&
				isCountryValid &&
				isCityValid &&
				isStreetValid &&
				isPhoneValid
		);

		if (isAnyFieldInvalid) {
			document.getElementById('registrationForm').addEventListener('submit', function(event) {
				event.preventDefault();
				document.getElementById('categoriesError').innerText = 'Please fix the errors before submitting.';
			});
		} else {
			document.getElementById('categoriesError').innerText = '';
			document.getElementById('registrationForm').submit(); // Submit form if all validations pass
		}
	}

	document.getElementById('registrationForm').addEventListener('submit', function(event) {
		event.preventDefault();
		validateForm();
	});

	// Initialize the intl-tel-input for phone field
	const phoneInputField = document.querySelector("#phone");
	const phoneInput = window.intlTelInput(phoneInputField, {
		utilsScript: "https://cdnjs.cloudflare.com/ajax/libs/intl-tel-input/17.0.19/js/utils.js", // for formatting/validation etc.
		initialCountry: "auto",
		geoIpLookup: function (callback) {
			fetch('https://ipinfo.io/json?token=c341c7c125c720') // Replace with your own IPinfo token
					.then((response) => response.json())
					.then((data) => {
						callback(data.country);
					})
					.catch(() => {
						callback('us');
					});
		}
	});

	function populateCountriesDropdown() {
		const countrySelect = document.getElementById('country');
		fetch('https://restcountries.com/v3.1/all')
				.then((response) => response.json())
				.then((data) => {
					data.sort((a, b) => a.name.common.localeCompare(b.name.common)); // Sort countries alphabetically
					data.forEach(country => {
						const option = document.createElement('option');
						option.value = country.name.common;
						option.textContent = country.name.common;
						countrySelect.appendChild(option);
					});
				})
				.catch(error => console.error('Error fetching countries:', error));
	}

	// Call the function to populate the countries dropdown
	populateCountriesDropdown();

</script>


</body>
</html>


