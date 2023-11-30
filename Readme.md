<h1>Run Project</h1>

<h2>With Docker</h2>

<h5>- Run Server</h5>
<ul>
<li>Install Docker, and open it.</li>
<li> If you don´t want to run client do:</li>
        
    - Open docker-compose.yml and remove:
        client:
            build:
              context: ./client
              dockerfile: Dockerfile
            ports:
              - "4200:80"
    - Now go to the root directory, the same directory that docker-compose.yml and run: "docker-compose up", now the server and Sql service is running.
</ul>

<h5>- Run Client</h5>

<ul>
<li> OPTIONAL STEP: If you need change ip or port go to "src/app/services/GLOBAL.ts" and set up it (default localhost:8034)</li>
<li> Copy all content root directory dist to root server project into a new folder called client/</li>
<li> Structure like that:</li>

        project-root/
        │
        ├── tarjet/              
        │   ├── trainproject-0.0.1-SNAPSHOT.jar
        │   └── Dockerfile        
        │
        ├── client/               
        │   ├── src/              
        │   ├── angular.json      
        │   ├── package.json      
        │   ├── Dockerfile            *
        │   ├── nginx-custom.conf     *
        │   ├── etc 
        │
        └── docker-compose.yml    

<li> Run docker-compose.yml with this command in terminal y same directory: "docker-compose up"</li>
<li>Now the project is running!</li>
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
