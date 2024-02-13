
(ns x.developer.request-inspector.state
    (:require [reagent.core :as reagent]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @ignore
;
; @atom (keyword)
(defonce INSPECTED-REQUEST (reagent/atom nil))

; @ignore
;
; @atom (integer)
(defonce INSPECTED-HISTORY-DEX (reagent/atom nil))
