(ns rpn-calculator.logging
  (:require
    [clojure.tools.logging :as log]))

(defprotocol ErrorsLogger
  (log-invalid-tokens [this errors])
  (log-invalid-expression [this expression]))

(defrecord ClojureLoggingErrorsLogger []
  ErrorsLogger
  (log-invalid-tokens [_ errors]
    (doseq [error errors]
      (log/error (str "unknown token '" (:invalid-token error) "'"))
      nil))
  (log-invalid-expression [_ expression]
    (log/error (str "invalid expression '" expression "'"))
    nil))
