(ns smsbrana.core
  (:refer-clojure :exclude [send])
  (:require
    [clojure.java.io :as io]
    [clojure.string :refer [join]]
    [clojure.xml :as xml]
    [java-time :as jt]
    [org.httpkit.client :as http]
    [pandect.algo.md5 :refer [md5]]
    [struct.core :as st]))


(def ResponseSchema
  {:err [st/required st/integer-str]
   :price st/number-str
   :sms_count st/integer-str
   :credit st/number-str
   :sms_id st/string-like})


(def default-host "https://api.smsbrana.cz/smsconnect/http.php")
(def char-seq (map char (range 33 127)))


(defn send
  "Sends a SMS within a given options:
   :login - a login
   :password - a password
   :text - a SMS test
   :number - a phone number

  See other optional arguments in SMS brána documentation.
  It returns a tuple within a status, a parsed body and a HTTP response e.g.: [:ok {:err 0, ...} {...}]"
  ([opts]
   (send opts default-host))
  ([{:keys [password] :as opts} host]
   (let [now (jt/local-date-time)
         salt (join (repeatedly 50 #(rand-nth char-seq)))
         auth (md5 (join [password (jt/format "yyyyMMdd" now) "T" (jt/format "HHmmss" now) salt]))
         response @(http/post host {:query-params (merge opts
                                                         {:auth auth
                                                          :action "send_sms"})})]
     (try
       (let [body (->> response
                       :body
                       .getBytes
                       io/input-stream
                       xml/parse
                       :content
                       (reduce (fn [acc e] (assoc acc (:tag e) (-> e :content first))) {}))
             [err model] (st/validate body ResponseSchema)]
         (if (nil? err)
           [(if (and (some? (:err model)) (zero? (:err model))) :ok :error) model response]
           [:error-malformed nil response]))
       (catch Exception e [:error-unmarshalling nil response])))))
