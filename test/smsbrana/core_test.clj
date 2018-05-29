(ns smsbrana.core-test
  (:require
    [clojure.test :refer [deftest is testing]]
    [org.httpkit.fake :refer [with-fake-http]]
    [smsbrana.core :as smsbrana]))


(deftest send-test
  (testing "should not send a SMS, error 4"
    (with-fake-http [#"^https://api.smsbrana.cz"
                     "<?xml version='1.0' encoding='utf-8'?>\n<result>\n<err>4</err>\n</result>"]
      (let [[status result _]
            (smsbrana/send {:login "login", :password "password", :text "TEST", :number "+420777666555"})]
        (is (= :error status))
        (is (= {:err 4} result)))))

  (testing "should send a SMS"
    (with-fake-http [#"^https://api.smsbrana.cz"
                     (str "<?xml version='1.0' encoding='utf-8'?>\n"
                          "<result><err>0</err><price>1.1</price><sms_count>1</sms_count>"
                          "<credit>1523.32</credit><sms_id>377351</sms_id></result>")]
      (let [[status result _]
            (smsbrana/send {:login "login", :password "password", :text "TEST", :number "+420777666555"})]
        (is (= :ok status))
        (is (= {:credit 1523.32, :price 1.1, :sms_count 1, :sms_id "377351", :err 0} result)))))

  (testing "should not send a SMS, malformed response"
    (with-fake-http [#"^https://api.smsbrana.cz"
                     "<?xml version='1.0' encoding='utf-8'?>\n<result>\n</result>"]
      (let [[status result _]
            (smsbrana/send {:login "login", :password "password", :text "TEST", :number "+420777666555"})]
        (is (= :error-malformed status))
        (is (nil? result)))))

  (testing "should not send a SMS, invalid XML response"
    (with-fake-http [#"^https://api.test-smsbrana.cz"
                     ""]
      (let [[status result _]
            (smsbrana/send {:login "login", :password "password", :text "TEST", :number "+420777666555"}
                           "https://api.test-smsbrana.cz")]
        (is (= :error-unmarshalling status))
        (is (nil? result))))))
