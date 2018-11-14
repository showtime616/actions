# actions

Инструкция:
1. Для запуска приложения должны быть установлены:
 а) Java 8u181
 б) Apache Tomcat 8.5.34 (%TOMCAT_HOME%)
2. actions.war из папки deploy скопировать в папку %TOMCAT_HOME%/webapps 
3. Перейти в папку %TOMCAT_HOME%/bin
4. Выполнить команду в cmd/терминале:
 а) для Windows: catalina.bat run
 б) для Linux: catalina.sh run 
5. После запуска, приложение будет доступно по адресу localhost:8080/actions/
6. Для доступа в консоль БД, перейти по адресу localhost:8080/actions/h2
7. В файле WEB-INF/classes/data.sql находятся sql-скрипты для инициализации БД, можно удалить или заменить своими
8. Изначально, в соответствии со скриптами из data.sql(см. пункт 7), есть две акции с идентификаторами 1 и 2, которые доступны по адресам localhost:8080/actions/action/1 и localhost:8080/actions/action/2 соответственно