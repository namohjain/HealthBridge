<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>About Us</title>
<!-- Fonts & Icons -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
<link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@700&display=swap" rel="stylesheet">

<!-- CSS -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/navbar.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/styles.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/home.css">
</head>
<body>
<%@ include file="header.jsp" %>
<br>

<div class="shadow-sm  mb-5 bg-body rounded">
  <div class="jumbotron" style="padding: 15px;">
    <h1 style="color: #2D89FF;">About Us</h1>  
  </div>
</div>
<div class="container">

    <!-- Post Content -->
    <p>
        Healthcare data is fragmented across providers, systems, and geographies. 
        <strong>HealthBridge</strong> is a secure, interoperable healthcare platform enabling patients and healthcare providers to share 
        and access comprehensive health data in real time. It promotes connected, informed, and 
        proactive healthcare.
    </p>

    <p>
        By integrating electronic health records (EHR), diagnostic results, prescriptions, and patient history, 
        HealthBridge creates a unified view of each patient's medical journey. This empowers healthcare 
        professionals to make faster, better-informed decisions and reduces the risk of medical errors or duplicate tests.
    </p>

    <p>
        Patients benefit by having full control over their medical data, with the ability to grant or revoke 
        access to providers as needed. The platform follows strict data privacy standards and complies with 
        national healthcare regulations to ensure secure communication and storage of sensitive health information.
    </p>

    <p>
        HealthBridge also supports telemedicine and remote monitoring by seamlessly connecting devices, 
        wearables, and healthcare applications. Whether in rural or urban settings, the platform bridges the 
        gap between patients and quality healthcare services.
    </p>

    <h4>Group Members:</h4>
    <ul>
        <li>Payal Kadav</li>
        <li>Namoh Jain</li>
        <li>Shraddha Chandanshiv</li>
        <li>Shweta Pawar</li>
        <li>Yash Kanade</li>
    </ul>

</div>

<%@ include file="footer.jsp" %>
</body>
</html>