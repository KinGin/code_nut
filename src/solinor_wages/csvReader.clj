(ns solinor_wages.csvReader
  (:require [solinor_wages.model.wage_item :as db]
            [clojure.java.io :as iov]
            [clojure-csv.core :as csv]
            [semantic-csv.core :as sc :refer :all]))

(defn greet ([] {:status 200
                 :headers {"Content-Type" "text/plain"}
                 :body "Tirsdsdssk"}))

(defn parseLine ([line]
                 (let [insertLine line]
                   (println str (insertLine (keyword "Person Name")))
                   (db/insertWork
                    (str (insertLine (keyword "Person Name")))
                    (str (insertLine (keyword "Person ID")))
                    (str (insertLine (keyword "Date")))
                    (str (insertLine (keyword "Start")))
                    (str (insertLine (keyword "End")))))))

(defn readFile ([in-file]
                (let [result (with-open [in-file (iov/reader in-file)]
                               (->> (csv/parse-csv in-file)
                                    remove-comments
                                    mappify
                                    doall))]
                  (doseq [result-set result]
                    (parseLine result-set)))))
