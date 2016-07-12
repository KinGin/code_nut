(ns solinor_wages.view.upload_view
  (:require [solinor_wages.view.layout :as layout]
            [hiccup.core :refer [h]]
            [hiccup.form :as form]))

(comment (defn file-upload-form []
  [:div {:class "container"}
  (form/form-to [:post "/upload"]
                 (form/file-upload "file")
                 (form/label "shout" "What do you want to SHOUT?")
                 (form/submit-button "upload csv"))]))

(defn file-upload-form []
  [:form {:action "/upload" :method "post" :enctype "multipart/form-data"}
   [:input {:name "file" :type "file"}]
   [:input {:type "submit" :name "submit" :value "submit"}]])

(defn upload []
  (layout/default
      (file-upload-form)))
