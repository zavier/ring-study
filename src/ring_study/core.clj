(ns ring-study.core
  (:require [ring.adapter.jetty :as jetty]
            [ring.util.response :as rp]
            [ring.middleware.file :as f]
            [ring.middleware.cookies :as ck]
            [ring.middleware.content-type :as ct]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.session :refer [wrap-session]]
            [ring.util.response :refer [response content-type status]]
            [ring.middleware.multipart-params :refer [wrap-multipart-params]])
  (:import (java.io File)))

(defn handler [request]
  {:status 200
   :headers {"Authorization" "Bear JFA57AFKJ4FJA"}
   :cookies {"session_id" {:value "session-id-hash"
                           :max-age 10}}
   :body "Hello World"})

(defn what-is-my-ip [request]
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body (:remote-addr request)})

; 返回文件
(defn f-response [request]
  (rp/file-response "index.html" {:root "public"}))

(def file-app
  (f/wrap-file handler "/resources"))

; 会将参数中的 :cookies 添加到 response
(def session-app
  (ck/wrap-cookies handler))

; 根据url后缀判断添加类型，已经定义一些在ring.util.mime-type，此处可以添加扩展自定义类型
(def content-type-app
  (ct/wrap-content-type handler {:mime-types {"foo" "text/x-foo"}}))

(defn param-handler [request]
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body (str (:remote-addr request) (:params request))})
(def param-app
  (wrap-params param-handler))

(defn session-handler [{session :session}]
  (response (str "Hello " (:username session))))
(defn session-handler-new [{session :session, uri :uri}]
  (println session)
  (let [n (session :n 1)]
    (if (= uri "/")
      (-> (response (str "You have visited " n " times"))
          (content-type "text/plain")
          (assoc-in [:session :n] (inc n)))
      (-> (response "Page not found")
          (status 400)))))

(def session-app
  (wrap-session session-handler-new))


(def upload-app
  (-> handler
      wrap-params
      wrap-multipart-params))

(defn start-server []
  (jetty/run-jetty session-app {:host "localhost"
                            :port 3001}))

(start-server)