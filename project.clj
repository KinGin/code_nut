(defproject solinor_wages "1.0.0-SNAPSHOT"
  :description "Solinor wages app"
  :url "http://solinor-wages.herokuapp.com"
  :license {:name "Eclipse Public License v1.0"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/data.csv "0.1.3"]
                 [compojure "1.4.0"]
                 [ring/ring-jetty-adapter "1.4.0"]
                 [environ "1.0.0"]
                 [org.clojure/java.jdbc "0.6.1"]
                 [org.postgresql/postgresql "9.4-1201-jdbc41"]
                 [semantic-csv "0.1.0"]
                 [hiccup "1.0.5"]]
  :min-lein-version "2.0.0"
  :main ^:skip-aot solinor_wages.web
  :plugins [[environ/environ.lein "0.3.1"]]
  :hooks [environ.leiningen.hooks]
  :uberjar-name "solinor_wages_standalone.jar"
  :profiles {:production {:env {:production true}} :uberjar {:aot :all}})
