(ns classify_ufo.topic-model
  (:require [clojure.string :as str]
            [clojure.java.io :as io]
            [clojure.data.csv :as csv])
  (:import [cc.mallet.util.*]
           [cc.mallet.types InstanceList]
           [cc.mallet.pipe
            Input2CharSequence TokenSequenceLowercase
            CharSequence2TokenSequence SerialPipes
            TokenSequenceRemoveStopwords
            TokenSequence2FeatureSequence]
           [cc.mallet.pipe.iterator FileListIterator]
           [cc.mallet.topics ParallelTopicModel]
           [java.io FileFilter]
           [java.util Formatter Locale]))


(defn make-pipe-list
  []
  (InstanceList.
   (SerialPipes.
    [(Input2CharSequence. "UTF-8")
     (CharSequence2TokenSequence.
      #"\p{L}[\p{L}\p{P}]+\p{L}")
     (TokenSequenceLowercase.)
     (TokenSequenceRemoveStopwords. false false)
     (TokenSequence2FeatureSequence.)])))


(defn add-dir-files
  [instance-list data-dir]
  (.addThruPipe
   instance-list
   (FileListIterator.
    (.listFiles (io/file data-dir))
    (reify FileFilter
      (accept [this pathname] true))
    #"/(.).txt$"
    true)))

(defn train-model
  ([instances] (train-model 100 4 50 instances))
  ([num-topics num-threads num-iterations instances]
     (doto (ParallelTopicModel. num-topics 1.0 0.01)
       (.addInstances instances)
       (.setNumthreads num-threads)
       (.setNumIterations num-iterations)
       (.estimate))))
