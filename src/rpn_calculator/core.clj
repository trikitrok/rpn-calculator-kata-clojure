(ns rpn-calculator.core
  (:gen-class)
  (:require [clojure.tools.logging :as log]))
(use '[clojure.string :only (split)])
(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))



(defn- split-string [string]
  (split string #" "))

(defn- operator [token]
  (let [operators {
                   "+" {:fn + :operands 2}
                   "-" {:fn - :operands 2}
                   "*" {:fn * :operands 2}
                   "/" {:fn quot :operands 2}
                   "~" {:fn - :operands 1}}]
    (get operators token)))

(defn num-representation? [token]
  (re-find #"\A-?\d+" token))


(defn parse-token [token]
  (if-let [operator (operator token)]
    {:result operator}
    (if (num-representation? token)
      {:result (Integer/parseInt token)}
      {:result nil :cause (Exception. (str "unknown token '" token "'"))})))

(defn- repeat-reduce
  "repeats the function f as many times as indicated in n.
  As is, the function is applied to the output of the previous invocation, so it's a composed operation:

  (repeat-reduce 3 f) =  (f (f (f)))
  ((repeat-reduce 3 f) x) =  (f (f (f x)))"

  [n f]
  (apply comp (repeat n f)))

(defn- grow-or-apply
  [stack symbol]
  (if (number? symbol)
    (conj stack symbol)
    (do
      (let [rest-stack ((repeat-reduce (:operands symbol) pop) stack)
            new-operand (apply (:fn symbol) (take-last (:operands symbol) stack))]
        (conj rest-stack new-operand)))))

(defn- traverse-tokens [tokens]
  (reduce grow-or-apply [] tokens ))

(defn- log-wrong
  [tokens]
  (->> tokens
       (map #(.getMessage (:cause %)))
       (map #(log/error %))
       doall))

(defn calculate [x]
  (let [tokens (->> x split-string (map parse-token))
        wrong-tokens (filter #(nil? (:result %)) tokens)]
    (if (empty? wrong-tokens)
      (->> tokens (map :result) traverse-tokens first)
      (do (log-wrong wrong-tokens) nil))))
