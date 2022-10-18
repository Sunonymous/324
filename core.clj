(ns threetwofour.core
  (:require [clojure.string :refer [index-of]]))

; 324
; this pattern is seen frequently on clocks
; though sometimes in another order
; i'd like to know how many times these patterns
; can appear on a clock, including military time

(def target-digits ["243" "324" "342" "423" "432"])

; this pattern is seen least frequently and is less interesting to me
;   so i'm keeping it separate just to see the difference
(def with-234 (conj target-digits "234"))

(defn pad-single-char-string
  "Add a single leading zero if the string given is only one character."
  [s]
  (if (= (count s) 1)
    (str "0" s)
    s))

(def hours (for [hour (map str (range 24))]
               (pad-single-char-string hour)))

(def minutes (for [min (map str (range 60))]
               (pad-single-char-string min)))

(def all-clock-times (for [hr hours
                     mn minutes]
                 (str hr mn)))

(defn contains-one-of
  "Returns true if a given string contains one of the query strings in the vector queries. False otherwise."
  [queries s]
  (< 0 (count (remove nil? (map #(index-of s %) queries)))))

(def synchronous-times     (filter #(contains-one-of target-digits %) all-clock-times))
(def all-synchronous-times (filter #(contains-one-of with-234 %)      all-clock-times))

(defn format-time [time]
  (str (subs time 0 2) ":" (subs time 2)))

(println "\nSynchronous Times: " (map format-time synchronous-times))
(println "\t Total: "(count synchronous-times))

;; => Synchronous Times:  (02:43 03:24 03:42 04:23 04:32 12:43 13:24 13:42 14:23 14:32 22:43 23:24 23:42)
;; =>     Total:  13

;; this pattern makes the entire sequence of 23:4- visible. meh.
;; (println "\nIncluding 234: " (map format-time all-synchronous-times))
;; (println "\t Total: " (count all-synchronous-times))

;; => Including 234:  (02:34 02:43 03:24 03:42 04:23 04:32 12:34 12:43 13:24 13:42 14:23 14:32 22:34 22:43 23:24 23:40 23:41 23:42 23:43 23:44 23:45 23:46 23:47 23:48 23:49)
;; =>     Total:  25
