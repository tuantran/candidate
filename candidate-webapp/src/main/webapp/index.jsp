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
        <h2>Documentation</h2>
        <br />
        <p>Candidate is a simple JSON REST Service for registering
            candidates. It has two main functions, one for registering new
            candidates and one for fetching already saved candidates.</p>
        <br />

        <h3>1. Saving</h3>
        <p>
            To register a Candidate use the <a
                href="http://siam.dev.abctech-thailand.com/candidate/rest/candidateService/save.json">SAVE</a>
            JSON webservice <br /> The function accepts a JSON object over HTTP
            POST, and save the candidate to database. <br /> <br />
            Save requires following fields in a JSON object
        </p>
        <table>
            <tr>
                <th>Field</th>
                <th>Description</th>
            </tr>
            <tr>
                <td><code>name</code></td>
                <td>The candidate's name</td>
            </tr>
            <tr>
                <td><code>age</code></td>
                <td>The candidate's age, numeric, in unit of years</td>
            </tr>
            <tr>
                <td><code>gender</code></td>
                <td>The candidate's gender, can be <code>M</code> or <code>F</code></td>
            </tr>
        </table>
        <p>
            The actual request might look like:
        </p>
<pre>
POST /candidate/rest/candidateService/save.json HTTP/1.1
Host: siam.dev.abctech-thailand.com
Content-Type: application/json

{"name":"John Gavin","age":38,"gender":"M"}
</pre>

        <h3>2. Loading</h3>
        <p>
            To load a Candidate, use the <a
                href="http://siam.dev.abctech-thailand.com/candidate/rest/candidateService/load/{uuid}.json">LOAD</a>
            JSON webservice <br /> The function will fetch from database using
            the uuid to lookup the candidate.<br /> <br />
        </p>
        <p>
            The actual request might look like:
        </p>
<pre>
GET /candidate/rest/candidateService/load/fa0fac6b-c166-43ce-bb64-28120cde4183.json HTTP/1.1
HOST: siam.dev.abctech-thailand.com
</pre>
        <p>
            The response contains the following fields in a JSON object
        </p>
        <table>
            <tr>
                <th>Field</th>
                <th>Description</th>
            </tr>
            <tr>
                <td><code>status</code></td>
                <td>The status of the request, can be <code>1</code> if success or <code>-1</code> if error occurred</td>
            </tr>
            <tr>
                <td><code>errorMessage</code></td>
                <td>The message describes what's wrong, can be <code>null</code></td>
            </tr>
            <tr>
                <td><code>errorCode</code></td>
                <td>The code of the error, can be <code>null</code></td>
            </tr>
            <tr>
                <td><code>id</code></td>
                <td>The internal id</td>
            </tr>
            <tr>
                <td><code>uuid</code></td>
                <td>The universally unique id</td>
            </tr>
            <tr>
                <td><code>name</code></td>
                <td>The candidate's name</td>
            </tr>
            <tr>
                <td><code>age</code></td>
                <td>The candidate's age, numeric, in unit of years</td>
            </tr>
            <tr>
                <td><code>gender</code></td>
                <td>The candidate's gender, can be <code>M</code> or <code>F</code></td>
            </tr>
            <tr>
                <td><code>createdTime</code></td>
                <td>The created time, numeric, in the unit of the milliseconds since January 1, 1970, 00:00:00 GMT</td>
            </tr>
            <tr>
                <td><code>modifiedTime</code></td>
                <td>The last modification time, numeric, in the unit of the milliseconds since January 1, 1970, 00:00:00 GMT</td>
            </tr>

        </table>
        <p>
            The actual response's payload might look like:
        </p>
<pre>
{"status":1,"errorMessage":null,"errorCode":null,"id":1,"uuid":"fa0fac6b-c166-43ce-bb64-28120cde4183","name":"John Gavin","age":38,"gender":"M","createdTime":1371815469461,"modifiedTime":1371815469461}
</pre>
        <p>
            If candidate's uuid is NOT existed, the actual response's payload might look like:
        </p>
<pre>
{"status":-1,"errorMessage":"Candidate does not exist","errorCode":-2,"id":null,"uuid":null,"name":null,"age":null,"gender":null,"createdTime":null,"modifiedTime":null}
</pre>


        <h3>3. Uploading picture</h3>
        <p>
            To upload a picture to the candidate, use the following service<a
                href="http://siam.dev.abctech-thailand.com/candidate/rest/candidateService/upload/image/{uuid}.json">Upload
            image</a> with HTTP PUT<br /> The function accepts a json object
            containing the image in base64 encoded. <br /> <br />
            And please only upload small portrait picture less than 100KB. :) <br/><br/>
            Upload image requires following fields in a JSON object
        </p>
        <table>
            <tr>
                <th>Field</th>
                <th>Description</th>
            </tr>
            <tr>
                <td><code>uuid</code></td>
                <td>The candidate's universally unique id</td>
            </tr>
            <tr>
                <td><code>imageBase64</code></td>
                <td>The base64 encoded of image, should not be over 100KB</td>
            </tr>
        </table>
        <p>
            The actual request might look like:
        </p>
<pre>
PUT /candidate/rest/candidateService/upload/image/fdb81038-c17d-4520-ad5a-d2b385eb3ad9.json HTTP/1.1
Host: siam.dev.abctech-thailand.com
Content-Type: application/json

{"uuid":"fdb81038-c17d-4520-ad5a-d2b385eb3ad9","imageBase64":"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"}
</pre>


        <h3>4. Viewing the candidate</h3>


        The candidate can be viewed <a href="http://siam.dev.abctech-thailand.com/candidate/view.html?uuid=fa0fac6b-c166-43ce-bb64-28120cde4183">here</a>

    </div>
    <footer>
            <span>&copy; 2012 by ABC Tech Ltd. (Thailand). All rights
                reserved.<br /> </span>
    </footer>
</div>

</body>
</html>
