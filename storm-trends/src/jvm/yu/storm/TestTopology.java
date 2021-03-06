/*package yu.storm;

import java.util.Arrays;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import backtype.storm.utils.Utils;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.tuple.Values;

import yu.storm.bolt.DocumentFetchBolt;
import yu.storm.bolt.TokenizeBolt;
import yu.storm.bolt.TermFilterBolt;
import yu.storm.bolt.DCountBolt;
import yu.storm.bolt.DfCountBolt;
import yu.storm.bolt.TfCountBolt;
import yu.storm.bolt.TfidfBolt;
import yu.storm.bolt.TopNBolt;
import yu.storm.bolt.IntermediateRankingsBolt;
import yu.storm.bolt.TotalRankingsBolt;
import yu.storm.bolt.RankingsReportBolt;
import yu.storm.spout.TestTfidfSpout;



class TestTopology {

  private static final int DEFAULT_RUNTIME_IN_SECONDS = 60;
  private static final int TOP_N = 20;

	private static String[] mimeTypes = new String[] { "application/pdf", "text/html", "text/plain" };

	public static void main(String[] args) throws Exception{
    String spoutId = "twitter-spout";
    String tfidfId = "tfidf-bolt";
    String intermediateRankerId = "intermediateRanker";
    String totalRankerId = "finalRanker";
    String reporterId = "report-bolt";
    // create the topology
    TopologyBuilder builder = new TopologyBuilder();

    // set topology
    builder.setSpout(spoutId, new TestTfidfSpout(),1);
    builder.setBolt("document-fetch-bolt", new DocumentFetchBolt(mimeTypes), 10).shuffleGrouping(spoutId);
    builder.setBolt("tokenize-bolt", new TokenizeBolt(), 10).shuffleGrouping("document-fetch-bolt");
    builder.setBolt("filter-bolt", new TermFilterBolt(), 10).shuffleGrouping("tokenize-bolt");
    builder.setBolt("dfcount-bolt", new DfCountBolt(), 5).fieldsGrouping("filter-bolt", new Fields("term"));
    builder.setBolt("tfcount-bolt", new TfCountBolt(), 5).fieldsGrouping("filter-bolt", new Fields("term", "documentId"));
    builder.setBolt(tfidfId, new TfidfBolt(), 1).globalGrouping("dfcount-bolt")
                                                .globalGrouping("tfcount-bolt");
    //builder.setBolt("topn-bolt", new TopNBolt(), 1).globalGrouping("tfidf-bolt");
    builder.setBolt(intermediateRankerId, new IntermediateRankingsBolt(TOP_N), 4).fieldsGrouping(tfidfId, new Fields("term"));
    builder.setBolt(totalRankerId, new TotalRankingsBolt(TOP_N)).globalGrouping(intermediateRankerId);
    builder.setBolt(reporterId, new RankingsReportBolt(), 1).globalGrouping(totalRankerId);
    
    // create the default config object
    Config conf = new Config();

    // set the config in debugging mode
    conf.setDebug(true);

    if (args != null && args.length > 0) {

      // run it in a live cluster

      // set the number of workers for running all spout and bolt tasks
      conf.setNumWorkers(3);

      // create the topology and submit with config
      StormSubmitter.submitTopology(args[0], conf, builder.createTopology());

    } else {

      // run it in a simulated local cluster

      // set the number of threads to run - similar to setting number of workers in live cluster
      conf.setMaxTaskParallelism(4);

      // create the local cluster instance
      LocalCluster cluster = new LocalCluster();

      // submit the topology to the local cluster
      cluster.submitTopology("tweet-word-count", conf, builder.createTopology());

      // let the topology run for 300 seconds. note topologies never terminate!
      Utils.sleep(300000000);

      // now kill the topology
      cluster.killTopology("tweet-word-count");

      // we are done, so shutdown the local cluster
      cluster.shutdown();
    }
  }
}*/