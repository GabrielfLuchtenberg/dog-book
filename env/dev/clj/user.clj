(ns user
  "Userspace functions you can run by default in your local REPL."
  (:require
   [dog-book.config :refer [env]]
   [dog-book.db.core :as db]
    [clojure.pprint]
    [clojure.spec.alpha :as s]
    [expound.alpha :as expound]
    [mount.core :as mount]
    [dog-book.core :refer [start-app]]
    [dog-book.db.core]
    [conman.core :as conman]
    [luminus-migrations.core :as migrations]))

(alter-var-root #'s/*explain-out* (constantly expound/printer))

(add-tap (bound-fn* clojure.pprint/pprint))

(defn start
  "Starts application.
  You'll usually want to run this on startup."
  []
  (mount/start-without #'dog-book.core/repl-server))

(defn stop
  "Stops application."
  []
  (mount/stop-except #'dog-book.core/repl-server))

(defn restart
  "Restarts application."
  []
  (stop)
  (start))

(defn restart-db
  "Restarts database."
  []
  (mount/stop #'dog-book.db.core/*db*)
  (mount/start #'dog-book.db.core/*db*)
  (binding [*ns* (the-ns 'dog-book.db.core)]
    (conman/bind-connection dog-book.db.core/*db* "sql/queries.sql")))

(defn reset-db
  "Resets database."
  []
  (migrations/migrate ["reset"] (select-keys env [:database-url])))

(defn migrate
  "Migrates database up for all outstanding migrations."
  []
  (migrations/migrate ["migrate"] (select-keys env [:database-url])))

(defn rollback
  "Rollback latest database migration."
  []
  (migrations/migrate ["rollback"] (select-keys env [:database-url])))

(defn create-migration
  "Create a new up and down migration file with a generated timestamp and `name`."
  [name]
  (migrations/create name (select-keys env [:database-url])))


(comment
  (start)
  (migrate)

  (db/create-user!
   {:id 1
    :first-name "Gab"
    :last-name "Luch"
    :email "gab@gab.gab"
    :pass "123456"})
  ,)
