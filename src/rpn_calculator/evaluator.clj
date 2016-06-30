(ns rpn-calculator.evaluator)

(defn- remove-used [stack]
  (vec (butlast (butlast stack))))

(defn- process-symbol [stack symbol]
  (if (number? symbol)
    (conj stack symbol)
    (conj (remove-used stack)
          (apply symbol (take-last 2 stack)))))

(defn evaluate [symbols]
  (peek (reduce process-symbol [] symbols)))
