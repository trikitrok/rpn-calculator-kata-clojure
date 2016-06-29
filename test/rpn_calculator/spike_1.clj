(ns rpn-calculator.spike-1)
(use '[clojure.string :only (split)])


(take 3 '(1 2 3 4))

(apply + '(1 2 3))

(reduce
  +
  (map #(Integer/parseInt %)
       (split "1 2 3" #" ")))


(map #(Integer/parseInt %)
     (take 2
           (split "1 2 +" #" ")))


(defn pop-expr [stac]
  (pop stac))


(defn test-peek [stac]
  (peek stac))

(= (test-peek '(\+ 2 3)) \+)


(def single-expr [1 2 +])

single-expr

(= + +)


(pop-expr single-expr)


;represent a stack as a seq
;push
(conj [1] 2)

;peek
(peek [1 2])

;pop
(pop [1 2])


(def stack [0 1 2 3])

stack

(def stack2 [:test])


(defn operator? [string]
  (= string \+))

(defn apply-operator [operator stack]
  (conj
    (pop (pop stack))
    (operator
      (first stack) (second stack))))

(apply-operator - [0 1])

(defn apply-all-operators [stack]
  (if (= (count stack) 1)
    (first stack)
    (apply-all-operators
      (apply-operator (peek stack) (pop stack)))))

(apply-all-operators [0 1 + 2 +])

(defn rpn-iterative [operands operator]
  (do
    (peek (pop operands))
    (peek (pop operands))))


(rpn-iterative '(1 2 4)  +)

(defn rpn-iterative-all [tokens]
  )

(defn- process-symbol [stack symbol]
  (if (number? symbol)
    (conj stack symbol)
    (conj (pop (pop stack))
          (apply symbol (take-last 2 stack)))))

(defn evaluate [expression]
  (peek
    (reduce
      process-symbol
      []
      expression)))


(evaluate '[1 2 +])


(defn- parse-int [s]
  (Integer/parseInt (re-find #"\A-?\d+" s)))

(defn- parse-token [token]
  (let
    [operators {"+" + "-" - "*" * "/" quot}]
    (if-let [op (get operators token)]
      op
      (parse-int token))))

(defn parse [expression]
  (map parse-token
       (split expression #"\s")))

(defn- process-symbol [stack symbol]
  (if (number? symbol)
    (conj stack symbol)
    (conj (pop (pop stack))
          (apply symbol (take-last 2 stack)))))

(defn evaluate [expression]
  (peek
    (reduce
      process-symbol
      []
      (parse expression))))




'(1 2 +)
(= (nth '(1 2 +) 2) '+)


(def simple-expr-seq '(1 2 +))
(pop simple-expr-seq)
