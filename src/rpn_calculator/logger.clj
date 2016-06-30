(ns rpn-calculator.logger
  (:require
    [clojure.tools.logging :as log]))

(defprotocol ErrorsLogger
  (log-invalid-tokens [this errors])
  (log-empty-expression [this]))

(defrecord ClojureLoggingErrorsLogger []
  ErrorsLogger
  (log-invalid-tokens [_ errors]
    (doseq [error errors]
      (log/error (str "unknown token '" (:invalid-token error) "'"))))

  (log-empty-expression [_]
    (log/error "empty expression")))
