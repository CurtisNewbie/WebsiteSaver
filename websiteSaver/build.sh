frontendStatic=frontend/static
javaStatic=src/main/resources/static
buildLog="build.log"
jarName="websiteSaver-0.0.1.jar"

if [ -f $buildLog ]; then
    rm $buildLog
fi

echo "Start building project, see '$buildLog'"

if [ ! -d $javaStatic ]; then
    echo "The 'static' folder (${javaStatic}) for java project not exists, creating it..." 
    mkdir $javaStatic
fi

# copy hosted files
echo "Copying frontend files to 'static' folder"
cp -rv ${frontendStatic}/* $javaStatic >> $buildLog

# do a clean package
echo "Executing maven clean package"
mvn clean package >> $buildLog

# remove hosted files
echo "Removing frontend files from 'static' folder"
rm -rf $javaStatic/* >> $buildLog

find target/ -name ${jarName} -type f -exec cp {} . \;

if [ -f "${jarName}" ]; then
    echo "Build success: '$(readlink -e ${jarName})'"
fi
