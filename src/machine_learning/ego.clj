(ns machine_learning.ego
  (:require [clojure.java.io :as io]
            [clojure.set :as set]
            [clojure.core.reducers :as r]
            [clojure.string :as string]
            [clojure.data.json :as json]
            [machine_learning.graph :as g]

            [me.raynes.fs :as fs])
  (:import [java.io.File]))
(defn read-edge-file
  [filename]
  (with-open
      [f (io/reader filename)]
    (->>
     f
     line-seq
     (r/map #(string/split % #"\s+"))
     (r/map #(mapv (fn [x] (Long/parseLong x)) %))
     (r/reduce #(g/add %1 (first %2) (second %2))
               g/empty-graph))))

(defn read-edge-files
  [ego-dir]
 (r/reduce g/merge-graphs {}
           (r/map read-edge-file
                  (fs/find-files ego-dir #".*\.#ego.clj"))

))
