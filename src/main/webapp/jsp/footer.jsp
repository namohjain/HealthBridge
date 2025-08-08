<%@page import="java.util.Date"%>
<%@page import="java.time.Year"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Footer Example</title>
    <style>
        /* Reset */
        html, body {
            height: 100%;
            margin: 0;
            display: flex;
            flex-direction: column;
        }

        /* Page content takes available space */
        .content {
            flex: 1;
        }

        /* Footer styling */
        footer {
            background-color: #2D89FF;;
            color: white;
            text-align: center;
            padding: 15px 0;
            font-size: 16px;
            position: relative; /* No fixed, so it stays responsive */
        }

        /* Optional hover effect for footer text */
        footer:hover {
            background-color: #6699FF;
            transition: background 0.3s;
        }
    </style>
</head>
<body>
    <div class="content"></div>
    <footer>
        &#169;2025 Copyright: Health Bridge - An automated Healthcare System
    </footer>
</body>
</html>
