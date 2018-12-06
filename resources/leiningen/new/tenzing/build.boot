(set-env!
 :source-paths {{{source-paths}}}
 :resource-paths #{"resources"}
 :dependencies '[[org.clojure/clojurescript "1.9.946"]

                 ;; Boot dev environment
                 [adzerk/boot-cljs            "2.1.1"      :scope "test"]
                 [adzerk/boot-cljs-repl       "0.3.3"      :scope "test"]
                 [crisptrutski/boot-cljs-test "0.3.4"      :scope "test"]
                 [adzerk/boot-reload          "0.5.1"      :scope "test"]
                 [pandeiro/boot-http          "0.8.3"      :scope "test"]
                 [samestep/boot-refresh       "0.1.0"      :scope "test"]{{{boot-deps}}}

                 ;; cljs development environment
                 [com.cemerick/piggieback   "0.2.1"      :scope "test"]
                 [org.clojure/tools.nrepl   "0.2.13"     :scope "test"]
                 [weasel                    "0.7.0"      :scope "test"]
                 [org.clojure/tools.nrepl   "0.2.13"     :scope "test"]{{#deps}}

                 ;; Optional deps
                 {{{deps}}}{{/deps}}])

(require
 '[adzerk.boot-cljs      :refer [cljs]]
 '[adzerk.boot-cljs-repl :refer [cljs-repl start-repl]]
 '[crisptrutski.boot-cljs-test :refer [test-cljs]]
 '[adzerk.boot-reload    :refer [reload]]
 '[samestep.boot-refresh :refer [refresh]]
 '[pandeiro.boot-http    :refer [serve]]{{{requires}}})


;; Pipeline configuration tasks

(deftask production []
  (task-options! cljs {:optimizations :advanced}{{{production-task-opts}}})
  identity)

(deftask development []
  (task-options! cljs {:optimizations :none}
                 reload {:on-jsload '{{name}}.app/init}{{{development-task-opts}}})
  identity)

(deftask testing
  "Configure a task pipeline with testing options"
  []
  (set-env! :source-paths #(conj % "test"))
  identity)


;; Low-level do-the-stuff tasks

(deftask build
  "This task contains all the necessary steps to produce a build
   You can use 'profile-tasks' like `production` and `development`
   to change parameters (like optimizations level of the cljs compiler)"
  []
  (comp (notify :audible true)
        {{{pre-build-steps}}}
        (cljs)
        {{{build-steps}}}))

(deftask run
  "The `run` task wraps the building of your application in some
   useful tools for local development: an http server, a file watcher
   a ClojureScript REPL and a hot reloading mechanism"
  []
  (comp (serve)
        (watch)
        (cljs-repl)
        {{{run-steps}}}
        (reload)
        (build)))


;; High-level daily usage tasks

(deftask release
  "Create a time-stamped zip file for deploying"
  []
  (require 'atreus.task.index
           'atreus.task.time)
  (comp (production)
        ((resolve 'atreus.task.index/write-index))
        (build)
        (sift :include #{#"\.out" #"\.cljs.edn$"} :invert true)
        (zip :file (str "atreus-layout-" ((resolve 'atreus.task.time/now)) ".zip"))
        (target :dir #{"target"})))

(deftask dev
  "Simple alias to run application in development mode"
  []
  (comp (development)
        (testing)
        (run)
        (refresh)))

;;; Silence name collision warning
(ns-unmap 'boot.user 'test)
(deftask test
  "Run the clojurescript tests"
  []
  (comp (testing)
        (test-cljs :js-env :phantom
                   :exit?  true)))

(deftask auto-test
  "Setup an automatic test runner"
  []
  (comp (testing)
        (watch)
        (notify :audible true)
        (alt-test)))

{{{tasks}}}
