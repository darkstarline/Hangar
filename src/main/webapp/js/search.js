/*
'use strict';

var searchBox = document.querySelectorAll('.search-box input[type="text"] + span');

searchBox.forEach(function (elm) {
	elm.addEventListener('click', function () {
		elm.previousElementSibling.value = '';
	});
});*/
$(".text").click(function (){
    window.location.href="views/travel.html";
})