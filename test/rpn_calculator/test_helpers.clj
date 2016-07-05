(ns rpn-calculator.test-helpers
  (:require
    [rpn-calculator.logger :as logger]))

(defrecord NullErrorsLogger []
  logger/ErrorsLogger
  (log-invalid-tokens [_ _]))

(defrecord FakeErrorsLogger [logged-errors]
  logger/ErrorsLogger
  (log-invalid-tokens [_ errors]
    (swap! logged-errors conj errors)
    nil))

(defn fake-errors-logger [an-atom]
  (->FakeErrorsLogger an-atom))

(defn null-errors-logger []
  (->NullErrorsLogger))
