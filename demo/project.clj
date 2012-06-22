(defproject socialstorm-basic "0.0.1"
    :source-path "src/clj"
    :java-source-path "src/jvm"
    :aot :all
    :jvm-opts ["-Djava.library.path=/usr/local/lib:/opt/local/lib:/usr/lib"]
    :repositories {
                   "twitter4j" "http://twitter4j.org/maven2"
                   "mvnrepository" "http://mvnrepository.com"
                   "local" ~(str (.toURI (java.io.File. "mvn_repo")))  
                 }

    :dependencies [
                   [com.rapportive/storm-amqp-spout "0.1.4"]
                   [com.rapportive/storm-json "0.0.1"]
                   [com.rabbitmq/amqp-client "2.6.1"]
                   [org.twitter4j/twitter4j-core "2.2.6-SNAPSHOT"]
                   [org.twitter4j/twitter4j-stream "2.2.6-SNAPSHOT"]
                 ]

    :dev-dependencies [
                       [storm "0.7.2"]
                       [org.clojure/clojure "1.4.0"]
                      ])           

