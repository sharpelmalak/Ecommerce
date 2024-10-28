<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Notifications</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <style>
        .alert-overlay {
            position: fixed;
            top: 10px;
            left: 50%;
            transform: translateX(-50%);
            width: auto;
            max-width: 90%;
            border-radius: 5px;
            padding: 10px 20px;
            display: flex;
            align-items: center;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            transition: opacity 0.5s ease-out;
            opacity: 1;
            z-index: 9999;
        }

        .alert-success {
            background: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }

        .alert-danger {
            background: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }
    </style>
</head>
<body>
<!-- Success Message -->
<c:if test="${not empty success}">
    <div class="alert-overlay alert-success">
        <i class="fas fa-check-circle" style="font-size: 20px; margin-right: 10px;"></i>
        <strong>${success}</strong>
    </div>
</c:if>


<!-- Error Message -->
<c:if test="${not empty errors}">
    <c:forEach var="error" items="${errors}">
    <div class="alert-overlay alert-danger">
        <i class="fas fa-exclamation-circle" style="font-size: 20px; margin-right: 10px;"></i>
        <strong>${errors}</strong>
    </div>
    </c:forEach>
</c:if>

<script>
    function hideAlert() {
        var alerts = document.querySelectorAll('.alert-overlay');
        alerts.forEach(function(alert) {
            alert.style.opacity = '0';
            setTimeout(function() {
                alert.style.display = 'none';
            }, 500); // Duration of the fade-out transition
        });
    }

    // Hide alert on page click
    document.addEventListener('click', function(event) {
        var alerts = document.querySelectorAll('.alert-overlay');
        alerts.forEach(function(alert) {
            if (alert && !alert.contains(event.target)) {
                hideAlert();
            }
        });
    });

    // Automatically hide after 5 seconds
    setTimeout(hideAlert, 5000);
</script>
</body>
</html>