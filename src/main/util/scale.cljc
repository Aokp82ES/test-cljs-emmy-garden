(ns util.scale
  (:require
   [emmy.generic :as g]
   [emmy.numbers]))

(defn typescale [x]
  (g/expt 2 x))
