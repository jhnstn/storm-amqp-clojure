package com.promojam;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.utils.Utils;
import com.promojam.bolt.PrinterBolt;

import com.rapportive.storm.spout.AMQPSpout;
import com.rapportive.storm.amqp.ExclusiveQueueWithBinding;
import com.rapportive.storm.scheme.SimpleJSONScheme;

public class BasicStream {

  public static void main(String[] args){
    
    TopologyBuilder builder = new TopologyBuilder();

      ExclusiveQueueWithBinding queue = new ExclusiveQueueWithBinding("logstash","#");
      AMQPSpout amqp = new AMQPSpout("localhost", 5672, "guest" ,"guest", "/" , queue , new SimpleJSONScheme());


      builder.setSpout("spout",amqp);  
      builder.setBolt("print", new PrinterBolt())
             .shuffleGrouping("spout");

      Config conf = new Config();
        
        
      LocalCluster cluster = new LocalCluster();
        
      cluster.submitTopology("test", conf, builder.createTopology());
  }
}
