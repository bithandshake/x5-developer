
(ns x.developer.request-inspector.utils)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn filter-request-data
  ; @ignore
  ;
  ; @param (map) n
  ;
  ; @return (map)
  [n]
  (cond-> n (:error-handler-f    n) (assoc :error-handler-f    "...")
            (:response-f         n) (assoc :response-f         "...")
            (:progress-handler-f n) (assoc :progress-handler-f "...")
            (:response-handler-f n) (assoc :response-handler-f "...")
            (:validator-f        n) (assoc :validator-f        "...")))
