(ns rpn-calculator.calculator-test
  (:require
    [rpn-calculator.test-helpers :as helpers]
    [rpn-calculator.calculator :refer :all]
    [midje.sweet :refer :all]))

(facts
  "about the RPN calculator"

  (facts
    "calculates correct expressions"

    (let [calculate (partial calculate helpers/null-errors-logger)]
      (fact
        "with only a number"
        (calculate "0") => 0
        (calculate "1") => 1)

      (fact
        "adding two numbers"
        (calculate "1 1 +") => 2
        (calculate "0 1 +") => 1)

      (fact
        "subtracting two numbers"
        (calculate "3 2 -") => 1)

      (fact
        "adding several numbers"
        (calculate "1 2 3 + +") => 6)

      (fact
        "with negative numbers"
        (calculate "1 -") => -1
        (calculate "1 - 2 +") => 1
        (calculate "3 - 1 +") => -2)

      (fact
        "multiplying several numbers"
        (calculate "0 1 *") => 0
        (calculate "1 2 5 * *") => 10)

      (fact
        "dividing several numbers (integer division)"
        (calculate "4 2 /") => 2
        (calculate "10 5 5 / /") => 10)))

  (facts
    "rejects incorrect expressions"

    (fact
      "with wrong input"
      (let [logged-errors (atom [])]
        (calculate (helpers/fake-errors-logger logged-errors) "1 2 ' 3 4 _ koko +") => nil
        @logged-errors => ['({:invalid-token "'"}
                              {:invalid-token "_"}
                              {:invalid-token "koko"})]))))
