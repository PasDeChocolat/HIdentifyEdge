(ns hident.handler
  (:require [compojure.core :refer [defroutes GET]]
            [compojure.route :as route]
            [clojure.java.jdbc :as sql]
            [noir.util.middleware :as noir-middleware]
            [noir.session :as session]))

(defn init []
  (println "starting"))

(defn destroy []
  (println "shutting down"))

; (defn user-access [request]
;   (session/get :user))

(defroutes app-routes
  (GET "/" [] "<p>Hello from compojure</p>")
  (route/resources "/")
  (route/not-found "Page not found"))

; (def handler
;   (handler/site app-routes))

(def app
  (noir-middleware/app-handler
   [app-routes]
   ; :access-rules [user-access]
   ))

(def war-handler (noir-middleware/war-handler app))
