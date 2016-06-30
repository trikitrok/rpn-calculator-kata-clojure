(ns rpn-calculator.evaluator)

(defn- remove-used [stack]
  (let [stack-minus-one-element (pop stack)]
    (if (empty? stack-minus-one-element)
      stack-minus-one-element
      (pop stack-minus-one-element))))

(defn- process-symbol [stack symbol]
  (if (number? symbol)
    (conj stack symbol)
    (conj (remove-used stack)
          (apply symbol (take-last 2 stack)))))

(defn evaluate [symbols]
  (peek (reduce process-symbol [] symbols)))
