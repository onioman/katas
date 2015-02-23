## Pick-guess kata

###Notas generales
Hecho en Perl porque seguramente lo tenga que utilizar en breve y conviene refrescar 
conceptos. Con más tiempo me hubiera gustado utilizar cosas más complejas del 
lenguaje (evaluación perezosa por ejemplo).

Pensando quizá en un futuro con memoria, tanto pick como guess tienen diferentes 
estrategias siendo una elegida al azar.

###Pick
	- Random, selecciona un valor aleatoriamente sin más.
  	- No binary, calcula los valores que un guesser binario escogería (no muy 
  	  eficientemente porque tarda lo suyo, 44s!!!) y no los tiene en cuenta al 
	  escoger aleatoriamente.

###Guess
Los guessers son muy parecidos y reflejan mi evolución:
	- Binary, búsqueda binaria sin más.
 	- Bin-qua, búsqueda binaria, pero en la segunda vuelta hace un división 
 	  extra asumiendo aleatoriamente un feedback.
	- Solvable, muy similar al anterior, pero en la segunda vuelta elige un rango 
	  que sea capaz de resolver (2 ** intentos restantes - 1).
	- NearBinary, búsqueda binaria añadiendo un offset (+/- 4) aleatorio al pívot.

Lo realmente interesante hubiera sido enfrentarlos y elegir el mejor, pero me faltó tiempo.1
