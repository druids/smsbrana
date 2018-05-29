(defproject smsbrana "0.0.0"
  :description "A connector for SMS brána https://www.smsbrana.cz"
  :url "https://github.com/druids/smsbrana"
  :license {:name "MIT License"
            :url "http://opensource.org/licenses/MIT"}

  :dependencies [[cheshire "5.8.0"]
                 [clojure.java-time "0.3.2"]
                 [funcool/struct "1.2.0"]
                 [http-kit "2.3.0"]
                 [pandect "0.6.1"]]

  :profiles {:dev {:plugins [[lein-kibit "0.1.6"]
                             [venantius/ultra "0.5.2"]
                             [jonase/eastwood "0.2.5"]]
                   :dependencies [[http-kit.fake "0.2.1"]
                                  [org.clojure/clojure "1.9.0"]]
                   :source-paths ["src" "dev/src"]}})
