(ns {{name}}.page
    (:require [hiccup.element :as e]
              [hiccup.page :as p]))

(def page
  (p/html5
   [:head
    [:meta {:charset "utf-8"}]]

   [:body
    [:div#app]
    (e/javascript-tag "alert(\"Hello World!\");")]))
