<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

    <title>Mail List</title>
    <link rel="stylesheet" type="text/css" href="css/reset.css"/>
    <link rel="stylesheet" type="text/css" href="css/styles.css"/>
    <link rel="stylesheet" type="text/css" href="css/jquery-calendar.css" />
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/jquery-calendar.js"></script>
    <script type="text/javascript" src="js/default.js"></script>
    <script type="text/javascript">
        //<![CDATA[
            $(document).ready(function (){
                $("#fromDate, #calendar1").calendar();
                $("#fromDate_alert").click(function(){
                    alert(popUpCal.parseDate($('#fromDate').val()))
                });
                $("#toDate, #calendar2").calendar();
                $("#toDate_alert").click(function(){
                    alert(popUpCal.parseDate($('#toDate').val()))
                });
            });
        //]]>
    </script>

    <!--[if lt IE 9]>
    <script src="js/html5.js"></script>
    <![endif]-->
    <script type="text/javascript" src="js/pagination.js"></script>
</head>
<body>
    <div id="page">
        <!-- header -->
        <header>
            <div id="admin_head">
                <div id="top_menu_logo"></div>
                <h1>Candidate</h1>
            </div>
        </header>
        <nav>
            <a href="dashboard.html" title="Home">Dashboard</a>
            <a href="showlist.html" title="Mail List">Mail List</a>
            <!--<a href="" title="Statistic">Statistic</a>-->
            <!--<a href="" title="Report">Report</a>-->
            <c:choose>
                <c:when test="${isMailServerUp}">
                    <span class="tab_status_green">OK</span>
                </c:when>
                <c:when test="${not isMailServerUp}">
                    <span class="tab_status_red">Mail Server Down</span>
                </c:when>
                <c:otherwise>
                    <span class="tab_status_red">Unknown</span>
                </c:otherwise>
            </c:choose>

            <span class="tab_status">System Check</span>
        </nav>