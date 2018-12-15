LD_LIBRARY_PATH=`pwd`
export LD_LIBRARY_PATH
echo 'which file do you wanna read: '
read file_name
java ReadFile $file_name
echo 'Do you want to quit(y/n): '
read n
if [ $n = 'n' ]; then
	sh ReadFile.sh
fi
