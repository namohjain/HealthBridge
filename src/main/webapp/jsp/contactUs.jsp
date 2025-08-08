<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Contact Us - HealthBridge</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <!-- Fonts & Icons -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
<link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@700&display=swap" rel="stylesheet">

<!-- CSS -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/navbar.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/styles.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/home.css">
    <style>
        body {
            background-color: #f4f7f9;
            color: #333;
        }

        .navbar {
            background-color: #00796b;
        }

        .navbar-brand, .navbar-nav .nav-link {
            color: #fff !important;
        }

        .navbar-nav .nav-link:hover {
            color: #2D89FF; !important;
        }

        .contact {
            padding: 50px;
            background: #e0f7fa;
            border-radius: 10px;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
        }

        .contact h1 {
            color: #00796b;
        }

        .contact .form-control {
            border-radius: 5px;
        }

        .contact .btn-primary {
            background-color: #00796b;
            border: none;
        }

        .contact .btn-primary:hover {
            background-color: #004d40;
        }

        .contact address {
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <%@ include file="header.jsp" %>

    <div class="container contact mt-5">
        <h1>Contact Us</h1>
        <div class="row">
            <div class="col-md-8">
                <div class="well well-sm">
                    <form>
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="name">Name</label>
                                    <input type="text" class="form-control" id="name" placeholder="Enter name" required="required" />
                                </div>
                                <div class="form-group">
                                    <label for="email">Email Address</label>
                                    <input type="email" class="form-control" id="email" placeholder="Enter email" required="required" />
                                </div>
                                <div class="form-group">
                                    <label for="subject">Subject</label>
                                    <select id="subject" name="subject" class="form-control" required="required">
                                        <option value="na" selected="">Choose One:</option>
                                        <option value="service">General Customer Service</option>
                                        <option value="suggestions">Suggestions</option>
                                        <option value="product">Product Support</option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="message">Message</label>
                                    <textarea name="message" id="message" class="form-control" rows="9" required="required" placeholder="Message"></textarea>
                                </div>
                            </div>
                            <div class="col-md-12">
                                <button type="submit" class="btn btn-primary pull-right" id="btnContactUs">Send Message</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div class="col-md-4">
                <legend>
                	<span class="glyphicon glyphicon-globe"></span> Our Office
                </legend>
                <address>
                    <strong>HealthBridge Team</strong><br>
                    2nd Floor, IT Tower, Rajiv Gandhi Infotech Park<br>
                    Hinjawadi Phase 2, Pune, Maharashtra 411057<br>
                    <abbr title="Phone">P:</abbr> +91 98765 43210
                </address>
                <address>
                    <strong>Email</strong><br>
                    <a href="mailto:HealthBridge@gmail.com">HealthBridge@gmail.com</a>
                </address>
            </div>
        </div>
    </div>
	<%@ include file="footer.jsp"%>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>