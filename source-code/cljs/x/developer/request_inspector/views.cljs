
(ns x.developer.request-inspector.views
    (:require [fruits.hiccup.api                          :as hiccup]
              [fruits.pretty.api                          :as pretty]
              [reagent.core                               :as reagent]
              [time.api                                   :as time]
              [ugly-elements.api                          :as ugly-elements]
              [x.developer.request-inspector.env          :as request-inspector.env]
              [x.developer.request-inspector.side-effects :as request-inspector.side-effects]
              [x.developer.request-inspector.state        :as request-inspector.state]
              [x.developer.request-inspector.utils        :as request-inspector.utils]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn request-data
  ; @ignore
  []
  (let [show-more?    (reagent/atom false)
        request-props (request-inspector.env/get-inspected-request-props)]
       (fn [] [:<> [ugly-elements/label ::server-response-label
                                        {:content    "Request details:"
                                         :text-color :muted}]
                   [ugly-elements/label ::request-details
                                        {:content   (pretty/mixed->string (if @show-more? request-props (request-inspector.utils/filter-request-data request-props)))
                                         :font-size :xs}]
                   [ugly-elements/horizontal-separator {:height :xs}]
                   [ugly-elements/button ::show-more-button
                                         {:disabled?  (= request-props (request-inspector.utils/filter-request-data request-props))
                                          :content    (if @show-more? "SHOW LESS" "SHOW MORE")
                                          :on-click-f #(swap! show-more? not)}]])))

(defn response-data
  ; @ignore
  []
  (let [request-response (request-inspector.env/get-inspected-server-response)]
       [:<> [ugly-elements/label ::server-response-label
                                 {:content    "Server response:"
                                  :text-color :muted}]
            [ugly-elements/label ::server-response-value
                                 {:content   (pretty/mixed->string request-response)
                                  :font-size :xs}]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn go-bwd-button
  ; @ignore
  []
  (let [request-history-dex (request-inspector.env/get-request-history-dex)]
       [ugly-elements/icon-button ::go-bwd-button
                                  {:disabled?  (= request-history-dex 0)
                                   :icon       :chevron_left
                                   :on-click-f request-inspector.side-effects/inspect-prev-history!}]))

(defn go-fwd-button
  ; @ignore
  []
  (let [request-history-count (request-inspector.env/get-request-history-count)
        request-history-dex   (request-inspector.env/get-request-history-dex)]
       [ugly-elements/icon-button ::go-fwd-button
                                  {:disabled?  (= request-history-count (inc request-history-dex))
                                   :icon        :chevron_right
                                   :on-click-f request-inspector.side-effects/inspect-next-history!}]))

(defn request-history-timestamp
  ; @ignore
  []
  (let [request-props (request-inspector.env/get-inspected-request-props)]
       [ugly-elements/label ::request-history-timestamp
                            {:content (-> request-props :sent-time (time/timestamp-string->date-time :yyyymmdd :hhmmssmmm))
                             :font-size :xs}]))

(defn go-up-button
  ; @ignore
  []
  (let [on-click-f #(request-inspector.side-effects/reset-inspector!)]
       [ugly-elements/icon-button ::go-up-button
                                  {:icon       :arrow_back
                                   :on-click-f on-click-f}]))

(defn request-label
  ; @ignore
  []
  (let [request-id @request-inspector.state/INSPECTED-REQUEST]
       [ugly-elements/label ::request-label
                            {:content (str request-id)}]))

(defn request-data-label-bar
  ; @ignore
  []
  [:div {:style {:display "flex" :justify-content "space-between"}}
        [:div {:style {:display "flex"}}
              [go-up-button]
              [request-label]]
        [:div {:style {:display "flex"}}
              [go-bwd-button]
              [request-history-timestamp]
              [go-fwd-button]]])

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn request-view
  ; @ignore
  []
  [:div {:style {:display "flex" :flex-direction "column" :height "100%"}}
        [request-data-label-bar]
        [:div {:style {:padding "12px" :overflow "scroll"}}
              [request-data]
              [ugly-elements/horizontal-separator {}]
              [response-data]]])

;; -- Request list ------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn request-list-item-timestamp
  ; @ignore
  ;
  ; @param (keyword) request-id
  [request-id]
  (let [request-failed?        (request-inspector.env/request-failed?            request-id)
        request-responsed?     (request-inspector.env/request-responsed?         request-id)
        request-last-sent-time (request-inspector.env/get-request-last-sent-time request-id)]
       [ugly-elements/badge {:content    (time/timestamp-string->date-time request-last-sent-time)
                             :fill-color (cond request-failed? :warning request-responsed? :success :else :primary)}]))

(defn request-list-item
  ; @ignore
  ;
  ; @param (keyword) request-id
  [request-id]
  [ugly-elements/button {:on-click-f #(request-inspector.side-effects/inspect-request! request-id)
                         :content   [:<> (str request-id) [request-list-item-timestamp request-id]]}])

(defn request-list
  ; @ignore
  []
  (let [request-ids (request-inspector.env/get-request-ids)]
       [:div {:style {:display "flex" :flex-direction "column" :overflow "scroll"}}
             (letfn [(f0 [request-id] [request-list-item request-id])]
                    (hiccup/put-with [:<>] request-ids f0))]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn view
  ; @description
  ; Request history inspector for requests sent by the 'x.sync' module.
  ;
  ; @usage
  ; [request-inspector]
  []
  [:<> [ugly-elements/style-scope]
       (if-let [request-id @request-inspector.state/INSPECTED-REQUEST]
               [request-view]
               [request-list])])
