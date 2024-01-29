<%-- 
    Document   : PlanetSortedByMass
    Created on : Jan 13, 2024, 9:48:08 AM
    Author     : marty
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <title>Pick Feature</title>
</head>
<body>
    <h1>Pick Feature</h1>
    
    <div>
        <form action="PlanetSortedByMassButtonClick" method="GET">
            <button type="submit">Planet Sorted By Mass</button>
        </form>
        <form action="CorrelationCoefficientController" method="GET">
            <button type="submit">Correlation Coefficient</button>
        </form>
        <form action="TemperatureAndDistanceCorrelationController" method="GET">
            <button type="submit">Temperature And Distance Correlation</button>
        </form>
        <form action="PlanetsWithMagneticFieldController" method="GET">
            <button type="submit">Planets With Magnetic Field</button>
        </form>
        <form action="AddPlanetController" method="GET">
            <button type="submit">Add Planet</button>
        </form>
        <form action="CookieAccessController" method="GET">
            <button type="submit">Cookie Info</button>
        </form>
        
        <button onclick='goBack()'>Back</button>
        <script>
            function goBack() {
            window.history.back();
            }
            </script>
    </div>
</body>
</html>
