(ns solinor_wages.view.layout
  (:require [hiccup.page :as h]))

(defn default [& body]
  (h/html5
   [:head
    [:meta {:charset "utf-8"}]
    [:meta {:name "viewport" :content
            "width=device-width, initial-scale=1, maximum-scale=1"}]
    [:title "wages"]
    (h/include-css "/css/bootstrap.min.css"
                   "/css/bootstrap.css"
                   "/css/bootstrap-theme.min.css")]
   [:nav {:class "navbar navbar-default" :style "button margin-left:3px"}
    [:a {:class "navbar-brand" :href "/"} "Wage system 2000"]
    [:a {:href "/"} [:button {:class "navbar-btn btn btn-default" :type "button"} "Home"]]
    [:a {:href "/upload"} [:button {:class "navbar-btn btn btn-default" :type "button" :href "/upload"} "Import csv"]]
    [:a {:href "/data"} [:button {:class "navbar-btn btn btn-default" :type "button" :href "/upload"} "Delete data"]]]
   [:body
    [:div {:id "content" :class "container"} body]]))
