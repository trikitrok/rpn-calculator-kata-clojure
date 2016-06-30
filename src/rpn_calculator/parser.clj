(ns rpn-calculator.parser
  (:require
    [clojure.string :refer [split blank?]]))

(defn- num-representation? [token]
  (re-find #"\A-?\d+" token))

(def ^:private operators {"+" + "-" - "*" * "/" /})

(defn- parse-token [token]
  (if-let [operator (get operators token)]
    operator
    (if (num-representation? token)
      (Integer/parseInt token)
      {:invalid-token token})))

(def invalid-symbols
  (partial filter #(and (map? %) (get % :invalid-token))))

(defn correct-expression? [terms]
  (empty? (invalid-symbols terms)))

(defn parse-expression [expression]
  (->> (split expression #" ")
       (map parse-token)))
