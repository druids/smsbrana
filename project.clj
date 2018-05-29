(defproject smsbrana "0.0.0"
  :description "A connector for SMS brána https://www.smsbrana.cz"
  :url "https://github.com/druids/smsbrana"
  :license {:name "MIT License"
            :url "http://opensource.org/licenses/MIT"}

  :dependencies [[cheshire "5.8.0"]
                 [http-kit "2.3.0"]
                 [com.cemerick/url "0.1.1"]]

  :profiles {:dev {:plugins [[lein-cloverage "1.0.10"]
                             [lein-kibit "0.1.6"]]
                   :dependencies [[clj-http-fake "1.0.3"]
                                  [org.clojure/clojure "1.9.0"]]
                   :source-paths ["src" "dev/src"]}})
