(defproject rpn-calculator "0.1.0-SNAPSHOT"
  :description "Revisiting RPN calculator kata in Clojure"
  :license {:name "GNU GPL V3"
            :url "http://www.gnu.org/licenses/gpl-3.0.txt"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/tools.logging "0.3.1"]]
  :main ^:skip-aot rpn-calculator.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}
             :dev {:dependencies [[midje "1.6.3"]]
                   :plugins [[lein-midje "3.1.3"]]}
             })
