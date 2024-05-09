(ns css.h1
  (:require
   [util.scale :as scale]
   [garden.core :as garden]))

(def style (garden/css [:h1 {:font-size (str (scale/typescale 1) "rem")}]))
