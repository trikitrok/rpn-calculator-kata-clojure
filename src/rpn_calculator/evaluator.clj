(ns rpn-calculator.evaluator)

(defn- remove-used [stack]
  (vec (butlast (butlast stack))))

(defn- invalid-state? [operator last-two-numbers]
  (and (< (count last-two-numbers) 2)
       (not= operator -)))

(defn- check-stack-state! [operator last-two-numbers]
  (when (invalid-state? operator last-two-numbers)
    (throw (Exception. "wrong expression!"))))

(defn- apply-operator [operator stack]
  (let [last-two-numbers (take-last 2 stack)]
    (check-stack-state! operator last-two-numbers)
    (apply operator last-two-numbers)))

(defn- process-symbol [stack symbol]
  (if (number? symbol)
    (conj stack symbol)
    (conj (remove-used stack)
          (apply-operator symbol stack))))

(defn evaluate [symbols]
  (peek (reduce process-symbol [] symbols)))
