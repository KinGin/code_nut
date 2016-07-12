(ns clojure-getting-started.csvReader
  (:require [[clojure.data.csv :as csv]]))

(defn greet ([] {:status 200
   :headers {"Content-Type" "text/plain"}
   :body "Tirsdsdssk"}))



(with-open [in-file (io/reader "in-file.csv")]
  (doall
    (csv/read-csv in-file)))

(with-open [out-file (io/writer "out-file.csv")]
  (csv/write-csv out-file
                 [["abc" "def"]
                  ["ghi" "jkl"]]))
