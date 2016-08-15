(ns solinor_wages.view.upload_view
  (:require [solinor_wages.view.layout :as layout]
            [hiccup.core :refer [h]]
            [hiccup.form :as form]))

(defn file-upload-form []
  [:form {:action "/upload" :method "post" :enctype "multipart/form-data"}
   [:input {:name "file" :type "file"}]
   [:input {:type "submit" :name "submit" :value "submit"}]])

(defn upload []
  (layout/default
    (file-upload-form)))
