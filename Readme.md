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


<h1>Run Mobile project</h1>
<ul>

<li> Install Android Studio from oficial website: <a>https://developer.android.com/</a> </li>
<li> Go to src/java/com.adrianj.trainapp/general/Global and change BASE_URL for your internal ip, ex: "http://192.168.x.x:8034/" </li>
<li> Go to in menu Tools/Device Manager, and create a new device:</li>


    1. Select in Phone, Pixel 3a and press next.
    2. Select system image named Tiramisu, API level 33 and press next
    3. Press finish.

<li> Ensure select in spring application select the same ip in the file application.properties and run it</li>
<li> Ensure select angular application select the same ip in the file src/app/services/GLOBAL</li>
<li> Now run the mobile project </li>

</ul>
