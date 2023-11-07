
(ns x.developer.request-inspector.side-effects
    (:require [x.developer.request-inspector.env   :as request-inspector.env]
              [x.developer.request-inspector.state :as request-inspector.state]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn reset-inspector!
  ; @ignore
  []
  (reset! request-inspector.state/INSPECTED-REQUEST     nil)
  (reset! request-inspector.state/INSPECTED-HISTORY-DEX nil))

(defn inspect-request!
  ; @ignore
  ;
  ; @param (keyword) request-id
  [request-id]
  (reset! request-inspector.state/INSPECTED-REQUEST request-id))

(defn inspect-prev-history!
  ; @ignore
  []
  (let [request-history-dex (request-inspector.env/get-request-history-dex)]
       (reset! request-inspector.state/INSPECTED-HISTORY-DEX (dec request-history-dex))))

(defn inspect-next-history!
  ; @ignore
  []
  (let [request-history-dex (request-inspector.env/get-request-history-dex)]
       (reset! request-inspector.state/INSPECTED-HISTORY-DEX (inc request-history-dex))))
