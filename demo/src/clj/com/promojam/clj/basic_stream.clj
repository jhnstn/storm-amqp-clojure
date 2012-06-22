(ns com.promojam.clj.basic-stream
  (:import 
    (backtype.storm LocalCluster)
    (backtype.storm.topology IRichSpout )
    (com.rapportive.storm.spout  AMQPSpout )
    (com.rapportive.storm.amqp ExclusiveQueueWithBinding )
    (com.rapportive.storm.scheme SimpleJSONScheme )
    (com.promojam.bolt PrinterBolt))
  (:use [backtype.storm clojure config])
  (:gen-class))


;; log-count
;; This will count all the log messages moving through the topology 
;; based on storm-starter word_count.clj 'word-count' bolt

(defbolt log-count ["count"] {:prepare true}
  [conf tuple collector]
  (let [count (atom {})]                         ; set up count atom
    (bolt                                        ; closure to hold the local count 
      (execute [tuple]                           ; call IRichBolt::execute(Tuple input)
                 (swap! count                    ; ok atoms are immutable so swap! count lets us change counts value
                        (partial                 ; very cool http://blog.jayfields.com/2011/01/clojure-partial-and-comp.html
                          merge-with +)          ; mapping function : apply '+' to all matching log-msg  
                                 {"cnt" 1})      ; hard code "cnt" key in the count atom
                 (emit-bolt!                     ; storm function  
                   collector [@count]            ; we said we would emit the count so lets do so - also said the tuple would have 1 value
                   :anchor tuple)                ; ? not sure what :anchor does 
                 (ack! collector tuple)          ; ack the tuple back to the source ?       
                 ))))  
                        

(defn mk-topology [exchange] 
  (topology
    {"s1" (spout-spec  
            (AMQPSpout. 
                "localhost"  ; host
                5672         ; port
                "guest"      ; username
                "guest"      ; pass
                "/"          ; vhost
                (ExclusiveQueueWithBinding.  exchange  "#" )       ; queue
                (SimpleJSONScheme.))
             :p 1)}

    {"b1" (bolt-spec {"b2" :shuffle}
                     (PrinterBolt.)
                     :p 2 )
     "b2" (bolt-spec {"s1" []} ; not sure if we need to pass anything in here
                      log-count
                      :p 5)}
     ))

 
(defn -main []
  (let [cluster (LocalCluster.)]
    (.submitTopology cluster "demo" {TOPOLOGY-DEBUG true} (mk-topology "logstash" ))))
                        

