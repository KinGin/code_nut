(ns solinor_wages.model.migration
  (:require [clojure.java.jdbc :as sql]
            [solinor_wages.model.wage_item :as wage_item]))

(defn migrated? []
  (-> (sql/query wage_item/spec
                 [(str "select count(*) from information_schema.tables "
                       "where table_name='wages'")])
      first :count pos?))

(defn migrate []
  (when (not (migrated?))
    (print "Creating database structure...") (flush)
    (sql/db-do-commands wage_item/spec
                        (sql/create-table-ddl
                         :wages
                         [[:id :serial "PRIMARY KEY"]
                          [:person_name :text]
                          [:person_id :text]
                          [:worktime :real]
                          [:month :int]
                          [:year :int]]))
    (sql/db-do-commands wage_item/spec
                        (sql/create-table-ddl
                         :wages_person
                         [[:id :serial "PRIMARY KEY"]
                          [:person_name :text]
                          [:person :text]
                          [:wage_of_month :real]
                          [:month :int]
                          [:year :int]]))
    (println " done")))
