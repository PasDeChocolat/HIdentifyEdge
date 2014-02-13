(defproject hident "0.1.0-SNAPSHOT"
  :description "I am awesome."
  :url "http://example.com/FIXME"
  :license {:name "GNU GENERAL PUBLIC LICENSE"
            :url "http://www.gnu.org/licenses/"}

  :source-paths ["src/clj" "src/cljs" "src/brepl"]
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/clojurescript "0.0-2138"]
                 [com.cemerick/piggieback "0.1.3-SNAPSHOT"]
                 [domina "1.0.3-SNAPSHOT"]
                 [hiccups "0.3.0"]
                 [org.clojure/core.async "0.1.256.0-1bf8cf-alpha"]

                 [compojure "1.1.6"]
                 [hiccup "1.0.4"]
                 [ring-server "0.3.0"]
                 [ring/ring-jetty-adapter "1.1.6"]
                 [org.postgresql/postgresql "9.3-1100-jdbc4"]
                 [org.clojure/java.jdbc "0.3.0-alpha4"]
                 [com.jolbox/bonecp "0.8.0.RELEASE"]
                 [lib-noir "0.6.9"]
                 [honeysql "0.4.2"]
                 [org.slf4j/slf4j-log4j12 "1.7.3"]]
  :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}
  :injections [(require '[cljs.repl.browser :as brepl]
                        '[cemerick.piggieback :as pb])
               (defn browser-repl []
                 (pb/cljs-repl :repl-env (brepl/repl-env :port 9000)))]

  :main hident.repl

  ;; Lein-cljsbuild plugin to build a CLJS project
  :plugins [[lein-cljsbuild "1.0.0"]
            [lein-ring "0.8.10"]]

  :min-lein-version "2.0.0"
  :ring {:handler hident.handler/app
         :init hident.handler/init
         :destroy hident.handler/destroy}
  :profiles
  {:production
   {:ring
    {:open-browser? false, :stacktraces? false, :auto-reload? false}}
   :dev
   {:dependencies [[ring-mock "0.1.5"]
                   [ring/ring-devel "1.2.0"]]}}

  ;; cljsbuild options configuration
  :cljsbuild {:builds
              {:dev
               { ;; CLJS source code path
                :source-paths ["src/cljs" "src/brepl"]

                ;; Google Closure (CLS) options
                ;; configuration
                :compiler { ;; CLS generated JS script
                           ;; filename
                           :output-to "resources/public/js/core.js"

                           ;; minimal JS optimization directive
                           :optimizations :whitespace

                           ;; generated JS code prettyfication
                           :pretty-print true}}
               :pre-prod
               { ;; CLJS source code path
                :source-paths ["src/cljs" "src/brepl"]

                ;; Google Closure (CLS) options
                ;; configuration
                :compiler { ;; CLS generated JS script
                           ;; filename
                           :output-to "resources/public/js/core.js"

                           ;; simple optimization directive
                           :optimizations :simple

                           ;; no need for prettyfication
                           :pretty-print false}}
               :prod
               { ;; CLJS source code path
                :source-paths ["src/cljs"]
                ;; Google Closure (CLS) options configuration
                :compiler { ;; CLS generated JS script
                           ;; filename
                           :output-to "resources/public/js/core.js"

                           ;; advanced optimization directive
                           :optimizations :advanced

                           ;; no need for prettyfication
                           :pretty-print false}}}})
