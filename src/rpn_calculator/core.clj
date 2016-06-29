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
  (let [operators {"+" + "-" - "*" * "/" quot}]
    (get operators token)))

(defn num-representation? [token]
  (re-find #"\A-?\d+" token))


(defn parse-token [token]
  (if-let [operator (operator token)]
    {:result operator}
    (if (num-representation? token)
      {:result (Integer/parseInt token)}
      {:result nil :cause (Exception. (str "unknown token '" token "'"))})))

(defn- grow-or-apply
  [stack symbol]
  (if (number? symbol)
    (conj stack symbol)
    (conj (pop (pop stack)) (apply symbol (take-last 2 stack)))))

(defn- traverse-tokens [tokens]
  (reduce grow-or-apply [] tokens ))

(defn calculate [x]
  (let [tokens (->> x split-string (map parse-token))]
    (if (empty? (filter #(nil? (:result %)) tokens))
      (->> tokens (map :result) traverse-tokens first)
      (do
        (->> tokens
             (filter #(nil? (:result %)))
             (map #(.getMessage (:cause %)))
             (map #(log/error %))
             doall)
        nil))))




