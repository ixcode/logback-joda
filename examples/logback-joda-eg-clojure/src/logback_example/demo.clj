(ns logback-example.demo
  (:use clojure.tools.logging clj-logging-config.logback))


(defn -main [& args]
  (info "Hello Logback"))
