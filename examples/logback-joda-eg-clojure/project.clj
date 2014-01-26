(defproject logback-joda-eg-clojure "0.1.0-SNAPSHOT"
  :description "An api server for climbing routes"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :repositories {"sonatype-oss-public" "https://oss.sonatype.org/content/groups/public/"
                 "ixcode-repo" "http://repo.ixcode.org/public"}

  :dependencies [[org.clojure/clojure "1.5.1"]
                 [clj-logging-config "1.9.11-SNAPSHOT"]
                 [org.ixcode/logback-joda "1.0-SNAPSHOT"]
                 ]
  :main logback-example.demo
  :aot [logback-example.demo]
  )

