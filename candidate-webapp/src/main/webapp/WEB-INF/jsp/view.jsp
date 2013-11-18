<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>Candidate</title>
<link rel="stylesheet" type="text/css" href="css/reset.css" />
<link rel="stylesheet" type="text/css" href="css/styles.css" />
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
		<br />
		<div>
			<h2>Name: ${candidate.name}</h2>
			<br />
			<img alt="candidate" src="image/${candidate.modifiedTime.millis}/${candidate.uuid}.image">
			<h3>Name: ${candidate.name}</h3>
			<h3>Age: ${candidate.age}</h3>
			<h3>Uuid: ${candidate.uuid}</h3>
			<br />
		</div>
		<footer>
			<span>&copy; 2012 by ABC Tech Ltd. (Thailand). All rights
				reserved.<br /> </span>
		</footer>
	</div>

</body>
</html>
