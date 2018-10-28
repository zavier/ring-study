(ns ring-study.core-test
  (:require [clojure.test :refer :all]
            [ring-study.core :refer :all]
            [ring.mock.request :as mock]))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 0 1))))

(deftest handler-test
  (is (= (handler (mock/request :get "/"))
         {:status 200
          :headers {"Authorization" "Bear JFA57AFKJ4FJA"}
          :cookies {"session_id" {:value "session-id-hash"
                                  :max-age 10}}
          :body "Hello World"})))

(deftest your-json-handler-test
  (is (= (your-handler (-> (mock/request :post "/api/endpoint")
                           (mock/json-body {:foo "bar"})))
         {:status  201
          :headers {"content-type" "application/json"}
          :body    {:key "your expected result"}})))