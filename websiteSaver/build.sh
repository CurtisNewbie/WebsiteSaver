FRONTEND_ROOT=frontend/static
JAVA_RESOUCES=src/main/resources/static

mkdir $JAVA_RESOUCES

# copy hosted files
cp -rv $FRONTEND_ROOT/.* $JAVA_RESOUCES

# do a clean package
mvn clean package

# remove hosted files
rm -rvf $JAVA_RESOUCES

cp ./**/*.jar .

