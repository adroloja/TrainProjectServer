<h1>Run Project</h1>

<h3>With Docker</h3>
<ul>
<li>Install Docker, and open it.</li>
<li>Root project open console and run: mvn clean</li>
<li>After that run: mvn package</li>
<li>Go to target directory and create a new file named Dockerfile</li>
<li>Open Dockerfile with an editor and write:</li>

FROM openjdk:17

WORKDIR /app

COPY trainproject-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8034

CMD ["java", "-jar", "app.jar"]
<li>Now go to the root directory, the same directory that docker-compose.yml</li>
<li>Run: docker-compose up</li>
<li>Now the project is running!</li>
</ul>

<h3>With default Xampp connection</h3>
<ul>
<li>Install Xampp <a>https://www.apachefriends.org/</a></li>
<li>Open Xampp and run Apache and MySql service.</li>
<li>Open browser and go <a>http://localhost/phpmyadmin/</a></li>
<li>Tag SQL and write "CREATE DATABASE trainDDBB" and push continue.</li>
<li>Now the project is ready to launch!</li>
</ul>

<h3>With MySql Workbench</h3>
<ul>
<li>Install Xampp <a>https://www.apachefriends.org/</a></li>
<li>Open Xampp and run MySql service.</li>
<li>Open MySql Workbench, connect it to the service and create a database with this name "trainDDBB".</li>
<li>Now the project is ready to launch!</li>
</ul>

