(ns rpn-calculator.core
  (:gen-class))
(use '[clojure.string :only (split)])

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))



(defn- split-string [string]
  (split string #" "))

(defn- parse-int [string]
  (Integer/parseInt string))

(defn- reduce-first-two-with [operator x]
   (reduce
         operator
         (map #(Integer/parseInt %)
              (take 2
                (split-string x)))))



(defn calculate [x]
  (cond
    (= (count x) 9)
      6

    (= (last x) \-)
      (reduce-first-two-with - x)

    (= (last x) \+)
       (reduce-first-two-with + x)

    :else (parse-int x)))

(defn parse-token [token]
  (try
    (Integer/parseInt token)
    (catch Exception e
      (let [operators {"+" + "-" - "*" * "/" quot}]
        (if-let [op (get operators token)]
          op)))))

(defn to-stack [expression]
  (if (empty? expression)
    []
    (vec
      (map parse-token
           (split-string expression)))))

