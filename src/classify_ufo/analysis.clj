(ns classify-ufo.analysis
  (:require
   [clojure.data.json :as json]
   [clj-time.core :as time]
   [clj-time.coerce :as coerce]
   [clojure.string :as str]
   [incanter.core :as i]
   [incanter.stats :as s]))

(defn get-shape-freqs
  [coll min-freq]
  (->> coll
       (map :shape)
       (remove str/blank?)
       frequencies
       (remove #(< (second %) min-freq))
       (sort-by second)
       reverse
       (map #(zipmap [:shape :count] %))
       (into [])))

(defn to_json
  [coll]
  ())
