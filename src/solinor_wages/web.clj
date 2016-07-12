(ns solinor_wages.web
  (:require [solinor_wages.csvReader :as csvReader]
            [solinor_wages.model.migration :as migration]
            [solinor_wages.view.index_view :as index]
            [solinor_wages.view.upload_view :as upload]
            [solinor_wages.model.wage_item :as db]
            [ring.util.response :as response]
            [compojure.core :refer [defroutes GET PUT POST DELETE ANY]]
            [compojure.handler :refer [site]]
            [compojure.route :as route]
            [clojure.java.io :as io]
            [hiccup.core :as h]
            [ring.adapter.jetty :as jetty]
            [environ.core :refer [env]])
  (:gen-class))

(defroutes app
  (GET "/" []
    (index/index (db/all)))
  (route/resources "/")
  (GET "/upload" []
    (upload/upload))
  (GET "/data" []
    (db/deleteData)
    (response/redirect "/"))
  (POST "/upload"
     {{{tempfile :tempfile filename :filename} :file} :params :as params}
     (io/copy tempfile (io/file "resources" "public" filename))
     (csvReader/readFile (str "resources/public/" filename))
     (response/redirect "/"))
  (ANY "*" []
       (route/not-found (slurp (io/resource "404.html")))))

(defn -main [& [port]]
  (migration/migrate)
  (let [port (Integer. (or port (env :port) 5000))]
    (jetty/run-jetty (site #'app) {:port port :join? false})))

;; For interactive development:
;; (.stop server)
;; (def server (-main))

