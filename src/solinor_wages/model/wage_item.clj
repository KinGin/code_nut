(ns solinor_wages.model.wage_item
  (:require [clojure.java.jdbc :as sql]
            [clojure.string :as str]))

(def spec { :classname "org.postgresql.Driver"
            :subprotocol "postgresql"
            :subname (or (System/getenv "DATABASE_URL") "//localhost:5432/solinorapp")
            :user "solinorapp"
            :password "solinorapp"})

(def HOUR 60)
(def normalwage 3.75)
(def overtimewage 4.90)

(defn all []
  (into [] (sql/query spec ["select * from wages_person order by person desc"])))

(defn deleteData []
  (sql/delete! spec :wages_person [])
  (sql/delete! spec :wages []))


(defn getMonth[date]
  (let [dateSplit (clojure.string/split date #"\.")]
    (if (>= (count dateSplit) 2)
      (read-string (second dateSplit))
      (read-string (count dateSplit)))
    ))

(defn getYear[date]
  (let [dateSplit (clojure.string/split date #"\.")]
    (if (== (count dateSplit) 3)
      (read-string (last dateSplit))
      (read-string (count dateSplit)))
    ))

(defn determineWage[start]
  (let [startSplit (clojure.string/split start #":")]
      (if (>= (read-string (first startSplit)) 18)
        overtimewage
        normalwage)
    ))

;; Worktime in minutes
(defn calculateWage[worktime wage]
  (read-string (format "%.2f" (+ (* (quot worktime HOUR) wage) (* (float (/ (mod worktime HOUR) HOUR)) wage)))))

(defn wage_for_person_for_month_exists? [id month year]
  (-> (sql/query spec
                 [(str "select count(*) from wages_person "
                       "where person= ? and
                       month = ? and
                       year = ?") id month year])
      first :count pos?))

(defn getMinutes[start end]
  (let [startSplit (clojure.string/split start #":") endSplit (clojure.string/split end #":")]
  (+ ( * (- (read-string (first endSplit)) (read-string (first startSplit))) 60) (- (read-string (second endSplit)) (read-string (second startSplit))))))

(defn updateWage[person_name person_id wage worktime month year]
  (if (wage_for_person_for_month_exists? person_id month year)
    (sql/execute! spec ["update wages_person set wage_of_month =(wage_of_month + ?) where person = ?" (calculateWage worktime wage) person_id] )
    (sql/insert! spec :wages_person {:person_name person_name :person person_id :wage_of_month (calculateWage worktime wage) :month month :year year}))
  ;;(sql/update! spec :wages_person {:person_name person_name :wage_of_month worktime :month month} ["person = ?" person_id "month =?" month "year = ?" year]))
)

(defn insertWork [person_name person_id date start end]
  (sql/insert! spec :wages {:person_name person_name :person_id person_id :worktime (getMinutes start end) :month (getMonth date) :year (getYear date)})
  (updateWage person_name person_id (determineWage start) (getMinutes start end) (getMonth date) (getYear date)))



