# ARISHEV_NC_Project2
## Описание
Данный проект разрабатывается в рамках учебного курса компании NetCracker и представляет собой пошаговую многопользовательскую PvP игру.

## Необходимое ПО
Для работы с проектом понадобится:
* [JDK 8](http://www.oracle.com/technetwork/java/javase/downloads/2133151)
* Любая IDE (желательно [IntelliJ IDEA](https://www.jetbrains.com/idea/download/#section=windows))
* Средство сборки проектов ([Maven](http://maven.apache.org/download.cgi))
* [Google Web Toolkit](http://www.gwtproject.org/download.html)
* Сервер приложений [Tomcat](http://tomcat.apache.org/download-70.cgi)

## Начало

Импортируйте данный проект в свою IDE как Maven-проект. После этого с проектом можно работать.

## Запуск проекта на сервере приложений

1. Необходимо собрать проект в WAR-файл. Для этого в IDE во вкладке Maven Projects запустите goal под названием **Package** 
2. После успешной сборки скопируйте получившийся WAR-файл в директорию C:\apache-tomcat-7.0.73\webapps
3. Перейдите в директорию C:\apache-tomcat-7.0.73\bin b и запустите **Startup.bat**(либо **Startup.sh** для пользователей Linux). 
   Таким образом происходит запуск сервера приложйний.
4. Откройте браузер и в адресной строке введите следующий текст: http://localhost:8080/StepFighting/GameNC.html
5. Чтобы остановить работу сервера приложений снова перейдите в директорию C:\apache-tomcat-7.0.73\bin и запустите 
   **Shutdown.bat**(либо **Shutdown.sh** для пользователей Linux)
   
Для проверки внесенных в проект изменений пункты с 1 по 4 повторить.
