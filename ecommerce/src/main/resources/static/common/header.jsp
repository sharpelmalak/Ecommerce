<%@ page import="gov.iti.jets.models.User" %>

<header class="header_area sticky-header">

	<div class="main_menu">
		<nav class="navbar navbar-expand-lg navbar-light main_box">
			<div class="container">
				<!-- Brand and toggle get grouped for better mobile display -->
				<a class="navbar-brand logo_h" href="/ECommerceWebApp/">
					<img src="<%= request.getContextPath() %>/img/logo1.png" alt="Logo" style="width: 180px; height: 80px;">
				</a>
				<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
						aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<!-- Collect the nav links, forms, and other content for toggling -->
				<div class="collapse navbar-collapse offset" id="navbarSupportedContent">
					<ul class="nav navbar-nav menu_nav ml-auto">

						<li class="nav-item"><a class="nav-link" href="/ECommerceWebApp/">Home</a></li>

						<li class="nav-item"><a class="nav-link" href="products">Shop</a></li>

						<li class="nav-item"><a class="nav-link" href="contact.jsp">Contact</a></li>
					</ul>

					<ul class="nav navbar-nav navbar-right">
						<li class="nav-item">
							<button class="search"><span class="lnr lnr-magnifier" id="search"></span></button>
						</li>
						<li class="nav-item dropdown">
							<a href="cart?action=listByUser" class="cart">
								<span class="ti-shopping-cart"></span>
							</a>
							<ul class="dropdown-menu">
								<li class="nav-item"><a class="nav-link" href="cart?action=listByUser">Cart</a></li>
								<li class="nav-item"><a class="nav-link" href="checkout">Checkout</a></li>
							</ul>
						</li>

						<li class="nav-item dropdown">
							<a href="userAcc" class="profile"><span class="lnr lnr-user"></span></a>
							<ul class="dropdown-menu">
								<%
									try {
										// Check if user is logged in
										Object userIdObj = session.getAttribute("userId");

										// Check if userId is present and of the correct type
										if (userIdObj != null && userIdObj instanceof String) {
											// Convert userId to Long
											Long userId = Long.parseLong((String) userIdObj);

											// User is logged in
								%>
								<li class="nav-item"><a class="nav-link" href="userAcc">My Account</a></li>
								<li class="nav-item"><a class="nav-link" href="logout">Logout</a></li>
								<%
								} else {
									// User not logged in
								%>
								<li class="nav-item"><a class="nav-link" href="/ECommerceWebApp/login">Login</a></li>
								<%
										}
									} catch (Exception e) {
										// Log the error and fallback to login option
										out.println("<li class='nav-item'><a class='nav-link' href='/ECommerceWebApp/login'>Login</a></li>");
									}
								%>
							</ul>
						</li>
					</ul>
				</div>
			</div>
		</nav>
	</div>
	<div class="search_input" id="search_input_box">
		<div class="container">
			<form action="home" method="GET" class="d-flex justify-content-between">
				<input type="text" name="searchShoes" class="form-control" id="searchShoes" placeholder="Search Here">
				<button type="button" class="btn"></button>
				<span class="lnr lnr-cross" id="close_search" title="Close Search"></span>
			</form>
		</div>
	</div>
	<%@ include file="/common/notifications.jsp" %>
</header>
