(ns rpn-calculator.calculator
  (:require
    [rpn-calculator.evaluator :as evaluator]
    [rpn-calculator.parser :as parser]
    [rpn-calculator.logging :as logging]))

(defn- log-invalid-symbols [logger symbols]
  (logging/log-invalid-tokens
    logger (parser/invalid-symbols symbols)))

(defn- evaluate [logger expression symbols]
  (try
    (evaluator/evaluate symbols)
    (catch Exception _
      (logging/log-invalid-expression logger expression)
      nil)))

(defn calculate [logger expression]
  (let [symbols (parser/parse-expression expression)
        evaluate (partial evaluate logger expression)]
    (if (parser/correct-expression? symbols)
      (evaluate symbols)
      (log-invalid-symbols logger symbols))))

