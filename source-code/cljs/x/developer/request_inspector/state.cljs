
(ns x.developer.request-inspector.state
    (:require [reagent.api :refer [ratom]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @ignore
;
; @atom (keyword)
(defonce INSPECTED-REQUEST (ratom nil))

; @ignore
;
; @atom (integer)
(defonce INSPECTED-HISTORY-DEX (ratom nil))
