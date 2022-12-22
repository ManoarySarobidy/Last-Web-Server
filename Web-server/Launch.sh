#!/bin/bash

# je dois dire ici si le programme que je recherche est php
# si oui je set un environement de variable a mon bin pour que le php soit valable n'import où
# En notant que ce php sera juste temporaire

# misy php ve le olona
if ! [ -x "$( command -v php  )" ]; then
	echo 'Error : php is not installed .' >&2
	exit 1
fi

# tokony hoe asiako bin php mba afahan'ilay olona mampiasa ilay web server

# set root folder path
WEB_PATH=$(sudo find $HOME/ -name 'Server')/project/
echo $WEB_PATH