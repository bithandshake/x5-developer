
(ns x.developer.api
    (:require [x.developer.request-inspector.views :as request-inspector.views]
              [x.developer.route-browser.views     :as route-browser.views]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; x.developer.request-inspector.views
(def request-inspector request-inspector.views/view)

; x.developer.route-browser.views
(def route-browser route-browser.views/view)
