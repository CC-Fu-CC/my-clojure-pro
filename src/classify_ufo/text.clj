(ns classify-ufo.text
  (:require [clojure.java.io :as io]
            [clojure.core.reducers :as r]
            [clojure.string :as str]
            [clojure.data.json :as json]
            [clj-time.format :as tf]
            [classify-ufo.util :refer :all]
            [me.raynes.fs :as fs])
  (:import [java.lang.StringBuffer]))

(defrecord UFOSighting
        [sighted-at reported-at location shape duration description year month season])
(defn ->ufo
        [row]
        (let [row (cond
                                (> (count row) 6)
                                                (concat (take 5 row)
                                                        [(str/join \t (drop 5 row))])
                                (< (count row) 6)
                                                (concat row (repeat (- 6 (count row)) nil))
                                :else row)]
        (apply ->UFOSighting (concat row [nil nil nil]))))

(def date-formatter (tf/formatter "yyyyMMdd"))
(defn read-date
        [date-str]
        try
                (tf/parse date-formatter date-str)
                (catch Exception ex
                        nil))

(defn coerce-fields
        [ufo]
        (assoc ufo
                        :sighted-at (read-date (:sighted-at ufo))
                        :reported-at (read-date (:reported-at ufo))))

(defn read-date
        [filename]
        (with-open [f (io/reader filename)]
                (->>(csv/read-csv f :separator \tab)
                        vec
                        (r/map ->ufo)
                        (r/map coerce-fields)
                        (into []))))
