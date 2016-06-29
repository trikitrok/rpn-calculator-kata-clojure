(ns rpn-calculator.core-test
  (:require [clojure.test :refer :all]
            [rpn-calculator.core :refer :all])
  (:use [midje.sweet]))


(facts
 "about the RPN calculator"

  (fact
   "0"

   (calculate "0") => 0)

   (fact
    "1"
   (calculate "1") => 1)

 (fact "it should add numbers"
       (calculate "1 1 +") => 2)


 (fact "it should add any number"
       (calculate "0 1 +") => 1)


 (fact "it should subtract numbers"
       (calculate "3 2 -") => 1)

 (fact "it should add three numbers"
       (calculate "1 2 3 + +") => 6)

 (fact "it should not accept wrong input"
       (calculate "1 2 3 ' +") => nil
       (calculate "'") => nil)

 (fact "it should not accept empty expressions"
       (calculate "") => nil
       (calculate " ") => nil)
 )
