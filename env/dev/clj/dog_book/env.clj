(ns dog-book.env
  (:require
    [selmer.parser :as parser]
    [clojure.tools.logging :as log]
    [dog-book.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[dog-book started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[dog-book has shut down successfully]=-"))
   :middleware wrap-dev})
