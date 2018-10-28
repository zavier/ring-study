(defproject ring-study "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [ring/ring-core "1.6.3"]
                 [ring/ring-mock "0.3.2"]
                 [ring/ring-jetty-adapter "1.6.3"]]
  :ring {:handler ring-study.core/handler}
  :plugins [[lein-ring "0.12.1"]])
