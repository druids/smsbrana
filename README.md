smsbrana
========

A connector for SMS brána [https://www.smsbrana.cz](https://www.smsbrana.cz)

[![CircleCI](https://circleci.com/gh/druids/smsbrana.svg?style=svg)](https://circleci.com/gh/druids/smsbrana)
[![Dependencies Status](https://jarkeeper.com/druids/smsbrana/status.png)](https://jarkeeper.com/druids/smsbrana)
[![License](https://img.shields.io/badge/MIT-Clause-blue.svg)](https://opensource.org/licenses/MIT)


Leiningen/Boot
--------------

```clojure
[smsbrana "0.0.0"]
```


Documentation
-------------

All functions are designed to return errors instead of throwing exceptions.

All API calls return a tuple within following structure: [keyword response http-response] wherekeyword can be:
- :ok when a response is a success and parsed
- :error when a response is parsed but it's an error response
- :error-malformed when a response is not an expected XML structure
- :error-unmarshalling when a response is not a valid XML

A `response` is a parsed body of an original HTTP response.

To be able to run examples this line is needed:

```clojure
(require '[smsbrana.core :as sms])
```

### send

Sends a SMS within `opts`.

```clojure
(sms/send {:login "login", :password "password", :text "TEST", :number "+420777666555"})
;; [:ok
;;  {:credit 1523.32, :price 1.1, :sms_count 1, :sms_id "377351", :err 0}
;;  {:request-time 386, ...
```

Example of an error response:

```clojure
(sms/send {:login "login", :password "password", :text "TEST", :number "+420777666555"})
;; [:error
;;  {:err 4}
;;  {:request-time 386, ...
```

Or

```clojure
(sms/send {:login "login", :password "password", :text "TEST", :number "+420777666555"})
;; [:error-unmarshalling
;;  nil
;;  {:request-time 386, ...
```
