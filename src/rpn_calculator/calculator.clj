(ns rpn-calculator.calculator
  (:require
    [rpn-calculator.evaluator :as evaluator]
    [rpn-calculator.parser :as parser]
    [clojure.string :refer [blank?]]
    [rpn-calculator.logger :as logger]))

(defn calculate [logger expression]
  (if (blank? expression)
    (logger/log-empty-expression logger)
    (let [symbols (parser/parse-expression expression)]
      (if (parser/correct-expression? symbols)
        (evaluator/evaluate symbols)
        (logger/log-invalid-tokens
          logger (parser/invalid-symbols symbols))))))

