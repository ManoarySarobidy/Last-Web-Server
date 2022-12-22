<?php

	if(!isset($_SERVER['HTTP_POST'])){
		parse_str($argv[1], $_GET);
		parse_str($argv[1], $_POST);
	}
	
?>