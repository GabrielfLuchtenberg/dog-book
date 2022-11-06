(ns dog-book.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[dog-book started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[dog-book has shut down successfully]=-"))
   :middleware identity})
