(ns solinor_wages.view.index_view
  (:require [solinor_wages.view.layout :as layout]
           [hiccup.core :refer [h]]))

(defn display-wages [wages]
  [:table {:class "table table-striped table-bordered"}
   [:tr [:th "Person name" ][:th "Person id" ][:th "Wage of month"][:th "Month"] [:th "Year"]]
   (map
    (fn [wage] [:tr [:td (h (wage :person_name))] [:td (h (wage :person))] [:td (str (h (wage :wage_of_month)) "$")] [:td (h (wage :month))] [:td (h (wage :year))]])
    wages)])

(defn index [wages]
  (layout/default
      (display-wages wages)))
