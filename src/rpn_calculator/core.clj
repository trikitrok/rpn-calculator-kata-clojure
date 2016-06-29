(ns rpn-calculator.core
  (:gen-class))
(use '[clojure.string :only (split)])

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))



(defn- split-string [string]
  (split string #" "))

(defn parse-token [token]
  (try
    (Integer/parseInt token)
    (catch NumberFormatException _
      (let [operators {"+" + "-" - "*" * "/" quot}]
        (if-let [op (get operators token)]
          op)))))

(defn- grow-or-apply
  [stack symbol]
  (if (number? symbol)
    (conj stack symbol)
    (conj (pop (pop stack)) (apply symbol (take-last 2 stack)))))

(defn- traverse-tokens [tokens]
  (reduce grow-or-apply [] tokens ))

(defn calculate [x]
  (let [tokens (->> x split-string (map parse-token))]
    (if (not-empty (filter #(nil? %) tokens))
      nil
      (->> tokens traverse-tokens first))))

