#!/bin/bash

#make sure working dir is correct
cd $(dirname $0)

quiet="false"

say(){
	if [[ "$quiet" != "true" ]]; then
		echo "$1"
	fi
}

clean(){
	#remove compiled code
	say "Cleaning"
	rm *.class 2>/dev/null
	say "Done"
}

compiled(){
	say "Checking if all java files are compiled"
	for i in *.java; do
		if [[ ! -e `echo $i|sed -e s/.java$/.class/` ]]; then
			say "$i not compiled"
			return 1
		fi
	done
	say "Done"
	return 0
}

compile(){
	say "Compiling"
	javac *.java
	if [[ "$?" != "0" ]]; then
		echo "Compilation failed"
		return 1
	else
		say "Done"
	fi
	return 0
}

run(){
	#run with all the jars in the current directory in the classpath
	say "Running"
	cp="."
	for i in *.jar
	do
		cp="$cp:$i"
	done
	java -cp $cp Main
	say "Done"
	return 0
}

case "$1" in
	clean)
		clean
		;;
	build)
		clean && compile
		;;
	rerun)
		run
		;;
	run)
		clean && compile && run
		;;
	
	*)
		quiet="true"
		compiled && run && exit # if the program is already compiled, just run it
		clean && compile && run # if not, compile and run
		;;
esac
