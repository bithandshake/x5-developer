
(ns x.developer.request-inspector.env
    (:require [x.sync.request-handler.state]
              [x.sync.response-handler.state]
              [map.api                             :as map]
              [noop.api                            :refer [return]]
              [vector.api                          :as vector]
              [x.developer.request-inspector.state :as request-inspector.state]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn get-request-ids
  ; @ignore
  ;
  ; @description
  ; Returns the request IDS read from the request history.
  []
  (map/get-keys @x.sync.request-handler.state/REQUEST-HISTORY))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn get-request-last-sent-time
  ; @ignore
  ;
  ; @description
  ; Returns the LAST sending time of a specific request.
  ;
  ; @param (keyword) request-id
  [request-id]
  (-> @x.sync.request-handler.state/REQUEST-HISTORY request-id last :sent-time))

(defn request-responsed?
  ; @ignore
  ;
  ; @description
  ; Returns whether the LAST sending of a specific request has been already responsed.
  ;
  ; @param (keyword) request-id
  [request-id]
  (if-let [request-last-sent-time (get-request-last-sent-time request-id)]
          (-> @x.sync.response-handler.state/RESPONSE-HISTORY request-id (get request-last-sent-time))))

(defn request-failed?
  ; @ignore
  ;
  ; @description
  ; Returns whether the LAST sending of a specific request has been failed.
  ;
  ; @param (keyword) request-id
  [request-id]
  (if-let [request-last-sent-time (get-request-last-sent-time request-id)]
          (-> @x.sync.response-handler.state/RESPONSE-HISTORY request-id (get request-last-sent-time) :failure)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn get-request-history-dex
  ; @ignore
  []
  (if-let [request-id @request-inspector.state/INSPECTED-REQUEST]
          (if-let [request-history-dex @request-inspector.state/INSPECTED-HISTORY-DEX]
                  ; Returns the currently inspected history index
                  (return request-history-dex)
                  ; Returns the last history index of the currently inspected request (as default)
                  (-> @x.sync.request-handler.state/REQUEST-HISTORY request-id vector/last-dex))))

(defn get-request-history-count
  ; @ignore
  ;
  ; @description
  ; Returns how many times the request has been sent (and its settings stored in the history).
  ;
  []
  (if-let [request-id @request-inspector.state/INSPECTED-REQUEST]
          (-> @x.sync.request-handler.state/REQUEST-HISTORY request-id count)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn get-inspected-request-props
  ; @ignore
  ;
  ; @description
  ; Returns the actually inspected request's properties.
  []
  (if-let [request-id @request-inspector.state/INSPECTED-REQUEST]
          (if-let [request-history-dex (get-request-history-dex)]
                  (-> @x.sync.request-handler.state/REQUEST-HISTORY request-id (get request-history-dex)))))

(defn get-inspected-server-response
  ; @ignore
  ;
  ; @description
  ; Returns the actually inspected request's server response.
  []
  (if-let [request-id @request-inspector.state/INSPECTED-REQUEST]
          (if-let [request-history-dex (get-request-history-dex)]
                  (if-let [sent-time (-> @x.sync.request-handler.state/REQUEST-HISTORY request-id (get request-history-dex) :sent-time)]
                          (-> @x.sync.response-handler.state/RESPONSE-HISTORY request-id (get sent-time))))))
