(ns build.hooks
  (:require
   [clojure.core :as c]
   [clojure.java.io :as io]
   [clojure.spec.alpha :as s]
   [clojure.string :as string]))

(s/def ::output-dir string?)
(s/def ::vals (s/+ qualified-symbol?))
(s/def ::file
  (s/and
   (s/map-of simple-keyword? any?)
   (s/keys
    :req-un
    [::vals])))
(s/def ::files
  (s/map-of
   simple-keyword?
   ::file))
(s/def ::options
  (s/and
   (s/map-of
    simple-keyword?
    any?)
   (s/keys
    :req-un
    [::output-dir ::files])))

(defn eval-symbol [sym]
  (require (symbol (namespace sym)))
  (eval sym))

(defn write-css
  {:shadow.build/stage :flush}
  [build-state options]
  {:pre [(s/valid? ::options options)]}
  (prn options)
  (let
   [output-dir (io/file (options :output-dir))]
    (.mkdirs output-dir)
    (prn output-dir)
    (doseq
     [[file file-opts] (options :files)]
      (let
       [output-file (io/file output-dir (str (name file) ".css"))
        css (string/join "\n" (mapv #(eval-symbol %) (file-opts :vals)))]
        (prn output-file)
        (prn css)
        (c/spit output-file css))))
  build-state)
