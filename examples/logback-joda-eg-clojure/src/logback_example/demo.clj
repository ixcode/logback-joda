(ns logback-example.demo
  (:use clojure.tools.logging clj-logging-config.logback)
  (:import org.ixcode.logback.joda.LogbackJodaContext))


(defn -main [& args]
  (reset-logging!)
  (LogbackJodaContext/configure)
  (set-logger! :pattern "[%d{yyyy-MM-DD'T'HH:mm:ss.SSSZZ z}] - %msg%n")
  (info "Hello Logback and Joda"))

