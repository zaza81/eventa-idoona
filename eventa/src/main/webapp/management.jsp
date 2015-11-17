<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet" integrity="sha256-MfvZlkHCEqatNoGiOXveE8FIwMzZg4W85qfrfIFBfYc= sha512-dTfge/zgoMYpP7QbHy4gWMEGsbsdZeCXz7irItjcC3sPUFtf0kuFbDz/ixG7ArTxmDjLXDmezHubeNikyKGVyQ==" crossorigin="anonymous">
<link href="/css/management.css" rel="stylesheet" >
<script src="https://code.jquery.com/jquery-1.11.3.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js" integrity="sha256-Sk3nkD6mLTMOF0EOpNtsIry+s1CsaqQC1rVLTAy+0yc= sha512-K1qjQ+NcF2TYO/eI3M6v8EiNYZfA95pQumfvcVrTHtwQVDG+aHRqLi/ETn2uB+1JqwYqVG3LIvdm9lj6imS/pQ==" crossorigin="anonymous"></script>


<title>Manage Eventa Contacts</title>
</head>
<body>

 <form NAME="followForm" action="/management" METHOD="POST">

<table class="table table-responsive">
<img src="https://www.eventa.it/assets/logo_eventa-01.svg" alt="Eventa logo" class="img-responsive logo center-block" >

<h4 class="title text-center"> Seleziona le citt&agrave; che vuoi seguire:</h4>

<tr >
	<td >Eventi Milano</td>
  <td ><button class="follow btn btn-default" type="button">+ Segui</button></td>
</tr>

<tr>
	<td >Eventi Roma</td>
  <td ><div class="follow btn btn-default" >+ Segui</div></td>
</tr>

<tr >
	<td >Eventi Napoli</td>
  <td ><button  class="follow btn btn-default"type="button">+ Segui</button></td>
</tr>

<tr >

	<td >Eventi Palermo</td>
  <td ><button  class="follow btn btn-default" type="button">+ Segui</button></td>
</tr>

<tr>
  <td >Eventi Cagliari</td>
  <td ><button class="follow btn btn-default" type="button">+ Segui</button></td>
</tr>
<tr>
  <td >Eventi Torino</td>
  <td ><button class="follow btn btn-default" type="button">+ Segui</button></td>
</tr>

<tr>
  <td >Eventi Verona</td>
  <td ><button class="follow btn btn-default" type="button">+ Segui</button></td>
</tr>

<tr>
  <td >Eventi Bologna</td>
  <td ><button class="follow btn btn-default" type="button">+ Segui</button></td>
</tr>

<tr>
  <td >Eventi Firenze</td>
  <td ><button class="follow btn btn-default" type="button">+ Segui</button></td>
</tr>

<tr>
  <td >Eventi Bari</td>
  <td ><button class="follow btn btn-default" type="button">+ Segui</button></td>
</tr>

<tr>
  <td >Eventi Catanzaro</td>
  <td ><button class="follow btn btn-default" type="button">+ Segui</button></td>
</tr>



<INPUT TYPE="HIDDEN" NAME="sender">

</table>
</form>

</body>

  <SCRIPT LANGUAGE="JavaScript">

$( document ).ready(function() {
  

  $(".follow").click(function(){
  // Holds the product ID of the clicked element

 if($(this).hasClass("checked")){
  $(this).html("+ Segui");
   $(this).removeClass( "checked" );

 }else {
  $(this).html("&radic;");
   $(this).addClass( "checked" );
 }

});

});


         
       
        </SCRIPT>
</html>