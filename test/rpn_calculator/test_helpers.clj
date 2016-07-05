(ns rpn-calculator.test-helpers
  (:require
    [rpn-calculator.logging :as logger]))

(defrecord NullErrorsLogger []
  logger/ErrorsLogger
  (log-invalid-tokens [_ _])
  (log-invalid-expression [_ _]))

(defrecord FakeErrorsLogger [logged-errors]
  logger/ErrorsLogger
  (log-invalid-tokens [_ errors]
    (swap! logged-errors conj errors)
    nil)
  (log-invalid-expression [_ expression]
    (swap! logged-errors conj expression)
    nil))

(defn fake-errors-logger [an-atom]
  (->FakeErrorsLogger an-atom))

(defn null-errors-logger []
  (->NullErrorsLogger))
