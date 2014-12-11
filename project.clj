(defproject machine_learning "0.1.0-SNAPSHOT"
  :description "my clojure project:) : write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :plugins [ [lein-cljsbuild "0.3.2" ]
             [cider/cider-nrepl "0.8.0-SNAPSHOT"]]
  :dependencies [[org.clojure/clojure "1.6.0"]
 				[org.clojure/data.json "0.2.5"]
 				[org.clojure/data.csv "0.1.2"]
              	[org.clojure/clojurescript "0.0-2202"]
 				[clj-time "0.5.1"]
 				[incanter "1.5.2"]
 				[cc.mallet/mallet "2.0.7"]
 				[me.raynes/fs "1.4.4"]]
  :cljsbuild {:builds [{:source-paths ["src-cljs"],
                        :compiler {:pretty-printer true,
                                   :output-to "www/js/main.js",
                                   :optimizations :whitespace}}]}
  :main ^:skip-aot machine-learning.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
