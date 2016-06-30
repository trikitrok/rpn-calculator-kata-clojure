(ns rpn-calculator.test-helpers
  (:require
    [rpn-calculator.logger :as logger]))

(defrecord NullErrorsLogger []
  logger/ErrorsLogger
  (log-invalid-tokens [_ _])
  (log-empty-expression [_]))

(defrecord FakeErrorsLogger [logged-errors]
  logger/ErrorsLogger
  (log-invalid-tokens [_ errors]
    (swap! logged-errors conj errors))
  (log-empty-expression [_]
    (swap! logged-errors conj :empty-expression)))

(defn fake-errors-logger [an-atom]
  (->FakeErrorsLogger an-atom))

(defn null-errors-logger []
  (->NullErrorsLogger))
