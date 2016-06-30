(ns rpn-calculator.core
  (:gen-class)
  (:require
    [rpn-calculator.logger :as logger]
    [rpn-calculator.calculator :as calculator]))

(defn -main [& args]
  (let [expression (first args)
        logger logger/->ClojureLoggingErrorsLogger
        calculate (partial calculator/calculate logger)]
    (calculate expression)))
