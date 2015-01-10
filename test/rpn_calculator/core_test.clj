(ns rpn-calculator.core-test
  (:require [clojure.test :refer :all]
            [rpn-calculator.core :refer :all])
  (:use [midje.sweet]))


(deftest a-test
  (testing "canary test"
    (is (= 0 0))))

(fact
   "canary test in midje"
   (= 0 0) => true
 )

(facts
 "about the RPN calculator"

 (fact
    "it should sum numbers"
    (sum "0 0 +") => 0
  ))
