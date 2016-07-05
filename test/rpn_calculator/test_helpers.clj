(ns rpn-calculator.test-helpers
  (:require
    [rpn-calculator.logging :as logging]
    [midje.sweet :refer :all]))

(defrecord NullErrorsLogger []
  logging/ErrorsLogger
  (log-invalid-expression [_ _])
  (log-invalid-tokens [_ _]))

(defrecord FakeErrorsLogger [logged-errors]
  logging/ErrorsLogger
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

(defn check-expression-calculation-fails-with-expected-logged-errors
  [calculate-fn expression expected-logged-errors]
  (fact
    (let [logged-errors (atom [])]
      (calculate-fn (fake-errors-logger logged-errors) expression) => nil
      @logged-errors => expected-logged-errors)))