<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Welcome To HealthBridge</title>

<!-- Fonts & Icons -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
<link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@700&display=swap" rel="stylesheet">

<!-- CSS -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/navbar.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/styles.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/home.css">
<link rel="stylesheet" href="<%=EHCView.APP_CONTEXT%>/css/login.css">

<style>
body {
	width:100vw;
    margin: 0;
    background: #f8f9fa;
    font-family: 'Plus Jakarta Sans', sans-serif;
}

.hero-section {
	height: 100vh;
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 50px 80px;
    position: relative;
}

.hero-text {
    max-width: 600px;
}

.hero-text .title1 {
    font-size: 72px;
    font-weight: 800;
    color: #3D3D3E;
    margin-bottom: 20px;
}

.hero-text .title2 {
    font-size: 55px;
    font-weight: 800;
    color: #2D89FF;
    margin-bottom: 15px;
}

.hero-text .quote {
    font-size: 24px;
    color: #737373;
    margin-bottom: 30px;
}

.points {
    font-size: 18px;
    color: #565656;
    margin-top: 15px;
}

.points span {
    color: #2D89FF;
    font-weight: bold;
}

.hero-image img {
    width: 450px;
    max-width: 100%;
}

/* Decorative Assets */
.asset1, .asset2 {
    position: absolute;
    width: 100px;
}

.asset1 {
    bottom: 210px;
    left: 115px;
}

.asset2 {
    bottom: 61px;
    left: 83px;
}

/* Responsive Fix */
@media(max-width: 992px) {
    .hero-section {
        flex-direction: column;
        text-align: center;
        padding: 30px 20px;
    }
    .hero-text .title1, .hero-text .title2 {
        font-size: 42px;
    }
    .hero-text .quote {
        font-size: 18px;
    }
    .hero-image img {
        margin-top: 30px;
        width: 600px;
        height: 600px;
        
    }
}
</style>
</head>
<body>

<%-- --%>
<%@ include file="header.jsp" %>

<section class="hero-section">
    <div class="hero-text">
        <div class="title1">WELCOME TO</div>
        <div class="title2">HEALTHBRIDGE</div>
        <div class="quote">Making Health Care Better Together</div>
		<div class="frame5" style="border: 2px solid #2D89FF;">
 			<div class="frame52"></div>
 			<div class="point1"><span style="color: #2D89FF;">Hassle-free,</span> Online Doctor Appointment</div>
 			<div style="line-height: 148px;" class="point1"><span style="color: #2D89FF;">24/7, </span> Medical Service</div>
 			<div style="line-height: 219px;" class="point1"><span style="color: #2D89FF;">Schedule, </span> Appointment at your own will</div>
 			<img src="images/Asset 3@4x.png" alt="" class="asset1" />
 			<img src="images/Asset 4@4x.png" alt="" class="asset2" />
 		</div>
    </div>

    <div class="hero-image">
        <img src="images/pose_6.png" alt="Pose">
    </div>

</section>
	<%@ include file="footer.jsp"%>

</body>
</html>