#Demo Topology

This is just a simple topology demo not unlike any found in the [storm-starter](https://github.com/nathanmarz/storm-starter) repo. I would recommend starting there. But this topology does demo the amqp-spout and has a heavily annotated clojure topology. 

##Install

1. Make sure local jars are avaialable in ```../lib/``` . Currently two jars are required
   1. storm-amqp-spout-0.1.4.jar 
   2. storm-json-0.0.1.jar
   
2. Install local jars. From the lib directory run both commands below
    
   ```
   mvn install:install-file  -Dfile=storm-json-0.0.1.jar -DartifactId=storm-json  -Dversion=0.0.1 -DgroupId=com.rapportive  -Dpackaging=jar  -DlocalRepositoryPath=../demo/mvn_repo
   
   mvn install:install-file -Dfile=storm-amqp-spout-0.1.4.jar -DartifactId=storm-amqp-spout -Dversion=0.1.4 -DgroupId=com.rapportive -Dpackaging=jar -DlocalRepositoryPath=../demo/mvn_repo
   ```
   
3. Load up depedencies and compile

   ```
   lein deps 
   lein compile
   ```
   
4. Run the demo:
   
   Clojure
   
   ```
   lein run -m com.promojam.clj.basic_stream.clj
   ```
   
   Java
   
   ```
   java -cp $(lein classpath) com.promojam.BasicStream
   ```
   
   both accept optional arguements : exchange , routing_key ( defaults to 'logstash' , '#' )

   e.g.
   
   ```
   lein run -m com.promojam.clj.basic_stream.clj logstash #
   ```
 
   

