<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin - Success</title>

    <!-- Include Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet" />

    <style>
        body {
            background-color: #f4f7f9;
        }

        /* Sidebar Style */
        .sidebar {
            width: 250px;
            background-color: #343a40;
            position: fixed;
            height: 100vh;
            left: 0;
            top: 0;
            padding-top: 20px;
            color: white;
        }

        .sidebar a {
            color: white;
            padding: 10px;
            display: block;
            text-decoration: none;
        }

        .sidebar a:hover {
            background-color: #495057;
        }

        /* Main content area */
        .main-content {
            margin-left: 250px;
            padding: 20px;
        }

        .card {
            margin-top: 100px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            text-align: center;
        }

        .card-header {
            background-color: #28a745;
            color: white;
        }

        footer {
            text-align: center;
            background-color: #343a40;
            color: white;
            padding: 10px 0;
            position: fixed;
            bottom: 0;
            width: 100%;
            box-shadow: 0 -4px 6px rgba(0, 0, 0, 0.1);
        }
    </style>
</head>

<body>

    <!-- Sidebar Section -->
    <div class="sidebar">
        <h2>Admin Panel</h2>
        <a href="${pageContext.request.contextPath}/ProductController">Manage Products</a>
        <a href="${pageContext.request.contextPath}/AdminController">My Profile</a>
        <a href="<c:url value='/user?action=list'/>">Customer Profile</a>
        <a href="${pageContext.request.contextPath}/LogoutServlet">Logout</a>
    </div>

    <!-- Main Content Section -->
    <div class="main-content">
        <div class="container">
            <div class="card">
                <div class="card-header">
                    <h2>Success</h2>
                </div>
                <div class="card-body">
                    <p class="alert alert-success">${successMessage}</p>
                    <div class="text-center">
                        <!-- Link to go back to user list -->
                        <a href="<c:url value='/user?action=list'/>" class="btn btn-primary">Back to User List</a>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <footer>
        &copy; 2024 Your Admin Panel. All Rights Reserved for shoesly.
    </footer>

    <!-- Include Bootstrap JS and jQuery -->
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.bundle.min.js"></script>
</body>

</html>
